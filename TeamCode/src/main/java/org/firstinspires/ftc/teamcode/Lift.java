package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lift {

    //This class wil contain the lift motor
    //Methods will include an up/down move, and maybe others
    /******************************************************
     * Decisions that need to be made
     *
     * How to limit upward motion
     * Assuming we start from the bottom every time. The only way to get down is to go all the way.
     * How to return enum from getLiftPosition
     *****************************************************/

    public DcMotor liftDrive = null;

    HardwareMap hwMap        = null;

    DigitalChannel REVTouchBottom;

    static final double SPEED = .5;
    static final int BLOCK_HEIGHT = 250;
    static final int LEVEL_TWO = BLOCK_HEIGHT;
    static final int LEVEL_THREE = BLOCK_HEIGHT*2;
    static final int LEVEL_FOUR = BLOCK_HEIGHT*3;
    static final int LEVEL_FIVE = BLOCK_HEIGHT*4;
    static final int LEVEL_CAP = BLOCK_HEIGHT*5;

    public Lift(){
    }
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        liftDrive  = hwMap.get(DcMotor.class, "lift_drive");

        //Directions subject to change when motor facing is identified
        liftDrive.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        liftDrive.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        liftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Define and initialize ALL installed sensors
        REVTouchBottom = hwMap.get(DigitalChannel.class, "Bottom_Touch");

        // set the digital channel to input.
        REVTouchBottom.setMode(DigitalChannel.Mode.INPUT);
    }
    //send the lift down to its lowest height
    public void liftDown (double power) {
        // if the digital channel returns true it's HIGH and the button is unpressed.
        if (REVTouchBottom.getState()) {
            liftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            liftDrive.setPower(-power);//May need to be fixed for direction
            while (REVTouchBottom.getState());
            liftDrive.setPower(0);
            liftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            liftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }
    public void placeLevel (PlaceLevel level) {
        switch (level){
            case ONE:
                liftDown(SPEED);

                break;
            case TWO:
                liftDrive.setTargetPosition(LEVEL_TWO);
                liftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                liftDrive.setPower(SPEED);
                while(liftDrive.isBusy());
                liftDrive.setPower(0);
                liftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                break;
            case THREE:
                liftDrive.setTargetPosition(LEVEL_THREE);
                liftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                liftDrive.setPower(SPEED);
                while(liftDrive.isBusy());
                liftDrive.setPower(0);
                liftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                break;
            case FOUR:
                liftDrive.setTargetPosition(LEVEL_FOUR);
                liftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                liftDrive.setPower(SPEED);
                while(liftDrive.isBusy());
                liftDrive.setPower(0);
                liftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                break;
            case FIVE:
                liftDrive.setTargetPosition(LEVEL_FIVE);
                liftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                liftDrive.setPower(SPEED);
                while(liftDrive.isBusy());
                liftDrive.setPower(0);
                liftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                break;
            case CAP:
                liftDrive.setTargetPosition(LEVEL_CAP);
                liftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                liftDrive.setPower(SPEED);
                while(liftDrive.isBusy());
                liftDrive.setPower(0);
                liftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                break;
        }
    }

    public int getLiftPosition() {
        return Math.round(liftDrive.getCurrentPosition()/BLOCK_HEIGHT);
    }
}
