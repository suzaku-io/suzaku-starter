package starter.client

import suzaku.platform.Transport
import suzaku.platform.web.AppEntry

import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("ClientEntry")
object ClientEntry extends AppEntry {
  override def start(transport: Transport) = new StarterApp(transport)
}
