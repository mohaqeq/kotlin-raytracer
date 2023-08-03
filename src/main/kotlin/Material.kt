import kotlin.math.max
import kotlin.math.min

data class Scatter(val ray: Ray, val color: Color, val collided: Boolean)

interface Material {
    fun Scatter(ray: Ray, hit: Collision): Scatter
}

class Lambertian(private val color: Color) : Material {
    override fun Scatter(ray: Ray, hit: Collision): Scatter {
        var scatterDirection = hit.normal + Vector.randomUnitVector()
        if (scatterDirection.nearZero()) {
            scatterDirection = hit.normal
        }
        return Scatter(Ray(hit.point, scatterDirection), color, true)
    }
}

class Metal(private val color: Color, fuzzy: Double = 0.0) : Material {
    private val fuzzy: Double

    init {
        this.fuzzy = max(0.0, min(1.0, fuzzy))
    }

    override fun Scatter(ray: Ray, hit: Collision): Scatter {
        val reflected = ray.direction.unit().reflect(hit.normal) + fuzzy * Vector.randomInUnitSphere()
        return Scatter(Ray(hit.point, reflected), color, (reflected dot hit.normal) > 0)
    }
}

class Dielectric(private val ir: Double) : Material {
    override fun Scatter(ray: Ray, hit: Collision): Scatter {
        val refractionRatio = if (hit.front) 1.0 / ir else ir
        val unitDirection = ray.direction.unit()
        val refracted = unitDirection.refract(hit.normal, refractionRatio)
        return Scatter(Ray(hit.point, refracted), Color(1.0, 1.0, 1.0), true)
    }
}
