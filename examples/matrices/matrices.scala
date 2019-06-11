package matrices

object TypeFunctions {
  type Size[T <: Tuple] <: Nat = T match {
    case Unit => Zero
    case head *: tail => Succ[Size[tail]]
  }

  type %[S <: Singleton & Int] <: Nat = S match {
    case 1 => Succ[Zero]
    case 2 => Succ[%[1]]
    case 3 => Succ[%[2]]
    case 4 => Succ[%[3]]
  }
}

import TypeFunctions._

sealed trait Nat
trait Zero extends Nat
trait Succ[N <: Nat] extends Nat

type One = Succ[Zero]
type Two = Succ[One]
type Three = Succ[Two]
type Four = Succ[Three]
type Five = Succ[Four]
type Six = Succ[Five]
type Seven = Succ[Six]
type Eight = Succ[Seven]
type Nine = Succ[Eight]

trait Squares[N <: Nat, N2 <: Nat]

implied for Squares[One, One]
implied for Squares[Two, Four]
implied for Squares[Three, Nine]

object LinearAlgebra {
  opaque type Matrix[N <: Nat] = Array[Double]
  
  def matrix[N <: Singleton & Int, Xs <: Tuple, S <: Nat](xs: Xs)
      given (sq: Squares[%[N], S], eq: Size[Xs] =:= S): Matrix[%[N]] = ??? //xs.toArray
  
  def (m1: Matrix[N1]) + [N1 <: Nat, N2 <: Nat] (m2: Matrix[N2]) given (N1 =:= N2) : Matrix[N1] = m1.zip(m2).map(_ + _)
}

object Test {
  import LinearAlgebra._
  val id = matrix[N = 3](1, 0, 0, 0, 1, 0, 0, 0, 1)
  val ones = matrix[N = 3](1, 1, 1, 1, 1, 1, 1, 1, 1)
  //val ones = matrix[N = 2](1, 1, 1, 1)

  println(ones + id)
}


