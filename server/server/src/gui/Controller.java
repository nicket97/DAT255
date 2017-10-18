package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import server.DataGrabber;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.avformat.AVIOContext.Update_checksum_long_BytePointer_int;

import com.sun.media.jfxmediaimpl.platform.Platform;

import communication.MopedSteeringHandler;

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
    
    public void startUpdateUISequence() {
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					while (true) {
						System.out.println("startUpdateUISequence looped");
					updateUI();
					Thread.sleep(200);
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}).start();
    }
    
    private void updateUI() {
    	javafx.application.Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					txtVelocity.setText(String.valueOf(mDataGrabber.getVelocity()));
					txtHandling.setText(String.valueOf(mDataGrabber.getHandling()));

					//TODO ADD PATH TO MOST RECENT PIC

					txtACC.setText(String.valueOf(mDataGrabber.getACCActivated()));

					txtApp.setText(String.valueOf(mDataGrabber.getAppConnection()));

					txtPlatooning.setText(String.valueOf(mDataGrabber.getPlatooningActivated()));
					if (true) {
						//String imgAbsPath = mDataGrabber.getImagePath();
						
						String imgPath = mDataGrabber.getImagePath();
						//File imgFile = new File(imgPath);
						//imgPath = imgFile.
						//BufferedImage buffImage = ImageIO.read(imgFile);
						//Image image = SwingFXUtils.toFXImage(buffImage, null);
						//System.out.println(image.getHeight() + ", " + image.getWidth());
						//Image image = new Image("file:./resources/3.jpg");
						if (imgPath != null) {
							System.out.println(imgPath);
							Image image = new Image(imgPath);
							System.out.println(image.getHeight() + ", " + image.getWidth());
							System.out.println();
							imgView.setImage(image);
							//imgView.setImage(image);
							//imgView.autosize();
							System.out.println("image set");
						} else {
							System.out.println("IMG PATH NULL");
						}
						
						
					}
					
					

					System.out.println("updated ui");

					txtACC.setText("CHANGING");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
    }


    @Override
    public void initialize(URL location, ResourceBundle resources){
    	txtVelocity.setText(String.valueOf(mDataGrabber.getVelocity()));
        txtHandling.setText(String.valueOf(mDataGrabber.getHandling()));



        System.out.println("SSRTAERGRG");
        txtACC.setText(String.valueOf(mDataGrabber.getACCActivated()));

        txtApp.setText(String.valueOf(mDataGrabber.getAppConnection()));

        txtPlatooning.setText(String.valueOf(mDataGrabber.getPlatooningActivated()));


        txtACC.setText("CHANING");
        
        startUpdateUISequence();
    }

}
