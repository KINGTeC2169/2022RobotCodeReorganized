package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import frc.robot.utils.Controls;

public class DriveCommand extends CommandBase {
    
    private DriveTrain driveTrain;
    public DriveCommand(DriveTrain subsystem) {
        driveTrain = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        driveTrain.lDrive(Controls.getLeftY());
        driveTrain.rDrive(Controls.getRightY());
    }


}