package test;

import org.junit.Assert;
import org.junit.Test;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * 有一个非常庞大的数据，假设全是 int 类型。
 * 
 * 给定一个数，判定它是否存在其中(尽量高效)。
 */

public class SearchInLargeArray {

	@Test
	public void guavaTest() {
		long star = System.currentTimeMillis();
		BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), 10000000, 0.01);

		for (int i = 0; i < 10000000; i++) {
			filter.put(i);
		}

		Assert.assertTrue(filter.mightContain(1));
		Assert.assertTrue(filter.mightContain(2));
		Assert.assertTrue(filter.mightContain(3));
		Assert.assertFalse(filter.mightContain(10000000));
		long end = System.currentTimeMillis();
		System.out.println("执行时间：" + (end - star) / 1000);
	}

}
