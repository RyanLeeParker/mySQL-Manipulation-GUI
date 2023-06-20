package controller;

import dao.Customers;
import dao.Appointments;
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
    public void start(Stage stage) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/Main_Screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) throws SQLException
    {
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
