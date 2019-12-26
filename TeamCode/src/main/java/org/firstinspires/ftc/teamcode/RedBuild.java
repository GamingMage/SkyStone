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

    @Override
    public void loop() {
        switch (stateMachineFlow) {
            case 0:
                runtime.reset();
                time = getRuntime();
                stateMachineFlow++;
                break;
            case 1:
                //Move diagonally (forward-right) 40 inches and grab the foundation
                robot.diagonalDrive(.5,40,DiagonalDirection.RIGHT);
                placing.setClawGrip(ServoPosition.DOWN);
                stateMachineFlow++;
                break;
            case 2:
                //Back up 20 inches to move the foundation into the building site
                robot.linearDrive(.5,-20);
                stateMachineFlow++;
                break;
            case 3:
                //Move left 70 inches
                robot.sideDrive(.5,-70);
                stateMachineFlow++;
                break;
            case 4:
                //Move forward 20 inches
                robot.linearDrive(.5,20);
                stateMachineFlow++;
                break;
            case 5:
                //Move left using viewforia, stop when a skystone is detected
                stateMachineFlow++;
                break;
            case 6:
                //Move forward 20 inches and grab the skystone
                robot.linearDrive(.5,20);
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
                //Move right 85 inches
                robot.sideDrive(.5,85);
                stateMachineFlow++;
                break;
            case 9:
                //Place the skystone on the foundation
                placing.setClawGrip(ServoPosition.UP);
                stateMachineFlow++;
                break;
            case 10:
                //Move left 70 inches
                robot.sideDrive(-.5,70);
                stateMachineFlow++;
                break;
            case 11:
                //Move left using viewforia, stop when a skystone is detected
                stateMachineFlow++;
                break;
            case 12:
                //Move forward 20 inches and grab the skystone
                robot.linearDrive(.5,20);
                placing.setClawWrist(ServoPosition.UP);
                placing.setClawTurn(ServoPosition.TURN_OUT);
                placing.setClawWrist(ServoPosition.DOWN);
                placing.setClawGrip(ServoPosition.DOWN);
                stateMachineFlow++;
                break;
            case 13:
                //Back up 20 inches
                robot.linearDrive(.5,-20);
                stateMachineFlow++;
                break;
            case 14:
                //Move right 85 inches
                robot.sideDrive(.5,85);
                stateMachineFlow++;
                break;
            case 15:
                //Place the skystone on the foundation
                placing.setClawWrist(ServoPosition.UP);
                placing.setClawTurn(ServoPosition.TURN_OUT);
                placing.setClawWrist(ServoPosition.DOWN);
                placing.setClawGrip(ServoPosition.DOWN);
                stateMachineFlow++;
                break;
            case 16:
                //Move left 40 inches to park under red alliance bridge
                robot.sideDrive(-.5,40);
                stateMachineFlow++;
                break;
        }
    }
}
