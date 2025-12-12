import controller.HotelController;
import view.LoginWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        HotelController controller = new HotelController();
        LoginWindow loginWindow = new LoginWindow(primaryStage, controller);
        
        primaryStage.setTitle("Hotel Management System");
        primaryStage.setScene(loginWindow.createScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
