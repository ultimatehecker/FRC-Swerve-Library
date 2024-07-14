package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.utilities.constants.Constants;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class SwerveController extends Command {
    private SwerveSubsystem swerveSubsystem;

    private DoubleSupplier translationSupplier;
    private DoubleSupplier strafeSupplier;
    private DoubleSupplier rotationSupplier;
    private BooleanSupplier robotCentricSupplier;

    private SlewRateLimiter translationLimiter = new SlewRateLimiter(2.9);
    private SlewRateLimiter strafeLimiter = new SlewRateLimiter(2.9);
    private SlewRateLimiter rotationLimiter = new SlewRateLimiter(2.9);

    public SwerveController(SwerveSubsystem swerveSubsystem, DoubleSupplier translationSupplier, DoubleSupplier strafeSupplier, DoubleSupplier rotationSupplier, BooleanSupplier robotCentricSupplier) {
        this.swerveSubsystem = swerveSubsystem;
        this.translationSupplier = translationSupplier;
        this.strafeSupplier = strafeSupplier;
        this.rotationSupplier = rotationSupplier;
        this.robotCentricSupplier = robotCentricSupplier;

        addRequirements(swerveSubsystem);
    }

    @Override
    public void execute() {
        double translationValue = translationLimiter.calculate(MathUtil.applyDeadband(translationSupplier.getAsDouble(), Constants.DriverConstants.kDeadband));
        double strafeValue = strafeLimiter.calculate(MathUtil.applyDeadband(strafeSupplier.getAsDouble(), Constants.DriverConstants.kDeadband));
        double rotationValue = rotationLimiter.calculate(MathUtil.applyDeadband(rotationSupplier.getAsDouble(), Constants.DriverConstants.kDeadband));

        swerveSubsystem.drive(new Translation2d(translationValue, strafeValue).times(Constants.SwerveConstants.PhysicalMaxTranslationSpeed), rotationValue * Constants.SwerveConstants.PhysicalMaxAngularVelocity, !robotCentricSupplier.getAsBoolean(), true);
    }
}
