import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;

public class SettingsFrame extends JDialog {
    private JPanel panel;

    public SettingsFrame(){
        panel = new JPanel(new GridLayout(4,1));
        setContentPane(panel);
        JPanel panel2 = new JPanel(new FlowLayout());
        JLabel label1 = new JLabel("Maximum number of downloading files at the same time: (Set 0 for unlimited) ");
        SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 100, 1);
        JSpinner spinner = new JSpinner(model);
        JFormattedTextField txt = ((JSpinner.NumberEditor) spinner.getEditor()).getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
        panel2.add(label1);
        panel2.add(spinner);
        panel.add(panel2);
        setSize(500,300);
        JLabel label2 = new JLabel("Download directory folder:");
        JButton button = new JButton("Select File");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") +  "/Desktop"));
                int result = fileChooser.showOpenDialog(SettingsFrame.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                }
            }
        });
        JPanel panel3 = new JPanel(new FlowLayout());
        panel3.add(label2);
        panel3.add(button);
        panel.add(panel3);


    }

}
