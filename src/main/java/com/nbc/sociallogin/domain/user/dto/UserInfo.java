package com.nbc.sociallogin.domain.user.dto;

import java.time.LocalDate;

import com.nbc.sociallogin.infrastructure.oauth.dto.response.OAuthUserInfoResponse;
import com.nbc.sociallogin.infrastructure.oauth.type.OAuthProvider;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfo {
	private String email;
	private String name;
	private LocalDate birth;
	private String phone;
	private OAuthProvider oAuthProvider;

	public static UserInfo convertToUserInfo(OAuthUserInfoResponse oAuthUserInfoResponse) {
		return UserInfo.builder()
			.email(oAuthUserInfoResponse.getEmail())
			.name(oAuthUserInfoResponse.getName())
			.birth(oAuthUserInfoResponse.getBirth())
			.phone(oAuthUserInfoResponse.getPhone())
			.oAuthProvider(oAuthUserInfoResponse.getOAuthProvider())
			.build();
	}
}
