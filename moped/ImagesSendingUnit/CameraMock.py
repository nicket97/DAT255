"""Even though poorly named script, this handles the PiCamera capturing"""
import time
from picamera import PiCamera


class Value:
    """Wrapper class for variables"""

    def __init__(self, init_value):
        """iniittes all"""
        self.value = init_value

    def set_value(self, new_value):
        """A setter method"""
        self.value = new_value

    def get_value(self):
        """A getter method"""
        return self.value


FLAG = Value(False)
HEIGHT = 225
WIDTH = 400
QUALITY = 100
TIMEOUT = 1
FOLDER = "./ImagesSendingUnit/images/"

COUNTER = Value(0)


def start_capturin():
    """Instantiates the camera settings and starts capturing pictures"""
    camera = PiCamera()
    camera.resolution = (WIDTH, HEIGHT)
    camera.framerate = 30
    FLAG.set_value(True)
    while FLAG.get_value():
        COUNTER.set_value(COUNTER.get_value() + 1)
        filename = str(COUNTER.get_value()) + ".jpg"
        camera.capture(FOLDER + filename, use_video_port=True)
        time.sleep(0.025)


def stop_capturing():
    """Sets the FLAG to false so the camera stops the while loop and thus stops capturing images"""
    FLAG.set_value(False)


if __name__ == "__main__":
    start_capturin()
