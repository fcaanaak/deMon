#ifndef WIFI_MANAGER_H
#define WIFI_MANAGER_H

#include <WiFi.h>
#include <Preferences.h>
#include "LEDManager.h"
#include "WiFiProvManager.h"

enum State{
  CONNECTED,
  DISCONNECTED
};

class WiFiManager{

 private:

  static const unsigned short autoReconnectSeconds = 10;

  Preferences prefObject;
  WiFiProvManager wifiProv;

  static bool connectToWiFi(const char* ssid, const char* password, unsigned int waitSecs);
  static bool reconnectToWiFi(unsigned int waitSecs);
  static bool waitForConnection(unsigned int waitSecs);
  static bool reconnectWithRetries(unsigned int retries, unsigned int waitSecs); 

  String scanStoredNetworks();
  bool autoReconnect();
  
  void registerWiFiEvents();
  void registerWiFiConnected();
  void registerWiFiDisconnected();
  void resetToSTA();


  static void onWiFiReconnect(WiFiEvent_t event, WiFiEventInfo_t info);
  static void onWiFiDisconnect(WiFiEvent_t event, WiFiEventInfo_t info);

  static State state;

public:
  
  WiFiManager(Preferences prefObject);
  
  void setup();
  
};

#endif
