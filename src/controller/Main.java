package controller;


import helper.JDBC;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;

/** Main class of application. This class launches the main controller that runs the program.*/
public class Main extends Application
{
    /**
     * This is the start method which initializes the fxml login screen.
     * @param stage initiates fxml
     * @throws Exception
     * */
    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setScene(scene);
        stage.show();
    }

    /** Main method of the program, loads the database connector and then launches the fxml module.*/
    public static void main(String[] args) throws SQLException
    {
        JDBC.openConnection();

        launch(args);

        JDBC.closeConnection();
    }
}
