package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        System.out.println("Mic check 1, 2, 1, 2");
    }

    public void Test_Push(ActionEvent actionEvent)
    {
        System.out.println("Button Pressed");
    }

    // two main model obj classes, Customer & Appointments
    // only translate login screen, America doesn't have to be translated
}
