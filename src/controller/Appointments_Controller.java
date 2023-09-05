package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
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

    public void initialize() throws Exception
    {
        //contact name assigned to appt using drop down menu or CB
        // appt id autogen
        // time should be stored UTC, but converted to local time for user      3 timezones: UTC, EST, SystemDefault

        ObservableList<Appointments> allAppointmentsList = Appointments_Access.getAppointments();

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

        Appointment_Table.setItems(allAppointmentsList);

        Appt_ID_Input.setText("Auto Gen - Disabled");
        Appt_ID_Input.setDisable(true);
        Appt_ID = allAppointmentsList.size();

    }

    public void Add_Button(ActionEvent actionEvent)                         // is separate add page necessary?
    {

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

            //lert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete the selected appointment with appointment id: " + Appt_to_del_id_1 + " and appointment type " + App_to_del_type_1);

            //Optional<ButtonType> confirmation = alert.showAndWait();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setContentText("Do you want to delete ID: "  + Appt_to_del_id_1  + " Type: " + App_to_del_type_1 + "?");
            alert.setHeaderText("Confirm Deletion");

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK)
            {
                Appointments_Access.removeAppointment(Appt_to_del_id_1, connect);

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
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void Save_Button(ActionEvent actionEvent)
    {
        try
        {

            Connection connection = JDBC.startConnection();

            if (!Appt_Name_Input.getText().isEmpty()
                    && !Appt_Desc_Input.getText().isEmpty()
                    && !Appt_Loc_Input.getText().isEmpty()
                    && !Appt_Type_Input.getText().isEmpty()
                    && Appt_StartDate_Picker.getValue() != null
                    && Appt_EndDate_Picker.getValue() != null
                    && !Appointment_TimeStart_CB.getValue().isEmpty()
                    && !Appointment_EndTime.getValue().isEmpty() &&
                    !Appt_Cust_ID_Input.getText().isEmpty())
            {

                Appt_ID_Input;
                Appt_Name_Input;
                Appt_Desc_Input;
                Appt_Loc_Input;
                Appt_Type_Input;
                Appt_Cust_ID_Input;
                Appt_StartDate_Picker;
                Appt_EndDate_Picker;
                Appointment_TimeStart_CB;
                Appointment_EndTime;
                Appt_UserID_Input;
                Appointment_Contact_CB;


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

                LocalTime localTimeStart = LocalTime.parse(Appointment_TimeStart_CB.getValue(), minHourFormat);
                LocalTime LocalTimeEnd = LocalTime.parse(Appointment_EndTime.getValue(), minHourFormat);

                LocalDateTime dateTimeStart = LocalDateTime.of(localDateStart, localTimeStart);
                LocalDateTime dateTimeEnd = LocalDateTime.of(localDateEnd, LocalTimeEnd);

                ZonedDateTime zoneDtStart = ZonedDateTime.of(dateTimeStart, ZoneId.systemDefault());
                ZonedDateTime zoneDtEnd = ZonedDateTime.of(dateTimeEnd, ZoneId.systemDefault());

                ZonedDateTime convertStartEST = zoneDtStart.withZoneSameInstant(ZoneId.of("America/New_York"));
                ZonedDateTime convertEndEST = zoneDtEnd.withZoneSameInstant(ZoneId.of("America/New_York"));

                if (convertStartEST.toLocalDate().getDayOfWeek().getValue() == (DayOfWeek.SATURDAY.getValue()) || convertStartEST.toLocalDate().getDayOfWeek().getValue() == (DayOfWeek.SUNDAY.getValue()) || convertEndEST.toLocalDate().getDayOfWeek().getValue() == (DayOfWeek.SATURDAY.getValue())  || convertEndEST.toLocalDate().getDayOfWeek().getValue() == (DayOfWeek.SUNDAY.getValue()) )
                {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Day is outside of business operations (Monday-Friday)");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("day is outside of business hours");
                    return;
                }

                if (convertStartEST.toLocalTime().isBefore(LocalTime.of(8, 0, 0)) || convertStartEST.toLocalTime().isAfter(LocalTime.of(22, 0, 0)) || convertEndEST.toLocalTime().isBefore(LocalTime.of(8, 0, 0)) || convertEndEST.toLocalTime().isAfter(LocalTime.of(22, 0, 0)))
                {
                    System.out.println("time is outside of business hours");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Time is outside of business hours (8am-10pm EST): " + convertStartEST.toLocalTime() + " - " + convertEndEST.toLocalTime() + " EST");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    return;
                }

                int newCustomerID = Integer.parseInt(Appt_Cust_ID_Input.getText());
                int appointmentID = Integer.parseInt(Appt_ID_Input.getText());


                if (dateTimeStart.isAfter(dateTimeEnd))
                {
                    System.out.println("Appointment has start time after end time");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment has start time after end time");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    return;
                }

                if (dateTimeStart.isEqual(dateTimeEnd))
                {
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
                    if ((newCustomerID == appointment.getCustomer_ID()) && (appointmentID != appointment.getAppointment_ID()) &&
                            (dateTimeStart.isBefore(checkStart)) && (dateTimeEnd.isAfter(checkEnd)))
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment overlaps with existing appointment.");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("Appointment overlaps with existing appointment.");
                        return;
                    }

                    if ((newCustomerID == appointment.getCustomer_ID()) && (appointmentID != appointment.getAppointment_ID()) &&
//                            Clarification on isEqual is that this does not count as an overlapping appointment
//                            (dateTimeStart.isEqual(checkStart) || dateTimeStart.isAfter(checkStart)) &&
//                            (dateTimeStart.isEqual(checkEnd) || dateTimeStart.isBefore(checkEnd))) {
                            (dateTimeStart.isAfter(checkStart)) && (dateTimeStart.isBefore(checkEnd)))
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Start time overlaps with existing appointment.");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("Start time overlaps with existing appointment.");
                        return;
                    }



                    if (newCustomerID == appointment.getCustomer_ID() && (appointmentID != appointment.getAppointment_ID()) &&
//                            Clarification on isEqual is that this does not count as an overlapping appointment
//                            (dateTimeEnd.isEqual(checkStart) || dateTimeEnd.isAfter(checkStart)) &&
//                            (dateTimeEnd.isEqual(checkEnd) || dateTimeEnd.isBefore(checkEnd)))
                            (dateTimeEnd.isAfter(checkStart)) && (dateTimeEnd.isBefore(checkEnd)))
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "End time overlaps with existing appointment.");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("End time overlaps with existing appointment.");
                        return;
                    }
                }

                String startDate = addAppointmentStartDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String startTime = addAppointmentStartTime.getValue();

                String endDate = addAppointmentEndDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String endTime = addAppointmentEndTime.getValue();

                String startUTC = convertTimeDateUTC(startDate + " " + startTime + ":00");
                String endUTC = convertTimeDateUTC(endDate + " " + endTime + ":00");

                String insertStatement = "UPDATE appointments SET Appointment_ID = ?, Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

                JDBC.setPreparedStatement(JDBC.getConnection(), insertStatement);
                PreparedStatement ps = JDBC.getPreparedStatement();
                ps.setInt(1, Integer.parseInt(updateAppointmentID.getText()));
                ps.setString(2, updateAppointmentTitle.getText());
                ps.setString(3, addAppointmentDescription.getText());
                ps.setString(4, addAppointmentLocation.getText());
                ps.setString(5, addAppointmentType.getText());
                ps.setString(6, startUTC);
                ps.setString(7, endUTC);
                ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(9, "admin");
                ps.setInt(10, Integer.parseInt(addAppointmentCustomerID.getText()));
                ps.setInt(11, Integer.parseInt(addAppointmentUserID.getText()));
                ps.setInt(12, Integer.parseInt(contactAccess.findContactID(addAppointmentContact.getValue())));
                ps.setInt(13, Integer.parseInt(updateAppointmentID.getText()));

                System.out.println("ps " + ps);
                ps.execute();
s
                ObservableList<Appointments> allAppointmentsList = Appointments_Access.getAllAppointments();
                Appointment_Table.setItems(allAppointmentsList);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

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
