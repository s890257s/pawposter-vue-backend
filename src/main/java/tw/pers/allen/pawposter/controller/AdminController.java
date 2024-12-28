package tw.pers.allen.pawposter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

	@GetMapping("/admin/function")
	public String adminFunction() {
		return "這功能只有管理員才能執行，你看的到表示你是管理員。(管理員目前 hardcode 在 JwtAuthenticationFilter 裡)";
	}

}
