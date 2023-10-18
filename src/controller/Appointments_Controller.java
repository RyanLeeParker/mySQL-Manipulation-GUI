package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dao.Appointments_Access;
import dao.Contacts_Access;
import dao.Country_Access;
import dao.Customer_Access;
import dao.FirstLevelDivision_Access;
import dao.Report_Access;
import dao.Users_Access;
import helper.JDBC;
import helper.Time;
import model.Appointments;
import model.Contacts;
import model.Country;
import model.Customers;
import model.First_Level_Division;
import model.Reports;
import model.Users;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import model.*;
import dao.*;

import dao.Appointments_Access;
import dao.Country_Access;
import dao.Customer_Access;
import dao.FirstLevelDivision_Access;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import helper.JDBC;
import model.Customers;
import model.*;
import java.sql.Connection;
import model.Country;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import helper.Time;
import java.time.ZoneId;

import static helper.Time.convertTimeDateUTC;

//import static Main.timeUtil.convertTimeDateUTC;

public class Appointments_Controller
{
    public TextField Appt_ID_Input;
    public TextField Appt_Name_Input;
    public TextField Appt_Desc_Input;
    public TextField Appt_Loc_Input;
    public TextField Appt_Type_Input;
    public TextField Appt_Cust_ID_Input;
    public TextField Appt_UserID_Input;
    public RadioButton Week_Radio;
    public ToggleGroup Appt_Toggle;
    public RadioButton Month_Radio;
    public TableView Appointment_Table;
    public TableColumn Appointment_ID_Column;
    public TableColumn Appointment_Title_Column;
    public TableColumn Appointment_Desc_Column;
    public TableColumn Appt_Loc_Column;
    public TableColumn Appt_Type_Column;
    public TableColumn Appt_StartDateTime_Column;
    public TableColumn Appt_EndDateTime_Coulmn;
    public TableColumn Appt_CustID_Column;
    public TableColumn Appt_ContactID_Column;
    public TableColumn Appt_UserID_Column;
    public Button AddButton;
    public Button Edit_Button;
    public Button DeleteButton;
    public Button SaveButton;
    public Button Cancel_Button;
    public ComboBox Appointment_EndTime;
    public ComboBox Appointment_Contact_CB;
    public DatePicker Appt_StartDate_Picker;
    public DatePicker Appt_EndDate_Picker;
    public ComboBox Appointment_TimeStart_CB;
    public RadioButton All_Radio;

    public static int Appt_ID;

    public void initialize() throws Exception                                                                               // apply cust id fix to appts
    {
        // time should be stored UTC, but converted to local time for user      3 timezones: UTC, EST, SystemDefault
        Connection connect = JDBC.openConnection();

        ObservableList<Contacts> contactsObservableList = Contacts_Access.getContacts();
        ObservableList<Appointments> allAppointmentsList = Appointments_Access.getAppointments();               // should be showing as local time
        ObservableList<Appointments> temp_allAppointmentsList = FXCollections.observableArrayList();
        ObservableList<String> allContactsNames = FXCollections.observableArrayList();


        // lambda #2
        contactsObservableList.forEach(contacts -> allContactsNames.add(contacts.getContact_Name()));
        // here
        ObservableList<String> appointmentTimes = FXCollections.observableArrayList();

        Appointment_ID_Column.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        Appointment_Title_Column.setCellValueFactory(new PropertyValueFactory<>("Title"));
        Appointment_Desc_Column.setCellValueFactory(new PropertyValueFactory<>("Description"));
        Appt_Loc_Column.setCellValueFactory(new PropertyValueFactory<>("Location"));
        Appt_Type_Column.setCellValueFactory(new PropertyValueFactory<>("Type"));
        Appt_StartDateTime_Column.setCellValueFactory(new PropertyValueFactory<>("Start"));
        Appt_EndDateTime_Coulmn.setCellValueFactory(new PropertyValueFactory<>("End"));
        Appt_CustID_Column.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        Appt_ContactID_Column.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
        Appt_UserID_Column.setCellValueFactory(new PropertyValueFactory<>("User_ID"));


         //or for loop here to make local time, since it's already saved as UTC in DB?
        for (Appointments appointment : allAppointmentsList)
        {
            //(allAppointmentsList.getAppointments(appointment))

            LocalDateTime temp_Start = appointment.getStart();
            //System.out.println(temp_Start);
            LocalDateTime temp_End = appointment.getEnd();
            //System.out.println(temp_End);
            //temp_allAppointmentsList.add(temp_Start);


            // call function to convert UTC obj to local time
            // add to obslist to be be displayed in table
        }

        LocalTime firstAppointment = LocalTime.MIN.plusHours(8);
        LocalTime lastAppointment = LocalTime.MAX.minusHours(1).minusMinutes(45);

        if (!firstAppointment.equals(0) || !lastAppointment.equals(0))
        {
            while (firstAppointment.isBefore(lastAppointment))
            {
                appointmentTimes.add(String.valueOf(firstAppointment));
                firstAppointment = firstAppointment.plusMinutes(15);
            }
        }


        ZoneId systemZone = ZoneId.systemDefault();                                     // suggestions

//        for (Appointments appointment : allAppointmentsList)
//        {
//            LocalDateTime temp_Start = appointment.getStart();
//            LocalDateTime temp_End = appointment.getEnd();
//
//            // Convert UTC time to system's local time zone
//            ZonedDateTime localStart = temp_Start.atZone(ZoneId.of("UTC")).withZoneSameInstant(systemZone);
//            ZonedDateTime localEnd = temp_End.atZone(ZoneId.of("UTC")).withZoneSameInstant(systemZone);
//
//            // Update the appointment's start and end times
//            appointment.setStart(localStart.toLocalDateTime());
//            appointment.setEnd(localEnd.toLocalDateTime());
//        }

        // ...

        // Set the items in your TableView
        Appointment_Table.setItems(allAppointmentsList);

        // ...














        Appointment_TimeStart_CB.setItems(appointmentTimes);
        Appointment_EndTime.setItems(appointmentTimes);
        Appointment_Contact_CB.setItems(allContactsNames);
        //Appointment_Table.setItems(appointmentTimes);
        Appointment_Table.setItems(allAppointmentsList);                                     // might just be populating with UTC, never converting back, edit grabs from here, propagates

        Appt_ID_Input.setText("Auto Gen - Disabled");
        Appt_ID_Input.setDisable(true);
        Appt_ID = allAppointmentsList.size();

        for (Appointments appointment: allAppointmentsList)                                   // fixes corner case of deletion OOO
        {
            while ((Appt_ID + 1) == appointment.getAppointment_ID())
            {
                Appt_ID++;
            }
        }

    }

    public void Add_Button(ActionEvent actionEvent) throws Exception
    {
        try
        {
            Connection connection = JDBC.openConnection();

            ObservableList<Customers> CustomersObservableList = Customer_Access.getCustomers(connection);
            ObservableList<Customers> getAllCustomers = Customer_Access.getCustomers(connection);
            ObservableList<Integer> storeCustomerIDs = FXCollections.observableArrayList();
            ObservableList<Users_Access> getAllUsers = Users_Access.getUsersList();
            ObservableList<Users_Access> UsersObservableList = Users_Access.getUsersList();
            ObservableList<Integer> storeUserIDs = FXCollections.observableArrayList();
            ObservableList<Appointments> getAllAppointments = Appointments_Access.getAppointments();

            getAllCustomers.stream().map(Customers::getCustomer_ID).forEach(storeCustomerIDs::add);
            getAllUsers.stream().map(Users::getUserId).forEach(storeUserIDs::add);

            if(Appt_Name_Input.getText().isEmpty())
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Appointment.");
                alert_err.setContentText("Please enter a valid Name to add Appointment.");
                alert_err.showAndWait();
                return;
            }
            if(Appt_Desc_Input.getText().isEmpty())
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Appointment.");
                alert_err.setContentText("Please enter a valid Description to add Appointment.");
                alert_err.showAndWait();
                return;
            }
            if(Appt_Loc_Input.getText().isEmpty())
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Appointment.");
                alert_err.setContentText("Please enter a valid Location to add Appointment.");
                alert_err.showAndWait();
                return;
            }
            if(Appt_Type_Input.getText().isEmpty())
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Appointment.");
                alert_err.setContentText("Please enter a valid Type to add Appointment.");
                alert_err.showAndWait();
                return;
            }
            if(Appt_StartDate_Picker.getValue() == null)
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Appointment.");
                alert_err.setContentText("Please enter a valid Start Date to add Appointment.");
                alert_err.showAndWait();
                return;
            }
            if(Appt_EndDate_Picker.getValue() == null)
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Appointment.");
                alert_err.setContentText("Please enter a valid End Date to add Appointment.");
                alert_err.showAndWait();
                return;
            }
            if(Appointment_TimeStart_CB.getValue() == null)
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Appointment.");
                alert_err.setContentText("Please enter a valid Start Time to add Appointment.");
                alert_err.showAndWait();
                return;
            }
            if(Appointment_EndTime.getValue() == null)
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Appointment.");
                alert_err.setContentText("Please enter a valid End Time to add Appointment.");
                alert_err.showAndWait();
                return;
            }

            boolean Cust_Valid = false;

            for (Customers customer : CustomersObservableList)                                                          // checks that custID is in range
            {
                String Cust_tempID = String.valueOf(customer.getCustomer_ID());
                if (Appt_Cust_ID_Input.getText().equals(Cust_tempID))
                {
                    Cust_Valid = true;
                }
            }

            if((Appt_Cust_ID_Input.getText().isEmpty()) || (!Cust_Valid))
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Appointment.");
                alert_err.setContentText("Please enter a valid Customer ID to add Appointment.");
                alert_err.showAndWait();
                return;
            }

            boolean User_Valid = false;

            for (Users_Access user : UsersObservableList)
            {
                System.out.println("I got here 1,UserID: " + user.getUserId());
                String Usr_tempID = String.valueOf(user.getUserId());
                if (Appt_UserID_Input.getText().equals(Usr_tempID))
                {
                    System.out.println("I got here 2,User_Input: " + Appt_UserID_Input);
                    User_Valid = true;
                }
            }
            if((Appt_UserID_Input.getText().isEmpty()) || (!User_Valid))
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Appointment.");
                alert_err.setContentText("Please enter a valid User ID to add Appointment.");
                alert_err.showAndWait();
                return;
            }
            if(Appointment_Contact_CB.getValue() == null)
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Appointment.");
                alert_err.setContentText("Please enter a valid Contact to add Appointment.");
                alert_err.showAndWait();
                return;
            }

                LocalDate localDateEnd = Appt_EndDate_Picker.getValue();
                LocalDate localDateStart = Appt_StartDate_Picker.getValue();

                DateTimeFormatter minHourFormat = DateTimeFormatter.ofPattern("HH:mm");
                String appointmentStartDate = Appt_StartDate_Picker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String appointmentStartTime = (String) Appointment_TimeStart_CB.getValue();

                String endDate = Appt_EndDate_Picker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String endTime = (String) Appointment_EndTime.getValue();

                System.out.println("thisDate + thisStart " + appointmentStartDate + " " + appointmentStartTime + ":00");
//                String startUTC = convertTimeDateUTC(appointmentStartDate + " " + appointmentStartTime + ":00");
//                String endUTC = convertTimeDateUTC(endDate + " " + endTime + ":00");
            String startUTC = appointmentStartDate + " " + appointmentStartTime + ":00";
            String endUTC = endDate + " " + endTime + ":00";


                LocalTime localTimeStart = LocalTime.parse((CharSequence) Appointment_TimeStart_CB.getValue(), minHourFormat);
                LocalTime LocalTimeEnd = LocalTime.parse((CharSequence) Appointment_EndTime.getValue(), minHourFormat);

                LocalDateTime dateTimeStart = LocalDateTime.of(localDateStart, localTimeStart);
                LocalDateTime dateTimeEnd = LocalDateTime.of(localDateEnd, LocalTimeEnd);

                ZonedDateTime zoneDtStart = ZonedDateTime.of(dateTimeStart, ZoneId.systemDefault());
                ZonedDateTime zoneDtEnd = ZonedDateTime.of(dateTimeEnd, ZoneId.systemDefault());

                ZonedDateTime convertStartEST = zoneDtStart.withZoneSameInstant(ZoneId.of("America/New_York"));
                ZonedDateTime convertEndEST = zoneDtEnd.withZoneSameInstant(ZoneId.of("America/New_York"));

                LocalTime startAppointmentTimeToCheck = convertStartEST.toLocalTime();
                LocalTime endAppointmentTimeToCheck = convertEndEST.toLocalTime();

                DayOfWeek startAppointmentDayToCheck = convertStartEST.toLocalDate().getDayOfWeek();
                DayOfWeek endAppointmentDayToCheck = convertEndEST.toLocalDate().getDayOfWeek();

                int startAppointmentDayToCheckInt = startAppointmentDayToCheck.getValue();
                int endAppointmentDayToCheckInt = endAppointmentDayToCheck.getValue();

                int workWeekStart = DayOfWeek.MONDAY.getValue();
                int workWeekEnd = DayOfWeek.FRIDAY.getValue();

                LocalTime estBusinessStart = LocalTime.of(8, 0, 0);
                LocalTime estBusinessEnd = LocalTime.of(22, 0, 0);

                if (startAppointmentDayToCheckInt < workWeekStart || startAppointmentDayToCheckInt > workWeekEnd || endAppointmentDayToCheckInt < workWeekStart || endAppointmentDayToCheckInt > workWeekEnd) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Day is outside of business operations (Monday-Friday)");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("day is outside of business hours");
                    return;
                }

                if (startAppointmentTimeToCheck.isBefore(estBusinessStart) || startAppointmentTimeToCheck.isAfter(estBusinessEnd) || endAppointmentTimeToCheck.isBefore(estBusinessStart) || endAppointmentTimeToCheck.isAfter(estBusinessEnd))
                {
                    System.out.println("time is outside of business hours");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Time is outside of business hours (8am-10pm EST): " + startAppointmentTimeToCheck + " - " + endAppointmentTimeToCheck + " EST");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    return;
                }

                int newAppointmentID = Integer.parseInt(String.valueOf((int) (Math.random() * 100)));
                int customerID = Integer.parseInt(Appt_Cust_ID_Input.getText());

                if (dateTimeStart.isAfter(dateTimeEnd)) {
                    System.out.println("Appointment has start time after end time");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment has start time after end time");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    return;
                }

                if (dateTimeStart.isEqual(dateTimeEnd)) {
                    System.out.println("Appointment has same start and end time");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment has same start and end time");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    return;
                }
                for (Appointments appointment: getAllAppointments)
                {
                    LocalDateTime checkStart = appointment.getStart();
                    LocalDateTime checkEnd = appointment.getEnd();

                    //"outer verify" meaning check to see if an appointment exists between start and end.
                    if ((customerID == appointment.getCustomer_ID()) && (newAppointmentID != appointment.getAppointment_ID()) &&
                            (dateTimeStart.isBefore(checkStart)) && (dateTimeEnd.isAfter(checkEnd))) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment overlaps with existing appointment.");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("Appointment overlaps with existing appointment.");
                        return;
                    }

                    if ((customerID == appointment.getCustomer_ID()) && (newAppointmentID != appointment.getAppointment_ID()) &&
//                            Clarification on isEqual is that this does not count as an overlapping appointment
//                            (dateTimeStart.isEqual(checkStart) || dateTimeStart.isAfter(checkStart)) &&
//                            (dateTimeStart.isEqual(checkEnd) || dateTimeStart.isBefore(checkEnd))) {
                            (dateTimeStart.isAfter(checkStart)) && (dateTimeStart.isBefore(checkEnd))) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Start time overlaps with existing appointment.");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("Start time overlaps with existing appointment.");
                        return;
                    }



                    if (customerID == appointment.getCustomer_ID() && (newAppointmentID != appointment.getAppointment_ID()) &&
//                            Clarification on isEqual is that this does not count as an overlapping appointment
//                            (dateTimeEnd.isEqual(checkStart) || dateTimeEnd.isAfter(checkStart)) &&
//                            (dateTimeEnd.isEqual(checkEnd) || dateTimeEnd.isBefore(checkEnd)))
                            (dateTimeEnd.isAfter(checkStart)) && (dateTimeEnd.isBefore(checkEnd))) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "End time overlaps with existing appointment.");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("End time overlaps with existing appointment.");
                        return;
                    }
                }

                String insertStatement = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                Appt_ID++;

                JDBC.setPreparedStatement(JDBC.getConnection(), insertStatement);
                PreparedStatement ps = JDBC.getPreparedStatement();
                ps.setInt(1, Appt_ID);
                ps.setString(2, Appt_Name_Input.getText());
                ps.setString(3, Appt_Desc_Input.getText());
                ps.setString(4, Appt_Loc_Input.getText());
                ps.setString(5, Appt_Type_Input.getText());
                //ps.setTimestamp(6, Timestamp.valueOf(startLocalDateTimeToAdd));
                //ps.setTimestamp(6, Timestamp.valueOf(startUTC));
                //ps.setTimestamp(7, Timestamp.valueOf(endUTC));

            ps.setString(6, startUTC);
            ps.setString(7, endUTC);

                //need to verify this is correct
                ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(9, "admin");
                ps.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
                ps.setInt(11, 1);
                ps.setInt(12, Integer.parseInt(Appt_Cust_ID_Input.getText()));
                ps.setInt(13, Integer.parseInt(Contacts_Access.findContactID((String) Appointment_Contact_CB.getValue())));
                ps.setInt(14, Integer.parseInt(Contacts_Access.findContactID(Appt_UserID_Input.getText())));

                //System.out.println("ps " + ps);
                ps.execute();

            }
        catch (SQLException throwables)
        {
            //throwables.printStackTrace();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/views/Appointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();

    }

    public void Edit_Button(ActionEvent actionEvent)
    {
        try
        {
            JDBC.openConnection();
            Appointments selectedAppointment = (Appointments) Appointment_Table.getSelectionModel().getSelectedItem();

            if (selectedAppointment != null)
            {

                //get all contact info and fill ComboBox.
                ObservableList<Contacts> contactsObservableList = Contacts_Access.getContacts();
                ObservableList<String> allContactsNames = FXCollections.observableArrayList();
                String displayContactName = "";

                for (Contacts contacts : contactsObservableList) {
                    allContactsNames.add(contacts.getContact_Name());
                }

                Appointment_Contact_CB.setItems(allContactsNames);

                for (Contacts contact: contactsObservableList)
                {
                    if (selectedAppointment.getContact_ID() == contact.getContact_ID())
                    {
                        displayContactName = contact.getContact_Name();
                    }
                }

                Appt_ID_Input.setText(String.valueOf(selectedAppointment.getAppointment_ID()));
                Appt_Name_Input.setText(selectedAppointment.getTitle());
                Appt_Desc_Input.setText(selectedAppointment.getDescription());
                Appt_Loc_Input.setText(selectedAppointment.getLocation());
                Appt_Type_Input.setText(selectedAppointment.getType());
                Appt_Cust_ID_Input.setText(String.valueOf(selectedAppointment.getCustomer_ID()));
                Appt_StartDate_Picker.setValue(selectedAppointment.getStart().toLocalDate());
                Appt_EndDate_Picker.setValue(selectedAppointment.getEnd().toLocalDate());
                Appointment_TimeStart_CB.setValue(String.valueOf(selectedAppointment.getStart().toLocalTime()));
                Appointment_EndTime.setValue(String.valueOf(selectedAppointment.getEnd().toLocalTime()));
                Appt_UserID_Input.setText(String.valueOf(selectedAppointment.getUser_ID()));                // might need to temp enable show text field here, disable on save
                Appointment_Contact_CB.setValue(displayContactName);

                ObservableList<String> appointmentTimes = FXCollections.observableArrayList();

                LocalTime firstAppointment = LocalTime.MIN.plusHours(8);
                LocalTime lastAppointment = LocalTime.MAX.minusHours(1).minusMinutes(45);

                //if statement fixed issue with infinite loop
                if (!firstAppointment.equals(0) || !lastAppointment.equals(0))
                {
                    while (firstAppointment.isBefore(lastAppointment))
                    {
                        appointmentTimes.add(String.valueOf(firstAppointment));
                        firstAppointment = firstAppointment.plusMinutes(15);
                    }
                }
                Appointment_TimeStart_CB.setItems(appointmentTimes);
                Appointment_EndTime.setItems(appointmentTimes);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void Delete_Button(ActionEvent actionEvent) throws Exception
    {
        //custom message displayed

        try
        {
            Connection connect = JDBC.openConnection();
            Appointments tempAppointment_1 = (Appointments)  Appointment_Table.getSelectionModel().getSelectedItem();
            int Appt_to_del_id_1 = tempAppointment_1.getCustomer_ID();
            String App_to_del_type_1 = tempAppointment_1.getType();
            int Appt_to_del_ApptID = tempAppointment_1.getAppointment_ID();

            //lert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete the selected appointment with appointment id: " + Appt_to_del_id_1 + " and appointment type " + App_to_del_type_1);

            //Optional<ButtonType> confirmation = alert.showAndWait();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setContentText("Do you want to delete Appointment ID: "  + Appt_to_del_ApptID  + " Type: " + App_to_del_type_1 + "?");
            alert.setHeaderText("Confirm Deletion");

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK)
            {
                System.out.println("Apt ID: " + Appt_to_del_ApptID);
                Appointments_Access.removeAppointment(Appt_to_del_ApptID, connect);

                ObservableList<Appointments> allAppointmentsList = Appointments_Access.getAppointments();
                Appointment_Table.setItems(allAppointmentsList);

            }
            else if (result.get() == ButtonType.CANCEL) {return;}

//            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK)
//            {
//                Appointments_Access.removeAppointment(Appt_to_del_id_1, connect);
//
//                ObservableList<Appointments> allAppointmentsList = Appointments_Access.getAppointments();
//                Appointment_Table.setItems(allAppointmentsList);
//            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void Save_Button(ActionEvent actionEvent) throws Exception         // every save adds 5hrs to appointment      // Make sure Cust ID and maybe user ID are within range
    {
        try
        {
            Connection connection = JDBC.openConnection();

            if(Appt_Name_Input.getText().isEmpty())
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Appointment.");
                alert_err.setContentText("Please enter a valid Name to add Appointment.");
                alert_err.showAndWait();
                return;
            }
            if(Appt_Desc_Input.getText().isEmpty())
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Appointment.");
                alert_err.setContentText("Please enter a valid Description to add Appointment.");
                alert_err.showAndWait();
                return;
            }
            if(Appt_Loc_Input.getText().isEmpty())
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Appointment.");
                alert_err.setContentText("Please enter a valid Location to add Appointment.");
                alert_err.showAndWait();
                return;
            }
            if(Appt_Type_Input.getText().isEmpty())
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Appointment.");
                alert_err.setContentText("Please enter a valid Type to add Appointment.");
                alert_err.showAndWait();
                return;
            }
            if(Appt_StartDate_Picker.getValue() == null)
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Appointment.");
                alert_err.setContentText("Please enter a valid Start Date to add Appointment.");
                alert_err.showAndWait();
                return;
            }
            if(Appt_EndDate_Picker.getValue() == null)
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Appointment.");
                alert_err.setContentText("Please enter a valid End Date to add Appointment.");
                alert_err.showAndWait();
                return;
            }
            if(Appointment_TimeStart_CB.getValue() == null)
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Appointment.");
                alert_err.setContentText("Please enter a valid Start Time to add Appointment.");
                alert_err.showAndWait();
                return;
            }
            if(Appointment_EndTime.getValue() == null)
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Appointment.");
                alert_err.setContentText("Please enter a valid End Time to add Appointment.");
                alert_err.showAndWait();
                return;
            }
            if(Appt_Cust_ID_Input.getText().isEmpty())
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Appointment.");
                alert_err.setContentText("Please enter a valid Customer ID to add Appointment.");
                alert_err.showAndWait();
                return;
            }
            if(Appt_UserID_Input.getText().isEmpty())
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Appointment.");
                alert_err.setContentText("Please enter a valid User ID to add Appointment.");
                alert_err.showAndWait();
                return;
            }


                ObservableList<Customers> getAllCustomers = Customer_Access.getCustomers(connection);
                ObservableList<Integer> storeCustomerIDs = FXCollections.observableArrayList();
                ObservableList<Users_Access> getAllUsers = Users_Access.getUsersList();
                ObservableList<Integer> storeUserIDs = FXCollections.observableArrayList();
                ObservableList<Appointments> getAllAppointments = Appointments_Access.getAppointments();

                for (Customers customer : getAllCustomers)
                {
                    storeCustomerIDs.add(customer.getCustomer_ID());
                }

                for (Users user : getAllUsers)
                {
                    storeUserIDs.add(user.getUserId());
                }

                LocalDate localDateEnd = Appt_EndDate_Picker.getValue();
                LocalDate localDateStart = Appt_StartDate_Picker.getValue();

                DateTimeFormatter minHourFormat = DateTimeFormatter.ofPattern("HH:mm");

                LocalTime localTimeStart = LocalTime.parse((CharSequence) Appointment_TimeStart_CB.getValue(), minHourFormat);
            //System.out.println("1: LocalTimeStart: " + localTimeStart);
                LocalTime LocalTimeEnd = LocalTime.parse((CharSequence) Appointment_EndTime.getValue(), minHourFormat);

                LocalDateTime dateTimeStart = LocalDateTime.of(localDateStart, localTimeStart);
            //System.out.println("2: dateTimeStart: " + dateTimeStart);
                LocalDateTime dateTimeEnd = LocalDateTime.of(localDateEnd, LocalTimeEnd);

                ZonedDateTime zoneDtStart = ZonedDateTime.of(dateTimeStart, ZoneId.systemDefault());
            //System.out.println("3: zondDTStart: " + zoneDtStart);
                ZonedDateTime zoneDtEnd = ZonedDateTime.of(dateTimeEnd, ZoneId.systemDefault());

                ZonedDateTime convertStartEST = zoneDtStart.withZoneSameInstant(ZoneId.of("America/New_York"));                 // might be all this bullshit here
            System.out.println("4: convertStartEST: " + convertStartEST);
                ZonedDateTime convertEndEST = zoneDtEnd.withZoneSameInstant(ZoneId.of("America/New_York"));
            System.out.println("4: convertEndEST: " + convertEndEST);

                if (convertStartEST.toLocalDate().getDayOfWeek().getValue() == (DayOfWeek.SATURDAY.getValue()) || convertStartEST.toLocalDate().getDayOfWeek().getValue() == (DayOfWeek.SUNDAY.getValue()) || convertEndEST.toLocalDate().getDayOfWeek().getValue() == (DayOfWeek.SATURDAY.getValue())  || convertEndEST.toLocalDate().getDayOfWeek().getValue() == (DayOfWeek.SUNDAY.getValue()) )
                {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please schedule within business hours. (Mon-Fri)");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("Please schedule within business hours.");
                    return;
                }

                if (convertStartEST.toLocalTime().isBefore(LocalTime.of(8, 0, 0)) || convertStartEST.toLocalTime().isAfter(LocalTime.of(22, 0, 0)) || convertEndEST.toLocalTime().isBefore(LocalTime.of(8, 0, 0)) || convertEndEST.toLocalTime().isAfter(LocalTime.of(22, 0, 0)))
                {
                    System.out.println("Please schedule within business hours.");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please schedule within business hours. (8am-10pm EST): " + convertStartEST.toLocalTime() + " - " + convertEndEST.toLocalTime() + " EST");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    return;
                }

                int newCustomerID = Integer.parseInt(Appt_Cust_ID_Input.getText());
                int appointmentID = Integer.parseInt(Appt_ID_Input.getText());

//            System.out.println("5: LocalTimeStart: " + localTimeStart);
//            System.out.println("6: dateTimeStart: " + dateTimeStart);
//            System.out.println("7: zondDTStart: " + zoneDtStart);
//            System.out.println("8: convertStartEST: " + convertStartEST);


                if (dateTimeStart.isAfter(dateTimeEnd))
                {
                    System.out.println("Please schedule within business hours.");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please schedule within business hours.");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    return;
                }

                if (dateTimeStart.isEqual(dateTimeEnd))
                {
                    System.out.println("Appointment has the same start and end time");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment has the same start and end time");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    return;
                }

                for (Appointments appointment: getAllAppointments)
                {
                    LocalDateTime checkStart = appointment.getStart();
                    LocalDateTime checkEnd = appointment.getEnd();

                    //"outer verify" meaning check to see if an appointment exists between start and end.
                    if ((newCustomerID == appointment.getCustomer_ID()) && (appointmentID != appointment.getAppointment_ID()) &&
                            (dateTimeStart.isBefore(checkStart)) && (dateTimeEnd.isAfter(checkEnd)))
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment already exists at that time.");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("Appointment already exists at that time.");
                        return;
                    }

                    if ((newCustomerID == appointment.getCustomer_ID()) && (appointmentID != appointment.getAppointment_ID()) &&
//                            Clarification on isEqual is that this does not count as an overlapping appointment
//                            (dateTimeStart.isEqual(checkStart) || dateTimeStart.isAfter(checkStart)) &&
//                            (dateTimeStart.isEqual(checkEnd) || dateTimeStart.isBefore(checkEnd))) {
                            (dateTimeStart.isAfter(checkStart)) && (dateTimeStart.isBefore(checkEnd)))
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment already exists at that time.");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("Appointment already exists at that time.");
                        return;
                    }

                    if (newCustomerID == appointment.getCustomer_ID() && (appointmentID != appointment.getAppointment_ID()) &&
//                            Clarification on isEqual is that this does not count as an overlapping appointment
//                            (dateTimeEnd.isEqual(checkStart) || dateTimeEnd.isAfter(checkStart)) &&
//                            (dateTimeEnd.isEqual(checkEnd) || dateTimeEnd.isBefore(checkEnd)))
                            (dateTimeEnd.isAfter(checkStart)) && (dateTimeEnd.isBefore(checkEnd)))
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment already exists at that time.");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("Appointment already exists at that time.");
                        return;
                    }
                }

                String startDate = Appt_StartDate_Picker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String startTime = (String) Appointment_TimeStart_CB.getValue();

                String endDate = Appt_EndDate_Picker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String endTime = (String) Appointment_EndTime.getValue();

                //String startUTC = convertTimeDateUTC(startDate + " " + startTime + ":00");                                      // likely here, Yup it's converting UTC into UTC, adding 5hrs.
            String startUTC = startDate + " " + startTime + ":00";
            //String endUTC = convertTimeDateUTC(endDate + " " + endTime + ":00");                                            // and here
            String endUTC = endDate + " " + endTime + ":00";



            System.out.println("1: SUTC: " + startUTC + " EUTC: " + endUTC);                                                            // time change happens before here

                String insertStatement = "UPDATE appointments SET Appointment_ID = ?, Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

                JDBC.setPreparedStatement(JDBC.getConnection(), insertStatement);
                PreparedStatement ps = JDBC.getPreparedStatement();
                ps.setInt(1, Integer.parseInt(Appt_ID_Input.getText()));
                ps.setString(2, Appt_Name_Input.getText());
                ps.setString(3, Appt_Desc_Input.getText());
                ps.setString(4, Appt_Loc_Input.getText());
                ps.setString(5, Appt_Type_Input.getText());

            //ps.setTimestamp(6, Timestamp.valueOf(startUTC));
            //ps.setTimestamp(7, Timestamp.valueOf(endUTC));

                ps.setString(6, startUTC);
                ps.setString(7, endUTC);
                ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(9, "admin");
                ps.setInt(10, Integer.parseInt(Appt_Cust_ID_Input.getText()));
                ps.setInt(11, Integer.parseInt(Appt_UserID_Input.getText()));
                ps.setInt(12, Integer.parseInt(Contacts_Access.findContactID((String) Appointment_Contact_CB.getValue())));
                ps.setInt(13, Integer.parseInt(Appt_ID_Input.getText()));

                System.out.println("ps " + ps);
                //System.out.println("2: ST: " + startTime + " ET: " + endTime);
                ps.execute();
                //System.out.println("3: ST: " + startTime + " ET: " + endTime);

                // loop here to reconvert back to local time, otherwise it displays UTC


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/views/Appointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Add Appointments");
        stage.setScene(scene);
        stage.show();

    }

    public void Cancel_Button(ActionEvent actionEvent)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/views/Main_Screen.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }
        catch (IllegalStateException | IOException e)
        {
            Alert alert_err = new Alert(Alert.AlertType.WARNING);
            alert_err.setTitle("Something went wrong.");
            alert_err.setContentText("Please restart the program and try again.");
            alert_err.showAndWait();
        }
    }

    public void EndTime_CB_Select(ActionEvent actionEvent) {
    }

    public void Appt_Cont_CB_Select(ActionEvent actionEvent) {
    }

    public void Appt_StartDate_Picked(ActionEvent actionEvent) {
    }

    public void Appt_EndDate_Picked(ActionEvent actionEvent) {
    }

    public void TimeStart_CB_Select(ActionEvent actionEvent) {
    }

    public void All_Radio_Selected(ActionEvent actionEvent) {
    }

    public void Week_Radio_Selected(ActionEvent actionEvent) {
    }

    public void Month_Radio_Selected(ActionEvent actionEvent) {
    }
}
