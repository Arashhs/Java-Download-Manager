import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Dialog.DEFAULT_MODALITY_TYPE;
import static javax.swing.SwingUtilities.updateComponentTreeUI;

/**
 * Main frame for download manager
 * @author Arash
 * @version 1.0.0
 */
public class JDMUI {
    private static JFrame frame;
    private static JPanel panel5;
    private static ArrayList<Download> downloads;
    private static ArrayList<Download> queuedDownloads;
    private static JPanel panel6;
    private static ArrayList<Download> searchResult;
    private static JTextField searchField;
    private static ArrayList<Download> sortedDownloads;
    private static HashMap<String,DownloadPanel> downloadPanelMap;
    private static boolean isDateSorted;
    private static boolean isNameSorted;
    private static boolean isSizeSorted;
    private static boolean isDescending;
    private static int currentList; //0: DownloadsList | 1: CompletedList | 2: QueuesList

    /**
     * constructor
     * @throws AWTException
     */
    public JDMUI() throws AWTException {
        currentList = 0;
        isDateSorted = true;
        isNameSorted = false;
        isSizeSorted = false;
        isDescending = true;
        downloads = new ArrayList<Download>();
        queuedDownloads = new ArrayList<Download>();
        searchResult = new ArrayList<Download>();
        sortedDownloads = new ArrayList<Download>();
        downloadPanelMap = new HashMap<String,DownloadPanel>();
        frame = new JFrame("Java Download Manager V1.00");
        JPanel panel1 = new JPanel();
        SpringLayout layout1 = new SpringLayout();
        Icon newIcon = new ImageIcon(getClass().getResource("assets\\new.png"));
        Icon pauseIcon = new ImageIcon(getClass().getResource("assets\\pause.png"));
        Icon resumeIcon = new ImageIcon(getClass().getResource("assets\\resume.png"));
        Icon cancelIcon = new ImageIcon(getClass().getResource("assets\\cancel.png"));
        Icon removeIcon = new ImageIcon(getClass().getResource("assets\\remove.png"));
        Icon settingsIcon = new ImageIcon(getClass().getResource("assets\\settings.png"));
        Icon sortIcon = new ImageIcon(getClass().getResource("assets\\sort.png"));
        JButton newButton = new JButton("", newIcon);
        JButton pauseButton = new JButton("", pauseIcon);
        JButton resumeButton = new JButton("", resumeIcon);
        JButton cancelButton = new JButton("", cancelIcon);
        JButton removeButton = new JButton("", removeIcon);
        JButton settingsButton = new JButton("", settingsIcon);
        JButton sortButton = new JButton("",sortIcon);
        ArrayList<JButton> downloadButtons = new ArrayList<>();
        downloadButtons.add(newButton);
        downloadButtons.add(pauseButton);
        downloadButtons.add(resumeButton);
        downloadButtons.add(cancelButton);
        downloadButtons.add(removeButton);
        downloadButtons.add(settingsButton);
        downloadButtons.add(sortButton);
        newButton.setToolTipText("New download");
        pauseButton.setToolTipText("Pause");
        resumeButton.setToolTipText("Resume");
        cancelButton.setToolTipText("Cancel download task");
        removeButton.setToolTipText("Completely remove download task");
        settingsButton.setToolTipText("Settings");
        sortButton.setToolTipText("Sort");
        JToolBar downloadToolbar = new JToolBar();
        for (int i = 0; i < downloadButtons.size(); i++) {
            downloadButtons.get(i).setPreferredSize(new Dimension(56,30));
           downloadToolbar.add(downloadButtons.get(i));
            if(downloadButtons.get(i).equals(newButton) || downloadButtons.get(i).equals(cancelButton)||downloadButtons.get(i).equals(removeButton)||downloadButtons.get(i).equals(settingsButton))
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
        JPopupMenu sortPopupMenu = new JPopupMenu("Sort Type");
        JRadioButtonMenuItem dateSort = new JRadioButtonMenuItem("Start Date");
        JRadioButtonMenuItem nameSort = new JRadioButtonMenuItem("Filen Name");
        JRadioButtonMenuItem sizeSort = new JRadioButtonMenuItem("File Size");
        JRadioButtonMenuItem ascSort = new JRadioButtonMenuItem("Ascending");
        JRadioButtonMenuItem desSort = new JRadioButtonMenuItem("Descending");
        ButtonGroup group1 = new ButtonGroup();
        group1.add(ascSort);
        group1.add(desSort);
        sortPopupMenu.add(dateSort);
        sortPopupMenu.add(nameSort);
        sortPopupMenu.add(sizeSort);
        sortPopupMenu.add(new JPopupMenu.Separator());
        sortPopupMenu.add(ascSort);
        sortPopupMenu.add(desSort);

        dateSort.setSelected(true);
        desSort.setSelected(true);


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

            completedButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JDMUI.showCompletedList();
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
/**
 * Listener for buttons and menue items
 */
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
                            for(Download download: downloads){
                                if(download.isSelected()) {
                                    FileUnits.backupRemovedDownload(download);
                                    (new File(download.getFilePath())).delete();
                                }
                            }
                            while (it.hasNext()) {
                                Download dd;
                                dd = it.next();
                                if (dd.isSelected()) {
                                    queuedDownloads.remove(dd);
                                    it.remove();
                                }
                            }

                            initSortedDownloads();
                            showDownloadList();
                            FileUnits.saveAllDownloads(downloads);
                            FileUnits.saveQueue(queuedDownloads);
                    }
                    else {

                    }
                }
                else if(e.getSource().equals(pauseButton)||e.getSource().equals(pauseDownloadMenu)) {
                    for(Download download: downloads){
                        if(download.isSelected()) {
                            download.setDownloadStatus(0);
                            if(download.isCompleted())
                                download.setDownloadStatus(2);
                        }
                    }
                    showCurrentList();
                }
                else if(e.getSource().equals(resumeButton)||e.getSource().equals(resumeDownloadMenu)){
                    for(Download download: downloads){
                        if(download.isSelected()) {
                            download.setDownloadStatus(1);
                            Thread thread = new Thread(download);
                            thread.start();
                        }
                    }
                    showCurrentList();
                }
                else if(e.getSource().equals(cancelButton)||e.getSource().equals(cancelDownloadMenu)) {
                    int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel selected tasks?", "Cancel", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        Iterator<Download> it = downloads.iterator();
                        for(Download download: downloads){
                            if(download.isSelected()) {
                                FileUnits.backupRemovedDownload(download);
                            }
                        }
                        while (it.hasNext()) {
                            Download dd;
                            dd = it.next();
                            if (dd.isSelected()) {
                                queuedDownloads.remove(dd);
                                it.remove();
                            }
                        }

                        initSortedDownloads();
                        showDownloadList();
                        FileUnits.saveAllDownloads(downloads);
                        FileUnits.saveQueue(queuedDownloads);
                    }
                    else {

                    }
                }
                else if(e.getSource().equals(sortButton)){
                }
                else if(e.getSource().equals(dateSort)) {
                    isDateSorted = !isDateSorted;
                    showCurrentList();
                }
                else if(e.getSource().equals(nameSort)) {
                    isNameSorted = !isNameSorted;
                    showCurrentList();
                }
                else if(e.getSource().equals(sizeSort)) {
                    isSizeSorted = !isSizeSorted;
                    showCurrentList();
                }
                else if(e.getSource().equals(ascSort)) {
                    if(ascSort.isSelected())
                        isDescending = false;
                    else if(desSort.isSelected())
                        isDescending = true;
                    switch (currentList){
                        case 0:
                            showDownloadList();
                            break;
                        case 1:
                            showCompletedList();
                            break;
                        case 2:
                            showQueueList();
                            break;
                    }
                }
                else if(e.getSource().equals(desSort)){
                    if(ascSort.isSelected())
                        isDescending = false;
                    else if(desSort.isSelected())
                        isDescending = true;
                    showCurrentList();

                }
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

        down.addActionListener(new ActionListener() {
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

        up.addActionListener(new ActionListener() {
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
                Iterator<Download> it = downloads.iterator();
                while (it.hasNext()) {
                    Download dd;
                    dd = it.next();
                    if (dd.isSelected()) {
                        dd.setQueued(false);
                        queuedDownloads.remove(dd);
                    }
                }
                initSortedDownloads();
                showQueueList();
                FileUnits.saveAllDownloads(downloads);
                FileUnits.saveQueue(queuedDownloads);
            }
        });

        startQueue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread thread = new Thread(new QueueDownloader());
                thread.start();
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
    sortButton.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            sortPopupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    });
    dateSort.addActionListener(buttonListener);
    nameSort.addActionListener(buttonListener);
    sizeSort.addActionListener(buttonListener);
    ascSort.addActionListener(buttonListener);
    desSort.addActionListener(buttonListener);


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
        sortedDownloads.add(download);
         Thread thread = new Thread(download);
          thread.start();
         FileUnits.saveAllDownloads(downloads);
        searchField.setText("Search files");
     //   DownloadPanel panel = new DownloadPanel(download);
     //   GridLayout layout = (GridLayout) panel5.getLayout();
     //   layout.setRows(layout.getRows()+1);
    //    panel5.add(panel.getPanel());
    }

    public static void addQueued(Download download){
        download.setQueued(true);
        downloads.add(download);
        queuedDownloads.add(download);
        sortedDownloads.add(download);
        FileUnits.saveQueue(queuedDownloads);
        FileUnits.saveAllDownloads(downloads);
        download.setDownloadStatus(0);
        searchField.setText("Search files");
    /**    DownloadPanel panel = new DownloadPanel(download);
        GridLayout layout = (GridLayout) panel5.getLayout();
        layout.setRows(layout.getRows()+1);
        panel5.add(panel.getPanel()); */
    }

    public static void addAlreadyDownloadingToQueue(Download download){
        download.setQueued(true);
        queuedDownloads.add(download);
        FileUnits.saveQueue(queuedDownloads);
    }

  /*  public static void showDownloadList(){
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
    } */

    public static void showDownloadList(){
        currentList = 0;
        sort();
        panel5.removeAll();
        downloadPanelMap.clear();
        ((GridLayout) panel5.getLayout()).setRows(sortedDownloads.size());
        for(int i = sortedDownloads.size()-1 ; i>= 0 ; i--) {
            DownloadPanel panel = new DownloadPanel(sortedDownloads.get(i));
            downloadPanelMap.put(sortedDownloads.get(i).getUrl(), panel);
            if (!sortedDownloads.get(i).isCompleted()) {
                panel5.add(panel.getPanel());
                if (sortedDownloads.get(i).isSelected())
                    panel.getPanel().setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            }
            else
                ((GridLayout) panel5.getLayout()).setRows(sortedDownloads.size()-1);
        }
        frame.revalidate();
        frame.repaint();
    }

    public static void showQueueList(){
        currentList = 2;
        panel5.removeAll();
        if(queuedDownloads.size()==0)
            panel6.setVisible(false);
        else if(queuedDownloads.size()>=1)
            panel6.setVisible(true);
            ((GridLayout) panel5.getLayout()).setRows(queuedDownloads.size() + 1);
            panel5.add(panel6);

                for (int i = 0; i < queuedDownloads.size(); i++) {
                    DownloadPanel panel = downloadPanelMap.get(queuedDownloads.get(i).getUrl());
                    panel5.add(panel.getPanel());
                    if (queuedDownloads.get(i).isSelected())
                        panel.getPanel().setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
            frame.revalidate();
            frame.repaint();
    }

    public static void showCompletedList(){
        currentList = 1;
        sort();
        panel5.removeAll();
        ((GridLayout) panel5.getLayout()).setRows(1);
        for(int i = sortedDownloads.size()-1 ; i>= 0 ; i--) {
            if (sortedDownloads.get(i).isCompleted()) {
                DownloadPanel panel = new DownloadPanel(sortedDownloads.get(i));
                panel5.add(panel.getPanel());
                ((GridLayout) panel5.getLayout()).setRows(((GridLayout) panel5.getLayout()).getRows()+1);
                if (sortedDownloads.get(i).isSelected())
                    panel.getPanel().setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            }
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
            DownloadPanel panel = getDownloadPanelMap().get(searchResult.get(i).getUrl());
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

    /**
     * updates downloads' panel
     */
    public static void updateDownloadsPanel(){
        showDownloadList();
    }

    /**
     * initializes sorted downloads arraylist
     */
    public static void initSortedDownloads(){
        sortedDownloads.clear();
        for(Download d: downloads)
            sortedDownloads.add(d);
    }

    /**
     * Sorts downloads according to chosen factors
     */
    public static void sort(){
        Collections.sort(sortedDownloads, new Comparator<Download>() {
            @Override
            public int compare(Download o1, Download o2) {
                int compareName = o1.getFileName().compareToIgnoreCase(o2.getFileName());

                if(isDateSorted && !isNameSorted && !isSizeSorted){
                    if(isDescending){
                        if(o1.getStartDate().before(o2.getStartDate()))
                            return -1;
                        else if(o1.getStartDate().after(o2.getStartDate()))
                            return 1;
                        return 0;
                    }
                    else{
                        if(o1.getStartDate().before(o2.getStartDate()))
                            return 1;
                        else if(o1.getStartDate().after(o2.getStartDate()))
                            return -1;
                        else
                            return 0;
                    }
                }
                else if(!isDateSorted && isNameSorted && !isSizeSorted){
                    if(isDescending){
                        if( compareName < 0 )
                            return -1;
                        else if(compareName > 0)
                            return 1;
                        else
                            return 0;
                    }
                    else{
                        if( compareName < 0 )
                            return 1;
                        else if(compareName > 0)
                            return -1;
                        else
                            return 0;
                    }
                }
                else if(!isDateSorted && !isNameSorted && isSizeSorted){
                    if(isDescending){
                        if(o1.getDownloadedSize()<o2.getDownloadedSize())
                            return -1;
                        else if(o1.getDownloadedSize()>o2.getDownloadedSize())
                            return 1;
                        else
                            return 0;
                    }
                    else{
                        if(o1.getDownloadedSize()<o2.getDownloadedSize())
                            return 1;
                        else if(o1.getDownloadedSize()>o2.getDownloadedSize())
                            return -1;
                        else
                            return 0;
                    }

                }

                else if(!isDateSorted && isNameSorted && isSizeSorted){
                    if(isDescending){
                        if( compareName < 0 )
                            return -1;
                        else if(compareName > 0)
                            return 1;
                        else{
                            if(isDescending){
                                if(o1.getDownloadedSize()<o2.getDownloadedSize())
                                    return -1;
                                else if(o1.getDownloadedSize()>o2.getDownloadedSize())
                                    return 1;
                                else
                                    return 0;
                            }
                            else{
                                if(o1.getDownloadedSize()<o2.getDownloadedSize())
                                    return 1;
                                else if(o1.getDownloadedSize()>o2.getDownloadedSize())
                                    return -1;
                                else
                                    return 0;
                            }
                        }
                    }
                    else{
                        if( compareName < 0 )
                            return 1;
                        else if(compareName > 0)
                            return -1;
                        else{
                            if(isDescending){
                                if(o1.getDownloadedSize()<o2.getDownloadedSize())
                                    return -1;
                                else if(o1.getDownloadedSize()>o2.getDownloadedSize())
                                    return 1;
                                else
                                    return 0;
                            }
                            else{
                                if(o1.getDownloadedSize()<o2.getDownloadedSize())
                                    return 1;
                                else if(o1.getDownloadedSize()>o2.getDownloadedSize())
                                    return -1;
                                else
                                    return 0;
                            }
                        }
                    }

                }

                else if(isDateSorted && isNameSorted && !isSizeSorted){
                    if(isDescending){
                        if( compareName < 0 )
                            return -1;
                        else if(compareName > 0)
                            return 1;
                        else{
                            if(isDescending){
                                if(o1.getStartDate().before(o2.getStartDate()))
                                    return -1;
                                else if(o1.getStartDate().after(o2.getStartDate()))
                                    return 1;
                                return 0;
                            }
                            else{
                                if(o1.getStartDate().before(o2.getStartDate()))
                                    return 1;
                                else if(o1.getStartDate().after(o2.getStartDate()))
                                    return -1;
                                else
                                    return 0;
                            }
                        }
                    }
                    else{
                        if( compareName < 0 )
                            return 1;
                        else if(compareName > 0)
                            return -1;
                        else{

                            if(isDescending){
                                if(o1.getStartDate().before(o2.getStartDate()))
                                    return -1;
                                else if(o1.getStartDate().after(o2.getStartDate()))
                                    return 1;
                                return 0;
                            }
                            else{
                                if(o1.getStartDate().before(o2.getStartDate()))
                                    return 1;
                                else if(o1.getStartDate().after(o2.getStartDate()))
                                    return -1;
                                else
                                    return 0;
                            }

                        }
                    }
                }

                else if(isDateSorted && !isNameSorted && isSizeSorted){

                    if(isDescending){
                        if(o1.getDownloadedSize()<o2.getDownloadedSize())
                            return -1;
                        else if(o1.getDownloadedSize()>o2.getDownloadedSize())
                            return 1;
                        else{

                            if(isDescending){
                                if(o1.getStartDate().before(o2.getStartDate()))
                                    return -1;
                                else if(o1.getStartDate().after(o2.getStartDate()))
                                    return 1;
                                return 0;
                            }
                            else{
                                if(o1.getStartDate().before(o2.getStartDate()))
                                    return 1;
                                else if(o1.getStartDate().after(o2.getStartDate()))
                                    return -1;
                                else
                                    return 0;
                            }

                        }
                    }
                    else{
                        if(o1.getDownloadedSize()<o2.getDownloadedSize())
                            return 1;
                        else if(o1.getDownloadedSize()>o2.getDownloadedSize())
                            return -1;
                        else{

                            if(isDescending){
                                if(o1.getStartDate().before(o2.getStartDate()))
                                    return -1;
                                else if(o1.getStartDate().after(o2.getStartDate()))
                                    return 1;
                                return 0;
                            }
                            else{
                                if(o1.getStartDate().before(o2.getStartDate()))
                                    return 1;
                                else if(o1.getStartDate().after(o2.getStartDate()))
                                    return -1;
                                else
                                    return 0;
                            }

                        }
                    }

                }

                else if(isDateSorted && isNameSorted && isSizeSorted){


                    if(isDescending){
                        if( compareName < 0 )
                            return -1;
                        else if(compareName > 0)
                            return 1;
                        else{
                            if(isDescending){
                                if(o1.getDownloadedSize()<o2.getDownloadedSize())
                                    return -1;
                                else if(o1.getDownloadedSize()>o2.getDownloadedSize())
                                    return 1;
                                else
                                    return 0;
                            }
                            else{
                                if(o1.getDownloadedSize()<o2.getDownloadedSize())
                                    return 1;
                                else if(o1.getDownloadedSize()>o2.getDownloadedSize())
                                    return -1;
                                else
                                    return 0;
                            }
                        }
                    }
                    else{
                        if( compareName < 0 )
                            return 1;
                        else if(compareName > 0)
                            return -1;
                        else{
                            if(isDescending){
                                if(o1.getDownloadedSize()<o2.getDownloadedSize())
                                    return -1;
                                else if(o1.getDownloadedSize()>o2.getDownloadedSize())
                                    return 1;
                                else
                                    return 0;
                            }
                            else{
                                if(o1.getDownloadedSize()<o2.getDownloadedSize())
                                    return 1;
                                else if(o1.getDownloadedSize()>o2.getDownloadedSize())
                                    return -1;
                                else
                                    return 0;
                            }
                        }
                    }

                }

                    return 0;
            }
        });
    }

    public static void showCurrentList(){
        switch (currentList){
            case 0:
                showDownloadList();
                break;
            case 1:
                showCompletedList();
                break;
            case 2:
                showQueueList();
                break;
        }
    }

    public static HashMap<String, DownloadPanel> getDownloadPanelMap() {
        return downloadPanelMap;
    }
}
