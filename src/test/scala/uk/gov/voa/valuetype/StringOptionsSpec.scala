package uk.gov.voa.valuetype

import org.scalatest.{Matchers, WordSpec}

class StringOptionsSpec extends WordSpec with Matchers {

  sealed trait TestStringOption extends StringValue

  object TestStringOption extends StringOptions[TestStringOption] {

    case object TestOption1 extends TestStringOption {
      val value = "1"
    }

    case object TestOption2 extends TestStringOption {
      val value = "2"
    }

    val all = Seq(
      TestOption1,
      TestOption2
    )
  }

  import TestStringOption._

  "all" should {

    "return all items" in {
      TestStringOption.all should contain theSameElementsAs Seq(TestOption1, TestOption2)
    }
  }

  "get" should {

    "return the matching option" in {
      TestStringOption.get("1") shouldBe Some(TestOption1)
    }

    "return None if given String does not match the value of any option" in {
      TestStringOption.get("invalid") shouldBe None
    }
  }

  "of" should {

    "return the matching option" in {
      TestStringOption.of("2") shouldBe TestOption2
    }

    "throw an exception if the given String does not match the value of any option" in {
      an[IllegalArgumentException] should be thrownBy TestStringOption.of("invalid")
    }
  }
}
