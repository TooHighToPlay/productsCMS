package controllers

import models.Product
import play.api.mvc.{Action, Controller}

/**
 * Created by TooHighToPlay [github.com/TooHighToPlay] on 04/03/15.
 */
object Products extends Controller {

  def list = Action { implicit request =>

      val products = Product.findAll

      Ok(views.html.products.list(products))
  }

  def show(ean: Long) = Action { implicit request =>

    Product.findByEan(ean).map { product =>
      Ok(views.html.products.details(product))
    }.getOrElse(NotFound)

  }
}
