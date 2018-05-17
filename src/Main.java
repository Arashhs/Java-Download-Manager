import javax.swing.*;

public class Main {


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {

        }
        JDMUI jdm = new JDMUI();
        jdm.setVisible(true);
        SettingsFrame sf = new SettingsFrame();
        sf.setVisible(true);

    }

}
