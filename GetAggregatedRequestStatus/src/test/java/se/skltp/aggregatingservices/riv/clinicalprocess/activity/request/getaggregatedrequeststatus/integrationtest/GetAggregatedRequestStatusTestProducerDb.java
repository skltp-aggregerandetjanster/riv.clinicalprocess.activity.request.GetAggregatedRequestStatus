package se.skltp.aggregatingservices.riv.clinicalprocess.activity.request.getaggregatedrequeststatus.integrationtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.activity.request.getrequeststatusresponder.v1.GetRequestStatusResponseType;

import riv.clinicalprocess.activity.request.v1.CVType;
import riv.clinicalprocess.activity.request.v1.FullRecipientType;
import riv.clinicalprocess.activity.request.v1.HsaIdType;
import riv.clinicalprocess.activity.request.v1.PersonIdType;
import riv.clinicalprocess.activity.request.v1.RequestIdType;
import riv.clinicalprocess.activity.request.v1.RequestStatusIdType;
import riv.clinicalprocess.activity.request.v1.RequestStatusOrganisationType;
import riv.clinicalprocess.activity.request.v1.RequestStatusType;
import riv.clinicalprocess.activity.request.v1.SimpleAuthorType;
import riv.clinicalprocess.activity.request.v1.SimplePatientType;
import riv.clinicalprocess.activity.request.v1.TimeStampType;

import se.skltp.agp.test.producer.TestProducerDb;

public class GetAggregatedRequestStatusTestProducerDb extends TestProducerDb {

	private static final Logger log = LoggerFactory.getLogger(GetAggregatedRequestStatusTestProducerDb.class);

	@Override
	public Object createResponse(Object... responseItems) {
		log.debug("Creates a response with {} items", responseItems);
		GetRequestStatusResponseType response = new GetRequestStatusResponseType();
		for (int i = 0; i < responseItems.length; i++) {
			response.getRequestStatus().add((RequestStatusType)responseItems[i]);
		}
		return response;
	}
	
	@Override
	public Object createResponseItem(String logicalAddress, String registeredResidentId, String businessObjectId, String time) {
		
       log.debug("Created one response item for logical-address {}, registeredResidentId {} and businessObjectId {}",
                  new Object[] {logicalAddress, registeredResidentId, businessObjectId});

		RequestStatusType response = new RequestStatusType();

		response.setPatient(new SimplePatientType());
		response.getPatient().setPersonId(new PersonIdType());
		response.getPatient().getPersonId().setExtension(registeredResidentId);
		
		response.setRequestOrganisation(new RequestStatusOrganisationType());
		response.getRequestOrganisation().setCareUnitId(new HsaIdType());
		response.getRequestOrganisation().getCareUnitId().setExtension(logicalAddress);
		
		
		response.setRecipient(new FullRecipientType());
		response.setRequestAuthor(new SimpleAuthorType());
		response.setRequestId(new RequestIdType());
		response.setRequestMedium(new CVType());
		response.setRequestStatus(new CVType());
		response.setRequestStatusId(new RequestStatusIdType());
		response.setRequestVersionNumber("123");
		response.setStatusSetter(new SimpleAuthorType());
		response.setStatusTime(new TimeStampType());
		
		return response;
	}
}