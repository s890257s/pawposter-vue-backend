package tw.pers.allen.pawposter.tools;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import tw.pers.allen.pawposter.model.dto.MemberDto;

public class LoggedInMemberTool {

	public static MemberDto getLoggedInMember() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!authentication.isAuthenticated()) {
			throw new RuntimeException("使用者未登入，無法取得會員物件");
		}

		return (MemberDto) authentication.getPrincipal();
	}

	public static Integer getLoggedInMemberId() {
		return getLoggedInMember().getMemberId();
	}
}
