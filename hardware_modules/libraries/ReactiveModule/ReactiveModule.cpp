
#include "Arduino.h"
#include "ReactiveModule.h"

#define WIFI_DATABASE "wifiDatabase"
#define READ true
#define READ_WRITE false
#define DEFAULT_CONNECTION_CHECK_TIME 5000
#define MILLIS_TO_SECONDS 1000

#define DATE_STRING_LENGTH 37

void ReactiveModule::testMethod(){
  
  modulePrefs.begin(WIFI_DATABASE,READ_WRITE);


  modulePrefs.clear();

  modulePrefs.end();
  
}


void ReactiveModule::getDateTime(){

  struct tm timeinfo;

  if (!getLocalTime(&timeinfo)){
    strlcpy(dateTime,"FAILURE",DATE_STRING_LENGTH);
  } else{
    
    strftime(dateTime,DATE_STRING_LENGTH,"%A %B %d %Y %H:%M:%S",&timeinfo);

  }  

}


void ReactiveModule::setupDateTime(){

  const char* ntpServer = "pool.ntp.org";
  const long gmOffset_sec = -8*3600;
  const int daylightOffset_sec = 3600;
  
  configTime(gmOffset_sec,daylightOffset_sec,ntpServer);
  

}


void ReactiveModule::setThreshold(float newThreshold){
  
  threshold = newThreshold;

}

void ReactiveModule::setIntervalMillis(unsigned long newInterval){

  intervalMillis = newInterval;

}

void ReactiveModule::setup(){

  LEDManager::setupLED();
  wifi.setup();
  setupDateTime();
  
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

void ReactiveModule::mainloop(){
  

  if (checkTimer(intervalMillis) && WiFi.status() == WL_CONNECTED){
    
    if (detectExternalEvent()){
      
      LEDManager::setLED(0,0,255);
      getDateTime();
      inactivityCounter = 0;
      
    } else {
      
      LEDManager::disableLED();
      inactivityCounter++;
      
    }
    
  }

}
