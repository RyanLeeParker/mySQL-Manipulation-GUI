package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import dao.Appointments_Access;
import dao.Contacts_Access;
import dao.Customer_Access;
import dao.Users_Access;
import helper.JDBC;
import helper.Time;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Scene;
import java.time.LocalDateTime;
import java.time.ZoneId;
import static helper.Time.convertTimeDateUTC;

/** */
public class Appointments_Controller
{
    public TextField Appt_ID_Input;
    public TextField Appt_Name_Input;
    public TextField Appt_Desc_Input;
    public TextField Appt_Loc_Input;
    public TextField Appt_Type_Input;
    public TextField Appt_Cust_ID_Input;
    public TextField Appt_UserID_Input;
    public RadioButton All_Radio;
    public RadioButton Week_Radio;
    public RadioButton Month_Radio;
    public ToggleGroup Appt_Toggle;
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
    public ComboBox Appointment_TimeStart_CB;
    public DatePicker Appt_StartDate_Picker;
    public DatePicker Appt_EndDate_Picker;
    public static int Appt_ID;

    /** */
    public void initialize() throws Exception
    {
        Connection connect = JDBC.openConnection();

        ObservableList<Contacts> contactsObservableList = Contacts_Access.getContacts();
        ObservableList<String> allContactsNames = FXCollections.observableArrayList();
        ObservableList<String> appointmentTimes = FXCollections.observableArrayList();
        ObservableList<Appointments> AppointmentsList = Appointments_Access.getAppointments();
        ObservableList<Appointments> LocalAppointmentsList;

        ZoneId systemZone = ZoneId.systemDefault();

        for (Contacts contact : contactsObservableList)
        {
            allContactsNames.add(contact.getContact_Name());
        }

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

        LocalAppointmentsList = Time.convertTimeDateLocal();

        LocalTime firstAppointmentofDay = LocalTime.MIN.plusHours(0);
        LocalTime lastAppointmentofDay = LocalTime.MAX.minusHours(0).minusMinutes(45);

        if (!firstAppointmentofDay.equals(0) || !lastAppointmentofDay.equals(0))
        {
            while (firstAppointmentofDay.isBefore(lastAppointmentofDay))
            {
                appointmentTimes.add(String.valueOf(firstAppointmentofDay));
                firstAppointmentofDay = firstAppointmentofDay.plusMinutes(15);
            }
        }

        Appointment_TimeStart_CB.setItems(appointmentTimes);
        Appointment_EndTime.setItems(appointmentTimes);
        Appointment_Contact_CB.setItems(allContactsNames);
        Appointment_Table.setItems(LocalAppointmentsList);

        Appt_ID_Input.setDisable(true);
        Appt_ID = AppointmentsList.size();

        for (Appointments appointment: AppointmentsList)                                                                // fixes corner case of deletion OOO
        {
            while ((Appt_ID + 1) == appointment.getAppointment_ID())
            {
                Appt_ID++;
            }
        }
    }

    /** */
    public void Add_Button(ActionEvent actionEvent) throws Exception
    {
        try
        {
            Connection connection = JDBC.openConnection();
            ObservableList<Appointments> LocalAppointmentsList;
            ObservableList<Customers> CustomersList = Customer_Access.getCustomers(connection);
            ObservableList<Customers> getCustomers = Customer_Access.getCustomers(connection);
            ObservableList<Integer> UserIDs = FXCollections.observableArrayList();
            ObservableList<Integer> CustomerIDs = FXCollections.observableArrayList();
            ObservableList<Users_Access> getUsers = Users_Access.getUsersList();
            ObservableList<Users_Access> UsersList = Users_Access.getUsersList();

            int customerID = Integer.parseInt(Appt_Cust_ID_Input.getText());

            for (Customers customer : getCustomers)
            {
                CustomerIDs.add(customer.getCustomer_ID());
            }
            for (Users user : getUsers)
            {
                UserIDs.add(user.getUserId());
            }

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

            /** */
            boolean Cust_Valid = CustomersList.stream()                                                       // lambda # 1
                    .map(customer -> String.valueOf(customer.getCustomer_ID()))
                    .anyMatch(Cust_tempID -> Appt_Cust_ID_Input.getText().equals(Cust_tempID));

            if((Appt_Cust_ID_Input.getText().isEmpty()) || (!Cust_Valid))
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Appointment.");
                alert_err.setContentText("Please enter a valid Customer ID to add Appointment.");
                alert_err.showAndWait();
                return;
            }

            /** */
            boolean User_Valid = UsersList.stream()                                                           // lambda # 2
                    .anyMatch(user -> Appt_UserID_Input.getText().equals(String.valueOf(user.getUserId())));


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

            Appt_ID++;

            LocalDate localEndDate = Appt_EndDate_Picker.getValue();                                                    // change time names
            LocalDate localStartDate = Appt_StartDate_Picker.getValue();

            DateTimeFormatter minHourFormat = DateTimeFormatter.ofPattern("HH:mm");
            String appointmentStartDate = Appt_StartDate_Picker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String appointmentStartTime = (String) Appointment_TimeStart_CB.getValue();

            String endDate = Appt_EndDate_Picker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String endTime = (String) Appointment_EndTime.getValue();

            String UTC_Start = convertTimeDateUTC(appointmentStartDate + " " + appointmentStartTime + ":00");
            String UTC_End = convertTimeDateUTC(endDate + " " + endTime + ":00");

            LocalTime localTimeStart = LocalTime.parse((CharSequence) Appointment_TimeStart_CB.getValue(), minHourFormat);
            LocalTime LocalTimeEnd = LocalTime.parse((CharSequence) Appointment_EndTime.getValue(), minHourFormat);

            LocalDateTime dateTimeStart = LocalDateTime.of(localStartDate, localTimeStart);
            LocalDateTime dateTimeEnd = LocalDateTime.of(localEndDate, LocalTimeEnd);

            ZonedDateTime zoneDateTimeStart = ZonedDateTime.of(dateTimeStart, ZoneId.systemDefault());
            ZonedDateTime zoneDateTimeEnd = ZonedDateTime.of(dateTimeEnd, ZoneId.systemDefault());

            ZonedDateTime EST_Start = zoneDateTimeStart.withZoneSameInstant(ZoneId.of("America/New_York"));
            ZonedDateTime EST_End = zoneDateTimeEnd.withZoneSameInstant(ZoneId.of("America/New_York"));

            LocalTime EST_StartTimeToCheck = EST_Start.toLocalTime();
            LocalTime EST_EndTimeToCheck = EST_End.toLocalTime();

            DayOfWeek EST_StartDayToCheck = EST_Start.toLocalDate().getDayOfWeek();
            DayOfWeek EST_EndDayToCheck = EST_End.toLocalDate().getDayOfWeek();

            int startDayToCheck_Integer = EST_StartDayToCheck.getValue();
            int endDayToCheck_Integer = EST_EndDayToCheck.getValue();

            int WeekStart = DayOfWeek.MONDAY.getValue();
            int WeekEnd = DayOfWeek.FRIDAY.getValue();

            LocalTime EST_BusinessHoursStart = LocalTime.of(8, 0, 0);
            LocalTime EST_BusinessHoursEnd = LocalTime.of(22, 0, 0);

            boolean condition1 = startDayToCheck_Integer < WeekStart;
            boolean condition2 = startDayToCheck_Integer > WeekEnd;
            boolean condition3 = endDayToCheck_Integer < WeekStart;
            boolean condition4 = endDayToCheck_Integer > WeekEnd;

            boolean combinedCondition = condition1 || condition2 || condition3 || condition4;

            if (combinedCondition)
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Day selected is outside of business operations (Monday-Friday)");
                Optional<ButtonType> confirmation = alert.showAndWait();
                System.out.println("Day selected is outside of business hours");

                return;
            }

            boolean isStartTimeBeforeBusinessHours = EST_StartTimeToCheck.isBefore(EST_BusinessHoursStart);
            boolean isStartTimeAfterBusinessHours = EST_StartTimeToCheck.isAfter(EST_BusinessHoursEnd);
            boolean isEndTimeBeforeBusinessHours = EST_EndTimeToCheck.isBefore(EST_BusinessHoursStart);
            boolean isEndTimeAfterBusinessHours = EST_EndTimeToCheck.isAfter(EST_BusinessHoursEnd);

            boolean isTimeOutsideBusinessHours = isStartTimeBeforeBusinessHours || isStartTimeAfterBusinessHours || isEndTimeBeforeBusinessHours || isEndTimeAfterBusinessHours;

            if (isTimeOutsideBusinessHours)
            {
                System.out.println("Time selected is outside of business hours");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Time selected is outside of business hours (8am-10pm EST): " + EST_StartTimeToCheck + " - " + EST_EndTimeToCheck + " EST");
                Optional<ButtonType> confirmation = alert.showAndWait();
                return;
            }

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

            LocalAppointmentsList = Time.convertTimeDateLocal();

            for (Appointments appointment: LocalAppointmentsList)
            {
                LocalDateTime checkApptStart = appointment.getStart();
                LocalDateTime checkApptEnd = appointment.getEnd();

                if ((customerID == appointment.getCustomer_ID()) && (Appt_ID != appointment.getAppointment_ID()))
                {
                    if ((dateTimeStart.isBefore(checkApptStart)) && (dateTimeEnd.isAfter(checkApptEnd)))
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment coincides with an existing appointment.");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("Appointment coincides with an existing appointment.");
                        return;
                    }
                }
                if ((customerID == appointment.getCustomer_ID()) && (Appt_ID != appointment.getAppointment_ID()))
                {
                    if ((dateTimeStart.isAfter(checkApptStart)) && (dateTimeStart.isBefore(checkApptEnd)))                  // this line causing corner case bug, maybe no corner case after all
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The start time coincides with an existing appointment");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("The start time coincides with an existing appointment");
                        return;
                    }
                }
                if ((customerID == appointment.getCustomer_ID()) && (Appt_ID != appointment.getAppointment_ID()))
                {
                     if   ((dateTimeEnd.isAfter(checkApptStart)) && (dateTimeEnd.isBefore(checkApptEnd)))
                     {
                         Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The end time coincides with an existing appointment");
                         Optional<ButtonType> confirmation = alert.showAndWait();
                         System.out.println("The end time coincides with an existing appointment.");
                         return;
                     }
                }
                if ((customerID == appointment.getCustomer_ID()) && (Appt_ID != appointment.getAppointment_ID()))
                {
                    if   ((dateTimeStart.isEqual(checkApptStart)) && (dateTimeEnd.isEqual(checkApptEnd)))
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment coincides with an existing appointment.");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("Appointment coincides with an existing appointment.");
                        return;
                    }
                }
            }

            String insertStatement = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            JDBC.setPreparedStatement(JDBC.getConnection(), insertStatement);
            PreparedStatement ps = JDBC.getPreparedStatement();
            ps.setInt(1, Appt_ID);
            ps.setString(2, Appt_Name_Input.getText());
            ps.setString(3, Appt_Desc_Input.getText());
            ps.setString(4, Appt_Loc_Input.getText());
            ps.setString(5, Appt_Type_Input.getText());
            ps.setString(6, UTC_Start);
            ps.setString(7, UTC_End);
            ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(9, Users_Access.getCurrentUser());
            ps.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(11, Users_Access.getCurrentUser());
            ps.setInt(12, Integer.parseInt(Appt_Cust_ID_Input.getText()));
            ps.setInt(13, Integer.parseInt(Appt_UserID_Input.getText()));
            ps.setInt(14, Integer.parseInt(Contacts_Access.getContactID((String) Appointment_Contact_CB.getValue())));
            ps.execute();
        }
        catch (Exception e)
        {
            System.out.println("Please ensure all fields are appropriately filled.");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please ensure all fields are appropriately filled.");
            Optional<ButtonType> confirmation = alert.showAndWait();
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/views/Appointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1100, 600);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    /** */
    public void Edit_Button(ActionEvent actionEvent)
    {
        try
        {
            JDBC.openConnection();
            Appointments selectedAppointment = (Appointments) Appointment_Table.getSelectionModel().getSelectedItem();

            if (selectedAppointment != null)
            {
                ObservableList<Contacts> contactsObservableList = Contacts_Access.getContacts();
                ObservableList<String> allContactsNames = FXCollections.observableArrayList();
                String displayContactName = "";

                for (Contacts contacts : contactsObservableList)
                {
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
                Appt_UserID_Input.setText(String.valueOf(selectedAppointment.getUser_ID()));
                Appointment_Contact_CB.setValue(displayContactName);

                ObservableList<String> appointmentTimes = FXCollections.observableArrayList();

                LocalTime firstAppointment = LocalTime.MIN.plusHours(0);
                LocalTime lastAppointment = LocalTime.MAX.minusHours(0).minusMinutes(45);


                if (!firstAppointment.equals(0) || !lastAppointment.equals(0))                                                          //if statement fixed issue with infinite loop
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
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /** */
    public void Delete_Button(ActionEvent actionEvent) throws Exception
    {
        try
        {
            Connection connect = JDBC.openConnection();
            Appointments tempAppointment_1 = (Appointments)  Appointment_Table.getSelectionModel().getSelectedItem();
            String App_to_del_type_1 = tempAppointment_1.getType();
            int Appt_to_del_ApptID = tempAppointment_1.getAppointment_ID();

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

                FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/views/Appointments.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
                stage.setTitle("Add Appointments");
                stage.setScene(scene);
                stage.show();
            }
            else if (result.get() == ButtonType.CANCEL) {return;}
        }
        catch (Exception e)
        {
            System.out.println("Please select a valid appointment to delete.");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please select a valid appointment to delete.");
            Optional<ButtonType> confirmation = alert.showAndWait();
            return;
        }
    }

    /** */
    public void Save_Button(ActionEvent actionEvent) throws Exception
    {
        try
        {
            Connection connection = JDBC.openConnection();
            ObservableList<Users_Access> UsersObservableList = Users_Access.getUsersList();
            ObservableList<Customers> CustomersObservableList = Customer_Access.getCustomers(connection);
            ObservableList<Appointments> LocalAppointmentsList;
            int customerID = Integer.parseInt(Appt_Cust_ID_Input.getText());


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

            boolean Cust_Valid = CustomersObservableList.stream()                                                       // lambda # 1
                    .map(customer -> String.valueOf(customer.getCustomer_ID()))
                    .anyMatch(Cust_tempID -> Appt_Cust_ID_Input.getText().equals(Cust_tempID));

            if((Appt_Cust_ID_Input.getText().isEmpty()) || (!Cust_Valid))
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Appointment.");
                alert_err.setContentText("Please enter a valid Customer ID to add Appointment.");
                alert_err.showAndWait();
                return;
            }

            boolean User_Valid = UsersObservableList.stream()                                                           // lambda # 2
                    .anyMatch(user -> Appt_UserID_Input.getText().equals(String.valueOf(user.getUserId())));

            if((Appt_UserID_Input.getText().isEmpty()) || (!User_Valid))
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

                for (Customers customer : getAllCustomers)
                {
                    storeCustomerIDs.add(customer.getCustomer_ID());
                }

                for (Users user : getAllUsers)
                {
                    storeUserIDs.add(user.getUserId());
                }

            LocalDate localEndDate = Appt_EndDate_Picker.getValue();
            LocalDate localStartDate = Appt_StartDate_Picker.getValue();

            DateTimeFormatter minHourFormat = DateTimeFormatter.ofPattern("HH:mm");

            LocalTime localStartTime = LocalTime.parse((CharSequence) Appointment_TimeStart_CB.getValue(), minHourFormat);
            LocalTime LocalEndTime = LocalTime.parse((CharSequence) Appointment_EndTime.getValue(), minHourFormat);

            LocalDateTime dateStartTime = LocalDateTime.of(localStartDate, localStartTime);
            LocalDateTime dateEndTime = LocalDateTime.of(localEndDate, LocalEndTime);

            LocalAppointmentsList = Time.convertTimeDateLocal();

            for (Appointments appointment: LocalAppointmentsList)
            {
                LocalDateTime checkStart = appointment.getStart();
                LocalDateTime checkEnd = appointment.getEnd();

                if ((customerID == appointment.getCustomer_ID()) && (Appt_ID != appointment.getAppointment_ID()))
                {
                    if ((dateStartTime.isBefore(checkStart)) && (dateEndTime.isAfter(checkEnd)))
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment overlaps with existing appointment.");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("Appointment overlaps with existing appointment.");
                        return;
                    }
                }
                if ((customerID == appointment.getCustomer_ID()) && (Appt_ID != appointment.getAppointment_ID()))
                {
                    if ((dateStartTime.isAfter(checkStart)) && (dateStartTime.isBefore(checkEnd)))
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Start time overlaps with existing appointment.");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("Start time overlaps with existing appointment.");
                        return;
                    }
                }
                if ((customerID == appointment.getCustomer_ID()) && (Appt_ID != appointment.getAppointment_ID()))
                {
                    if   ((dateEndTime.isAfter(checkStart)) && (dateEndTime.isBefore(checkEnd)))
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "End time overlaps with existing appointment.");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("End time overlaps with existing appointment.");
                        return;
                    }
                }
                if ((customerID == appointment.getCustomer_ID()) && (Appt_ID != appointment.getAppointment_ID()))
                {
                    if   ((dateStartTime.isEqual(checkStart)) && (dateEndTime.isEqual(checkEnd)))
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment overlaps with existing appointment.");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("Appointment overlaps with existing appointment.");
                        return;
                    }
                }
            }

            ZonedDateTime zoneDateStart = ZonedDateTime.of(dateStartTime, ZoneId.systemDefault());
            ZonedDateTime zoneDateEnd = ZonedDateTime.of(dateEndTime, ZoneId.systemDefault());
            ZonedDateTime EST_convertStart = zoneDateStart.withZoneSameInstant(ZoneId.of("America/New_York"));
            ZonedDateTime EST_convertEnd = zoneDateEnd.withZoneSameInstant(ZoneId.of("America/New_York"));

            boolean isStartDayWeekend = EST_convertStart.toLocalDate().getDayOfWeek().getValue() == DayOfWeek.SATURDAY.getValue() || EST_convertStart.toLocalDate().getDayOfWeek().getValue() == DayOfWeek.SUNDAY.getValue();
            boolean isEndDayWeekend = EST_convertEnd.toLocalDate().getDayOfWeek().getValue() == DayOfWeek.SATURDAY.getValue() || EST_convertEnd.toLocalDate().getDayOfWeek().getValue() == DayOfWeek.SUNDAY.getValue();

            boolean isOutsideBusinessHours = isStartDayWeekend || isEndDayWeekend;

            if (isOutsideBusinessHours)
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please schedule within business hours. (Mon-Fri)");
                Optional<ButtonType> confirmation = alert.showAndWait();
                System.out.println("Please schedule within business hours.");
                return;
            }

            LocalTime businessHoursStart = LocalTime.of(8, 0, 0);
            LocalTime businessHoursEnd = LocalTime.of(22, 0, 0);

            boolean isStartTimeOutsideBusinessHours = EST_convertStart.toLocalTime().isBefore(businessHoursStart) || EST_convertStart.toLocalTime().isAfter(businessHoursEnd);
            boolean isEndTimeOutsideBusinessHours = EST_convertEnd.toLocalTime().isBefore(businessHoursStart) || EST_convertEnd.toLocalTime().isAfter(businessHoursEnd);

            boolean isOutsideBusinessHours1 = isStartTimeOutsideBusinessHours || isEndTimeOutsideBusinessHours;

            if (isOutsideBusinessHours1)
            {
                System.out.println("Please schedule within business hours.");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please schedule within business hours. (8am-10pm EST): " + EST_convertStart.toLocalTime() + " - " + EST_convertEnd.toLocalTime() + " EST");
                Optional<ButtonType> confirmation = alert.showAndWait();
                return;
            }


            String startDate = Appt_StartDate_Picker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String startTime = (String) Appointment_TimeStart_CB.getValue();

            String endDate = Appt_EndDate_Picker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String endTime = (String) Appointment_EndTime.getValue();

            String startUTC = convertTimeDateUTC(startDate + " " + startTime + ":00");
            String endUTC = convertTimeDateUTC(endDate + " " + endTime + ":00");

            String insertStatement = "UPDATE appointments SET Appointment_ID = ?, Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

            JDBC.setPreparedStatement(JDBC.getConnection(), insertStatement);
            PreparedStatement ps = JDBC.getPreparedStatement();
            ps.setInt(1, Integer.parseInt(Appt_ID_Input.getText()));
            ps.setString(2, Appt_Name_Input.getText());
            ps.setString(3, Appt_Desc_Input.getText());
            ps.setString(4, Appt_Loc_Input.getText());
            ps.setString(5, Appt_Type_Input.getText());
            ps.setString(6, startUTC);
            ps.setString(7, endUTC);
            ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(9, Users_Access.getCurrentUser());
            ps.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(11, Users_Access.getCurrentUser());
            ps.setInt(12, Integer.parseInt(Appt_Cust_ID_Input.getText()));
            ps.setInt(13, Integer.parseInt(Appt_UserID_Input.getText()));
            ps.setInt(14, Integer.parseInt(Contacts_Access.getContactID((String) Appointment_Contact_CB.getValue())));
            ps.setInt(15, Integer.parseInt(Appt_ID_Input.getText()));
            ps.execute();
        }
        catch (Exception f)
        {
            f.printStackTrace();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/views/Appointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Add Appointments");
        stage.setScene(scene);
        stage.show();
    }

    /** */
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

    /** */
    public void All_Radio_Selected(ActionEvent actionEvent)
    {
        try
        {
            ObservableList<Appointments> LocalAppointmentsList;
            LocalAppointmentsList = Time.convertTimeDateLocal();

            if (LocalAppointmentsList != null)
                for (Appointments appointment : LocalAppointmentsList)
                {
                    Appointment_Table.setItems(LocalAppointmentsList);
                }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /** */
    public void Week_Radio_Selected(ActionEvent actionEvent)
    {
        try
        {
            ObservableList<Appointments> allAppointmentsList = Appointments_Access.getAppointments();
            ObservableList<Appointments> appointmentsWeek = FXCollections.observableArrayList();
            ObservableList<Appointments> LocalAppointmentsList;
            LocalAppointmentsList = Time.convertTimeDateLocal();

            LocalDateTime weekStart = LocalDateTime.now().minusWeeks(1);
            LocalDateTime weekEnd = LocalDateTime.now().plusWeeks(1);

            if (allAppointmentsList != null)
                for (Appointments appointment : LocalAppointmentsList)
                {
                    if (appointment.getEnd().isAfter(weekStart) && appointment.getEnd().isBefore(weekEnd))
                    {
                        appointmentsWeek.add(appointment);
                    }
                }
            Appointment_Table.setItems(appointmentsWeek);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /** */
    public void Month_Radio_Selected(ActionEvent actionEvent)
    {
        try
        {
            ObservableList<Appointments> allAppointmentsList = Appointments_Access.getAppointments();
            ObservableList<Appointments> appointmentsMonth = FXCollections.observableArrayList();
            ObservableList<Appointments> LocalAppointmentsList;
            LocalAppointmentsList = Time.convertTimeDateLocal();

            LocalDateTime currentMonthStart = LocalDateTime.now().minusMonths(1);
            LocalDateTime currentMonthEnd = LocalDateTime.now().plusMonths(1);

            if (allAppointmentsList != null)
            {
                for (Appointments appointment : LocalAppointmentsList)
                {
                    if (appointment.getEnd().isAfter(currentMonthStart) && appointment.getEnd().isBefore(currentMonthEnd))
                    {
                        appointmentsMonth.add(appointment);
                    }
                }
            }
                Appointment_Table.setItems(appointmentsMonth);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void EndTime_CB_Select(ActionEvent actionEvent) {}

    public void Appt_Cont_CB_Select(ActionEvent actionEvent) {}

    public void Appt_StartDate_Picked(ActionEvent actionEvent) {}

    public void Appt_EndDate_Picked(ActionEvent actionEvent) {}

    public void TimeStart_CB_Select(ActionEvent actionEvent) {}
}