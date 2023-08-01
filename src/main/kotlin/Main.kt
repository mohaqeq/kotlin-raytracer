import kotlin.math.sqrt

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
        System.err.print("\rScanlines remaining: $j  ")
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
    val center = Point(0.0, 0.0, -1.0)
    val hitValue = hitSphere(center, 0.5)
    if (hitValue > 0.0) {
        val surfaceNormal = at(hitValue) - center
        return 0.5 * Color(surfaceNormal.x * 1.0, surfaceNormal.y + 1.0, surfaceNormal.z + 1.0)
    }
    val blendFactor = 0.5 * (direction.unit().y + 1.0)
    return (1 - blendFactor) * Color(1.0, 1.0, 1.0) + blendFactor * Color(0.5, 0.7, 1.0)
}

fun Ray.hitSphere(center: Point, radius: Double): Double {
    val oc = origin - center
    val a = direction.lengthSquared()
    val b = oc dot direction
    val c = oc.lengthSquared() - radius * radius
    val discriminant = b * b - a * c
    return if (discriminant < 0) {
        -1.0
    } else {
        (-b - sqrt(discriminant)) / a
    }
}
