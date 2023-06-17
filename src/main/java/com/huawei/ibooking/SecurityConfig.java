package com.huawei.ibooking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.huawei.ibooking.service.CustomAuthenticationEntryPoint;
import com.huawei.ibooking.service.CustomAuthenticationSuccessHandler;
import com.huawei.ibooking.service.UserDetailsServiceImpl;

@SpringBootConfiguration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
            .antMatchers("/student/**").hasRole("USER")
            .antMatchers("/admin/**").hasRole("ADMIN")
        // .anyRequest().authenticated()
        .and()
        .formLogin().successHandler(authenticationSuccessHandler).and()
        .httpBasic()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint)
        .and()
        .csrf().disable(); // 禁用CSRF保护
        // http.authorizeRequests()
        //     // .antMatchers("/admin/**").hasRole("ADMIN") // 需要ADMIN角色才能访问/admin下的URL
        //     .antMatchers("/**").hasRole("STUDENT")
        //     .and()
        //     .formLogin()
        //     // .usernameParameter("stuNum")
        //     // .passwordParameter("password")
        //     // .loginPage("/login") // 指定登录页面的URL
        //     .defaultSuccessUrl("/") // 登录成功后的默认URL
        //     .and()
        //     .logout()
        //     .logoutSuccessUrl("/login") // 登出后的URL
        //     .and()
        //     .csrf().disable(); // 禁用CSRF保护

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
