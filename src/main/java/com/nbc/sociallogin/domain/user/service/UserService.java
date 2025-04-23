package com.nbc.sociallogin.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nbc.sociallogin.domain.auth.vo.AuthUser;
import com.nbc.sociallogin.domain.user.entity.User;
import com.nbc.sociallogin.domain.user.enums.RegistrationType;
import com.nbc.sociallogin.domain.user.enums.UserRole;
import com.nbc.sociallogin.domain.user.repository.UserRepository;
import com.nbc.sociallogin.infrastructure.oauth.dto.response.OAuthUserInfoResponse;
import com.nbc.sociallogin.presentation.user.dto.UserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	@Transactional
	public User findOrCreate(OAuthUserInfoResponse userInfoResponse) {
		return userRepository.findByEmail(userInfoResponse.getEmail())
			.orElseGet(() -> createNewOAuthUser(userInfoResponse));
	}

	public UserResponse getUser(AuthUser authUser) {
		User user = userRepository.findById(authUser.getId())
			.orElseThrow(() -> new RuntimeException("User not found"));

		return UserResponse.from(user);
	}

	@Transactional
	protected User createNewOAuthUser(OAuthUserInfoResponse userInfoResponse) {
		User user = User.builder()
			.email(userInfoResponse.getEmail())
			.name(userInfoResponse.getName())
			.birth(userInfoResponse.getBirth())
			.phone(userInfoResponse.getPhone())
			.registrationType(RegistrationType.from(userInfoResponse.getOAuthProvider()))
			.userRole(UserRole.ROLE_USER)
			.build();

		return userRepository.save(user);
	}

}
