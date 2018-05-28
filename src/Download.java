import com.sun.scenario.Settings;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;
import java.net.*;


/**
 * Simulates download's task
 * @author Arash
 * @version 1.0.0
 */
public class Download implements Serializable, Runnable {
    private String url;
    private String fileName;
    private long downloadedSize;
    private double progress;
    private long downloaded;
    private int downloadStatus; //0: paused | 1: downloading | 2: Completed
    private boolean queueStatus; //false: not Queued | true: Queued
    private boolean isSelected;
    private Date startDate;
    private static final int BUFFER_SIZE = 4096;


    public Download(String url){
        try {
            SettingsFrame settingsFrame = new SettingsFrame();
            settingsFrame.dispose();
            URL fileUrl = new URL(url);
            HttpURLConnection httpConn = (HttpURLConnection) fileUrl.openConnection();
            fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
            downloadedSize = httpConn.getContentLength();
            downloaded = (new File(SettingsFrame.getDownloadDirectory() + File.separator + fileName)).length();
            progress = getIntSizeLengthFile((new File(SettingsFrame.getDownloadDirectory() + File.separator + fileName)).length());
            downloadStatus = 1;
            queueStatus = false;
            this.url = url;
            isSelected = false;
            startDate = new Date();
            httpConn.disconnect();

        }
        catch (IOException e){
            this.fileName = url;
            downloadedSize = (new Random()).nextInt(10) + 2;
            downloaded = 2;
            progress = 0;
            downloadStatus = 0;
            queueStatus = false;
            this.url = url;
            isSelected = false;
            startDate = new Date();
        }

    }

    public boolean equals(Object o){
                Download other = (Download) o;
                if(this.url.equals(other.url) && this.fileName.equals(other.fileName))
                    return true;
                return false;
    }

    /**
     * Search for entered URL or name in download task
     * @param s searched string
     * @return true: matched result | false: no match result
     */
    public boolean searchRes(String s){
        if(this.url.contains(s) || this.fileName.contains(s))
            return true;
        return false;
    }

    public void downloadFile() throws IOException {
        SettingsFrame settingsFrame = new SettingsFrame();
        settingsFrame.dispose();
        URL fileUrl = new URL(url);
        HttpURLConnection httpConn = (HttpURLConnection) fileUrl.openConnection();
        String byteRange = downloaded + "-" + downloadedSize;
        System.out.println("Downloaded:"+downloaded+"  Size: "+downloadedSize);
        httpConn.setRequestProperty("Range", "bytes=" + byteRange);

        int responseCode = httpConn.getResponseCode();


        // Check HTTP response code first
        if (responseCode/100 == 2) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();

            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
                fileName = url.substring(url.lastIndexOf("/") + 1,
                        url.length());
            }

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
         //   inputStream.skip(downloaded);
            String saveFilePath = SettingsFrame.getDownloadDirectory() + File.separator + fileName;
            System.out.println(saveFilePath);

            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath,true);
          /*  RandomAccessFile raf = new RandomAccessFile(saveFilePath,"rw");
            raf.seek(downloaded); */

            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            System.out.println(downloaded);
            while ((downloadStatus==1) && ((bytesRead = inputStream.read(buffer)) != -1)) {
                outputStream.write(buffer, 0, bytesRead);
                downloaded += bytesRead;
                JDMUI.getDownloadPanelMap().get(this).updateProgressBar(this);
            }
            System.out.println(downloaded);
            outputStream.flush();
            outputStream.close();
            inputStream.close();

            System.out.println("File downloaded");
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }

    @Override
    public void run() {
        try {
            downloadFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getStringSizeLengthFile(long n) {

        DecimalFormat df = new DecimalFormat("0.00");

        float sizeKb = 1024.0f;
        float sizeMo = sizeKb * sizeKb;
        float sizeGo = sizeMo * sizeKb;
        float sizeTerra = sizeGo * sizeKb;


        if(n < sizeMo)
            return df.format(n / sizeKb)+ " KB";
        else if(n < sizeGo)
            return df.format(n / sizeMo) + " MB";
        else if(n < sizeTerra)
            return df.format(n / sizeGo) + " GB";

        return "";
    }

    public float getIntSizeLengthFile(long downloadSize) {

        DecimalFormat df = new DecimalFormat("0.00");

        float sizeKb = 1024.0f;
        float sizeMo = sizeKb * sizeKb;
        float sizeGo = sizeMo * sizeKb;
        float sizeTerra = sizeGo * sizeKb;


        if(downloadedSize < sizeMo)
            return Float.parseFloat(df.format(downloadedSize / sizeKb));
        else if(downloadedSize < sizeGo)
            return Float.parseFloat(df.format(downloadedSize / sizeMo));
        else if(downloadedSize < sizeTerra)
            return Float.parseFloat(df.format(downloadedSize / sizeGo));

        return 0;
    }

    public String getFilePath(){
        SettingsFrame settingsFrame = null;
        try {
            settingsFrame = new SettingsFrame();
        } catch (IOException e) {
            e.printStackTrace();
        }
        settingsFrame.dispose();
        return SettingsFrame.getDownloadDirectory() + File.separator + fileName;
    }

    public long getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(int downloaded) {
        this.downloaded = downloaded;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getDownloadedSize() {
        return downloadedSize;
    }

    public void setDownloadedSize(long downloadedSize) {
        this.downloadedSize = downloadedSize;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public int getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(int downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    public boolean isQueueStatus() {
        return queueStatus;
    }

    public void setQueueStatus(boolean queueStatus) {
        this.queueStatus = queueStatus;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
