#include "WiFiProvManager.h"

// Setup methods

/**
 * @brief Set up the WiFiProvisioner config object with default values
 *
*/
void WiFiProvManager::setupProvConfig(){

  WiFiProvisioner::Config &config = provisioner.getConfig();
  
  config.AP_NAME = "New Modsec Device";
  config.HTML_TITLE = "New Device Setup";
  config.THEME_COLOR = "#666060";
  config.SVG_LOGO = "New Modsec Device";
  config.PROJECT_TITLE= "New Modsec Device";
  config.FOOTER_TEXT= "New Modsec Device";
  config.RESET_CONFIRMATION_TEXT = "RESET?";
  config.CONNECTION_SUCCESSFUL = "CONNECTION SUCCESSFUL :)";
  config.INPUT_TEXT = "New Modsec Device";
  config.INPUT_LENGTH = 6;
  config.SHOW_INPUT_FIELD = false;
  config.SHOW_RESET_FIELD = false;
  
}


void WiFiProvManager::setupSuccessCallback(){

  provisioner.onSuccess( [this](const char* ssid, const char* password, const char* input) {
    prefObject.begin("wifiDatabase", false);
    prefObject.putString(ssid,password);
    prefObject.end();

    ESP.restart();

  });

}

void WiFiProvManager::setupProvCallback(){
  provisioner.onProvision( [this]() {

  });
}

void WiFiProvManager::setupCallbacks(){

  setupProvCallback();
  setupSuccessCallback();

}


void WiFiProvManager::setupProvButton(){
  pinMode(provButtonPin,INPUT_PULLUP);
  attachInterrupt(digitalPinToInterrupt(provButtonPin),std::bind(&WiFiProvManager::activateProvisioningFlag,this), FALLING);

}

void WiFiProvManager::setup(){

  provisionOnNextCheck = false;

  setupProvConfig();
  setupProvButton();
  setupCallbacks();
  
}

void WiFiProvManager::activateProvisioning(){
  provisioner.startProvisioning();
}

void WiFiProvManager::activateProvisioningFlag(){
  provisionOnNextCheck = true;
}

void WiFiProvManager::provisionIfFlagSet(){

  if (provisionOnNextCheck) {
    activateProvisioning();
    provisionOnNextCheck = false;
  }

}

