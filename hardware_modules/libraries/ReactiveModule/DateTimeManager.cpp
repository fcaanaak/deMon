#include "DateTimeManager.h"


void DateTimeManager::loadDateTime(){
  
  struct tm timeinfo;

  if (!getLocalTime(&timeinfo)){
    dateTimeFetchFailed = true;
      
  } else{

    if (dateTimeFetchFailed){ dateTimeFetchFailed = false; }
    
    year = formatDateFieldToUInt(yearFormatter, yearMaxStringLength,&timeinfo);
    month = formatDateFieldToUInt(monthFormatter, nonYearMaxStringLength,&timeinfo);
    day = formatDateFieldToUInt(dayFormatter, nonYearMaxStringLength,&timeinfo);
    hour = formatDateFieldToUInt(hourFormatter, nonYearMaxStringLength,&timeinfo);
    minute = formatDateFieldToUInt(minuteFormatter, nonYearMaxStringLength,&timeinfo);
    second = formatDateFieldToUInt(secondFormatter, nonYearMaxStringLength,&timeinfo);
    
  }
  
}

void DateTimeManager::setup(){

  const char* ntpServer = "pool.ntp.org";
  const long gmOffset_sec = -8*3600;
  const int daylightOffset_sec = 3600;
  
  configTime(gmOffset_sec,daylightOffset_sec,ntpServer);

}

unsigned int DateTimeManager::formatDateFieldToUInt(const char* formatter, unsigned int maxLength, struct tm* timeInfo){

  char temp[maxLength];

  strftime(temp,maxLength,formatter,timeInfo);

  return atoi(temp);

}

unsigned int DateTimeManager::getYear(){ return year; }
unsigned int DateTimeManager::getMonth(){ return month; }
unsigned int DateTimeManager::getDay(){ return day; }
unsigned int DateTimeManager::getHour(){ return hour; }
unsigned int DateTimeManager::getMinute(){ return minute; }
unsigned int DateTimeManager::getSecond(){ return second; }

bool DateTimeManager::checkFailure(){ return dateTimeFetchFailed;  }
