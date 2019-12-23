package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name="Mecanum_Control", group="Test")

public class MecanumControl extends OpMode
{

    MecanumDrive robot = new MecanumDrive();
    Placing      place = new Placing();

    //Speed variables to allow for speed dilation
    float rXSpeed;
    float lXSpeed;
    float lYSpeed;

    @Override
    public void init() {
        robot.init(hardwareMap);
        place.init(hardwareMap);

        msStuckDetectLoop = 8000;

        telemetry.addData("Hello","It's time");
        telemetry.addData("Loop_Timeout",msStuckDetectLoop);
        telemetry.update();
    }
    @Override
    public void loop() {
        telemetry.addData("RSx",gamepad1.right_stick_x);
        telemetry.addData("LSy",gamepad1.left_stick_y);
        telemetry.addData("LSx",gamepad1.left_stick_x);
        telemetry.update();

        //Set everything to zero when neither stick is in use
        if (gamepad1.left_stick_y>-.1 && gamepad1.left_stick_y<.1 && gamepad1.left_stick_x>-.1 && gamepad1.left_stick_x<.1 && gamepad1.right_stick_x>-.1 && gamepad1.right_stick_x<.1){
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
        if (gamepad1.left_stick_x>-.1 && gamepad1.left_stick_x<.1 && (gamepad1.left_stick_y<-.1 || gamepad1.left_stick_y>.1)) {
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
    }
}
