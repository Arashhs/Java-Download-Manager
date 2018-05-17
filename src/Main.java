import javax.swing.*;

public class Main {


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {

        }
        SettingsFrame settingsFrame = new SettingsFrame();
        settingsFrame.setVisible(false);
        JDMUI jdm = new JDMUI();
        jdm.setVisible(true);
        Download download = new Download("Java.exe",40);
        download.setURL("www.java.com/java.exe");
        download.setDownloadStatus(1);


    }

}
