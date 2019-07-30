package cn.smarthse.config.sharding;

import java.util.Collection;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

/**
 * 分库策略，用户id为奇数的落在master1，偶数的落在master0
 */
public final class DefaultDatabaseShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {

	@Override
	public String doSharding(final Collection<String> availableTargetNames,
			final PreciseShardingValue<Integer> shardingValue) {
		for (String each : availableTargetNames) {
			if (each.endsWith(shardingValue.getValue() % 2 + "")) {
				return each;
			}
		}
		throw new UnsupportedOperationException();
	}
}
