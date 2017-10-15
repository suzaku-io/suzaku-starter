package starter.client

import diode.Action
import starter.shared.model.Todo
import suzaku.ui._

import scala.collection.immutable

object TodoList {
  case class State(editing: Option[String])

  case class CBP private (
      todos: immutable.Seq[Todo],
      dispatch: Action => Unit
  ) extends ComponentBlueprint { override def create(proxy: StateProxy) = new ComponentImpl(this)(proxy) }

  class ComponentImpl(initialBlueprint: CBP)(proxy: StateProxy) extends Component[CBP, State](initialBlueprint, proxy) {
    override def render(state: State): Blueprint = {
      import suzaku.ui.layout._

      LinearLayout(Direction.Vertical)(
        blueprint.todos.map(todo => TodoView(todo, state.editing.contains(todo.id), blueprint.dispatch))
      )
    }

    override def initialState: State = State(None)
  }

  def apply(todos: immutable.Seq[Todo], dispatch: Action => Unit) = CBP(todos, dispatch)
}
