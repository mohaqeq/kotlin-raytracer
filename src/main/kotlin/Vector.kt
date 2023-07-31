import kotlin.math.sqrt

typealias Point = Vector
typealias Color = Vector

data class Vector(var x: Double, var y: Double, var z: Double) {

    constructor() : this(0.0, 0.0, 0.0)

    operator fun Vector.plus(vector: Vector): Vector = Vector(x + vector.x, y + vector.y, z + vector.z)

    operator fun Vector.minus(vector: Vector): Vector = Vector(x - vector.x, y - vector.y, z - vector.z)

    operator fun Vector.times(vector: Vector): Vector = Vector(x * vector.x, y * vector.y, z * vector.z)

    operator fun Vector.times(times: Double): Vector = Vector(x * times, y * times, z * times)

    operator fun Vector.div(times: Double): Vector = Vector(x / times, y / times, z / times)

    operator fun Vector.plusAssign(vector: Vector) {
        this.x += vector.x
        this.y += vector.y
        this.z += vector.z
    }

    operator fun Vector.timesAssign(vector: Vector) {
        this.x *= vector.x
        this.y *= vector.y
        this.z *= vector.z
    }

    operator fun Vector.divAssign(factor: Double) {
        this.x /= factor
        this.y /= factor
        this.z /= factor
    }

    fun lengthSquared(): Double = x * x + y * y * z * z

    fun length(): Double = sqrt(lengthSquared())

    fun dot(vector: Vector): Double = x * vector.x + y * vector.y + z * vector.z

    fun cross(vector: Vector): Vector =
        Vector(y * vector.z - z * vector.y, z * vector.x - x * vector.z, x * vector.y - y * vector.x)

    fun unit(): Vector = this / length()
}