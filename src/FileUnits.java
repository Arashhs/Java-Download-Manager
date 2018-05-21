import java.io.*;
import java.util.ArrayList;

public class FileUnits {

    public FileUnits() {
        loadAllDownloads();
        JDMUI.updateDownloadsPanel();
    }

    public static void saveAllDownloads(ArrayList<Download> downloads) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("userdata\\list.jdm"));
            outputStream.writeObject(downloads);
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadAllDownloads() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("userdata\\list.jdm"));
            ArrayList<Download> downloads = (ArrayList<Download>) inputStream.readObject();
            JDMUI.setDownloads(downloads);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void backupRemovedDownload(Download d) {
        try {
            FileWriter writer = new FileWriter("userdata\\removed.jdm", true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.newLine();
            bufferedWriter.write("---+---+---+---+---+---+---+---+---+---");
            bufferedWriter.newLine();
            bufferedWriter.write("File Name: ");
            bufferedWriter.write(d.getFileName());
            bufferedWriter.newLine();
            bufferedWriter.write("URL: ");
            bufferedWriter.write(d.getURL());
            bufferedWriter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
