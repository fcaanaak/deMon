import random
import time
import json
from threading import Thread
from datetime import datetime
from websockets.sync.client import connect

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
        
        self.server_url = "ws://10.0.0.114:8080/"
        self.reports_url = self.server_url + "ws-reports"
                
        self.interval_secs = 0.5
        self.detection_chance = 10

    def add_report(self,sock,json_content):
        """
        Send a detection report to the server
        """
        sock.send(json.dumps(json_content));
        message = sock.recv();
        print(f"Received: {message}")

    def generate_report(self):
        curr_datetime = datetime.now()

        report_data = {
            "name": self.name,
            "date": {
                "year": curr_datetime.year,
                "month": curr_datetime.month,
                "day": curr_datetime.day,
                "hour": curr_datetime.hour,
                "minute": curr_datetime.minute,
                "second": curr_datetime.second
            }
        }

        test_data = {
            "deviceName" : self.name
        }

        return test_data

    def main_loop(self):
        
        with connect(self.reports_url) as ws:
            
            while True:
            
                if self.detect():
                    print(f"Detection made by {self.name}")
                    self.add_report(ws,self.generate_report())

                time.sleep(self.interval_secs)
            
    def detect(self):

        return random.randint(1,100) <= self.detection_chance 


# VModule definition done

def create_vmodules(number):

    for i in range(number):
        Thread(target = VirtualModule().main_loop).start()



if __name__ == "__main__":
    create_vmodules(3);

    
    
    


    
