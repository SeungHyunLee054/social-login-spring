package com.nbc.sociallogin.presentation.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nbc.sociallogin.common.annotation.Auth;
import com.nbc.sociallogin.domain.auth.vo.AuthUser;
import com.nbc.sociallogin.domain.user.service.UserService;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping
	public ResponseEntity<?> getUserProfile(@Parameter(hidden = true) @Auth AuthUser authUser) {
		return ResponseEntity.ok(userService.getUser(authUser));
	}
}
