import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Download panel for each downloads. Will be added to main panel of program
 * @author Arash
 * @version 1.0.0
 */
public class DownloadPanel {
    private JPanel panel;
    private JLabel fileName;
    private JProgressBar progressBar;
    private JLabel progress;
    private JLabel speed;
    private JLabel downloadState;


    public DownloadPanel(Download download) {
        speed = new JLabel("0Mb/s");
        GridLayout layout = new GridLayout(0, 1);
        panel = new JPanel(layout);
        JPanel panel2 = new JPanel(new GridLayout(1, 3));
        fileName = new JLabel();
        progressBar = new JProgressBar(0, 100);
        progressBar.setMinimum(0);
        progressBar.setMaximum((int) download.getDownloadedSize());
        progressBar.setValue((int) download.getDownloaded());
        progress = new JLabel();
        progress.setText(download.getStringSizeLengthFile(download.getDownloaded()) + " / " + download.getStringSizeLengthFile(download.getDownloadedSize()));
        fileName.setText(download.getFileName());
        progressBar.setStringPainted(true);
        fileName.setHorizontalAlignment(SwingConstants.CENTER);
        progress.setHorizontalAlignment(SwingConstants.CENTER);
        downloadState = new JLabel();
        downloadState.setText(download.getDownloadStatus() == 0 ? "Paused" : download.getDownloadStatus() == 1 ? "Downloading" : download.getDownloadStatus() == 2 ? "Completed" : "Waiting");
        downloadState.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(fileName);
        panel.add(progressBar);
        panel.add(panel2);
        speed.setHorizontalAlignment(SwingConstants.CENTER);
        panel2.add(speed);
        panel2.add(downloadState);
        panel2.add(progress);
        panel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        //  panel.setPreferredSize(new Dimension(200,50));
        JPopupMenu popMenu = new JPopupMenu();
        JMenuItem item1 = new JMenuItem("Add to queue");
        JMenuItem item2 = new JMenuItem("Info");
        item1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        item2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        popMenu.add(item1);
        popMenu.add(item2);
        panel.setComponentPopupMenu(popMenu);

        class PopUpMenueListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(item1)) {
                    if (!JDMUI.getQueuedDownloads().contains(download)) {
                        JDMUI.addAlreadyDownloadingToQueue(download);
                    }
                } else if (e.getSource().equals(item2)) {
                    DownloadInfo info = new DownloadInfo(download);
                    info.setVisible(true);
                }
            }
        }
        PopUpMenueListener popUpMenueListener = new PopUpMenueListener();
        item1.addActionListener(popUpMenueListener);
        item2.addActionListener(popUpMenueListener);


        /**
         * Handles selecting by mouse click
         */
        class Listener implements MouseListener {


            Border redBorder = BorderFactory.createLineBorder(Color.RED, 2);
            Border blackBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
            boolean isHighlighted;

            @Override
            public void mouseClicked(MouseEvent e) {

                if ((e.getClickCount() == 2) && (download.isCompleted())) {
                    panel.setBorder(blackBorder);
                    download.setSelected(false);
                    Desktop desktop = Desktop.getDesktop();
                    File file = new File(download.getFilePath());
                    if(file.exists()) {
                        try {
                            desktop.open(file);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        if (isHighlighted) {
                            panel.setBorder(blackBorder);
                            download.setSelected(false);
                        } else {
                            panel.setBorder(redBorder);
                            download.setSelected(true);
                        }
                        isHighlighted = !isHighlighted;
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                    /*
                    DownloadInfo info = new DownloadInfo(download);
                    info.setVisible(true); */
                    }
                }
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

        if (progressBar.getValue() == progressBar.getMaximum()) {
            download.setDownloadStatus(2);
            download.setCompleted(true);
        }

    }


    public JPanel getPanel() {
        return panel;
    }

    /**
     * updates progressbar
     *
     * @param d Download task
     */
    public void updateProgressBar(Download d , double downloadSpeed) {
        progressBar.setValue((int) d.getDownloaded());
        progress.setText(d.getStringSizeLengthFile(d.getDownloaded()) + " / " + d.getStringSizeLengthFile(d.getDownloadedSize()));
        DecimalFormat df = new DecimalFormat("0.0");
        speed.setText(df.format(downloadSpeed)+" KB/s");
        panel.revalidate();
        panel.repaint();
        if (progressBar.getValue() == progressBar.getMaximum()) {
            d.setDownloadStatus(2);
            d.setCompleted(true);
            JDMUI.showCurrentList();
            if(d.isQueued()){
                JDMUI.getQueuedDownloads().remove(d);
                JDMUI.showQueueList();
                FileUnits.saveQueue(JDMUI.getQueuedDownloads());
            }
        }
    }

    /**
     * Updates download's state
     * @param d Download task
     */
    public void updateDownloadState(Download d){
        downloadState.setText(d.getDownloadStatus() == 0 ? "Paused" : d.getDownloadStatus() == 1 ? "Downloading" : d.getDownloadStatus() == 2 ? "Completed" : "Waiting");
    }
}
