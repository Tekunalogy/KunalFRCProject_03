package org.usfirst.frc.team3647.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3647.robot.Encoders;
import org.usfirst.frc.team3647.robot.Joysticks;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot 
{
	Joysticks controller;
	Encoders enc;
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		controller = new Joysticks();
		enc = new Encoders();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
		enc.resetEncoders();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		switch (autoSelected) {
		case customAuto:
			// Put custom auto code here
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	double leftSpeed;
	double rightSpeed;
	@Override
	public void teleopPeriodic() 
	{
		controller.updateMainController();
		moveForwardBackward(enc.getRightEncoder(), enc.getLeftEncoder());
		Motors.leftTalon.set(leftSpeed);
		Motors.rightTalon.set(rightSpeed);
		System.out.println("Right Encoder: " + enc.getRightEncoder());
		System.out.println("Left Encoder: " + enc.getLeftEncoder());
		System.out.println();
	}
	
	public void moveForwardBackward(double rightenc, double leftenc)
	{
		double joysticky = Joysticks.leftJoySticky; //sets joystick Y value from -1 to 1 to a var called double joysticky

		double difference = Math.abs(rightenc - leftenc); // get the abs value of the difference in encoder values
		if(joysticky != 0) // if joystick is not equal to zero run the code below
		{
			double average = (leftSpeed + rightSpeed) / 2;
			if(difference < 5 || average < 0.3) //if the difference in encoder values is less than 5, the speed is set the same for both motors
			{
				rightSpeed = -joysticky; // right speed is set to negative joystick value
				leftSpeed = joysticky; //left speed is set to positive joystick value
			}
			else if(difference > 5) // difference in encoder values is greater than 5
			{
				if (rightenc < leftenc) //check if the right encoder is less than the left encoder
				{
					//if so
					rightSpeed = -joysticky; // right speed stays the same
					leftSpeed = joysticky - 0.3; //left speed decreased
				}
				else
				{
					//if the right encoder is GREATER than the left encoder
					rightSpeed = -joysticky + 0.3; // right speed is decreased
					leftSpeed = joysticky; //left speed stays the same
				}
			}
		} 
		else // if joystick isn't moving, speed is set to zero
		{
			rightSpeed = 0;
			leftSpeed = 0;
			enc.resetEncoders();
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

