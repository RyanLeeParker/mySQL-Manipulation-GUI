package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Login_Screen
{

    public TextField Username_textfield;
    public TextField Password_textfield;
    public Label Location_label;
    public Label Error_Message;
    public Button Login_Button;
    public Button CancelButton;

    public void Login_Button_Press(ActionEvent actionEvent)
    {



    }

    public void Cancel_Press(ActionEvent actionEvent)
    {
        System.exit(0);
    }
}
