package controller;

import dao.Appointments_Access;
import dao.Users_Access;
import helper.Time;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointments;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.time.ZoneId;

public class Login_Screen implements Initializable
{
    public TextField Username_textfield;
    public TextField Password_textfield;
    public Label Location_label;
    public Label Error_Message;
    public Button Login_Button;
    public Button CancelButton;
    public Label username_label;
    public Label password_label;

    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            Locale locale = Locale.getDefault();
            //Locale locale = new Locale("fr");
            Locale.setDefault(locale);
            ZoneId zone_id = ZoneId.systemDefault();
            rb = ResourceBundle.getBundle("language/login", Locale.getDefault());
            //rb = ResourceBundle.getBundle("language/login", locale);
            Location_label.setText(rb.getString("Locale"));
            username_label.setText(rb.getString("username"));
            password_label.setText(rb.getString("password"));
            Login_Button.setText(rb.getString("login"));
            CancelButton.setText(rb.getString("exit"));
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
            ObservableList<Appointments> getAllAppointments = Appointments_Access.getAppointments();
            ObservableList<Appointments> allAppointmentsList = Appointments_Access.getAppointments();
            ObservableList<Appointments> LocalAppointmentsList = Appointments_Access.getAppointments();

            LocalDateTime currentTimeMinus15Min = LocalDateTime.now().minusMinutes(15);
            LocalDateTime currentTimePlus15Min = LocalDateTime.now().plusMinutes(15);
            LocalDateTime startTime;
            ZoneId systemZone = ZoneId.systemDefault();
            System.out.println(systemZone);
            int getAppointmentID = 0;
            LocalDateTime displayTime = null;
            boolean appointmentWithin15Min = false;

            //Locale locale = new Locale("fr");
            ResourceBundle rb = ResourceBundle.getBundle("language/login", Locale.getDefault());
            //ResourceBundle rb = ResourceBundle.getBundle("language/login", locale);

            FileWriter WriteToFile = new FileWriter("login_activity.txt", true);
            PrintWriter recordFile = new PrintWriter(WriteToFile);

            String userName = Username_textfield.getText();
            String password = Password_textfield.getText();
            int userId = Users_Access.validation(userName, password);

            if (userId > 0)
            {
                try
                {
                    FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/views/Main_Screen.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
                    stage.setTitle("Main Screen");
                    stage.setScene(scene);
                    stage.show();

                    //log login
                    recordFile.print(userName + " Logged in at " + Timestamp.valueOf(LocalDateTime.now()) + "\n");     // reqs need user printed too?

                    LocalAppointmentsList = Time.convertTimeDateLocal();

                    //check for upcoming appointments if user is validated
                    for (Appointments appointment: LocalAppointmentsList)
                    {
                        startTime = appointment.getStart();
                        if ((startTime.isAfter(currentTimeMinus15Min) || startTime.isEqual(currentTimeMinus15Min)) && (startTime.isBefore(currentTimePlus15Min) || (startTime.isEqual(currentTimePlus15Min))))
                        {
                            getAppointmentID = appointment.getAppointment_ID();
                            displayTime = startTime;
                            appointmentWithin15Min = true;
                        }
                    }
                    if (appointmentWithin15Min)
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment within 15 minutes: " + getAppointmentID + " and appointment start time of: " + displayTime);
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("There is an appointment within 15 minutes");
                    }
                    else                                                                                                // this fires
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No upcoming appointments.");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("no upcoming appointments");
                    }
                }
                catch (Exception e)
                {
                    Alert alert_err = new Alert(Alert.AlertType.WARNING);
                    alert_err.setTitle("Invalid Login");
                    alert_err.setContentText("Please reenter and try again.");
                    alert_err.showAndWait();
                }
            }
            else if (userId < 0)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "errorHeader");
                alert.setTitle(rb.getString("errorTitle"));
                alert.setContentText(rb.getString("errorText"));
                alert.showAndWait();

                //log failed attempt
                recordFile.print(userName + " Failed to login." + Timestamp.valueOf(LocalDateTime.now()) + "\n");           //moved to after
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