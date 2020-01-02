package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
@Disabled
@Autonomous(name="Red: Build", group="Test")
public class RedBuild extends OpMode{

    private int stateMachineFlow;
    MecanumDrive robot = new MecanumDrive();
    Intake intake      = new Intake();
    Lift lift          = new Lift();
    Placing placing    = new Placing();
    SkystoneCam cam    = new SkystoneCam();

    double time;
    double distanceToTarget;
    static final int NINETY_DEGREES = 90;
    int angleToTarget = 0;
    int initView = 0;
    private ElapsedTime     runtime = new ElapsedTime();
    /***********************************
     *
     * This program starts with the robot's intake facing the wall
     *
     ***********************************/
    @Override
    public void init() {
        msStuckDetectInit = 11500;
        msStuckDetectLoop = 10000;

        robot.init(hardwareMap);
        intake.init(hardwareMap);
        lift.init(hardwareMap);
        placing.init(hardwareMap);
        cam.init(hardwareMap);

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
                //Move diagonally (back-left) and grab the foundation
                robot.diagonalDrive(.65, -25, DiagonalDirection.LEFT);
                stateMachineFlow++;
                break;
            case 2:
                //Back up to the foundation
                robot.linearDrive(.65, -11);
                stateMachineFlow++;
                break;
            case 3:
                placing.setClawGrip(ServoPosition.DOWN);
                stateMachineFlow++;
                break;
            case 4:
                //move the plate into the zone
                robot.linearDrive(.65,29);
                stateMachineFlow++;
                break;
            case 5:
                //move under the bridge
                robot.sideDrive(.65,45);
                stateMachineFlow++;
                break;
        }
    }
}
