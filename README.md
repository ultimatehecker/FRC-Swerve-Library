# FRC Basic Swerve Library
A basic swerve library that Minotaur 1369 used during the 2024 season. Although this supports all type's of swerve pods, it was specifically created
for the MK3 Swerve Modules, but with some adjustments it will work for any other module expects those with unsupported hardware. I do plan on adding
support for other frameworks, but for now, this will have to do

## Installation
Installation is fairly straight forward, since all of the vendoerdeps have been loaded online, there is nothing needed to be downloaded on your end, all
you need to do is to clone the repository, and make sure that your WPILib is on the latest version, which is version 2024.3.2

## Configuration
Although this library makes is easier to setup, it isn't as simple as using YAGSL beacuse I don't have the hardware to test all of them, nor do I have
the time to do it at this moment. This library doesn't have any good starter values, execpt for the NEO motor characterization values. Everything else
such as PID will have to be tuned manually for the best results

Everything to be configured will be done in Constants.java, located in /utilites/constants/Constants. Below is a list of everything needed to be 
configured in order to get it up and running. For some of the steps below, it might be useful to log some values using SmartDashboard in order to 
configure them, some should already be there for starters.

Inside ModuleConstants, you will see four class's, one for each module, which has the IDs for the motors, CANCoders, and then the encoder offset. These
will have to be set to what you have setup on your end. 

For your Neos, in REV Hardware Client, configure each motor to be a Brushless motor with a unique ID between 1-63.Then make sure the numbers match 
everything insde these four class's  for each module.

For your CANCoder's, you need to open Pheonix Tuner X and make sure each one has a unique ID that is not the same as the other Neo's. You will
also need to record the absolute encoder offset, which is the position the encoder reads when the wheel is straight. Since these encoders use a 
polar magnet in order to sense its position, it will not be the same for all four encoders. Once those values are recorded, place them in their
respective spots. Also with these CANCoder's, do note that you can't flash these devices through code, so you will have to flash them after the 
code has been deployed to the robot.

Inside the SwerveConstants class, you will need to configure your TrackWidth and WheelBase to be the distance from center of each one of the wheels. Since
this can be a bit tricky, for beta testing it is alright to leave it as the width and length of your drivetrain, but it will need to be adjusted in order
to get the best performance

The DriveGearing and SteeringGearing will need to be edited based on the ratio of your swerve module. If you are using Swerve Drive Specialities Modules, the
steering ratio for the MK3, MK4, MK4i and MK4N are all 12.8:1, so you will not have to worry about this. Although you will need to worry about the Drive Ratio.
On the MK3 Modules, there were only two ratios, Standard (8.16:1) and Fast (6.86:1). After the MK3s, the ratios changed, so you will need to input the ratio
that you have on your modules.

The PhysicalMaxTranslationSpeed and PhysicalMaxAngularVelocity is the next thing to figure out, which will require some math. These will need to be calculated 
based on the gear ratio that your have on your drivetrain. The other two will be set based on preference, but can't be set to high as it will fail to speed up.

At the end of the SwerveConstants class, there will we a whole bunch of inversions. Usually these do not have to be adjusted, but if your motors are freaking out,
they might need to be changed, reference this page for help: https://yagsl.gitbook.io/yagsl/configuring-yagsl/when-to-invert

Now you should be moving, but it may not be accurate. Tuning will require turning both PID values for drive and steering seen in ModuleConstants