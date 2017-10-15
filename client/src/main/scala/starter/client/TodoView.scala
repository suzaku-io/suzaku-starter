package starter.client

import diode.Action
import starter.shared.model.Todo
import suzaku.ui._
import suzaku.widget.{Checkbox, Text, TextField}

object TodoView {
  case class State()

  case class CBP private (
      todo: Todo,
      editing: Boolean,
      dispatch: Action => Unit
  ) extends ComponentBlueprint { override def create(proxy: StateProxy) = new ComponentImpl(this)(proxy) }

  class ComponentImpl(initialBlueprint: CBP)(proxy: StateProxy) extends Component[CBP, State](initialBlueprint, proxy) {
    def dispatch = blueprint.dispatch

    override def render(state: State): Blueprint = {
      import suzaku.ui.layout._

      val todo = blueprint.todo
      LinearLayout()(
        Checkbox(todo.done, onChange = _ => dispatch(ToggleCompleted(todo.id))),
        if (blueprint.editing)
          TextField(todo.content, onChange = (value) => dispatch(Update(todo.id, value)))
        else
          Text(s"${todo.content} (${if(todo.done) "done" else "todo"})")
      )
    }

    override def initialState: State = State()
  }

  def apply(todo: Todo, editing: Boolean, dispatch: Action => Unit) = CBP(todo, editing, dispatch)
}
