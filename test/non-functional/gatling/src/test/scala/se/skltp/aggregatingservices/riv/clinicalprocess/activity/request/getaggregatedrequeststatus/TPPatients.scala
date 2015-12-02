package se.skltp.aggregatingservices.riv.clinicalprocess.activity.request.getaggregatedrequeststatus

import se.skltp.agp.testnonfunctional.TPPatientsAbstract

/**
 * Test GetAggregatedRequestStatus using test cases defined in patients.csv (or patients-override.csv)
 */
class TPPatients extends TPPatientsAbstract with CommonParameters {
  setUp(setUpAbstract(serviceName, urn, responseElement, responseItem, baseUrl, Some(responseItemUrn)))
}
