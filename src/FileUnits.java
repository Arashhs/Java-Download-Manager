
import java.io.*;
import java.util.ArrayList;

public class FileUnits {
    private static ArrayList<java.lang.String> filteredURLs;

    public FileUnits() {
        filteredURLs = new ArrayList<>();
        loadAllDownloads();
        loadQueue();
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

    public static void saveQueue(ArrayList<Download> queue) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("userdata\\queue.jdm"));
            outputStream.writeObject(queue);
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

    public static void loadQueue() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("userdata\\queue.jdm"));
            ArrayList<Download> queue = (ArrayList<Download>) inputStream.readObject();
            JDMUI.setQueuedDownloads(queue);
            JDMUI.initSortedDownloads();
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

    public static void saveFilteredURLs(){
        try {
            FileWriter writer = new FileWriter("userdata\\filter.jdm");
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.write(SettingsFrame.getTextArea().getText());
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> loadFilteredURLs(){
        try {
            filteredURLs.clear();
            FileReader reader = new FileReader("userdata\\filter.jdm");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String url;
            while ((url = bufferedReader.readLine()) != null){
                filteredURLs.add(url);
            }
            bufferedReader.close();
            return filteredURLs;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){

        }
        return null;
    }

    public static void saveSettings(int maxDL, String Directory , int laf){
        try {
            FileWriter writer = new FileWriter("userdata\\settings.jdm");
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.println(maxDL);
            printWriter.println(Directory);
            printWriter.println(laf);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads settings
     * @return Look and Feel status
     */
    public static void loadSettings(){
        try {
            FileReader reader = new FileReader("userdata\\settings.jdm");
            BufferedReader bufferedReader = new BufferedReader(reader);
            SettingsFrame.setMaxDL(Integer.parseInt(bufferedReader.readLine()));
            SettingsFrame.setDownloadDirectory(bufferedReader.readLine());
            int laf = Integer.parseInt(bufferedReader.readLine());
            SettingsFrame.setLookAndFeel(laf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){

        }
    }

    public static int lookAndFeel(){
        try {
            FileReader reader = new FileReader("userdata\\settings.jdm");
            BufferedReader bufferedReader = new BufferedReader(reader);
            bufferedReader.readLine();
            bufferedReader.readLine();
            int laf = Integer.parseInt(bufferedReader.readLine());
            return laf;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){

        }
        return 0;
    }
 }
