package com.nbc.sociallogin.domain.auth.vo;

import com.nbc.sociallogin.domain.user.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthUser {
	private final Long id;

	private final String email;

	private final UserRole userRole;
}
