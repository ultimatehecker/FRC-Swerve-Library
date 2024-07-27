package frc.robot.utilities;

/**
 * COTS Swerve Module Gearing.
 * A configuration represents a unique mechanical configuration of a module. For example, the Swerve Drive Specialties
 * Mk3 swerve module has two configurations, standard and fast, and therefore should have two configurations
 * ModuleConfiguration.Fast and ModuleConfiguration.Standard respectively.
 */

public enum SwerveModuleGearing {
    MK3_STANDARD(GearRatio.MK3_STANDARD, false, GearRatio.MK3_STEERING, false),
    MK3_FAST(GearRatio.MK3_FAST, false, GearRatio.MK3_STEERING, false),
    MK4_L1(GearRatio.MK4_L1, false, GearRatio.MK4_STEERING, false),
    MK4_L2(GearRatio.MK4_L2, false, GearRatio.MK4_STEERING, false),
    MK4_L3(GearRatio.MK4_L3, false, GearRatio.MK4_STEERING, false),
    MK4_L4(GearRatio.MK4_L4, false, GearRatio.MK4_STEERING, false),
    MK4I_L1(GearRatio.MK4I_L1, false, GearRatio.MK4I_STEERING, true),
    MK4I_L2(GearRatio.MK4I_L2, false, GearRatio.MK4I_STEERING, true),
    MK4I_L3(GearRatio.MK4I_L3, false, GearRatio.MK4I_STEERING, true);

    private double driveReduction;
    private boolean driveInverted;
    private double steerReduction;
    private boolean steerInverted;

    /**
     * Creates a new COTS Module Gearing Configuration.
     * @param driveReduction The overall drive reduction of the module. Multiplying motor rotations by this value should result in wheel rotations.
     * @param driveInverted  Whether the drive motor should be inverted. If there is an odd number of gea reductions this is typically true.
     * @param steerReduction The overall steer reduction of the module. Multiplying motor rotations by this value should result in rotations of the steering pulley.
     * @param steerInverted  Whether the steer motor should be inverted. If there is an odd number of gear reductions this is typically true.
     */

    SwerveModuleGearing(double driveReduction, boolean driveInverted, double steerReduction, boolean steerInverted) {
        this.driveReduction = driveReduction;
        this.driveInverted = driveInverted;
        this.steerReduction = steerReduction;
        this.steerInverted = steerInverted;
    }

    /**
     * Gets the overall reduction of the drive system. If this value is multiplied by drive motor rotations the result would be drive wheel rotations.
     */

    public double getDriveReduction() {
        return driveReduction;
    }

    /**
     * Gets if the drive motor should be inverted.
     */

    public boolean isDriveInverted() {
        return driveInverted;
    }

    /**
     * Gets the overall reduction of the steer system. If this value is multiplied by steering motor rotations the result would be steering pulley rotations.
     */

    public double getSteerReduction() {
        return steerReduction;
    }

    /**
     * Gets if the steering motor should be inverted.
     */

    public boolean isSteerInverted() {
        return steerInverted;
    }

    /**
     * Swerve Module Gearing.
     * A collection of every single gear ratio for every COTS swerve module.
     */

    private class GearRatio {
        public static final double MK3_STANDARD = (50.0 / 14.0) * (16.0 / 28.0) * (60.0 / 15.0);
        public static final double MK3_FAST = (48.0 / 16.0) * (16.0 / 28.0) * (60.0 / 15.0);

        public static final double MK4_L1 = (50.0 / 14.0) * (19.0 / 25.0) * (45.0 / 15.0);
        public static final double MK4_L2 = (50.0 / 14.0) * (17.0 / 27.0) * (45.0 / 15.0);
        public static final double MK4_L3 = (50.0 / 14.0) * (16.0 / 28.0) * (45.0 / 15.0);
        public static final double MK4_L4 = (48.0 / 16.0) * (16.0 / 28.0) * (45.0 / 15.0);

        public static final double MK4I_L1 = (50.0 / 14.0) * (19.0 / 25.0) * (45.0 / 15.0);
        public static final double MK4I_L2 = (50.0 / 14.0) * (17.0 / 27.0) * (45.0 / 15.0);
        public static final double MK4I_L3 = (50.0 / 14.0) * (16.0 / 28.0) * (45.0 / 15.0);

        public static final double MK3_STEERING = (32.0 / 15.0) * (60.0 / 10.0);
        public static final double MK4_STEERING = (32.0 / 15.0) * (60.0 / 10.0);
        public static final double MK4I_STEERING = (50.0 / 14.0) * (60.0 / 10.0);
    }
}
