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

import play.api.libs.json._
import uk.gov.voa.valuetype.ValueType

import scala.util.{Failure, Success, Try}

object ValueTypeFormat extends ValueTypeFormat

trait ValueTypeFormat {

  implicit def parseString: PartialFunction[JsValue, String] = {
    case JsString(value) if !value.isEmpty => value
  }

  implicit def parseInt: PartialFunction[JsValue, Int] = {
    case JsNumber(value) if value.isValidInt => value.toInt
  }

  implicit def parseBigDecimal: PartialFunction[JsValue, BigDecimal] = {
    case JsNumber(value) => value
  }

  implicit def parseBoolean: PartialFunction[JsValue, Boolean] = {
    case JsBoolean(value) => value
  }

  implicit val stringToJson = JsString.apply _
  implicit val intToJson = (value: Int) => JsNumber.apply(value)
  implicit val bigDecimalToJson = (value: BigDecimal) => JsNumber.apply(value)
  implicit val booleanToJson = JsBoolean.apply _

  def format[T, V <: ValueType[T]](instantiateFromSimpleType: T => V)
                                  (implicit parse: PartialFunction[JsValue, T],
                                   toJson: T => JsValue) =
    new Format[V] {

      def reads(json: JsValue): JsResult[V] = Try(parse(json)) match {
        case Success(value) => JsSuccess(instantiateFromSimpleType(value))
        case Failure(e) => JsError(e.getMessage)
      }

      def writes(value: V): JsValue = toJson(value.value)

    }
}
