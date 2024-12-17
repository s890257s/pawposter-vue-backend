package tw.pers.allen.pawposter.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// CORS: 設定允許的 domain、method、header
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(List.of("/**"));
		corsConfiguration.setAllowedMethods(List.of("/**"));
		corsConfiguration.setAllowedHeaders(List.of("/**"));

		// 設定開放的 URL，無須登入
		List<String> allowedURL = List.of("/test/**");

		return http // 使用 HttpSecurity http 物件展開串聯設定
				.cors(cros -> cros.configurationSource(request -> corsConfiguration)) // 使用自訂的 corsConfiguration
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // jwt 無狀態
				.csrf(AbstractHttpConfigurer::disable) // 因無狀態，故不用考慮 CSRF(跨站請求偽造) 問題
				.authorizeHttpRequests(auth -> { // 設定權限主要位置

					// 迴圈 allowedURL，內所有值都開放
					for (String url : allowedURL) {
						auth.requestMatchers(url).permitAll();
					}
				}).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // 添加自訂過濾器
				.build();
	}

}
