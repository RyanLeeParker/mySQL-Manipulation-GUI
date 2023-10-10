package controller;

import dao.Report_Access;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Contacts;
import model.Reports;
import java.io.IOException;
import java.sql.SQLException;
import dao.Appointments_Access;
import dao.Contacts_Access;
import model.First_Level_Division;
import dao.FirstLevelDivision_Access;
import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Month;
import java.util.Collections;


public class Reports_Controller
{
    public Tab SchedByContact_Tab;
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
    public ComboBox Contact_CB;
    public Tab AppointmentTotals_Tab;
    public TableColumn AppointmentType_Column;
    public TableColumn AppointmentTypeTotals_Column;
    public TableColumn AppointmentByMonth_Column;
    public TableColumn AppointmentByMonthTotals_Column;
    public Tab CustomersByState_Tab;
    public TableColumn<?, ?> CustomersByState_Column;
    public TableColumn<?, ?> CustomersByStateTotals_Column;
    public Button Cancel_Button;
    public TableView<Reports> customerByState;
    public TableView TotalApptsByMonth;
    public TableView TotalApptsByType;

    public void initialize() throws SQLException
    {
        CustomersByState_Column.setCellValueFactory(new PropertyValueFactory<>("division_name"));
        CustomersByStateTotals_Column.setCellValueFactory(new PropertyValueFactory<>("divisionCount"));
        Appointment_ID_Column.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        Appointment_Title_Column.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        Appointment_Desc_Column.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        Appt_Loc_Column.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        Appt_Type_Column.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        Appt_StartDateTime_Column.setCellValueFactory(new PropertyValueFactory<>("start"));
        Appt_EndDateTime_Coulmn.setCellValueFactory(new PropertyValueFactory<>("end"));
        Appt_CustID_Column.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        Appt_ContactID_Column.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        AppointmentType_Column.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        AppointmentTypeTotals_Column.setCellValueFactory(new PropertyValueFactory<>("appointmentTotal"));
        AppointmentByMonth_Column.setCellValueFactory(new PropertyValueFactory<>("appointmentMonth"));
        AppointmentByMonthTotals_Column.setCellValueFactory(new PropertyValueFactory<>("appointmentTotal"));

        ObservableList<Contacts> contactsObservableList = Contacts_Access.getContacts();
        ObservableList<String> allContactsNames = FXCollections.observableArrayList();
        contactsObservableList.forEach(contacts -> allContactsNames.add(contacts.getContact_Name()));
        Contact_CB.setItems(allContactsNames);
    }


    public void SchedByContact_Tab_Selected(Event event)
    {
    }

    public void Contact_CB_Selected(ActionEvent actionEvent) {
    }

    public void AppointmentTotals_Tab_Selected(Event event) throws SQLException
    {
        try {
            ObservableList<Appointments> getAllAppointments = Appointments_Access.getAppointments();
            ObservableList<Month> appointmentMonths = FXCollections.observableArrayList();
            ObservableList<Month> monthOfAppointments = FXCollections.observableArrayList();

            ObservableList<String> appointmentType = FXCollections.observableArrayList();
            ObservableList<String> uniqueAppointment = FXCollections.observableArrayList();

            ObservableList<ReportType> reportType = FXCollections.observableArrayList();
            ObservableList<ReportMonth> reportMonths = FXCollections.observableArrayList();


            //IDE converted to Lambda
            getAllAppointments.forEach(appointments -> {
                appointmentType.add(appointments.getType());
            });

            //IDE converted to Lambda
            getAllAppointments.stream().map(appointment -> {
                return appointment.getStart().getMonth();
            }).forEach(appointmentMonths::add);

            //IDE converted to Lambda
            appointmentMonths.stream().filter(month ->
            {
                return !monthOfAppointments.contains(month);
            }).forEach(monthOfAppointments::add);

            for (Appointments appointments: getAllAppointments)
            {
                String appointmentsAppointmentType = appointments.getType();
                if (!uniqueAppointment.contains(appointmentsAppointmentType)) {
                    uniqueAppointment.add(appointmentsAppointmentType);
                }
            }

            for (Month month: monthOfAppointments)
            {
                int totalMonth = Collections.frequency(appointmentMonths, month);
                String monthName = month.name();
                ReportMonth appointmentMonth = new ReportMonth(monthName, totalMonth);
                reportMonths.add(appointmentMonth);
            }
            TotalApptsByMonth.setItems(reportMonths);

            for (String type: uniqueAppointment)
            {
                String typeToSet = type;
                int typeTotal = Collections.frequency(appointmentType, type);
                ReportType appointmentTypes = new ReportType(typeToSet, typeTotal);
                reportType.add(appointmentTypes);
            }
            TotalApptsByType.setItems(reportType);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void CustomersByState_Tab_Selected(Event event) throws SQLException
    {
        try
        {
            ObservableList<Reports> aggregatedStates = Report_Access.getFirstLevelDivision();
            ObservableList<Reports> statesToAdd = FXCollections.observableArrayList();
            //IDE converted
            aggregatedStates.forEach(statesToAdd::add);

//            for (Reports report : aggregatedStates)
//            {
//                System.out.println(aggregatedStates);
//            }

            customerByState.setItems(statesToAdd);                   // exception in apply to table?
            System.out.println("I got here 2.3");

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void Cancel_Button(ActionEvent actionEvent)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/views/Main_Screen.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            stage.setTitle("Main Screen");
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
}
