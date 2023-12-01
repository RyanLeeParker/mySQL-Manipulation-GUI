package controller;

import dao.*;
import model.*;
import helper.JDBC;
import java.net.URL;
import model.Country;
import model.Customers;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Timestamp;
import java.util.Optional;
import java.io.IOException;
import java.sql.Connection;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import java.time.LocalDateTime;
import javafx.event.ActionEvent;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.sql.PreparedStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

/** This class is the main controller for all Customer related functions and manipulation. */
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
    public TableColumn Customer_Phone_Column;
    public TableColumn Customer_FLD_Column;
    public ComboBox Customer_State;
    public ComboBox Customer_Country_CB;
    public static int Cust_ID;

    /** This method initializes the customer page by retrieving customer data and populating the tableview.
     * It also sets the FirstLevelDivision comboboxes to only display States/Provines in their country.
     * @param url
     * @param resourceBundle*/
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

            Customer_Country_CB.valueProperty().addListener((observable, oldValue, newValue) ->                         // listener to detect changes
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

    /** This method returns to the main page.
     * @param actionEvent
     * @throws Exception*/
    public void Cancel_Button(ActionEvent actionEvent) throws Exception
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
        catch (Exception e)
        {
            Alert alert_err = new Alert(Alert.AlertType.WARNING);
            alert_err.setTitle("Something went wrong.");
            alert_err.setContentText("Please restart the program and try again.");
            alert_err.showAndWait();
        }
    }

    /** This method saves the data in the input field that were populated and editing by the user, upon hitting edit.
     * It error checks all input fields before attempting to save.
     * It overwrites the old customer.
     * It reloads the page upon completion to refresh the data displayed.
     * @param actionEvent */
    public void Save_Button(ActionEvent actionEvent)
    {
        try
        {
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

            for (FirstLevelDivision_Access firstLevelDivision : FirstLevelDivision_Access.getFirst_Level_Division())
            {
                if (Customer_State.getSelectionModel().getSelectedItem().equals(firstLevelDivision.getDivision_name()))
                {
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
            ps.setString(7, Users_Access.getCurrentUser());
            ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(9, Users_Access.getCurrentUser());
            ps.setInt(10, temp_FLD_ID);
            ps.setInt(11, Integer.parseInt(Customer_ID_Input.getText()));
            ps.execute();

            FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/views/Customer.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
            stage.setTitle("Customer Records");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e)
        {
            System.out.println("Customer Save exception encountered.");
            //e.printStackTrace();
        }
    }

    /** This method deletes the selected customer after deleting all of their associated appointments.
     * It includes error checking to ensure a customer is selected.
     * It then reloads the page to update the display data.
     * @param actionEvent
     * @throws Exception*/
    public void Delete_Button(ActionEvent actionEvent) throws Exception
    {
        try
        {
            Customers selectedCustomer = (Customers) Customer_Table.getSelectionModel().getSelectedItem();

            if (selectedCustomer == null)
            {
                Alert alertErr = new Alert(Alert.AlertType.WARNING);
                alertErr.setTitle("No Customer Selected");
                alertErr.setContentText("Please select a customer to delete.");
                alertErr.showAndWait();
                return;
            }

            int customerId = selectedCustomer.getCustomer_ID();

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Delete Confirmation");
            confirmationAlert.setHeaderText("Confirm Deletion");
            confirmationAlert.setContentText("Are you sure you want to delete this customer and their associated appointments?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK)
            {
                Connection connection = JDBC.openConnection();

                String deleteAppointmentsQuery = "DELETE FROM appointments WHERE Customer_ID = ?";
                JDBC.setPreparedStatement(JDBC.getConnection(), deleteAppointmentsQuery);
                PreparedStatement preparedStatementDeleteAppointments = JDBC.getPreparedStatement();
                preparedStatementDeleteAppointments.setInt(1, customerId);
                preparedStatementDeleteAppointments.execute();


                String deleteCustomerQuery = "DELETE FROM customers WHERE Customer_ID = ?";
                JDBC.setPreparedStatement(JDBC.getConnection(), deleteCustomerQuery);
                PreparedStatement preparedStatementDeleteCustomer = JDBC.getPreparedStatement();
                preparedStatementDeleteCustomer.setInt(1, customerId);
                preparedStatementDeleteCustomer.execute();

                JDBC.closeConnection();

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Customer Deleted");
                successAlert.setContentText("Customer and associated appointments have been deleted successfully!");
                successAlert.showAndWait();

                FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/views/Customer.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
                stage.setTitle("Customer Records");
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (Exception e)
        {
            Alert alertErr = new Alert(Alert.AlertType.ERROR);
            alertErr.setTitle("Deletion Error");
            alertErr.setContentText("An error occurred during customer deletion. Please try again.");
            alertErr.showAndWait();
            e.printStackTrace();
        }
    }

    /** This method takes input from the text fields, creates a new customer with it, and saves that customer to the database.
     * It includes extensive error checking or all input fields.
     * It reloads the page upon completion to refresh the tableview data.
     * @param actionEvent
     * @throws Exception*/
    public void Add_Button(ActionEvent actionEvent) throws Exception
    {
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

        int firstLevelDivisionID = 0;

        for (FirstLevelDivision_Access firstLevelDivision : FirstLevelDivision_Access.getFirst_Level_Division())
        {
            if (Customer_State.getSelectionModel().getSelectedItem().equals(firstLevelDivision.getDivision_name()))
            {
                firstLevelDivisionID = firstLevelDivision.getDivision_ID();
            }
        }

        Cust_ID++;

        String insertStatement = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";
        JDBC.setPreparedStatement(JDBC.getConnection(), insertStatement);
        PreparedStatement ps = JDBC.getPreparedStatement();
        ps.setInt(1, Cust_ID);
        ps.setString(2, Customer_Name_Input.getText());
        ps.setString(3, Customer_Address_Input.getText());
        ps.setString(4, Customer_PostalCode_Input.getText());
        ps.setString(5, Customer_PhoneNumber_Input.getText());
        ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(7, Users_Access.getCurrentUser());
        ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(9, Users_Access.getCurrentUser());
        ps.setInt(10, firstLevelDivisionID);
        ps.execute();

        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/views/Customer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Customer Records");
        stage.setScene(scene);
        stage.show();
    }

    /** This method calls another method to populate the text fields with a pre-existing customers data to edit.
     * It ensures a customer is selected.
     * @param actionEvent
     * @throws Exception*/
    public void Edit_Button(ActionEvent actionEvent) throws Exception
    {
        try
        {
            Customers selectedCustomer = (Customers) Customer_Table.getSelectionModel().getSelectedItem();

            if (selectedCustomer != null)
            {
                populateCustomerData(selectedCustomer);
            }
        }
        catch
        (Exception e)
        {
            //e.printStackTrace();
        }
    }

    /** This method populates the text fields with a pre-existing customers data.
     * It also ensure that the State/Provinces in the comboboxes are correct for their country.
     * @param selectedCustomer from previous function.*/
    private void populateCustomerData(Customers selectedCustomer)
    {
        try
        {
            ObservableList<Country_Access> getCountries = Country_Access.getCountries();
            ObservableList<FirstLevelDivision_Access> getFirstleveldivision_Names = FirstLevelDivision_Access.getFirst_Level_Division();
            ObservableList<String> allFirstleveldivisionivision = FXCollections.observableArrayList();
            ObservableList<String> US_FSD = FXCollections.observableArrayList();
            ObservableList<String> UK_FSD = FXCollections.observableArrayList();
            ObservableList<String> CAN_FSD = FXCollections.observableArrayList();
            ObservableList<String> First_Level_Divisions_Names = FXCollections.observableArrayList();

            Customer_ID_Input.setText(String.valueOf(selectedCustomer.getCustomer_ID()));
            Customer_Name_Input.setText(selectedCustomer.getCustomer_Name());
            Customer_Address_Input.setText(selectedCustomer.getAddress());
            Customer_PostalCode_Input.setText(selectedCustomer.getPostal_Code());
            Customer_PhoneNumber_Input.setText(selectedCustomer.getPhone());

            String divisionName = "";
            String countryName = "";

            for (First_Level_Division flDivision : getFirstleveldivision_Names)
            {
                allFirstleveldivisionivision.add(flDivision.getDivision_name());
                int countryIDToSet = flDivision.getCountry_ID();

                if (flDivision.getDivision_ID() == selectedCustomer.getDivision_ID())
                {
                    divisionName = flDivision.getDivision_name();

                    for (Country country : getCountries)
                    {
                        if (country.getCountry_ID() == countryIDToSet)
                        {
                            countryName = country.getCountry_Name();
                        }
                    }
                }
            }

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

            if (countryName.equals("U.S"))
            {
                Customer_State.setItems(US_FSD);
                Customer_State.setValue(divisionName);
            }
            else if (countryName.equals("UK"))
            {
                Customer_State.setItems(UK_FSD);
                Customer_State.setValue(divisionName);
            }
            else if (countryName.equals("Canada"))
            {
                Customer_State.setItems(CAN_FSD);
                Customer_State.setValue(divisionName);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}