package com.hotel.view;

import com.hotel.controller.HotelController;
import com.hotel.model.Employee;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {
    private Stage stage;
    private HotelController controller;

    public LoginView(Stage stage, HotelController controller) {
        this.stage = stage;
        this.controller = controller;
    }

    public Scene createScene() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f5f5f5;");

        Label titleLabel = new Label("Hotel Management System");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label subtitleLabel = new Label("Please login to continue");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefWidth(250);
        usernameField.setStyle("-fx-font-size: 14px;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(250);
        passwordField.setStyle("-fx-font-size: 14px;");

        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(250);
        loginButton.setStyle("-fx-font-size: 14px; -fx-background-color: #3498db; -fx-text-fill: white;");
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            
            if (username.isEmpty() || password.isEmpty()) {
                showAlert("Error", "Please enter both username and password", Alert.AlertType.ERROR);
                return;
            }

            Employee employee = controller.login(username, password);
            if (employee != null) {
                openRoleView(employee);
            } else {
                showAlert("Login Failed", "Invalid credentials!", Alert.AlertType.ERROR);
            }
        });

        root.getChildren().addAll(titleLabel, subtitleLabel, usernameField, passwordField, loginButton);
        
        Scene scene = new Scene(root, 400, 400);
        return scene;
    }

    private void openRoleView(Employee employee) {
        String role = employee.getRole();
        Scene roleScene = null;

        if (role.equals("Receptionist")) {
            ReceptionistView receptionistView = new ReceptionistView(stage, controller, (com.hotel.model.Receptionist) employee);
            roleScene = receptionistView.createScene();
        } else if (role.equals("Housekeeping Staff")) {
            HousekeepingView housekeepingView = new HousekeepingView(stage, controller, (com.hotel.model.HousekeepingStaff) employee);
            roleScene = housekeepingView.createScene();
        } else if (role.equals("Manager")) {
            ManagerView managerView = new ManagerView(stage, controller, (com.hotel.model.Manager) employee);
            roleScene = managerView.createScene();
        }

        if (roleScene != null) {
            stage.setScene(roleScene);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

