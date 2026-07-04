#ifndef MOTION_MODULE_H
#define MOTION_MODULE_H
#include <ReactiveModule.h>

class MotionModule : public ReactiveModule {

 private:

  static const unsigned short sensorTriggerPin = 25;
  static const unsigned short sensorEchoPin = 26;
  
  bool detectExternalEvent() override;
  float getMeasuredDistance();
  unsigned long detectionDuration;
  float distanceCM;

  unsigned short calibrationTimeMillis = 3000;
  unsigned short calibrationDelayMillis = 20;
  unsigned short numMeasurements = calibrationTimeMillis/calibrationDelayMillis;

  unsigned short toleranceCm = 2;

  // setup methods
  void setupSensor();
  void setupThreshold();
  
 public:
  void setup() override;
};


#endif
