package agp

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scenarios.GetAggregatedRequestStatusPingForConfigurationScenario

/**
 * Ping for configuration run against remote service - returns ok.
 */
class TP01PingForConfiguration extends Simulation {

  val httpProtocol = http.baseURL("http://33.33.33.33:8084").disableResponseChunksDiscarding

  val pingForConfiguration = scenario("ping for configuration")
                 .repeat(2) {
                    exec(GetAggregatedRequestStatusPingForConfigurationScenario.request)
                  }
                 
  setUp (pingForConfiguration.inject(atOnceUsers(1)).protocols(httpProtocol))
}