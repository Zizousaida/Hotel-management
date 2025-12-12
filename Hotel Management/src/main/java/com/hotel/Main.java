package com.hotel;

import com.hotel.controller.HotelController;
import com.hotel.model.*;
import com.hotel.view.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Hotel hotel = new Hotel("Grand Hotel", "123 Main Street");
        HotelController controller = new HotelController(hotel);
        
        initializeData(hotel, controller);
        
        primaryStage.setTitle("Hotel Management System");
        LoginView loginView = new LoginView(primaryStage, controller);
        primaryStage.setScene(loginView.createScene());
        primaryStage.show();
    }

    private void initializeData(Hotel hotel, HotelController controller) {
        hotel.addRoom(new Room(101, "Single", 80.0));
        hotel.addRoom(new Room(102, "Single", 80.0));
        hotel.addRoom(new Room(201, "Double", 120.0));
        hotel.addRoom(new Room(202, "Double", 120.0));
        hotel.addRoom(new Room(301, "Suite", 200.0));
        hotel.addRoom(new Room(302, "Suite", 200.0));

        controller.addEmployee(new Receptionist(1, "John", "Doe", "EMP001", "receptionist", "recep123", controller));
        controller.addEmployee(new HousekeepingStaff(2, "Jane", "Smith", "EMP002", "housekeeper", "house123", controller));
        controller.addEmployee(new Manager(3, "Bob", "Johnson", "EMP003", "manager", "manager123", controller));
    }

    public static void main(String[] args) {
        launch(args);
    }
}

