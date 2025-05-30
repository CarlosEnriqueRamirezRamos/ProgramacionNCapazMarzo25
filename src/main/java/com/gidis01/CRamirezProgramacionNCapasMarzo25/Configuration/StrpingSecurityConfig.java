package com.gidis01.CRamirezProgramacionNCapasMarzo25.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class StrpingSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("Carlos")
                .password("{noop}12345")
                .roles("Admin")
                .build();

        UserDetails programador = User.builder()
                .username("Enrique")
                .password("{noop}12345")
                .roles("Programador")
                .build();

        UserDetails analista = User.builder()
                .username("Luis")
                .password("{noop}12345")
                .roles("Analista")
                .build();

        return new InMemoryUserDetailsManager(admin, programador, analista);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/Usuario").hasAnyRole("Admin", "Programador", "Analista")
                .requestMatchers("/Usuario/Form/0").hasAnyRole("Programador", "Admin")
                .requestMatchers("/Usuario/formEditable").hasRole("Programador")
                .requestMatchers("/Usuario/CargaMasiva").hasAnyRole("Programador", "Admin")
                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                .defaultSuccessUrl("/Usuario", true)
                .permitAll()
                )
                .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                );
        return http.build();
    }
}
