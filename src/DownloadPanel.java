import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

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
        progressBar.setValue(download.getDownloaded());
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
        panel.setBorder(BorderFactory.createLineBorder(Color.black,2));
      //  panel.setPreferredSize(new Dimension(200,50));
        JPopupMenu popMenu= new JPopupMenu();
        JMenuItem item1 = new JMenuItem("Add to queue");
        JMenuItem item2 = new JMenuItem("Info");
        item1.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        item2.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        popMenu.add(item1);
        popMenu.add(item2);
        panel.setComponentPopupMenu(popMenu);

        class PopUpMenueListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource().equals(item1)){
                    //add to queue
                }
                else if(e.getSource().equals(item2)){
                    DownloadInfo info = new DownloadInfo(download);
                    info.setVisible(true);
                }
            }
        }
        PopUpMenueListener popUpMenueListener = new PopUpMenueListener();
        item1.addActionListener(popUpMenueListener);
        item2.addActionListener(popUpMenueListener);




        class Listener implements MouseListener{

            Border redBorder = BorderFactory.createLineBorder(Color.RED,2);
            Border blackBorder = BorderFactory.createLineBorder(Color.BLACK,2);
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
                    /*
                    DownloadInfo info = new DownloadInfo(download);
                    info.setVisible(true); */
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
