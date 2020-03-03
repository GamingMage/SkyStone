package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Red: Color Test", group="Color")
public class RedColorTest extends OpMode{

    private int stateMachineFlow;
    MecanumDrive robot = new MecanumDrive();
    Intake intake      = new Intake();
    Lift lift          = new Lift();
    Placing placing    = new Placing();
    FindStone color    = new FindStone();

    StoneID stonePosition;
    double time;
    double distanceToTarget;
    static final int NINETY_DEGREES = 90;
    int angleToTarget = 0;
    int initView = 0;
    private ElapsedTime     runtime = new ElapsedTime();

    @Override
    public void init() {
        msStuckDetectInit = 11500;
        msStuckDetectLoop = 10000;

        robot.init(hardwareMap);
        robot.initIMU(hardwareMap);
        intake.init(hardwareMap);
        lift.init(hardwareMap);
        placing.init(hardwareMap);
        color.init(hardwareMap);

        lift.frontLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.backLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        stateMachineFlow = 0;
    }

    @Override
    public void loop() {
        switch (stateMachineFlow){
            case 0:
                runtime.reset();
                time = getRuntime();
                stateMachineFlow++;
                break;
            case 1:
                robot.sideDrive(.45,28);
                stateMachineFlow++;
                break;
            case 2:
                stonePosition = color.SkystoneTest();
                if (stonePosition == StoneID.ONE){
                    robot.linearDrive(.6,5);
                    telemetry.addData("Stone Position","One");
                    telemetry.update();
                }else if (stonePosition == StoneID.TWO){
                    robot.linearDrive(.6,12);
                    telemetry.addData("Stone Position","Two");
                    telemetry.update();
                }else if (stonePosition == StoneID.THREE){
                    robot.linearDrive(.6,18);
                    telemetry.addData("Stone Position","Three");
                    telemetry.update();
                }
                stateMachineFlow++;
                break;
            case 3:
                robot.sideAllDrive(.45,20);
                stateMachineFlow++;
                break;
            case 4:
                intake.leftIntake.setPower(1);
                intake.rightIntake.setPower(1);
                time = runtime.time();
                while (.6 > runtime.time() - time);
                lift.liftPower(-.6);
                stateMachineFlow++;
                break;
            case 5:
                robot.linearDrive(.35,8);
                lift.liftPower(.4);
                stateMachineFlow++;
                break;
            case 6:
                intake.leftIntake.setPower(0);
                intake.rightIntake.setPower(0);
                lift.liftPower(0);
                robot.sideDrive(.45,-10);
                stateMachineFlow++;
                break;
            case 7:
                if (stonePosition == StoneID.ONE){
                    robot.linearDrive(.5,-71);
                }else if (stonePosition == StoneID.TWO){
                    robot.linearDrive(.5,-80);
                }else if (stonePosition == StoneID.THREE){
                    robot.linearDrive(.5,-86);
                }
                stateMachineFlow++;
                break;
            case 8:
                robot.sideDrive(.45,-6);
                stateMachineFlow++;
                break;
            case 9:
                robot.gStatTurn(.6,NINETY_DEGREES);
                stateMachineFlow++;
                break;
            case 10:
                robot.linearDrive(.5,-7);
                stateMachineFlow++;
                break;
            case 11:
                placing.setPlateHooks(ServoPosition.DOWN);
                stateMachineFlow++;
                break;
            case 12:
                //move the plate into the zone
                robot.linearDrive(.4,36);
                stateMachineFlow++;
                break;
            case 13:
                robot.gStatTurn(.6,-NINETY_DEGREES);
                stateMachineFlow++;
                break;
            case 14:
                robot.linearDrive(.75,-15);
                stateMachineFlow++;
                break;
            case 15:
                placing.setClawGrip(ServoPosition.DOWN);
                stateMachineFlow++;
                break;
            case 16:
                lift.liftPower(-.5);
                stateMachineFlow++;
                break;
            case 17:
                placing.armMotor.setPower(.5);
                stateMachineFlow++;
                break;
            case 18:
                placing.setPlateHooks(ServoPosition.UP);
                placing.setClawGrip(ServoPosition.UP);
                stateMachineFlow++;
                break;
            case 19:
                placing.armMotor.setPower(-.6);
                placing.setPlateHooks(ServoPosition.DOWN);
                placing.setPlateHooks(ServoPosition.UP);
                lift.liftPower(.4);
                stateMachineFlow++;
                break;
            case 20:
                lift.liftPower(0);
                stateMachineFlow++;
                break;
            case 21:
                robot.sideDrive(.4,-12);
                stateMachineFlow++;
                break;
            case 22:
                //move under the bridge
                robot.linearDrive(.6,40);
                stateMachineFlow++;
                break;
        }
    }
}
