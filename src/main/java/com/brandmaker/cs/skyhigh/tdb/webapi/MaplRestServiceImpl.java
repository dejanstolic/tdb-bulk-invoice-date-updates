package com.brandmaker.cs.skyhigh.tdb.webapi;

import com.brandmaker.cs.skyhigh.tdb.config.Globals;
import com.brandmaker.cs.skyhigh.tdb.dto.*;
import com.brandmaker.cs.skyhigh.tdb.webapi.helpers.AuthFilter;
import com.brandmaker.cs.skyhigh.tdb.webapi.helpers.JacksonCustomProvider;
import com.brandmaker.cs.skyhigh.tdb.webapi.helpers.SignLoginAuthorizer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import javax.annotation.PreDestroy;


/**
 * Implementation of MaplRestService interface {@link com.brandmaker.cs.skyhigh.tdb.webapi.MaplRestService}
 */
public class MaplRestServiceImpl implements com.brandmaker.cs.skyhigh.tdb.webapi.MaplRestService {

    private static final Log LOG = LogFactory.getLog(MaplRestServiceImpl.class);

    private final ResteasyClient client;
    private final com.brandmaker.cs.skyhigh.tdb.webapi.MaplRestService mapl;

    /**
     * Constructor initialization and web api authorization
     */
    public MaplRestServiceImpl() {

        final SignLoginAuthorizer authorizer = new SignLoginAuthorizer(Globals.WEB_API_USERNAME(), Globals.WEB_API_ROOT(), Globals.WEB_API_PASSWORD());
        client = new ResteasyClientBuilder().connectionPoolSize(50).build();
        client.register(new AuthFilter(authorizer::getBearerToken));
        client.register(JacksonCustomProvider.instance());
        mapl = client.target(Globals.WEB_API_ROOT()).proxy(com.brandmaker.cs.skyhigh.tdb.webapi.MaplRestService.class);
        LOG.info("MapsRestClient init!");
    }

    /**
     * Closing ResteasyClient on destroy
     */
    @PreDestroy
    public void destroy() {
        client.close();
        LOG.info("MapsRestClient destroy!");
    }

    /**
     * Create invoice record in planner
     *
     * @param nodeId      node id
     * @param invoiceDto     data to save
     * @param forseResize resize invoice object
     * @return BillDto of updated object
     */
    @Override
    public InvoiceDto createInvoiceRecord(Integer nodeId, Boolean forseResize, InvoiceDto invoiceDto) {
        try {
            return mapl.createInvoiceRecord(nodeId, forseResize, invoiceDto);
        } catch (Exception exception) {
            LOG.error(exception.getMessage());
            return null;
        }
    }



}
