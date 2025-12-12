package view;

import controller.HotelController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HousekeepingWindow {
    private Stage stage;
    private HotelController controller;

    public HousekeepingWindow(Stage stage, HotelController controller) {
        this.stage = stage;
        this.controller = controller;
    }

    public Scene createScene() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label title = new Label("Housekeeping Dashboard");
        title.setStyle("-fx-font-size: 16px;");

        Button viewDirtyBtn = new Button("View Dirty Rooms");
        Button markCleanBtn = new Button("Mark Room as Clean");
        Button viewAllBtn = new Button("View All Rooms");
        Button logoutBtn = new Button("Logout");

        viewDirtyBtn.setOnAction(e -> showDirtyRooms());
        markCleanBtn.setOnAction(e -> showMarkCleanDialog());
        viewAllBtn.setOnAction(e -> showAllRooms());
        logoutBtn.setOnAction(e -> {
            view.LoginWindow login = new view.LoginWindow(stage, controller);
            stage.setScene(login.createScene());
        });

        root.getChildren().addAll(title, viewDirtyBtn, markCleanBtn, viewAllBtn, logoutBtn);
        return new Scene(root, 400, 300);
    }

    private void showDirtyRooms() {
        Stage dialog = new Stage();
        TableView<model.Room> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(controller.getDirtyRooms()));

        TableColumn<model.Room, Integer> idCol = new TableColumn<>("Room");
        idCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        
        TableColumn<model.Room, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("roomType"));

        table.getColumns().addAll(idCol, typeCol);
        
        VBox vbox = new VBox(table);
        Scene scene = new Scene(vbox, 300, 200);
        dialog.setScene(scene);
        dialog.setTitle("Dirty Rooms");
        dialog.show();
    }

    private void showMarkCleanDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Mark Room as Clean");
        dialog.setHeaderText("Enter Room Number");
        dialog.showAndWait().ifPresent(roomNum -> {
            try {
                controller.markRoomAsClean(Integer.parseInt(roomNum));
                showAlert("Success", "Room marked as clean!");
            } catch (Exception e) {
                showAlert("Error", e.getMessage());
            }
        });
    }

    private void showAllRooms() {
        Stage dialog = new Stage();
        TableView<model.Room> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(controller.getRooms()));

        TableColumn<model.Room, Integer> idCol = new TableColumn<>("Room");
        idCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        
        TableColumn<model.Room, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("roomType"));

        table.getColumns().addAll(idCol, typeCol);
        
        VBox vbox = new VBox(table);
        Scene scene = new Scene(vbox, 300, 200);
        dialog.setScene(scene);
        dialog.setTitle("All Rooms");
        dialog.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

