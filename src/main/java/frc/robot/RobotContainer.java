package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.LLDistanceCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LimeLight;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveTrain m_driveTrainSubsystem = new DriveTrain();

  private final DriveCommand m_teleopCommand = new DriveCommand(m_driveTrainSubsystem);
  private SendableChooser<Command> chooser;
  public RobotContainer() {
    //chooser.addOption("LimeLight_Distance", new LLDistanceCommand(new LimeLight()));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    
    // An ExampleCommand will run in autonomous
    //return chooser.getSelected();
    return new LLDistanceCommand(new LimeLight());
  }
  public Command getTeleopCommand() {
    return m_teleopCommand;
  }
}
