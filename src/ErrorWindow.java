import javax.swing.*;

public class ErrorWindow {

    // Instantiate the objects for GUI
    JFrame errorInput = new JFrame();
    JLabel labelInput = new JLabel("Error, Input number, Please Try again");

    // Constructor
    ErrorWindow()
    {
        InputError();

    }

    // Window for NumberFormat exception
    public void InputError()
    {
        errorInput.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        errorInput.setBounds(500,300, 400,100);
        labelInput.setSize(50,40);
        labelInput.setHorizontalAlignment((int)JFrame.CENTER_ALIGNMENT);
        errorInput.add(labelInput);
        errorInput.setVisible(true);
    }
}
