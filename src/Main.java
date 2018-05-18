import javax.swing.*;
import java.io.*;

public class Main {


    public static void main(String[] args) throws IOException {
        int laf = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("LAF.jdm"));
            laf = Integer.parseInt(br.readLine());

        }
        catch (Exception e){
            laf = 1;
        }
        System.out.println(laf);
        try {
            if(laf == 0) {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            }
            else if(laf == 1){
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            }
            else if(laf == 2){
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            }
        } catch (Exception e) {

        }
        JDMUI jdm;
        try{jdm = new JDMUI();
            jdm.setVisible(true);
        }
        catch (Exception e){
            System.err.print("Exception");
        }


    }

}
