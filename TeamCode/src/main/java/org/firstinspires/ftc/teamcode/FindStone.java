package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class FindStone {

    ColorSensor lColorSensor;
    ColorSensor rColorSensor;

    int red;
    int green;
    int blue;

    int fred;
    int fgreen;
    int fblue;

    int bred;
    int bgreen;
    int bblue;

    HardwareMap hwMap           = null;

    public FindStone(){

    }

    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        lColorSensor = hwMap.get(ColorSensor.class,"lColor");
        rColorSensor = hwMap.get(ColorSensor.class,"rColor");

        //lColorSensor.enableLed(false);
        //rColorSensor.enableLed(false);
    }

    public void lColorLED(boolean onOff){
        if (onOff){lColorSensor.enableLed(true);
        }else if (!onOff){lColorSensor.enableLed(false);}
    }
    public void rColorLED(boolean onOff){
        if (onOff){rColorSensor.enableLed(true);
        }else if (!onOff){rColorSensor.enableLed(false);}
    }
    public StoneID isSkyStone(StoneID sensorSide) {
        if (sensorSide == StoneID.LEFT){
            lColorSensor.enableLed(true);
            red   = lColorSensor.red();
            green = lColorSensor.green();
            blue  = lColorSensor.blue();
        }else if (sensorSide == StoneID.RIGHT){
            rColorSensor.enableLed(true);
            red   = rColorSensor.red();
            green = rColorSensor.green();
            blue  = rColorSensor.blue();
        }

        if (red < 10 && green < 10) {
            lColorSensor.enableLed(false);
            rColorSensor.enableLed(false);
            return StoneID.SKYSTONE;
        }else if (red > 12 && green > 12) {
            lColorSensor.enableLed(false);
            rColorSensor.enableLed(false);
            return StoneID.STONE;
        }else {
            lColorSensor.enableLed(false);
            rColorSensor.enableLed(false);
            return StoneID.UNKNOWN;
        }
    }
    public StoneID SkystoneTest() {
        lColorSensor.enableLed(true);
        rColorSensor.enableLed(true);
        fgreen = lColorSensor.green();
        bgreen = rColorSensor.green();

        if (Math.abs(fgreen - bgreen) > 50){
            if (fgreen < bgreen){
                return StoneID.ONE;
            }else if (fgreen > bgreen){
                return StoneID.TWO;
            }
        }else if (Math.abs(fgreen - bgreen) < 50){
            return StoneID.THREE;
        }
        return StoneID.UNKNOWN;
    }
    public int lGetRedVal(){
        return lColorSensor.red();
    }
    public int lGetGreenVal(){
        return lColorSensor.green();
    }
    public int lGetBlueVal(){
        return lColorSensor.blue();
    }
    public int rGetRedVal(){
        return rColorSensor.red();
    }
    public int rGetGreenVal(){
        return rColorSensor.green();
    }
    public int rGetBlueVal(){
        return rColorSensor.blue();
    }
}
