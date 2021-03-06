package guldilin.config;

import guldilin.config.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JwtFilter jwtFilter;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth").permitAll()
                .antMatchers("/register").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/*").hasAnyAuthority("ADMIN", "MANAGER", "EDITOR", "MAIN_EDITOR", "FARM")
                .antMatchers("/api/workers").hasAnyAuthority("ADMIN", "MANAGER")
                .antMatchers(HttpMethod.POST,"/api/formula", "/api/medicaments/*/formula").hasAnyAuthority("ADMIN", "FARM")
                .antMatchers(HttpMethod.PATCH,"/api/formula", "/api/medicaments/*/formula").hasAnyAuthority("ADMIN", "FARM")
                .antMatchers(HttpMethod.POST,"/api/medicaments").hasAnyAuthority("ADMIN", "EDITOR", "MAIN_EDITOR")
                .antMatchers(HttpMethod.PATCH, "/api/medicaments").hasAnyAuthority("ADMIN", "EDITOR", "MAIN_EDITOR")
                .antMatchers(HttpMethod.POST,"/api/process").hasAnyAuthority("ADMIN", "EDITOR", "MAIN_EDITOR", "MANAGER")
                .antMatchers(HttpMethod.PATCH, "/api/process").hasAnyAuthority("ADMIN", "EDITOR", "MAIN_EDITOR", "MANAGER")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}