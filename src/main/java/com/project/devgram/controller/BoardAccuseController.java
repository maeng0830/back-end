package com.project.devgram.controller;

import com.project.devgram.dto.RegisterBoardAccuse;
import com.project.devgram.dto.SearchBoardAccuse;
import com.project.devgram.dto.SearchBoardAccuse.Response;
import com.project.devgram.oauth2.token.TokenService;
import com.project.devgram.service.BoardAccuseService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards/accuse")
@RequiredArgsConstructor
public class BoardAccuseController {

	private final BoardAccuseService boardAccuseService;

	private final TokenService tokenService;

	@GetMapping
	public List<Response> searchBoardAccuse(SearchBoardAccuse.Request request) {
		return SearchBoardAccuse.Response.listOf(boardAccuseService.searchBoardAccuse(request));
	}


	@PostMapping
	public RegisterBoardAccuse.Response registerBoardAccuse(@RequestBody RegisterBoardAccuse.Request request, HttpServletRequest servletRequest) {
		String username = tokenService.getUsername(servletRequest.getHeader("Authentication"));

		return RegisterBoardAccuse.Response.of(
			boardAccuseService.registerBoardAccuse(username, request.getBoardSeq(), request.getContent()));
	}


}
