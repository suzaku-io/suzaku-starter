package starter.client

import org.scalajs.dom
import org.scalajs.dom.raw.DedicatedWorkerGlobalScope
import suzaku.platform.web.{WebWorkerTransport, WorkerTransport}

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scala.scalajs.js.typedarray.ArrayBuffer

@JSExportTopLevel("ClientEntry")
object ClientEntry {
  import DedicatedWorkerGlobalScope.self

  private var transport: WebWorkerTransport = _

  @JSExport
  def main(args: Array[String]): Unit = {
    // create transport
    transport = new WorkerTransport(self)
    // receive WebWorker messages
    self.onmessage = onMessage _
    // create the actual application
    new StarterApp(transport)
  }

  private def onMessage(msg: dom.MessageEvent): Unit = {
    msg.data match {
      case buffer: ArrayBuffer =>
        transport.receive(buffer)
      case _ => // ignore other messages
    }
  }
}
