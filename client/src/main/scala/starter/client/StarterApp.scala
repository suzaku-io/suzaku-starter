package starter.client

import diode.ModelRO
import starter.shared.model.Todo
import suzaku.app.AppBase
import suzaku.platform.Transport

class StarterApp(transport: Transport) extends AppBase(transport) {
  def render(todoReader: ModelRO[List[Todo]]) = {
    uiManager.render(
      TodoList(todoReader.value, AppCircuit.dispatch)
    )
  }

  override protected def main(): Unit = {
    AppCircuit.subscribe(AppCircuit.zoom(_.todos))(render)
    AppCircuit.dispatch(StartApp)
  }
}
