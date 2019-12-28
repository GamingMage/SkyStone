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
    MecanumDrive robot      = new MecanumDrive();
    Intake intake  = new Intake();
    Lift lift = new Lift();
    Placing placing = new Placing();

    double time;
    int initView =0;
    private ElapsedTime     runtime = new ElapsedTime();

    @Override
    public void init() {
        msStuckDetectInit = 11500;

        robot.init(hardwareMap);
        intake.init(hardwareMap);
        lift.init(hardwareMap);
        placing.init(hardwareMap);

        stateMachineFlow = 0;
    }
//
    @Override
    public void loop() {
        switch (stateMachineFlow){
            case 0:
                runtime.reset();
                time = getRuntime();
                stateMachineFlow++;
                break;
            case 1:
                //Move diagonally (forward-left) and grab the foundation
                robot.diagonalDrive(.5,40,DiagonalDirection.LEFT);
                placing.setClawGrip(ServoPosition.DOWN);
                stateMachineFlow++;
                break;
            case 2:
                //Back up to move the foundation into the building site
                robot.linearDrive(.5,-20);
                stateMachineFlow++;
                break;
            case 3:
                //Move right
                robot.sideDrive(.5,70);
                stateMachineFlow++;
                break;
            case 4:
                //Move forward
                robot.linearDrive(.5,10);
                stateMachineFlow++;
                break;
            case 5:
                //Move right while using viewforia, stop when a skystone is detected
                stateMachineFlow++;
                break;
            case 6:
                //Move forward and grab the skystone
                robot.linearDrive(.5,10);
                placing.setClawWrist(ServoPosition.UP);
                placing.setClawTurn(ServoPosition.TURN_OUT);
                placing.setClawWrist(ServoPosition.DOWN);
                placing.setClawGrip(ServoPosition.DOWN);
                stateMachineFlow++;
                break;
            case 7:
                //Back up 20 inches
                robot.linearDrive(.5,-20);
                stateMachineFlow++;
                break;
            case 8:
                //Move left 85 inches
                robot.sideDrive(-.5,85);
                stateMachineFlow++;
                break;
            case 9:
                //Place the skystone on the foundation
                placing.setClawGrip(ServoPosition.UP);
                stateMachineFlow++;
                break;
            case 10:
                //Move right 70 inches
                robot.sideDrive(.5,70);
                stateMachineFlow++;
                break;
            case 11:
                //Move right while using viewforia, stop when a skystone is sensed
                stateMachineFlow++;
                break;
            case 12:
                //Move forward 20 inches and grab the skystone
                robot.linearDrive(.5,20);
                stateMachineFlow++;
                break;
            case 13:
                //Back up 20 inches
                robot.linearDrive(.5,-20);
                stateMachineFlow++;
                break;
            case 14:
                //Move left 100 inches
                robot.sideDrive(-.5,100);
                stateMachineFlow++;
                break;
            case 15:
                //Place the skystone on the foundation
                placing.setClawGrip(ServoPosition.UP);
                stateMachineFlow++;
                break;
            case 16:
                //Move right 40 inches to park under blue alliance bridge
                robot.sideDrive(.5,40);
                stateMachineFlow++;
                break;
        }
    }
}
