package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.opencv.core.Core;

public class Main extends Application {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene((loader.load()), 800, 500));
        primaryStage.show();
        // set the proper behavior on closing the application
        Controller controller = (loader).getController();
        primaryStage.setOnCloseRequest((new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we)
            {
                controller.setClosed();
            }
        }));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
