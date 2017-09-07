package starter.webui

import suzaku.platform.Transport

import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("UIEntry")
object UIEntry extends suzaku.platform.web.UIEntry {
  override def start(transport: Transport) = new StarterUI(transport)
}
