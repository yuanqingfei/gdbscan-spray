package com.simple.rest

import spray.json.DefaultJsonProtocol
import spray.httpx.SprayJsonSupport

case class Location(val x: Double, val y: Double)

case class Param(val eps: Int, val minP: Int)

object LocationJsonProtocol extends DefaultJsonProtocol with SprayJsonSupport{
  implicit val locationFormat = jsonFormat2(Location)
  implicit val paramFormat = jsonFormat2(Param)
}

