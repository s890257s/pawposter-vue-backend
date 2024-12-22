package tw.pers.allen.pawposter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import tw.pers.allen.pawposter.model.dto.MemberDto;
import tw.pers.allen.pawposter.model.dto.PostDto;
import tw.pers.allen.pawposter.service.MemberService;
import tw.pers.allen.pawposter.service.PostService;

@RestController
public class TestController {

	@Autowired
	MemberService memberService;

	@Autowired
	PostService postService;

	@GetMapping("/test/{id}")
	public MemberDto getMember(@PathVariable Integer id) {
		return memberService.getById(id);
	}

	@GetMapping("/t/{id}")
	public MemberDto getMemberDto(@PathVariable Integer id) {
		return memberService.getById(id);
	}

	@GetMapping("/test/d/{id}")
	public PostDto test(@PathVariable Integer id) {
		return postService.getById(id);
	}

}
