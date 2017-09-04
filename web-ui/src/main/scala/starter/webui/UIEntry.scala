package starter.webui

import org.scalajs.dom
import org.scalajs.dom.raw.Worker
import suzaku.platform.web.{WebWorkerTransport, WorkerClientTransport}

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scala.scalajs.js.typedarray.ArrayBuffer

@JSExportTopLevel("UIEntry")
object UIEntry {
  private var transport: WebWorkerTransport = _

  @JSExport
  def entry(workerScript: String): Unit = {
    // create the worker to run our application in
    val worker = new Worker(workerScript)

    // create the transport
    transport = new WorkerClientTransport(worker)

    // listen to messages from worker
    worker.onmessage = onMessage _

    new StarterUI(transport)
  }

  private def onMessage(msg: dom.MessageEvent): Unit = {
    msg.data match {
      case buffer: ArrayBuffer =>
        transport.receive(buffer)
      case _ => // ignore other messages
    }
  }
}
