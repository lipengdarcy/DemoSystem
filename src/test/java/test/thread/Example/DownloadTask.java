package test.thread.Example;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by Mobin on 2017/8/5.
 */
public class DownloadTask implements Runnable {
	private final long lowerBound;
	private final long upperBound;
	private final DownloadBuffer xbuf;
	private final URL requestURL;
	private final AtomicBoolean cancelFlag;
	
	private static final Log log = LogFactory.getLog(DownloadTask.class);

	public DownloadTask(long lowerBound, long upperBound, URL requestURL, Storage storage, AtomicBoolean cancelFlag) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.requestURL = requestURL;
		this.xbuf = new DownloadBuffer(lowerBound, upperBound, storage);
		this.cancelFlag = cancelFlag;
	}

	@Override
	public void run() {
		if (cancelFlag.get()) {
			return;
		}
		ReadableByteChannel channel = null;
		try {
			channel = Channels.newChannel(issueRequest(requestURL, lowerBound, upperBound));
			ByteBuffer buf = ByteBuffer.allocate(1024);
			while (!cancelFlag.get() && channel.read(buf) > 0) {
				xbuf.write(buf); // 将数据写入缓冲区
				buf.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				channel.close();
				xbuf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private static InputStream issueRequest(URL requestURL, long lowerBound, long upperBound) throws IOException {
		Thread me = Thread.currentThread();
		log.info(me + "->[" + lowerBound + "," + upperBound + "]");
		final HttpURLConnection conn;
		InputStream in = null;
		conn = (HttpURLConnection) requestURL.openConnection();
		String strConnTimeout = System.getProperty("x.dt.conn.timeout");
		int connTimeout = null == strConnTimeout ? 6000 : Integer.valueOf(strConnTimeout);
		conn.setConnectTimeout(connTimeout);

		String strReadTimeout = System.getProperty("x.dt.read.timeout");
		int readTimeout = null == strReadTimeout ? 6000 : Integer.valueOf(strReadTimeout);
		conn.setReadTimeout(readTimeout);

		conn.setRequestMethod("GET");
		conn.setRequestProperty("Connection", "keep-alive");
		conn.setRequestProperty("Range", "bytes=" + lowerBound + "-" + upperBound);
		conn.setDoInput(true);
		conn.connect();

		int statusCode = conn.getResponseCode();
		if (HttpURLConnection.HTTP_PARTIAL != statusCode) {
			conn.disconnect();
			throw new IOException("Server exception, status code:" + statusCode);
		}
		log.info(me + "-Content-Range:" + conn.getHeaderField("Content-Range") + ",connection:"
				+ conn.getHeaderField("connection"));

		in = new BufferedInputStream(conn.getInputStream()) {
			@Override
			public void close() throws IOException {
				try {
					super.close();
				} finally {
					conn.disconnect();
				}
			}
		};
		return in;
	}
}
