import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Dialog.DEFAULT_MODALITY_TYPE;
import static javax.swing.SwingUtilities.updateComponentTreeUI;

public class JDMUI {
    private JFrame frame;

    public JDMUI() {
        frame = new JFrame("Java Download Manager V1.00");
        JPanel panel1 = new JPanel();

        SpringLayout layout1 = new SpringLayout();
        Icon newIcon = new ImageIcon(getClass().getResource("new.png"));
        Icon pauseIcon = new ImageIcon(getClass().getResource("pause.png"));
        Icon resumeIcon = new ImageIcon(getClass().getResource("resume.png"));
        Icon cancelIcon = new ImageIcon(getClass().getResource("cancel.png"));
        Icon removeIcon = new ImageIcon(getClass().getResource("remove.png"));
        Icon settingsIcon = new ImageIcon(getClass().getResource("settings.png"));
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
        frame.setMinimumSize(new Dimension(900,400));
        panel1.setLayout(layout1);
        downloadToolbar.add(Box.createHorizontalGlue());
        layout1.putConstraint(SpringLayout.EAST,downloadToolbar,0,SpringLayout.EAST,panel1);
        layout1.putConstraint(SpringLayout.WEST,downloadToolbar,0,SpringLayout.WEST,panel1);
        downloadToolbar.setBackground(Color.decode("#d0dff8"));
     //   panel1.add(downloadToolbar);
        frame.add(panel1);
        JMenuBar menuBar = new JMenuBar();
        JMenu downloadMenu = new JMenu("Download");
        JMenuItem newDownloadMenu = new JMenuItem("New Download");
        JMenuItem pauseDownloadMenu = new JMenuItem("Pause");
        JMenuItem resumeDownloadMenu = new JMenuItem("Resume");
        JMenuItem cancelDownloadMenu = new JMenuItem("Cancel");
        JMenuItem removeDownloadMenue = new JMenuItem("Remove");
        JMenuItem SettingsDownloadMenue = new JMenuItem("Settings");
        JMenuItem exitDownloadMenu = new JMenuItem("Exit");
        downloadMenu.add(newDownloadMenu);
        downloadMenu.addSeparator();
        downloadMenu.add(pauseDownloadMenu);
        downloadMenu.add(resumeDownloadMenu);
        downloadMenu.add(cancelDownloadMenu);
        downloadMenu.addSeparator();
        downloadMenu.add(removeDownloadMenue);
        downloadMenu.addSeparator();
        downloadMenu.add(SettingsDownloadMenue);
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
        Download testDownload = new Download("test.exe","250",10);
        String data[][]={ {"101","Amit","670000"},
                {"102","Jai","780000"},
                {"101","Sachin","700000"}};
        String column[]={"FileName","FileSize","Progress"};
        JTable downloadTable = new JTable(data,column);
        //panel1.add(downloadTable);
        JPanel panel2 = new JPanel(new GridLayout());
        JPanel panel3 = new JPanel(new FlowLayout());
        ImageIcon eagleIcon = new ImageIcon(getClass().getResource("logo.png"));
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
        JTabbedPane tabs = new JTabbedPane(SwingConstants.VERTICAL);
        panel3.add(tabs);
        Icon processingIcon = new ImageIcon(getClass().getResource("processing.png"));
        Icon completedIcon = new ImageIcon(getClass().getResource("completed.png"));
        Icon queuesIcon = new ImageIcon(getClass().getResource("queue.png"));
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
            tabButtons[i].setIconTextGap(30);
            tabButtons[i].setVerticalTextPosition(SwingConstants.CENTER);
            tabButtons[i].setHorizontalTextPosition(SwingConstants.RIGHT);
            tabButtons[i].setBackground(Color.GRAY);
            panel1.add(tabButtons[i]);
            tabButtons[i].setVisible(true);
        }



        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }


    public void setVisible(boolean a) {
        frame.pack();
        if (a) {
            frame.setVisible(true);
        } else
            frame.setVisible(false);
    }


}
