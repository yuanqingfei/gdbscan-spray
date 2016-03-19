package com.example

import breeze.linalg.DenseMatrix
import spray.json.DefaultJsonProtocol
import com.simple.algorithm.ClusterAlgo
import com.simple.io.DataGenerator
import shapeless.get
import akka.actor.Actor
import spray.routing.HttpService
import spray.http.HttpHeaders
import spray.http.MediaType
import spray.http.ContentType
import spray.http.MediaTypes
import nak.data.SparseFeatureDataset
import com.simple.rest.LocationJsonProtocol
import com.simple.rest.Location
import spray.json._
import LocationJsonProtocol._
import com.simple.rest.DataService
import com.simple.rest.Param

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class MyServiceActor extends Actor with RestService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}

// this trait defines our service behavior independently from the service actor
trait RestService extends HttpService {
  // to keep increased points
  var dataCache: Seq[(Double, Double)] = DataService.getOrginalData
  var eps: Int = 5
  var minP: Int = 15

  val myRoute = path("getClusters") {
    get {
      respondWithMediaType(MediaTypes.`application/json`) { // XML is marshalled to `text/xml` by default, so we simply override here
        complete {
          new DataService(dataCache, eps, minP).getClustersAsLocations.toJson.prettyPrint
        }
      }
    }
  } ~
    path("getAll") {
      get {
        respondWithMediaType(MediaTypes.`application/json`) {
          complete {
            new DataService(dataCache, eps, minP).getAll().toJson.prettyPrint
          }
        }
      }
    } ~
    path("getNoise") {
      get {
        respondWithMediaType(MediaTypes.`application/json`) {
          complete {
            new DataService(dataCache, eps, minP).getNoise().toJson.prettyPrint
          }
        }
      }
    } ~
    path("addLocation") {
      post {
        respondWithMediaType(MediaTypes.`application/json`) {
          entity(as[Location]) { newLoc =>
            {
              dataCache = dataCache :+ (newLoc.x, newLoc.y)
              complete {
                println("all data number: " + dataCache.size)
                """{"message": "create new point successfully"}"""
                //                new DataService(dataCache).getClustersAsLocations().toJson.prettyPrint
              }
            }
          }
        }
      }
    } ~
    path("updatePara") {
      post {
        respondWithMediaType(MediaTypes.`application/json`) {
          entity(as[Param]) { newParam =>
            {
              eps = newParam.eps
              minP = newParam.minP

              println("new parameter eps: " + eps + " minP: " + minP)
              complete {
                """{"message": "parameter update successfully"}"""
              }
            }
          }
        }
      }
    }

}


