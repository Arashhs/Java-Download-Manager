import java.io.Serializable;

public class Download implements Serializable {
    private String URL;
    private String fileName;
    private int downloadedSize;
    private double progress;
    private int downloaded;
    private int downloadStatus; //0: paused | 1: downloading | 2: Completed
    private boolean queueStatus; //false: not Queued | true: Queued
    private boolean isSelected;
    private Date startDate;

    public Download(String fileName, int fileSize) {
        this.fileName = fileName;
        downloadedSize = 0;
        downloaded = 0;
        progress = 0;
        downloadStatus = 0;
        queueStatus = false;
        URL = "";
        isSelected = false;
        startDate = new Date();
    }

    public Download(String URL){
        this.fileName = URL;
        downloadedSize = 10;
        downloaded = 2;
        progress = 0;
        downloadStatus = 0;
        queueStatus = false;
        this.URL = URL;
        isSelected = false;
        startDate = new Date();
    }

    public boolean equals(Object o){
                Download other = (Download) o;
                if(this.URL.equals(other.URL) && this.fileName.equals(other.fileName))
                    return true;
                return false;
    }

    public int getDownloaded() {
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

    public int getDownloadedSize() {
        return downloadedSize;
    }

    public void setDownloadedSize(int downloadedSize) {
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

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
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
