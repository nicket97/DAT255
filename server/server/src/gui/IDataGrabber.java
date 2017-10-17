package gui;

public interface IDataGrabber {
    double getVelocity();
    double getHandling();

    String getImagePath();

    boolean getAppConnection();
    boolean getACCActivated();
    boolean getPlatooningActivated();
}
