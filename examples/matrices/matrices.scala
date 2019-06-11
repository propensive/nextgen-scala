package matrices

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
  
  def matrix[N <: Nat, Xs <: Tuple, S <: Nat](xs: Xs)
      given (sq: Squares[N, S], eq: TypeFunctions.Size[Xs] =:= S): Matrix[N] = ??? //xs.toArray
  
  def (m1: Matrix[N1]) + [N1 <: Nat, N2 <: Nat] (m2: Matrix[N2]) given (N1 =:= N2) : Matrix[N1] = m1.zip(m2).map(_ + _)
}

object TypeFunctions {
  type Size[T <: Tuple] <: Nat = T match {
    case Unit => Zero
    case head *: tail => Succ[Size[tail]]
  }
}


object Test {
  import LinearAlgebra._
  val id = matrix[N = Three](1, 0, 0, 0, 1, 0, 0, 0)
  val ones = matrix[N = Three](1, 1, 1, 1, 1, 1, 1, 1, 1)
  //val ones = matrix[N = Two](1, 1, 1, 1)

  println(ones + id)
}


