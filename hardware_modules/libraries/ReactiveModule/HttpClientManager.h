#ifndef HTTP_CLIENT_MANAGER_H
#define HTTP_CLIENT_MANAGER_H

#include <HTTPClient.h>
#include "PrefManager.h"

class HttpClientManager{

 private:

  HTTPClient client;
  PrefManager pref;

  const String rootPath = "/";
  const String reportsPath = rootPath + "reports";

  const String serverPort = "8000";
  
  static const byte maxIPLength = 15;

  String serverIP;

 public:

  void setup();
  void sendReport();
  void startReportsConnection();

  void sendPost(String query);
  void endConnection();
  

};




#endif
