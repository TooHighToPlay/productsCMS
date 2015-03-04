package controllers

import play.api.mvc.{Action, Controller}

/**
 * Created by TooHighToPlay [github.com/TooHighToPlay] on 04/03/15.
 */
object Barcodes extends Controller {

  val ImageResolution = 144

  def ean13BarCode(ean: Long, mimeType: String) : Array[Byte] = {

    import org.krysalis.barcode4j.impl.upcean.EAN13Bean
    import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider
    import java.io.ByteArrayOutputStream
    import java.awt.image.BufferedImage

    val output: ByteArrayOutputStream = new ByteArrayOutputStream
    val canvas: BitmapCanvasProvider = new BitmapCanvasProvider(
      output, mimeType, ImageResolution, BufferedImage.TYPE_BYTE_BINARY, false, 0)

    val barcode = new EAN13Bean()
    barcode.generateBarcode(canvas, String valueOf ean)
    canvas.finish()

    output.toByteArray
  }

  def barcode(ean: Long) = Action {

    val MimeType = "image/png"
    try {
      val imageData = ean13BarCode(ean, MimeType)
      Ok(imageData).as(MimeType)
    }
    catch {
      case e: IllegalArgumentException =>
        BadRequest("Couldn't generate bar code. Error: " + e.getMessage)
    }
  }
}
