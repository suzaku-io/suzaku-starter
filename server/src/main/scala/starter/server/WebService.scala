package starter.server

import akka.http.scaladsl.server.Directives

class WebService() extends Directives {

  val route = {
    pathSingleSlash {
      get {
        getFromResource("public/index.html")
      }
    } ~
      pathPrefix("assets" / Remaining) { file =>
        // optionally compresses the response with Gzip or Deflate
        // if the client accepts compressed responses
        encodeResponse {
          getFromResource("public/" + file)
        }
      }
  }
}