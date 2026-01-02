#include "WiFiManager.h"


void WiFiManager::resetToSTA(){
  /**
     @brief Set the esp32 to STA mode in case it wasn't already
   */
  WiFi.mode(WIFI_STA);
  WiFi.disconnect();
  delay(100);
}

bool WiFiManager::reconnectWithRetries(unsigned int retries, unsigned int waitSecs){

  unsigned int currentRetry = 0;
  unsigned int waitTime = waitSecs;
  
  for (currentRetry; (currentRetry < retries) && (!reconnectToWiFi(waitTime)); currentRetry++){
    
    if (WiFi.disconnect(false,false,10*1000)){
      Serial.println("disconnected");
    } else{
      Serial.println("timeout");
    }
  }

  return (currentRetry < retries);

}

bool WiFiManager::waitForConnection(unsigned int waitSecs){

  unsigned int currentSec = 0;

  
  for (currentSec; ((currentSec < waitSecs) && (WiFi.status() != WL_CONNECTED)); currentSec++){
    delay(1000);
    Serial.print(".");

  }

  return (currentSec < waitSecs);


}

bool WiFiManager::connectToWiFi(const char* ssid, const char* password, unsigned int waitSecs){
   /**
     @brief Connect to a WiFi network
     @param ssid: The ssid or name of the WiFi network
     @param password: The password of the WiFi network 
   */
  Serial.print("Connecting to Wifi network with credentials: ");
  Serial.print(ssid);
  Serial.print(" ");
  Serial.println(password);
  
  // Add logic if we fail to connect
  WiFi.begin(ssid,password);
  
  return waitForConnection(waitSecs);
  
  
}

bool WiFiManager::reconnectToWiFi(unsigned int waitSecs){
  /**
     @brief Reconnect to a WiFi network previously connected to
     @param waitSecs: Max connection establishment wait time
     @return true if successful WiFi connection before waitSecs, false otherwise
   */

  WiFi.reconnect();

  return waitForConnection(waitSecs);
  
}

String WiFiManager::scanStoredNetworks(){

  pref.beginWiFiDatabase(PrefManager::readWriteMode);
  
  int availableNetworks = WiFi.scanNetworks();

  if (availableNetworks > 0) {

    for (int currentNetwork = 0;(currentNetwork < availableNetworks);currentNetwork++){
      
      String ssid = WiFi.SSID(currentNetwork);
      
      if (pref.getString(ssid.c_str(),"") != ""){	

	WiFi.scanDelete();
	pref.end();
        return ssid;
      }
     
    }
  }
  
  pref.end();
  return String();

}

bool WiFiManager::autoReconnect(){
  /**
     @brief Automatically reconnect to a previously saved WiFi network
     if said network is found during scanning

     @return true if a WiFi network was able to be connected
     to and false otherwise
   */
  String storedSSID = scanStoredNetworks();
 
  if (storedSSID != ""){

    pref.beginWiFiDatabase(PrefManager::readMode);
    
    String password = pref.getString(storedSSID.c_str());
    const char* password_cstring = password.c_str();

    pref.end();

    bool connectionSuccessful = connectToWiFi(storedSSID.c_str(),password_cstring,10);

    return connectionSuccessful;  
  }
  
  return false;

}

void WiFiManager::registerWiFiEvents(){
  
  registerWiFiConnected();
  registerWiFiDisconnected();


}

void WiFiManager::registerWiFiConnected(){
  WiFi.onEvent(
	       onWiFiReconnect,
	       WiFiEvent_t::ARDUINO_EVENT_WIFI_STA_CONNECTED
	       );
}

void WiFiManager::registerWiFiDisconnected(){

  WiFi.onEvent(
	       onWiFiDisconnect,
	       WiFiEvent_t::ARDUINO_EVENT_WIFI_STA_DISCONNECTED
	       );

}


void WiFiManager::onWiFiReconnect(WiFiEvent_t event, WiFiEventInfo_t info){
  
  LEDManager::flashLEDBlocking(0,255,0,3,100);
  

}


void WiFiManager::onWiFiDisconnect(WiFiEvent_t event, WiFiEventInfo_t info){

  if (!WiFiProvManager::checkIfProvisioning()){
    LEDManager::setLED(255,63,0);
  
    if (!reconnectToWiFi(10)){

      ESP.restart();
    }

  }
  
}


void WiFiManager::setup(){
  //  resetToSTA();
  registerWiFiEvents();
  wifiProv.setup();
  

  bool successfulAutoReconnect = autoReconnect();
  
  if (!successfulAutoReconnect){
    LEDManager::setLED(255,0,0);
  }
  
  while (!successfulAutoReconnect){
    wifiProv.provisionIfFlagSet();
    successfulAutoReconnect = autoReconnect();
    delay(10*1000);
    
  }
  
}
