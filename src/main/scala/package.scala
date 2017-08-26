package freestyleKafkaExample

import freestyle.async.{AsyncContext, Proc}

object types {

  type Long = java.lang.Long

  type String = java.lang.String

  //type came from https://github.com/frees-io/freestyle-kafka/blob/master/core/src/test/scala/FSKafkaAlgebraSpec.scala#L39
  type Target[A] = Either[Throwable, A]
}

object AsyncImplicit {
  
  import types._

  // code in this object came from https://github.com/frees-io/freestyle-kafka/blob/master/core/src/test/scala/FSKafkaAlgebraSpec.scala
  implicit val eitherTestAsyncContext: AsyncContext[Target] = new AsyncContext[Target] {
    override def runAsync[A](fa: Proc[A]): Target[A] = {
      var result: Target[A] = Left(new IllegalStateException("callback did not return"))
      fa(_.fold(e => result = Left(e), a => result = Right(a)))
      result
    }
  }
}
