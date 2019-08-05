package pl.com.luxsoft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pl.com.luxsoft.dto.ResultDTO;
import pl.com.luxsoft.service.SearchService;

@RestController
public class SearchController {

	@Autowired
	private SearchService searchService;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity search(@RequestParam(value = "term") String term) {
		List<ResultDTO> lista = null;
		try {
			lista = searchService.search(term);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.ok(lista);
	}
}