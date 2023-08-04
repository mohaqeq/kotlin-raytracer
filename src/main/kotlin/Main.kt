import java.io.File
import java.util.Collections
import java.util.concurrent.Executors
import java.util.concurrent.Future
import kotlin.math.sqrt
import kotlin.random.Random

fun main() {
    // Image
    val aspectRatio = 3.0 / 2.0
    val imageWidth = 1200
    val imageHeight = (imageWidth / aspectRatio).toInt()
    val fileWriter = File("image.ppm").printWriter()
    val pixelSamples = 500
    val maxDeath = 50

    // World
    val world = randomScene()

    // Camera
    val lookFrom = Point(13.0, 2.0, 3.0)
    val lookAt = Point(0.0, 0.0, 0.0)
    val camera = Camera(
        lookFrom, lookAt, Vector(0.0, 1.0, 0.0), 25.0, aspectRatio, 0.1, 10.0
    )

    // Render
    val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
    fileWriter.use { writer ->
        writer.println("P3\n${imageWidth} ${imageHeight}\n256")
        for (j in imageHeight - 1 downTo 0) {
            print("\rScanlines remaining: $j  ")
            for (i in 0 until imageWidth) {
                val pixels = Collections.synchronizedList(mutableListOf<Color>())
                val result = mutableListOf<Future<*>>()
                for (s in 0 until pixelSamples) {
                    result.add(executor.submit {
                        val u = (i.toDouble() + Random.nextDouble()) / (imageWidth - 1)
                        val v = (j.toDouble() + Random.nextDouble()) / (imageHeight - 1)
                        val ray = camera.getRay(u, v)
                        pixels.add(ray.color(world, maxDeath))
                    })
                }
                result.map { it.get() }
                val pixelColor = pixels.reduce { x, y -> x + y } / pixelSamples.toDouble()
                val gammaCorrectedColor = Color(sqrt(pixelColor.x), sqrt(pixelColor.y), sqrt(pixelColor.z))
                writer.println(gammaCorrectedColor.toColorString())
            }
        }
    }
}

fun randomScene(): Iterable<Hittable> {
    val world = mutableListOf<Hittable>()

    val groundMaterial = Lambertian(Color(0.5, 0.5, 0.5))
    world.add(Sphere(Point(0.0, -1000.0, 0.0), 1000.0, groundMaterial))

    for (a: Int in -11 until 11) {
        for (b: Int in -11 until 11) {
            val chooseMat = Random.nextDouble()
            val center = Point(a + 0.9 * Random.nextDouble(), 0.2, b + 0.9 * Random.nextDouble())

            if ((center - Point(4.0, 0.2, 0.0)).length() > 0.9) {
                val sphereMaterial: Material

                if (chooseMat < 0.55) {
                    // diffuse
                    val albedo = Color.random() * Color.random()
                    sphereMaterial = Lambertian(albedo)
                    world.add(Sphere(center, 0.2, sphereMaterial))
                } else if (chooseMat < 0.80) {
                    // metal
                    val albedo = Color.random(0.5, 1.0)
                    val fuzz = Random.nextDouble(0.0, 0.5)
                    sphereMaterial = Metal(albedo, fuzz)
                    world.add(Sphere(center, 0.2, sphereMaterial))
                } else {
                    // glass
                    sphereMaterial = Dielectric(1.5, Color.random(0.7, 1.0))
                    world.add(Sphere(center, 0.2, sphereMaterial))
                }
            }
        }
    }

    val material1 = Dielectric(1.5)
    world.add(Sphere(Point(0.0, 1.0, 0.0), 1.0, material1))

    val material2 = Lambertian(Color(0.4, 0.2, 0.1))
    world.add(Sphere(Point(-4.0, 1.0, 0.0), 1.0, material2))

    val material3 = Metal(Color(0.7, 0.6, 0.5), 0.0)
    world.add(Sphere(Point(4.0, 1.0, 0.0), 1.0, material3))

    return world
}
