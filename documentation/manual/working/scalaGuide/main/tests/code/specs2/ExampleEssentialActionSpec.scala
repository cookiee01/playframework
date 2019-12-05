/*
 * Copyright (C) 2009-2019 Lightbend Inc. <https://www.lightbend.com>
 */

package scalaguide.tests.specs2

import play.api.mvc._
import play.api.test._
import play.api.mvc.Results._
import play.api.libs.json.Json

// #scalatest-exampleessentialactionspec
class ExampleEssentialActionSpec extends PlaySpecification {
  "An essential action" should {
    "can parse a JSON body" in new WithApplication() with Injecting {
      val Action = inject[DefaultActionBuilder]
      val parse  = inject[PlayBodyParsers]

      val action: EssentialAction = Action(parse.json) { request =>
        val value = (request.body \ "field").as[String]
        Ok(value)
      }

      val request = FakeRequest(POST, "/").withJsonBody(Json.parse("""{ "field": "value" }"""))

      val result = call(action, request)

      status(result) mustEqual OK
      contentAsString(result) mustEqual "value"
    }
  }
}
// #scalatest-exampleessentialactionspec
