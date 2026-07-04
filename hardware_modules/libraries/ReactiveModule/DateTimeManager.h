#ifndef DATE_TIME_MANAGER_H
#define DATE_TIME_MANAGER_H

#include <time.h>
#include <Arduino.h>

class DateTimeManager{

private:


    const unsigned int dateTimeStringLength = 20;
    const char* templateDateTimeString = "%d-%m-%Y %T";

    const byte failureStringLength = 8;

    String dateTime;

    bool dateTimeFetchFailed = false;
    void processDateTime(struct tm* timeInfo);
  
public:

    void loadDateTime();
    void setup();
    bool checkFailure();
    String getDateTime();
      
};

#endif
