package starter.server

import akka.http.scaladsl.server.{Directives, Route}

class WebService() extends Directives {

  val route: Route = {
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
