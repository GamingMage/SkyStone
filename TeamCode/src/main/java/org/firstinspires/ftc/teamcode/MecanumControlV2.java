package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Mecanum_Control_V2", group="Testier")

public class MecanumControlV2 extends OpMode
{

    MecanumDrive robot  = new MecanumDrive();
    Placing      place  = new Placing();
    Intake       intake = new Intake();
    Lift         lift   = new Lift();
    FindStone    stone  = new FindStone();

    double driveSpeed;
    double turnSpeed;
    double direction;

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
        stone.init(hardwareMap);

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
        //telemetry.addData("Place Height",placeHeight);
        //telemetry.addData("front lift",lift.getFrontLiftEncoder());
        //telemetry.addData("back lift",lift.getBackLiftEncoder());
        //telemetry.addData("arm",place.getArmEncoder());
        telemetry.addData("lRed",stone.lGetRedVal());
        telemetry.addData("lGreen",stone.lGetGreenVal());
        telemetry.addData("lBlue",stone.lGetBlueVal());
        telemetry.addData("rRed",stone.rGetRedVal());
        telemetry.addData("rGreen",stone.rGetGreenVal());
        telemetry.addData("rBlue",stone.rGetBlueVal());
        //telemetry.addData("lift touch",lift.REVTouchBottom.getState());
        //telemetry.addData("arm touch",place.REVTouchInside.getState());
        //telemetry.addData("Drive Speed",driveSpeed);
        //telemetry.addData("Direction",direction);
        //telemetry.addData("Turn Speed", turnSpeed);
        //telemetry.addData("LB",robot.getLBencoder());
        //telemetry.addData("RB",robot.getRBencoder());
        //telemetry.addData("LF",robot.getLFencoder());
        //telemetry.addData("RF",robot.getRFencoder());
        telemetry.update();

        //Speed control (turbo/slow mode) and direction of stick calculation
        if (gamepad1.left_bumper){
            driveSpeed = Math.hypot(-gamepad1.left_stick_x, -gamepad1.left_stick_y);
            direction = Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI / 4;
            turnSpeed = gamepad1.right_stick_x;
        }else if (gamepad1.right_bumper){
            driveSpeed = Math.hypot(-gamepad1.left_stick_x, -gamepad1.left_stick_y)*.4;
            direction = Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI / 4;
            turnSpeed = gamepad1.right_stick_x*.4;
        }else {
            driveSpeed = Math.hypot(-gamepad1.left_stick_x, -gamepad1.left_stick_y)*.7;
            direction = Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI / 4;
            turnSpeed = gamepad1.right_stick_x*.7;
        }
        //set power and direction of drive motors
        if (turnSpeed == 0){
            robot.MecanumController(driveSpeed,direction,0);
        }
        //control of turning
        if (gamepad1.right_stick_x != 0 && driveSpeed == 0){
            robot.leftFront.setPower(turnSpeed);
            robot.leftBack.setPower(turnSpeed);
            robot.rightFront.setPower(-turnSpeed);
            robot.rightBack.setPower(-turnSpeed);
        }

        //Gripper control
        if (gamepad1.dpad_down){
            place.clawGrip.setPosition(.2);
        }if (gamepad1.dpad_up){
            place.clawGrip.setPosition(.67);
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
        /*if (gamepad2.dpad_up){
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
        }*/

        //manual control of lift
        if (gamepad2.y){
            lift.frontLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            lift.backLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            lift.liftPower(-.8);
//        }else if (gamepad2.x && lift.REVTouchBottom.getState()){
        }else if (gamepad2.x){
            lift.frontLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            lift.backLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            lift.liftPower(.8);
        }else {
            lift.liftPower(0);
            lift.frontLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lift.backLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        //manual control of the arm
/*        if (gamepad2.left_stick_y != 0 && place.REVTouchInside.getState()){*/
        if (gamepad2.right_stick_y != 0){
            place.armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            place.armMotor.setPower(-gamepad2.right_stick_y*.7);
        }if (gamepad2.right_stick_y == 0){
            place.armMotor.setPower(0);
            place.armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        //control of wrist
        if (gamepad2.dpad_right){
            place.clawWrist.setPosition(.32);
        }
        if (gamepad2.dpad_left){
            place.clawWrist.setPosition(.82);
        }
        //control of plate hooks
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
