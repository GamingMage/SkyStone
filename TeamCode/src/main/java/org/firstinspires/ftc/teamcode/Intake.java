package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    //This class will contain the 2 motors that drive the intake system
    //sensors include a touch sensor to stop system when sensor is hit by block (grab block when hit)
    //Methods will include an in/off/reverse

    public DcMotor leftIntake  = null;
    public DcMotor rightIntake = null;

    HardwareMap hwMap          = null;

    public Intake(){
    }

    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftIntake  = hwMap.get(DcMotor.class, "left_intake");
        rightIntake = hwMap.get(DcMotor.class,"right_intake");
        //Directions subject to change when motor facing is identified
        leftIntake.setDirection(DcMotor.Direction.REVERSE);
        rightIntake.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        leftIntake.setPower(0);
        rightIntake.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    //Power directions subject to change depending on how motors move when giving positive or negative powers
    public void Intake(IntakeDirection direction){
        if (direction == IntakeDirection.IN){
            leftIntake.setPower(.5);
            rightIntake.setPower(-.5);
        }if (direction == IntakeDirection.OUT){
            leftIntake.setPower(-.5);
            rightIntake.setPower(.5);
        }if (direction == IntakeDirection.OFF){
            leftIntake.setPower(0);
            rightIntake.setPower(0);
        }
    }
}
