public class Download {
    private String fileName;
    private int downloadedSize;
    private double progress;
    private int downloaded;
    private int downloadStatus; //0: paused | 1: downloading | 2: Completed
    private int queueStatus; //0: not Queued | 1: Queued

    public Download(String fileName, int fileSize) {
        this.fileName = fileName;
        downloadedSize = 0;
        downloaded = 0;
        progress = 0;
        downloadStatus = 0;
        queueStatus = 0;
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

    public int getQueueStatus() {
        return queueStatus;
    }

    public void setQueueStatus(int queueStatus) {
        this.queueStatus = queueStatus;
    }
}
