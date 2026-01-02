#include "HttpClientManager.h"

void HttpClientManager::setup(){

  serverIP = pref.getServerIP();

}

void HttpClientManager::startReportsConnection(){

  String fullHostName = String(serverIP) + ":" + serverPort;

  String hostAndPathName = fullHostName + reportsPath;

  client.begin(serverIP,8000,reportsPath);

}

void HttpClientManager::endConnection(){

  client.end();
  
}

void HttpClientManager::sendPost(String query){
  
  client.POST(query);
}





