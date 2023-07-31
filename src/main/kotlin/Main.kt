fun main() {
    val imageWidth = 256
    val imageHeight = 256

    println("P3\n${imageWidth} ${imageHeight}\n256")
    for (j in imageHeight - 1 downTo 0) {
        System.err.print("\rScanline's remaining: $j ")
        for (i in 0 until imageWidth) {
            val r = i.toDouble() / (imageWidth - 1)
            val g = j.toDouble() / (imageHeight - 1)
            val b = 0.25

            val ir = (255.999 * r).toInt()
            val ig = (255.999 * g).toInt()
            val ib = (255.999 * b).toInt()

            println("$ir $ig $ib")
        }
    }
}