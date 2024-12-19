package tw.pers.allen.pawposter.service;

import java.util.Date;
import java.util.Objects;

import org.springframework.stereotype.Service;

import tw.pers.allen.pawposter.exception.runtime.AccountDisabledException;
import tw.pers.allen.pawposter.exception.runtime.IncorrectAccountOrPasswordException;
import tw.pers.allen.pawposter.model.dto.EmailAndPasswordDto;
import tw.pers.allen.pawposter.model.dto.LoggedInMemberDto;
import tw.pers.allen.pawposter.model.entity.Member;
import tw.pers.allen.pawposter.repository.MemberRepository;
import tw.pers.allen.pawposter.tools.BCryptEncryptionTool;
import tw.pers.allen.pawposter.tools.JwtTool;

@Service
public class AuthService {

	private final MemberRepository memberRepository;

	public AuthService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public LoggedInMemberDto login(EmailAndPasswordDto emailAndPasswordDto) {

		Member member = memberRepository.findByMemberMail(emailAndPasswordDto.getEmail())
				.orElseThrow(() -> new RuntimeException("登入失敗，使用者不存在。"));

		// 帳號不符合
		boolean incorrectAccount = !Objects.equals(emailAndPasswordDto.getEmail(), member.getMemberMail());

		// 密碼不符合
		boolean incorrectPassword = !BCryptEncryptionTool.verify(emailAndPasswordDto.getPassword(),
				member.getMemberPassword());

		if (incorrectAccount || incorrectPassword) {
			throw new IncorrectAccountOrPasswordException();
		}

		// 走到這表示登入成功，更新登入時間
		member.setLoggedInDate(new Date());
		memberRepository.save(member);
		memberRepository.flush();

		// 帳號被禁止使用
		if (!member.getIsEnabled()) {
			throw new AccountDisabledException();
		}

		// 建立登入成功物件
		LoggedInMemberDto loggedInMemberDto = new LoggedInMemberDto();
		loggedInMemberDto.setMemberMail(member.getMemberMail());
		loggedInMemberDto.setMemberName(member.getMemberName());
		loggedInMemberDto.setMemberPhoto(member.getMemberDetail().getMemberPhoto());
		loggedInMemberDto.setJwtToken(JwtTool.generateToken(member.getMemberId().toString()));

		return loggedInMemberDto;
	}
}
