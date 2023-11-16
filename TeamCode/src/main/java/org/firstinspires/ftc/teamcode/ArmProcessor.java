package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.hardware.Gamepad;


public class ArmProcessor {
    public static boolean open;
    static {
        open = false;
    }

    public void ProcessGamepad(Gamepad manager) {
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
