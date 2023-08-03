data class Collision(val point: Point, val normal: Vector, val material: Material, val t: Double, val front: Boolean) {
    constructor(point: Point, t: Double, material: Material, rayDirection: Vector, outNormal: Vector) : this(
        point,
        if ((rayDirection dot outNormal) < 0) outNormal else -outNormal,
        material,
        t,
        (rayDirection dot outNormal) < 0
    )
}

data class CollisionResult(val collided: Boolean, val collision: Collision?)
