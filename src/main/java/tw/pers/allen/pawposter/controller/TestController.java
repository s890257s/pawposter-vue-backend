package tw.pers.allen.pawposter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import tw.pers.allen.pawposter.model.dto.MemberDto;
import tw.pers.allen.pawposter.service.MemberService;

@RestController
public class TestController {

	@Autowired
	MemberService memberService;

	@GetMapping("/test/{id}")
	public MemberDto getMember(@PathVariable Integer id) {
		return memberService.findById(id);
	}

	@GetMapping("/t/{id}")
	public MemberDto getMemberDto(@PathVariable Integer id) {
		return memberService.findById(id);
	}
}
