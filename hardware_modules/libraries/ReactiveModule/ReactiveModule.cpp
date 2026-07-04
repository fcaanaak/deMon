#include "ReactiveModule.h"

#define READ true
#define READ_WRITE false
#define DEFAULT_CONNECTION_CHECK_TIME 5000
#define MILLIS_TO_SECONDS 1000

#define DATE_STRING_LENGTH 37


void ReactiveModule::setThreshold(float newThreshold){
  
  threshold = newThreshold;

}

void ReactiveModule::setIntervalMillis(unsigned long newInterval){

  intervalMillis = newInterval;

}

void ReactiveModule::setup(){

  LEDManager::setupLED();
  httpClient.setup();
  wifi.setup();
  dateTime.setup();
  
}


bool ReactiveModule::checkTimer(unsigned long detectTimeMillis){
  
  if (!timerRunning){
    cycleStartTime = millis();
    timerRunning = true;
    
  }

  if (millis() >= (cycleStartTime + detectTimeMillis)){
    timerRunning = false;
    return true;
  }

  return false;
  
}

String ReactiveModule::generateJSONReport(char* name){

  char output[256];

  JsonDocument doc;

  doc["deviceName"] = name;
  doc["detectionDateTime"] = dateTime.getDateTime();

  serializeJson(doc,output);

  return String(output);
}

void ReactiveModule::mainloop(){
  

  if (checkTimer(intervalMillis) && WiFi.status() == WL_CONNECTED){
    
    if (detectExternalEvent()){
      
      LEDManager::setLED(0,0,255);
      
      dateTime.loadDateTime();
      
      String report = generateJSONReport("placeholder-name");
      
      httpClient.sendReport(report);
      
      inactivityCounter = 0;
      
    } else {
      
      LEDManager::disableLED();
      inactivityCounter++;
      
    }
    
  }

}
