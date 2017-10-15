package starter.client

import java.util.UUID

import diode._
import starter.shared.model.Todo

object AppCircuit extends Circuit[AppModel] {
  def initialModel = AppModel()

  def actionHandler = new TodoHandler(zoomTo(_.todos))
}

class TodoHandler[M](modelRW: ModelRW[M, List[Todo]]) extends ActionHandler(modelRW) {
  private def updateOne(Id: String)(f: Todo => Todo): List[Todo] =
    value.map {
      case found @ Todo(Id, _, _) => f(found)
      case other                  => other
    }

  override protected def handle = {
    case StartApp =>
      updated(List(
        Todo("1", "Testing", false),
        Todo("2", "Testing again", false),
        Todo("3", "Testing moar", true)
      ))
    case UpdateTodos(newTodos) =>
      updated(newTodos)
    case AddTodo(content) =>
      updated(value :+ Todo(UUID.randomUUID().toString, content, done = false))
    case ToggleAll(checked) =>
      updated(value.map(_.copy(done = checked)))
    case ToggleCompleted(id) =>
      println(s"Toggling $id")
      updated(updateOne(id)(old => old.copy(done = !old.done)))
    case Update(id, content) =>
      println(s"Updating $id = $content")
      updated(updateOne(id)(_.copy(content = content)))
    case Delete(id) =>
      println(s"Deleting $id")
      updated(value.filterNot(_.id == id))
    case ClearCompleted =>
      updated(value.filterNot(_.done))
  }
}

// actions
case object StartApp extends Action

case object LoadTodos extends Action

case class UpdateTodos(todos: List[Todo]) extends Action

case class AddTodo(content: String) extends Action

case class ToggleAll(checked: Boolean) extends Action

case class ToggleCompleted(id: String) extends Action

case class Update(id: String, content: String) extends Action

case class Delete(id: String) extends Action

case object ClearCompleted extends Action
