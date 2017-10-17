package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.DataGrabber;

public class MainGUI extends Application implements Runnable {

    @Override
    public void start(Stage primaryStage) throws Exception{
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
    }

    @Override
    public void run() {
    	System.out.println("Starting GUI");
        main(null);
        
    }
}
