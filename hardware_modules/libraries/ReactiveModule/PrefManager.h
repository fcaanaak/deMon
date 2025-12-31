#ifndef PREF_MANAGER_H
#define PREF_MANAGER_H

#include <Preferences.h>

class PrefManager{


 private:

  Preferences internalPrefObject;

  static constexpr const char* wifiDatabaseName = "wifiDatabase";
  static constexpr const char* systemDatabaseName = "systemDatabase";

  static constexpr const char* serverIPKey = "serverIP";
  
  static const bool readWriteMode = false;
  static const bool readMode = true;


  void addString(const char* nameSpace, const char* key, const char* value);
  
 public:


  void addServerIP(const char* serverIP);
  void addWiFiNetwork(const char* ssid, const char* password);

  
};


#endif
