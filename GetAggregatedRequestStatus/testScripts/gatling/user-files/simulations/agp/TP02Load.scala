package agp

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scenarios.GetAggregatedRequestStatusScenario

/**
 * Load test VP:GetAggregatedRequestStatus.
 * Aims to exercise maximum
 */
class TP02Load extends Simulation {

  val baseURL             = "https://33.33.33.33:20000"
  
  val testDuration        =      2 minutes
  val rampDuration        =     10 seconds
  val minWaitDuration     =      2 seconds
  val maxWaitDuration     =      5 seconds
  val numberOfConcurrentUsers = 10
  
  val httpProtocol = http.baseURL(baseURL).disableResponseChunksDiscarding

  val load = scenario("load")
                 .during(testDuration) {
                    feed(csv("patients.csv").circular)
                   .exec(GetAggregatedRequestStatusScenario.request)
                   .pause(minWaitDuration, maxWaitDuration)
                  }
                 
  setUp (load.inject(rampUsers(numberOfConcurrentUsers) over (rampDuration)).protocols(httpProtocol))
}