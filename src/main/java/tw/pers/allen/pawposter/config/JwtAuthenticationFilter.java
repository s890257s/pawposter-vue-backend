package tw.pers.allen.pawposter.config;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 從 headers 中取得 Authorization header
		final String authHeader = request.getHeader("Authorization");

		// 若 http 請求的 headers 中不包含 Authorization，或包含但不合法，直接交由 Spring Security 處理
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		// 提取 jwt token
		final String jwtToken = authHeader.substring(7);

	}

}
