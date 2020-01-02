package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Placing {

    //This class will contain the claw servos (wrist and grip) and the plate hook servos
    //Methods will include an open/close grip and hooks, rotate wrist

    public Servo clawGrip   = null;
    public Servo clawWrist  = null;
    public Servo clawTurn   = null;

    public Servo rPlateHook = null;
    public Servo lPlateHook = null;

    HardwareMap hwMap       = null;
    private ElapsedTime period  = new ElapsedTime();
    double runtime = 0;

    public Placing(){
    }

    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and initialize ALL installed servos
        clawGrip = hwMap.get(Servo.class, "grip_servo");
        clawWrist = hwMap.get(Servo.class, "wrist_servo");
        clawTurn = hwMap.get(Servo.class, "turn_servo");

        rPlateHook = hwMap.get(Servo.class, "right_hook");
        lPlateHook = hwMap.get(Servo.class, "left_hook");

        clawGrip.setPosition(.3);
    }

    //All servo values are subject to change

    public void setClawGrip(ServoPosition position){
        //lower bar to hold brick
        if (position == ServoPosition.DOWN){
            clawGrip.setPosition(.6);
            runtime = period.time();
            while (.6 > period.time() - runtime);
        }
        //raise bar to release brick
        if (position == ServoPosition.UP){
            clawGrip.setPosition(.25);
            runtime = period.time();
            while (.6 > period.time() - runtime);
        }
    }
    public void setClawWrist(ServoPosition position){
        //raise gripper to clear lift
        if (position == ServoPosition.UP){
            clawWrist.setPosition(1);
            runtime = period.time();
            while (.6 > period.time() - runtime);
        }
        //lower gripper
        if (position == ServoPosition.DOWN){
            clawWrist.setPosition(0);
            runtime = period.time();
            while (.6 > period.time() - runtime);
        }
    }
    public void setClawTurn(ServoPosition position){
        //turn gripper over lift to get into placing position
        if (position == ServoPosition.TURN_OUT){
            clawTurn.setPosition(1);
            runtime = period.time();
            while (.6 > period.time() - runtime);
        }
        //turn gripper over lift to go back to collection position
        if (position == ServoPosition.TURN_IN){
            clawTurn.setPosition(0);
            runtime = period.time();
            while (.6 > period.time() - runtime);
        }
    }
    public void setPlateHooks(ServoPosition position){
        //lower hooks onto plate so it can be moved
        if (position == ServoPosition.DOWN){
            rPlateHook.setPosition(1);
            lPlateHook.setPosition(1);
            runtime = period.time();
            while (.6 > period.time() - runtime);
        }
        //raise hooks off plate
        if (position == ServoPosition.UP){
            rPlateHook.setPosition(0);
            lPlateHook.setPosition(0);
            runtime = period.time();
            while (.6 > period.time() - runtime);
        }
    }
}
