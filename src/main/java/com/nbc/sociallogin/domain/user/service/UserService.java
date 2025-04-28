package com.nbc.sociallogin.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nbc.sociallogin.domain.auth.vo.AuthUser;
import com.nbc.sociallogin.domain.user.dto.UserInfo;
import com.nbc.sociallogin.domain.user.entity.User;
import com.nbc.sociallogin.domain.user.enums.RegistrationType;
import com.nbc.sociallogin.domain.user.enums.UserRole;
import com.nbc.sociallogin.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	@Transactional
	public User findOrCreate(UserInfo userInfo) {
		return userRepository.findByEmail(userInfo.getEmail())
			.orElseGet(() -> createNewOAuthUser(userInfo));
	}

	public User getUser(AuthUser authUser) {
		return userRepository.findById(authUser.getId())
			.orElseThrow(() -> new RuntimeException("User not found"));
	}

	protected User createNewOAuthUser(UserInfo userInfo) {
		User user = User.builder()
			.email(userInfo.getEmail())
			.name(userInfo.getName())
			.birth(userInfo.getBirth())
			.phone(userInfo.getPhone())
			.registrationType(RegistrationType.from(userInfo.getOAuthProvider()))
			.userRole(UserRole.ROLE_USER)
			.build();

		return userRepository.save(user);
	}

}
