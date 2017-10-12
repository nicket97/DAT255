import SafetyLength

def shouldStop (acc, platooning, speed, fsensor):

    #Om acc är false så vill vi att safety length metoden aktiveras
    if (acc == False or platooning == False):
        SafetyLength.simpleSafety(speed, fsensor)


#def valuesDrive(value):
    #send info to dodriving with value.
    #drivning.dodrive(values)