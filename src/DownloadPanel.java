import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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



        class Listener implements MouseListener{

            Border redBorder = BorderFactory.createLineBorder(Color.RED,5);
            Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
            boolean isHighlighted;

            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (isHighlighted) {
                        panel.setBorder(blackBorder);
                        download.setSelected(false);
                    } else {
                        panel.setBorder(redBorder);
                        download.setSelected(true);
                    }
                    isHighlighted = !isHighlighted;
                }
                else if(SwingUtilities.isRightMouseButton(e)){
                    DownloadInfo info = new DownloadInfo(download);
                    info.setVisible(true);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        }

        Listener listener = new Listener();
        panel.addMouseListener(listener);



    }


    public JPanel getPanel() {
        return panel;
    }

    public void updateProgressBar(Download d){
        progressBar.setValue(d.getDownloaded());
    }
}
