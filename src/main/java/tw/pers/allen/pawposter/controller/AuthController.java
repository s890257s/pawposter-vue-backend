package tw.pers.allen.pawposter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tw.pers.allen.pawposter.model.dto.EmailAndPasswordDto;
import tw.pers.allen.pawposter.model.dto.LoggedInMemberDto;
import tw.pers.allen.pawposter.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<LoggedInMemberDto> login(@Valid @RequestBody EmailAndPasswordDto emailAndPasswordDto) {

		LoggedInMemberDto loggedInMemberDto = authService.login(emailAndPasswordDto);

		return ResponseEntity.ok().header("Authorization", "Bearer " + loggedInMemberDto.getJwtToken())
				.body(loggedInMemberDto);
	}

}
