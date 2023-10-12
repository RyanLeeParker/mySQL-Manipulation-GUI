package controller;


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

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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

public class Customer_Controller implements Initializable
{
    public TextField Customer_ID_Input;
    public TextField Customer_Name_Input;
    public TextField Customer_Address_Input;
    public TextField Customer_PostalCode_Input;
    public TextField Customer_PhoneNumber_Input;
    public TableView Customer_Table;
    public TableColumn Customer_ID_Column;
    public TableColumn Customer_Name_Column;
    public TableColumn Customer_Address_Column;
    public TableColumn Customer_PostalCode_Column;
    public Button AddButton;
    public Button Edit_Button;
    public Button DeleteButton;
    public Button SaveButton;
    public Button Cancel_Button;
    public ComboBox CustomerUpdate_Country_CB;                      // weird CB things that don't exist?
    public ComboBox CustomerUpdate_State;
    public TableColumn Customer_Phone_Column;
    public TableColumn Customer_FLD_Column;
    public ComboBox Customer_State;                                 // actual state CB
    public ComboBox Customer_Country_CB;                            // actual country CB

    public static int Cust_ID;

    class ItemChangeListener implements ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent event)
        {
            if (event.getStateChange() == ItemEvent.SELECTED)
            {
                Object item = event.getItem();
                // do something with object
            }
        }
    }


    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            Connection connect = JDBC.openConnection();
            ObservableList<Country_Access> Countries_All = Country_Access.getCountries();
            ObservableList<FirstLevelDivision_Access> First_Level_Divisions_All = FirstLevelDivision_Access.getFirst_Level_Division();
            ObservableList<Customers> Customers_All = Customer_Access.getCustomers((java.sql.Connection) connect);
            ObservableList<String> Countries = FXCollections.observableArrayList();
            ObservableList<String> First_Level_Divisions_Names = FXCollections.observableArrayList();
            ObservableList<String> US_FSD = FXCollections.observableArrayList();
            ObservableList<String> UK_FSD = FXCollections.observableArrayList();
            ObservableList<String> CAN_FSD = FXCollections.observableArrayList();


            Customer_ID_Input.setText("Auto Gen - Disabled");
            Customer_ID_Input.setDisable(true);
            Cust_ID = Customers_All.size();

            for (Customers customer: Customers_All)                                         // fixes corner case of deletion OOO
            {
                while ((Cust_ID + 1) == customer.getCustomer_ID())
                {
                    Cust_ID++;
                }
            }

            Customer_Table.setItems(Customers_All);


            Customer_ID_Column.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
            Customer_Name_Column.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
            Customer_Address_Column.setCellValueFactory(new PropertyValueFactory<>("Address"));
            Customer_PostalCode_Column.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
            Customer_Phone_Column.setCellValueFactory(new PropertyValueFactory<>("Phone"));
            Customer_FLD_Column.setCellValueFactory(new PropertyValueFactory<>("Division_ID"));


            for (Country country : Countries_All)                                           // have three lists, per country
            {
                Countries.add(country.getCountry_Name());
            }

            Customer_Country_CB.setItems(Countries);

            for (First_Level_Division firstLevelDivision : First_Level_Divisions_All)
            {

                if (firstLevelDivision.getCountry_ID() == 1)
                {
                    US_FSD.add(firstLevelDivision.getDivision_name());
                }
                else if (firstLevelDivision.getCountry_ID() == 2)
                {
                    UK_FSD.add(firstLevelDivision.getDivision_name());
                }
                else if (firstLevelDivision.getCountry_ID() == 3)
                {
                    CAN_FSD.add(firstLevelDivision.getDivision_name());
                }

                First_Level_Divisions_Names.add(firstLevelDivision.getDivision_name());
            }

            Customer_Country_CB.valueProperty().addListener((observable, oldValue, newValue) ->
            {
                if ("U.S".equals(newValue))
                {
                    Customer_State.setItems(US_FSD);
                }
                if ("UK".equals(newValue))
                {
                    Customer_State.setItems(UK_FSD);
                }
                if ("Canada".equals(newValue))
                {
                    Customer_State.setItems(CAN_FSD);
                }
            });

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void Cancel_Button(ActionEvent actionEvent) throws IOException
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
        catch (IllegalStateException e)
        {
            Alert alert_err = new Alert(Alert.AlertType.WARNING);
            alert_err.setTitle("Something went wrong.");
            alert_err.setContentText("Please restart the program and try again.");
            alert_err.showAndWait();
        }
    }

    public void Save_Button(ActionEvent actionEvent)                            // push everything to actual mySQL DB from updated text fields
    {
        try {
            Connection connect = JDBC.openConnection();

            if(Customer_Name_Input.getText().isEmpty())
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Customer.");
                alert_err.setContentText("Please enter a valid Name to add Customer.");
                alert_err.showAndWait();
                return;
            }
            if(Customer_Address_Input.getText().isEmpty())
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Customer.");
                alert_err.setContentText("Please enter a valid Address to add Customer.");
                alert_err.showAndWait();
                return;
            }
            if(Customer_PostalCode_Input.getText().isEmpty())
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Customer.");
                alert_err.setContentText("Please enter a valid Postal Code to add Customer.");
                alert_err.showAndWait();
                return;
            }
            if(Customer_PhoneNumber_Input.getText().isEmpty())
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Customer.");
                alert_err.setContentText("Please enter a valid Phone Number to add Customer.");
                alert_err.showAndWait();
                return;
            }
            if(Customer_State.getValue() == null)
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Customer.");
                alert_err.setContentText("Please select a valid State to add Customer.");
                alert_err.showAndWait();
                return;
            }
            if(Customer_Country_CB.getValue() == null)
            {
                Alert alert_err = new Alert(Alert.AlertType.WARNING);
                alert_err.setTitle("Unable to add Customer.");
                alert_err.setContentText("Please Select a valid Country to add Customer.");
                alert_err.showAndWait();
                return;
            }

            int temp_FLD_ID = 0;

            for (FirstLevelDivision_Access firstLevelDivision : FirstLevelDivision_Access.getFirst_Level_Division()) {
                if (Customer_State.getSelectionModel().getSelectedItem().equals(firstLevelDivision.getDivision_name())) {
                    temp_FLD_ID = firstLevelDivision.getDivision_ID();
                }
            }

            String insertStatement = "UPDATE customers SET Customer_ID = ?, Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
            JDBC.setPreparedStatement(JDBC.getConnection(), insertStatement);
            PreparedStatement ps = JDBC.getPreparedStatement();
            ps.setInt(1, Integer.parseInt(Customer_ID_Input.getText()));
            ps.setString(2, Customer_Name_Input.getText());
            ps.setString(3, Customer_Address_Input.getText());
            ps.setString(4, Customer_PostalCode_Input.getText());
            ps.setString(5, Customer_PhoneNumber_Input.getText());
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(7, "test");
            ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(9, "test");
            ps.setInt(10, temp_FLD_ID);
            ps.setInt(11, Integer.parseInt(Customer_ID_Input.getText()));
            ps.execute();

            Customer_ID_Input.clear();
            Customer_Name_Input.clear();
            Customer_Address_Input.clear();
            Customer_PostalCode_Input.clear();
            Customer_PhoneNumber_Input.clear();

            ObservableList<Customers> refreshCustomersList = Customer_Access.getCustomers(connect);
            Customer_Table.setItems(refreshCustomersList);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void Delete_Button(ActionEvent actionEvent) throws Exception          //When deleting customers, delete their appointments first
    {
        Connection connect = JDBC.openConnection();
        ObservableList<Appointments> All_Appointments = Appointments_Access.getAppointments();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setContentText("Are you sure you want to delete this item?");
        alert.setHeaderText("Confirm Deletion");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK)
        {

            Customers Selected_Customer = (Customers) Customer_Table.getSelectionModel().getSelectedItem();
            int Cust_to_del = Selected_Customer.getCustomer_ID();

            if (Selected_Customer == null) {return;}

            Appointments_Access.removeAppointment(Cust_to_del, connect);                        //dbl chk appts shld be del after cust
            String sqlDelete = "DELETE FROM customers WHERE Customer_ID = ?";
            JDBC.setPreparedStatement(JDBC.getConnection(), sqlDelete);
            PreparedStatement psDelete = JDBC.getPreparedStatement();


            //int customerFromTable = Customer_Table.getSelectionModel().getSelectedItem().getCustomer_ID();
            Customers Selected_Customer2 = (Customers) Customer_Table.getSelectionModel().getSelectedItem();
            int Cust_to_del2 = Selected_Customer.getCustomer_ID();

            //Delete all customer appointments from database.
            for (Appointments appointment : All_Appointments)
            {
                int customer_Appointments = appointment.getCustomer_ID();

                if (Cust_to_del2 == customer_Appointments)
                {
                    String remove_Appointments = "DELETE FROM appointments WHERE Appointment_ID = ?";
                    JDBC.setPreparedStatement(JDBC.getConnection(), remove_Appointments);
                }
            }

            psDelete.setInt(1, Cust_to_del2);
            psDelete.execute();
            ObservableList<Customers> refreshCustomersList = Customer_Access.getCustomers(connect);
            Customer_Table.setItems(refreshCustomersList);

            //Cust_ID--;
        }
        else if (result.get() == ButtonType.CANCEL)
        {
            return;
        }
    }

    public void Add_Button(ActionEvent actionEvent) throws Exception            // adds new entry from text fields
    {
        Connection connect = JDBC.openConnection();

        // check all data is entered correctly
        if(Customer_Name_Input.getText().isEmpty())
        {
            Alert alert_err = new Alert(Alert.AlertType.WARNING);
            alert_err.setTitle("Unable to add Customer.");
            alert_err.setContentText("Please enter a valid Name to add Customer.");
            alert_err.showAndWait();
            return;
        }
        if(Customer_Address_Input.getText().isEmpty())
        {
            Alert alert_err = new Alert(Alert.AlertType.WARNING);
            alert_err.setTitle("Unable to add Customer.");
            alert_err.setContentText("Please enter a valid Address to add Customer.");
            alert_err.showAndWait();
            return;
        }
        if(Customer_PostalCode_Input.getText().isEmpty())
        {
            Alert alert_err = new Alert(Alert.AlertType.WARNING);
            alert_err.setTitle("Unable to add Customer.");
            alert_err.setContentText("Please enter a valid Postal Code to add Customer.");
            alert_err.showAndWait();
            return;
        }
        if(Customer_PhoneNumber_Input.getText().isEmpty())
        {
            Alert alert_err = new Alert(Alert.AlertType.WARNING);
            alert_err.setTitle("Unable to add Customer.");
            alert_err.setContentText("Please enter a valid Phone Number to add Customer.");
            alert_err.showAndWait();
            return;
        }
        if(Customer_State.getValue() == null)
        {
            Alert alert_err = new Alert(Alert.AlertType.WARNING);
            alert_err.setTitle("Unable to add Customer.");
            alert_err.setContentText("Please select a valid State to add Customer.");
            alert_err.showAndWait();
            return;
        }
        if(Customer_Country_CB.getValue() == null)
        {
            Alert alert_err = new Alert(Alert.AlertType.WARNING);
            alert_err.setTitle("Unable to add Customer.");
            alert_err.setContentText("Please Select a valid Country to add Customer.");
            alert_err.showAndWait();
            return;
        }

        int firstLevelDivisionName = 0;
        for (FirstLevelDivision_Access firstLevelDivision : FirstLevelDivision_Access.getFirst_Level_Division())
        {
            if (Customer_State.getSelectionModel().getSelectedItem().equals(firstLevelDivision.getDivision_name()))
            {
                firstLevelDivisionName = firstLevelDivision.getDivision_ID();
            }
        }

        Cust_ID++;      // leads to corner case of using program multiple times, and making deletions that mess up the ID

        String insertStatement = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";
        JDBC.setPreparedStatement(JDBC.getConnection(), insertStatement);
        PreparedStatement ps = JDBC.getPreparedStatement();
        ps.setInt(1, Cust_ID);
        ps.setString(2, Customer_Name_Input.getText());
        ps.setString(3, Customer_Address_Input.getText());
        ps.setString(4, Customer_PostalCode_Input.getText());
        ps.setString(5, Customer_PhoneNumber_Input.getText());
        ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(7, "test");
        ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(9, "test");
        ps.setInt(10, firstLevelDivisionName);
        ps.execute();

        Customer_ID_Input.clear();                                                                                  // can probably drop this chunk if refreshing
        Customer_Name_Input.clear();
        Customer_Address_Input.clear();
        Customer_PostalCode_Input.clear();
        Customer_PhoneNumber_Input.clear();

        ObservableList<Customers> refreshCustomersList = Customer_Access.getCustomers(connect);
        Customer_Table.setItems(refreshCustomersList);

        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/views/Customer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Customer Records");
        stage.setScene(scene);
        stage.show();

    }

    public void Edit_Button(ActionEvent actionEvent)                                        // populates text fields
    {
        try
        {
            JDBC.openConnection();
            Customers selectedCustomer = (Customers) Customer_Table.getSelectionModel().getSelectedItem();

            String divisionName = "";
            String countryName = "";

            if (selectedCustomer != null)
            {
                ObservableList<Country_Access> getCountries = Country_Access.getCountries();
                ObservableList<FirstLevelDivision_Access> getFirstleveldivision_Names = FirstLevelDivision_Access.getFirst_Level_Division();
                ObservableList<String> allFirstleveldivisionivision = FXCollections.observableArrayList();

                ObservableList<String> US_FSD = FXCollections.observableArrayList();
                ObservableList<String> UK_FSD = FXCollections.observableArrayList();
                ObservableList<String> CAN_FSD = FXCollections.observableArrayList();
                ObservableList<String> First_Level_Divisions_Names = FXCollections.observableArrayList();

                Customer_ID_Input.setText(String.valueOf((selectedCustomer.getCustomer_ID())));
                Customer_Name_Input.setText(selectedCustomer.getCustomer_Name());
                Customer_Address_Input.setText(selectedCustomer.getAddress());
                Customer_PostalCode_Input.setText(selectedCustomer.getPostal_Code());
                Customer_PhoneNumber_Input.setText(selectedCustomer.getPhone());

                for (First_Level_Division flDivision: getFirstleveldivision_Names)
                {
                    allFirstleveldivisionivision.add(flDivision.getDivision_name());
                    int countryIDToSet = flDivision.getCountry_ID();

                    if (flDivision.getDivision_ID() == selectedCustomer.getDivision_ID())
                    {
                        divisionName = flDivision.getDivision_name();

                        for (Country country: getCountries)
                        {
                            if (country.getCountry_ID() == countryIDToSet)
                            {
                                countryName = country.getCountry_Name();
                            }
                        }
                    }
                }
                //Customer_State.setValue(divisionName);
                Customer_Country_CB.setValue(countryName);




                for (First_Level_Division firstLevelDivision : getFirstleveldivision_Names)
                {

                    if (firstLevelDivision.getCountry_ID() == 1)
                    {
                        US_FSD.add(firstLevelDivision.getDivision_name());
                    }
                    else if (firstLevelDivision.getCountry_ID() == 2)
                    {
                        UK_FSD.add(firstLevelDivision.getDivision_name());
                    }
                    else if (firstLevelDivision.getCountry_ID() == 3)
                    {
                        CAN_FSD.add(firstLevelDivision.getDivision_name());
                    }

                    First_Level_Divisions_Names.add(firstLevelDivision.getDivision_name());
                }

                String finalDivisionName = divisionName;
                Customer_Country_CB.valueProperty().addListener((observable, oldValue, newValue) ->
                {
                    if ("U.S".equals(newValue))
                    {
                        Customer_State.setItems(US_FSD);
                        Customer_State.setValue(finalDivisionName);
                    }
                    if ("UK".equals(newValue))
                    {
                        Customer_State.setItems(UK_FSD);
                        Customer_State.setValue(finalDivisionName);

                    }
                    if ("Canada".equals(newValue))
                    {
                        Customer_State.setItems(CAN_FSD);
                        Customer_State.setValue(finalDivisionName);
                    }
                });


            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
