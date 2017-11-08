/**
 * Copyright (c) 2014 Inera AB, <http://inera.se/>
 *
 * This file is part of SKLTP.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
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