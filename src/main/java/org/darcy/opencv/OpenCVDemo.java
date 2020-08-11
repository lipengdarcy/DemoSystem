package org.darcy.opencv;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class OpenCVDemo {

	public static void main(String[] args) {
		// 加载静态库
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		// matDemo();
		// showImage();
		editImage();
	}

	/**
	 * mat即matrix的简称，即矩阵
	 */
	private static void matDemo() {
		/**
		 * 对于二维多通道图像，首先要定义其尺寸，即行数和列数。
		 * 
		 * 然后，需要指定存储元素的数据类型以及每个矩阵点的通道数。
		 * 
		 * 比如 CV_8UC3 表示使用8位的 unsigned char 型，每个像素由三个元素组成三通道,预先定义的通道数可以多达四个。 Scalar
		 * 是个short型vector。指定这个能够使用指定的定制化值来初始化矩阵。
		 */
		// Mat-基本图像容器
		Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
		System.out.println("mat = " + mat.dump());

		Mat m = new Mat(2, 2, CvType.CV_8UC3, new Scalar(0, 0, 255));
		System.out.println("m = " + m.dump());

		Core.randu(m, 0, 255);
	}

	/**
	 * mat展现图片
	 */
	private static void showImage() {
		Mat mat = Imgcodecs.imread("C:\\Users\\darcy\\Desktop\\1.png");
		MyImageViewer imageViewer = new MyImageViewer(mat, "第一幅图片");
		imageViewer.imshow();
	}

	/**
	 * mat操作图片
	 */
	private static void editImage() {

		Mat img1 = Imgcodecs.imread("C:/Users/darcy/Desktop/1.png");
		Mat img2 = Imgcodecs.imread("C:/Users/darcy/Desktop/2.png");
		Mat img = new Mat(), erodeImg = new Mat(), dilateImg = new Mat();

		// 像素做差,做差把相同部分除去，剩下的明显的就是不同处。
		Core.absdiff(img1, img2, img);
		Imgcodecs.imwrite("C:/Users/darcy/Desktop/new_diff.png", img);

		/**
		 * 腐蚀是把暗度增强，达到去除噪声的目的，但弊端是比较细小的点会被当作噪点忽略掉，
		 * 
		 * 腐蚀的强度与kernel（腐蚀范围）、iterations（腐蚀次数）有关。
		 */
		Mat kernel = Imgproc.getStructuringElement(1, new Size(4, 6));
		// 腐蚀
		Imgproc.erode(img, erodeImg, kernel, new Point(-1, -1), 1);
		Imgcodecs.imwrite("C:/Users/darcy/Desktop/new_diff_fs.png", erodeImg);

		// 膨胀与腐蚀相反，先腐蚀后膨胀就可以达到将相同点去除，突出不同点的效果。
		Mat kernel1 = Imgproc.getStructuringElement(1, new Size(2, 3));
		// 膨胀
		Imgproc.dilate(erodeImg, dilateImg, kernel1);
		Imgcodecs.imwrite("C:/Users/darcy/Desktop/new_diff_pz.png", dilateImg);

		Mat threshImg = new Mat();
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

		Mat hierarchy = new Mat();
		// 检测边缘
		Imgproc.threshold(dilateImg, threshImg, 20, 255, Imgproc.THRESH_BINARY);
		// 转化成灰度
		Imgproc.cvtColor(threshImg, threshImg, Imgproc.COLOR_RGB2GRAY);
		// 找到轮廓(3：CV_RETR_TREE，2：CV_CHAIN_APPROX_SIMPLE)
		Imgproc.findContours(threshImg, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE,
				new Point(0, 0));

		List<Rect> boundRect = new ArrayList<Rect>(contours.size());
		for (int i = 0; i < contours.size(); i++) {
			// Mat conMat = (Mat)contours.get(i);
			// Imgproc.approxPolyDP((MatOfPoint2f)conMat,contours_poly.get(i),3,true);
			// 根据轮廓生成外包络矩形
			Rect rect = Imgproc.boundingRect(contours.get(i));
			boundRect.add(rect);
		}

		for (int i = 0; i < contours.size(); i++) {
			Scalar color = new Scalar(0, 0, 255);
			// 绘制轮廓
			Imgproc.drawContours(img1, contours, i, color, 1, 0, hierarchy, 0, new Point());
			// 绘制矩形
			Imgproc.rectangle(img1, boundRect.get(i).tl(), boundRect.get(i).br(), color, 2, 0);
		}

		Imgcodecs.imwrite("C:/Users/darcy/Desktop/new_diff_rect.png", img1);

	}
}
