#include "ReactiveModule.h"


void ReactiveModule::setThreshold(float newThreshold){
  
  threshold = newThreshold;

}


void ReactiveModule::setIntervalMillis(unsigned long newInterval){

  detectionIntervalMillis = newInterval;

}


void ReactiveModule::setup(){

  LEDManager::setupLED();
  httpClient.setup();
  wifi.setup();
  dateTime.setup();
  
}


bool ReactiveModule::runTimerInterval(unsigned long detectTimeMillis){
  
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


/**
 * @brief Create a JSON document encoded as a string to represent a report
 * @param name: The name of the device to show in the report
 * @return The string representing a JSON document
 */
String ReactiveModule::generateJSONReport(char* name){

  char output[256];

  JsonDocument doc;

  doc["deviceName"] = name;
  doc["detectionDateTime"] = dateTime.getDateTime();

  serializeJson(doc,output);

  return String(output);
}


void ReactiveModule::mainloop(){

  if (checkTimer(detectionIntervalMillis) && WiFi.status() == WL_CONNECTED){
    
    if (detectExternalEvent()){
      
      LEDManager::setLED(0,0,255);
      
      dateTime.loadDateTime();
      
      String report = generateJSONReport("placeholder-name");
      
      httpClient.sendReport(report);
      
    } else {
      
      LEDManager::disableLED();
      
    }
    
  }

}
