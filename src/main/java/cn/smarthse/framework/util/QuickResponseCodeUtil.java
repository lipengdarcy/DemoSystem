package cn.smarthse.framework.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

/**
 * 二维码生成工具类
 */

public class QuickResponseCodeUtil {

	// 二维码颜色
	private final int BLACK = 0xFF000000;
	// 二维码颜色
	private final int WHITE = 0xFFFFFFFF;
	// 二维码默认长度
	private int length = 300;
	// 二维码默认宽度
	private int width = 300;
	// 二维码默认保存路径
	private String path = "";
	// 二维码默认保存格式
	private String type = "jpg";

	/**
	 * ZXing 方式生成二维码
	 * 
	 * @param url
	 *            二维码内容，即url路径
	 */
	public void createCode(String url) {
		this.createCode(url, length, width, path, type);
	}

	/**
	 * ZXing 方式生成二维码
	 * 
	 * @param url
	 *            二维码内容，即url路径
	 * @param path
	 *            二维码保存路径
	 */
	public void createCode(String url, String path) {
		this.createCode(url, length, width, path, type);
	}

	/**
	 * <span style="font-size:18px;font-weight:blod;">ZXing 方式生成二维码</span>
	 * 
	 * @param text
	 *            <a href="javascript:void();">二维码内容</a>
	 * @param width
	 *            二维码宽
	 * @param height
	 *            二维码高
	 * @param outPutPath
	 *            二维码生成保存路径
	 * @param imageType
	 *            二维码生成格式
	 */
	public void createCode(String text, int width, int height, String outPutPath, String imageType) {
		Map<EncodeHintType, String> his = new HashMap<EncodeHintType, String>();
		// 设置编码字符集
		his.put(EncodeHintType.CHARACTER_SET, "utf-8");
		try {
			// 1、生成二维码
			BitMatrix encode = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, his);

			// 2、获取二维码宽高
			int codeWidth = encode.getWidth();
			int codeHeight = encode.getHeight();

			// 3、将二维码放入缓冲流
			BufferedImage image = new BufferedImage(codeWidth, codeHeight, BufferedImage.TYPE_INT_RGB);
			for (int i = 0; i < codeWidth; i++) {
				for (int j = 0; j < codeHeight; j++) {
					// 4、循环将二维码内容定入图片
					image.setRGB(i, j, encode.get(i, j) ? BLACK : WHITE);
				}
			}
			File outPutImage = new File(outPutPath);
			// 如果图片不存在创建图片
			if (!outPutImage.exists())
				outPutImage.createNewFile();
			// 5、将二维码写入图片
			ImageIO.write(image, imageType, outPutImage);
		} catch (WriterException e) {
			e.printStackTrace();
			System.out.println("二维码生成失败");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("生成二维码图片失败");
		}
	}

	/**
	 * <span style="font-size:18px;font-weight:blod;">二维码解析</span>
	 * 
	 * @param filePath
	 *            二维码路径
	 * @return
	 * @throws IOException
	 */
	public String decode(String filePath) {
		String res = null;
		BufferedImage image;
		try {
			image = ImageIO.read(new File(filePath));
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
			Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
			// System.out.println("图片中内容：" + result.getText());
			// System.out.println("图片中格式：" + result.getBarcodeFormat());
			res = result.getText();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		return res;
	}

	public static void main(String[] args) throws Exception {
		QuickResponseCodeUtil tool = new QuickResponseCodeUtil();
		tool.createCode("http://localhost:8080/radioCertEdit/251?index=1", 300, 300, "E://zxingcode.jpg", "jpg");
		String res = tool.decode("E://zxingcode.jpg");
		System.out.println("二维码生成成功，图片中内容：" + res);
	}

}