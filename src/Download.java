public class Download {
    private String fileName;
    private String fileSize;
    private double progress;

    public Download(String fileName, String fileSize, double progress) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.progress = progress;
    }

    public String[] getDownloadInfo(){
        String[] info = {fileName,fileSize,String.valueOf(progress)};
        return info;
    }
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }
}
