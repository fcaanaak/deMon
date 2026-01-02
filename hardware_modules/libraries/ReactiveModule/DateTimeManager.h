#ifndef DATE_TIME_MANAGER_H
#define DATE_TIME_MANAGER_H

#include <time.h>
#include <Arduino.h>

class DateTimeManager{

 private:

  const char* yearFormatter = "%Y";
  const char* monthFormatter = "%m" ;
  const char* dayFormatter = "%d";
  const char* hourFormatter = "%H";
  const char* minuteFormatter = "%M";
  const char* secondFormatter = "%S";
  

  const byte nonYearMaxStringLength = 3; // 2 + 1 null terminator
  const byte yearMaxStringLength = 5;
  const byte failureStringLength = 8;
  
  unsigned int year = 0;
  unsigned int month = 0;
  unsigned int day = 0;
  unsigned int hour = 0;
  unsigned int minute = 0;
  unsigned int second = 0;

  bool dateTimeFetchFailed = false;
  
  
 public:

  void loadDateTime();
  void setup();

  unsigned int formatDateFieldToUInt(const char* formatter, unsigned int maxLength, struct tm* timeInfo);

  unsigned int getYear();
  unsigned int getMonth();
  unsigned int getDay();
  unsigned int getHour();
  unsigned int getMinute();
  unsigned int getSecond();

  bool checkFailure();

      
};

#endif
