/**
 * 
 */
package com.customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.customers.security.AdminAuthProvider;
import com.customers.security.UserAuthProvider;

/**
 * @author upendra
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Order(1)
	@Configuration
	public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

		@Autowired
		private AdminAuthProvider adminAuthProvider;

		public AdminSecurityConfig() {
	        super();
	    }
		
		@Override
		protected void configure(AuthenticationManagerBuilder builder) throws Exception {
			builder.authenticationProvider(adminAuthProvider);
		}

		@Override
		protected void configure(HttpSecurity adminHttp) throws Exception {
			/*adminHttp.authorizeRequests().antMatchers("/admin_secure/**").permitAll().anyRequest().authenticated().and().formLogin()
					.loginPage("/admin/login").loginProcessingUrl("/admin/login").defaultSuccessUrl("/admin_secure/home")
					.failureUrl("/admin/login/failure").permitAll().and().logout().logoutUrl("/admin/logout").deleteCookies("JSESSIONID")
					.permitAll().and()
			          .csrf().disable();*/
			
			adminHttp.antMatcher("/admin*")
	          .authorizeRequests()
	          .anyRequest()
	          .hasRole("ADMIN")
	           
	          .and()
	          .formLogin()
	          .loginPage("/loginAdmin")
	          .loginProcessingUrl("/admin_login")
	          .failureUrl("/loginAdmin?error=loginError")
	          .defaultSuccessUrl("/adminPage")
	           
	          .and()
	          .logout()
	          .logoutUrl("/admin_logout")
	          .logoutSuccessUrl("/protectedLinks")
	          .deleteCookies("JSESSIONID")
	           
	          .and()
	          .exceptionHandling()
	          .accessDeniedPage("/403")
	           
	          .and()
	          .csrf().disable();
			
			
		}
		
		//public void configure(WebSecurity web) throws Exception {
			//web.ignoring().antMatchers("/h2", "/h2/**", "/httpcollector/**", "/js/**", "/*/js/**", "/css/**", "/*/css/**",
				//	"/fonts/**", "/*/fonts/**", "/img/**", "/*/img/**");
		//}
	}
	
	@Order(2)
	@Configuration
	public static class UserSecurityConfig extends WebSecurityConfigurerAdapter {

		@Autowired
		UserAuthProvider userAuthProvider;

		public UserSecurityConfig() {
	        super();
	    }
		
		@Override
		protected void configure(AuthenticationManagerBuilder builder) throws Exception {
			builder.authenticationProvider(userAuthProvider);
		}

		@Override
		protected void configure(HttpSecurity userHttp) throws Exception {
			/*userHttp.authorizeRequests().antMatchers("/user_secure/**").permitAll().anyRequest().authenticated().and()
					.formLogin().loginPage("/user/login").loginProcessingUrl("/user/login")
					.defaultSuccessUrl("/user_secure/home").failureUrl("/user/login/failure").permitAll().and().logout()
					.logoutUrl("/user/logout").deleteCookies("JSESSIONID").permitAll().and()
			          .csrf().disable();*/
			
			
			userHttp.antMatcher("/user*")
	          .authorizeRequests()
	          .anyRequest()
	          .hasRole("USER")
	           
	          .and()
	          .formLogin()
	          .loginPage("/loginUser")
	          .loginProcessingUrl("/user_login")
	          .failureUrl("/loginUser?error=loginError")
	          .defaultSuccessUrl("/userPage")
	           
	          .and()
	          .logout()
	          .logoutUrl("/user_logout")
	          .logoutSuccessUrl("/protectedLinks")
	          .deleteCookies("JSESSIONID")
	           
	          .and()
	          .exceptionHandling()
	          .accessDeniedPage("/403")
	           
	          .and()
	          .csrf().disable();
			
		}

		//public void configure(WebSecurity web) throws Exception {
			//web.ignoring().antMatchers("/h2", "/h2/**", "/httpcollector/**", "/js/**", "/*/js/**", "/css/**", "/*/css/**",
				//	"/fonts/**", "/*/fonts/**", "/img/**", "/*/img/**");
		//}

	}

}
