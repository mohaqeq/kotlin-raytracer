fun main() {
    val imageWidth = 256
    val imageHeight = 256

    println("P3\n${imageWidth} ${imageHeight}\n256")
    for (j in imageHeight - 1 downTo 0) {
        System.err.print("\rScanline's remaining: $j ")
        System.err.flush()
        for (i in 0 until imageWidth) {
            val pixel = Color(i.toDouble() / (imageWidth - 1), j.toDouble() / (imageHeight - 1), 0.25)
            println(pixel.toColorString())
        }
    }
}