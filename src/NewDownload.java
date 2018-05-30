import com.sun.scenario.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.Object;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * New download panel
 * @author Arash
 * @version 1.0.0
 */
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
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isFiltered(tf.getText())){
                    JOptionPane optionPane = new JOptionPane();
                    JOptionPane.showMessageDialog(optionPane,"Selected URL is blocked.","Attention",optionPane.ERROR_MESSAGE);
                }
                else if(!isValidURL(tf.getText())){
                    JOptionPane optionPane = new JOptionPane();
                    JOptionPane.showMessageDialog(optionPane,"Please enter a valid URL","Attention",optionPane.ERROR_MESSAGE);
                }
                else if(isAlreadyDownloading(tf.getText())){
                    JOptionPane optionPane = new JOptionPane();
                    JOptionPane.showMessageDialog(optionPane,"Download task already exists","Attention",optionPane.ERROR_MESSAGE);
                }
                else {
                    if (button1.isSelected()) {
                        Download download = new Download(tf.getText());
                        JDMUI.addDownload(download);
                        JDMUI.showDownloadList();
                        dispose();
                    } else if (button2.isSelected()) {
                        Download download = new Download(tf.getText());
                        JDMUI.addQueued(download);
                        download.setQueued(true);
                        JDMUI.showDownloadList();
                        dispose();
                    }
                }
            }
        });
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

    /**
     *
     * @param URL URL of new download
     * @return true: is filtered | false: is not filtered
     */
    public boolean isFiltered(String URL){
        for(String string: FileUnits.loadFilteredURLs()){
            if(URL.contains(string))
                return true;
        }
        return false;
    }

    public boolean isValidURL(String url) {

        URL u = null;

        try {
            u = new URL(url);
        } catch (MalformedURLException e) {
            return false;
        }

        try {
            u.toURI();
        } catch (URISyntaxException e) {
            return false;
        }

        return true;
    }

    public boolean isAlreadyDownloading(String s){
        for(Download download: JDMUI.getDownloads()){
            if(download.getUrl().equals(s))
                return true;
        }
        return false;
    }

    }
