package io.finch.endpoint

import cats.Id
import cats.effect.Sync
import io.finch._

import scala.reflect.ClassTag

abstract private[finch] class Header[F[_], G[_], A](name: String)(implicit
    d: DecodeEntity[A],
    tag: ClassTag[A],
    protected val F: Sync[F]
) extends Endpoint[F, G[A]] { self =>

  protected def missing(name: String): F[Output[G[A]]]
  protected def present(value: A): G[A]

  final def apply(input: Input): EndpointResult[F, G[A]] = {
    val output: F[Output[G[A]]] = F.defer {
      input.request.headerMap.getOrNull(name) match {
        case null  => missing(name)
        case value =>
          d(value) match {
            case Right(s) => F.pure(Output.payload(present(s)))
            case Left(e)  => F.raiseError(Error.HeaderNotParsed(name, tag).initCause(e))
          }
      }
    }

    EndpointResult.Matched(input, Trace.empty, output)
  }

  final override def toString: String = s"header($name)"
}

private[finch] object Header {

  trait Required[F[_], A] { _: Header[F, Id, A] =>
    protected def missing(name: String): F[Output[A]] =
      F.raiseError(Error.HeaderNotPresent(name))
    protected def present(value: A): Id[A] = value
  }

  trait Optional[F[_], A] { _: Header[F, Option, A] =>
    protected def missing(name: String): F[Output[Option[A]]] =
      F.pure(Output.None)
    protected def present(value: A): Option[A] = Some(value)
  }
}
