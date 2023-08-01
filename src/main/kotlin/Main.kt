fun main() {

    // Image
    val aspectRatio = 16.0 / 9.0
    val imageWidth = 400
    val imageHeight = (imageWidth / aspectRatio).toInt()

    // Camera
    val viewportHeight = 2.0
    val viewportWidth = aspectRatio * viewportHeight
    val focalLength = 1.0

    val origin = Point(0.0, 0.0, 0.0)
    val horizontal = Vector(viewportWidth, 0.0, 0.0)
    val vertical = Vector(0.0, viewportHeight, 0.0)
    val lowerLeftCorner = origin - horizontal / 2.0 - vertical / 2.0 - Vector(0.0, 0.0, focalLength)

    // Render
    println("P3\n${imageWidth} ${imageHeight}\n256")
    for (j in imageHeight - 1 downTo 0) {
        System.err.print("\rScanlines remaining: $j ")
        System.err.flush()
        for (i in 0 until imageWidth) {
            val u = i.toDouble() / (imageWidth - 1)
            val v = j.toDouble() / (imageHeight - 1)
            val r = Ray(origin, lowerLeftCorner + u * horizontal + v * vertical - origin)
            println(r.color().toColorString())
        }
    }
}

fun Ray.color(): Color {
    val vec = 0.5 * (direction.unit().y + 1.0)
    return (1 - vec) * Color(1.0, 1.0, 1.0) + vec * Color(0.5, 0.7, 1.0)
}
