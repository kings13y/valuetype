package uk.gov.voa.valuetype

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


sealed trait TestIntOption extends IntValue

object TestIntOption extends IntOptions[TestIntOption] {

  case object TestOption5 extends TestIntOption {
    val value = 5
  }

  case object TestOption6 extends TestIntOption {
    val value = 6
  }

  case object TestOption7 extends TestIntOption {
    val value = 7
  }

  val all = Seq(
    TestOption5,
    TestOption6,
    TestOption7
  )
}