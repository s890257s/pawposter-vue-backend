package tw.pers.allen.pawposter.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import tw.pers.allen.pawposter.exception.runtime.AccountDisabledException;
import tw.pers.allen.pawposter.exception.runtime.IncorrectAccountOrPasswordException;
import tw.pers.allen.pawposter.model.dto.EmailAndPasswordDto;
import tw.pers.allen.pawposter.model.dto.LoggedInMemberDto;
import tw.pers.allen.pawposter.tools.JwtTool;

@SpringBootTest
@Transactional
public class AuthServiceTest {

	@Autowired
	AuthService authService;

	@Test
	void testLoginFailWithIncorrectAccount() {
		EmailAndPasswordDto dto = new EmailAndPasswordDto("AAA", "1234");

		assertThatThrownBy(() -> authService.login(dto))// 執行 login
				.isInstanceOf(IncorrectAccountOrPasswordException.class); // 預期拋出錯誤
	}

	@Test
	void testLoginFailWithIncorrectPassword() {
		EmailAndPasswordDto dto = new EmailAndPasswordDto("alice@mail.com", "0000");

		assertThatThrownBy(() -> authService.login(dto))// 執行 login
				.isInstanceOf(IncorrectAccountOrPasswordException.class); // 預期拋出錯誤
	}

	@Test
	void testLoginFailWithAccountDisabled() {
		EmailAndPasswordDto dto = new EmailAndPasswordDto("zoe@mail.com", "1234");

		assertThatThrownBy(() -> authService.login(dto))// 執行 login
				.isInstanceOf(AccountDisabledException.class); // 預期拋出錯誤
	}

	@Test
	void testLoginSuccess() {
		EmailAndPasswordDto dto = new EmailAndPasswordDto("alice@mail.com", "1234");
		LoggedInMemberDto loggedInMember = authService.login(dto);

		assertEquals("1", JwtTool.getSubject(loggedInMember.getJwtToken()));
	}
}
