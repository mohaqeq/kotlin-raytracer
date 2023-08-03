import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt
import kotlin.math.pow
import kotlin.random.Random

data class Scatter(val ray: Ray, val color: Color, val collided: Boolean)

interface Material {
    fun Scatter(ray: Ray, collision: Collision): Scatter
}

class Lambertian(private val color: Color) : Material {
    override fun Scatter(ray: Ray, collision: Collision): Scatter {
        var scatterDirection = collision.normal + Vector.randomUnitVector()
        if (scatterDirection.nearZero()) {
            scatterDirection = collision.normal
        }
        return Scatter(Ray(collision.point, scatterDirection), color, true)
    }
}

class Metal(private val color: Color, fuzzy: Double = 0.0) : Material {
    private val fuzzy: Double

    init {
        this.fuzzy = max(0.0, min(1.0, fuzzy))
    }

    override fun Scatter(ray: Ray, collision: Collision): Scatter {
        val reflected = ray.direction.unit().reflect(collision.normal) + fuzzy * Vector.randomInUnitSphere()
        return Scatter(Ray(collision.point, reflected), color, (reflected dot collision.normal) > 0)
    }
}

class Dielectric(private val ir: Double) : Material {
    override fun Scatter(ray: Ray, collision: Collision): Scatter {
        val refractionRatio = if (collision.front) 1.0 / ir else ir
        val unitDirection = ray.direction.unit()

        val cosTheta = min(-unitDirection dot collision.normal, 1.0)
        val sinTheta: Double = sqrt(1.0 - cosTheta * cosTheta)

        val cannotRefract = refractionRatio * sinTheta > 1.0
        val direction = if (cannotRefract || reflectance(cosTheta, refractionRatio) > Random.nextDouble())
            unitDirection.reflect(collision.normal)
        else
            unitDirection.refract(collision.normal, refractionRatio)

        return Scatter(Ray(collision.point, direction), Color(1.0, 1.0, 1.0), true)
    }

    private fun reflectance(cosine: Double, refIdx: Double): Double {
        val r0 = (1 - refIdx) / (1 + refIdx).pow(2)
        return r0 + (1 - r0) * (1 - cosine).pow(5)
    }
}
