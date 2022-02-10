package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.ActuatorMap;
import frc.robot.utils.Constants;

public class DriveTrain extends SubsystemBase {
    TalonSRX rMaster = new TalonSRX(ActuatorMap.rMaster);
    TalonSRX lMaster = new TalonSRX(ActuatorMap.lMaster);

    VictorSPX rSlave = new VictorSPX(ActuatorMap.rSlave);
    VictorSPX lSlave = new VictorSPX(ActuatorMap.lSlave); 

    Solenoid dog = new Solenoid(PneumaticsModuleType.CTREPCM, ActuatorMap.dog);

    boolean driveToMyBallsisDone;
    boolean turnIsDone;

    private double endPos;

    //Drive for the right gearbox
    public void rDrive(double power) {
        //set slaves to follow
        rSlave.follow(rMaster);


        //set right motors to be inverted
        rMaster.setInverted(true);

        rMaster.set(ControlMode.PercentOutput, power);
    }

    //Drive for the left gearbox
    public void lDrive(double power) {
        //set slaves to follow
        lSlave.follow(lMaster);


        //set slaves to be inverted
        lMaster.setInverted(true);

        lMaster.set(ControlMode.PercentOutput, power);
    }

    public void driveToMyBalls(double inches, double speed) {
        endPos = Constants.TalonSRXCPR / Constants.wheelCirc * inches;
        lMaster.setSelectedSensorPosition(0);
        if(lMaster.getSelectedSensorPosition() < endPos) {
            rDrive(speed);
            lDrive(speed);
        }else {
            driveToMyBallsisDone = true;
        }
    }

    public void turn(double angle, double currentAngle) {
        if(currentAngle < angle) {
            rDrive(0.5);
            lDrive(-0.5);
        }
        else
            turnIsDone = true;
    }
    public boolean driveToMyBallsisDone() {
        if(driveToMyBallsisDone) {
            driveToMyBallsisDone = false;
            return true;
        }
        return false;
    } 

    public boolean turnisDone() {
        if(turnisDone) {
            turnisDone = false;
            return true;
        }
        return false;
    } 

    public void shiftThatDog() {
        dog.toggle();
    }
    
}
