package com.clone.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * Created by pontusellboj on 2017-01-25.
 */
@Configuration
@EnableAuthorizationServer
class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    public static final String RESOURCE_ID = "twitter-clone-client";  // TODO: Change name

    @Autowired
    private AuthenticationManager authenticationManager;

    private TokenStore tokenStore = new InMemoryTokenStore();

    /**
     * Configure authorization and token access for endpoints
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(this.tokenStore)
                .authenticationManager(authenticationManager);
    }

    /**
     * Configure security:
     *
     * withClients: allowed client applications
     * authorizationGrantTypes: accepted ways to authorize
     * authorities: users CRUD-rights in relation to database
     * resourceIds: allowed client applications ids
     * secret: secret required by client application
     * accessTokenValiditySeconds: time before access-token expires (currently 1h)
     * refreshTokenValiditySeconds: time before access-token expires (currently 24h)
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                .withClient(RESOURCE_ID)
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .authorities("ROLE_USER").scopes("write", "read", "trust")
                .authorities("ROLE_ADMIN").scopes("write", "read", "trust")
                .resourceIds(RESOURCE_ID)
                .secret("123456")
                .accessTokenValiditySeconds(60 * 60)
                .refreshTokenValiditySeconds(60 * 60 * 24);
    }

    /**
     * Returns DefaultTokenServices using InMemoryTokenService
     * @return DefaultTokenServices
     */
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(this.tokenStore);
        return tokenServices;
    }
}
