import javax.swing.*;

public class Main {


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Any handling you want to do here, possibly logging
            // Optionally, you could just do... nothing.
        }
        JDMUI jdm = new JDMUI();
        jdm.setVisible(true);

    }

}
