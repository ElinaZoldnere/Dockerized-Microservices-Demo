package lv.javaguru.travel.insurance.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class SecurityConfig {

    private final Environment env;

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails internalUser = User.withUsername("internal_user_1")
                .password(passwordEncoder().encode("javaguru1"))
                .roles("INTERNAL_USER")
                .build();

        UserDetails externalUser = User.withUsername("external_user_2")
                .password(passwordEncoder().encode("javaguru2"))
                .roles("EXTERNAL_USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("javaguru3"))
                .roles("ADMIN")
                .build();

        UserDetails internalTestUser = User.withUsername("internal_test_user")
                .password(passwordEncoder().encode("javaguru4"))
                .roles("INTERNAL_USER")
                .build();

        UserDetails externalTestUser = User.withUsername("external_test_user")
                .password(passwordEncoder().encode("javaguru5"))
                .roles("EXTERNAL_USER")
                .build();

        return new InMemoryUserDetailsManager(
                internalUser,
                externalUser,
                admin,
                internalTestUser,
                externalTestUser);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/insurance/travel/web/**")
                                .permitAll()
                                .requestMatchers("/favicon.ico").permitAll()
                                .requestMatchers("/js/person.js").permitAll()

                                .requestMatchers("/h2-console/**")
                                .permitAll()

                                .requestMatchers("/insurance/travel/api/v**")
                                .hasAnyRole("EXTERNAL_USER", "INTERNAL_USER", "ADMIN")

                                .requestMatchers("/insurance/travel/api/internal/**")
                                .hasAnyRole("INTERNAL_USER", "ADMIN")

                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        if (isH2profileActive()) {
            // Allow embedding of the H2 console in an iframe (same-origin only)
            http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        }
        return http.build();
    }

    /**
     * <b>Note:</b> Method must not be private.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private boolean isH2profileActive() {
        return env.acceptsProfiles(Profiles.of("h2"));
    }

}
