package controller;

import dao.Appointments_Access;
import dao.Users_Access;
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
            Locale.setDefault(locale);
            ZoneId zone_id = ZoneId.systemDefault();
            rb = ResourceBundle.getBundle("language/login", Locale.getDefault());
            Location_label.setText(rb.getString("Locale"));
            //loginField.setText(rb.getString("Login"));
            username_label.setText(rb.getString("username"));
            password_label.setText(rb.getString("password"));
            Login_Button.setText(rb.getString("Login"));
            CancelButton.setText(rb.getString("Exit"));
            //locationText.setText(rb.getString("Location"));
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

            ResourceBundle rb = ResourceBundle.getBundle("language/login", Locale.getDefault());

            FileWriter WriteToFile = new FileWriter("login_activity.txt", true);
            PrintWriter recordFile = new PrintWriter(WriteToFile);

            String userName = Username_textfield.getText();
            String password = Password_textfield.getText();
            int userId = Users_Access.validation(userName, password);

            if (userId > 0)
            {
                System.out.println("userID: " + userId);
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


                    for (Appointments appointment : allAppointmentsList)                                                //for loop here to make local time
                    {
                        LocalDateTime temp_Start = appointment.getStart();
                        ZonedDateTime ZDT_start = temp_Start.atZone(ZoneId.of("UTC"));
                        ZonedDateTime ZDT_final_start = ZDT_start.withZoneSameInstant(systemZone);
                        LocalDateTime Start = ZDT_final_start.toLocalDateTime();

                        LocalDateTime temp_End = appointment.getEnd();
                        ZonedDateTime ZDT_end = temp_End.atZone(ZoneId.of("UTC"));
                        ZonedDateTime ZDT_final_end = ZDT_end.withZoneSameInstant(systemZone);
                        LocalDateTime End = ZDT_final_end.toLocalDateTime();

                        Integer Appointment_ID = appointment.getAppointment_ID();
                        String Title = appointment.getTitle();
                        String Description = appointment.getDescription();
                        String Location = appointment.getLocation();
                        String Type = appointment.getType();
                        Integer Customer_ID = appointment.getCustomer_ID();
                        Integer User_ID = appointment.getUser_ID();
                        Integer Contact_ID = appointment.getContact_ID();

                        Appointments Appointment = new Appointments(Appointment_ID,Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID);
                        LocalAppointmentsList.add(Appointment);
                    }

                    //check for upcoming appointments if user is validated
                    for (Appointments appointment: LocalAppointmentsList)
                    {
//                        System.out.println("currentTimeMinus15Min: " + currentTimeMinus15Min);
//                        System.out.println("currentTimePlus15Min: " + currentTimePlus15Min);
                        startTime = appointment.getStart();
//                        System.out.println("startTime: " + startTime + "\n");
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
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Invalid Login");
                alert_err.setContentText("Please reenter and try again.");
                alert_err.showAndWait();

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