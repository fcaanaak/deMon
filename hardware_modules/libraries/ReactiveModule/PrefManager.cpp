#include "PrefManager.h"


void PrefManager::addString(const char* nameSpace,const char* key, const char* value){

  internalPrefObject.begin(nameSpace,readWriteMode);
  internalPrefObject.putString(key,value);
  internalPrefObject.end();
  
}

void PrefManager::addServerIP(const char* serverIP){
    
  addString(systemDatabaseName,serverIPKey,serverIP);
    
}

void PrefManager::addWiFiNetwork(const char* ssid, const char* password){

  addString(wifiDatabaseName,ssid,password);
  
}

void PrefManager::beginWiFiDatabase(bool mode){

  internalPrefObject.begin(wifiDatabaseName, mode);
  
}

void PrefManager::beginSystemDatabase(bool mode){

  internalPrefObject.begin(systemDatabaseName, mode);

}

String PrefManager::getString(const char* key, String failValue){

  return internalPrefObject.getString(key, failValue);

}

String PrefManager::getServerIP(){

  beginSystemDatabase(readMode);
  String serverIP = getString(serverIPKey);
  end();

  return serverIP;
}

void PrefManager::end(){

  internalPrefObject.end();
  
}


