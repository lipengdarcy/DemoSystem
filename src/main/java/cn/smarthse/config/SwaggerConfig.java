package cn.smarthse.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2 // Enable swagger 2.0 spec
@ComponentScan(basePackages = { "cn.smarthse.business.controller,cn.smarthse.app.controller" })
public class SwaggerConfig {

	@Bean
	public SecurityScheme apiKey() {
		return new ApiKey("access_token", "accessToken", "header");
	}

	@Bean
	public Docket springbootvueApi() {
		String auth = "Demo System-auth";

		// 可以添加多个header或参数
		ParameterBuilder aParameterBuilder = new ParameterBuilder();
		aParameterBuilder.name("Authorization").defaultValue(auth).description("i").modelRef(new ModelRef("string"))
				.parameterType("header").required(false).build();

		List<Parameter> aParameters = new ArrayList<Parameter>();
		aParameters.add(aParameterBuilder.build());

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo()).useDefaultResponseMessages(false)
				.globalOperationParameters(aParameters).select()
				.apis(RequestHandlerSelectors.basePackage("cn.smarthse.business.controller")).paths(PathSelectors.any())
				.build().ignoredParameterTypes(ApiIgnore.class).enableUrlTemplating(false)
				.securitySchemes(Arrays.asList(apiKey()));
	}

	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder().title("Demo系统 API").description("©2019 Copyright. Powered By Demo.")
				.contact(new Contact("DemoSystem", "", "demo@qq.com")).license("Apache License Version 2.0")
				.licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE").version("2.0").build();
	}

}