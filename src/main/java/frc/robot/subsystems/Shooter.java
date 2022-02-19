package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.ActuatorMap;
import frc.robot.utils.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.SensorVelocityMeasPeriod;

public class Shooter extends SubsystemBase {
    TalonFX shooter = new TalonFX(ActuatorMap.shooter);
    boolean hitRPM;
    double currentPower;
    int P = 5;
    int I = 0;
    int D = 0;
    int integral, previous_error, setpoint = 0;
    

    public Shooter() {
        //TODO: how fast for ramp up
        shooter.configOpenloopRamp(2.5);
        shooter.configVelocityMeasurementPeriod(SensorVelocityMeasPeriod.Period_100Ms);
    }

    public void shoot(double power) {
        CompressorTank.disable();
        shooter.set(ControlMode.PercentOutput, power);
    }

    public double getRPM() {
        return (600 * shooter.getSelectedSensorVelocity() / Constants.TalonFXCPR) * (24.0/18.0);
    }

    public void setRPM(double rpm) {
        boolean isASnake = false;
        if(rpm < 0) {
            rpm *= -1;
            isASnake = true;
        }
        if(Math.abs(getRPM()) >= rpm) {
            shooter.set(ControlMode.PercentOutput, 0);
            hitRPM = true;
        } else {
            shooter.set(ControlMode.PercentOutput, isASnake ? -1 : 1);
            hitRPM = false;
        }
    }

    public void setCoolRPM(double rpm) {
        CompressorTank.disable();
        double power = 0;
        double offBy = rpm - getRPM();
        power += (offBy / rpm) * 10;
        if(rpm > 0) {
            shooter.set(ControlMode.PercentOutput, power);
        }
        else if(rpm < 0) {
            shooter.set(ControlMode.PercentOutput, -power);
        }
        
    }

    public void setCoolerRPM(double rpm) {
        CompressorTank.disable();
        double error = rpm - getRPM(); // Error = Target - Actual
        this.integral += (error*.02); // Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
        double derivative = (error - this.previous_error) / .02;
        shooter.set(ControlMode.PercentOutput, P*error + I*this.integral + D*derivative);
    }

    public boolean hitRPM() {
        return hitRPM;

    }

    public void stopShooter() {
        shooter.set(ControlMode.PercentOutput, 0);
    }
}
