import kotlin.math.sqrt

data class Sphere(val center: Point, val radius: Double, val material: Material) : Hittable {
    override fun hit(ray: Ray, tMin: Double, tMax: Double): CollisionResult {
        val oc = ray.origin - center
        val a = ray.direction.lengthSquared()
        val b = oc dot ray.direction
        val c = oc.lengthSquared() - radius * radius

        val discriminant = b * b - a * c
        if (discriminant < 0) return CollisionResult(false, null)
        val sqrtD = sqrt(discriminant)

        // Find the nearest root that lies in the acceptable range.
        var root = (-b - sqrtD) / a
        if (root < tMin || tMax < root) {
            root = (-b + sqrtD) / a
            if (root < tMin || tMax < root) return CollisionResult(false, null)
        }

        val point = ray.at(root)
        return CollisionResult(true, Collision(point, root, material, ray.direction, (point - center) / radius))
    }
}
