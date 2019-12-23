/*package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class LiftSystem
{
    /* Public OpMode members.
    public DcMotor  liftMotor   = null;
    public DcMotor  armMotor    = null;

    public Servo    hookServo   = null;

    DigitalChannel REVTouchBottom;
    DigitalChannel REVTouchTop;
    /**
     * The REV Robotics Touch Sensor
     * is treated as a digital channel.  It is HIGH if the button is unpressed.
     * It pulls LOW if the button is pressed.
     *
     * Also, when you connect a REV Robotics Touch Sensor to the digital I/O port on the
     * Expansion Hub using a 4-wire JST cable, the second pin gets connected to the Touch Sensor.
     * The lower (first) pin stays unconnected.*
     *

    public static final int MARKER = 850; //Subject to change
    public static final int DUMP   = 900; //Subject to change
    public static final int BOTTOM = 30;

    public static final int DROP = 1500;  //Need to measure
    public static final int HOOK = 0; //Should be fine since we start here

    public static final double HOOK_ON    =  1; //Flip hook up into the bracket
    public static final double HOOK_OFF   = .4; //Flip hook out

    /* local OpMode members. *
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor *
    public LiftSystem(){

    }

    /* Initialize standard Hardware interfaces *
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        armMotor = hwMap.get(DcMotor.class, "arm_drive");
        armMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        liftMotor = hwMap.get(DcMotor.class, "lift_drive");
        liftMotor.setDirection(DcMotor.Direction.FORWARD); //subject to change
        // Set all motors to zero power
        armMotor.setPower(0);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setPower(0);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // Set all motors to run using encoders if encoders are installed... or else, set to run without
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Define and initialize ALL installed servos
        hookServo = hwMap.get(Servo.class, "hook_servo");
        //hookServo.setPosition(HOOK_OFF);
        // Define and initialize ALL installed sensors
        REVTouchBottom = hwMap.get(DigitalChannel.class, "Bottom_Touch");
        REVTouchTop = hwMap.get(DigitalChannel.class, "Top_Touch");
        // set the digital channel to input.
        REVTouchBottom.setMode(DigitalChannel.Mode.INPUT);
        REVTouchTop.setMode(DigitalChannel.Mode.INPUT);
    }
    public void armPos(ArmPosition armPosition){ //Add raise up lift to get basket clear
        if (armPosition == ArmPosition.TOP) {
            hookServo.setPosition(HOOK_OFF);
            liftControl(.6,LiftDirection.UP); //Clear the basket before flipping it
            armMotor.setTargetPosition(DUMP);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armMotor.setPower(.5);
            while (armMotor.isBusy());
            armMotor.setPower(0);
            armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }else if (armPosition == ArmPosition.BOTTOM) {
            hookServo.setPosition(HOOK_OFF);
            armMotor.setTargetPosition(BOTTOM);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armMotor.setPower(.65);
            while (armMotor.isBusy());
            armMotor.setPower(0);
            armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            liftControl(.5,LiftDirection.DOWN); //lower the lift after the box is back down
        }
    }
    public void armPosMark(ArmPosition armPosition){ //Add raise up lift to get basket clear
        if (armPosition == ArmPosition.TOP) {
            hookServo.setPosition(HOOK_OFF);
            liftControl(.6,LiftDirection.UP); //Clear the basket before flipping it
            armMotor.setTargetPosition(MARKER);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armMotor.setPower(.5);
            while (armMotor.isBusy());
            armMotor.setPower(0);
            armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }else if (armPosition == ArmPosition.BOTTOM) {
            hookServo.setPosition(HOOK_OFF);
            armMotor.setTargetPosition(BOTTOM);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armMotor.setPower(.65);
            while (armMotor.isBusy());
            armMotor.setPower(0);
            armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            liftControl(.5,LiftDirection.DOWN); //lower the lift after the box is back down
        }
    }
    public void liftControl (double power, LiftDirection upOrDown) {
        // if the digital channel returns true it's HIGH and the button is unpressed.
        if (upOrDown == LiftDirection.DOWN && REVTouchBottom.getState()) {
            liftMotor.setPower(-power);//May need to be fixed for direction
            while (REVTouchBottom.getState());
            liftMotor.setPower(0);
        } else if (upOrDown == LiftDirection.UP && REVTouchTop.getState()) {
            liftMotor.setPower(power);
            while (REVTouchTop.getState());
            liftMotor.setPower(0);
        }
    }
    public void liftHookOnOff (HookOnOff hookOnOff) {
        if (hookOnOff == HookOnOff.HOOK) {

            liftControl(.65,LiftDirection.UP); //raise lift into place

            hookSet(HookOnOff.HOOK); //put hook through bracket
            double runtime = period.time();
            while (.6 > period.time() - runtime );
            liftControl(.65,LiftDirection.DOWN); //lower lift to raise robot
        }
        else if (hookOnOff == HookOnOff.DROP) {
            liftControl(.65,LiftDirection.UP); //lower robot off the lander

            hookSet(HookOnOff.OFF); //remove hook from bracket
            double runtime = period.time();
            while (.6 > period.time() - runtime );
            liftControl(.65,LiftDirection.DOWN); //Lower lift to get it out of the way
        }
    }
    public void hookSet (HookOnOff hookOnOff) {
        if (hookOnOff == HookOnOff.HOOK) {hookServo.setPosition(HOOK_ON);}
        else if (hookOnOff == HookOnOff.OFF) {hookServo.setPosition(HOOK_OFF);}
    }
    public void armPower (double power){
        armMotor.setPower(power);
    }
    public int getLiftPosition(){
        return liftMotor.getCurrentPosition();
    }
    public int getArmPosition() {
        return armMotor.getCurrentPosition();
    }
 }

*/