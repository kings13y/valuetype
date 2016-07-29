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

package uk.gov.voa.valuetype

import org.scalacheck.Arbitrary._
import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, WordSpec}

import scala.math.BigDecimal.RoundingMode

class StringValueSpec extends WordSpec with PropertyChecks with Matchers {

  val generatedStrings = Gen.alphaStr

  "StringValue.apply" should {

    "work for any String" in {
      forAll(generatedStrings) { value =>
        TestStringValue(value).value shouldBe value
      }
    }
  }

  "StringValue.toString" should {

    "be the same as the given value" in {
      forAll(generatedStrings) { value =>
        TestStringValue(value).toString shouldBe value
      }
    }
  }
}

class IntValueSpec extends WordSpec with PropertyChecks with Matchers {

  val generatedInts = Gen.choose(Int.MinValue, Int.MaxValue)

  "IntValue.apply" should {

    "work for any Int" in {
      forAll(generatedInts) { value =>
        TestIntValue(value).value shouldBe value
      }
    }
  }

  "IntValue.toString" should {

    "be the same as String representation of the given Int value" in {
      forAll(generatedInts) { value =>
        TestIntValue(value).toString shouldBe value.toString
      }
    }
  }
}

class BooleanValueSpec extends WordSpec with PropertyChecks with Matchers {

  val booleans = Table("Boolean", true, false)

  "BooleanValue.apply" should {

    "work for any Boolean" in {
      forAll(booleans) { value =>
        println(value)
        TestBooleanValue(value).value shouldBe value
      }
    }
  }

  "BooleanValue.toString" should {

    "be the same as String representation of the given Boolean value" in {
      forAll(booleans) { value =>
        TestBooleanValue(value).toString shouldBe value.toString
      }
    }
  }
}

class BigDecimalValueSpec extends WordSpec with PropertyChecks with Matchers {

  case class AnotherTestBigDecimalValue(nonRoundedValue: BigDecimal) extends BigDecimalValue {

    protected[this] def isOfThisInstance(other: BigDecimalValue) = other.isInstanceOf[AnotherTestBigDecimalValue]

  }

  val generatedBigDecimals = arbitrary[Double].map(BigDecimal.apply)

  "BigDecimalValue.apply" should {

    "work with all given BigDecimal values" in {
      forAll(generatedBigDecimals) { value =>
        TestBigDecimalValue(value).value shouldBe value.setScale(2, RoundingMode.HALF_DOWN)
      }
    }
  }

  "BigDecimalValue.value" should {

    "be initial value rounded half down using the scale of 2" in {
      TestBigDecimalValue(2.005).value shouldBe BigDecimal(2.00)
      TestBigDecimalValue(2.0051).value shouldBe BigDecimal(2.01)
    }
  }

  "BigDecimalValue.toString" should {

    "be the same as the given value" in {
      forAll(generatedBigDecimals) { value =>
        TestBigDecimalValue(value).toString shouldBe value.setScale(2, RoundingMode.HALF_DOWN).toString()
      }
    }
  }

  "BigDecimalValue.equals" should {

    "use the rounded value for checking equality" in {
      TestBigDecimalValue(2.005) shouldBe TestBigDecimalValue(2.00)
      TestBigDecimalValue(2.0051) shouldBe TestBigDecimalValue(2.01)
    }

    "return false if compared with different type of BigDecimalValue" in {
      TestBigDecimalValue(2.005) should not be AnotherTestBigDecimalValue(2.00)
      TestBigDecimalValue(2.005) should not be AnotherTestBigDecimalValue(2.005)
    }

    "return false if compared with a non BigDecimalValue" in {
      TestBigDecimalValue(2) should not be BigDecimal(2.00)
    }
  }

  "BigDecimalValue.hashCode" should {

    "return the same values for different instances representing the same value" in {
      TestBigDecimalValue(2.005).hashCode shouldBe TestBigDecimalValue(2.00).hashCode
      TestBigDecimalValue(2.0051).hashCode shouldBe TestBigDecimalValue(2.01).hashCode
    }

    "return different values for different instances representing different values" in {
      TestBigDecimalValue(2.05).hashCode should not be TestBigDecimalValue(2.00).hashCode
    }
  }

  "BigDecimalValue.==" should {

    "return true if compared with another instance of the same type representing the same value" in {
      TestBigDecimalValue(2.005) == TestBigDecimalValue(2.00) shouldBe true
      TestBigDecimalValue(2.0051) == TestBigDecimalValue(2.01) shouldBe true
    }

    "return false if compared with another instance of the same type representing different value" in {
      TestBigDecimalValue(2.01) == TestBigDecimalValue(2.00) shouldBe false
    }

    "return false if compared with different implementation of the same super type even when representing the same value" in {
      TestBigDecimalValue(2.005) == AnotherTestBigDecimalValue(2.005) shouldBe false
      TestBigDecimalValue(2.0051) == AnotherTestBigDecimalValue(2.051) shouldBe false
    }
  }
}
