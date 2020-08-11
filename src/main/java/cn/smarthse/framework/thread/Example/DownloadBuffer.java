package cn.smarthse.framework.thread.Example;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 下载缓冲区
 */
public class DownloadBuffer implements Closeable {
	// 当前Buffer中缓冲的数据相对于整个存储文件的公交车偏移
	private long globalOffset;
	private long upperBound;
	private int offset = 0;
	public final ByteBuffer byteBuf;
	private final Storage storage;

	public DownloadBuffer(long globalOffset, long upperBound, Storage storage) {
		this.globalOffset = globalOffset;
		this.upperBound = upperBound;
		this.byteBuf = ByteBuffer.allocateDirect(1024 * 1024);
		this.storage = storage;
	}

	public void write(ByteBuffer buf) throws IOException {
		int length = buf.position();
		final int capacity = byteBuf.capacity();
		// 当前缓冲区已满
		if ((offset + length) > capacity || length == capacity) {
			flush();
		}
		byteBuf.position(offset);
		buf.flip();
		byteBuf.put(buf);
		offset += length;
	}

	private void flush() throws IOException {
		int length;
		byteBuf.flip();
		length = storage.store(globalOffset, byteBuf);
		byteBuf.clear();
		globalOffset += length;
		offset = 0;
	}

	@Override
	public void close() throws IOException {
		if (globalOffset < upperBound) {
			flush();
		}
	}
}
