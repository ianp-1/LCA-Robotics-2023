package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.ftccommon.internal.manualcontrol.responses.ParentHub;


public class ArmProcessor {
    public static double power = 0.825;

    private DcMotor arm = null;


    public ArmProcessor(DcMotor clawMotor) {
        arm = clawMotor;
    }

    public void ProcessGamepad(Gamepad manager) {
        if (manager.left_bumper) {
            arm.setPower(-power);
        } else {
            if (manager.right_bumper) {
                arm.setPower(power);
            } else {
                arm.setPower(0);
            }
        }
    }
}

//to transfer code, set config to "TeamCode" plug a usb from the computer into Control Hub (not Expansion Hub)