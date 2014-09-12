package org.accountable.expanse.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.accountable.expanse.Expanse;
import org.accountable.expanse.ExpansesService;

@Path("expanses")
public class ExpanseResource {
	private volatile ExpansesService expanseService;
	
	@Context
	UriInfo uriInfo;
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createExpanse(Expanse expanse) {
		expanse.setUniqueId(null);
		Expanse created = expanseService.addExpanse(expanse);
		UriBuilder uriBuilder = UriBuilder.fromUri(uriInfo.getRequestUri());
		uriBuilder.path("{uniqueId}");
		return Response.created(uriBuilder.build(created.getUniqueId())).entity(created).build();
	}

	@POST
	@Path("/{uniqueId}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateExpanse(@PathParam("uniqueId") String uniqueId, Expanse expanse) {
		expanse.setUniqueId(uniqueId);
		Expanse updated = expanseService.updateExpanse(expanse);
		return Response.ok(updated).entity(updated).build();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Expanse> getExpanses() {
		return expanseService.getAllExpanses();
	}
}
