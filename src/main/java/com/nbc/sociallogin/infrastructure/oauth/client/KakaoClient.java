package com.nbc.sociallogin.infrastructure.oauth.client;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.nbc.sociallogin.infrastructure.oauth.dto.request.OAuthLoginParam;
import com.nbc.sociallogin.infrastructure.oauth.dto.response.KakaoTokenResponse;
import com.nbc.sociallogin.infrastructure.oauth.dto.response.KakaoUserInfoResponse;
import com.nbc.sociallogin.infrastructure.oauth.dto.response.OAuthUserInfoResponse;
import com.nbc.sociallogin.infrastructure.oauth.type.OAuthProvider;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KakaoClient implements OAuthClient {
	private final RestTemplate restTemplate;

	@Value("${oauth.kakao.clientId}")
	private String clientId;

	@Override
	public OAuthProvider oAuthProvider() {
		return OAuthProvider.KAKAO;
	}

	@Override
	public String getAuthorizationCode() {
		return "https://kauth.kakao.com/oauth/authorize?"
			+ "response_type=code&client_id="
			+ clientId
			+ "&redirect_uri=http://localhost:8080/oauth/kakao/callback";
	}

	@Override
	public String getToken(OAuthLoginParam loginRequest) {
		String url = "https://kauth.kakao.com/oauth/token";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> body = loginRequest.makeBody();
		body.add("grant_type", "authorization_code");
		body.add("client_id", clientId);
		body.add("redirect_uri", "http://localhost:8080/oauth/kakao/callback");

		HttpEntity<?> request = new HttpEntity<>(body, headers);
		KakaoTokenResponse response = restTemplate.postForObject(url, request, KakaoTokenResponse.class);

		return Objects.requireNonNull(response).getAccessToken();
	}

	@Override
	public OAuthUserInfoResponse getUserInfo(String token) {
		String url = "https://kapi.kakao.com/v2/user/me";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", "Bearer " + token);

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.profile\"]");

		HttpEntity<?> request = new HttpEntity<>(body, headers);

		return restTemplate.postForObject(url, request,
			KakaoUserInfoResponse.class);
	}
}
