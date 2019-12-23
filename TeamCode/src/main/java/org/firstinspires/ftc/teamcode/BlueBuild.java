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
                stateMachineFlow++;
                break;
            case 2:
                //Back up to move the foundation into the building site
                stateMachineFlow++;
                break;
            case 3:
                //Move right
                stateMachineFlow++;
                break;
            case 4:
                //Move forward
                stateMachineFlow++;
                break;
            case 5:
                //Move right while using viewforia, stop when a skystone is detected
                stateMachineFlow++;
                break;
            case 6:
                //Move forward and grab the skystone
                stateMachineFlow++;
                break;
            case 7:
                //Back up 20 inches
                stateMachineFlow++;
                break;
            case 8:
                //Move left 85 inches
                stateMachineFlow++;
                break;
            case 9:
                //Place the skystone on the foundation
                stateMachineFlow++;
                break;
            case 10:
                //Move right 70 inches
                stateMachineFlow++;
                break;
            case 11:
                //Move right while using viewforia, stop when a skystone is sensed
                stateMachineFlow++;
                break;
            case 12:
                //Move forward 20 inches and grab the skystone
                stateMachineFlow++;
                break;
            case 13:
                //Back up 20 inches
                stateMachineFlow++;
                break;
            case 14:
                //Move left 100 inches
                stateMachineFlow++;
                break;
            case 15:
                //Place the skystone on the foundation
                stateMachineFlow++;
                break;
            case 16:
                //Move right 40 inches to park under blue alliance bridge
                stateMachineFlow++;
                break;
        }
    }
}
