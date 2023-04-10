package tools;

import javax.swing.*;

public class Helper {
    public Helper() {

    }

    public static void InformationBox(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public static void WarningBox(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public static void ErrorBox(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public static int confirmMSg(String message) {
        return JOptionPane.showConfirmDialog(null, message);
    }


}
