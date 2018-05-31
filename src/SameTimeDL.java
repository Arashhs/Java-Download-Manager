import java.util.concurrent.Executor;

/**
 * Automatically checks sametime download limit restriction every one second
 * must be started in another thread
 * @author Arash
 * @version 1.0.0
 */
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

    /**
     * Calculates current number of downloading files
     */
    public void numberOfCurrentDownloading(){
        currentDownloading = 0;
        for(Download download: JDMUI.getDownloads()){
            if(download.getDownloadStatus() == 1)
                currentDownloading++;
        }
    }

    /**
     *
     * @return Same time download limit is regarded or not
     */
    public boolean isLimited(){
        numberOfCurrentDownloading();
        if(currentDownloading <= SettingsFrame.getMaxDL() || currentDownloading == 0)
            return true;
        else
            return false;
    }

    /**
     * Can start one of waiting tasks or not
     * @return true or false
     */
    public boolean canBeDownloaded(){
        numberOfCurrentDownloading();
        if(currentDownloading < SettingsFrame.getMaxDL() || currentDownloading == 0)
            return true;
        else
            return false;
    }
}
