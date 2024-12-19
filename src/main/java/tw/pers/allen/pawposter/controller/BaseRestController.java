package tw.pers.allen.pawposter.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public interface BaseRestController<T> {

	@GetMapping("/{id}")
	T getById(@PathVariable Integer id);

	@GetMapping("/")
	List<T> getAll();

	@GetMapping("/page")
	Page<T> getPaginatedByConditions(@RequestParam(defaultValue = "0") Integer page, // 當前頁面
			@RequestParam(defaultValue = "20") Integer size, // 每頁顯示筆數
			@RequestParam(defaultValue = "ASC") String direction, // 排序欄位
			@RequestParam(defaultValue = "id") String sort // 升冪 asc 或降冪 desc
	);

	@PostMapping("/")
	T create(@Valid @RequestBody T object);

	@PutMapping("/{id}")
	T update(@PathVariable Integer id, @Valid @RequestBody T object);

	@DeleteMapping("/{id}")
	T delete(@PathVariable Integer id);

}
