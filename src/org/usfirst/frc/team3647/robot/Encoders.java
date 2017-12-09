package org.usfirst.frc.team3647.robot;

import edu.wpi.first.wpilibj.Encoder;

public class Encoders 
{
	public Encoder leftEncoder = new Encoder(0,1, false, Encoder.EncodingType.k4X);
	public Encoder rightEncoder = new Encoder(4,5, false, Encoder.EncodingType.k4X);
	
	double getRightEncoder()
	{
		return rightEncoder.get();
	}
	double getLeftEncoder()
	{
		return (-1 * leftEncoder.get());
	}
	void resetEncoders()
	{
		rightEncoder.reset();
		leftEncoder.reset();
	}
}
