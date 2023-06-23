package controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;

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

    public void SchedByContact_Tab_Selected(Event event) {
    }

    public void Contact_CB_Selected(ActionEvent actionEvent) {
    }

    public void AppointmentTotals_Tab_Selected(Event event) {
    }

    public void CustomersByState_Tab_Selected(Event event) {
    }

    public void Cancel_Button(ActionEvent actionEvent) {
    }
}
