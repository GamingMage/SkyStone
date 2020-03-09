package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Blue: Color Test", group="Color")
public class BlueColorTest extends OpMode{

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

        color.lfColorLED(true);
        color.lbColorLED(true);

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
                robot.sideAllDrive(.45,-30);
                stateMachineFlow++;
                break;
            case 2:
                stonePosition = color.leftSkystoneTest();
                if (stonePosition == StoneID.ONE){
                    robot.linearDrive(.6,5);
                    telemetry.addData("Stone Position","One");
                }else if (stonePosition == StoneID.TWO){
                    robot.linearDrive(.6,12);
                    telemetry.addData("Stone Position","Two");
                }else if (stonePosition == StoneID.THREE){
                    robot.linearDrive(.6,18);
                    telemetry.addData("Stone Position","Three");
                }
                telemetry.addData("front green",color.lfgreen);
                telemetry.addData("back green",color.lbgreen);
                telemetry.update();
                color.lfColorLED(false);
                color.lbColorLED(false);
                stateMachineFlow++;
                break;
            case 3:
                robot.sideAllDrive(.45,-18);
                stateMachineFlow++;
                break;
            case 4:
                intake.intakeControl(IntakeDirection.IN);
                //intake.leftIntake.setPower(1);
                //intake.rightIntake.setPower(1);
                telemetry.addData("Starting Intake","True");
                telemetry.update();
                //time = runtime.time();
                //while (1 > runtime.time() - time);
                lift.placeLevel(PlaceLevel.THREE);
                stateMachineFlow++;
                break;
            case 5:
                robot.linearDrive(.4,6);
                stateMachineFlow++;
                break;
            case 6:
                intake.intakeControl(IntakeDirection.OFF);
                //intake.leftIntake.setPower(0);
                //intake.rightIntake.setPower(0);
                lift.liftDown(.6);
                stateMachineFlow++;
                break;
            case 7:
                robot.sideAllDrive(.45,18);
                stateMachineFlow++;
                break;
            case 8:
                if (stonePosition == StoneID.ONE){
                    robot.linearDrive(.5,-71);
                }else if (stonePosition == StoneID.TWO){
                    robot.linearDrive(.5,-80);
                }else if (stonePosition == StoneID.THREE){
                    robot.linearDrive(.5,-86);
                }
                stateMachineFlow++;
                break;
            case 9:
                robot.sideAllDrive(.45,6);
                stateMachineFlow++;
                break;
            case 10:
                robot.gStatTurn(.6,-NINETY_DEGREES);
                stateMachineFlow++;
                break;
            case 11:
                robot.linearDrive(.5,-7);
                stateMachineFlow++;
                break;
            case 12:
                placing.setPlateHooks(ServoPosition.DOWN);
                stateMachineFlow++;
                break;
            case 13:
                //move the plate into the zone
                robot.linearDrive(.4,27);
                stateMachineFlow++;
                break;
            case 14:
                robot.gStatTurn(.6,NINETY_DEGREES);
                stateMachineFlow++;
                break;
            case 15:
                robot.linearDrive(.5,-8);
                stateMachineFlow++;
                break;
            case 16:
                placing.setClawGrip(ServoPosition.DOWN);
                stateMachineFlow++;
                break;
            case 17:
                lift.placeLevel(PlaceLevel.FIVE);
                stateMachineFlow++;
                break;
            case 18:
                placing.armDrive(.5,-700);
                stateMachineFlow++;
                break;
            case 19:
                placing.setClawGrip(ServoPosition.UP);
                stateMachineFlow++;
                break;
            case 20:
                placing.armDrive(.5,0);
                stateMachineFlow++;
                break;
            case 21:
                lift.liftDown(.6);
                stateMachineFlow++;
                break;
            case 22:
                robot.linearDrive(.45,5);
                stateMachineFlow++;
                break;
            case 23:
                robot.sideDrive(.4,8);
                stateMachineFlow++;
                break;
            case 24:
                //move under the bridge
                robot.linearDrive(.6,35);
                stateMachineFlow++;
                break;
        }
    }
}
