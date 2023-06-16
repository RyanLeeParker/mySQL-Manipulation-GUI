package controller;

import dao.Customers;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import helper.JDBC;

import java.sql.SQLException;

public class Main extends Application
{



    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("../views/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        //Locale.setDefault(new Locale("fr"));
        JDBC.openConnection();
//        int rowsAffected = Customers.insert(001, 1);        // testing link, can update with update to test further
//
//        if (rowsAffected > 0)
//        {
//            System.out.println("Insert Successful");
//        }
//        else
//        {
//            System.out.println("Insert Failed");
//        }

        launch(args);
        JDBC.closeConnection();
    }
}
