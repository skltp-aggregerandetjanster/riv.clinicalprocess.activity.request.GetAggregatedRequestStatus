package se.skltp.aggregatingservices.riv.clinicalprocess.activity.request.getaggregatedrequeststatus.integrationtest;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.activity.request.getrequeststatus.v1.rivtabp21.GetRequestStatusResponderInterface;
import riv.clinicalprocess.activity.request.getrequeststatusresponder.v1.GetRequestStatusResponseType;
import riv.clinicalprocess.activity.request.getrequeststatusresponder.v1.GetRequestStatusType;
import se.skltp.agp.test.producer.TestProducerDb;

@WebService(serviceName     = "GetRequestStatusResponderService", 
            portName        = "GetRequestStatusResponderPort", 
            targetNamespace = "urn:riv:clinicalprocess:activity:request:GetRequestStatus:1:rivtabp21", 
            name            = "GetRequestStatusInteraction")
public class GetAggregatedRequestStatusTestProducer implements GetRequestStatusResponderInterface {

	private static final Logger log = LoggerFactory.getLogger(GetAggregatedRequestStatusTestProducer.class);

	private TestProducerDb testDb;
	public void setTestDb(TestProducerDb testDb) {
		this.testDb = testDb;
	}

	public GetRequestStatusResponseType getRequestStatus(String logicalAddress, GetRequestStatusType request) {
		GetRequestStatusResponseType response = null;

		log.info("### Virtual service for GetRequestStatus call the source system with logical address: {} and patientId: {}", logicalAddress, request.getPersonId().getExtension());

		response = (GetRequestStatusResponseType)testDb.processRequest(logicalAddress, request.getPersonId().getExtension());
        if (response == null) {
        	// Return an empty response object instead of null if nothing is found
        	response = new GetRequestStatusResponseType();
        }

		log.info("### Virtual service got {} booknings in the reply from the source system with logical address: {} and patientId: {}", new Object[] {response.getRequestStatus().size(), logicalAddress, request.getPersonId().getExtension()});


		// We are done
        return response;
	}
}