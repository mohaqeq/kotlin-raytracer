data class Ray(val origin: Point, val direction: Vector) {
    fun at(t: Double) = origin + t * direction

    fun color(world: Iterable<Hittable>, depth: Int): Color {
        if (depth <= 0)
            return Color()

        val collisionResult = world.hit(this, 0.001, Double.MAX_VALUE)
        if (collisionResult.collided) {
            val scatter = collisionResult.collision!!.material.Scatter(this, collisionResult.collision!!)
            return if (scatter.collided)
                scatter.color * scatter.ray.color(world, depth - 1)
            else
                Color()
        }

        val blendFactor = 0.5 * (direction.unit().y + 1.0)
        return (1 - blendFactor) * Color(1.0, 1.0, 1.0) + blendFactor * Color(0.5, 0.7, 1.0)
    }
}
