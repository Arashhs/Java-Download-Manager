import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DownloadPanel {
    private JPanel panel;
    private JLabel fileName;
    private JProgressBar progressBar;
    private JLabel progress;
    private JLabel speed;

    public DownloadPanel(Download download) {
        speed = new JLabel("0Mb/s");
        GridLayout layout = new GridLayout(0,1);
        panel = new JPanel(layout);
        JPanel panel2 = new JPanel(new GridLayout(1,2));
        fileName = new JLabel();
        progressBar = new JProgressBar(0,100);
        progressBar.setMinimum(0);
        progressBar.setMaximum(download.getDownloadedSize());
        progressBar.setValue(0);
        progress = new JLabel();
        progress.setText(download.getDownloaded()+"MB/"+download.getDownloadedSize()+"MB");
        fileName.setText(download.getFileName());
        progressBar.setStringPainted(true);
        fileName.setHorizontalAlignment(SwingConstants.CENTER);
        progress.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(fileName);
        panel.add(progressBar);
        panel.add(panel2);
        speed.setHorizontalAlignment(SwingConstants.CENTER);
        panel2.add(speed);
        panel2.add(progress);
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
      //  panel.setPreferredSize(new Dimension(200,50));



    }


    public JPanel getPanel() {
        return panel;
    }

    public void updateProgressBar(Download d){
        progressBar.setValue(d.getDownloaded());
    }
}
