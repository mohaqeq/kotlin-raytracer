data class Ray(val origin: Point, val direction: Vector) {
    fun at(t: Double) = origin + t * direction

    fun color(world: Iterable<Hittable>): Color {
        val hit = world.hit(this, 0.0, Double.MAX_VALUE)
        if (hit.first) {
            val normal = hit.second!!.normal
            return 0.5 * (normal + Color(1.0, 1.0, 1.0))
        }
        val blendFactor = 0.5 * (direction.unit().y + 1.0)
        return (1 - blendFactor) * Color(1.0, 1.0, 1.0) + blendFactor * Color(0.5, 0.7, 1.0)
    }
}
