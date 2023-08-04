import java.lang.Math.toRadians
import kotlin.math.tan

class Camera(
    lookFrom: Point,
    lookAt: Point,
    viewUp: Vector,
    verticalFoV: Double,
    aspectRatio: Double,
    aperture: Double,
    focusDistance: Double
) {
    private val viewportHeight = 2.0 * tan(toRadians(verticalFoV) / 2)
    private val viewportWidth = aspectRatio * viewportHeight

    private val w = (lookFrom - lookAt).unit()
    private val u = (viewUp cross w).unit()
    private val v = w cross u

    private val origin = lookFrom
    private val horizontal = focusDistance * viewportWidth * u
    private val vertical = focusDistance * viewportHeight * v
    private val lowerLeftCorner = origin - horizontal / 2.0 - vertical / 2.0 - focusDistance * w

    private val lensRadius = aperture / 2.0

    fun getRay(s: Double, t: Double): Ray {
        val rd = lensRadius * Vector.randomInUnitDisk()
        val offset = u * rd.x + v * rd.y

        return Ray(origin + offset, lowerLeftCorner + s * horizontal + t * vertical - origin - offset)
    }
}
