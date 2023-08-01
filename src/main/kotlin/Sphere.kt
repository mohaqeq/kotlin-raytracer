import kotlin.math.sqrt

data class Sphere(val center: Point, val radius: Double) : Hittable {

    override fun hit(ray: Ray, tMin: Double, tMax: Double): Pair<Boolean, Hit?> {
        val oc = ray.origin - center
        val a = ray.direction.lengthSquared()
        val b = oc dot ray.direction
        val c = oc.lengthSquared() - radius * radius

        val discriminant = b * b - a * c
        if (discriminant < 0) return Pair(false, null)
        val sqrtD = sqrt(discriminant)

        // Find the nearest root that lies in the acceptable range.
        var root = (-b - sqrtD) / a
        if (root < tMin || tMax < root) {
            root = (-b + sqrtD) / a
            if (root < tMin || tMax < root) return Pair(false, null)
        }

        val point = ray.at(root)
        return Pair(true, Hit(point, (point - center) / radius, root))
    }
}