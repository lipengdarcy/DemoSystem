package oauth2;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.CompositeFilter;

/**
 * https://spring.io/guides/tutorials/spring-boot-oauth2/
 * 
 * spring security demo
 */
@SpringBootApplication
@EnableOAuth2Sso
@RestController
public class SocialApplication extends WebSecurityConfigurerAdapter {

	@Autowired
	private OAuth2ClientContext oauth2ClientContext;

	public static void main(String[] args) {
		SpringApplication.run(SocialApplication.class, args);
	}

	/**
	 * It’s not a great idea to return a whole Principal in a /user endpoint like
	 * that (it might contain information you would rather not reveal to a browser
	 * client). We only did it to get something working quickly. Later in the guide
	 * we will convert the endpoint to hide the information we don’t need the
	 * browser to have.
	 */
	@RequestMapping("/user")
	public Principal user(Principal principal) {
		return principal;
	}

	/**
	 * This app will now work fine and authenticate as before, but without giving
	 * the user a chance to click on the link we just provided. To make the link
	 * visible we also need to switch off the security on the home page by adding a
	 * WebSecurityConfigurer:
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
		http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**", "/webjars/**", "/error**").permitAll()
				.anyRequest().authenticated()
				.and().logout().logoutSuccessUrl("/").permitAll()
				.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}

	/**
	 * Handling the Redirects. The last change we need to make is to explicitly
	 * support the redirects from our app to Facebook. This is handled in Spring
	 * OAuth2 with a servlet Filter, and the filter is already available in the
	 * application context because we used @EnableOAuth2Client. All that is needed
	 * is to wire the filter up so that it gets called in the right order in our
	 * Spring Boot application. To do that we need a FilterRegistrationBean:
	 */
	@Bean
	public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(
			OAuth2ClientContextFilter filter) {
		FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<OAuth2ClientContextFilter>();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}

	private Filter ssoFilter() {

		CompositeFilter filter = new CompositeFilter();
		List<Filter> filters = new ArrayList<>();

		OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter(
				"/login/facebook");
		OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
		facebookFilter.setRestTemplate(facebookTemplate);
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(facebookResource().getUserInfoUri(),
				facebook().getClientId());
		tokenServices.setRestTemplate(facebookTemplate);
		facebookFilter.setTokenServices(tokenServices);
		filters.add(facebookFilter);

		OAuth2ClientAuthenticationProcessingFilter githubFilter = new OAuth2ClientAuthenticationProcessingFilter(
				"/login/github");
		OAuth2RestTemplate githubTemplate = new OAuth2RestTemplate(github(), oauth2ClientContext);
		githubFilter.setRestTemplate(githubTemplate);
		tokenServices = new UserInfoTokenServices(githubResource().getUserInfoUri(), github().getClientId());
		tokenServices.setRestTemplate(githubTemplate);
		githubFilter.setTokenServices(tokenServices);
		filters.add(githubFilter);

		filter.setFilters(filters);
		return filter;

	}

	@Bean
	@ConfigurationProperties("facebook.client")
	public AuthorizationCodeResourceDetails facebook() {
		return new AuthorizationCodeResourceDetails();
	}

	@Bean
	@ConfigurationProperties("facebook.resource")
	public ResourceServerProperties facebookResource() {
		return new ResourceServerProperties();
	}

	@Bean
	@ConfigurationProperties("github.client")
	public AuthorizationCodeResourceDetails github() {
		return new AuthorizationCodeResourceDetails();
	}

	@Bean
	@Primary
	@ConfigurationProperties("github.resource")
	public ResourceServerProperties githubResource() {
		return new ResourceServerProperties();
	}

}
