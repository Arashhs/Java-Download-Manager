import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Dialog.DEFAULT_MODALITY_TYPE;
import static javax.swing.SwingUtilities.updateComponentTreeUI;

public class JDMUI {
    private static JFrame frame;
    private static JPanel panel5;
    private static ArrayList<Download> downloads;
    private static ArrayList<Download> queuedDownloads;
    private static JPanel panel6;
    private static ArrayList<Download> searchResult;
    private static JTextField searchField;

    public JDMUI() throws AWTException {
        downloads = new ArrayList<Download>();
        queuedDownloads = new ArrayList<Download>();
        searchResult = new ArrayList<Download>();
        frame = new JFrame("Java Download Manager V1.00");
        JPanel panel1 = new JPanel();
        SpringLayout layout1 = new SpringLayout();
        Icon newIcon = new ImageIcon(getClass().getResource("assets\\new.png"));
        Icon pauseIcon = new ImageIcon(getClass().getResource("assets\\pause.png"));
        Icon resumeIcon = new ImageIcon(getClass().getResource("assets\\resume.png"));
        Icon cancelIcon = new ImageIcon(getClass().getResource("assets\\cancel.png"));
        Icon removeIcon = new ImageIcon(getClass().getResource("assets\\remove.png"));
        Icon settingsIcon = new ImageIcon(getClass().getResource("assets\\settings.png"));
        JButton newButton = new JButton("", newIcon);
        JButton pauseButton = new JButton("", pauseIcon);
        JButton resumeButton = new JButton("", resumeIcon);
        JButton cancelButton = new JButton("", cancelIcon);
        JButton removeButton = new JButton("", removeIcon);
        JButton settingsButton = new JButton("", settingsIcon);
        ArrayList<JButton> downloadButtons = new ArrayList<>();
        downloadButtons.add(newButton);
        downloadButtons.add(pauseButton);
        downloadButtons.add(resumeButton);
        downloadButtons.add(cancelButton);
        downloadButtons.add(removeButton);
        downloadButtons.add(settingsButton);
        newButton.setToolTipText("New download");
        pauseButton.setToolTipText("Pause");
        resumeButton.setToolTipText("Resume");
        cancelButton.setToolTipText("Cancel download task");
        removeButton.setToolTipText("Completely remove download task");
        settingsButton.setToolTipText("Settings");
        JToolBar downloadToolbar = new JToolBar();
        for (int i = 0; i < downloadButtons.size(); i++) {
            downloadButtons.get(i).setPreferredSize(new Dimension(56,30));
           downloadToolbar.add(downloadButtons.get(i));
            if(downloadButtons.get(i).equals(newButton) || downloadButtons.get(i).equals(cancelButton)||downloadButtons.get(i).equals(removeButton))
                downloadToolbar.addSeparator(new Dimension(20,20));
        //    downloadButtons.get(i).setContentAreaFilled(false);
            downloadButtons.get(i).setFocusPainted(false);
            downloadButtons.get(i).setBackground(Color.decode("#d0dff8"));
        }
        frame.setPreferredSize(new Dimension(900,400));
        panel1.setLayout(layout1);
        downloadToolbar.add(Box.createHorizontalGlue());
        layout1.putConstraint(SpringLayout.EAST,downloadToolbar,0,SpringLayout.EAST,panel1);
        layout1.putConstraint(SpringLayout.WEST,downloadToolbar,0,SpringLayout.WEST,panel1);
        downloadToolbar.setBackground(Color.decode("#d0dff8"));
     //   panel1.add(downloadToolbar);
        frame.add(panel1);
        searchField = new JTextField("Search files");
        downloadToolbar.add(searchField);
        searchField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                searchField.setText(null); // Empty the text field when it receives focus
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().equals(""))
                    searchField.setText("Search files");
            }

        });
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchDownloads(searchField.getText());
                showSearchResult();
            }
        });
        JMenuBar menuBar = new JMenuBar();
        JMenu downloadMenu = new JMenu("Download");
        JMenuItem newDownloadMenu = new JMenuItem("New Download");
        JMenuItem pauseDownloadMenu = new JMenuItem("Pause");
        JMenuItem resumeDownloadMenu = new JMenuItem("Resume");
        JMenuItem cancelDownloadMenu = new JMenuItem("Cancel");
        JMenuItem removeDownloadMenue = new JMenuItem("Remove");
        JMenuItem settingsDownloadMenue = new JMenuItem("Settings");
        JMenuItem exitDownloadMenu = new JMenuItem("Exit");
        downloadMenu.add(newDownloadMenu);
        downloadMenu.addSeparator();
        downloadMenu.add(pauseDownloadMenu);
        downloadMenu.add(resumeDownloadMenu);
        downloadMenu.add(cancelDownloadMenu);
        downloadMenu.addSeparator();
        downloadMenu.add(removeDownloadMenue);
        downloadMenu.addSeparator();
        downloadMenu.add(settingsDownloadMenue);
        downloadMenu.addSeparator();
        downloadMenu.add(exitDownloadMenu);
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutMenuItem = new JMenuItem("About");
        helpMenu.add(aboutMenuItem);
        aboutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AboutWindow aboutWindow = new AboutWindow();
                aboutWindow.setVisible(true);
            }
        });

        menuBar.add(downloadMenu);
        menuBar.add(helpMenu);
        frame.setJMenuBar(menuBar);
        helpMenu.setMnemonic('2');
        aboutMenuItem.setMnemonic(KeyEvent.VK_A);
        KeyStroke ctrlXKeyStroke = KeyStroke.getKeyStroke("control A");
        aboutMenuItem.setAccelerator(ctrlXKeyStroke);
        downloadMenu.setMnemonic('1');
        KeyStroke ctrlSKeyStroke = KeyStroke.getKeyStroke("control S");
        settingsDownloadMenue.setAccelerator(ctrlSKeyStroke);
        settingsDownloadMenue.setMnemonic('s');
        KeyStroke ctrlQKeyStroke = KeyStroke.getKeyStroke("control Q");
        exitDownloadMenu.setAccelerator(ctrlQKeyStroke);
        exitDownloadMenu.setMnemonic('q');
        KeyStroke ctrlDKeyStroke = KeyStroke.getKeyStroke("control D");
        newDownloadMenu.setAccelerator(ctrlDKeyStroke);
        newDownloadMenu.setMnemonic('d');


        //panel1.add(downloadTable);
        JPanel panel2 = new JPanel(new GridLayout());
        JPanel panel3 = new JPanel(new FlowLayout());
        ImageIcon eagleIcon = new ImageIcon(getClass().getResource("assets\\logo.png"));
        JLabel label3 = new JLabel();
        label3.setIcon(eagleIcon);
        layout1.putConstraint(SpringLayout.NORTH,panel2,0,SpringLayout.SOUTH,downloadToolbar);
        layout1.putConstraint(SpringLayout.SOUTH,panel2,0,SpringLayout.SOUTH,panel1);
        layout1.putConstraint(SpringLayout.EAST,panel2,0,SpringLayout.EAST,panel1);
        layout1.putConstraint(SpringLayout.WEST,panel2,140,SpringLayout.WEST,panel1);
        layout1.putConstraint(SpringLayout.NORTH,panel3,0,SpringLayout.NORTH,frame);
        layout1.putConstraint(SpringLayout.WEST,downloadToolbar,0,SpringLayout.WEST,panel2);
        //layout1.putConstraint(SpringLayout.EAST,panel3,0,SpringLayout.WEST,downloadToolbar);
       // layout1.putConstraint(SpringLayout.WEST,panel3,0,SpringLayout.WEST,frame);
        panel3.setBackground(Color.decode("#32363f"));
        panel2.setBackground(Color.decode("#e7effb"));
        panel1.add(downloadToolbar);
        //panel2.add(downloadTable);
        panel1.add(panel2);
/*
        layout1.putConstraint(SpringLayout.NORTH,label3,0,SpringLayout.NORTH,frame);
        layout1.putConstraint(SpringLayout.WEST,label3,0,SpringLayout.WEST,frame);
        layout1.putConstraint(SpringLayout.EAST,label3,0,SpringLayout.WEST,downloadToolbar);
        */


        panel3.add(label3);
        layout1.putConstraint(SpringLayout.SOUTH,panel3,0,SpringLayout.SOUTH,panel1);
        panel1.add(panel3);
       // JTabbedPane tabs = new JTabbedPane(SwingConstants.VERTICAL);
       // panel3.add(tabs);
        Icon processingIcon = new ImageIcon(getClass().getResource("assets\\processing.png"));
        Icon completedIcon = new ImageIcon(getClass().getResource("assets\\completed.png"));
        Icon queuesIcon = new ImageIcon(getClass().getResource("assets\\queue.png"));
        JButton processingButton = new JButton();
        JButton completedButton = new JButton();
        JButton queuesButton = new JButton();
        downloadToolbar.setFloatable(false);
        JButton[] tabButtons = new JButton[3];
        tabButtons[0] = processingButton;
        tabButtons[1] = completedButton;
        tabButtons[2] = queuesButton;
        layout1.putConstraint(SpringLayout.NORTH, processingButton, 0, SpringLayout.SOUTH, label3);
        layout1.putConstraint(SpringLayout.NORTH, completedButton, 0, SpringLayout.SOUTH, processingButton);
        layout1.putConstraint(SpringLayout.NORTH, queuesButton, 0, SpringLayout.SOUTH, completedButton);


        for(int i = 0 ; i < 3 ; i++) {
            tabButtons[i].setPreferredSize(new Dimension(140, 40));
        //    layout1.putConstraint(SpringLayout.NORTH, tabButtons[i], 0, SpringLayout.SOUTH, tabButtons[i]);
            layout1.putConstraint(SpringLayout.WEST, tabButtons[i], 0, SpringLayout.WEST, frame);
            layout1.putConstraint(SpringLayout.EAST, tabButtons[i], 0, SpringLayout.WEST, downloadToolbar);
            tabButtons[i].setBackground(Color.decode("#32363f"));
            if(i==0) {
                tabButtons[i].setIcon(processingIcon);
                tabButtons[i].setText("Processing");
            }
            else if(i==1){
                tabButtons[i].setIcon(completedIcon);
                tabButtons[i].setHorizontalAlignment(SwingConstants.LEFT);
                tabButtons[i].setText("Completed");
            }
            else if(i==2){
                tabButtons[i].setIcon(queuesIcon);
                tabButtons[i].setText("      Queue");

            }

            processingButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JDMUI.showDownloadList();
                }
            });

            tabButtons[i].setIconTextGap(30);
            tabButtons[i].setVerticalTextPosition(SwingConstants.CENTER);
            tabButtons[i].setHorizontalTextPosition(SwingConstants.RIGHT);
            tabButtons[i].setBackground(Color.GRAY);
            panel1.add(tabButtons[i]);
            tabButtons[i].setVisible(true);
        }
        BorderLayout borderLayout = new BorderLayout();
        GridLayout gridLayout = new GridLayout(10,1);
        JPanel panel4 = new JPanel(borderLayout);
        panel5 = new JPanel(gridLayout);
        JScrollPane scrollPane = new JScrollPane(panel4,   ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel4.add(panel5,BorderLayout.NORTH);
        panel4.add(panel5);
        panel2.add(scrollPane);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("assets\\icon.png"));
        frame.setIconImage(img);
        PopupMenu popMenu= new PopupMenu();
        MenuItem item1 = new MenuItem("Exit");
        popMenu.add(item1);
        TrayIcon trayIcon = new TrayIcon(img, "JDM", popMenu);
        try{SystemTray.getSystemTray().add(trayIcon);
            trayIcon.setImageAutoSize(true);
            trayIcon.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        setVisible(true);
                        frame.setExtendedState(JFrame.NORMAL);
                    }
                }

            });
        }
        catch (Exception e){

            System.err.print("Exception");
        }






        class ButtonListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
                if(e.getSource().equals(settingsButton)||e.getSource().equals(settingsDownloadMenue)){
                    SettingsFrame settingsFrame = null;
                    try {
                        settingsFrame = new SettingsFrame();
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    settingsFrame.setVisible(true);
            }
            else if(e.getSource().equals(newButton)||e.getSource().equals(newDownloadMenu)){
                    NewDownload newDownload = new NewDownload();
                    newDownload.setVisible(true);

                }
                else if(e.getSource().equals(exitDownloadMenu)||e.getSource().equals(item1))
                    System.exit(0);

                else if(e.getSource().equals(removeButton)||e.getSource().equals(removeDownloadMenue)){
                    int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove selected tasks?", "Remove", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                            Iterator<Download> it = downloads.iterator();
                            Iterator<Download> it2 = queuedDownloads.iterator();
                            for(Download download: downloads){
                                if(download.isSelected())
                                    FileUnits.backupRemovedDownload(download);
                            }
                            while (it.hasNext()) {
                                if (it.next().isSelected()) {
                                    it.remove();
                                }
                            }
                        while (it2.hasNext()) {
                            if (it2.next().isSelected()) {
                                it2.remove();
                            }
                        }
                            showDownloadList();
                            FileUnits.saveAllDownloads(downloads);
                            FileUnits.saveQueue(queuedDownloads);
                    }
                    else {

                    }
                }
                else if(e.getSource().equals(pauseButton)||e.getSource().equals(pauseDownloadMenu))
                    System.out.println("Pause");
                else if(e.getSource().equals(resumeButton)||e.getSource().equals(resumeDownloadMenu))
                    System.out.println("Resume");
                else if(e.getSource().equals(cancelButton)||e.getSource().equals(cancelDownloadMenu))
                    System.out.println("Cancel");
        }

    }

        JButton startQueue = new JButton("Start");
        JButton stopQueue = new JButton("Pause");
        JButton removeFromQueue = new JButton("Remove queue");
        panel6 = new JPanel(new GridLayout(1, 5));
        JButton up = new JButton();
        JButton down = new JButton();
        ImageIcon upIcon = new ImageIcon(getClass().getResource("assets\\up.png"));
        ImageIcon downIcon = new ImageIcon(getClass().getResource("assets\\down.png"));
        up.setIcon(upIcon);
        down.setIcon(downIcon);
        panel6.add(startQueue);
        panel6.add(stopQueue);
        panel6.add(removeFromQueue);
        panel6.add(up);
        panel6.add(down);
        panel6.setVisible(false);

        up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = queuedDownloads.size()-1 ; i >= 0 ; i--){
                    if(queuedDownloads.get(i).isSelected() && i<queuedDownloads.size()-1){
                        Collections.swap(queuedDownloads,i,i+1);
                    }
                }
                JDMUI.showQueueList();
            }
        });

        down.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0 ; i < queuedDownloads.size() ; i++){
                    if(queuedDownloads.get(i).isSelected() && i>0){
                        Collections.swap(queuedDownloads,i,i-1);
                    }
                }
                JDMUI.showQueueList();
            }
        });

        removeFromQueue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Iterator<Download> it = queuedDownloads.iterator();
                while (it.hasNext()) {
                    if (it.next().isSelected()) {
                        it.remove();
                    }
                }
                showQueueList();
                FileUnits.saveQueue(queuedDownloads);
            }
        });

        ButtonListener buttonListener = new ButtonListener();
    settingsButton.addActionListener(buttonListener);
    settingsDownloadMenue.addActionListener(buttonListener);
    newDownloadMenu.addActionListener(buttonListener);
    newButton.addActionListener(buttonListener);
    exitDownloadMenu.addActionListener(buttonListener);
    item1.addActionListener(buttonListener);
    removeButton.addActionListener(buttonListener);
    removeDownloadMenue.addActionListener(buttonListener);
    pauseButton.addActionListener(buttonListener);
    pauseDownloadMenu.addActionListener(buttonListener);
    resumeButton.addActionListener(buttonListener);
    resumeDownloadMenu.addActionListener(buttonListener);
    cancelButton.addActionListener(buttonListener);
    cancelDownloadMenu.addActionListener(buttonListener);

    queuesButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JDMUI.showQueueList();
        }
    });

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

    public static void addDownload(Download download){
        downloads.add(download);
        FileUnits.saveAllDownloads(downloads);
        searchField.setText("Search files");
     //   DownloadPanel panel = new DownloadPanel(download);
     //   GridLayout layout = (GridLayout) panel5.getLayout();
     //   layout.setRows(layout.getRows()+1);
    //    panel5.add(panel.getPanel());
    }

    public static void addQueued(Download download){
        downloads.add(download);
        queuedDownloads.add(download);
        FileUnits.saveQueue(queuedDownloads);
        FileUnits.saveAllDownloads(downloads);
        searchField.setText("Search files");
    /**    DownloadPanel panel = new DownloadPanel(download);
        GridLayout layout = (GridLayout) panel5.getLayout();
        layout.setRows(layout.getRows()+1);
        panel5.add(panel.getPanel()); */
    }

    public static void addAlreadyDownloadingToQueue(Download download){
        queuedDownloads.add(download);
        FileUnits.saveQueue(queuedDownloads);
    }

    public static void showDownloadList(){
        panel5.removeAll();
        ((GridLayout) panel5.getLayout()).setRows(downloads.size());
        for(int i = downloads.size()-1 ; i>= 0 ; i--){
            DownloadPanel panel = new DownloadPanel(downloads.get(i));
            panel5.add(panel.getPanel());
            if(downloads.get(i).isSelected())
                panel.getPanel().setBorder(BorderFactory.createLineBorder(Color.RED,2));
        }
        frame.revalidate();
        frame.repaint();
    }

    public static void showQueueList(){
        panel5.removeAll();
        if(queuedDownloads.size()==0)
            panel6.setVisible(false);
        else if(queuedDownloads.size()>=1)
            panel6.setVisible(true);
            ((GridLayout) panel5.getLayout()).setRows(queuedDownloads.size() + 1);
            panel5.add(panel6);
            for (int i = queuedDownloads.size() - 1; i >= 0; i--) {
                DownloadPanel panel = new DownloadPanel(queuedDownloads.get(i));
                panel5.add(panel.getPanel());
                if(queuedDownloads.get(i).isSelected())
                    panel.getPanel().setBorder(BorderFactory.createLineBorder(Color.RED,2));
            }
            frame.revalidate();
            frame.repaint();
    }

    public static void searchDownloads(String search){
        searchResult.clear();
        for(Download download: downloads){
            if(download.searchRes(search)){
                searchResult.add(download);
            }
        }
    }

    public static void showSearchResult(){
        panel5.removeAll();
        ((GridLayout) panel5.getLayout()).setRows(searchResult.size());
        for(int i = searchResult.size()-1 ; i>= 0 ; i--){
            DownloadPanel panel = new DownloadPanel(searchResult.get(i));
            panel5.add(panel.getPanel());
            if(searchResult.get(i).isSelected())
                panel.getPanel().setBorder(BorderFactory.createLineBorder(Color.RED,2));
        }
        frame.revalidate();
        frame.repaint();
    }

    public static ArrayList<Download> getQueuedDownloads() {
        return queuedDownloads;
    }

    public static ArrayList<Download> getDownloads() {
        return downloads;
    }

    public static void setDownloads(ArrayList<Download> downloads) {
        JDMUI.downloads = downloads;
    }

    public static void setQueuedDownloads(ArrayList<Download> queuedDownloads) {
        JDMUI.queuedDownloads = queuedDownloads;
    }

    public static void updateDownloadsPanel(){
        showDownloadList();
    }
}
