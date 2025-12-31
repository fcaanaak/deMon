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
  config.INPUT_TEXT = "Server IP";
  config.INPUT_LENGTH = 15;
  config.SHOW_INPUT_FIELD = true;
  config.SHOW_RESET_FIELD = false;
  
}


void WiFiProvManager::setupSuccessCallback(){

  provisioner.onSuccess( [this](const char* ssid, const char* password, const char* input) {

    pref.addWiFiNetwork(ssid,password);
    pref.addServerIP(input);
    
    isProvisioning = false;
    
    ESP.restart();
  });

}

void WiFiProvManager::setupInputCheckCallback(){
  
  provisioner.onInputCheck( [this](const char* input) -> bool {

    char* inputParam;
    *inputParam = *input;

    matcher.Target(inputParam);
    return matcher.Match(ipPattern) > 0;
    
  });

}

void WiFiProvManager::setupProvCallback(){
  provisioner.onProvision( [this]() {
    isProvisioning = true;
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
    provisionOnNextCheck = false;
    activateProvisioning();
  }

}

bool WiFiProvManager::checkIfProvisioning(){

  return isProvisioning;
}

