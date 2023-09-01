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
import views.Appointments.fxml;

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

    }

    public void Add_Button(ActionEvent actionEvent)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/views/Add_Appointments.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            stage.setTitle("Add Appointments");
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

    public void Edit_Button(ActionEvent actionEvent)
    {

    }

    public void Delete_Button(ActionEvent actionEvent) throws Exception
    {
        //custom message displayed

        try
        {

            //Customers Selected_Customer = (Customers) Customer_Table.getSelectionModel().getSelectedItem();
            Customers Selected_Customer2 = (Customers) Customer_Table.getSelectionModel().getSelectedItem();

            Connection connect = JDBC.openConnection();
            int deleteAppointmentID = Appointment_Table.getSelectionModel().getSelectedItem().getAppointment_ID();
            Appointments tempAppointment_1 = (Appointments)  Appointment_Table.getSelectionModel().getSelectedItem();
            int Appt_to_del_1 = Selected_Customer.getCustomer_ID();



            String deleteAppointmentType = Appointment_Table.getSelectionModel().getSelectedItem().getType();








            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete the selected appointment with appointment id: " + deleteAppointmentID + " and appointment type " + deleteAppointmentType);

            Optional<ButtonType> confirmation = alert.showAndWait();

//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);                            // use this one
//            alert.setTitle("Delete Confirmation");
//            alert.setContentText("Are you sure you want to delete this item?");
//            alert.setHeaderText("Confirm Deletion");

            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK)
            {
                Appointments_Access.removeAppointment(deleteAppointmentID, connect);

                ObservableList<Appointments> allAppointmentsList = Appointments_Access.getAppointments();
                Appointment_Table.setItems(allAppointmentsList);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void Save_Button(ActionEvent actionEvent)
    {

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
