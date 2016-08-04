package uk.gov.voa.valuetype

import org.scalatest.{Matchers, WordSpec}

class IntOptionsSpec extends WordSpec with Matchers {

  import TestIntOption._

  "all" should {

    "return all items" in {
      TestIntOption.all should contain theSameElementsAs Seq(TestOption5, TestOption6, TestOption7)
    }
  }

  "get" should {

    "return the matching option" in {
      TestIntOption.get(6) shouldBe Some(TestOption6)
    }

    "return None if the given Int does not match the value of any option" in {
      TestIntOption.get(0) shouldBe None
    }
  }

  "of" should {

    "return the matching option" in {
      TestIntOption.of(7) shouldBe TestOption7
    }

    "throw an exception if the given Int does not match the value of any option" in {
      an[IllegalArgumentException] should be thrownBy TestIntOption.of(8)
    }
  }
}
