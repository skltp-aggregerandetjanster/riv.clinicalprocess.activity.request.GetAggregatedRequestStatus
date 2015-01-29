package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.check.HttpCheck

object GetAggregatedRequestStatusScenario {
  
  val contextPath   = "/vp/clinicalprocess/activity/request/GetRequestStatus/1/rivtabp21"
   
  val headers = Map(
    "Accept-Encoding"                        -> "gzip,deflate",
    "Content-Type"                           -> "text/xml;charset=UTF-8",
    "SOAPAction"                             -> "urn:riv:clinicalprocess:activity:request:GetRequestStatusResponder:1:GetRequestStatus",
    "x-vp-sender-id"                         -> "sid",
    "x-rivta-original-serviceconsumer-hsaid" -> "TP",
    "Keep-Alive"                             -> "115")

  val request = exec(
        http("GetAggregatedRequestStatus ${patientid} - ${name}")
          .post(contextPath)
          .headers(headers)
          .body(ELFileBody("GetRequestStatus.xml"))
          .check(status.is(session => session("status").as[String].toInt))
          .check(xpath("soap:Envelope", List("soap" -> "http://schemas.xmlsoap.org/soap/envelope/")).exists)
          .check(substring("${patientid}"))
      )
  }

/*

<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
  xmlns:urn="urn:riv:interoperability:headers:1" xmlns:urn1="urn:riv:itintegration:registry:1">
  <soapenv:Header>
    <ProcessingStatus xmlns="urn:riv:interoperability:headers:1">
      <ProcessingStatusList>
        <logicalAddress>HSA-ID-1</logicalAddress>
        <statusCode>DataFromSource</statusCode>
        <isResponseFromCache>false</isResponseFromCache>
        <isResponseInSynch>true</isResponseInSynch>
        <lastSuccessfulSynch>20150129021509</lastSuccessfulSynch>
      </ProcessingStatusList>
    </ProcessingStatus>
  </soapenv:Header>
  <soapenv:Body>
    <ns2:GetRequestStatusResponse
      xmlns:ns2="urn:riv:clinicalprocess:activity:request:GetRequestStatusResponder:1"
      xmlns="urn:riv:clinicalprocess:activity:request:1" xmlns:ns3="urn:riv:interoperability:headers:1">
      <requestStatus>
        <requestStatusId />
        <requestStatus />
        <statusTime />
        <statusSetter />
        <requestId />
        <requestVersionNumber>123</requestVersionNumber>
        <requestMedium />
        <requestOrganisation>
          <careUnitId extension="HSA-ID-1" />
        </requestOrganisation>
        <requestAuthor />
        <patient>
          <personId extension="194911172296" />
        </patient>
        <recipient />
      </requestStatus>
    </ns2:GetRequestStatusResponse>
  </soapenv:Body>
</soapenv:Envelope>

*/
