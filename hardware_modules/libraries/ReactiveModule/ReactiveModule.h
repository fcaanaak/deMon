#ifndef REACTIVE_MODULE_H
#define REACTIVE_MODULE_H

#include "Arduino.h"
#include "WiFiManager.h"
#include "LEDManager.h"
#include "WiFiProvManager.h"
#include "DateTimeManager.h"
#include <ArduinoJson.h>
#include "HTTPClientManager.h"

class ReactiveModule{

protected:

    // Fields
    WiFiManager wifi;
    HTTPClientManager httpClient;
    DateTimeManager dateTime;
  
    const unsigned short autoReconnectSeconds = 10;

    unsigned long detectionIntervalMillis = 200;
    bool timerRunning = false;
    unsigned long cycleStartTime = 0;

    float threshold;
    
    // Methods
    bool runTimerInterval(unsigned long detectTimeMillis);
  
    virtual bool detectExternalEvent() = 0;
  
    String generateJSONReport(char* name);
    void sendReport();
    
  
public:
  
    virtual void setup();
    void setThreshold(float newThreshold);
    void setDetectionIntervalMillis(unsigned long newInterval);
    void mainloop();

};

#endif
