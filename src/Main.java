import javax.swing.*;
import java.io.*;
import java.util.Date;

/**
 * Main class
 * @author Arash
 * @version 1.0.0
 */
public class Main {


    public static void main(String[] args) throws IOException {

        int laf = FileUnits.lookAndFeel();
        try {
            if (laf == 0) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } else if (laf == 1) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.motfi.MotfiLookAndFeel");
            } else if (laf == 2) {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            }
        }
        catch (ClassNotFoundException e){

        }
        catch (InstantiationException e){

        }
        catch (IllegalAccessException e){

        }
        catch (UnsupportedLookAndFeelException e){

        }
        JDMUI jdm;
        try {
            jdm = new JDMUI();
            jdm.setVisible(true);
        } catch (Exception e) {
            System.err.print("Exception");
        }
        FileUnits fileUnits = new FileUnits();
    }
}