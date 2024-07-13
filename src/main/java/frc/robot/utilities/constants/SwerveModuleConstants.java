package frc.robot.utilities.constants;

import edu.wpi.first.math.geometry.Rotation2d;

public class SwerveModuleConstants {
    public final int driveMotorID;
    public final int steeringMotorID;
    public final int swerveEnocderID;
    public final Rotation2d angleOffset;

    /**
     * @param driveMotorID The CAN ID of the drive motor
     * @param steeringMotorID The CAN ID of the steering motor
     * @param swerveEnocderID The CAN ID of the swerve absolute encoder
     * @param angleOffset The angle offset of the absolute encoder
     */

    public SwerveModuleConstants(int driveMotorID, int steeringMotorID, int swerveEnocderID, Rotation2d angleOffset) {
        this.driveMotorID = driveMotorID;
        this.steeringMotorID = steeringMotorID;
        this.swerveEnocderID = swerveEnocderID;
        this.angleOffset = angleOffset;
    }
}
