#include "DateTimeManager.h"


void DateTimeManager::loadDateTime(){
  
  struct tm timeinfo;

  if (!getLocalTime(&timeinfo)){
    dateTimeFetchFailed = true;
  } else{

    if (dateTimeFetchFailed){ dateTimeFetchFailed = false; }

    processDateTime(&timeinfo);
    
  }
  
}


void DateTimeManager::setup(){

  const char* ntpServer = "pool.ntp.org";
  const long gmOffset_sec = -8*3600;
  const int daylightOffset_sec = 3600;
  
  configTime(gmOffset_sec,daylightOffset_sec,ntpServer);

}


bool DateTimeManager::checkFailure(){ return dateTimeFetchFailed;  }


void DateTimeManager::processDateTime(struct tm* timeInfo){

    char temp[dateTimeStringLength];

    strftime(temp, dateTimeStringLength, templateDateTimeString ,timeInfo);

    dateTime = String(temp);
}


String DateTimeManager::getDateTime(){
    return dateTime;
}
