package frc.robot.utilities;

import com.ctre.phoenix6.signals.SensorDirectionValue;

import edu.wpi.first.math.util.Units;

public class ModuleConfiguration {
    public final double wheelDiameter;
    public final double wheelCircumference;
    public final double angleGearRatio;
    public final double driveGearRatio;
    public final boolean driveMotorInverted;
    public final boolean angleMotorInverted;
    public final boolean cancoderInvert;

    public ModuleConfiguration(double wheelDiameter, double angleGearRatio, double driveGearRatio, boolean driveMotorInverted, boolean angleMotorInverted, boolean cancoderInvert){
        this.wheelDiameter = wheelDiameter;
        this.wheelCircumference = wheelDiameter * Math.PI;
        this.angleGearRatio = angleGearRatio;
        this.driveGearRatio = driveGearRatio;
        this.driveMotorInverted = driveMotorInverted;
        this.angleMotorInverted = angleMotorInverted;
        this.cancoderInvert = cancoderInvert;
    }

    public static final class SwerveDriveSpecialities {
        public static final class MK3 {
            public static final ModuleConfiguration KrakenX60(double driveGearRatio) {
                double wheelDiameter = Units.inchesToMeters(4.0);
                double angleGearRatio = SwerveModuleGearing.MK3_STANDARD.getSteerReduction();

                boolean driveMotorInvert = SwerveModuleGearing.MK3_STANDARD.isDriveInverted();
                boolean angleMotorInvert = SwerveModuleGearing.MK3_STANDARD.isSteerInverted();
                boolean cancoderInvert = false;

                return new ModuleConfiguration(wheelDiameter, angleGearRatio, driveGearRatio, driveMotorInvert, angleMotorInvert, cancoderInvert);
            }

            public static final ModuleConfiguration Falcon500(double driveGearRatio) {
                double wheelDiameter = Units.inchesToMeters(4.0);
                double angleGearRatio = SwerveModuleGearing.MK3_STANDARD.getSteerReduction();

                boolean driveMotorInvert = SwerveModuleGearing.MK3_STANDARD.isDriveInverted();
                boolean angleMotorInvert = SwerveModuleGearing.MK3_STANDARD.isSteerInverted();
                boolean cancoderInvert = false;

                return new ModuleConfiguration(wheelDiameter, angleGearRatio, driveGearRatio, driveMotorInvert, angleMotorInvert, cancoderInvert);
            }

            public static final ModuleConfiguration NEO(double driveGearRatio) {
                double wheelDiameter = Units.inchesToMeters(4.0);
                double angleGearRatio = SwerveModuleGearing.MK3_STANDARD.getSteerReduction();

                boolean driveMotorInvert = SwerveModuleGearing.MK3_STANDARD.isDriveInverted();
                boolean angleMotorInvert = SwerveModuleGearing.MK3_STANDARD.isSteerInverted();
                boolean cancoderInvert = false;

                return new ModuleConfiguration(wheelDiameter, angleGearRatio, driveGearRatio, driveMotorInvert, angleMotorInvert, cancoderInvert);
            }

            public static final class RatioOptions {
                public static final double MK3_STANDARD = SwerveModuleGearing.MK3_STANDARD.getDriveReduction();
                public static final double MK3_FAST = SwerveModuleGearing.MK3_FAST.getDriveReduction();
            }
        }

        public static final class MK4 {
            public static final ModuleConfiguration KrakenX60(double driveGearRatio) {
                double wheelDiameter = Units.inchesToMeters(4.0);
                double angleGearRatio = SwerveModuleGearing.MK4_L1.getSteerReduction();

                boolean driveMotorInvert = SwerveModuleGearing.MK4_L1.isDriveInverted();
                boolean angleMotorInvert = SwerveModuleGearing.MK4_L1.isSteerInverted();
                boolean cancoderInvert = false;

                return new ModuleConfiguration(wheelDiameter, angleGearRatio, driveGearRatio, driveMotorInvert, angleMotorInvert, cancoderInvert);
            }

            public static final ModuleConfiguration Falcon500(double driveGearRatio) {
                double wheelDiameter = Units.inchesToMeters(4.0);
                double angleGearRatio = SwerveModuleGearing.MK4_L1.getSteerReduction();

                boolean driveMotorInvert = SwerveModuleGearing.MK4_L1.isDriveInverted();
                boolean angleMotorInvert = SwerveModuleGearing.MK4_L1.isSteerInverted();
                boolean cancoderInvert = false;

                return new ModuleConfiguration(wheelDiameter, angleGearRatio, driveGearRatio, driveMotorInvert, angleMotorInvert, cancoderInvert);
            }

            public static final ModuleConfiguration NEO(double driveGearRatio) {
                double wheelDiameter = Units.inchesToMeters(4.0);
                double angleGearRatio = SwerveModuleGearing.MK4_L1.getSteerReduction();

                boolean driveMotorInvert = SwerveModuleGearing.MK4_L1.isDriveInverted();
                boolean angleMotorInvert = SwerveModuleGearing.MK4_L1.isSteerInverted();
                boolean cancoderInvert = false;

                return new ModuleConfiguration(wheelDiameter, angleGearRatio, driveGearRatio, driveMotorInvert, angleMotorInvert, cancoderInvert);
            }

            public static final class RatioOptions {
                public static final double L1 = SwerveModuleGearing.MK4_L1.getDriveReduction();
                public static final double L2 = SwerveModuleGearing.MK4_L2.getDriveReduction();
                public static final double L3 = SwerveModuleGearing.MK4_L3.getDriveReduction();
                public static final double L4 = SwerveModuleGearing.MK4_L4.getDriveReduction();
            }
        }

        public static final class MK4I {
            public static final ModuleConfiguration KrakenX60(double driveGearRatio) {
                double wheelDiameter = Units.inchesToMeters(4.0);
                double angleGearRatio = SwerveModuleGearing.MK4I_L1.getSteerReduction();

                boolean driveMotorInvert = SwerveModuleGearing.MK4I_L1.isDriveInverted();
                boolean angleMotorInvert = SwerveModuleGearing.MK4I_L1.isSteerInverted();
                boolean cancoderInvert = false;

                return new ModuleConfiguration(wheelDiameter, angleGearRatio, driveGearRatio, driveMotorInvert, angleMotorInvert, cancoderInvert);
            }

            public static final ModuleConfiguration Falcon500(double driveGearRatio) {
                double wheelDiameter = Units.inchesToMeters(4.0);
                double angleGearRatio = SwerveModuleGearing.MK4I_L1.getSteerReduction();

                boolean driveMotorInvert = SwerveModuleGearing.MK4I_L1.isDriveInverted();
                boolean angleMotorInvert = SwerveModuleGearing.MK4I_L1.isSteerInverted();
                boolean cancoderInvert = false;

                return new ModuleConfiguration(wheelDiameter, angleGearRatio, driveGearRatio, driveMotorInvert, angleMotorInvert, cancoderInvert);
            }

            public static final ModuleConfiguration NEO(double driveGearRatio) {
                double wheelDiameter = Units.inchesToMeters(4.0);
                double angleGearRatio = SwerveModuleGearing.MK4I_L1.getSteerReduction();

                boolean driveMotorInvert = SwerveModuleGearing.MK4I_L1.isDriveInverted();
                boolean angleMotorInvert = SwerveModuleGearing.MK4I_L1.isSteerInverted();
                boolean cancoderInvert = false;

                return new ModuleConfiguration(wheelDiameter, angleGearRatio, driveGearRatio, driveMotorInvert, angleMotorInvert, cancoderInvert);
            }

            public static final class RatioOptions {
                public static final double L1 = SwerveModuleGearing.MK4I_L1.getDriveReduction();
                public static final double L2 = SwerveModuleGearing.MK4I_L2.getDriveReduction();
                public static final double L3 = SwerveModuleGearing.MK4I_L3.getDriveReduction();
            }
        }
    }
}