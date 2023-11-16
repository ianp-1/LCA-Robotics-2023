package org.firstinspires.ftc.teamcode;

import static org.mockito.Mockito.*;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.AdditionalMatchers.*;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ArmProcessorTest {
    @Test
    public void testArmProcessorSimple() {
        DcMotor mockShoulderMotor = mock(DcMotor.class);
        DcMotor mockWristMotor = mock(DcMotor.class);
        Gamepad mockGamepad = mock(Gamepad.class);
        mockGamepad.dpad_up = true;

        ArmProcessor processor = new ArmProcessor(mockShoulderMotor, mockWristMotor);
        processor.ProcessGamepad(mockGamepad);
        verify(mockShoulderMotor).setPower(gt(900.0d));
    }
}
