#include "WiFiManager.h"

/**
 * @brief Set the esp32 to STA mode in case it wasn't already
*/
void WiFiManager::resetToSTA(){

    WiFi.mode(WIFI_STA);
    WiFi.disconnect();
    delay(100);
}


/**
 * @brief Reconnect to the wifi with retries
 * @param retries: The number of connection retries to attempt
 * @param waitSecs: How long to wait for a connection to establish each connection attempt
 * @return True if we successfully reconnected within the specified retries and false otherwise
 */
bool WiFiManager::reconnectWithRetries(unsigned int retries, unsigned int waitSecs){

    unsigned int currentRetry = 0;
    unsigned int waitTime = waitSecs;
  
    for (currentRetry; (currentRetry < retries) && (!reconnectToWiFi(waitTime)); currentRetry++){}

    return (currentRetry < retries);

}


/**
 * @brief Wait and see if an active connection has been established
 * @param waitSecs: How long to wait before checking if connected
 * @return True if a connection was achieved within waitSecs seconds and false otherwise
 */
bool WiFiManager::waitForConnection(unsigned int waitSecs){

    unsigned int currentSec = 0;
  
    for (currentSec; ((currentSec < waitSecs) && (WiFi.status() != WL_CONNECTED)); currentSec++){
	delay(1000);
    }

    return (currentSec < waitSecs);

}


/**
 * @brief Connect to a WiFi network
 * @param ssid: The ssid or name of the WiFi network
 * @param password: The password of the WiFi network
*/
bool WiFiManager::connectToWiFi(const char* ssid, const char* password, unsigned int waitSecs){
  
    WiFi.begin(ssid,password);
    waitForConnection(waitSecs);
  
}


/**
   @brief Reconnect to a WiFi network previously connected to
   @param waitSecs: Max connection establishment wait time
   @return true if successful WiFi connection before waitSecs, false otherwise
*/
bool WiFiManager::reconnectToWiFi(unsigned int waitSecs){

    WiFi.reconnect();

    return waitForConnection(waitSecs);
  
}


/**
 * @brief Scan all available WiFi networks and see if any networks found match the stored network
 *
 * @return The SSID of the network if it matches whats stored and an empty string otherwise
 */
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


/**
 *
 * @brief Automatically reconnect to a previously saved WiFi network
 *  if said network is found during scanning
 *
 *  @return true if a WiFi network was able to be connected to and false otherwise
 *
 */
bool WiFiManager::autoReconnect(){

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

    const unsigned int reconnectDelayMillis = 10*1000;
    
    resetToSTA();
    
    registerWiFiEvents();
    wifiProv.setup();
  
    bool successfulAutoReconnect = autoReconnect();
  
    if (!successfulAutoReconnect){
	LEDManager::setLED(255,0,0);
    }
  
    while (!successfulAutoReconnect){
	wifiProv.provisionIfFlagSet();
	successfulAutoReconnect = autoReconnect();
	delay(reconnectDelayMillis);
    }
  
}
