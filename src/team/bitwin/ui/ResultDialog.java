package team.bitwin.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class ResultDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTable tblResult;
    private JLabel lblConclusion;

    public ResultDialog(Stack<String[]> result) {
        setSize(new Dimension(500, 400));
        setContentPane(contentPane);
        setModal(true);
        pack();
        getRootPane().setDefaultButton(buttonOK);

        DefaultTableModel data = new DefaultTableModel();

        data.addColumn("a");
        data.addColumn("b");
        data.addColumn("c");
        data.addColumn("b - c");
        data.addColumn("f(c)");

        // Populating data
        for (String[] d : result) {
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
