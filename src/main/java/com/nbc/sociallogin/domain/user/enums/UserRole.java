package com.nbc.sociallogin.domain.user.enums;

import java.util.Arrays;

public enum UserRole {
	ROLE_ADMIN, ROLE_USER;

	public static UserRole from(String role) {
		return Arrays.stream(UserRole.values())
			.filter(r -> r.name().equalsIgnoreCase(role))
			.findFirst()
			.orElseThrow(() -> new RuntimeException("유효하지 않은 UerRole"));
	}
}
