package tw.pers.allen.pawposter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import tw.pers.allen.pawposter.exception.runtime.AccessDeniedException;
import tw.pers.allen.pawposter.exception.runtime.AccountDisabledException;
import tw.pers.allen.pawposter.exception.runtime.IncorrectAccountOrPasswordException;

@ControllerAdvice
public class GlobalExceptionHandler {
	/* === 其他內部錯誤，直接回應 === */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleInternalServerException(Exception exception) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
	}

	/* === 未登入或權限不足 === */
	@ExceptionHandler(InsufficientAuthenticationException.class)
	public ResponseEntity<String> handleInsufficientAuthenticationException(Exception exception) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未登入或無權存取此資源。");
	}

	/* === 權限不足(例如刪除別人的貼文) === */
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<String> AccessDeniedException(Exception exception) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("你無權執行此操作");
	}

	/* === 請求參數檢驗失敗(null 或空白) === */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException exception) {

		StringBuffer errorMessage = new StringBuffer();

		exception.getBindingResult().getAllErrors().forEach(error -> {
			if (error instanceof FieldError fieldError) {
				errorMessage.append(fieldError.getField());
				errorMessage.append(": ");
				errorMessage.append(fieldError.getDefaultMessage());
				errorMessage.append("、");
			} else {
				errorMessage.append(error.getDefaultMessage());
			}
		});

		// 若最後一個字是「、」則移除。
		if (errorMessage.lastIndexOf("、") == errorMessage.length() - 1) {
			errorMessage.deleteCharAt(errorMessage.length() - 1);
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
	}

	/* === 登入失敗 === */
	// 帳號或密碼錯誤 > 401
	@ExceptionHandler(IncorrectAccountOrPasswordException.class)
	public ResponseEntity<String> handleIncorrectAccountOrPasswordException(Exception exception) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("登入失敗，帳號或密碼錯誤。");
	}

	// 帳號被禁用 > 403
	@ExceptionHandler(AccountDisabledException.class)
	public ResponseEntity<String> handleIncorrectAccountDisabledException(Exception exception) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("登入失敗，此帳號已被禁止使用。");
	}

	/* === JWT === */
	// jwt 過期 > 401
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<String> handleJwtExpiredException(Exception exception) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("jwt token 已過期，請重新登入。");
	}

	// jwt 解析錯誤 > 401
	@ExceptionHandler(JwtException.class)
	public ResponseEntity<String> handleClaimJwtException(Exception exception) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("jwt token 不合法。");
	}
}
