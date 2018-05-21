import java.io.*;
import java.util.ArrayList;

public class FileUnits {

    public FileUnits() {
        loadAllDownloads();
        JDMUI.updateDownloadsPanel();
    }

    public static void saveAllDownloads(ArrayList<Download> downloads){
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("userdata\\list.jdm"));
            outputStream.writeObject(downloads);
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadAllDownloads(){
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("userdata\\list.jdm"));
            ArrayList<Download> downloads = (ArrayList<Download>) inputStream.readObject();
            JDMUI.setDownloads(downloads);
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }



}
