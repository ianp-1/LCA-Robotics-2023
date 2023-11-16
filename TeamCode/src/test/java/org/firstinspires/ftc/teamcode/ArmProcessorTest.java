package org.firstinspires.ftc.teamcode;

import static org.mockito.Mockito.*;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.AdditionalMatchers.*;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ArmProcessorTest {
    @Test
    public void testArmProcessorDPadUp() {
        DcMotor mockShoulderMotor = mock(DcMotor.class);
        DcMotor mockWristMotor = mock(DcMotor.class);
        Gamepad mockGamepad = mock(Gamepad.class);
        mockGamepad.dpad_up = true;

        ArmProcessor processor = new ArmProcessor(mockShoulderMotor, mockWristMotor);
        processor.ProcessGamepad(mockGamepad);
        verify(mockShoulderMotor).setPower(gt(900d));
    }

    @Test
    public void testArmProcessorDPadDown() {
        DcMotor mockShoulderMotor = mock(DcMotor.class);
        DcMotor mockWristMotor = mock(DcMotor.class);
        Gamepad mockGamepad = mock(Gamepad.class);
        mockGamepad.dpad_down = true;

        ArmProcessor processor = new ArmProcessor(mockShoulderMotor, mockWristMotor);
        processor.ProcessGamepad(mockGamepad);
        verify(mockShoulderMotor).setDirection(DcMotorSimple.Direction.REVERSE);
        verify(mockShoulderMotor).setPower(gt(900d));
    }

    @Test
    public void testArmProcessorDPadNone() {
        DcMotor mockShoulderMotor = mock(DcMotor.class);
        DcMotor mockWristMotor = mock(DcMotor.class);
        Gamepad mockGamepad = mock(Gamepad.class);
        mockGamepad.dpad_up = false;
        mockGamepad.dpad_down = false;

        ArmProcessor processor = new ArmProcessor(mockShoulderMotor, mockWristMotor);
        processor.ProcessGamepad(mockGamepad);
        verify(mockShoulderMotor).setPower(eq(0d));
    }
}
