/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/*
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When a selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@TeleOp
//@Disabled
public class FTC2023OpMode extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotor clawMotor = null;
    private ArmProcessor clawDrive = null;
    private static final int APPLIED_POWER = 1000000;
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize motors.
        initializeMotors();
        resetMotorEncoders();

        // Wait for start
        waitForStart();
        runtime.reset();
        boolean active = opModeIsActive();
        // Main loop
        while (active) {
            telemetry.addData("Active", active);
            processGamepadInput();
            updateTelemetry();
            active = opModeIsActive();
        }
    }
    private void initializeMotors() {
        leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front_drive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "left_back_drive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");
        clawMotor = hardwareMap.get(DcMotor.class, "claw_motor");

        clawDrive = new ArmProcessor(clawMotor);

        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

    }

    private void processGamepadInput() {
        double leftY = gamepad1.left_stick_y;
        double leftX = gamepad1.left_stick_x;
        double rightY = gamepad1.right_stick_y;
        double rightX = gamepad1.right_stick_x;

        clawDrive.ProcessGamepad(gamepad2);

        telemetry.addData("After processor", "Initialized");
        telemetry.addData("LeftStickMotion", "x: %.2f, y: %.2f", leftX, leftY);
        telemetry.addData("RightStickMotion", "x: %.2f, y: %.2f", rightX, rightY);

        boolean lBumper = gamepad1.left_bumper;
        boolean rBumper = gamepad1.right_bumper;

        telemetry.addData("Left Bumper", lBumper ? "Pressed" : "Not Pressed");
        telemetry.addData("Right Bumper", rBumper ? "Pressed" : "Not Pressed");

        double set1Power = calculatePower(leftX, leftY);
        double set2Power = calculatePower(-leftX, leftY);
        setAllMotors(set1Power, set2Power);
        /*
        if (rBumper) {
            setBumperPower(APPLIED_POWER, -APPLIED_POWER);
        } else if (lBumper) {
            setBumperPower(-APPLIED_POWER, APPLIED_POWER);
        } else {
            setRegularPower(set1Power, set2Power);
        }
         */
    }

    private void setAllMotors(double leftPower, double rightPower) {
        telemetry.addData("In setAllMotors", "In setAllMotors");
        leftFrontDrive.setPower(leftPower);
        leftBackDrive.setPower(leftPower);
        rightFrontDrive.setPower(rightPower);
        rightBackDrive.setPower(rightPower);
    }

    private double calculatePower(double x, double y) {
        return (x + y) / 2;
    }

    private void setBumperPower(int leftTarget, int rightTarget) {
        leftFrontDrive.setTargetPosition(leftTarget);
        leftBackDrive.setTargetPosition(leftTarget);
        rightFrontDrive.setTargetPosition(rightTarget);
        rightBackDrive.setTargetPosition(rightTarget);
    }

    private void setRegularPower(double set1Power, double set2Power) {
        leftFrontDrive.setTargetPosition((int) (set2Power * APPLIED_POWER));
        leftBackDrive.setTargetPosition((int) (set1Power * APPLIED_POWER));
        rightFrontDrive.setTargetPosition((int) (set1Power * APPLIED_POWER));
        rightBackDrive.setTargetPosition((int) (set2Power * APPLIED_POWER));
    }

    private void resetMotorEncoders() {
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private void updateTelemetry() {
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftFrontDrive.getPower(), rightFrontDrive.getPower());
        telemetry.update();
    }
}

