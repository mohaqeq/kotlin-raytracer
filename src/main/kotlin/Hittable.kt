class Hit(val point: Point, val normal: Vector, val t: Double, val front: Boolean) {

    companion object {
        @JvmStatic
        fun build(point: Point, t: Double, rayDirection: Vector, outNormal: Vector): Hit {
            val front = (rayDirection dot outNormal) < 0
            return Hit(point, if (front) outNormal else -outNormal, t, front)
        }
    }
}

interface Hittable {

    fun hit(ray: Ray, tMin: Double, tMax: Double): Pair<Boolean, Hit?>
}

fun Iterable<Hittable>.hit(ray: Ray, tMin: Double, tMax: Double): Pair<Boolean, Hit?> {
    var hitAnything = false
    var closest = tMax
    var hit: Hit? = null

    for (item in this) {
        val itemHit = item.hit(ray, tMin, closest)
        if (itemHit.first) {
            hitAnything = true
            closest = itemHit.second!!.t
            hit = itemHit.second
        }
    }

    return Pair(hitAnything, hit)
}