package com.nbc.sociallogin.presentation.user.dto;

import com.nbc.sociallogin.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
	private Long userId;
	private String email;
	private String name;
	private String userType;

	public static UserResponse from(User user) {
		return UserResponse.builder()
			.userId(user.getId())
			.email(user.getEmail())
			.name(user.getName())
			.userType(user.getRegistrationType().name())
			.build();
	}
}
