package pl.dmcs.pkomuda.housing.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import static pl.dmcs.pkomuda.housing.model.AccessLevelType.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new CharacterEncodingFilter("utf-8", true), CsrfFilter.class);
        http.authorizeRequests()
                .antMatchers("/account**").hasAuthority(ADMIN.label)
                .antMatchers("/accounts**").hasAuthority(ADMIN.label)
                .antMatchers("/addAccount**").hasAuthority(ADMIN.label)
                .antMatchers("/addBill**").hasAuthority(MANAGER.label)
                .antMatchers("/addBuilding**").hasAuthority(MANAGER.label)
                .antMatchers("/addFlat**").hasAuthority(MANAGER.label)
                .antMatchers("/api/buildings**").hasAuthority(MANAGER.label)
                .antMatchers("/assignFlat**").hasAuthority(ADMIN.label)
                .antMatchers("/bill**").hasAnyAuthority(MANAGER.label, RESIDENT.label)
                .antMatchers("/bills**").hasAuthority(MANAGER.label)
                .antMatchers("/building**").hasAuthority(MANAGER.label)
                .antMatchers("/buildings**").hasAuthority(MANAGER.label)
                .antMatchers("/changeAccessLevel**").authenticated()
                .antMatchers("/editAccount**").hasAuthority(ADMIN.label)
                .antMatchers("/flats**").hasAuthority(MANAGER.label)
                .antMatchers("/generatePdf**").hasAnyAuthority(MANAGER.label, RESIDENT.label)
                .antMatchers("/myAccount**").authenticated()
                .antMatchers("/myBills**").hasAuthority(RESIDENT.label)
                .and().formLogin().loginPage("/login").failureUrl("/loginError")
                .and().logout().logoutSuccessUrl("/")
                .and().exceptionHandling().accessDeniedPage("/noAccess");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
