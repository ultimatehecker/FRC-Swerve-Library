<br />
<h1>
    <p align="center">
        <!-- <img src="https://github.com/user-attachments/assets/72e32a4f-a451-42e7-9f9b-95c1e4606282" alt="Logo" width="140" height="110"> -->
        <br>FRC Swerve Library
    </h1>
<p align="center">Python3 script to transfer files in Google Colab 10-50x faster. <br /> </p>
</p>

<p align="center">
  <a href="#about-the-project">About The Project</a> •
  <a href="#installation">Installation</a> •
  <a href="#configuration">Configuration</a> •
  <a href="#credits">Credits</a>
</p>  

<p align="center">
![screenshot](https://github.com/user-attachments/assets/72e32a4f-a451-42e7-9f9b-95c1e4606282)
</p>  

## About The Project
A basic swerve library that Minotaur 1369 used during the 2024 season, adapted so that all teams can use it regardless of hardware. Although this 
supports any type of swerve modules, it was specifically created for the MK3 and MK4 series swerve modules. Support TalonFX controllers with CANCoders
as well as NEOs with CANCoders, but the platform is being expanded for all kinds of modules with different hardware configurations.

## Installation
Installation is fairly straight forward, since all of the vendoerdeps have been loaded online, there is nothing needed to be downloaded on your end, all
you need to have the latest version of WPILib installed, which as of right now is build 2024.3.2. Once install, simplify clone the repository
```sh
git clone https://github.com/ultimatehecker/FRC-Swerve-Library
```

## Configuration
Although this library makes is easier to setup, it is still a WIP, and updates will be added to furthermore improve the ease of use. There are some important
settings that you will need to change. If there are any issues, please open a GitHub issue and will address the problem as soon as possible. The settings changes
needed to make will be located in `src/main/java/frc/robot/utilities/constants/Constants.java` under the `ModuleConstants` and `SwerveConstants` classes.
- `steeringkP, steeringkI, steeringkD`: These will need to be optimized for your robot, as every robot will have a different weight distribution and center of gravity
- `drivekP, drivekI, drivekD`: These will also need to be optimized for your robot, as different gearing setups and modules will be effected differently
- `driveKS, driveKV, driveKA`: For the MK3 and MK4 modules, these constants are tuned fairly accurately, but for other these may need to be tuned
- `FrontLeftModule, FrontRightModule, BackLeftModule, BackRightModule`: The CAN ID's of your hardware, need to be changed based on values in REV Hardware Client or Pheonix Tuner X
- `SwerveHardware`: Change to what motors you are using on your modules
- `ModuleGearing`: Change to the gear ration currently on your modules (contains motor inversions that may not be totally accurate)
- `TrackWidth, WheelBase`: Change these to the length between the wheels of your modules center to center

## Credits
- Credit to The Yellow Haze (9627) for providing feedback while testing