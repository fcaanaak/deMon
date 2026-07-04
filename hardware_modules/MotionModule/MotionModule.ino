#include "MotionModule.h"
#include <Preferences.h>
// 10.195.241.133
MotionModule mod = MotionModule();
Preferences pref;

void setup(){
  Serial.begin(9600);
  
  mod.setup();
  
}

void loop(){
  
  mod.mainloop();
}
