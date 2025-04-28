package com.nbc.sociallogin.presentation.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nbc.sociallogin.presentation.common.annotation.Auth;
import com.nbc.sociallogin.domain.auth.vo.AuthUser;
import com.nbc.sociallogin.domain.user.entity.User;
import com.nbc.sociallogin.domain.user.service.UserService;
import com.nbc.sociallogin.presentation.user.dto.UserResponse;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping
	public ResponseEntity<?> getUserProfile(@Parameter(hidden = true) @Auth AuthUser authUser) {
		User user = userService.getUser(authUser);

		return ResponseEntity.ok(UserResponse.from(user));
	}
}
