package controller;

import dao.Users_Access;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.time.ZoneId;
import java.util.MissingResourceException;

public class Login_Screen implements Initializable
{
    public TextField Username_textfield;
    public TextField Password_textfield;
    public Label Location_label;
    public Label Error_Message;
    public Button Login_Button;
    public Button CancelButton;

    public void initialize(URL url, ResourceBundle resourcebundle)
    {
        try
        {
            //timezone stuff
            //Locale.setDefault(new Locale("fr"));

            Locale locale = Locale.getDefault();
            Locale.setDefault(locale);
            ZoneId zone_id = ZoneId.systemDefault();
            resourcebundle = ResourceBundle.getBundle("language/login", Locale.getDefault());
            Username_textfield.setText(resourcebundle.getString("username"));
            Password_textfield.setText(resourcebundle.getString("password"));
            Location_label.setText(resourcebundle.getString("location"));
        }
        catch (Exception e)
        {
            System.out.println("resourcebundle not found");
        }

    }


    public void Login_Button_Press(ActionEvent actionEvent) throws SQLException
    {
        try
        {
            // check if users appt is within 15mins, if so include apptId, date, time.  If no 15min appt then display mess saying no upcoming appts
            // can't use DB.now()m time must originate from program
            // write to file if user successfully logs in, login attempts, dates, timestamps, whether successful to file named "login_activity.txt"
            // append each record to existing file, save to root folder of application

            FileWriter WriteToFile = new FileWriter("login_activity.txt");      //add bool param?
            PrintWriter recordFile = new PrintWriter(WriteToFile);

            String username = Username_textfield.getText();
            String password = Password_textfield.getText();
            int userId = Users_Access.validation(username, password);

            if (userId != 0)
            {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/views/Main_Screen.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
                    stage.setTitle("Main Screen");
                    stage.setScene(scene);
                    stage.show();

                    //log login
                    recordFile.print(Username_textfield + " Logged in." + Timestamp.valueOf(LocalDateTime.now()) + "\n");     // reqs need user printed too?

                    //check for upcoming appts


                    //notifications needed for appts

                }
                catch (Exception e)
                {
                    Alert alert_err = new Alert(Alert.AlertType.WARNING);
                    alert_err.setTitle("Invalid Login");
                    alert_err.setContentText("Please reenter and try again.");
                    alert_err.showAndWait();
                }
            }
            else    // if does eq 0
            {
                //log failed attempt
                recordFile.print(Username_textfield + " Failed to login.");

                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Invalid Login");
                alert_err.setContentText("Please reenter and try again.");
                alert_err.showAndWait();
            }

            recordFile.close();
        }
        catch (SQLException | IOException f)
        {
            f.printStackTrace();
        }

    }

    public void Cancel_Press(ActionEvent actionEvent)
    {
        System.exit(0);
    }
}
