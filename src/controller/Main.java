package controller;


import helper.JDBC;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) throws SQLException
    {
        JDBC.openConnection();

        launch(args);

        JDBC.closeConnection();
    }
}
