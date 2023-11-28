package controller;


import helper.Time;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import dao.Users_Access;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Appointments;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.util.Optional;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;

/** This class is for all login related functions.*/
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
    public Label timeZone_label;
    public Label Location;
    public Label Timezone;

    /** This initializes the login page by setting all labels and buttons to either English or French depending on the users location
     * @param url
     * @param resourceBundle */
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            //Locale locale = new Locale("fr");
            //resourceBundle = ResourceBundle.getBundle("language/login", locale);

            Locale locale = Locale.getDefault();
            Locale.setDefault(locale);
            ZoneId zone_id = ZoneId.systemDefault();
            TimeZone timeZone = TimeZone.getTimeZone(zone_id);
            String timeZoneID = timeZone.getID();

            resourceBundle = ResourceBundle.getBundle("language/login", Locale.getDefault());

            Location_label.setText(resourceBundle.getString("Locale"));
            username_label.setText(resourceBundle.getString("username"));
            password_label.setText(resourceBundle.getString("password"));
            Login_Button.setText(resourceBundle.getString("login"));
            CancelButton.setText(resourceBundle.getString("exit"));
            Location.setText(resourceBundle.getString("Location"));
            Timezone.setText(resourceBundle.getString("Timezone"));
            timeZone_label.setText(timeZoneID);
        }
        catch (Exception e)
        {
            System.out.println("Resourcebundle not found");
        }
    }

    /** This method takes the users input of username and password and validates it against the MySQL database.
     * It also records all login attempts.
     * @param actionEvent
     * @throws SQLException */
    public void Login_Button_Press(ActionEvent actionEvent) throws SQLException
    {
        try
        {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("language/login", Locale.getDefault());
            FileWriter writeToFile = new FileWriter("login_activity.txt", true);
            PrintWriter recordFile = new PrintWriter(writeToFile);

            String userNameAttempt = Username_textfield.getText();
            String passwordAttempt = Password_textfield.getText();
            int userId = Users_Access.validation(userNameAttempt, passwordAttempt);

            if (userId > 0)
            {
                recordFile.print(userNameAttempt + " Logged in at " + Timestamp.valueOf(LocalDateTime.now()) + "\n");
                navigateToMainScreen(actionEvent);
                checkForUpcomingAppointments();
            }
            else if (userId < 0)
            {
                showLoginErrorAlert(resourceBundle);
                recordFile.print(userNameAttempt + " Failed to login." + Timestamp.valueOf(LocalDateTime.now()) + "\n");
            }
            recordFile.close();
        }
        catch (SQLException | IOException e)
        {
            e.printStackTrace();
        }
    }

    /** This method is called if login is successful and opens the main controller screen.
     * @param actionEvent
     * @throws IOException */
    private void navigateToMainScreen(ActionEvent actionEvent) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/views/Main_Screen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    /** This method checks the users current time and compares it to all appointments to notify the user if they have an appointment within 15 minutes.
     * @throws SQLException*/
    private void checkForUpcomingAppointments() throws SQLException
    {
        ObservableList<Appointments> localAppointmentsList = Time.convertTimeDateLocal();
        LocalDateTime FifteenMinUntilNow = LocalDateTime.now().minusMinutes(15);
        LocalDateTime FifteenMinFromNow = LocalDateTime.now().plusMinutes(15);
        int appointmentID = 0;
        LocalDateTime apptTime = null;
        boolean appointmentWithin15Min = false;

        for (Appointments appointment : localAppointmentsList)
        {
            LocalDateTime start = appointment.getStart();
            if ((start.isAfter(FifteenMinUntilNow) || start.isEqual(FifteenMinUntilNow)) &&
                    (start.isBefore(FifteenMinFromNow) || start.isEqual(FifteenMinFromNow)))
            {
                appointmentID = appointment.getAppointment_ID();
                apptTime = start;
                appointmentWithin15Min = true;
            }
        }

        if (appointmentWithin15Min)
        {
            showAppointmentAlert(appointmentID, apptTime);
        }
        else
        {
            showNoAppointmentsAlert();
        }
    }
    /** This method shows the alert is the appointment is within 15 minutes.
     * @param appointmentID
     * @param apptTime */
    private void showAppointmentAlert(int appointmentID, LocalDateTime apptTime)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment within 15 minutes: " + appointmentID +
                " and appointment start time of: " + apptTime);
        Optional<ButtonType> confirmation = alert.showAndWait();
        System.out.println("There is an appointment within 15 minutes");
    }
    /** This method shows that no appointments are within 15 minutes.*/
    private void showNoAppointmentsAlert()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No upcoming appointments.");
        Optional<ButtonType> confirmation = alert.showAndWait();
        System.out.println("No upcoming appointments");
    }
    /** This method shows the user if the login failed.
     * @param resourceBundle */
    private void showLoginErrorAlert(ResourceBundle resourceBundle)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR, "errorHeader");
        alert.setTitle(resourceBundle.getString("errorTitle"));
        alert.setContentText(resourceBundle.getString("errorText"));
        alert.showAndWait();
    }
    /** This method exits the program.
     * @param actionEvent */
    public void Cancel_Press(ActionEvent actionEvent)
    {
        System.exit(0);
    }
}