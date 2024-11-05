package com.abueladigital.backend.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.abueladigital.backend.service.CustomUserDetailsService;

/**
 * Configuración de seguridad para la aplicación.
 * Utiliza Spring Security para gestionar la autenticación y autorización de
 * usuarios,
 * protegiendo los endpoints mediante JWT y roles de usuario.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /**
     * Proporciona un administrador de autenticación para manejar el proceso de
     * autenticación.
     * 
     * @param authConfig la configuración de autenticación de Spring Security
     * @return AuthenticationManager el administrador de autenticación configurado
     * @throws Exception si se produce un error al obtener el AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Proporciona un codificador de contraseñas utilizando BCrypt.
     * Se usa para codificar las contraseñas de los usuarios antes de almacenarlas
     * en la base de datos.
     * 
     * @return PasswordEncoder el codificador de contraseñas configurado
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuración de la cadena de filtros de seguridad de Spring Security.
     * Define qué endpoints son accesibles públicamente, cuáles requieren
     * autenticación y aplica el filtro de JWT para validar el token en cada
     * solicitud.
     * 
     * <p>
     * <strong>Nota:</strong> Al agregar nuevas URLs públicas en la configuración de
     * seguridad, es necesario incluir estas rutas en el método
     * {@code shouldNotFilter} de {@code JwtRequestFilter} para evitar la validación
     * del token en dichas rutas.
     * </p>
     * 
     * @param http el objeto HttpSecurity para configurar la seguridad HTTP
     * @return SecurityFilterChain la cadena de filtros de seguridad configurada
     * @throws Exception si ocurre un error al configurar la cadena de filtros
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests((req) -> req
                        // acceso publico
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/recipes").permitAll()
                        // solo registrados
                        .requestMatchers(HttpMethod.GET, "/api/recipes/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/recipes").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/recipes/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/recipes/**").authenticated()
                        // otros
                        .anyRequest().authenticated())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Crea un servicio de detalles de usuario personalizado.
     * Este servicio se utiliza para cargar detalles del usuario durante la
     * autenticación.
     * 
     * @return UserDetailsService el servicio de detalles de usuario personalizado
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true); // Permitir cookies y credenciales
        config.addAllowedOrigin("http://localhost:8080"); // Reemplaza con el dominio del frontend
        config.addAllowedHeader("*"); // Permite todos los encabezados
        config.addAllowedMethod("*"); // Permite todos los métodos (GET, POST, etc.)

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:8080"); // Reemplaza con el dominio del frontend
        config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        return source;
    }

}