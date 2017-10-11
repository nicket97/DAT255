#Stops immediately if something is to close, IF ACC IS NOT ACTIVE?

#Simple stop method
def simpleSafety(speed, fsensor):

    extraSafety = 10 #Extra safety in cm

    sDistanceM =(((0.0070970262*(speed*speed))+(0.23482713*speed)+0.53924471) + extraSafety)/100
    print ("sDistance " + str(sDistanceM))

    #safetyDist = ((fsensor - 0.10)*100)
    if (sDistanceM >= fsensor):
        print("To close!")
        #g.speed("V0000H0000")
        #Method to stop
    else:
        print("Anything is to close")

#print(SimpleSafety(21,0.36))
