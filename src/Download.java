public class Download {
    private String fileName;
    private int fileSize;
    private int downloadedSize;
    private double progress;
    private int downloaded;

    public Download(String fileName, int fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        downloadedSize = 0;
        downloaded = 0;
        progress = 0;
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

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
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
}
