data class Ray(val origin: Point, val direction: Vector) {
    fun at(t: Double) = origin + t * direction
}
