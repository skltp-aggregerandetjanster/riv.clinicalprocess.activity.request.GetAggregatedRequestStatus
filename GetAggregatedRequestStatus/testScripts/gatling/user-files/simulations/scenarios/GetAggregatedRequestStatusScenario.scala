package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.check.HttpCheck

object GetAggregatedRequestStatusScenario {
  
  val headers = Map(
    "Accept-Encoding"                        -> "gzip,deflate",
    "Content-Type"                           -> "text/xml;charset=UTF-8",
    "SOAPAction"                             -> "urn:riv:clinicalprocess:activity:request:GetRequestStatusResponder:1:GetRequestStatus",
    "x-vp-sender-id"                         -> "test",
    "x-rivta-original-serviceconsumer-hsaid" -> "test",
    "Keep-Alive"                             -> "115")
    
  val request = exec(
        http("GetAggregatedRequestStatus ${patientid} - ${name}")
          .post("")
          .headers(headers)
          .body(ELFileBody("GetRequestStatus.xml"))
          .check(status.is(session => session("status").as[String].toInt))
          .check(xpath("soap:Envelope", List("soap" -> "http://schemas.xmlsoap.org/soap/envelope/")).exists)
          .check(substring("GetRequestStatusResponse"))
          .check(substring("HSA-ID-4"))
          .check(substring("HSA-ID-5"))
          .check(substring("HSA-ID-6"))
       // TODO - xpath expression for counting elements without prefix
       // .check(xpath("//requestStatus", List("" -> "urn:riv:clinicalprocess:activity:request:GetRequestStatusResponder:1")).count.is(session => session("count").as[String].toInt))
      )
}
