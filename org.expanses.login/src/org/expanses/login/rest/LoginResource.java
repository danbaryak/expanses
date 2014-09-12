package org.expanses.login.rest;

import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.amdatu.security.tokenprovider.InvalidTokenException;
import org.amdatu.security.tokenprovider.TokenProvider;
import org.amdatu.security.tokenprovider.TokenProviderException;
import org.expanses.login.api.GoogleUser;
import org.expanses.login.api.User;

@Path("googlelogin")
public class LoginResource {

	private volatile TokenProvider tokenProvider;

	@POST
	@Consumes("application/json")
	public Response googleLogin(GoogleUser user) throws TokenProviderException {
		SortedMap<String, String> userMap = new TreeMap<>();
		userMap.put(TokenProvider.USERNAME, user.getId());
		userMap.put("googletoken", user.getAccessToken());
		System.out.println("Google access token is " + user.getAccessToken());
		String token = tokenProvider.generateToken(userMap);
		System.out.println("Generated token " + token);
		return Response.ok().cookie(new NewCookie("amdatu_token", token))
				.build();
	}

	@GET
	@Produces("text/plain")
	public String getGoogleAuthToken(@Context HttpServletRequest request)
			throws TokenProviderException, InvalidTokenException {
		String token = tokenProvider.getTokenFromRequest(request);
		SortedMap<String, String> tokenProperties = tokenProvider
				.verifyToken(token);
		return tokenProperties.get("googletoken");
	}
		

}
