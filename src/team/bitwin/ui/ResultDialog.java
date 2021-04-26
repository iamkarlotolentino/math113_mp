package team.bitwin.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class ResultDialog extends JDialog {
    public static final int BISECTION_TYPE = 0x0;
    public static final int NEWTON_TYPE = 0x1;
    public static final int SECANT_TYPE = 0x3;

    private JPanel contentPane;
    private JButton buttonOK;
    private JTable tblResult;
    private JLabel lblConclusion;

    public ResultDialog(int type, Stack<String[]> result, String conclusion) {
        setTitle("Root approximation result");
        setSize(new Dimension(800, 400));
        setContentPane(contentPane);
        setLocationRelativeTo(null);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        lblConclusion.setText(conclusion);

        DefaultTableModel data = new DefaultTableModel();

        data.addColumn("n");
        switch (type) {
            case BISECTION_TYPE:
                data.addColumn("a");
                data.addColumn("b");
                data.addColumn("c");
                data.addColumn("b - c");
                data.addColumn("f(c)");
                break;
            case NEWTON_TYPE:
            case SECANT_TYPE:
                data.addColumn("<html>x<sub>n</sub></html");
                data.addColumn("<html>f(x<sub>n</sub>)</html>");
                data.addColumn("<html>x<sub>n</sub>-x<sub>n</sub>-1</html>");
                break;
        }

        for (int i = 0; i < result.size(); i++) {
            String[] array = result.get(i);
            String[] d = new String[array.length + 1];
            d[0] = String.valueOf(i);
            for (int j = 0; j < array.length; j++) d[j + 1] = array[j];
            data.addRow(d);
        }

        tblResult.setModel(data);
        tblResult.setDragEnabled(false);
        tblResult.setRowHeight(30);
        tblResult.setFont(new Font("Times New Roman", Font.PLAIN, 14));

        buttonOK.addActionListener(e -> onOK());
    }

    private void onOK() {
        dispose();
    }
}
