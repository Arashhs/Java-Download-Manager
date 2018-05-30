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
    private int downloadStatus; //0: paused | 1: downloading | 2: Completed | 3: Waiting
    private boolean isQueued; //false: not Queued | true: Queued
    private boolean isSelected;
    private boolean isCompleted;
    private Date startDate;
    private static final int BUFFER_SIZE = 4096;
    private double speedInKBps;


    public Download(String url){
        try {
            speedInKBps = 0;
            SettingsFrame settingsFrame = new SettingsFrame();
            settingsFrame.dispose();
            URL fileUrl = new URL(url);
            HttpURLConnection httpConn = (HttpURLConnection) fileUrl.openConnection();
            fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
            downloadedSize = httpConn.getContentLength();
            downloaded = (new File(SettingsFrame.getDownloadDirectory() + File.separator + fileName)).length();
            progress = getIntSizeLengthFile((new File(SettingsFrame.getDownloadDirectory() + File.separator + fileName)).length());
            downloadStatus = 1;
            isQueued = false;
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
            isQueued = false;
            this.url = url;
            isSelected = false;
            startDate = new Date();
        }

    }

    public boolean equals(Object o){
        Download d = (Download) o;
        if(this.getUrl().equals(d.getUrl()))
            return true;
        return false;
    }

    /**
     * Search for entered URL or name in download task
     * @param s searched string
     * @return true: matched result | false: no match result
     */
    public boolean searchRes(String s){
        if(this.url.toLowerCase().contains(s.toLowerCase()) || this.fileName.toLowerCase().contains(s.toLowerCase()))
            return true;
        return false;
    }

    public void downloadFile() throws IOException {
        downloadStatus = 1;
        SettingsFrame settingsFrame = new SettingsFrame();
        settingsFrame.dispose();
        URL fileUrl = new URL(url);
        HttpURLConnection httpConn = (HttpURLConnection) fileUrl.openConnection();
        String byteRange = downloaded + "-" + downloadedSize;
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

            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath,true);
          /*  RandomAccessFile raf = new RandomAccessFile(saveFilePath,"rw");
            raf.seek(downloaded); */

            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            speedInKBps = 0.0D;
            long startTime, endTime;
            startTime = System.nanoTime();
            int i = 1;
            while ((downloadStatus==1) && (((startTime = System.nanoTime()))!=0) && ((bytesRead = inputStream.read(buffer)) != -1)) {
                outputStream.write(buffer, 0, bytesRead);
                downloaded += bytesRead;
                JDMUI.getDownloadPanelMap().get(this.url).updateProgressBar(this,speedInKBps);
                endTime = System.nanoTime();
                if(i>=39) {
                    speedInKBps = (bytesRead / 1024) / (((double) (endTime - startTime)) / 1000000000);
                    i=1;
                }
                i++;
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

            System.out.println("File downloaded/paused");
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
 /*       SettingsFrame settingsFrame = null;
        try {
            settingsFrame = new SettingsFrame();
        } catch (IOException e) {
            e.printStackTrace();
        }
        settingsFrame.dispose(); */
        return SettingsFrame.getDownloadDirectory() + File.separator + fileName;
    }

    public long getDownloaded() {
        try {
            SettingsFrame settingsFrame = new SettingsFrame();
            settingsFrame.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloaded = (new File(SettingsFrame.getDownloadDirectory() + File.separator + fileName)).length();

    }

    public void setDownloaded(int downloaded) {
        this.downloaded = downloaded;
    }

    public void setCurrentDownloaded(){
        this.downloaded = (new File(SettingsFrame.getDownloadDirectory() + File.separator + fileName)).length();
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

    public boolean isQueued() {
        return isQueued;
    }

    public void setQueued(boolean queueStatus) {
        this.isQueued = queueStatus;
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

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public double getSpeedInKBps() {
        return speedInKBps;
    }

    public void setSpeedInKBps(double speedInKBps) {
        this.speedInKBps = speedInKBps;
    }
}
