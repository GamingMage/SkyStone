package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Mecanum_Control", group="Test")

public class MecanumControl extends OpMode
{

    MecanumDrive robot  = new MecanumDrive();
    Placing      place  = new Placing();
    Intake       intake = new Intake();
    Lift         lift   = new Lift();

    //Speed variables to allow for speed dilation
    float rXSpeed;
    float lXSpeed;
    float lYSpeed;
    private int placeHeight;
    private boolean isIntakeOn;

    @Override
    public void init() {
        robot.init(hardwareMap);
        place.init(hardwareMap);
        intake.init(hardwareMap);
        lift.init(hardwareMap);

        msStuckDetectInit = 18000;
        msStuckDetectLoop = 18000;
        placeHeight = 1;
        isIntakeOn = false;

        telemetry.addData("Hello","It's time");
        telemetry.addData("Loop_Timeout",msStuckDetectLoop);
        telemetry.update();
    }
    @Override
    public void loop() {
        telemetry.addData("RSx",gamepad1.right_stick_x);
        telemetry.addData("LSy",gamepad1.left_stick_y);
        telemetry.addData("LSx",gamepad1.left_stick_x);
        telemetry.addData("Place Height",placeHeight);
        telemetry.update();

        //Set everything to zero when neither stick is in use
        if (gamepad1.left_stick_y>-.1 && gamepad1.left_stick_y<.1 && gamepad1.left_stick_x>-.1
                && gamepad1.left_stick_x<.1 && gamepad1.right_stick_x>-.1 && gamepad1.right_stick_x<.1){
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
        }else {
            rXSpeed = gamepad1.right_stick_x/2;
            lXSpeed  = gamepad1.left_stick_x/2;
            lYSpeed  = gamepad1.left_stick_y/2;
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
            place.setClawGrip(ServoPosition.DOWN);
        }if (gamepad1.dpad_up){
            place.setClawGrip(ServoPosition.UP);
        }

        //Intake Control
        if (gamepad1.right_trigger > 0 && !isIntakeOn && gamepad1.left_trigger == 0){
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
        }

        //control of the lift
        if (gamepad2.dpad_up){
            placeHeight++;
            if (placeHeight == 7){
                placeHeight = 1;
            }
        }
        if (gamepad2.dpad_down){
            placeHeight--;
            if (placeHeight == 0){
                placeHeight = 6;
            }
        }
        if (gamepad1.a){
            robot.rightBack.setPower(0);
            robot.leftFront.setPower(0);
            robot.rightFront.setPower(0);
            robot.leftBack.setPower(0);
            if (placeHeight == 1){
                lift.placeLevel(PlaceLevel.ONE);
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
        if (gamepad1.dpad_left){
            robot.rightBack.setPower(0);
            robot.leftFront.setPower(0);
            robot.rightFront.setPower(0);
            robot.leftBack.setPower(0);
            place.setClawGrip(ServoPosition.DOWN);
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
            place.setClawTurn(ServoPosition.TURN_IN);
            place.setClawWrist(ServoPosition.DOWN);
            place.setClawGrip(ServoPosition.UP);
        }

        //control of plate hooks
        if (gamepad2.right_bumper){
            robot.rightBack.setPower(0);
            robot.leftFront.setPower(0);
            robot.rightFront.setPower(0);
            robot.leftBack.setPower(0);
            place.setPlateHooks(ServoPosition.DOWN);
        }
        if (gamepad2.left_bumper){
            robot.rightBack.setPower(0);
            robot.leftFront.setPower(0);
            robot.rightFront.setPower(0);
            robot.leftBack.setPower(0);
            place.setPlateHooks(ServoPosition.UP);
        }
    }
}
