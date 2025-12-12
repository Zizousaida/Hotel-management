package view;

import controller.HotelController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ManagerWindow {
    private Stage stage;
    private HotelController controller;

    public ManagerWindow(Stage stage, HotelController controller) {
        this.stage = stage;
        this.controller = controller;
    }

    public Scene createScene() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label title = new Label("Manager Dashboard");
        title.setStyle("-fx-font-size: 16px;");

        Button viewBillsBtn = new Button("View All Bills");
        Button revenueBtn = new Button("Calculate Revenue");
        Button viewRoomsBtn = new Button("View All Rooms");
        Button viewBookingsBtn = new Button("View All Bookings");
        Button logoutBtn = new Button("Logout");

        viewBillsBtn.setOnAction(e -> showBills());
        revenueBtn.setOnAction(e -> showRevenue());
        viewRoomsBtn.setOnAction(e -> showRooms());
        viewBookingsBtn.setOnAction(e -> showBookings());
        logoutBtn.setOnAction(e -> {
            view.LoginWindow login = new view.LoginWindow(stage, controller);
            stage.setScene(login.createScene());
        });

        root.getChildren().addAll(title, viewBillsBtn, revenueBtn, viewRoomsBtn, viewBookingsBtn, logoutBtn);
        return new Scene(root, 400, 300);
    }

    private void showBills() {
        Stage dialog = new Stage();
        TableView<model.Bill> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(controller.getBills()));

        TableColumn<model.Bill, Integer> idCol = new TableColumn<>("Bill ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("billId"));
        
        TableColumn<model.Bill, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        table.getColumns().addAll(idCol, amountCol);
        
        VBox vbox = new VBox(table);
        Scene scene = new Scene(vbox, 300, 200);
        dialog.setScene(scene);
        dialog.setTitle("All Bills");
        dialog.show();
    }

    private void showRevenue() {
        double revenue = controller.getTotalRevenue();
        showAlert("Total Revenue", String.format("Total Revenue: $%.2f", revenue));
    }

    private void showRooms() {
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

    private void showBookings() {
        Stage dialog = new Stage();
        TableView<model.Booking> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(controller.getBookings()));

        TableColumn<model.Booking, Integer> idCol = new TableColumn<>("Booking ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("bookingId"));

        table.getColumns().addAll(idCol);
        
        VBox vbox = new VBox(table);
        Scene scene = new Scene(vbox, 300, 200);
        dialog.setScene(scene);
        dialog.setTitle("All Bookings");
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

