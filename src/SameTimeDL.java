import java.util.concurrent.Executor;

public class SameTimeDL implements Runnable {

    private int currentDownloading;
    @Override
    public void run() {
        while (true) {

            if(SettingsFrame.getMaxDL() != 0) {
                if (canBeDownloaded()) {
                    for (int i = 0; i < JDMUI.getDownloads().size(); i++) {
                        if (JDMUI.getDownloads().get(i).getDownloadStatus() == 4) {
                            JDMUI.getDownloads().get(i).setDownloadStatus(1);
                            Thread thread = new Thread(JDMUI.getDownloads().get(i));
                            thread.start();
                        }
                    }
                    //JDMUI.showCurrentList();
                    JDMUI.revalidateFrame();
                } else if (!isLimited()) {
                    for (int i = 0; i < JDMUI.getDownloads().size() && !isLimited(); i++) {
                        if (JDMUI.getDownloads().get(i).getDownloadStatus() == 1)
                            JDMUI.getDownloads().get(i).setDownloadStatus(4);
                    }
                    JDMUI.showCurrentList();
                    JDMUI.revalidateFrame();
                }
            }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    public void numberOfCurrentDownloading(){
        currentDownloading = 0;
        for(Download download: JDMUI.getDownloads()){
            if(download.getDownloadStatus() == 1)
                currentDownloading++;
        }
    }

    public boolean isLimited(){
        numberOfCurrentDownloading();
        if(currentDownloading <= SettingsFrame.getMaxDL() || currentDownloading == 0)
            return true;
        else
            return false;
    }

    public boolean canBeDownloaded(){
        numberOfCurrentDownloading();
        if(currentDownloading < SettingsFrame.getMaxDL() || currentDownloading == 0)
            return true;
        else
            return false;
    }
}
