import javax.swing.*;
import java.awt.*;

public class DownloadInfo extends JDialog {

    public DownloadInfo(Download download) {
        setTitle(download.getFileName() + " Info");
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(6, 1));
        setContentPane(panel);
        JLabel fileName1, fileName2;
        fileName1 = new JLabel("    File: ");
        fileName2 = new JLabel();
        fileName2.setText(download.getFileName());
        JLabel status1, status2;
        status1 = new JLabel("    Status");
        status2 = new JLabel();
        status2.setText(download.getDownloadStatus() == 0 ? "Paused" : download.getDownloadStatus() == 1 ? "Downloading" : "Completed");
        JLabel size1 , size2;
        size1 = new JLabel("    Size: ");
        size2 = new JLabel();
        size2.setText(Integer.toString(download.getDownloadedSize())+"MB");
        JLabel saveTo1,saveTo2;
        saveTo1 = new JLabel("    Save To: ");
        saveTo2 = new JLabel(SettingsFrame.getDownloadDirectory());
        JLabel url1,url2;
        url1 = new JLabel("    URL: ");
        url2 = new JLabel(download.getURL());
        JPanel panel1 = new JPanel(new GridLayout(1,2));
        panel1.add(fileName1);
        panel1.add(fileName2);
        panel1.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(panel1);
        JPanel panel2 = new JPanel(new GridLayout(1,2));
        panel2.add(status1);
        panel2.add(status2);
        panel2.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(panel2);
        JPanel panel3 = new JPanel(new GridLayout(1,2));
        panel3.add(size1);
        panel3.add(size2);
        panel3.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(panel3);
        JPanel panel4 = new JPanel(new GridLayout(1,2));
        panel4.add(saveTo1);
        panel4.add(saveTo2);
        panel4.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(panel4);
        JPanel panel5 = new JPanel(new GridLayout(1,2));
        panel5.add(url1);
        panel5.add(url2);
        panel5.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(panel5);
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        JPanel panel6 = new JPanel(new GridLayout(1,2));
        JLabel date1,date2;
        date1 = new JLabel("    Started Date: ");
        date2 = new JLabel();
        date2.setText(download.getStartDate().toString());
        panel6.add(date1);
        panel6.add(date2);
        panel6.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(panel6);
        setSize(270,350);
        this.setLocationRelativeTo(null);



    }
}
