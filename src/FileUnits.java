
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * File units class
 * Fulfills all save/load tasks
 * @author Arash
 * @version 1.0.0
 */
public class FileUnits {
    private static CopyOnWriteArrayList<String> filteredURLs;

    public FileUnits() {
        filteredURLs = new CopyOnWriteArrayList<String>();
        loadAllDownloads();
        loadQueue();
        initQueue();
        JDMUI.updateDownloadsPanel();
    }

    /**
     * Saves all downloads
     * @param downloads downloads array
     */
    public static void saveAllDownloads(ArrayList<Download> downloads) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("userdata\\list.jdm"));
            outputStream.writeObject(downloads);
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves queued list
     * @param queue queued downloads
     */
    public static void saveQueue(ArrayList<Download> queue) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("userdata\\queue.jdm"));
            outputStream.writeObject(queue);
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads downloads
     */
    public static void loadAllDownloads() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("userdata\\list.jdm"));
            ArrayList<Download> downloads = (ArrayList<Download>) inputStream.readObject();
            JDMUI.setDownloads(downloads);
            for(Download download: JDMUI.getDownloads()){
                download.setDownloadStatus(0);
          //      download.setCurrentDownloaded();
                if(download.isCompleted())
                    download.setDownloadStatus(2);
                download.setSelected(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads queued downloads
     */
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

    /**
     * Backups removed downloads so that if user delete a task and wants to find out its URL/name can refer to it
     * @param d Deleted download task
     */
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
            bufferedWriter.write(d.getUrl());
            bufferedWriter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves filtered URLs
     */
    public static void saveFilteredURLs(String filtered){
        try {
            FileWriter writer = new FileWriter("userdata\\filter.jdm");
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.write(filtered);
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads filtered URLs
     * @return filtered URL's array
     */
    public static CopyOnWriteArrayList<String> loadFilteredURLs(){
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

    /**
     * Saves settings
     * @param maxDL maximum number of downloading files at the same time
     * @param Directory Directory to save downloaded files
     * @param laf Look and Feel status
     */
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

    /**
     *
     * @return Look and Feel status
     */
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

    public static void initQueue(){
        for (int i = 0 ; i < JDMUI.getQueuedDownloads().size() ; i++){
            Download d = JDMUI.getQueuedDownloads().get(i);
            for(Download d2 : JDMUI.getDownloads()){
                if(d.equals(d2)){
                    JDMUI.getQueuedDownloads().remove(d);
                    JDMUI.getQueuedDownloads().add(i,d2);
                }
            }
        }
    }
 }
