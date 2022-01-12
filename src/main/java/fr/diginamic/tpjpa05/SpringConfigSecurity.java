package fr.diginamic.tpjpa05;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import fr.diginamic.tpjpa05.providers.AppAuthProvider;
import fr.diginamic.tpjpa05.services.UserService;

@Configuration
@EnableWebSecurity
public class SpringConfigSecurity extends WebSecurityConfigurerAdapter {

    @Autowired  
    UserService userDetailsService;

    @Bean
    public AuthenticationProvider getProvider() {
        AppAuthProvider provider = new AppAuthProvider();
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authenticationProvider(getProvider())
        .formLogin().loginProcessingUrl("/login").and()
        .logout().logoutUrl("/logout").invalidateHttpSession(true).and()
        .authorizeRequests()
        .antMatchers("/login").permitAll()
        .antMatchers("logout").permitAll()
        .anyRequest().authenticated();
    }

}