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

public class ManagerView {
    private Stage stage;
    private HotelController controller;
    private Manager manager;

    public ManagerView(Stage stage, HotelController controller, Manager manager) {
        this.stage = stage;
        this.controller = controller;
        this.manager = manager;
    }

    public Scene createScene() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        Label titleLabel = new Label("Manager Dashboard");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        HBox header = new HBox(titleLabel);
        header.setAlignment(Pos.CENTER);
        root.setTop(header);

        VBox menuBox = new VBox(15);
        menuBox.setPadding(new Insets(20));
        menuBox.setStyle("-fx-background-color: #ecf0f1; -fx-background-radius: 10;");

        Button viewBillsBtn = createButton("View All Bills", () -> showBillsDialog());
        Button revenueBtn = createButton("Calculate Total Revenue", () -> showRevenueDialog());
        Button viewRoomsBtn = createButton("View All Rooms", () -> showRoomsDialog());
        Button viewBookingsBtn = createButton("View All Bookings", () -> showBookingsDialog());
        Button logoutBtn = createButton("Logout", () -> {
            LoginView loginView = new LoginView(stage, controller);
            stage.setScene(loginView.createScene());
        });
        logoutBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

        menuBox.getChildren().addAll(viewBillsBtn, revenueBtn, viewRoomsBtn, viewBookingsBtn, logoutBtn);
        root.setCenter(menuBox);

        return new Scene(root, 800, 600);
    }

    private Button createButton(String text, Runnable action) {
        Button btn = new Button(text);
        btn.setPrefWidth(200);
        btn.setPrefHeight(40);
        btn.setStyle("-fx-font-size: 14px; -fx-background-color: #9b59b6; -fx-text-fill: white;");
        btn.setOnAction(e -> action.run());
        return btn;
    }

    private void showBillsDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("All Bills");
        
        TableView<Bill> table = new TableView<>();
        ObservableList<Bill> bills = FXCollections.observableArrayList(controller.getBills());
        table.setItems(bills);

        if (bills.isEmpty()) {
            showAlert("Info", "No bills generated yet!", Alert.AlertType.INFORMATION);
            return;
        }

        TableColumn<Bill, Integer> idCol = new TableColumn<>("Bill ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("billId"));
        
        TableColumn<Bill, String> customerCol = new TableColumn<>("Customer");
        customerCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getBooking().getCustomer().getFullName()));
        
        TableColumn<Bill, Integer> roomCol = new TableColumn<>("Room ID");
        roomCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getBooking().getRoom().getRoomId()).asObject());
        
        TableColumn<Bill, Double> amountCol = new TableColumn<>("Total Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        
        TableColumn<Bill, String> dateCol = new TableColumn<>("Generated Date");
        dateCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(
            cell.getValue().getGeneratedDate().toString()));

        table.getColumns().addAll(idCol, customerCol, roomCol, amountCol, dateCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vbox = new VBox(table);
        vbox.setPadding(new Insets(10));
        Scene scene = new Scene(vbox, 700, 400);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showRevenueDialog() {
        double totalRevenue = 0;
        for (Bill bill : controller.getBills()) {
            totalRevenue += bill.getTotalAmount();
        }

        String message = String.format("Total Revenue: $%.2f\nNumber of Bills: %d", 
            totalRevenue, controller.getBills().size());
        showAlert("Total Revenue", message, Alert.AlertType.INFORMATION);
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

    private void showBookingsDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("All Bookings");
        
        TableView<Booking> table = new TableView<>();
        ObservableList<Booking> bookings = FXCollections.observableArrayList(controller.getBookings());
        table.setItems(bookings);

        if (bookings.isEmpty()) {
            showAlert("Info", "No bookings found!", Alert.AlertType.INFORMATION);
            return;
        }

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

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

