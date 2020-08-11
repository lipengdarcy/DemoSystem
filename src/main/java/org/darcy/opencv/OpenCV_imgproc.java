package org.darcy.opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * imgproc 模块. 图像处理
 */
public class OpenCV_imgproc {

	private static int MAX_KERNEL_LENGTH = 31;

	private static Mat src;

	private static Mat dst;

	public static void main(String[] args) {
		// 加载静态库
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		String path = OpenCV_imgproc.class.getClassLoader().getResource("static/opencv/1.png").getFile();
		System.out.println("classpath路径： " + path);

		/// 载入原图像
		src = Imgcodecs.imread(path);
		display_caption("Original Image");

		dst = src.clone();
		display_dst();

		/// 使用 均值平滑
		display_caption("Homogeneous Blur");

		for (int i = 1; i < MAX_KERNEL_LENGTH; i = i + 2) {
			Imgproc.blur(src, dst, new Size(i, i), new Point(-1, -1));
			display_dst();
		}

		/// 使用高斯平滑
		display_caption("Gaussian Blur");

		for (int i = 1; i < MAX_KERNEL_LENGTH; i = i + 2) {
			Imgproc.GaussianBlur(src, dst, new Size(i, i), 0, 0);
			display_dst();
		}

		/// 使用中值平滑
		display_caption("Median Blur");

		for (int i = 1; i < MAX_KERNEL_LENGTH; i = i + 2) {
			Imgproc.medianBlur(src, dst, i);
			display_dst();
		}

		/// 使用双边平滑
		display_caption("Bilateral Blur");

		for (int i = 1; i < MAX_KERNEL_LENGTH; i = i + 2) {
			Imgproc.bilateralFilter(src, dst, i, i * 2, i / 2);
			display_dst();
		}

		/// 等待用户输入
		display_caption("End: Press a key!");

	}

	private static void display_caption(String caption) {
		dst = Mat.zeros(src.size(), src.type());
		Imgproc.putText(dst, caption, new Point((double) src.cols() / 4, (double) src.rows() / 2), 0, 1,
				new Scalar(255, 255, 255));
		HighGui.imshow("图像处理", dst);
	}

	/**
	 * 展现处理后图片
	 */
	private static void display_dst() {
		HighGui.imshow("图像处理", dst);
	}

}
