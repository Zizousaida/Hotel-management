package view;

import controller.HotelController;
import model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginWindow {
    private Stage stage;
    private HotelController controller;

    public LoginWindow(Stage stage, HotelController controller) {
        this.stage = stage;
        this.controller = controller;
    }

    public Scene createScene() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Hotel Management System");
        title.setStyle("-fx-font-size: 18px;");

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPrefWidth(200);

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(200);

        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(200);
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            
            User user = controller.login(username, password);
            if (user != null) {
                openDashboard(user);
            } else {
                showAlert("Error", "Invalid username or password");
            }
        });

        root.getChildren().addAll(title, usernameLabel, usernameField, passwordLabel, passwordField, loginButton);
        return new Scene(root, 300, 250);
    }

    private void openDashboard(User user) {
        String role = user.getRole();
        if (role.equals("Receptionist")) {
            ReceptionistWindow window = new ReceptionistWindow(stage, controller);
            stage.setScene(window.createScene());
        } else if (role.equals("Housekeeping Staff")) {
            HousekeepingWindow window = new HousekeepingWindow(stage, controller);
            stage.setScene(window.createScene());
        } else if (role.equals("Manager")) {
            ManagerWindow window = new ManagerWindow(stage, controller);
            stage.setScene(window.createScene());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

