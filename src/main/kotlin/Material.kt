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

class Metal(private val color: Color) : Material {
    override fun Scatter(ray: Ray, hit: Collision): Scatter {
        val direction = ray.direction.unit().reflect(hit.normal)
        return Scatter(Ray(hit.point, direction), color, (direction dot hit.normal) > 0)
    }
}
