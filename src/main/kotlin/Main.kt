import java.io.File

fun main() {

    // Image
    val aspectRatio = 16.0 / 9.0
    val imageWidth = 400
    val imageHeight = (imageWidth / aspectRatio).toInt()
    val fileWriter = File("image.ppm").printWriter()

    // Camera
    val viewportHeight = 2.0
    val viewportWidth = aspectRatio * viewportHeight
    val focalLength = 1.0

    // Viewport
    val origin = Point(0.0, 0.0, 0.0)
    val horizontal = Vector(viewportWidth, 0.0, 0.0)
    val vertical = Vector(0.0, viewportHeight, 0.0)
    val lowerLeftCorner = origin - horizontal / 2.0 - vertical / 2.0 - Vector(0.0, 0.0, focalLength)

    //World
    val world = listOf(
        Sphere(Point(0.0, 0.0, -1.0), 0.5),
        Sphere(Point(0.0, -100.5, -1.0), 100.0)
    )

    // Render
    fileWriter.use { writer ->
        writer.println("P3\n${imageWidth} ${imageHeight}\n256")
        for (j in imageHeight - 1 downTo 0) {
            print("\rScanlines remaining: $j  ")
            for (i in 0 until imageWidth) {
                val u = i.toDouble() / (imageWidth - 1)
                val v = j.toDouble() / (imageHeight - 1)
                val r = Ray(origin, lowerLeftCorner + u * horizontal + v * vertical - origin)
                writer.println(r.color(world).toColorString())
            }
        }
    }
}

fun Ray.color(world: Iterable<Hittable>): Color {
    val hit = world.hit(this, 0.0, Double.MAX_VALUE)
    if (hit.first) {
        val normal = hit.second!!.normal
        return 0.5 * (normal + Color(1.0, 1.0, 1.0))
    }
    val blendFactor = 0.5 * (direction.unit().y + 1.0)
    return (1 - blendFactor) * Color(1.0, 1.0, 1.0) + blendFactor * Color(0.5, 0.7, 1.0)
}
