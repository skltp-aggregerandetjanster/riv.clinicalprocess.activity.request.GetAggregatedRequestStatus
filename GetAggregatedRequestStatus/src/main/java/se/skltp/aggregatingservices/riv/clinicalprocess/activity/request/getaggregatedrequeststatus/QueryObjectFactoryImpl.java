package se.skltp.aggregatingservices.riv.clinicalprocess.activity.request.getaggregatedrequeststatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;
import org.w3c.dom.Node;

import riv.clinicalprocess.activity.request.getrequeststatusresponder.v1.GetRequestStatusType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.QueryObjectFactory;

public class QueryObjectFactoryImpl implements QueryObjectFactory {

	private static final Logger log = LoggerFactory.getLogger(QueryObjectFactoryImpl.class);
	private static final JaxbUtil ju = new JaxbUtil(GetRequestStatusType.class);

	private String eiServiceDomain;
	public void setEiServiceDomain(String eiServiceDomain) {
		this.eiServiceDomain = eiServiceDomain;
	}

	@SuppressWarnings("unused")
	private String eiCategorization;
	public void setEiCategorization(String eiCategorization) {
		this.eiCategorization = eiCategorization;
	}

	/**
	 * Transformerar GetRequestStatus request till EI FindContent request.
	 */
	public QueryObject createQueryObject(Node node) {
		
		GetRequestStatusType request = (GetRequestStatusType)ju.unmarshal(node);
		
		log.debug("Transformed payload for pid: {}", request.getPersonId().getExtension());

		FindContentType fc = new FindContentType();		
		fc.setRegisteredResidentIdentification(request.getPersonId().getExtension());
		fc.setServiceDomain(eiServiceDomain);
		
		QueryObject qo = new QueryObject(fc, request);
		return qo;
	}
}
