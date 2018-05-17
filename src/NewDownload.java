import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewDownload extends JDialog {

    public NewDownload(){
        setModalityType(DEFAULT_MODALITY_TYPE);
        setTitle("New Download");
        JPanel panel = new JPanel(new GridLayout(4,1));
        setContentPane(panel);
        JTextField tf = new JTextField("Enter URL");
        panel.add(tf);
        JLabel label = new JLabel("Start Type:");
        JRadioButton button1 = new JRadioButton("Now");
        JRadioButton button2 = new JRadioButton("Queues");
        JButton button3 = new JButton("OK");
        JButton button4 = new JButton("Cancel");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(tf);
        JPanel panel2 = new JPanel(new GridLayout(1,2));
        JPanel panel3 = new JPanel(new FlowLayout());
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(button1);
        buttonGroup.add(button2);
        button1.setSelected(true);
        panel2.add(button3);
        panel2.add(button4);
        panel.add(tf);
        panel3.add(button1);
        panel3.add(button2);
        panel.add(label);
        panel.add(panel3);
        panel.add(panel2);
        setSize(500,150);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

    }
}
