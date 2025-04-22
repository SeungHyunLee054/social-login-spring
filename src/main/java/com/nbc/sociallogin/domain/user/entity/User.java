package com.nbc.sociallogin.domain.user.entity;

import java.time.LocalDate;

import com.nbc.sociallogin.domain.user.enums.RegistrationType;
import com.nbc.sociallogin.domain.user.enums.UserRole;
import com.nbc.sociallogin.infrastructure.oauth.type.OAuthProvider;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String email;

	private String password;

	private String name;

	private LocalDate birth;

	private String phone;

	@Enumerated(EnumType.STRING)
	private RegistrationType registrationType;

	@Enumerated(EnumType.STRING)
	private UserRole userRole;
}
