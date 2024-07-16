package frc.robot.utilities;

public class Conversions {
    /**
     * @param counts Falcon Counts
     * @param gearRatio Gear Ratio between Falcon and Mechanism
     * @return Degrees of Rotation of Mechanism
     */

    public static double falconToDegrees(double counts, double gearRatio) {
        return counts * (360.0 / (gearRatio * MotorParameters.Falcon500.getPulsesPerRevolution()));
    }

    /**
     * @param counts Neo Counts
     * @param gearRatio Gear Ratio between Neo and Mechanism
     * @return Degrees of Rotation of Mechanism
     */

    public static double neoToDegrees(double counts, double gearRatio) {
        return counts * (360.0 / (gearRatio * MotorParameters.NeoV1_1.getPulsesPerRevolution()));
    }


    /**
     * @param degrees Degrees of rotation of Mechanism
     * @param gearRatio Gear Ratio between Falcon and Mechanism
     * @return Falcon Counts
     */

    public static double degreesToFalcon(double degrees, double gearRatio) {
        double ticks =  degrees / (360.0 / (gearRatio * MotorParameters.Falcon500.getPulsesPerRevolution()));
        return ticks;
    }

    /**
     * @param degrees Degrees of rotation of Mechanism
     * @param gearRatio Gear Ratio between Neo and Mechanism
     * @return Neo Counts
     */

     public static double neoToFalcon(double degrees, double gearRatio) {
        double ticks =  degrees / (360.0 / (gearRatio * MotorParameters.NeoV1_1.getPulsesPerRevolution()));
        return ticks;
    }

    /**
     * @param velocityCounts Falcon Velocity Counts
     * @param gearRatio Gear Ratio between Falcon and Mechanism (set to 1 for Falcon RPM)
     * @return RPM of Mechanism
     */

    public static double falconToRPM(double velocityCounts, double gearRatio) {
        double motorRPM = velocityCounts * (600.0 / MotorParameters.Falcon500.getPulsesPerRevolution());        
        double mechRPM = motorRPM / gearRatio;
        return mechRPM;
    }

    /**
     * @param velocityCounts Neo Velocity Counts
     * @param gearRatio Gear Ratio between Neo and Mechanism (set to 1 for Neo RPM)
     * @return RPM of Mechanism
     */

     public static double neoToRPM(double velocityCounts, double gearRatio) {
        double motorRPM = velocityCounts * (600.0 / MotorParameters.NeoV1_1.getPulsesPerRevolution());        
        double mechRPM = motorRPM / gearRatio;
        return mechRPM;
    }

    /**
     * @param RPM RPM of mechanism
     * @param gearRatio Gear Ratio between Falcon and Mechanism (set to 1 for Falcon RPM)
     * @return RPM of Mechanism
     */

    public static double RPMToFalcon(double RPM, double gearRatio) {
        double motorRPM = RPM * gearRatio;
        double sensorCounts = motorRPM * (MotorParameters.Falcon500.getPulsesPerRevolution() / 600.0);
        return sensorCounts;
    }

    /**
     * @param RPM RPM of mechanism
     * @param gearRatio Gear Ratio between Neo and Mechanism (set to 1 for Neo RPM)
     * @return RPM of Mechanism
     */

     public static double RPMToNeo(double RPM, double gearRatio) {
        double motorRPM = RPM * gearRatio;
        double sensorCounts = motorRPM * (MotorParameters.NeoV1_1.getPulsesPerRevolution() / 600.0);
        return sensorCounts;
    }

    /**
     * @param velocitycounts Falcon Velocity Counts
     * @param circumference Circumference of Wheel
     * @param gearRatio Gear Ratio between Falcon and Mechanism (set to 1 for Falcon RPM)
     * @return Falcon Velocity Counts
     */

    public static double falconToMPS(double velocitycounts, double circumference, double gearRatio){
        double wheelRPM = falconToRPM(velocitycounts, gearRatio);
        double wheelMPS = (wheelRPM * circumference) / 60;
        return wheelMPS;
    }

    /**
     * @param velocitycounts Neo Velocity Counts
     * @param circumference Circumference of Wheel
     * @param gearRatio Gear Ratio between Neo and Mechanism (set to 1 for Neo RPM)
     * @return Neo Velocity Counts
     */

     public static double neoToMPS(double velocitycounts, double circumference, double gearRatio){
        double wheelRPM = neoToRPM(velocitycounts, gearRatio);
        double wheelMPS = (wheelRPM * circumference) / 60;
        return wheelMPS;
    }

    /**
     * @param velocity Velocity MPS
     * @param circumference Circumference of Wheel
     * @param gearRatio Gear Ratio between Falcon and Mechanism (set to 1 for Falcon RPM)
     * @return Falcon Velocity Counts
     */

    public static double MPSToFalcon(double velocity, double circumference, double gearRatio){
        double wheelRPM = ((velocity * 60) / circumference);
        double wheelVelocity = RPMToFalcon(wheelRPM, gearRatio);
        return wheelVelocity;
    }

    /**
     * @param velocity Velocity MPS
     * @param circumference Circumference of Wheel
     * @param gearRatio Gear Ratio between Neo and Mechanism (set to 1 for Neo RPM)
     * @return Neo Velocity Counts
     */

     public static double MPSToNeo(double velocity, double circumference, double gearRatio){
        double wheelRPM = ((velocity * 60) / circumference);
        double wheelVelocity = RPMToNeo(wheelRPM, gearRatio);
        return wheelVelocity;
    }

    /**
     * @param wheelRPS Wheel Velocity: (in Rotations per Second)
     * @param circumference Wheel Circumference: (in Meters)
     * @return Wheel Velocity: (in Meters per Second)
     */

    public static double RPSToMPS(double wheelRPS, double circumference) {
        double wheelMPS = wheelRPS * circumference;
        return wheelMPS;
    }

    /**
     * @param wheelMPS Wheel Velocity: (in Meters per Second)
     * @param circumference Wheel Circumference: (in Meters)
     * @return Wheel Velocity: (in Rotations per Second)
     */

    public static double MPSToRPS(double wheelMPS, double circumference) {
        double wheelRPS = wheelMPS / circumference;
        return wheelRPS;
    }

    /**
     * @param wheelRotations Wheel Position: (in Rotations)
     * @param circumference Wheel Circumference: (in Meters)
     * @return Wheel Distance: (in Meters)
     */

    public static double rotationsToMeters(double wheelRotations, double circumference) {
        double wheelMeters = wheelRotations * circumference;
        return wheelMeters;
    }

    /**
     * @param wheelMeters Wheel Distance: (in Meters)
     * @param circumference Wheel Circumference: (in Meters)
     * @return Wheel Position: (in Rotations)
     */

    public static double metersToRotations(double wheelMeters, double circumference) {
        double wheelRotations = wheelMeters / circumference;
        return wheelRotations;
    }
}