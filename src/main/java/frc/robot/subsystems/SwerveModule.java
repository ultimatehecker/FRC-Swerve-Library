package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.CANcoderConfigurator;
import com.ctre.phoenix6.configs.MagnetSensorConfigs;
import com.ctre.phoenix6.signals.AbsoluteSensorRangeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

import frc.robot.Robot;
import frc.robot.utilities.CANSparkMaxUtil;
import frc.robot.utilities.OnboardModuleState;
import frc.robot.utilities.constants.Constants;
import frc.robot.utilities.constants.SwerveModuleConstants;

public class SwerveModule {
    public int moduleNumber;
    public Rotation2d lastAngle;
    public Rotation2d angleOffset;

    private SwerveModuleState expectedState = new SwerveModuleState();

    private CANSparkMax driveMotor;
    private CANSparkMax steeringMotor;

    private RelativeEncoder driveEncoder;
    private RelativeEncoder steeringEncoder;
    private CANcoder swerveEncoder;
    private CANcoderConfigurator swerveEncoderConfigurator;

    private final SparkPIDController drivePIDController;
    private final SparkPIDController steeringPIDController;

    private final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(Constants.ModuleConstants.driveKS, Constants.ModuleConstants.driveKV, Constants.ModuleConstants.driveKA);

    public SwerveModule(int moduleNumber, SwerveModuleConstants moduleConstants) {
        this.moduleNumber = moduleNumber;
        this.angleOffset = moduleConstants.angleOffset;

        swerveEncoder = new CANcoder(moduleConstants.swerveEnocderID);
        configureSwerveEnocder();

        steeringMotor = new CANSparkMax(moduleConstants.steeringMotorID, MotorType.kBrushless);
        steeringEncoder = steeringMotor.getEncoder();
        steeringPIDController = steeringMotor.getPIDController();
        configureSteeringMotor();

        driveMotor = new CANSparkMax(moduleConstants.driveMotorID, MotorType.kBrushless);
        //driveMotor.setInverted(Constants);
        driveEncoder = driveMotor.getEncoder();
        drivePIDController = driveMotor.getPIDController();
        configureDriveMotor();

        //lastAngle =
    }

    private void configureSwerveEnocder() {
        swerveEncoderConfigurator = swerveEncoder.getConfigurator();
        MagnetSensorConfigs magnetSensorConfiguration = new MagnetSensorConfigs();

        magnetSensorConfiguration.AbsoluteSensorRange = AbsoluteSensorRangeValue.Unsigned_0To1;
        magnetSensorConfiguration.SensorDirection = SensorDirectionValue.CounterClockwise_Positive;
        magnetSensorConfiguration.MagnetOffset = angleOffset.getRotations();
        swerveEncoderConfigurator.apply(new CANcoderConfiguration().withMagnetSensor(magnetSensorConfiguration));
    }

    private void configureSteeringMotor() {
        steeringMotor.restoreFactoryDefaults();
        CANSparkMaxUtil.setSparkMaxBusUsage(steeringMotor, CANSparkMaxUtil.Usage.kPositionOnly);
        steeringMotor.setSmartCurrentLimit(Constants.ModuleConstants.steeringCurrentLimit);
        steeringMotor.setSecondaryCurrentLimit(Constants.ModuleConstants.maximumCurrentLimit);
        steeringMotor.setInverted(Constants.SwerveConstants.steeringInverted);
        steeringMotor.setIdleMode(Constants.SwerveConstants.activeNeutralMode);
        steeringEncoder.setPositionConversionFactor(Constants.SwerveConstants.SteeringPositionConversionFactor);
        steeringPIDController.setFeedbackDevice(steeringEncoder);
        steeringPIDController.setP(Constants.ModuleConstants.steeringkP);
        steeringPIDController.setI(Constants.ModuleConstants.steeringkI);
        steeringPIDController.setD(Constants.ModuleConstants.steeringkD);
        steeringMotor.enableVoltageCompensation(Constants.ModuleConstants.voltageCompensation);
        steeringMotor.burnFlash();
        resetToAbsolute();
    }

    private void configureDriveMotor() {
        driveMotor.restoreFactoryDefaults();
        CANSparkMaxUtil.setSparkMaxBusUsage(driveMotor, CANSparkMaxUtil.Usage.kAll);
        driveMotor.setSmartCurrentLimit(Constants.ModuleConstants.driveCurrentLimit);
        driveMotor.setSecondaryCurrentLimit(Constants.ModuleConstants.maximumCurrentLimit);
        driveMotor.setInverted(Constants.SwerveConstants.steeringInverted);
        driveMotor.setIdleMode(Constants.SwerveConstants.activeNeutralMode);
        driveEncoder.setPositionConversionFactor(Constants.SwerveConstants.DrivePositionConversionFactor);
        driveEncoder.setVelocityConversionFactor(Constants.SwerveConstants.DriveVelocityConversionFactor);
        drivePIDController.setFeedbackDevice(driveEncoder);
        drivePIDController.setP(Constants.ModuleConstants.drivekP);
        drivePIDController.setI(Constants.ModuleConstants.drivekI);
        drivePIDController.setD(Constants.ModuleConstants.drivekD);
        driveMotor.enableVoltageCompensation(Constants.ModuleConstants.voltageCompensation);
        driveMotor.burnFlash();
        driveEncoder.setPosition(0.0);
    }

    public Rotation2d getSwerveEncoder() {
        return Rotation2d.fromRotations(swerveEncoder.getAbsolutePosition().getValueAsDouble());
    }

    public Rotation2d getSteeringAngle() {
        return Rotation2d.fromDegrees(steeringEncoder.getPosition());
    }

    public double getMotorVelocity() {
        return driveEncoder.getVelocity();
    }

    public double getDrivePosition() {
        return driveEncoder.getPosition();
    }

    public SwerveModuleState getDesiredState() {
        return expectedState;
    }

    public SwerveModuleState getSwerveModuleState() {
        return new SwerveModuleState(getMotorVelocity(), getSteeringAngle());
    }

    public SwerveModulePosition getSwerveModulePosition() {
        return new SwerveModulePosition(getDrivePosition(), getSteeringAngle());
    }

    public void resetToAbsolute() {
        double absolutePosition = getSwerveEncoder().getDegrees();
        steeringEncoder.setPosition(absolutePosition);
    }

    public void onDisabled() {
        driveMotor.setIdleMode(Constants.SwerveConstants.disabledNeutralMode);
        steeringMotor.setIdleMode(Constants.SwerveConstants.disabledNeutralMode);
    }

    public void onEnabled() {
        driveMotor.setIdleMode(Constants.SwerveConstants.activeNeutralMode);
        steeringMotor.setIdleMode(Constants.SwerveConstants.activeNeutralMode);
    }

    private void setSpeed(SwerveModuleState desiredState, boolean isOpenLoop) {
        if(isOpenLoop) {
            double percentOutput = desiredState.speedMetersPerSecond / Constants.SwerveConstants.PhysicalMaxTranslationSpeed;
            driveMotor.set(percentOutput);
        } else {
            drivePIDController.setReference(desiredState.speedMetersPerSecond, CANSparkMax.ControlType.kVelocity, 0, feedforward.calculate(desiredState.speedMetersPerSecond));
        }
    }

    private void setAngle(SwerveModuleState desiredState) {
        Rotation2d angle = (Math.abs(desiredState.speedMetersPerSecond) <= (Constants.SwerveConstants.PhysicalMaxTranslationSpeed * 0.01)) ? lastAngle : desiredState.angle;
        steeringPIDController.setReference(angle.getDegrees(), CANSparkMax.ControlType.kPosition);

        if(Robot.isSimulation()) {
            steeringEncoder.setPosition(angle.getDegrees());
        }

        lastAngle = angle;
    }

    public void setDesiredState(SwerveModuleState desiredState, boolean isOpenLoop) {
        if(Math.abs(desiredState.speedMetersPerSecond) < 0.006) {
            driveMotor.set(0);
            steeringMotor.set(0);

            if(desiredState.angle == lastAngle) {
                resetToAbsolute();
            }

            return;
        }

        desiredState = OnboardModuleState.optimize(desiredState, getSwerveModuleState().angle);
        this.expectedState = expectedState;

        setAngle(desiredState);
        setSpeed(desiredState, isOpenLoop);
    }

    public void voltageDrive(double voltage) {
        driveMotor.setVoltage(voltage);
    }

    public void stopDriveMotor() {
        driveMotor.set(0.0);
    }

    public void stopSteeringMotor() {
        steeringMotor.set(0.0);
    }

    public void stop() {
        stopDriveMotor();
        stopSteeringMotor();
    }
}