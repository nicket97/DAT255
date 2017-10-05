import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Evelina on 2017-10-05.
 */
public class SensorData {
    private double inspeed_avg;
    private double fodometer;
    private double odometer;
    private double can_ultra;
    private double can_speed;
    private double can_steer;

    public void update (String data){
        try {
            JSONObject json = new JSONObject(data);

            inspeed_avg = json.getDouble("inspeed_avg");
            fodometer = json.getDouble("fodometer");
            odometer = json.getDouble("odometer");
            can_ultra = json.getDouble("can_ultra");
            can_speed = json.getDouble("can_speed");
            can_steer = json.getDouble("can_steer");

        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public double getInspeed_avg() {
        return inspeed_avg;
    }

    public double getFodometer() {
        return fodometer;
    }

    public double getOdometer() {
        return odometer;
    }

    public double getCan_ultra() {
        return can_ultra;
    }

    public double getCan_speed() {
        return can_speed;
    }

    public double getCan_steer() {
        return can_steer;
    }
}
