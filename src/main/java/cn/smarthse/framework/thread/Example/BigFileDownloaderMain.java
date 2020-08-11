package cn.smarthse.framework.thread.Example;

/**
 * 大文件下载，多线程处理Demo
 */
public class BigFileDownloaderMain {
	public static void main(String[] args) throws Exception {
		String url = "http://mirror.bit.edu.cn/apache/hbase/1.4.10/hbase-1.4.10-bin.tar.gz";
		BigFileDownloader downloader = new BigFileDownloader(url);
		int workerThreadCount = 3;
		long reportInterval = 2;
		downloader.download(workerThreadCount, reportInterval);
	}
}
