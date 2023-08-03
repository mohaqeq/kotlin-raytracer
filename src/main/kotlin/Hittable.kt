interface Hittable {
    fun hit(ray: Ray, tMin: Double, tMax: Double): CollisionResult
}

fun Iterable<Hittable>.hit(ray: Ray, tMin: Double, tMax: Double): CollisionResult {
    var hitAnything = false
    var closest = tMax
    var collision: Collision? = null

    for (item in this) {
        val itemHit = item.hit(ray, tMin, closest)
        if (itemHit.collided) {
            hitAnything = true
            closest = itemHit.collision!!.t
            collision = itemHit.collision
        }
    }

    return CollisionResult(hitAnything, collision)
}
