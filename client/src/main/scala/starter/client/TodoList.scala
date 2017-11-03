package starter.client

import diode.Action
import starter.shared.model.Todo
import suzaku.ui._

import scala.collection.immutable

object TodoList {
  case class State private (editing: Option[String])

  case class CBP private (
      todos: immutable.Seq[Todo],
      dispatch: Action => Unit
  ) extends ComponentBlueprint { override def create = new ComponentImpl(this) }

  class ComponentImpl(initialBlueprint: CBP) extends Component[CBP, State](initialBlueprint) {
    override def render(state: State): Blueprint = {
      import suzaku.ui.layout._
      import suzaku.ui.style._

      LinearLayout(Direction.Horizontal, justify = Justify.Center)(
        LinearLayout(Direction.Vertical)(
          blueprint.todos.map(
            todo =>
              TodoView(todo,
                       state.editing.contains(todo.id),
                       blueprint.dispatch,
                       (clickedTodo, active) => modState(s => State(if (active) Some(clickedTodo.id) else None))))
        ) << (
          width := 30.em
        )
      )
    }

    override def initialState: State = State(None)
  }

  def apply(todos: immutable.Seq[Todo], dispatch: Action => Unit) = CBP(todos, dispatch)
}
