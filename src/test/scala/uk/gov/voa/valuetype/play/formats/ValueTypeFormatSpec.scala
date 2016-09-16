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
import play.api.libs.json._
import uk.gov.voa.valuetype.play.formats.ValueTypeFormat._
import uk.gov.voa.valuetype._

class StringValueTypeFormatSpec extends WordSpec with Matchers {

  implicit val stringValueFormat = format(TestStringValue.apply)

  "StringValue.format" should {

    "allow to deserialize given value to json" in {
      toJson(TestStringValue("value")) shouldBe JsString("value")
    }

    "allow deserializing json into an object" in {
      fromJson(JsString("value")).get shouldBe TestStringValue("value")
    }

    "fail deserialization when given json has the wrong type" in {
      fromJson(JsNumber(1)) shouldBe a [JsError]
    }
  }
}

class IntValueTypeFormatSpec extends WordSpec with Matchers {

  implicit val intValueFormat = format(TestIntValue.apply)

  "IntValue.format" should {

    "allow to serialize given value to json" in {
      toJson(TestIntValue(1)) shouldBe JsNumber(1)
    }

    "allow deserializing json into an object" in {
      fromJson(JsNumber(1)).get shouldBe TestIntValue(1)
    }

    "fail deserialization when given json has the wrong type" in {
      fromJson(JsString("1")) shouldBe a [JsError]
    }

    "fail deserialization if the value is out of range" in {
      fromJson(JsNumber(1L + Int.MaxValue.toLong)) shouldBe a [JsError]
    }
  }
}

class LongValueTypeFormatSpec extends WordSpec with Matchers {

  implicit val longValueFormat = format(TestLongValue.apply)

  "LongValue.format" should {

    "allow to serialize given value to json" in {
      toJson(TestLongValue(1)) shouldBe JsNumber(1)
    }

    "allow deserializing json into an object" in {
      fromJson(JsNumber(1)).get shouldBe TestLongValue(1)
      fromJson(JsNumber(1L + Int.MaxValue.toLong)).get shouldBe TestLongValue(1L + Int.MaxValue.toLong)
    }

    "fail deserialization when given json has the wrong type" in {
      fromJson(JsString("1")) shouldBe a [JsError]
    }
  }
}

class BooleanValueTypeFormatSpec extends WordSpec with Matchers {

  implicit val booleanValueFormat = format(TestBooleanValue.apply)

  "BooleanValue.format" should {

    "allow to serialize given value to json" in {
      toJson(TestBooleanValue(true)) shouldBe JsBoolean(true)
    }

    "allow deserializing json into an object" in {
      fromJson(JsBoolean(false)).get shouldBe TestBooleanValue(false)
    }

    "fail deserialization when given json has the wrong type" in {
      fromJson(JsNumber(0)) shouldBe a [JsError]
    }
  }
}

class BigDecimalValueTypeFormatSpec extends WordSpec with Matchers {

  implicit val bigDecimalValueFormat = format(TestBigDecimalValue.apply)

  "BigDecimalValue.format" should {

    "allow to serialize given value to json" in {
      toJson(TestBigDecimalValue(2.1051)) shouldBe JsNumber(2.1051)
    }

    "allow deserializing json into an object" in {
      fromJson(JsNumber(2.1051)).get shouldBe TestBigDecimalValue(2.1051)
    }

    "fail deserialization when given json has the wrong type" in {
      fromJson[TestBigDecimalValue](JsString("1")) shouldBe a [JsError]
    }
  }

}

class RoundedBigDecimalValueTypeFormatSpec extends WordSpec with Matchers {

  implicit val bigDecimalValueFormat = format(TestRoundedBigDecimalValue.apply)

  "BigDecimalValue.format" should {

    "allow to serialize given value to json" in {
      toJson(TestRoundedBigDecimalValue(2.105)) shouldBe JsNumber(2.11)
    }

    "allow deserializing json into an object" in {
      fromJson(JsNumber(2.105)).get shouldBe TestRoundedBigDecimalValue(2.11)
    }

    "fail deserialization when given json has the wrong type" in {
      fromJson[TestRoundedBigDecimalValue](JsString("1")) shouldBe a [JsError]
    }
  }
}

class ValueTypeFormatSpec extends WordSpec with Matchers {

  private case class Weekend(value: String) extends ValueType[String] {
    require(value == "Saturday" || value == "Sunday")
  }

  private implicit val weekendFormat = format(Weekend)

  "serializing a value type" should {

    "produce the correct JSON" in {
      toJson(Weekend("Saturday")) shouldBe JsString("Saturday")
    }
  }

  "deserializing a value type" should {

    "produce the correct object for valid JSON" in {
      fromJson(JsString("Sunday")).get shouldBe Weekend("Sunday")
    }

    "fail when given JSON has the wrong type" in {
      fromJson[Weekend](JsNumber(2)) shouldBe a [JsError]
      fromJson[Weekend](JsBoolean(true)) shouldBe a [JsError]
      fromJson[Weekend](Json.arr("Saturday", "Sunday")) shouldBe a [JsError]
      fromJson[Weekend](Json.obj("day" -> "Saturday")) shouldBe a [JsError]
    }

    "fail when given JSON contains an invalid value" in {
      fromJson[Weekend](JsString("Monday")) shouldBe a [JsError]
    }
  }
}