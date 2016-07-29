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

import scala.math.BigDecimal.RoundingMode

sealed trait ValueType[T] {

  def value: T

  override def toString = value.toString

}

trait StringValue extends ValueType[String]

trait IntValue extends ValueType[Int]

trait BooleanValue extends ValueType[Boolean]

trait BigDecimalValue extends ValueType[BigDecimal] {

  protected[this] def nonRoundedValue: BigDecimal

  val decimalPlaces = 2

  final lazy val value = nonRoundedValue.setScale(decimalPlaces, RoundingMode.HALF_DOWN)

  final override def equals(other: Any): Boolean = other match {
    case that: BigDecimalValue => isOfThisInstance(that) && value == that.value
    case _ => false
  }

  protected[this] def isOfThisInstance(other: BigDecimalValue): Boolean

  final override def hashCode: Int = (41 * value).toInt
}
