package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;


public class ArmProcessor {
    private final DcMotor shoulderMotor;

    public ArmProcessor(DcMotor shoulderMotor, DcMotor wristMotor) {
        this.shoulderMotor = shoulderMotor;
    }

    public static boolean open;
    static {
        open = false;
    }

    public void ProcessGamepad(Gamepad manager) {
        if (manager.dpad_up) {
            shoulderMotor.setPower(5.0);
        }
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
