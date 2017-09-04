package starter.client

import suzaku.app.AppBase
import suzaku.platform.Transport
import suzaku.widget.Button

class StarterApp(transport: Transport) extends AppBase(transport) {
  override protected def main(): Unit = {
    uiManager.render(Button(label = "Hello world!"))
  }
}
