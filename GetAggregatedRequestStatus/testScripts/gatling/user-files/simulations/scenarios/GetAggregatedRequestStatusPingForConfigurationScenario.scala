package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.check.HttpCheck

object GetAggregatedRequestStatusPingForConfigurationScenario {
  
  val contextPath   = "/agp/getaggregatedrequeststatus/itintegration/monitoring/PingForConfiguration/1/rivtabp21"
  
  val headers = Map(
    "Accept-Encoding"                        -> "gzip,deflate",
    "Content-Type"                           -> "text/xml;charset=UTF-8",
    "SOAPAction"                             -> "urn:riv:itintegration:monitoring:PingForConfigurationResponder:1:PingForConfiguration",
//  "x-vp-sender-id"                         -> "sid",
//  "x-rivta-original-serviceconsumer-hsaid" -> "TP",
    "Keep-Alive"                             -> "115")

  val request = exec(
        http("GetAggregatedRequestStatusPingForConfiguration")
          .post(contextPath)
          .headers(headers)
          .body(RawFileBody("GetRequestStatusPingForConfiguration.xml"))
          .check(status.is(200))
          .check(substring("Applikation"))
          .check(substring("GetAggregatedRequestStatus"))
          .check(xpath("soap:Envelope", List("soap" -> "http://schemas.xmlsoap.org/soap/envelope/")).exists)
          .check(regex("GetAggregatedRequestStatus").exists)
      )
  }



/*
   <soap:Body>
      <PingForConfigurationResponse xmlns="urn:riv:itintegration:monitoring:PingForConfigurationResponder:1" xmlns:ns2="urn:riv:itintegration:registry:1">
         <pingDateTime>20150126021413</pingDateTime>
         <configuration>
            <name>Applikation</name>
            <value>GetAggregatedRequestStatus</value>
         </configuration>
      </PingForConfigurationResponse>
   </soap:Body>
*/