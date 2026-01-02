#ifndef PREF_MANAGER_H
#define PREF_MANAGER_H

#include <Preferences.h>

class PrefManager{
  /**
   * A modsec specific wrapper around Preferences
   */

 private:

  Preferences internalPrefObject;

  static constexpr const char* wifiDatabaseName = "wifiDatabase";
  static constexpr const char* systemDatabaseName = "systemDatabase";

  static constexpr const char* serverIPKey = "serverIP";
  

  void addString(const char* nameSpace, const char* key, const char* value);
  
 public:

  static const bool readWriteMode = false;
  static const bool readMode = true;

  void addServerIP(const char* serverIP);
  void addWiFiNetwork(const char* ssid, const char* password);

  void beginWiFiDatabase(bool mode);
  void beginSystemDatabase(bool mode);
  
  String getString(const char* key, String failValue=String());
  String getServerIP();
  
  void end();
  
};


#endif
