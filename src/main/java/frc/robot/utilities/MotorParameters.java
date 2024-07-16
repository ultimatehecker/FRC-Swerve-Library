package frc.robot.utilities;

public enum MotorParameters {
    Falcon500(6380.0, 4.69, 2048),
    KrakenX60(6000.0, 7.09, 2048),
    NeoV1_1(5676.0, 3.75, 42),
    NeoVortexMax(6784.0, 3.6, 42),
    NeoVortexFlex(6784.0, 3.6, 7168),
    Neo550(11000.0, 0.97, 42);

    private double freeSpeedRPM;
    private double stallTorque;
    private int pulsesPerRevolution;

    /**
    * Constructs an instance of this enum.
    *
    * @param freeSpeedRPM The free speed RPM.
    * @param stallTorque The stall torque in Nm.
    * @param pulsesPerRevolution The number of pulses per revolution of the motor reported by the
    *     integrated encoder.
    */

    MotorParameters(double freeSpeedRPM, double stallTorque, int pulsesPerRevolution) {
        this.freeSpeedRPM = freeSpeedRPM;
        this.stallTorque = stallTorque;
        this.pulsesPerRevolution = pulsesPerRevolution;
    }

    /**
    * Returns the free speed RPM of the motor.
    *
    * @return The free speed RPM.
    */

    public double getFreeSpeedRPM() {
        return this.freeSpeedRPM;
    }

    /**
    * Returns the stall torque of the motor in Nm.
    *
    * @return The stall torque in Nm.
    */

    public double getStallTorque() {
        return this.stallTorque;
    }

    /**
    * Returns the number of pulses per revolution of the integrated encoder.
    *
    * @return The number of pulses per revolution of the integrated encoder.
    */
    
    public int getPulsesPerRevolution() {
        return this.pulsesPerRevolution;
    }
}