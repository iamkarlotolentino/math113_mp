package team.bitwin.ui;

import javax.swing.*;

import static team.bitwin.parser.ValidatorUtils.isNumber;

public class Utils {
    public static boolean hasBlank(JTextField... args) {
        for (JTextField tf : args) {
            if (tf.getText().isBlank()) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasInvalidNumber(JTextField... args) {
        for (JTextField tf : args) {
            if (! isNumber(tf.getText())) {
                return true;
            }
        }
        return false;
    }
}
