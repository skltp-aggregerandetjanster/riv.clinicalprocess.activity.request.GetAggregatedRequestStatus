package se.skltp.aggregatingservices.riv.clinicalprocess.activity.request.getaggregatedrequeststatus.integrationtest;

import static se.skltp.agp.test.producer.TestProducerDb.TEST_RR_ID_ONE_HIT;

import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.activity.request.getrequeststatus.v1.rivtabp21.GetRequestStatusResponderInterface;
import riv.clinicalprocess.activity.request.getrequeststatusresponder.v1.GetRequestStatusResponseType;
import riv.clinicalprocess.activity.request.getrequeststatusresponder.v1.GetRequestStatusType;
import riv.clinicalprocess.activity.request.v1.PersonIdType;
import se.skltp.aggregatingservices.riv.clinicalprocess.activity.request.getaggregatedrequeststatus.GetAggregatedRequestStatusMuleServer;
import se.skltp.agp.test.consumer.AbstractTestConsumer;
import se.skltp.agp.test.consumer.SoapHeaderCxfInterceptor;
import se.skltp.agp.riv.interoperability.headers.v1.ProcessingStatusType;

public class GetAggregatedRequestStatusTestConsumer extends AbstractTestConsumer<GetRequestStatusResponderInterface> {

	private static final Logger log = LoggerFactory.getLogger(GetAggregatedRequestStatusTestConsumer.class);

	public static void main(String[] args) {
		String serviceAddress = GetAggregatedRequestStatusMuleServer.getAddress("SERVICE_INBOUND_URL");
		String personnummer = TEST_RR_ID_ONE_HIT;

		GetAggregatedRequestStatusTestConsumer consumer = new GetAggregatedRequestStatusTestConsumer(serviceAddress, SAMPLE_SENDER_ID, SAMPLE_ORIGINAL_CONSUMER_HSAID, SAMPLE_CORRELATION_ID);
		Holder<GetRequestStatusResponseType> responseHolder = new Holder<GetRequestStatusResponseType>();
		Holder<ProcessingStatusType> processingStatusHolder = new Holder<ProcessingStatusType>();

		consumer.callService("logical-adress", personnummer, processingStatusHolder, responseHolder);

		log.info("Returned #timeslots = " + responseHolder.value.getRequestStatus().size());

	}

	public GetAggregatedRequestStatusTestConsumer(String serviceAddress, String senderId, String originalConsumerHsaId, String correlationId) {
	    
		// Setup a web service proxy for communication using HTTPS with Mutual Authentication
		super(GetRequestStatusResponderInterface.class, serviceAddress, senderId, originalConsumerHsaId, correlationId);
	}

	public void callService(String logicalAddress, String registeredResidentId, Holder<ProcessingStatusType> processingStatusHolder, Holder<GetRequestStatusResponseType> responseHolder) {

		log.debug("Calling GetRequestActivities-soap-service with Registered Resident Id = {}", registeredResidentId);
		
		GetRequestStatusType request = new GetRequestStatusType();

		request.setPersonId(new PersonIdType());
		request.getPersonId().setExtension(registeredResidentId);
		
		

		GetRequestStatusResponseType response = _service.getRequestStatus(logicalAddress, request);
		responseHolder.value = response;
		
		processingStatusHolder.value = SoapHeaderCxfInterceptor.getLastFoundProcessingStatus();
	}
}