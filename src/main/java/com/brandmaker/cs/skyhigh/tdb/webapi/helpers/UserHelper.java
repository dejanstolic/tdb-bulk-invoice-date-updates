package com.brandmaker.cs.skyhigh.tdb.webapi.helpers;

import com.brandmaker.cs.skyhigh.tdb.config.Globals;
import com.brandmaker.cs.skyhigh.tdb.dto.UserDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.servlet.http.Cookie;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.NewCookie;

public class UserHelper {

    private static final Log LOG = LogFactory.getLog(UserHelper.class);

    public String getUsername(Cookie cookie) throws IllegalStateException {
        final Client client = ClientBuilder.newClient();
        try {
            final ResteasyWebTarget target = (ResteasyWebTarget) client.target(Globals.WEB_API_ROOT());
            AuthRestService ars = target.proxy(AuthRestService.class);
            NewCookie jsessiodIdSso = new NewCookie(cookie.getName(), cookie.getValue());
            UserDto userData = ars.getCurrentUser(jsessiodIdSso);
            return userData.getLogin();
        }
        catch (Exception ex) {
            LOG.error("Cant get user data, error: "+ ex.getMessage());
            return null;
        }
        finally {
            client.close();
        }
    }
}
