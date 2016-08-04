package uk.gov.voa.valuetype

import scala.language.implicitConversions

trait Options[T, VT <: ValueType[T]] {

  def all: Seq[VT]

  private lazy val map: Map[T, VT] = all.map(v => v.value -> v).toMap

  final def get(rawValue: T): Option[VT] = map.get(rawValue)

  final def of(rawValue: T): VT =
    get(rawValue).getOrElse(throw new IllegalArgumentException(s"'$rawValue' not known as a valid ${getClass.getSimpleName}"))
}

trait StringOptions[VT <: StringValue] extends Options[String, VT]

trait IntOptions[VT <: IntValue] extends Options[Int, VT]
