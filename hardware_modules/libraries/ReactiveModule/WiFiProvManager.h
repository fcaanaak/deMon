#ifndef WIFI_PROV_MANAGER_H
#define WIFI_PROV_MANAGER_H

#include <WiFiProvisioner.h>
#include <Preferences.h>
#include "FunctionalInterrupt.h"

class WiFiProvManager{

 private:

  // Fields
  WiFiProvisioner provisioner;
  Preferences prefObject;
  const unsigned int provButtonPin = 0;
  bool provisionOnNextCheck;
  
  // Methods

  // Setup WiFiProvisioner config
  void setupProvConfig();

  // Setup Provisioning button
  void setupProvButton();


  // - Setup callbacks
  void setupCallbacks();
  void setupProvCallback();
  void setupSuccessCallback();

  void activateProvisioningFlag();
  
  void activateProvisioning();
  
 public:

  void setup();

  void provisionIfFlagSet();
 
};



#endif
