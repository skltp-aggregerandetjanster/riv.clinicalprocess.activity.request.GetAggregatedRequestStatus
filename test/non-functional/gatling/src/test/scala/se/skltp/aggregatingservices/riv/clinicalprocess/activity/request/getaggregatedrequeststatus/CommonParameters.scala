package se.skltp.aggregatingservices.riv.clinicalprocess.activity.request.getaggregatedrequeststatus

trait CommonParameters {
  val serviceName:String     = "RequestStatus"
  val urn:String             = "urn:riv:clinicalprocess:activity:request:GetRequestStatusResponder:1"
  val responseElement:String = "GetRequestStatusResponse"
  val responseItem:String    = "requestStatus-hd"
  var baseUrl:String         = if (System.getProperty("baseUrl") != null && !System.getProperty("baseUrl").isEmpty()) {
                                   System.getProperty("baseUrl")
                               } else {
                                   "http://33.33.33.33:8081/GetAggregatedRequestStatus/service/v1"
                               }
}
