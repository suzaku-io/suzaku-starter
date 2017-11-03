package starter.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

object WebServer {
  def main(args: Array[String]):Unit = {
    implicit val system: ActorSystem             = ActorSystem("server-system")
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    val config    = ConfigFactory.load()
    val interface = config.getString("http.interface")
    val port      = config.getInt("http.port")

    val service = new WebService()

    Http().bindAndHandle(service.route, interface, port)

    println(s"Server online at http://$interface:$port")
  }
}
