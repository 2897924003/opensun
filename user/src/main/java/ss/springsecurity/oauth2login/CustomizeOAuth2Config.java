package ss.springsecurity.oauth2login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
class CustomizeOAuth2Config {

    //设置授权授予方的详细信息
    @Bean
    public Map<String, ClientRegistration> clientRegistrations() {
        Map<String, ClientRegistration> clientRegistrations = new HashMap<>();

        ClientRegistration github = CommonOAuth2Provider.GITHUB.getBuilder("github")
                .clientId("311454860d4ffc261ebf")
                .clientSecret("6834fd93ea54c7fca6e9130fab5b345c72bb4241")
                .redirectUri("https://115.120.246.30/cas/login/oauth2/code/github")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .build();
        clientRegistrations.put("github", github);
        return clientRegistrations;
    }

    //内存管理授权授予方信息
    @Bean
    public ClientRegistrationRepository inMemoryClientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(clientRegistrations());
    }

    //内存管理授权客户端信息
    @Bean
    public OAuth2AuthorizedClientService inMemoryOAuth2AuthorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(inMemoryClientRegistrationRepository());
    }

    @Bean
    public OAuth2AuthorizedClientRepository authenticatedPrincipalOAuth2AuthorizedClientRepository() {
        return new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(inMemoryOAuth2AuthorizedClientService());
    }


}
