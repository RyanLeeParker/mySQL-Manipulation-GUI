package controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Reports_Controller {
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
    public TableColumn CustomersByState_Column;
    public TableColumn CustomersByStateTotals_Column;
    public Button Cancel_Button;

    public void initialize() throws SQLException
    {
        // gen total # of cust appts by type and month
        // a schedule from each contact in org, includes most attributes   (apptID, title, type, desc, start, end, custID)
        // addtl report, count something, list something
    }


    public void SchedByContact_Tab_Selected(Event event) {
    }

    public void Contact_CB_Selected(ActionEvent actionEvent) {
    }

    public void AppointmentTotals_Tab_Selected(Event event) {
    }

    public void CustomersByState_Tab_Selected(Event event) {
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
