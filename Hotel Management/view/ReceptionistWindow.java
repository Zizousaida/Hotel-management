package view;

import controller.HotelController;
import model.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ReceptionistWindow {
    private Stage stage;
    private HotelController controller;

    public ReceptionistWindow(Stage stage, HotelController controller) {
        this.stage = stage;
        this.controller = controller;
    }

    public Scene createScene() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label title = new Label("Receptionist Dashboard");
        title.setStyle("-fx-font-size: 16px;");

        Button viewRoomsBtn = new Button("View All Rooms");
        Button bookRoomBtn = new Button("Book Room");
        Button checkInBtn = new Button("Check-in");
        Button checkOutBtn = new Button("Check-out");
        Button viewBookingsBtn = new Button("View Bookings");
        Button updateStatusBtn = new Button("Update Room Status");
        Button logoutBtn = new Button("Logout");

        viewRoomsBtn.setOnAction(e -> showRooms());
        bookRoomBtn.setOnAction(e -> showBookRoomDialog());
        checkInBtn.setOnAction(e -> showCheckInDialog());
        checkOutBtn.setOnAction(e -> showCheckOutDialog());
        viewBookingsBtn.setOnAction(e -> showBookings());
        updateStatusBtn.setOnAction(e -> showUpdateStatusDialog());
        logoutBtn.setOnAction(e -> {
            LoginWindow login = new LoginWindow(stage, controller);
            stage.setScene(login.createScene());
        });

        root.getChildren().addAll(title, viewRoomsBtn, bookRoomBtn, checkInBtn, checkOutBtn, viewBookingsBtn, updateStatusBtn, logoutBtn);
        return new Scene(root, 400, 400);
    }

    private void showRooms() {
        Stage dialog = new Stage();
        TableView<Room> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(controller.getRooms()));

        TableColumn<Room, Integer> idCol = new TableColumn<>("Room");
        idCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        
        TableColumn<Room, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        
        TableColumn<Room, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("pricePerNight"));

        table.getColumns().addAll(idCol, typeCol, priceCol);
        
        VBox vbox = new VBox(table);
        Scene scene = new Scene(vbox, 400, 300);
        dialog.setScene(scene);
        dialog.setTitle("All Rooms");
        dialog.show();
    }

    private void showBookRoomDialog() {
        Stage dialog = new Stage();
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        TextField nameField = new TextField();
        nameField.setPromptText("Customer Name");
        TextField idField = new TextField();
        idField.setPromptText("ID Number");
        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone");
        TextField roomField = new TextField();
        roomField.setPromptText("Room Number");
        TextField nightsField = new TextField();
        nightsField.setPromptText("Nights");

        Button bookBtn = new Button("Book");
        bookBtn.setOnAction(e -> {
            try {
                Customer customer = new Customer(nameField.getText(), idField.getText(), phoneField.getText());
                int roomNum = Integer.parseInt(roomField.getText());
                int nights = Integer.parseInt(nightsField.getText());
                controller.bookRoom(customer, roomNum, nights);
                showAlert("Success", "Room booked successfully!");
                dialog.close();
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        vbox.getChildren().addAll(new Label("Book Room"), nameField, idField, phoneField, roomField, nightsField, bookBtn);
        Scene scene = new Scene(vbox, 300, 250);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showCheckInDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Check-in");
        dialog.setHeaderText("Enter Booking ID");
        dialog.showAndWait().ifPresent(id -> {
            try {
                controller.checkInCustomer(Integer.parseInt(id));
                showAlert("Success", "Check-in successful!");
            } catch (Exception e) {
                showAlert("Error", e.getMessage());
            }
        });
    }

    private void showCheckOutDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Check-out");
        dialog.setHeaderText("Enter Booking ID");
        dialog.showAndWait().ifPresent(id -> {
            try {
                controller.checkOutCustomer(Integer.parseInt(id));
                showAlert("Success", "Check-out successful! Bill generated.");
            } catch (Exception e) {
                showAlert("Error", e.getMessage());
            }
        });
    }

    private void showBookings() {
        Stage dialog = new Stage();
        TableView<Booking> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(controller.getBookings()));

        TableColumn<Booking, Integer> idCol = new TableColumn<>("Booking ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        
        TableColumn<Booking, String> customerCol = new TableColumn<>("Customer");
        customerCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getCustomer().getName()));

        table.getColumns().addAll(idCol, customerCol);
        
        VBox vbox = new VBox(table);
        Scene scene = new Scene(vbox, 400, 300);
        dialog.setScene(scene);
        dialog.setTitle("All Bookings");
        dialog.show();
    }

    private void showUpdateStatusDialog() {
        Stage dialog = new Stage();
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        TextField roomField = new TextField();
        roomField.setPromptText("Room Number");
        
        ComboBox<String> bookingCombo = new ComboBox<>(FXCollections.observableArrayList("AVAILABLE", "BOOKED"));
        ComboBox<String> occupancyCombo = new ComboBox<>(FXCollections.observableArrayList("VACANT", "OCCUPIED"));
        ComboBox<String> cleanCombo = new ComboBox<>(FXCollections.observableArrayList("CLEAN", "DIRTY"));

        Button updateBtn = new Button("Update");
        updateBtn.setOnAction(e -> {
            try {
                int roomNum = Integer.parseInt(roomField.getText());
                Room room = controller.findRoom(roomNum);
                if (room == null) {
                    showAlert("Error", "Room not found!");
                    return;
                }
                if (bookingCombo.getValue() != null) {
                    room.setBookingStatus(BookingStatus.valueOf(bookingCombo.getValue()));
                }
                if (occupancyCombo.getValue() != null) {
                    room.setOccupancyStatus(OccupancyStatus.valueOf(occupancyCombo.getValue()));
                }
                if (cleanCombo.getValue() != null) {
                    room.setCleanlinessStatus(CleanlinessStatus.valueOf(cleanCombo.getValue()));
                }
                showAlert("Success", "Room status updated!");
                dialog.close();
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        vbox.getChildren().addAll(new Label("Update Room Status"), roomField, 
            new Label("Booking:"), bookingCombo,
            new Label("Occupancy:"), occupancyCombo,
            new Label("Cleanliness:"), cleanCombo, updateBtn);
        Scene scene = new Scene(vbox, 300, 300);
        dialog.setScene(scene);
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

