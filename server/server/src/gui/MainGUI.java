package gui;

import javax.xml.ws.Holder;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.DataGrabber;
import server.Start;

public class MainGUI extends Application implements Runnable {
	private JFXPanel holder;

    @Override
    public void start(Stage primaryStage) throws Exception{
    	holder = new JFXPanel();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        
        Controller mController = new Controller();
        mController.setDataGrabber(new DataGrabber());
        fxmlLoader.setController(mController);
        

        Parent root = fxmlLoader.load();

        primaryStage.setTitle("BRAISH  Server");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        Start.start = new Start();
    }

   /* @Override
    public void run() {
    	System.out.println("Starting GUI");
        main(null);
        
    }*/
}
