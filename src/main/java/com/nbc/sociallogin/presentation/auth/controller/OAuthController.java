package com.nbc.sociallogin.presentation.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nbc.sociallogin.application.auth.usecase.OAuthSignInUseCase;
import com.nbc.sociallogin.infrastructure.oauth.type.OAuthProvider;
import com.nbc.sociallogin.presentation.auth.dto.OAuthSignupDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthController {
	private final OAuthSignInUseCase OAuthSignInUseCase;

	@GetMapping("/kakao/authorization")
	public ResponseEntity<?> getAuthorizationCode() {
		String url = OAuthSignInUseCase.getAuthorizationCode(OAuthProvider.KAKAO);

		return ResponseEntity.status(HttpStatus.FOUND)
			.header("Location", url)
			.build();
	}

	@GetMapping("/kakao/callback")
	public ResponseEntity<?> getCallbackUrl(@RequestParam("code") String code) {
		String redirectUrl = "/callback.html?code=" + code;

		return ResponseEntity.status(HttpStatus.FOUND)
			.header("Location", redirectUrl)
			.build();
	}

	@PostMapping("/signin")
	public ResponseEntity<?> oAuthSignIn(@RequestBody OAuthSignupDto oAuthSignupDto) {
		return ResponseEntity.ok(OAuthSignInUseCase.signIn(oAuthSignupDto));
	}
}
