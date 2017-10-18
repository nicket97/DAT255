package gui;

public interface IDataGrabber {
    double getVelocity();
    double getHandling();

    String getImagePath();
    int getPosX();
    int getPosY();

    boolean getAppConnection();
    boolean getACCActivated();
    boolean getPlatooningActivated();
}
