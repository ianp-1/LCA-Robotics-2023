package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;


public class ArmProcessor {
    public ArmProcessor ( DcMotor shoulderMotor, DcMotor motor) {
        this.wristMotor = motor;
        this.shoulderMotor = shoulderMotor;
    }
    public static boolean open;
    private final DcMotor wristMotor;
    private final DcMotor shoulderMotor;
    static {
        open = false;
    }


    public void ProcessGamepad(Gamepad manager) {
        if (manager.dpad_up) {
            shoulderMotor.setPower(950);
        }else
        if (manager.a) {
            open = !open;
            if (open) {
                //rotate clamps toward each other if they're already open
            } else {
                //rotate clamps away from each other if they're already closed
            }
        }
    }
}
