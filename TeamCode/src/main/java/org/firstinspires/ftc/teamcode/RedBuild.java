package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
//@Disabled
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
        robot.initIMU(hardwareMap);
        intake.init(hardwareMap);
        lift.init(hardwareMap);
        placing.init(hardwareMap);
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
                //Move left
                robot.sideDrive(.45,-21);
                stateMachineFlow++;
                break;
            case 2:
                //Back up to the foundation
                robot.linearDrive(.5, -30);
                stateMachineFlow++;
                break;
            case 3:
                placing.setPlateHooks(ServoPosition.DOWN);
                stateMachineFlow++;
                break;
            case 4:
                //move the plate into the zone
                robot.linearDrive(.4,36);
                stateMachineFlow++;
                break;
            case 5:
                robot.gStatTurn(.6,-90);
                stateMachineFlow++;
                break;
            case 6:
                placing.setPlateHooks(ServoPosition.UP);
                stateMachineFlow++;
                break;
            case 7:
                robot.linearDrive(.75,-15);
                stateMachineFlow++;
                break;
            case 8:
                //move under the bridge
                robot.linearDrive(.6,40);
                stateMachineFlow++;
                break;
        }
    }
}
