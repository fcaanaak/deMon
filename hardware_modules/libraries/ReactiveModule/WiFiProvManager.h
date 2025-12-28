#ifndef WIFI_PROV_MANAGER_H
#define WIFI_PROV_MANAGER_H

#include <WiFiProvisioner.h>
#include <Preferences.h>
#include "FunctionalInterrupt.h"
#include <Regexp.h>

class WiFiProvManager{

 private:

  // Fields
  WiFiProvisioner provisioner;
  Preferences prefObject;
  const unsigned int provButtonPin = 0;
  static inline bool isProvisioning = false;
  bool provisionOnNextCheck = false;

  MatchState matcher;
  const char* ipPattern = "([0-9]{3}.){3}([0-9]{3})";
  
  // Methods

  // Setup WiFiProvisioner config
  void setupProvConfig();

  // Setup Provisioning button
  void setupProvButton();


  // - Setup callbacks
  void setupCallbacks();
  void setupProvCallback();
  void setupSuccessCallback();
  void setupInputCheckCallback();

  void activateProvisioningFlag();
  
  void activateProvisioning();
  
 public:

  void setup();
  
  static bool checkIfProvisioning();
  void provisionIfFlagSet();
 
};



#endif
