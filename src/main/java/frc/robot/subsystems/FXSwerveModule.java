package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

import frc.robot.Robot;
import frc.robot.utilities.Conversions;
import frc.robot.utilities.constants.Constants;
import frc.robot.utilities.constants.SwerveModuleConstants;

public class FXSwerveModule {
    public int moduleNumber;
    private Rotation2d angleOffset;

    private TalonFX driveMotor;
    private TalonFX steeringMotor;
    private CANcoder swerveEncoder;

    private SwerveModuleState expectedState = new SwerveModuleState();

    private final SimpleMotorFeedforward driveFeedForward = new SimpleMotorFeedforward(Constants.ModuleConstants.driveKS, Constants.ModuleConstants.driveKV, Constants.ModuleConstants.driveKA);
    private final DutyCycleOut driveDutyCycle = new DutyCycleOut(0);
    private final VelocityVoltage driveVelocity = new VelocityVoltage(0);

    private final PositionVoltage steeringPosition = new PositionVoltage(0);

    public FXSwerveModule(int moduleNumber, SwerveModuleConstants moduleConstants) {
        this.moduleNumber = moduleNumber;
        this.angleOffset = moduleConstants.angleOffset;

        swerveEncoder = new CANcoder(moduleConstants.swerveEnocderID);
        swerveEncoder.getConfigurator().apply(Robot.CTREConfiguration.swerveCANCoderConfiguration);

        driveMotor = new TalonFX(moduleConstants.driveMotorID);
        driveMotor.getConfigurator().apply(Robot.CTREConfiguration.driveTalonFXConfiguration);
        driveMotor.getConfigurator().setPosition(0.0);

        steeringMotor = new TalonFX(moduleConstants.steeringMotorID);
        steeringMotor.getConfigurator().apply(Robot.CTREConfiguration.steeringTalonFXConfiguration);
        resetToAbsolute();
    }

    public SwerveModuleState getDesiredState() {
        return expectedState;
    }

    public Rotation2d getSwerveEncoder() {
        return Rotation2d.fromRotations(swerveEncoder.getAbsolutePosition().getValue());
    }

    public SwerveModuleState getSwerveModuleState() {
        return new SwerveModuleState(
            Conversions.RPSToMPS(driveMotor.getVelocity().getValueAsDouble(), Constants.SwerveConstants.SwerveModule.wheelCircumference),
            Rotation2d.fromDegrees(steeringMotor.getPosition().getValueAsDouble())
        );
    }

    public SwerveModulePosition getSwerveModulePosition() {
        return new SwerveModulePosition(
            Conversions.RPSToMPS(driveMotor.getPosition().getValueAsDouble(), Constants.SwerveConstants.SwerveModule.wheelCircumference),
            Rotation2d.fromDegrees(steeringMotor.getPosition().getValueAsDouble())
        );
    }

    public void resetToAbsolute() {
        double absolutePosition = getSwerveEncoder().getRotations() - angleOffset.getRotations();
        steeringMotor.setPosition(absolutePosition);
    }

    public void onDisabled() {
        driveMotor.setNeutralMode(Constants.SwerveConstants.disabledNeutralMode ? NeutralModeValue.Coast : NeutralModeValue.Brake);
        steeringMotor.setNeutralMode(Constants.SwerveConstants.disabledNeutralMode ? NeutralModeValue.Coast : NeutralModeValue.Brake);
    }

    public void onEnabled() {
        driveMotor.setNeutralMode(Constants.SwerveConstants.activeNeutralMode ? NeutralModeValue.Brake : NeutralModeValue.Coast);
        steeringMotor.setNeutralMode(Constants.SwerveConstants.activeNeutralMode ? NeutralModeValue.Brake : NeutralModeValue.Coast);
    }

    public void setDesiredState(SwerveModuleState desiredState, boolean isOpenLoop) {
        desiredState = SwerveModuleState.optimize(desiredState, getSwerveModuleState().angle); 
        steeringMotor.setControl(steeringPosition.withPosition(desiredState.angle.getRotations()));
        this.expectedState = expectedState;
        setSpeed(desiredState, isOpenLoop);
    }

    private void setSpeed(SwerveModuleState desiredState, boolean isOpenLoop) {
        if(isOpenLoop){
            driveDutyCycle.Output = desiredState.speedMetersPerSecond / Constants.SwerveConstants.PhysicalMaxTranslationSpeed;
            driveMotor.setControl(driveDutyCycle);
        }
        else {
            driveVelocity.Velocity = Conversions.MPSToRPS(desiredState.speedMetersPerSecond, Constants.SwerveConstants.SwerveModule.wheelCircumference);
            driveVelocity.FeedForward = driveFeedForward.calculate(desiredState.speedMetersPerSecond);
            driveMotor.setControl(driveVelocity);
        }
    }
}
