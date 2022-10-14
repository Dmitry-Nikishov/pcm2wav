package su.elanor.pcm_to_wav_converter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import su.elanor.pcm_to_wav_converter.common.AppConstants;
import su.elanor.pcm_to_wav_converter.controllers.MainViewController;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main_win.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 300);

        MainViewController controller = fxmlLoader.getController();
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.setTitle(String.format("%s v%s", AppConstants.APP_NAME, AppConstants.APP_VERSION));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
