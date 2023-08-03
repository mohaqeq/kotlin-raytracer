import java.io.File
import kotlin.math.sqrt
import kotlin.random.Random

fun main() {
    // Image
    val aspectRatio = 16.0 / 9.0
    val imageWidth = 400
    val imageHeight = (imageWidth / aspectRatio).toInt()
    val fileWriter = File("image.ppm").printWriter()
    val pixelSamples = 100
    val maxDeath = 50

    // World
    val groundMaterial = Lambertian(Color(0.8, 0.8, 0.0))
    val centerMaterial = Dielectric(1.5)
    val leftMaterial = Metal(Color(0.8, 0.8, 0.8), 0.3)
    val rightMaterial = Metal(Color(0.8, 0.6, 0.2), 1.0)
    val world = listOf(
        Sphere(Point(0.0, 0.0, -1.0), 0.5, centerMaterial),
        Sphere(Point(1.1, 0.0, -1.0), 0.5, rightMaterial),
        Sphere(Point(-1.1, 0.0, -1.0), 0.5, leftMaterial),
        Sphere(Point(0.0, -100.5, -1.0), 100.0, groundMaterial)
    )

    // Camera
    val camera = Camera(aspectRatio)

    // Render
    fileWriter.use { writer ->
        writer.println("P3\n${imageWidth} ${imageHeight}\n256")
        for (j in imageHeight - 1 downTo 0) {
            print("\rScanlines remaining: $j  ")
            for (i in 0 until imageWidth) {
                val pixelColor = Color()
                for (s in 0 until pixelSamples) {
                    val u = (i.toDouble() + Random.nextDouble()) / (imageWidth - 1)
                    val v = (j.toDouble() + Random.nextDouble()) / (imageHeight - 1)
                    val ray = camera.getRay(u, v)
                    pixelColor += ray.color(world, maxDeath)
                }
                pixelColor /= pixelSamples.toDouble()
                val gammaCorrectedColor = Color(sqrt(pixelColor.x), sqrt(pixelColor.y), sqrt(pixelColor.z))
                writer.println(gammaCorrectedColor.toColorString())
            }
        }
    }
}
