package frc.robot.utilities;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;

import frc.robot.utilities.constants.Constants;

public class CTREConfiguration {
    public TalonFXConfiguration driveTalonFXConfiguration = new TalonFXConfiguration();
    public TalonFXConfiguration steeringTalonFXConfiguration = new TalonFXConfiguration();
    public CANcoderConfiguration swerveCANCoderConfiguration = new CANcoderConfiguration();

    public CTREConfiguration() {
        /** Swerve CANCoder Configuration */
        swerveCANCoderConfiguration.MagnetSensor.SensorDirection = Constants.SwerveConstants.swerveEncoderInverted ? SensorDirectionValue.CounterClockwise_Positive : SensorDirectionValue.Clockwise_Positive;

        /** Swerve Angle Motor Configurations ----------------------------------------------------------- */

        /* Motor Inverts and Neutral Mode */
        steeringTalonFXConfiguration.MotorOutput.Inverted = Constants.SwerveConstants.steeringInverted ? InvertedValue.CounterClockwise_Positive : InvertedValue.Clockwise_Positive;
        steeringTalonFXConfiguration.MotorOutput.NeutralMode = Constants.SwerveConstants.disabledNeutralMode ? NeutralModeValue.Coast : NeutralModeValue.Brake;

        /* Gear Ratio and Wrapping Config */
        steeringTalonFXConfiguration.Feedback.SensorToMechanismRatio = Constants.SwerveConstants.ModuleGearing.getSteerReduction();
        steeringTalonFXConfiguration.ClosedLoopGeneral.ContinuousWrap = true;
        
        /* Current Limiting */
        steeringTalonFXConfiguration.CurrentLimits.SupplyCurrentLimitEnable = Constants.ModuleConstants.steeringEnableCurrentLimit;
        steeringTalonFXConfiguration.CurrentLimits.SupplyCurrentLimit = Constants.ModuleConstants.steeringCurrentLimit;
        steeringTalonFXConfiguration.CurrentLimits.SupplyCurrentThreshold = Constants.ModuleConstants.steeringCurrentThreshold;
        steeringTalonFXConfiguration.CurrentLimits.SupplyTimeThreshold = Constants.ModuleConstants.steeringCurrentThresholdTime;

        /* PID Config */
        steeringTalonFXConfiguration.Slot0.kP = Constants.ModuleConstants.steeringkP;
        steeringTalonFXConfiguration.Slot0.kI = Constants.ModuleConstants.steeringkI;
        steeringTalonFXConfiguration.Slot0.kD = Constants.ModuleConstants.steeringkD;

        /** Swerve Drive Motor Configuration ------------------------------------------------------------- */

        /* Motor Inverts and Neutral Mode */
        driveTalonFXConfiguration.MotorOutput.Inverted = Constants.SwerveConstants.driveInverted ? InvertedValue.CounterClockwise_Positive : InvertedValue.Clockwise_Positive;
        driveTalonFXConfiguration.MotorOutput.NeutralMode = Constants.SwerveConstants.disabledNeutralMode ? NeutralModeValue.Coast : NeutralModeValue.Brake;

        /* Gear Ratio Config */
        driveTalonFXConfiguration.Feedback.SensorToMechanismRatio = Constants.SwerveConstants.ModuleGearing.getDriveReduction();

        /* Current Limiting */
        driveTalonFXConfiguration.CurrentLimits.SupplyCurrentLimitEnable = Constants.ModuleConstants.driveEnableCurrentLimit;
        driveTalonFXConfiguration.CurrentLimits.SupplyCurrentLimit = Constants.ModuleConstants.driveCurrentLimitCTRE;
        driveTalonFXConfiguration.CurrentLimits.SupplyCurrentThreshold = Constants.ModuleConstants.driveCurrentThreshold;
        driveTalonFXConfiguration.CurrentLimits.SupplyTimeThreshold = Constants.ModuleConstants.driveCurrentThresholdTime;

        /* PID Config */
        driveTalonFXConfiguration.Slot0.kP = Constants.ModuleConstants.drivekP;
        driveTalonFXConfiguration.Slot0.kI = Constants.ModuleConstants.drivekP;
        driveTalonFXConfiguration.Slot0.kD = Constants.ModuleConstants.drivekP;

        /* Open and Closed Loop Ramping */
        driveTalonFXConfiguration.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = Constants.ModuleConstants.openLoopRamp;
        driveTalonFXConfiguration.OpenLoopRamps.VoltageOpenLoopRampPeriod = Constants.ModuleConstants.openLoopRamp;

        driveTalonFXConfiguration.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = Constants.ModuleConstants.closedLoopRamp;
        driveTalonFXConfiguration.ClosedLoopRamps.VoltageClosedLoopRampPeriod = Constants.ModuleConstants.closedLoopRamp;
    }

}
