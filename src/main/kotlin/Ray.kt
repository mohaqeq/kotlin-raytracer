data class Ray(val origin: Point, val direction: Vector) {
    fun at(t: Double) = origin + t * direction

    fun color(world: Iterable<Hittable>, depth: Int): Color {
        if (depth <= 0)
            return Color()

        val hit = world.hit(this, 0.0, Double.MAX_VALUE)
        if (hit.first) {
            val target = hit.second!!.point + hit.second!!.normal + Vector.randomInUnitSphere()
            return 0.5 * Ray(hit.second!!.point, target - hit.second!!.point).color(world, depth - 1)
        }

        val blendFactor = 0.5 * (direction.unit().y + 1.0)
        return (1 - blendFactor) * Color(1.0, 1.0, 1.0) + blendFactor * Color(0.5, 0.7, 1.0)
    }
}
