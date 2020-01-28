package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Plate_Servo", group="Testier")

public class PlateServoTest extends OpMode
{

    MecanumDrive robot  = new MecanumDrive();
    Placing      place  = new Placing();
    Intake       intake = new Intake();
    Lift         lift   = new Lift();

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
        //intake.init(hardwareMap);
        //lift.init(hardwareMap);

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



        /*if (gamepad1.right_stick_x == 0 && gamepad1.left_stick_y == 0 && gamepad1.left_stick_x == 0){
            robot.leftFront.setPower(0);
            robot.leftBack.setPower(0);
            robot.rightFront.setPower(0);
            robot.rightBack.setPower(0);
        }*/
        //Speed control (turbo/slow mode) and direction of stick calculation
        if (gamepad1.left_bumper){
            driveSpeed = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
            direction = Math.atan2(-gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
            turnSpeed = gamepad1.right_stick_x;
        }else if (gamepad1.right_bumper){
            driveSpeed = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y)*.4;
            direction = Math.atan2(-gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
            turnSpeed = gamepad1.right_stick_x*.4;
        }else {
            driveSpeed = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y)*.7;
            direction = Math.atan2(-gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
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
            place.clawGrip.setPosition(.7);
        }if (gamepad1.dpad_up){
            place.clawGrip.setPosition(0);
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
