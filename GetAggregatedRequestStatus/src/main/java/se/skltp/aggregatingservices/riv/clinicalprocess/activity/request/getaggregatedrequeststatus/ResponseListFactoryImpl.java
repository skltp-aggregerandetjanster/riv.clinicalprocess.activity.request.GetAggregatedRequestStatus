package se.skltp.aggregatingservices.riv.clinicalprocess.activity.request.getaggregatedrequeststatus;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;

import riv.clinicalprocess.activity.request.getrequeststatusresponder.v1.GetRequestStatusResponseType;
import riv.clinicalprocess.activity.request.getrequeststatusresponder.v1.ObjectFactory;
import se.skltp.agp.riv.interoperability.headers.v1.ProcessingStatusType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.ResponseListFactory;

public class ResponseListFactoryImpl implements ResponseListFactory {

	private static final Logger log = LoggerFactory.getLogger(ResponseListFactoryImpl.class);
	private static final JaxbUtil jaxbUtil = new JaxbUtil(GetRequestStatusResponseType.class, ProcessingStatusType.class);
	private static final ObjectFactory OF = new ObjectFactory();
	
	@Override
	public String getXmlFromAggregatedResponse(QueryObject queryObject, List<Object> aggregatedResponseList) {
		GetRequestStatusResponseType aggregatedResponse = new GetRequestStatusResponseType();

	    for (Object object : aggregatedResponseList) {
	    	GetRequestStatusResponseType response = (GetRequestStatusResponseType)object;
			aggregatedResponse.getRequestStatus().addAll(response.getRequestStatus());
		}

	    if (log.isInfoEnabled()) {
    		String subjectOfCareId = queryObject.getFindContent().getRegisteredResidentIdentification();
        	log.info("Returning {} aggregated remisstatus for subject of care id {}", aggregatedResponse.getRequestStatus().size() ,subjectOfCareId);
        }


        // Since the class GetRequestStatusResponseType doesn't have an @XmlRootElement annotation
        // we need to use the ObjectFactory to add it.
        return jaxbUtil.marshal(OF.createGetRequestStatusResponse(aggregatedResponse));
	}
}