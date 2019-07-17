package controllers

import javax.inject._
import play.api.mvc._
import services._

import play.api.data._
import play.api.data.Forms._

// 自身で実装する
class TodoController @Inject()(todoService: TodoService, mcc: MessagesControllerComponents)
  extends MessagesAbstractController(mcc) {

  def helloworld() = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok("Hello World")
  }

  def list() = Action { implicit request: MessagesRequest[AnyContent] =>
    val items: Seq[Todo] = todoService.list()
    Ok(views.html.list(items))
  }

  // Web上のフォームの各の属性値をController側で決めてる？
  val todoForm: Form[String] = Form("name" -> nonEmptyText)
  //  新しいTodoを追加するフォームがあるページを作成するAction?
  def todoNew = Action { implicit  request: MessagesRequest[AnyContent] =>
    Ok(views.html.createForm(todoForm))
  }

  // 上で定義したFormからの送信を受け取る？
  def todoAdd() = Action { implicit request: MessagesRequest[AnyContent] =>
    val name: String = todoForm.bindFromRequest().get
    todoService.insert(Todo(name))
    Redirect(routes.TodoController.list())
  }
}


