package cn.smarthse.config.sharding;

import java.util.Collection;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

/**
 * 分表策略，订单id为奇数的落在t_order_1，偶数的落在t_order_0
 */
public final class DefaultTableShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

	@Override
	public String doSharding(final Collection<String> availableTargetNames,
			final PreciseShardingValue<Long> shardingValue) {
		for (String each : availableTargetNames) {
			if (each.endsWith(shardingValue.getValue() % 2 + "")) {
				return each;
			}
		}
		throw new UnsupportedOperationException();
	}
}
