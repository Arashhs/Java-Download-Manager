public class Queue implements Runnable {

    public Queue() {
    }


    @Override
    public void run() {
        for(Download download: JDMUI.getQueuedDownloads()){
            download.setDownloadStatus(3);
            JDMUI.getDownloadPanelMap().get(download.getUrl()).updateDownloadState(download);
        }
       while (!JDMUI.getQueuedDownloads().isEmpty()){
            if(JDMUI.getQueuedDownloads().get(0).getDownloadStatus() == 3){
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
}
