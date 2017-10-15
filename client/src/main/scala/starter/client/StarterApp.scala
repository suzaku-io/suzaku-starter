package starter.client

import suzaku.app.AppBase
import suzaku.platform.Transport
import suzaku.widget.Button
import diode._

class StarterApp(transport: Transport) extends AppBase(transport) {
  override protected def main(): Unit = {
    AppCircuit.subscribe(AppCircuit.zoom(_.todos))(todoReader => uiManager.render(TodoList(todoReader.value, AppCircuit.dispatch)))
    AppCircuit.dispatch(StartApp)
  }
}
