interface Hittable {
    fun hit(ray: Ray, tMin: Double, tMax: Double): Pair<Boolean, Collision?>
}

fun Iterable<Hittable>.hit(ray: Ray, tMin: Double, tMax: Double): CollisionResult {
    var hitAnything = false
    var closest = tMax
    var hit: Collision? = null

    for (item in this) {
        val itemHit = item.hit(ray, tMin, closest)
        if (itemHit.first) {
            hitAnything = true
            closest = itemHit.second!!.t
            hit = itemHit.second
        }
    }

    return CollisionResult(hitAnything, hit)
}
