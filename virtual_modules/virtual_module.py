import random
import time
import json
from threading import Thread
from datetime import datetime
import requests
from uuid import uuid4
from websockets.sync.client import connect

DEVICE_COUNT = 4


class VirtualModule:

    """
    Mock version of a hardware module
    aimed to mimick the detection
    functionality of the real reactive modules
    """
    
    count = 0
    
    def __init__(self):
        
        self.name = f"VModule_{VirtualModule.count}"
        VirtualModule.count += 1
        
        self.server_ws_url = "ws://localhost:8080/ws"
        self.server_rest_url = "http://localhost:8080/"
        self.reports_url = self.server_rest_url + "reports"
                
        self.interval_secs = 0.5
        self.detection_chance = 10
        self.uuid = str(uuid4())

        
    def adjust_single_digit(self,num):
        """
        Preconditions:
        num >= 0
        
        Returns a string with 0 prepended to num if num is single digit
        """
        
        if num < 10:
            return f"0{num}"
        
        return str(num)

    
    def add_report(self,json_content):
        """
        Send a detection report to the server
        """
        response = requests.post(
            self.reports_url,
            json = json_content
        )
        
        assert response.status_code == 201

    def gen_init(self):
        return json.dumps({"UUID":self.uuid, "isInit":True, "name":self.name})
        
    def generate_report(self):
        curr_datetime = datetime.now()

        str_day = self.adjust_single_digit(curr_datetime.day)
        str_month = self.adjust_single_digit(curr_datetime.month)
        
        str_hour = self.adjust_single_digit(curr_datetime.hour)
        str_minute = self.adjust_single_digit(curr_datetime.minute)
        str_second = self.adjust_single_digit(curr_datetime.second)
        
        report_data = {
            "deviceName" : self.name,
            "detectionDateTime": f"{str_day}-{str_month}-{curr_datetime.year} {str_hour}:{str_minute}:{str_second}"
        }

        return report_data

    
    def detect(self):

        return random.randint(1,100) <= self.detection_chance


    def main_loop(self):

        with connect(self.server_ws_url) as websocket:

            # Send the init json message
            websocket.send(self.gen_init())
            
            while True:
            
                if self.detect():
                    print(f"Detection made by {self.name}")
                    self.add_report(self.generate_report())

                time.sleep(self.interval_secs)


# VModule definition done

def create_vmodules(number):

    for i in range(number):
        Thread(target = VirtualModule().main_loop).start()


if __name__ == "__main__":
    create_vmodules(DEVICE_COUNT);

    
    
    


    
