package org.accountable.common.rest.impl;

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

import org.accountable.common.services.DbBase;

public abstract class CRUDResource<E extends DbBase> {
	
	@Context
	UriInfo uriInfo;
	
	protected Response create(E obj) {
		UriBuilder uriBuilder = UriBuilder.fromUri(uriInfo.getRequestUri());
		uriBuilder.path("{uniqueId}");
		return Response.created(uriBuilder.build(obj.getUniqueId())).entity(obj).build();
	}
	
//	@POST
//	@Path("/{uniqueId}")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response updateObject(@PathParam("uniqueId") String uniqueId, E obj) {
//		obj.setUniqueId(uniqueId);
//		E updated = update(obj);
//		return Response.ok(updated).entity(updated).build();
//	}
//	
//	protected abstract E update(E obj);
//	
//	@GET
//	@Produces({ MediaType.APPLICATION_JSON })
//	public List<E> getAllObjects() {
//		return getAll();
//	}
//	
//	protected abstract List<E> getAll();
}
