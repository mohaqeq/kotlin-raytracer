import kotlin.math.sqrt

typealias Point = Vector
typealias Color = Vector

data class Vector(var x: Double, var y: Double, var z: Double) {
    constructor() : this(0.0, 0.0, 0.0)

    operator fun unaryMinus() = Vector(-x, -y, -z)

    operator fun plus(vector: Vector) = Vector(x + vector.x, y + vector.y, z + vector.z)

    operator fun minus(vector: Vector) = Vector(x - vector.x, y - vector.y, z - vector.z)

    operator fun times(vector: Vector) = Vector(x * vector.x, y * vector.y, z * vector.z)

    operator fun times(times: Double) = Vector(x * times, y * times, z * times)

    operator fun div(times: Double) = Vector(x / times, y / times, z / times)

    operator fun plusAssign(vector: Vector) {
        this.x += vector.x
        this.y += vector.y
        this.z += vector.z
    }

    operator fun timesAssign(vector: Vector) {
        this.x *= vector.x
        this.y *= vector.y
        this.z *= vector.z
    }

    operator fun divAssign(factor: Double) {
        this.x /= factor
        this.y /= factor
        this.z /= factor
    }

    infix fun dot(vector: Vector) = x * vector.x + y * vector.y + z * vector.z

    infix fun cross(vector: Vector) =
        Vector(y * vector.z - z * vector.y, z * vector.x - x * vector.z, x * vector.y - y * vector.x)

    fun lengthSquared() = x * x + y * y + z * z

    fun length() = sqrt(lengthSquared())

    fun unit(): Vector = this / length()

    fun toVectorString() = "$x $y $z"

    fun toColorString(): String {
        val color = clamp(this, 0.0, 0.999)
        return "${(255.999 * color.x).toInt()} ${(255.999 * color.y).toInt()} ${(255.999 * color.z).toInt()}"
    }

    private fun clamp(x: Double, min: Double, max: Double) = if (x < min) min else if (x > max) max else x

    private fun clamp(vector: Vector, min: Double, max: Double) =
        Vector(clamp(vector.x, min, max), clamp(vector.y, min, max), clamp(vector.z, min, max))
}

operator fun Double.times(vector: Vector) = vector * this
