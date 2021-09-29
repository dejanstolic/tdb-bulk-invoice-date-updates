package com.brandmaker.cs.skyhigh.tdb.webapi;

import com.brandmaker.cs.skyhigh.tdb.dto.*;
import com.brandmaker.cs.skyhigh.tdb.dto.InvoiceDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/maps/rest")
public interface MaplRestService {
    /**
     * Create invoice record in planner
     *
     * @param nodeId node id
     * @param billDto data to save
     * @param forseResize resize invoice object
     * @return BillDto of updated object
     */
    @POST
    @Path("/api/v1.0/invoice/node/{nodeId}")
    @Consumes(MediaType.APPLICATION_JSON)
    InvoiceDto createInvoiceRecord(@PathParam("nodeId") Integer nodeId, @QueryParam("forseResize") Boolean forseResize,
                                   InvoiceDto billDto);


}
