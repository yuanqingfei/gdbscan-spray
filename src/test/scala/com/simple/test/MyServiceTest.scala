package com.simple.test

import org.scalacheck.Prop.forAll
import org.scalacheck.Properties

object ScalaCheckDemo extends Properties("Demo") {

  property("myprop") = forAll { l: List[Int] =>
    l.reverse.reverse == l
  }

}