package controller;


import java.net.URL;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

/** This is the class for the main controller, or the main page of the program.*/
public class Controller implements Initializable
{
    /** This method doesn't initialize anything.
     * @param resourceBundle
     * @param url */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }
    /** This method initializes the Customer page.
     * @param actionEvent
     * @throws Exception*/
    public void Customer_Press(ActionEvent actionEvent) throws Exception
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/views/Customer.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
            stage.setTitle("Customer Records");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e)
        {
            Alert alert_err = new Alert(Alert.AlertType.WARNING);
            alert_err.setTitle("Something went wrong.");
            alert_err.setContentText("Please restart the program and try again.");
            alert_err.showAndWait();
            e.printStackTrace();
        }

    }
    /** This method initializes the Appointments page.
     * @param actionEvent
     * @throws Exception*/
    public void Appointment_Press(ActionEvent actionEvent) throws Exception
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/views/Appointments.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 1100, 600);
            stage.setTitle("Appointments");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception k)
        {
            Alert alert_err = new Alert(Alert.AlertType.WARNING);
            alert_err.setTitle("Something went wrong.");
            alert_err.setContentText("Please restart the program and try again.");
            alert_err.showAndWait();
        }
    }
    /** This method initializes the Reports page.
     * @param actionEvent
     * @throws Exception*/
    public void Reports_Press(ActionEvent actionEvent) throws Exception
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/views/Reports.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
            stage.setTitle("Reports");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception j)
        {
            Alert alert_err = new Alert(Alert.AlertType.WARNING);
            alert_err.setTitle("Something went wrong.");
            alert_err.setContentText("Please restart the program and try again.");
            alert_err.showAndWait();
        }
    }
    /** This method exits the program.
     * @param actionEvent */
    public void Exit_Press(ActionEvent actionEvent)
    {
        System.exit(0);
    }
}
