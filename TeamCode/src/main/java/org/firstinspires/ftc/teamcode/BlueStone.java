package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
@Disabled
@Autonomous(name="Blue: Stones", group="Test")
public class BlueStone extends OpMode {

    private int stateMachineFlow;
    //the below int controls whether we move the plate (0 = move, 1 = is moved by teammates)
    private int plateIsMoved = 0;
    MecanumDrive robot = new MecanumDrive();
    Intake intake      = new Intake();
    Lift lift          = new Lift();
    Placing placing    = new Placing();
    SkystoneCam cam    = new SkystoneCam();

    double time;
    double distanceToTarget;
    static final double CAMERA_LENS_POS = 0;
    static final double NO_INPUT_DISTANCE = 10;
    static final int NINETY_DEGREES = 90;
    static final double DISTANCE_TO_WALL = 10;
    int angleToTarget = 0;
    int initView = 0;
    private ElapsedTime runtime = new ElapsedTime();
    /***********************************
     *
     * This program starts with the robot's left side(left of the intake) facing the field
     *
     ***********************************/
    @Override
    public void init() {
        msStuckDetectInit = 11500;
        msStuckDetectLoop = 11500;

        robot.init(hardwareMap);
        intake.init(hardwareMap);
        lift.init(hardwareMap);
        placing.init(hardwareMap);
        cam.init(hardwareMap);

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
                //Move towards stones
                robot.sideDrive(.5,5);
                stateMachineFlow++;
                break;
            case 2:
                if (cam.isVisible()) {
                    angleToTarget = Math.round(cam.getHeading()-NINETY_DEGREES);
                    robot.gStatTurn(.5, angleToTarget);
                    distanceToTarget = cam.getXPosition()-CAMERA_LENS_POS;
                }else {
                    angleToTarget = -90;
                    robot.gStatTurn(.5, angleToTarget);
                    distanceToTarget = NO_INPUT_DISTANCE;
                }
                stateMachineFlow++;
                break;
            case 3:
                robot.linearDrive(.5, -distanceToTarget);
                stateMachineFlow++;
                break;
            case 4:
                //square back to the stones
                robot.gStatTurn(.5, -NINETY_DEGREES-angleToTarget);
                stateMachineFlow++;
                break;
            case 5:
                //Move grabber into position
                placing.setClawWrist(ServoPosition.UP);
                placing.setClawTurn(ServoPosition.TURN_OUT);
                placing.setClawWrist(ServoPosition.DOWN);
                stateMachineFlow++;
                break;
            case 6:
                //Back up
                robot.linearDrive(.5,-3);

                stateMachineFlow++;
                break;
            case 7:
                //grab stone and raise it off the floor
                placing.setClawGrip(ServoPosition.DOWN);
                lift.placeLevel(PlaceLevel.TWO);
            case 8:
                //Move away from the other stones
                robot.linearDrive(.5,10);
                stateMachineFlow++;
                break;
            case 9:
                //face camera to picture on the wall
                robot.gStatTurn(.5,-NINETY_DEGREES);
                stateMachineFlow++;
                break;
            case 10:
                //move to picture on wall
                angleToTarget = Math.round(cam.getHeading());
                distanceToTarget = DISTANCE_TO_WALL*Math.tan(angleToTarget);
                robot.linearDrive(.5,distanceToTarget);
                stateMachineFlow++;
                break;
            case 11:
                //square to the picture
                angleToTarget = Math.round(cam.getHeading());
                robot.gStatTurn(.5,angleToTarget);
                stateMachineFlow++;
                break;
            case 12:
                //turn so back is facing the bridge
                robot.gStatTurn(.5,-NINETY_DEGREES);
                robot.gStatTurn(.5,-NINETY_DEGREES);
                stateMachineFlow++;
                break;
            case 13:
                //Move to other side of bridge
                robot.linearDrive(-.5,-60);
                stateMachineFlow++;
                break;
            case 14:
                //move to foundation
                if (plateIsMoved == 0){
                    robot.sideDrive(.5,10);
                }else if (plateIsMoved == 1){
                    robot.sideDrive(.5,-5);
                }
                stateMachineFlow++;
                break;
            case 15:
                //move up to the side of the foundation
                robot.linearDrive(.5,-5);
                stateMachineFlow++;
                break;
            case 16:
                //drop the stone
                lift.placeLevel(PlaceLevel.ONE);
                placing.setClawGrip(ServoPosition.UP);
                lift.placeLevel(PlaceLevel.TWO);
                stateMachineFlow++;
                break;
            case 17:
                if (plateIsMoved == 0){
                    //move to the side of the foundation
                    robot.sideDrive(.5,15);
                }else if (plateIsMoved == 1){
                    //move to the side to be in position to park under the bridge
                    robot.sideDrive(.5,7);
                }
                stateMachineFlow++;
                break;
            case 18:
                if (plateIsMoved ==0){
                    //turn so back is parallel to the plate
                    robot.gStatTurn(.5,NINETY_DEGREES);
                    stateMachineFlow++;
                }else if (plateIsMoved == 1){
                //drive under the bridge and finish
                robot.linearDrive(.5,20);
                stateMachineFlow = 30;
                }
                break;
            case 19:
                //move to the side of the plate and up to it
                robot.sideDrive(.5,7);
                robot.linearDrive(.5,-4);
                stateMachineFlow++;
                break;
            case 20:
                //set hooks and pull plate
                placing.setPlateHooks(ServoPosition.DOWN);
                robot.linearDrive(.5,15);
                stateMachineFlow++;
                break;
            case 21:
                //move out from plate and around
                robot.sideDrive(.5,-7);
                robot.linearDrive(.5,-20);
                robot.sideDrive(.5,7);
                stateMachineFlow++;
                break;
            case 22:
                //push plate
                robot.linearDrive(.5,10);
                stateMachineFlow++;
                break;
            case 23:
                //move under bridge
                robot.linearDrive(.5,-5);
                robot.sideDrive(.5,-20);
                stateMachineFlow++;
                break;
        }
    }
}
