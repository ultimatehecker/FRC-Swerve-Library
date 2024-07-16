package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.utilities.constants.Constants;

public class SwerveSubsystem extends SubsystemBase {
    private final AHRS gyroscope;

    private SwerveDriveOdometry swerveOdometry;
    private NEOSwerveModule[] swerveModules;
    private SwerveDriveKinematics driveKinematics;

    private SlewRateLimiter translationLimiter = new SlewRateLimiter(2.9);
    private SlewRateLimiter strafeLimiter = new SlewRateLimiter(2.9);
    private SlewRateLimiter rotationLimiter = new SlewRateLimiter(2.9);

    private Field2d field;

    public SwerveSubsystem() {
        gyroscope = new AHRS(SPI.Port.kMXP);
        resetHeading();

        swerveModules = new NEOSwerveModule[] {
                new NEOSwerveModule(0, Constants.ModuleConstants.FrontLeftModule.constants),
                new NEOSwerveModule(1, Constants.ModuleConstants.FrontRightModule.constants),
                new NEOSwerveModule(2, Constants.ModuleConstants.BackLeftModule.constants),
                new NEOSwerveModule(3, Constants.ModuleConstants.BackRightModule.constants)
        };

        driveKinematics = Constants.SwerveConstants.SwerveKinematics;
        swerveOdometry = new SwerveDriveOdometry(driveKinematics, getYawRotation2d(), getSwerveModulePositions());
        field = new Field2d();
    }

    public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {
        SwerveModuleState[] swerveModuleStates = driveKinematics.toSwerveModuleStates(fieldRelative
            ? ChassisSpeeds.fromFieldRelativeSpeeds(translation.getX(), translation.getY(), rotation, getYawRotation2d())
            : new ChassisSpeeds(translation.getX(), translation.getY(), rotation)
        );

        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.SwerveConstants.PhysicalMaxTranslationSpeed);
        for(NEOSwerveModule module : swerveModules) {
            module.setDesiredState(swerveModuleStates[module.moduleNumber], false);
        }
    }

    public void driveRobotRelative(ChassisSpeeds speeds) {
        SwerveModuleState[] swerveModuleStates = driveKinematics.toSwerveModuleStates(speeds);
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.SwerveConstants.PhysicalMaxTranslationSpeed);
        setSwerveModuleStates(swerveModuleStates);
    }

    public SwerveModuleState[] getSwerveModuleStates() {
        SwerveModuleState[] swerveModuleStates = new SwerveModuleState[4];
        for(NEOSwerveModule module : swerveModules) {
            swerveModuleStates[module.moduleNumber] = module.getSwerveModuleState();
        }

        return swerveModuleStates;
    }

    public SwerveModulePosition[] getSwerveModulePositions() {
        SwerveModulePosition[] swerveModulePositions = new SwerveModulePosition[4];
        for(NEOSwerveModule module : swerveModules) {
            swerveModulePositions[module.moduleNumber] = module.getSwerveModulePosition();
        }

        return swerveModulePositions;
    }

    public ChassisSpeeds getRobotRelativeSpeeds() {
        return driveKinematics.toChassisSpeeds(getSwerveModuleStates());
    }

    public void setSwerveModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, Constants.SwerveConstants.PhysicalMaxTranslationSpeed);
        for(NEOSwerveModule swerveModule : swerveModules) {
            swerveModule.setDesiredState(desiredStates[swerveModule.moduleNumber], false);
        }
    }

    public Pose2d getPose() {
        return swerveOdometry.getPoseMeters();
    }

    public double getHeading() {
        return Math.IEEEremainder(gyroscope.getAngle(), 360);
    }

    public Rotation2d getYawRotation2d() {
        return (Constants.SwerveConstants.swerveEncoderInverted) ? Rotation2d.fromDegrees(getHeading()) : Rotation2d.fromDegrees(360-getHeading());
    }

    public double getRawHeading() {
        return gyroscope.getYaw();
    }

    public void resetSwerveOdometry(Pose2d pose) {
        swerveOdometry.resetPosition(getYawRotation2d(), getSwerveModulePositions(), pose);
    }

    public void resetModulesToAbsolute() {
        for(NEOSwerveModule swerveModule : swerveModules) {
            swerveModule.resetToAbsolute();
        }
    }

    public void resetHeading() {
        gyroscope.zeroYaw();
    }

    public void stop() {
        for(NEOSwerveModule swerveModule : swerveModules) {
            swerveModule.stop();
        }
    }

    @Override
    public void periodic() {
        swerveOdometry.update(getYawRotation2d(), getSwerveModulePositions());
        field.setRobotPose(getPose());

        double[] measuredStates = {
                swerveModules[0].getSwerveModuleState().angle.getDegrees(),
                swerveModules[0].getSwerveModuleState().speedMetersPerSecond,
                swerveModules[1].getSwerveModuleState().angle.getDegrees(),
                swerveModules[1].getSwerveModuleState().speedMetersPerSecond,
                swerveModules[2].getSwerveModuleState().angle.getDegrees(),
                swerveModules[2].getSwerveModuleState().speedMetersPerSecond,
                swerveModules[3].getSwerveModuleState().angle.getDegrees(),
                swerveModules[3].getSwerveModuleState().speedMetersPerSecond,
        };

        double[] desiredStates = {
                swerveModules[0].getDesiredState().angle.getDegrees(),
                swerveModules[0].getDesiredState().speedMetersPerSecond,
                swerveModules[1].getDesiredState().angle.getDegrees(),
                swerveModules[1].getDesiredState().speedMetersPerSecond,
                swerveModules[2].getDesiredState().angle.getDegrees(),
                swerveModules[2].getDesiredState().speedMetersPerSecond,
                swerveModules[3].getDesiredState().angle.getDegrees(),
                swerveModules[3].getDesiredState().speedMetersPerSecond,

        };

        SmartDashboard.putNumberArray("MeasuredSwerveStates", measuredStates);
        SmartDashboard.putNumberArray("DesiredSwerveStates", desiredStates);
    }
}
