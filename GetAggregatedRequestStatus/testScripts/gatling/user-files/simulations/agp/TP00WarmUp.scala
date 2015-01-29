package agp

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scenarios.GetAggregatedRequestStatusScenario

/**
 * Simple requests to warm up service.
 */
class TP00WarmUp extends Simulation {

  val baseURL           = "https://33.33.33.33:20000"
  
  val testDuration      = 60 * 60 * 12 seconds // 12 timmar
  val minWaitDuration   = 2000 milliseconds
  val maxWaitDuration   = 5000 milliseconds
  val times:Int         = 1
  
  val httpProtocol = http.baseURL(baseURL).disableResponseChunksDiscarding

  val warmUp = scenario("warm up")
                 .repeat(times) {
                    feed(csv("patients.csv").circular)
                   .exec(GetAggregatedRequestStatusScenario.request)
                   .pause(1 second)
                  }
                 
  setUp (warmUp.inject(atOnceUsers(1)).protocols(httpProtocol))
}