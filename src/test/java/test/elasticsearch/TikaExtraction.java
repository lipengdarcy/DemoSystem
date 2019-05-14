package test.elasticsearch;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

public class TikaExtraction {

	public static void main(final String[] args) {
		String text = getDoc();
		System.out.println("原始文本: " + text);
		System.out.println("Base64: " + getBase64String(text));
	}

	public static String getDoc() {
		// String filename = "C:/Users/darcy/Desktop/3.pdf";
		// String filename = "C:/Users/darcy/Desktop/2.xml";
		String filename = "C:/Users/darcy/Desktop/1.doc";
		Tika tika = new Tika();
		String filecontent = null;
		try {
			filecontent = tika.parseToString(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TikaException e) {
			e.printStackTrace();
		}
		return filecontent;
	}

	public static String getBase64String(String text) {
		final Base64.Decoder decoder = Base64.getDecoder();
		final Base64.Encoder encoder = Base64.getEncoder();
		byte[] textByte = null;
		try {
			textByte = text.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 编码
		final String encodedText = encoder.encodeToString(textByte);
		System.out.println(encodedText);
		// 解码
		try {
			System.out.println(new String(decoder.decode(encodedText), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodedText;

	}
}
