package com.brandmaker.cs.skyhigh.tdb.webapi.helpers;

import com.brandmaker.cs.skyhigh.tdb.dto.AccessTokenDto;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;


public final class SignLoginAuthorizer {
    private static final String BEARER_PREFIX = "Bearer ";

    private final String serverUrl;
    private final String login;
    private final String password;

    public SignLoginAuthorizer(final String login, final String serverUrl, final String password) {
        this.login = login;
        this.password = password;
        this.serverUrl = serverUrl;
    }

    public String getBearerToken() throws IllegalStateException {
        return BEARER_PREFIX + getAccessToken();
    }

    private String getAccessToken() throws IllegalStateException {
        final Client client = ClientBuilder.newClient();
        try {
            final ResteasyWebTarget target = (ResteasyWebTarget) client.target(serverUrl);
            AuthRestService ars = target.proxy(AuthRestService.class);
            final AccessTokenDto accessTokenDto = ars.authUser(login, password);
            return accessTokenDto.getAccessToken();
        }
        catch (Exception ex) {
            throw new IllegalStateException("Get access token problem", ex);
        }
        finally {
            client.close();
        }
    }
}
