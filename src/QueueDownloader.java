import javax.swing.*;

/**
 * Runs a queue
 * Implements Runnable
 * @author Arash
 * @version 1.0.0
 */
public class QueueDownloader implements Runnable {

    public QueueDownloader() {
    }

    /**
     * Starts queue (must be started in another thread)
     */
    @Override
    public void run() {
        for(Download download: JDMUI.getQueuedDownloads()){
            download.setDownloadStatus(3);
            JDMUI.getDownloadPanelMap().get(download.getUrl()).updateDownloadState(download);
        }
       while (!JDMUI.getQueuedDownloads().isEmpty()){
            if(isFiltered(JDMUI.getQueuedDownloads().get(0))){
                JOptionPane optionPane = new JOptionPane();
                JOptionPane.showMessageDialog(optionPane,"One or more of tasks are filtered.","Attention",optionPane.ERROR_MESSAGE);
                for(Download download: JDMUI.getQueuedDownloads()){
                    download.setDownloadStatus(0);
                    JDMUI.getDownloadPanelMap().get(download.getUrl()).updateDownloadState(download);
                }
                break;
            }
            else if(JDMUI.getQueuedDownloads().get(0).getDownloadStatus() == 3){
                Thread thread = new Thread(JDMUI.getQueuedDownloads().get(0));
                thread.start();
            }
           try {
               Thread.sleep(1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }

    /**
     * Whether or not URL is filtered
     * @param d download task
     * @return true or false
     */
    public boolean isFiltered(Download d){
        for(String string: FileUnits.loadFilteredURLs()){
            if(d.getUrl().contains(string))
                return true;
        }
        return false;
    }
}
