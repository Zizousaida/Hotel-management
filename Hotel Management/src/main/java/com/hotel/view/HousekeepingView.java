package com.hotel.view;

import com.hotel.controller.HotelController;
import com.hotel.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HousekeepingView {
    private Stage stage;
    private HotelController controller;
    private HousekeepingStaff staff;

    public HousekeepingView(Stage stage, HotelController controller, HousekeepingStaff staff) {
        this.stage = stage;
        this.controller = controller;
        this.staff = staff;
    }

    public Scene createScene() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        Label titleLabel = new Label("Housekeeping Dashboard");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        HBox header = new HBox(titleLabel);
        header.setAlignment(Pos.CENTER);
        root.setTop(header);

        VBox menuBox = new VBox(15);
        menuBox.setPadding(new Insets(20));
        menuBox.setStyle("-fx-background-color: #ecf0f1; -fx-background-radius: 10;");

        Button viewDirtyBtn = createButton("View Dirty Rooms", () -> showDirtyRoomsDialog());
        Button markCleanBtn = createButton("Mark Room as Clean", () -> showMarkCleanDialog());
        Button viewAllBtn = createButton("View All Rooms", () -> showAllRoomsDialog());
        Button logoutBtn = createButton("Logout", () -> {
            LoginView loginView = new LoginView(stage, controller);
            stage.setScene(loginView.createScene());
        });
        logoutBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

        menuBox.getChildren().addAll(viewDirtyBtn, markCleanBtn, viewAllBtn, logoutBtn);
        root.setCenter(menuBox);

        return new Scene(root, 800, 600);
    }

    private Button createButton(String text, Runnable action) {
        Button btn = new Button(text);
        btn.setPrefWidth(200);
        btn.setPrefHeight(40);
        btn.setStyle("-fx-font-size: 14px; -fx-background-color: #27ae60; -fx-text-fill: white;");
        btn.setOnAction(e -> action.run());
        return btn;
    }

    private void showDirtyRoomsDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Dirty Rooms");
        
        TableView<Room> table = new TableView<>();
        ObservableList<Room> dirtyRooms = FXCollections.observableArrayList(controller.getDirtyRooms());
        table.setItems(dirtyRooms);

        if (dirtyRooms.isEmpty()) {
            showAlert("Info", "No dirty rooms found!", Alert.AlertType.INFORMATION);
            return;
        }

        TableColumn<Room, Integer> idCol = new TableColumn<>("Room ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        
        TableColumn<Room, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        
        TableColumn<Room, String> bookingCol = new TableColumn<>("Booking Status");
        bookingCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getBookingStatus().toString()));
        
        TableColumn<Room, String> occupancyCol = new TableColumn<>("Occupancy");
        occupancyCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getOccupancyStatus().toString()));

        table.getColumns().addAll(idCol, typeCol, bookingCol, occupancyCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vbox = new VBox(table);
        vbox.setPadding(new Insets(10));
        Scene scene = new Scene(vbox, 600, 300);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showMarkCleanDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Mark Room as Clean");
        dialog.setHeaderText("Enter Room ID");
        dialog.setContentText("Room ID:");

        dialog.showAndWait().ifPresent(roomIdStr -> {
            try {
                int roomId = Integer.parseInt(roomIdStr);
                controller.markRoomAsClean(roomId);
                showAlert("Success", "Room " + roomId + " marked as CLEAN!", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        });
    }

    private void showAllRoomsDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("All Rooms");
        
        TableView<Room> table = new TableView<>();
        ObservableList<Room> rooms = FXCollections.observableArrayList(controller.getAllRooms());
        table.setItems(rooms);

        TableColumn<Room, Integer> idCol = new TableColumn<>("Room ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        
        TableColumn<Room, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        
        TableColumn<Room, String> bookingCol = new TableColumn<>("Booking Status");
        bookingCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getBookingStatus().toString()));
        
        TableColumn<Room, String> occupancyCol = new TableColumn<>("Occupancy");
        occupancyCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getOccupancyStatus().toString()));
        
        TableColumn<Room, String> cleanCol = new TableColumn<>("Cleanliness");
        cleanCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getCleanlinessStatus().toString()));

        table.getColumns().addAll(idCol, typeCol, bookingCol, occupancyCol, cleanCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vbox = new VBox(table);
        vbox.setPadding(new Insets(10));
        Scene scene = new Scene(vbox, 700, 400);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

