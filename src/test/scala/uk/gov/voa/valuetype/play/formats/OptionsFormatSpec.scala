package uk.gov.voa.valuetype.play.formats

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json._
import uk.gov.voa.valuetype.{TestIntOption, TestStringOption}

class OptionsFormatSpec extends WordSpec with Matchers {

  import OptionsFormat._

  "optionsFormat for StringOptions" should {

    import TestStringOption._

    implicit val format = optionsFormat(TestStringOption)

    "allow to serialize the given option to a JsString with value of the given option's value" in {
      Json.toJson(TestOption1) shouldBe JsString("1")
    }

    "allow to deserialize the given json String to a relevant Option" in {
      Json.fromJson(JsString("2")) shouldBe JsSuccess(TestOption2)
    }

    "return deserialization error when deserializing value for non-exisiting option" in {
      Json.fromJson(JsString("x")) shouldBe a [JsError]
    }

    "return deserialization error when deserializing non-string value" in {
      implicit val format = optionsFormat(TestStringOption)
      Json.fromJson(JsNumber(1)) shouldBe a [JsError]
    }
  }

  "optionsFormat for IntOptions" should {

    import TestIntOption._

    implicit val format = optionsFormat(TestIntOption)

    "allow to serialize the given option to a JsNumber with value of the given option's value" in {
      Json.toJson(TestOption7) shouldBe JsNumber(7)
    }

    "allow to deserialize the given json Number to a relevant Option" in {
      Json.fromJson(JsNumber(5)) shouldBe JsSuccess(TestOption5)
    }

    "return deserialization error when deserializing value for non-exisiting option" in {
      Json.fromJson(JsNumber(0)) shouldBe a [JsError]
    }

    "return deserialization error when deserializing non-integer numeric value" in {
      implicit val format = optionsFormat(TestStringOption)
      Json.fromJson(JsNumber(5.1)) shouldBe a [JsError]
    }

    "return deserialization error when deserializing non-numeric value" in {
      implicit val format = optionsFormat(TestStringOption)
      Json.fromJson(JsString("5")) shouldBe a [JsError]
    }
  }
}
