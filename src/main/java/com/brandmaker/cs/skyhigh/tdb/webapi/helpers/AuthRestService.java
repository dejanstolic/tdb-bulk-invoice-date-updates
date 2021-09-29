package com.brandmaker.cs.skyhigh.tdb.webapi.helpers;

import com.brandmaker.cs.skyhigh.tdb.dto.AccessTokenDto;
import com.brandmaker.cs.skyhigh.tdb.dto.UserDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;

@Path("/rest/sso")
public interface AuthRestService {
    String LOGIN = "login";
    String PASSWORD = "password";
    String BEARER_PREFIX = "Bearer ";
    String JSESSIONIDSSO = "JSESSIONIDSSO";

    @POST
    @Path("/auth")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    AccessTokenDto authUser(@FormParam(LOGIN) final String login, @FormParam(PASSWORD) final String password);

    @POST
    @Path("/auth")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    String authUserString(@FormParam(LOGIN) final String login, @FormParam(PASSWORD) final String password);

    @GET
    @Path("/auth/jaas/jwt")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    AccessTokenDto getTokenByJaasSession(@CookieParam(JSESSIONIDSSO) final NewCookie jSessionIdSso);

    @GET
    @Path("/users/current")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    UserDto getCurrentUser(@CookieParam(JSESSIONIDSSO) final NewCookie jSessionIdSso);


}
