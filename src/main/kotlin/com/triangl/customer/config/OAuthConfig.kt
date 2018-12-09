package com.triangl.customer.config

import com.auth0.spring.security.api.JwtWebSecurityConfigurer
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@Profile("production")
class OAuthConfig : WebSecurityConfigurerAdapter() {

    @Value(value = "\${auth0.apiAudience}")
    private val apiAudience: String? = null
    @Value(value = "\${auth0.issuer}")
    private val issuer: String? = null

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("GET", "POST", "PATCH", "OPTIONS")
        configuration.allowCredentials = true
        configuration.addAllowedHeader("Authorization")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        JwtWebSecurityConfigurer
            .forRS256(apiAudience, issuer)
            .configure(http)
            .authorizeRequests()
            .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
            .antMatchers(HttpMethod.GET, "/**").hasAuthority("read:customer")
            .antMatchers(HttpMethod.POST, "/**").hasAuthority("write:customer")
            .antMatchers(HttpMethod.PATCH, "/**").hasAuthority("write:customer")
            .antMatchers("/**").denyAll()
    }
}

@Configuration
@EnableWebSecurity
@Profile("test")
class OAuthConfigTest : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .anyRequest().permitAll()
            .and().csrf().disable()
    }
}
