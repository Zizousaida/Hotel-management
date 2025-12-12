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

public class ReceptionistView {
    private Stage stage;
    private HotelController controller;
    private Receptionist receptionist;

    public ReceptionistView(Stage stage, HotelController controller, Receptionist receptionist) {
        this.stage = stage;
        this.controller = controller;
        this.receptionist = receptionist;
    }

    public Scene createScene() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        Label titleLabel = new Label("Receptionist Dashboard");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        HBox header = new HBox(titleLabel);
        header.setAlignment(Pos.CENTER);
        root.setTop(header);

        VBox menuBox = new VBox(15);
        menuBox.setPadding(new Insets(20));
        menuBox.setStyle("-fx-background-color: #ecf0f1; -fx-background-radius: 10;");

        Button viewRoomsBtn = createButton("View All Rooms", () -> showRoomsDialog());
        Button bookRoomBtn = createButton("Book Room", () -> showBookRoomDialog());
        Button checkInBtn = createButton("Check-in Customer", () -> showCheckInDialog());
        Button checkOutBtn = createButton("Check-out Customer", () -> showCheckOutDialog());
        Button viewBookingsBtn = createButton("View All Bookings", () -> showBookingsDialog());
        Button updateStatusBtn = createButton("Update Room Status", () -> showUpdateStatusDialog());
        Button logoutBtn = createButton("Logout", () -> {
            LoginView loginView = new LoginView(stage, controller);
            stage.setScene(loginView.createScene());
        });
        logoutBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

        menuBox.getChildren().addAll(viewRoomsBtn, bookRoomBtn, checkInBtn, checkOutBtn, viewBookingsBtn, updateStatusBtn, logoutBtn);
        root.setCenter(menuBox);

        return new Scene(root, 800, 600);
    }

    private Button createButton(String text, Runnable action) {
        Button btn = new Button(text);
        btn.setPrefWidth(200);
        btn.setPrefHeight(40);
        btn.setStyle("-fx-font-size: 14px; -fx-background-color: #3498db; -fx-text-fill: white;");
        btn.setOnAction(e -> action.run());
        return btn;
    }

    private void showRoomsDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("All Rooms");
        
        TableView<Room> table = new TableView<>();
        ObservableList<Room> rooms = FXCollections.observableArrayList(controller.getAllRooms());
        table.setItems(rooms);

        TableColumn<Room, Integer> idCol = new TableColumn<>("Room ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        
        TableColumn<Room, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        
        TableColumn<Room, Double> priceCol = new TableColumn<>("Price/Night");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("pricePerNight"));
        
        TableColumn<Room, String> bookingCol = new TableColumn<>("Booking Status");
        bookingCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getBookingStatus().toString()));
        
        TableColumn<Room, String> occupancyCol = new TableColumn<>("Occupancy");
        occupancyCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getOccupancyStatus().toString()));
        
        TableColumn<Room, String> cleanCol = new TableColumn<>("Cleanliness");
        cleanCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getCleanlinessStatus().toString()));

        table.getColumns().addAll(idCol, typeCol, priceCol, bookingCol, occupancyCol, cleanCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vbox = new VBox(table);
        vbox.setPadding(new Insets(10));
        Scene scene = new Scene(vbox, 700, 400);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showBookRoomDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Book Room");
        
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        TextField nameField = new TextField();
        nameField.setPromptText("Customer Name");
        TextField idField = new TextField();
        idField.setPromptText("ID Number");
        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number");
        TextField roomField = new TextField();
        roomField.setPromptText("Room ID");
        TextField nightsField = new TextField();
        nightsField.setPromptText("Number of Nights");

        Button bookBtn = new Button("Book");
        bookBtn.setOnAction(e -> {
            try {
                Customer customer = new Customer(0, nameField.getText(), "", idField.getText(), phoneField.getText());
                int roomId = Integer.parseInt(roomField.getText());
                int nights = Integer.parseInt(nightsField.getText());
                
                Booking booking = controller.bookRoom(customer, roomId, nights);
                showAlert("Success", "Room booked successfully!\n" + booking, Alert.AlertType.INFORMATION);
                dialog.close();
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        vbox.getChildren().addAll(new Label("Customer Name:"), nameField,
                new Label("ID Number:"), idField,
                new Label("Phone:"), phoneField,
                new Label("Room ID:"), roomField,
                new Label("Nights:"), nightsField, bookBtn);

        Scene scene = new Scene(vbox, 400, 400);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showCheckInDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Check-in");
        dialog.setHeaderText("Enter Booking ID");
        dialog.setContentText("Booking ID:");

        dialog.showAndWait().ifPresent(bookingIdStr -> {
            try {
                int bookingId = Integer.parseInt(bookingIdStr);
                controller.checkInCustomer(bookingId);
                showAlert("Success", "Customer checked in successfully!", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        });
    }

    private void showCheckOutDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Check-out");
        dialog.setHeaderText("Enter Booking ID");
        dialog.setContentText("Booking ID:");

        dialog.showAndWait().ifPresent(bookingIdStr -> {
            try {
                int bookingId = Integer.parseInt(bookingIdStr);
                controller.checkOutCustomer(bookingId);
                showAlert("Success", "Customer checked out successfully! Bill generated.", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        });
    }

    private void showBookingsDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("All Bookings");
        
        TableView<Booking> table = new TableView<>();
        ObservableList<Booking> bookings = FXCollections.observableArrayList(controller.getBookings());
        table.setItems(bookings);

        TableColumn<Booking, Integer> idCol = new TableColumn<>("Booking ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        
        TableColumn<Booking, String> customerCol = new TableColumn<>("Customer");
        customerCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getCustomer().getFullName()));
        
        TableColumn<Booking, Integer> roomCol = new TableColumn<>("Room ID");
        roomCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getRoom().getRoomId()).asObject());
        
        TableColumn<Booking, Integer> nightsCol = new TableColumn<>("Nights");
        nightsCol.setCellValueFactory(new PropertyValueFactory<>("numberOfNights"));
        
        TableColumn<Booking, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().addAll(idCol, customerCol, roomCol, nightsCol, statusCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vbox = new VBox(table);
        vbox.setPadding(new Insets(10));
        Scene scene = new Scene(vbox, 700, 400);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showUpdateStatusDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Update Room Status");
        
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        TextField roomField = new TextField();
        roomField.setPromptText("Room ID");
        
        ComboBox<String> bookingCombo = new ComboBox<>(FXCollections.observableArrayList("AVAILABLE", "BOOKED"));
        bookingCombo.setPromptText("Booking Status");
        
        ComboBox<String> occupancyCombo = new ComboBox<>(FXCollections.observableArrayList("VACANT", "OCCUPIED"));
        occupancyCombo.setPromptText("Occupancy Status");
        
        ComboBox<String> cleanCombo = new ComboBox<>(FXCollections.observableArrayList("CLEAN", "DIRTY"));
        cleanCombo.setPromptText("Cleanliness Status");

        Button updateBtn = new Button("Update");
        updateBtn.setOnAction(e -> {
            try {
                int roomId = Integer.parseInt(roomField.getText());
                Room room = controller.findRoom(roomId);
                if (room == null) {
                    showAlert("Error", "Room not found!", Alert.AlertType.ERROR);
                    return;
                }

                BookingStatus booking = bookingCombo.getValue() != null ? 
                    BookingStatus.valueOf(bookingCombo.getValue()) : null;
                OccupancyStatus occupancy = occupancyCombo.getValue() != null ? 
                    OccupancyStatus.valueOf(occupancyCombo.getValue()) : null;
                CleanlinessStatus cleanliness = cleanCombo.getValue() != null ? 
                    CleanlinessStatus.valueOf(cleanCombo.getValue()) : null;

                room.updateRoomStatus(booking, occupancy, cleanliness);
                showAlert("Success", "Room status updated!\n" + room, Alert.AlertType.INFORMATION);
                dialog.close();
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        vbox.getChildren().addAll(
            new Label("Room ID:"), roomField,
            new Label("Booking Status:"), bookingCombo,
            new Label("Occupancy Status:"), occupancyCombo,
            new Label("Cleanliness Status:"), cleanCombo,
            updateBtn
        );

        Scene scene = new Scene(vbox, 400, 350);
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

