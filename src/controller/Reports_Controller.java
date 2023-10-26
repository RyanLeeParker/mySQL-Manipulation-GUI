package controller;

import helper.Time;
import model.*;
import model.Reports;
import model.Contacts;
import java.time.Month;
import javafx.fxml.FXML;
import dao.Report_Access;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.event.Event;
import javafx.stage.Stage;
import java.io.IOException;
import dao.Contacts_Access;
import java.sql.SQLException;
import java.util.Collections;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import dao.Appointments_Access;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

/** */
public class Reports_Controller
{
    @FXML
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
    public TableColumn<Reports, String> CustomersByState_Column;
    public TableColumn<?, ?> CustomersByStateTotals_Column;
    public Button Cancel_Button;
    public TableView<Reports> customerByState;
    public TableView TotalApptsByMonth;
    public TableView TotalApptsByType;
    /** */
    public void initialize() throws SQLException
    {
        CustomersByState_Column.setCellValueFactory(new PropertyValueFactory<Reports, String>("division_name"));
        CustomersByStateTotals_Column.setCellValueFactory(new PropertyValueFactory<>("divisionCount"));
        Appointment_ID_Column.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        Appointment_Title_Column.setCellValueFactory(new PropertyValueFactory<>("Title"));
        Appointment_Desc_Column.setCellValueFactory(new PropertyValueFactory<>("Description"));
        Appt_Loc_Column.setCellValueFactory(new PropertyValueFactory<>("Location"));
        Appt_Type_Column.setCellValueFactory(new PropertyValueFactory<>("Type"));
        Appt_StartDateTime_Column.setCellValueFactory(new PropertyValueFactory<>("start"));
        Appt_EndDateTime_Coulmn.setCellValueFactory(new PropertyValueFactory<>("end"));
        Appt_CustID_Column.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        Appt_ContactID_Column.setCellValueFactory(new PropertyValueFactory<>("contact_ID"));
        Appt_UserID_Column.setCellValueFactory(new PropertyValueFactory<>("user_ID"));
        AppointmentType_Column.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        AppointmentTypeTotals_Column.setCellValueFactory(new PropertyValueFactory<>("appointmentTotal"));
        AppointmentByMonth_Column.setCellValueFactory(new PropertyValueFactory<>("appointmentMonth"));
        AppointmentByMonthTotals_Column.setCellValueFactory(new PropertyValueFactory<>("appointmentTotal"));

        ObservableList<Contacts> contactsList = Contacts_Access.getContacts();
        ObservableList<String> ContactsNames = FXCollections.observableArrayList();

        for (Contacts contacts : contactsList)
        {
            ContactsNames.add(contacts.getContact_Name());
        }

        Contact_CB.setItems(ContactsNames);
    }
    /** */
    public void Contact_CB_Selected(ActionEvent actionEvent) throws SQLException
    {
        try
        {
            ObservableList<Appointments> appointment = getAppointmentsForSelectedContact();
            Appointment_Table.setItems(appointment);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    /** */
    private ObservableList<Appointments> getAppointmentsForSelectedContact() throws SQLException
    {
        int contactID = getSelectedContactID();
        ObservableList<Appointments> getAppointments;
        getAppointments = Time.convertTimeDateLocal();
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();

        for (Appointments appointment : getAppointments)
        {
            if (appointment.getContact_ID() == contactID)
            {
                appointmentsList.add(appointment);
            }
        }
        return appointmentsList;
    }
    /** */
    private int getSelectedContactID() throws SQLException
    {
        String contactName = (String) Contact_CB.getSelectionModel().getSelectedItem();
        ObservableList<Contacts> getContacts = Contacts_Access.getContacts();

        for (Contacts contact : getContacts)
        {
            if (contactName.equals(contact.getContact_Name()))
            {
                return contact.getContact_ID();
            }
        }
        return 0;
    }
    /** */
    public void AppointmentTotals_Tab_Selected(Event event) throws SQLException
    {
        try
        {
            ObservableList<Appointments> getAppointments = Time.convertTimeDateLocal();

            ObservableList<Report_Type> reportType = generateAppointmentTypeReport(getAppointments);
            ObservableList<Report_Month> reportMonths = generateAppointmentMonthReport(getAppointments);

            TotalApptsByMonth.setItems(reportMonths);
            TotalApptsByType.setItems(reportType);
        } catch (Exception f)
        {
            f.printStackTrace();
        }
    }
    /** */
    private ObservableList<Report_Type> generateAppointmentTypeReport(ObservableList<Appointments> getAppointments)
    {
        ObservableList<Report_Type> reportType = FXCollections.observableArrayList();
        ObservableList<String> appointmentType = FXCollections.observableArrayList();
        ObservableList<String> distinctAppointment = FXCollections.observableArrayList();

        for (Appointments appointment : getAppointments)
        {
            appointmentType.add(appointment.getType());
        }

        for (Appointments appointments : getAppointments)
        {
            String AppointmentType2 = appointments.getType();
            if (!distinctAppointment.contains(AppointmentType2))
            {
                distinctAppointment.add(AppointmentType2);
            }
        }

        for (String type : distinctAppointment)
        {
            String ApptType = type;
            int typeTotal = Collections.frequency(appointmentType, type);
            Report_Type appointmentTypes = new Report_Type(ApptType, typeTotal);
            reportType.add(appointmentTypes);
        }
        return reportType;
    }
    /** */
    private ObservableList<Report_Month> generateAppointmentMonthReport(ObservableList<Appointments> getAppointments)
    {
        ObservableList<Report_Month> reportMonths = FXCollections.observableArrayList();
        ObservableList<Month> appointmentMonths = FXCollections.observableArrayList();
        ObservableList<Month> appointmentMonths2 = FXCollections.observableArrayList();

        for (Appointments appointment : getAppointments)
        {
            appointmentMonths.add(appointment.getStart().getMonth());
        }

        for (Month month : appointmentMonths)
        {
            if (!appointmentMonths2.contains(month))
            {
                appointmentMonths2.add(month);
            }
        }

        for (Month month : appointmentMonths2)
        {
            int MonthSum = Collections.frequency(appointmentMonths, month);
            String monthName = month.name();
            Report_Month appointmentMonth = new Report_Month(monthName, MonthSum);
            reportMonths.add(appointmentMonth);
        }
        return reportMonths;
    }
    /** */
    public void CustomersByState_Tab_Selected(Event event) throws SQLException
    {
        try
        {
            ObservableList<Reports> aggregatedStates = Report_Access.getFirst_Level_Division();
            customerByState.setItems(aggregatedStates);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
    /** */
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
    /** */
    public void SchedByContact_Tab_Selected(Event event) throws SQLException {}
}
