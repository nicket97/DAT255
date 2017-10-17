package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    Label txtVelocity;

    @FXML
    Label txtHandling;
    @FXML
    Label txtACC;
    @FXML
    Label txtPlatooning;

    @FXML
    Label txtApp;

    @FXML
    ImageView imgView;


    private IDataGrabber mDataGrabber;

    public void setDataGrabber(IDataGrabber dataGrabber){
        this.mDataGrabber=dataGrabber;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources){


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        txtVelocity.setText(String.valueOf(mDataGrabber.getVelocity()));
                        txtHandling.setText(String.valueOf(mDataGrabber.getHandling()));

                        //TODO ADD PATH TO MOST RECENT PIC
                        //imgView.setImage(new Image(mDataGrabber.getImagePath()));

                        txtACC.setText(String.valueOf(mDataGrabber.getACCActivated()));

                        txtApp.setText(String.valueOf(mDataGrabber.getAppConnection()));

                        txtPlatooning.setText(String.valueOf(mDataGrabber.getPlatooningActivated()));


                        txtACC.setText("CHANING");


                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
