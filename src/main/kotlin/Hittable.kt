data class Hit(val point: Point, val normal: Vector, val t: Double)

interface Hittable {

    fun hit(ray: Ray, tMin: Double, tMax: Double): Pair<Boolean, Hit?>
}