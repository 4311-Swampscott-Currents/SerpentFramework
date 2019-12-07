#include <frc/SampleRobot.h>
#include <frc/Timer.h>

namespace serpentframework {
    //Provides a Robot class built to update as fast as possible, without waiting for any DriverStation input.
    class FastRobot : public frc::SampleRobot {
        public:
            void RobotInit() override;
            void Autonomous() override;
            void OperatorControl() override;
            void Test() override;
            void Disabled() override;

            virtual void robotStart() {}
            virtual void robotUpdate() {}
            virtual void autonomousStart() {}
            virtual void autonomousUpdate() {}
            virtual void autonomousEnd() {}
            virtual void teleopStart() {}
            virtual void teleopUpdate() {}
            virtual void teleopEnd() {}
            virtual void testStart() {}
            virtual void testUpdate() {}
            virtual void testEnd() {}
            virtual void disabledStart() {}
            virtual void disabledUpdate() {}
            virtual void disabledEnd() {}
            
            double getMatchTime();
            double getRobotTime();
            double getTimeDelta();

        private:
            frc::Timer* matchTimer;
            frc::Timer* robotTimer;
            double timeDelta;
            double lastUpdateTime;

            void updateTimeDelta();
    };
}