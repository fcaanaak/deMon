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
