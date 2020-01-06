/*package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Blue: Depot View", group="Vuforia")
@Disabled
public class BlueDepot extends OpMode{

    private int stateMachineFlow;
    Robot robot      = new Robot();
    double time;
    int initView =0;
    private ElapsedTime     runtime = new ElapsedTime();

    @Override
    public void init() {
        msStuckDetectInit = 11500;

        telemetry.log().add("before init");
        robot.init(hardwareMap);
        telemetry.log().add("after robot");
        lift.init(hardwareMap);
        telemetry.log().add("after lift");
        //view.init(hardwareMap);
        //telemetry.log().add("after Vuforia");

        telemetry.log().add("after hardware init");

        stateMachineFlow = 0;
    }

    @Override
    public void init_loop(){
        if (initView == 0){
            view.init(hardwareMap);
            telemetry.log().add("After Vuforia");
            initView = 4;
        }
    }
    @Override
    public void loop() {
        switch(stateMachineFlow){
            case 0:
                runtime.reset();
                time = getRuntime();
                stateMachineFlow++;
                break;
            case 1:
                robot.linearDrive(0,0);
                // moves robot forward
                stateMachineFlow++;
                break;
            case 2:
                robot.linearDrive(.6,-8);
                time = getRuntime();
                stateMachineFlow++;
                break;
            case 3:
                robot.gStatTurn(.65,85);
                stateMachineFlow++;
                break;
            case 4:
                robot.linearDrive(.55,3);
                stateMachineFlow++;
                break;
            case 5:
                robot.gStatTurn(.65,84);
                time = getRuntime();
                stateMachineFlow++;
                break;
            case 6:
                if (getRuntime() - time > 1.25) {
                    goldPos = view.MineralRecog();
                    telemetry.addData("GoldPos",goldPos);
                    telemetry.update();
                    stateMachineFlow++;
                }
                break;
            case 7:
                if (goldPos == MineralPosition.LEFT){
                    robot.gStatTurn(.65,30);
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.gStatTurn(.65,-32);
                }
                stateMachineFlow++;
                break;
            case 8:
                if (goldPos == MineralPosition.LEFT){
                    robot.linearDrive(.65,27);
                }else if (goldPos == MineralPosition.CENTER || goldPos == MineralPosition.UNKNOWN){
                    robot.linearDrive(.65,23);
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.linearDrive(.65,27);
                }
                stateMachineFlow++;
                break;
            case 9:
                if (goldPos == MineralPosition.LEFT){
                    robot.linearDrive(.65,-27);
                }else if (goldPos == MineralPosition.CENTER || goldPos == MineralPosition.UNKNOWN){
                    robot.linearDrive(.65,-8);
                    stateMachineFlow = 12;
                    break;
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.linearDrive(.65,-27);
                }
                stateMachineFlow++;
                break;
            case 10:
                if (goldPos == MineralPosition.LEFT){
                    robot.gStatTurn(.6,-30);
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.gStatTurn(.6,32);
                }
                stateMachineFlow++;
                break;
            case 11:
                if (goldPos == MineralPosition.LEFT){
                    robot.linearDrive(.65,13);
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.linearDrive(.65,13);
                }
                stateMachineFlow++;
                break;
            case 12:
                robot.gStatTurn(.65,87); //seems to be over turning... so i lowered it
                stateMachineFlow++;
                break;
            case 13:
                robot.linearDrive(.65,52);
                // turn right
                stateMachineFlow++;
                break;
            case 14:
                robot.gStatTurn(.65,34);
                // move forward a little bit
                stateMachineFlow++;
                break;
            case 15:
                robot.linearDrive(.65,30);
                // turn so you can start testing the color of the elements
                stateMachineFlow++;
                break;
            case 16:
                lift.armPosMark(ArmPosition.TOP);
                stateMachineFlow++;
                break;
            case 17:
                lift.armPosMark(ArmPosition.BOTTOM);
                stateMachineFlow++;
                break;
            case 18:
                robot.linearDrive(.7,-64);
                // move forward to the blue depot
                stateMachineFlow++;
                break;
        }
    }
}//end of class*/