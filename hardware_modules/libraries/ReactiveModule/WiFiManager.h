#ifndef WIFI_MANAGER_H
#define WIFI_MANAGER_H

#include <WiFi.h>
#include "PrefManager.h"
#include "LEDManager.h"
#include "WiFiProvManager.h"

class WiFiManager{

private:

    // Fields
    static const unsigned short autoReconnectSeconds = 10;

    PrefManager pref;
    WiFiProvManager wifiProv;

    // Methods
    static bool connectToWiFi(const char* ssid, const char* password, unsigned int waitSecs);
    
    static bool reconnectToWiFi(unsigned int waitSecs);
    static bool reconnectWithRetries(unsigned int retries, unsigned int waitSecs);

    bool autoReconnect();
    
    static bool waitForConnection(unsigned int waitSecs);

    String scanStoredNetworks();
  
    void registerWiFiEvents();
    void registerWiFiConnected();
    void registerWiFiDisconnected();

    void resetToSTA();

    static void onWiFiReconnect(WiFiEvent_t event, WiFiEventInfo_t info);
    static void onWiFiDisconnect(WiFiEvent_t event, WiFiEventInfo_t info);

public:
  
    void setup();
  
};

#endif
