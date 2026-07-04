#ifndef HTTP_CLIENT_MANAGER_H
#define HTTP_CLIENT_MANAGER_H

#include <HTTPClient.h>
#include "PrefManager.h"

class HTTPClientManager{

private:

    // Fields
    HTTPClient client;
    PrefManager pref;

    const String rootPath = "/";
    const String reportsPath = rootPath + "reports";

    const String serverPort = "8080";
  
    static const byte maxIPLength = 15;

    String serverIP;

    // Methods
    void postJSON(String serializedJSON);
    void sendPost(String query);
    void endConnection();
    void startReportsConnection();
    
public:

    void setup();
    void sendReport(String serializedReport);
  
};




#endif
