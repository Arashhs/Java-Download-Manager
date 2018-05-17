import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;

public class SettingsFrame extends JDialog {
    private JPanel panel;
    private static String downloadDirectory;

    public SettingsFrame(){
        setModalityType(DEFAULT_MODALITY_TYPE);
        downloadDirectory = System.getProperty("user.home") +  "\\Desktop";
        panel = new JPanel(new GridLayout(5,1));
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
        JTextField textField = new JTextField();
        textField.setText(downloadDirectory);
        JButton button = new JButton("Select directory");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") +  "\\Desktop"));
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.setDialogTitle("Select directory");

                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    downloadDirectory = fileChooser.getSelectedFile().getAbsolutePath();
                    textField.setText(downloadDirectory);
                }
            }
        });
        JPanel panel3 = new JPanel(new FlowLayout());
        panel3.add(label2);
        panel3.add(button);
        panel.add(panel3);
        textField.setEditable(false);
        panel.add(textField);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel label3 = new JLabel("Select Skin: ");
        JPanel panel4 = new JPanel(new FlowLayout());
        JRadioButton laf1 = new JRadioButton("Default");
        JRadioButton laf2 = new JRadioButton("Metal");
        JRadioButton laf3 = new JRadioButton("Nimbus");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(laf1);
        buttonGroup.add(laf2);
        buttonGroup.add(laf3);
        laf1.setSelected(true);
        panel4.add(label3);
        panel4.add(laf1);
        panel4.add(laf2);
        panel4.add(laf3);
        panel.add(panel4);
        setTitle("Settings");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);


    }

    public static String getDownloadDirectory() {
        return downloadDirectory;
    }
}
