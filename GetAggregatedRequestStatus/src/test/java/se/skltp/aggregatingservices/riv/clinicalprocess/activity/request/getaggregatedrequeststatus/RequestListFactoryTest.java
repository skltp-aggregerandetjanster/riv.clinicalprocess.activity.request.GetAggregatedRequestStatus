package se.skltp.aggregatingservices.riv.clinicalprocess.activity.request.getaggregatedrequeststatus;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import riv.clinicalprocess.activity.request.getrequeststatusresponder.v1.GetRequestStatusType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentResponseType;
import se.skltp.agp.service.api.QueryObject;

public class RequestListFactoryTest {
	
	private RequestListFactoryImpl testObject = new RequestListFactoryImpl();
	
	@Test
	public void createRequestList() {
	    List<Object[]> list = testObject.createRequestList(new QueryObject(null, new GetRequestStatusType()), new FindContentResponseType());
	    assertTrue(list != null);
	}
}
