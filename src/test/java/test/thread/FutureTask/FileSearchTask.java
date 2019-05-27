package test.thread.FutureTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by Mobin on 2016/11/30. 查找文件中的关键字，每个文件一个线程
 */
public class FileSearchTask {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		String path = "D:\\Java\\Tomcat-9\\logs\\hd";
		String keyword = "alibaba";
		List<String> result = new ArrayList<String>();
		File[] files = new File(path).listFiles();
		ArrayList<Future<List<String>>> rs = new ArrayList<>();
		for (File file : files) { // 每个文件启动一个task去查找
			MatchCount count = new MatchCount();
			count.file = file;
			count.keyword = keyword;
			FutureTask<List<String>> task = new FutureTask(count);
			rs.add(task); // 将任务返回的结果添加到集合中
			Thread thread = new Thread(task);
			thread.start();
		}

		for (Future<List<String>> f : rs) {
			result.addAll(f.get());
		}
		for (String file : result) {
			System.out.println(file);
		}
		System.out.println("包含关键字的总文件数为：" + result.size());
	}
}

class MatchCount implements Callable<List<String>> {
	public File file;
	public String keyword;

	public List<String> call() throws Exception { // call封装线程所需做的任务
		List<String> list = new ArrayList<String>();
		String fileName = search(file);
		if (fileName != null)
			list.add(fileName);
		return list;
	}

	public String search(File file) {
		boolean founded = false;
		try (Scanner scanner = new Scanner(new FileInputStream(file))) {
			while (!founded && scanner.hasNextLine()) {
				if (scanner.nextLine().contains(keyword)) {
					founded = true;
					return file.getAbsolutePath();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
