package test.thread.Example;

/**
 * Created by Mobin on 2017/8/6.
 */
public class BigFileDownloaderMain {
    public static void main(String[] args) throws Exception {
        String url = "http://mirror.bit.edu.cn/apache/hbase/1.4.0/hbase-1.4.0-bin.tar.gz";
        BigFileDownloader downloader = new BigFileDownloader(url);
        int workerThreadCount = 3;
        long reportInterval = 2;
        downloader.download(workerThreadCount, reportInterval);
    }
}
