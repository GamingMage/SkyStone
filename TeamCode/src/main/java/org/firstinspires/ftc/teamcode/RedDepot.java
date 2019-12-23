package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
@Disabled
@Autonomous(name="Red: Depot", group="Test")
public class RedDepot extends OpMode {

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
                //Move right until vuforia detects skystone
                stateMachineFlow++;
                break;
            case 2:
                //Move forward, grabs skystone
                robot.linearDrive(.5,20);
                placing.setClawWrist(ServoPosition.UP);
                placing.setClawTurn(ServoPosition.TURN_OUT);
                placing.setClawWrist(ServoPosition.DOWN);
                placing.setClawGrip(ServoPosition.DOWN);
                stateMachineFlow++;
                break;
            case 3:
                // Back up
                robot.linearDrive(.5,-20);
                stateMachineFlow++;
                break;
            case 4:
                //Move left
                robot.sideDrive(-.5,70);
                stateMachineFlow++;
                break;
            case 5:
                //Move forward
                robot.linearDrive(.5,15);
                stateMachineFlow++;
                break;
            case 6:
                //  Put the skystone on foundation
                placing.setClawGrip(ServoPosition.UP);
                stateMachineFlow++;
                break;
            case 7:
                // Grab foundation, move backwards
                robot.linearDrive(.5,-1);
                placing.setClawGrip(ServoPosition.DOWN);
                robot.linearDrive(.5,15);
                stateMachineFlow++;
                break;
            case 8:
                // Move right until vuforia detects skystone
                stateMachineFlow++;
                break;
            case 9:
                // Move forward, grab skystone
                robot.linearDrive(.5,20);
                placing.setClawWrist(ServoPosition.UP);
                placing.setClawTurn(ServoPosition.TURN_OUT);
                placing.setClawWrist(ServoPosition.DOWN);
                placing.setClawGrip(ServoPosition.DOWN);
                stateMachineFlow++;
                break;
            case 10:
                // Back up
                robot.linearDrive(.5,-20);
                stateMachineFlow++;
                break;
            case 11:
                // Move left
                robot.sideDrive(-.5,70);
                stateMachineFlow++;
                break;
            case 12:
                //Put the skystone on foundation
                robot.linearDrive(.5,15);
                placing.setClawGrip(ServoPosition.UP);
                stateMachineFlow++;
                break;
            case 13:
                // Move right to park under blue alliance bridge
                robot.sideDrive(.5,40);
                stateMachineFlow++;
                break;
        }
    }
}
