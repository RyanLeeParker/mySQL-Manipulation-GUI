package controller;

import com.sun.jdi.connect.spi.Connection;
import dao.Country_Access;
import helper.JDBC;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import helper.JDBC;

public class Customer_Controller 
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
    public ComboBox CustomerUpdate_Country_CB;
    public ComboBox CustomerUpdate_State;

    public void initialize(URL url, ResourceBundle resourceBundle) throws Exception
    {
        try
        {
            Connection connection = (Connection) JDBC.getConnection();

            ObservableList<Country_Access> Countries_All = Country_Access.getCountries();
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

    public void Save_Button(ActionEvent actionEvent)
    {

    }

    public void Delete_Button(ActionEvent actionEvent)
    {

    }

    public void Add_Button(ActionEvent actionEvent)
    {

    }

    public void Edit_Button(ActionEvent actionEvent)
    {

    }

    public void UpdateCountry_CB_Select(ActionEvent actionEvent)
    {

    }

    public void UpdateState_CB_Select(ActionEvent actionEvent)
    {

    }

    public void Country_CB_Select(ActionEvent actionEvent)
    {

    }

    public void State_CB_Select(ActionEvent actionEvent)
    {

    }
}
