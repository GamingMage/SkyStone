package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="SkystoneCamTest", group="Test")

public class SkystoneCamTest extends OpMode {
    SkystoneCam cam    = new SkystoneCam();
    Intake      intake = new Intake();

    @Override
    public void init() {
        cam.init(hardwareMap);
        intake.init(hardwareMap);

        msStuckDetectInit = 8000;
    }

    @Override
    public void loop() {
        telemetry.addData("Position","{X,Y,Z} = %.1f, %.1f, %.1f",
                cam.getXPosition(),cam.getYPosition(),cam.getZPosition());
        telemetry.addData("Rotation","{Roll, Pitch, Heading} = %.0f, %.0f, %.0f",
                cam.getRoll(),cam.getPitch(),cam.getHeading());
        telemetry.update();

        //Intake Control
        if (gamepad1.right_trigger != 0){
            intake.intakeControl(IntakeDirection.IN);
        }else if (gamepad1.left_trigger != 0){
            intake.intakeControl(IntakeDirection.OUT);
        }else intake.intakeControl(IntakeDirection.OFF);
    }
}
