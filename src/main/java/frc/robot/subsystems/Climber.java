package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.ActuatorMap;
import frc.robot.utils.Constants;

public class Climber extends SubsystemBase {
    TalonFX climber = new TalonFX(ActuatorMap.climber);
    DoubleSolenoid climberAdjuster = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, ActuatorMap.climberPistonOne, ActuatorMap.climberPistonTwo);
    Solenoid ratchet = new Solenoid(PneumaticsModuleType.CTREPCM, ActuatorMap.winch);
    
    public Climber() {
        climber.configOpenloopRamp(1);
    }

    public void extendArmTrigger(double power) {
        climber.set(ControlMode.PercentOutput, -power);
    }

    public void reverseArmTrigger(double power) {
        climber.set(ControlMode.PercentOutput, power);
    }

    public void init() {
        climberAdjuster.set(Value.kForward);
    }

    public void extendArm() {
        climber.set(ControlMode.PercentOutput, -0.2);
    }

    public void stopArm() {
        climber.set(ControlMode.PercentOutput, 0);
    }

    public void retractArm() {
        climber.set(ControlMode.PercentOutput, 0.2);
    }
    
    public double getSensorPos() {
        return climber.getSelectedSensorPosition();
    }

    public void movePistonUp() {
        //Apparently we have to 'wiggle' the pistons somehow
        climberAdjuster.set(Value.kForward);
        }
    public void movePistonDown() {
        climberAdjuster.set(Value.kReverse);
    }
    public double getCurrent() {
        return climber.getSupplyCurrent();
    }
    public void movePistonForward() {
        climberAdjuster.set(Value.kForward);
    }

    public boolean isTop() {
        return climber.getSelectedSensorPosition() >= Constants.climberLimit;
    }

    public boolean isBottom() {
        return getCurrent() > Constants.climberCurrent;
    }

    public void lock() {
        ratchet.set(true);
    }
    public void unlock() {
        ratchet.set(false);
    }
    public void toggLock() {
        ratchet.toggle();
    }
    public void setZero() {
        climber.setSelectedSensorPosition(0);
    }
}
