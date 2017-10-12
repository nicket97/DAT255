/**
 * Created by Evelina on 2017-10-11.
 */
public class ConvertSpeedToESC {

    public String convertSpeedToESCValue(int speed){
        if (speed >= 15) {
            String value;
            value = ""+(speed - 15)/2;

            return value;
        } else {
            return "Hastigheten är för liten för att mopeden skall kunna röra sig.";
        }
    }
}
