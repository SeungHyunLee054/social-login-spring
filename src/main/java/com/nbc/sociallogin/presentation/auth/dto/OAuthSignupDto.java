package com.nbc.sociallogin.presentation.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuthSignupDto {
	private String authorizationCode;
	private String provider;

}
