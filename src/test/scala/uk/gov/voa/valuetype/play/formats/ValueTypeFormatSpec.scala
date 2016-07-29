/*
 * Copyright 2016 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.voa.valuetype.play.formats

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.Json.{fromJson, toJson}
import play.api.libs.json.{JsError, Json}
import uk.gov.voa.valuetype.play.formats.ValueTypeFormat._
import uk.gov.voa.valuetype.{TestBigDecimalValue, TestBooleanValue, TestIntValue, TestStringValue}

class StringValueTypeFormatSpec extends WordSpec with Matchers {

  implicit val stringValueFormat = format(TestStringValue.apply)

  "StringValue.format" should {

    "allow to deserialize given value to json" in {
      toJson(TestStringValue("value")) shouldBe Json.parse("\"value\"")
    }

    "allow deserializing json into an object" in {
      fromJson(Json.parse("\"value\"")).get shouldBe TestStringValue("value")
    }

    "fail deserialization when given json is invalid" in {
      fromJson(Json.parse("1")) shouldBe a [JsError]
    }
  }
}

class IntValueTypeFormatSpec extends WordSpec with Matchers {

  implicit val intValueFormat = format(TestIntValue.apply)

  "IntValue.format" should {

    "allow to serialize given value to json" in {
      Json.toJson(TestIntValue(1)) shouldBe Json.parse("1")
    }

    "allow deserializing json into an object" in {
      Json.fromJson(Json.parse("1")).get shouldBe TestIntValue(1)
    }

    "fail deserialization when given json is invalid" in {
      fromJson(Json.parse("\"1\"")) shouldBe a [JsError]
    }
  }
}

class BooleanValueTypeFormatSpec extends WordSpec with Matchers {

  implicit val booleanValueFormat = format(TestBooleanValue.apply)

  "BooleanValue.format" should {

    "allow to serialize given value to json" in {
      toJson(TestBooleanValue(true)) shouldBe Json.parse("true")
    }

    "allow deserializing json into an object" in {
      fromJson(Json.parse("false")).get shouldBe TestBooleanValue(false)
    }

    "fail deserialization when given json is invalid" in {
      fromJson(Json.parse("1")) shouldBe a [JsError]
    }
  }
}

class BigDecimalValueTypeFormatSpec extends WordSpec with Matchers {

  implicit val bigDecimalValueFormat = format(TestBigDecimalValue.apply)

  "BigDecimalValue.format" should {

    "allow to serialize given value to json" in {
      toJson(TestBigDecimalValue(2.1051)) shouldBe Json.parse("2.11")
    }

    "allow deserializing json into an object" in {
      fromJson(Json.parse("2.1051")).get shouldBe TestBigDecimalValue(2.11)
    }

    "fail deserialization when given json is invalid" in {
      fromJson(Json.parse("\"1\"")) shouldBe a [JsError]
    }
  }

}
