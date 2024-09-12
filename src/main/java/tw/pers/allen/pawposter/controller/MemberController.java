package tw.pers.allen.pawposter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.pers.allen.pawposter.model.entity.Member;
import tw.pers.allen.pawposter.service.MemberService;

@RestController
@RequestMapping("/api/member")
public class MemberController {

	@Autowired
	private MemberService memberService;

	@GetMapping("/{id}")
	public Member getMemberById(@PathVariable Integer id) {
		return memberService.findById(id);
	};

}
