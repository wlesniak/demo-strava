package com.example.demo;



import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DemoController {

	private RestTemplate restTemplate;
	
	public DemoController(RestTemplate restTemplate) {
		this.restTemplate=restTemplate;
	}
	
	@GetMapping("/test")
	public String test(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer "+client.getAccessToken().getTokenValue());
		HttpEntity<String> request = new HttpEntity<String>(headers);
		String url = "https://www.strava.com/api/v3/athlete";
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		return response.getBody();
	}
	
}
