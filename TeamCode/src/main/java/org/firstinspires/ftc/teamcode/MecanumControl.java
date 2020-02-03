package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Mecanum_Control", group="Test")

public class MecanumControl extends OpMode
{

    MecanumDrive robot  = new MecanumDrive();
    Placing      place  = new Placing();
    Intake       intake = new Intake();
    Lift         lift   = new Lift();

    //Speed variables allow for speed dilation
    private float rXSpeed;
    private float lXSpeed;
    private float lYSpeed;

    private int placeHeight;
    private boolean isIntakeOn;
    private boolean wasLevelIncreased;
    private boolean wasLevelDecreased;

    private ElapsedTime period  = new ElapsedTime();
    private double runtime = 0;

    @Override
    public void init() {
        robot.init(hardwareMap);
        place.init(hardwareMap);
        intake.init(hardwareMap);
        lift.init(hardwareMap);

        msStuckDetectInit = 18000;
        msStuckDetectLoop = 18000;
        placeHeight = 0;
        wasLevelIncreased = false;
        wasLevelDecreased = false;
        isIntakeOn = false;

        telemetry.addData("Hello","be ready");
        telemetry.addData("Loop_Timeout",msStuckDetectLoop);
        telemetry.update();
    }
    @Override
    public void loop() {
        telemetry.addData("Place Height",placeHeight);
        telemetry.addData("Front lift encoder",lift.getFrontLiftEncoder());
        telemetry.addData("Back lift encoder",lift.getBackLiftEncoder());
        //telemetry.addData("touch sensor",lift.REVTouchBottom.getState());
        //telemetry.addData("LB",robot.getLBencoder());
        //telemetry.addData("RB",robot.getRBencoder());
        //telemetry.addData("LF",robot.getLFencoder());
        //telemetry.addData("RF",robot.getRFencoder());
        telemetry.update();

        //Set everything to zero when neither stick is in use
        if (gamepad1.left_stick_y>-.1 && gamepad1.left_stick_y<.1 && gamepad1.left_stick_x>-.1
                && gamepad1.left_stick_x<.1 && gamepad1.right_stick_x==0){
            robot.rightBack.setPower(0);
            robot.leftFront.setPower(0);
            robot.rightFront.setPower(0);
            robot.leftBack.setPower(0);
        }

        //Speed control (turbo mode)
        if (gamepad1.left_bumper){
            rXSpeed = gamepad1.right_stick_x;
            lXSpeed  = gamepad1.left_stick_x;
            lYSpeed  = gamepad1.left_stick_y;
        }else if (gamepad1.right_bumper){
            rXSpeed = gamepad1.right_stick_x*.4f;
            lXSpeed  = gamepad1.left_stick_x*.4f;
            lYSpeed  = gamepad1.left_stick_y*.4f;
        }else {
            rXSpeed = gamepad1.right_stick_x*.7f;
            lXSpeed  = gamepad1.left_stick_x*.7f;
            lYSpeed  = gamepad1.left_stick_y*.7f;
        }

        //Control of left and right movement
        if ((gamepad1.left_stick_x>.1 || gamepad1.left_stick_x<-.1) && gamepad1.left_stick_y>-.1 && gamepad1.left_stick_y<.1){
            robot.rightBack.setPower(lXSpeed);
            robot.leftFront.setPower(lXSpeed);
            robot.rightFront.setPower(-lXSpeed);
            robot.leftBack.setPower(-lXSpeed);
        }

        //Control of forward and backward movement
        if (gamepad1.left_stick_x>-.1 && gamepad1.left_stick_x<.1 && (gamepad1.left_stick_y<-.1 || gamepad1.left_stick_y>.1)){
            robot.rightBack.setPower(-lYSpeed);
            robot.leftBack.setPower(-lYSpeed);
            robot.rightFront.setPower(-lYSpeed);
            robot.leftFront.setPower(-lYSpeed);
        }

        //Control of diagonal movement
        //Quadrant i
        if (gamepad1.left_stick_x>=.1 && gamepad1.left_stick_y<=-.1){
            robot.leftFront.setPower(lXSpeed);
            robot.rightBack.setPower(lXSpeed);
            robot.leftBack.setPower(0);
            robot.rightFront.setPower(0);
        }
        //Quadrant ii
        if (gamepad1.left_stick_x<=-.1 && gamepad1.left_stick_y<=-.1){
            robot.leftFront.setPower(0);
            robot.rightBack.setPower(0);
            robot.leftBack.setPower(-lYSpeed);
            robot.rightFront.setPower(-lYSpeed);
        }
        //Quadrant iii
        if (gamepad1.left_stick_x<=-.1 && gamepad1.left_stick_y>=.1){
            robot.leftFront.setPower(lXSpeed);
            robot.rightBack.setPower(lXSpeed);
            robot.leftBack.setPower(0);
            robot.rightFront.setPower(0);
        }
        //Quadrant iv
        if (gamepad1.left_stick_x>=.1 && gamepad1.left_stick_y>=.1){
            robot.leftFront.setPower(0);
            robot.rightBack.setPower(0);
            robot.leftBack.setPower(-lYSpeed);
            robot.rightFront.setPower(-lYSpeed);
        }

        //Control of left and right turning (Perspective shift)
        if (gamepad1.right_stick_x!=0){
            robot.rightBack.setPower(-rXSpeed);
            robot.leftFront.setPower(rXSpeed);
            robot.rightFront.setPower(-rXSpeed);
            robot.leftBack.setPower(rXSpeed);
        }

        //Gripper control
        if (gamepad1.dpad_down){
            place.clawGrip.setPosition(.7);
        }if (gamepad1.dpad_up){
            place.clawGrip.setPosition(0);
        }

        //Intake Control
        /*if (gamepad1.right_trigger > 0 && !isIntakeOn && gamepad1.left_trigger == 0){
            intake.intakeControl(IntakeDirection.IN);
            isIntakeOn = true;
        }else if (gamepad1.right_trigger == 0 && isIntakeOn){
            intake.intakeControl(IntakeDirection.OFF);
            isIntakeOn = false;
        }
        if (gamepad1.left_trigger > 0 && !isIntakeOn && gamepad1.right_trigger == 0){
            intake.intakeControl(IntakeDirection.OUT);
            isIntakeOn = true;
        }else if (gamepad1.left_trigger == 0 && isIntakeOn){
            intake.intakeControl(IntakeDirection.OFF);
            isIntakeOn = false;
        }*/
        intake.leftIntake.setPower(-gamepad2.left_stick_y);
        intake.rightIntake.setPower(-gamepad2.left_stick_y);

        //control of the lift
        if (gamepad2.dpad_up){
            wasLevelIncreased = true;
        }else if (!gamepad2.dpad_up && wasLevelIncreased){
            placeHeight++;
            if (placeHeight == 7){
                placeHeight = 0;
            }
            wasLevelIncreased = false;
        }
        if (gamepad2.dpad_down){
            wasLevelDecreased = true;
        }else if (!gamepad2.dpad_down && wasLevelDecreased){
            placeHeight--;
            if (placeHeight == -1){
                placeHeight = 6;
            }
            wasLevelDecreased = false;
        }
        if (gamepad1.b){
            robot.rightBack.setPower(0);
            robot.leftFront.setPower(0);
            robot.rightFront.setPower(0);
            robot.leftBack.setPower(0);
            if (placeHeight == 0){
                lift.placeLevel(PlaceLevel.ONE);
            }else if (placeHeight == 1){
                lift.placeLevel(PlaceLevel.INSIDE);
            }else if (placeHeight == 2){
                lift.placeLevel(PlaceLevel.TWO);
            }else if (placeHeight == 3){
                lift.placeLevel(PlaceLevel.THREE);
            }else if (placeHeight == 4){
                lift.placeLevel(PlaceLevel.FOUR);
            }else if(placeHeight == 5){
                lift.placeLevel(PlaceLevel.FIVE);
            }else if (placeHeight == 6){
                lift.placeLevel(PlaceLevel.CAP);
            }
        }

        //move the claw assembly into and out of scoring position
        /*if (gamepad1.dpad_left){
            robot.rightBack.setPower(0);
            robot.leftFront.setPower(0);
            robot.rightFront.setPower(0);
            robot.leftBack.setPower(0);
            place.setClawGrip(ServoPosition.DOWN);
            lift.placeLevel(PlaceLevel.THREE);
            place.setClawWrist(ServoPosition.UP);
            place.setClawTurn(ServoPosition.TURN_OUT);
            place.setClawWrist(ServoPosition.DOWN);
        }
        if (gamepad1.dpad_right){
            robot.rightBack.setPower(0);
            robot.leftFront.setPower(0);
            robot.rightFront.setPower(0);
            robot.leftBack.setPower(0);
            place.setClawWrist(ServoPosition.UP);
            lift.placeLevel(PlaceLevel.THREE);
            place.setClawTurn(ServoPosition.TURN_IN);
            place.setClawWrist(ServoPosition.DOWN);
            place.setClawGrip(ServoPosition.UP);
            lift.placeLevel(PlaceLevel.INSIDE);
        }*/

        //manual control of lift
        if (gamepad2.y){
            lift.frontLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            lift.backLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            lift.liftPower(-.8);
        }else if (gamepad2.x && lift.REVTouchBottom.getState()){
            lift.frontLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            lift.backLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            lift.liftPower(.8);
        }else {
            lift.liftPower(0);
            lift.frontLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lift.backLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        //manual control of servos
        if (gamepad2.right_bumper){
            place.clawWrist.setPosition(.25);
        }
        if (gamepad2.left_bumper){
            place.clawWrist.setPosition(.67);
        }
       /* if (gamepad2.dpad_right){
            place.clawTurn.setPosition(.975);
        }
        if (gamepad2.dpad_left){
            place.clawTurn.setPosition(.2);
        }
        //control of plate hooks

        */
        //down
        if (gamepad2.a){
            place.lPlateHook.setPosition(1);
            place.rPlateHook.setPosition(0);
        }
        //up
        if (gamepad2.b){
            place.lPlateHook.setPosition(0);
            place.rPlateHook.setPosition(1);
        }
    }
}
