package org.accountable.balance.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.accountable.balance.Balance;
import org.accountable.balance.BalanceService;
import org.accountable.common.rest.impl.CRUDResource;

@Path("balance")
public class BalanceResource extends CRUDResource<Balance> {

	private volatile BalanceService balanceService;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createObject(Balance obj) {
		obj.setUniqueId(null);
		Balance created = balanceService.addBalance(obj);
		return super.create(obj);
	}

	@POST
	@Path("/{uniqueId}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateObject(@PathParam("uniqueId") String uniqueId,
			Balance balance) {
		balance.setUniqueId(uniqueId);
		Balance updated = balanceService.updateBalance(balance);
		return Response.ok(updated).entity(updated).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Balance> getAllObjects() {
		return balanceService.getBalance();
	}

	@DELETE
	@Path("/{uniqueId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteFacility(@PathParam("uniqueId") String uniqueId) {
		Balance deleted = balanceService.deleteBalance(uniqueId);
		if (deleted == null) {
			return Response.ok().build();
		}
		return Response.ok().entity(deleted).build();
	}
}
