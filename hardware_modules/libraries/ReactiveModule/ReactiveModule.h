#ifndef REACTIVE_MODULE_H
#define REACTIVE_MODULE_H

#include "Arduino.h"
#include "WiFiManager.h"
#include "LEDManager.h"
#include "WiFiProvManager.h"
#include "DateTimeManager.h"
#include <ArduinoJson.h>
#include "HTTPClientManager.h"

#define HOUR_LENGTH 3
#define MINUTE_LENGTH 3
#define SECOND_LENGTH 3
#define DAY_LENGTH 3
#define WEEKDAY_LENGTH 10


class ReactiveModule{

protected:

  /* Class fields */
  
  /**Wi-Fi related fields **/
  WiFiManager wifi;
  HTTPClientManager httpClient;
  
  enum State{
    NETWORK_RECOVERY,
    MANUAL_SETUP,
    BORED,
    DETECTING,
    UNDECIDED
  };

  
  unsigned short inactivityCounter = 0;
  volatile State currentState = UNDECIDED;
  
  const unsigned short autoReconnectSeconds = 10;

  // Class methods

  // Timing related fields
  unsigned long intervalMillis = 200;
  bool timerRunning = false;
  unsigned long cycleStartTime = 0;
  
  bool checkTimer(unsigned long detectTimeMillis);
  
  virtual bool detectExternalEvent() = 0;
  float threshold;
  
  String generateJSONReport(char* name);
  void sendReport();

  DateTimeManager dateTime;

  
public:
  
  virtual void setup();
  void setThreshold(float newThreshold);
  void setIntervalMillis(unsigned long newInterval);
  void mainloop();

};

#endif
