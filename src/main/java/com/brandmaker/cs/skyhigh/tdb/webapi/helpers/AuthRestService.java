package com.brandmaker.cs.skyhigh.tdb.webapi.helpers;

import com.brandmaker.cs.skyhigh.tdb.dto.UserDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;

@Path("/rest/sso")
public interface AuthRestService {
    String JSESSIONIDSSO = "JSESSIONIDSSO";

    @GET
    @Path("/users/current")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    UserDto getCurrentUser(@CookieParam(JSESSIONIDSSO) final NewCookie jSessionIdSso);

}
