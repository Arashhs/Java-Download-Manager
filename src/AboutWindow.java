import javax.swing.*;
import java.awt.*;

import static java.awt.Dialog.DEFAULT_MODALITY_TYPE;

public class AboutWindow {
    private JDialog frame ;

    public AboutWindow(){
        frame = new JDialog();
        frame.setModalityType(DEFAULT_MODALITY_TYPE);
        frame.setTitle("About Me");
        String txt = "Java Download Manager version 1.00";
        JLabel label = new JLabel(txt);
        label.setFont(new Font("Roman",Font.BOLD,24));
        String text = "Author: Arash Hajisafi - 9631019\nStarted date for coding project: 4/26/2018\nEnd date: ??/26/2018\nFollow tool tips to use program";
        JTextArea textArea = new JTextArea(text);
        textArea.setFont(new Font("Serif",Font.PLAIN,17));
        textArea.setEditable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(label);
        panel.add(textArea);
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);

    }

    public void setVisible(boolean a) {
        frame.pack();
        if (a) {
            frame.setVisible(true);
        } else
            frame.setVisible(false);
    }


}
