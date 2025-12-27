#ifndef REACTIVE_MODULE_H
#define REACTIVE_MODULE_H

#include "Arduino.h"
#include <Preferences.h>
#include "WiFiManager.h"
#include "LEDManager.h"
#include "WiFiProvManager.h"
#include "time.h"


#define HOUR_LENGTH 3
#define MINUTE_LENGTH 3
#define SECOND_LENGTH 3
#define DAY_LENGTH 3
#define WEEKDAY_LENGTH 10

class ReactiveModule{

protected:

  /* Class fields */
  Preferences modulePrefs;
  
  /**Wi-Fi related fields **/
  WiFiManager wifi = WiFiManager(modulePrefs);  
  
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
  // WiFi connection methods
  // Abstract (virtual methods for child classes)

  // Timing related fields
  unsigned long intervalMillis = 200;
  bool timerRunning = false;
  unsigned long cycleStartTime = 0;
  
  bool checkTimer(unsigned long detectTimeMillis);
  
  virtual bool detectExternalEvent() = 0;
  float threshold;
  
  void generateJSONReport();
  void sendReport();

  // Date time retrieval methods
  void getDateTime();
  void setupDateTime();
  char dateTime[37];
  
  // A method purely to run one-time code (mainly for Preferences lib)
  void testMethod();

  
public:
  
  virtual void setup();
  void setThreshold(float newThreshold);
  void setIntervalMillis(unsigned long newInterval);
  void mainloop();

};

#endif
