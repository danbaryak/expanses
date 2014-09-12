package org.accountable.transaction.rest;

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

import org.accountable.transaction.Transaction;
import org.accountable.transaction.TransactionService;


/**
 * This class implements the REST Resource code for exposing facilities from the
 * enterprise domain via REST
 */
@Path("transactions")
public class TransactionResource {

	/** The URI Context */
	@Context
	UriInfo uriInfo;

	/** Injected facility service */
	private volatile TransactionService transactionService;
	
	/**
	 * This method implements the HTTP-GET method that can be used to retrieve
	 * the facility in the enterprise domain.
	 * 
	 * @param uniqueId
	 *            the identifier of the facility to retrieve
	 * @return the facility HTTP_NOT_FOUND (404) if the facility identified is
	 *         not found
	 */
//	@GET
//	@Path("/{uniqueId}")
//	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
//	public Facility getFacility(@PathParam("uniqueId") String uniqueId) {
//		Facility retrieved = facilityService.getFacilityById(uniqueId);
//		if (retrieved == null) {
//			throw new WebApplicationException(Response.Status.NOT_FOUND);
//		}
//		return retrieved;
//	}

	/**
	 * This method implements the HTTP-GET method that can be used to retrieve a
	 * list of facilities in the enterprise domain based upon query parameters
	 * used as filters. For example ?address=<value> filters on a specific
	 * address.
	 * 
	 * @return The retrieved list of facilities HTTP_BAD_REQUEST (401) if a
	 *         filter is specified that cannot be used, for example because of a
	 *         bad property name
	 */
//	@GET
//	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
//	public List<Facility> findFacilities() {
//		FacilitySearchCriteria query = null;
//
//		MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
//		if ((queryParams != null) && (queryParams.size() > 0)) {
//			query = new FacilitySearchCriteria();
//
//			for (String key : queryParams.keySet()) {
//				try {
//					Field privateField = FacilitySearchCriteria.class.getDeclaredField(key);
//					if (privateField != null) {
//						privateField.setAccessible(true);
//						privateField.set(query, queryParams.get(key).get(0));
//					} else {
//						throw new WebApplicationException(Response.Status.BAD_REQUEST);
//					}
//				} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
//					throw new WebApplicationException(Response.Status.BAD_REQUEST);
//				}
//			}
//			return facilityService.findFacilities(query);
//		}
//		return facilityService.getAllFacilities();
//	}

	/**
	 * This method implements the HTTP-GET method that can be used to retrieve a
	 * list of facilities in the enterprise domain based upon query parameters
	 * used as filters. For example ?address=<value> filters on a specific
	 * address.
	 * 
	 * @return The retrieved list of facilities HTTP_BAD_REQUEST (401) if a
	 *         filter is specified that cannot be used, for example because of a
	 *         bad property name
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Transaction> getTransactions() {
		return transactionService.getTransactions();
	}
	
	/**
	 * This method implements the HTTP-POST method that can be used to create a
	 * new facility in the enterprise domain.
	 * 
	 * @param facility
	 *            the facility to create
	 * @return HTTP_CREATED (201) with build URI if facility has been created
	 *         correctly.
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createTranaction(Transaction transaction) {

//		if (transaction == null) {
//			throw new WebApplicationException(Response.Status.BAD_REQUEST);
//		}

		/* Create a new facility based upon the facility parameter */
		transaction.setUniqueId(null);
		Transaction created = transactionService.addTransaction(transaction);

		/* Build the URI for the HTTP-CREATED (201) response */
		UriBuilder uriBuilder = UriBuilder.fromUri(uriInfo.getRequestUri());
		uriBuilder.path("{uniqueId}");

		/* Return HTTP_CREATED */
		// return
		return Response.created(uriBuilder.build(created.getUniqueId())).entity(created).build();
	}

	/**
	 * This method implements the HTTP-POST method that can be used to update an
	 * existing facility in the enterprise domain identified by the path param
	 * facility id.
	 * 
	 * @param uniqueId
	 *            the identifier of the facility to update
	 * @param facility
	 *            the facility to update
	 * @return Response HTTP_CREATED (200) with the updated facility if facility
	 *         has been updated correctly.
	 */
	@POST
	@Path("/{uniqueId}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateTransaction(@PathParam("uniqueId") String uniqueId, Transaction transaction) {
		/* Set the identifier and update the corresponding facility */
		transaction.setUniqueId(uniqueId);
		Transaction updated = transactionService.updateTransaction(transaction);

		/* Return HTTP_OK */
		return Response.ok(updated).entity(updated).build();
	}

//	/**
//	 * This method implements the HTTP-DELETE method that can be used to delete
//	 * an existing facility in the enterprise domain identified by the path
//	 * param unique Id.
//	 * 
//	 * @param uniqueId
//	 *            the identifier of the facility to delete
//	 * @return Response HTTP_OK (200) and the deleted facility if facility has
//	 *         been deleted correctly. Response HTTP_NOT_FOUND (404) if the
//	 *         facility to be deleted cannot be found.
//	 */
//	@DELETE
//	@Path("/{uniqueId}")
//	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
//	public Response deleteFacility(@PathParam("uniqueId") String uniqueId) {
//
//		/* Delete the facility */
//		Facility deleted = facilityService.deleteFacility(uniqueId);
//		if (deleted == null) {
//			/* The delete REST endpoint is idempotent */
//			return Response.ok().build();
//		}
//
//		/* Return HTTP_OK */
//		return Response.ok().entity(deleted).build();
//	}


	
	

}