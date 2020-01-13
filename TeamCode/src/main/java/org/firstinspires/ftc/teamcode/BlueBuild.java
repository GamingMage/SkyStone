package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
//@Disabled
@Autonomous(name="Blue: Build", group="Test")
public class BlueBuild extends OpMode{

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
                robot.linearDrive(.5,-5);
                stateMachineFlow++;
                break;
            case 2:
                //Move right
                robot.sideDrive(.65,22);
                stateMachineFlow++;
                break;
            case 3:
                //Back up to the foundation
                robot.linearDrive(.5, -25);
                stateMachineFlow++;
                break;
            case 4:
                placing.setPlateHooks(ServoPosition.DOWN);
                stateMachineFlow++;
                break;
            case 5:
                //move the plate into the zone
                robot.linearDrive(.4,33);
                stateMachineFlow++;
                break;
            case 6:
                placing.setPlateHooks(ServoPosition.UP);
                stateMachineFlow++;
                break;
            case 7:
                //move under the bridge
                robot.sideDrive(.6,-47);
                stateMachineFlow++;
                break;
        }
    }
}
