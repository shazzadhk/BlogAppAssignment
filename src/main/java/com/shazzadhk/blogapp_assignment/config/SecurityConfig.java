package com.shazzadhk.blogapp_assignment.config;

import com.shazzadhk.blogapp_assignment.security.CustomUserDetails;
import com.shazzadhk.blogapp_assignment.security.JwtAuthEntryPoint;
import com.shazzadhk.blogapp_assignment.security.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//@Configuration
@EnableWebMvc
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private CustomUserDetails userDetails;

    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

//    public static final String[] PUBLIC_URLS = {"/api/auth/**","/h2-console/**"};
    public static final String[] PUBLIC_URLS = {"/api/auth/**","/images/**","/h2-console/**"};


    @Order(1)
    @Configuration
    public class ApiSecurityConfig{

        @Bean
        public SecurityFilterChain securityApiFilterChain(HttpSecurity http) throws Exception {

            http
                    .antMatcher("/api/**")
                    .cors()
                    .and()
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers(PUBLIC_URLS)
                    .permitAll()
                    .antMatchers(HttpMethod.GET)
                    .permitAll()
//                    .antMatchers("/api/**")
                    .anyRequest()
                    .authenticated()
                    .and().exceptionHandling()
                    .authenticationEntryPoint(jwtAuthEntryPoint)
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

            http.authenticationProvider(daoAuthenticationProvider());
            return http.build();


        }
    }

    @Order(2)
    @Configuration
    public class WebSecurityConfig{
        @Bean
        public SecurityFilterChain securityFormBasedFilterChain(HttpSecurity http) throws Exception {

            http
                    .antMatcher("/**")
                    .csrf()
                    .disable()
                    .authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers(PUBLIC_URLS).permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login?error")
                    .defaultSuccessUrl("/home")
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true);

            return http.build();

        }

    }



    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.userDetails);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;

    }


    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




}
