package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
@Disabled
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
                //Move diagonally (back-right) and grab the foundation
                robot.diagonalDrive(.5, -20, DiagonalDirection.RIGHT);
                placing.setClawGrip(ServoPosition.DOWN);
                stateMachineFlow++;
                break;
            case 2:
                //Back up to move the foundation into the building site
                robot.linearDrive(.5, 15);
                stateMachineFlow++;
                break;
            case 3:
                //Move right out from plate
                robot.sideDrive(.5, -10);
                stateMachineFlow++;
                break;
            case 4:
                //move to other side of the plate
                robot.linearDrive(.5,-40);
                stateMachineFlow++;
                break;
            case 5:
                //move to the side of the plate
                robot.sideDrive(.5,10);
                stateMachineFlow++;
                break;
            case 6:
                //push the plate into the zone
                robot.linearDrive(.5,10);
                stateMachineFlow++;
                break;
            case 7:
                //move under the bridge
                robot.linearDrive(.5,-5);
                robot.sideDrive(.5,-20);
                stateMachineFlow++;
                break;
        }
    }
}
