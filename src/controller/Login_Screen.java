package controller;

import dao.Users_Access;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Login_Screen
{
    public TextField Username_textfield;
    public TextField Password_textfield;
    public Label Location_label;
    public Label Error_Message;
    public Button Login_Button;
    public Button CancelButton;

    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }


    public void Login_Button_Press(ActionEvent actionEvent) throws SQLException
    {
        String username = Username_textfield.getText();
        String password = Password_textfield.getText();
        int userId = Users_Access.validation( username, password);

        if (userId != 0)
        {
            try
            {
                FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/views/Main_Screen.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
                stage.setTitle("Main Screen");
                stage.setScene(scene);
                stage.show();
            }
            catch (Exception e) {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Something went wrong.");
                alert_err.setContentText("Please restart the program and try again.");
                alert_err.showAndWait();}
        }

    }

    public void Cancel_Press(ActionEvent actionEvent)
    {
        System.exit(0);
    }
}
