/*
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package test.dubbo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

public class AnnotationProviderBootstrap {

	public static void main(String[] args) throws Exception {
		new EmbeddedZooKeeper(2181, false).start();
		Thread.sleep(1000);

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				ProviderConfiguration.class);
		context.start();
		System.out.println("dubbo 服务启动成功.");
		System.in.read();
	}

	@Configuration
	@EnableDubbo(scanBasePackages = "test.dubbo.provider.service.impl")
	@PropertySource("classpath:/application-dev.yml")
	static public class ProviderConfiguration {
		@Bean
		public ProviderConfig providerConfig() {
			ProviderConfig providerConfig = new ProviderConfig();
			providerConfig.setTimeout(1000);
			return providerConfig;
		}
	}

}
