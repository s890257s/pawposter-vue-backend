package tw.pers.allen.pawposter.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tw.pers.allen.pawposter.model.dto.MemberDto;
import tw.pers.allen.pawposter.service.MemberService;
import tw.pers.allen.pawposter.tools.JwtTool;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final HandlerExceptionResolver handlerExceptionResolver;
	private final MemberService memberService;

	public JwtAuthenticationFilter(HandlerExceptionResolver handlerExceptionResolver, MemberService memberService) {
		this.handlerExceptionResolver = handlerExceptionResolver;
		this.memberService = memberService;
	}

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

		// 檢驗 jwt 是否有效，若解析過程中出現錯誤，則會由 jwtTool（基於 jjwt 實現）拋出異常。
		// 捕獲異常後，轉發給 GlobalExceptionHandler，並在那定義回應狀態碼。
		try {
			JwtTool.isTokenValid(jwtToken);
		} catch (Exception e) {
			handlerExceptionResolver.resolveException(request, response, null, e);
		}

		// 讀取 member 資料
		Integer memberId = Integer.valueOf(JwtTool.getSubject(jwtToken));
		MemberDto memberDto = memberService.getById(memberId);

		// 在此次 context 中儲存驗證成功的 user

		/**
		 * UsernamePasswordAuthenticationToken 為 Spring Security 設計用於表示已認證身份的標準物件 </br>
		 * 參數一: 認證成功的使用者物件 </br>
		 * 參數二: 憑證、密碼等物件，但在 JWT 驗證中不須再額外提供 </br>
		 * 參數三: 權限列表物件
		 */
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				memberDto, null, null);
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

		// 繼續執行過濾鏈
		filterChain.doFilter(request, response);
	}

}
