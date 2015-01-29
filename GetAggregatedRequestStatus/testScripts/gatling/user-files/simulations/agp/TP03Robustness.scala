package agp

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scenarios.GetAggregatedRequestStatusScenario

/**
 * Test VP:GetAggregatedRequestStatus over 12 hours
 */
class TP03Robustness extends Simulation {

  val baseURL                 = "https://33.33.33.33:20000"
  
  val testDuration            =  12 hours
  val rampDuration            =   2 minutes
  val minWaitDuration         =   2 seconds
  val maxWaitDuration         =   5 seconds
  val numberOfConcurrentUsers =   5
  
  val httpProtocol = http.baseURL(baseURL).disableResponseChunksDiscarding

  val robustness = scenario("robustness")
                  .during(testDuration) {
                    feed(csv("patients.csv").circular)
                   .exec(GetAggregatedRequestStatusScenario.request)
                   .pause(minWaitDuration, maxWaitDuration)
                  }
                 
  setUp (robustness.inject(rampUsers(numberOfConcurrentUsers) over (rampDuration)).protocols(httpProtocol))
}