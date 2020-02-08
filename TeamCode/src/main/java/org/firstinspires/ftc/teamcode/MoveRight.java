package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

//@Disabled
@Autonomous(name="Move: Right", group="Test")
public class MoveRight extends OpMode{

    private int stateMachineFlow;
    MecanumDrive robot = new MecanumDrive();
    Intake intake      = new Intake();
    Lift lift          = new Lift();
    Placing placing    = new Placing();
    SkystoneCam cam    = new SkystoneCam();

    double time;
    private ElapsedTime     runtime = new ElapsedTime();
    /***********************************
     *
     * This program starts with the robot's intake facing the center
     *
     ***********************************/
    @Override
    public void init() {
        msStuckDetectInit = 11500;
        msStuckDetectLoop = 25000;

        robot.init(hardwareMap);
        //intake.init(hardwareMap);
        //lift.init(hardwareMap);
        //placing.init(hardwareMap);
        //cam.init(hardwareMap);

        stateMachineFlow = 0;
    }

    @Override
    public void loop() {
        switch (stateMachineFlow) {
            case 0:
                runtime.reset();
                time = getRuntime();
                stateMachineFlow++;
                break;
            case 1:
                //move under the bridge
                robot.sideDrive(.45,25);
                stateMachineFlow++;
                break;
        }
    }
}
