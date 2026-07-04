#include "HTTPClientManager.h"

void HTTPClientManager::setup(){

    serverIP = pref.getServerIP();

}

void HTTPClientManager::startReportsConnection(){

    String fullHostName = String(serverIP) + ":" + serverPort;
    String hostAndPathName = "http://" + fullHostName + reportsPath;

    client.begin(hostAndPathName);

}

void HTTPClientManager::endConnection(){
    client.end();
}

void HTTPClientManager::sendPost(String query){
    Serial.println(client.POST(query));
}


void HTTPClientManager::postJSON(String serializedJSON){
    client.addHeader("Content-Type", "application/json");
    client.addHeader("Accept", "application/json");
    sendPost(serializedJSON);
}

void HTTPClientManager::sendReport(String serializedReport){

    startReportsConnection();
    postJSON(serializedReport);
    endConnection();
  
}
