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
import dao.Appointments_Access;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;


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

    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            //Locale locale = new Locale("fr");
            //ZoneId zone_id = ZoneId.systemDefault();
            //resourceBundle = ResourceBundle.getBundle("language/login", locale);

            Locale locale = Locale.getDefault();
            Locale.setDefault(locale);

            resourceBundle = ResourceBundle.getBundle("language/login", Locale.getDefault());

            Location_label.setText(resourceBundle.getString("Locale"));
            username_label.setText(resourceBundle.getString("username"));
            password_label.setText(resourceBundle.getString("password"));
            Login_Button.setText(resourceBundle.getString("login"));
            CancelButton.setText(resourceBundle.getString("exit"));
        }
        catch (Exception e)
        {
            System.out.println("Resourcebundle not found");
        }
    }

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

    private void navigateToMainScreen(ActionEvent actionEvent) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/views/Main_Screen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

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

    private void showAppointmentAlert(int appointmentID, LocalDateTime apptTime)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment within 15 minutes: " + appointmentID +
                " and appointment start time of: " + apptTime);
        Optional<ButtonType> confirmation = alert.showAndWait();
        System.out.println("There is an appointment within 15 minutes");
    }

    private void showNoAppointmentsAlert()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No upcoming appointments.");
        Optional<ButtonType> confirmation = alert.showAndWait();
        System.out.println("No upcoming appointments");
    }

    private void showLoginErrorAlert(ResourceBundle resourceBundle)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR, "errorHeader");
        alert.setTitle(resourceBundle.getString("errorTitle"));
        alert.setContentText(resourceBundle.getString("errorText"));
        alert.showAndWait();
    }

    public void Cancel_Press(ActionEvent actionEvent)
    {
        System.exit(0);
    }
}