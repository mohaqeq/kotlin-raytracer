import kotlin.math.abs
import kotlin.math.min
import kotlin.math.sqrt
import kotlin.random.Random

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

    fun unit() = this / length()

    fun reflect(normal: Vector) = this - 2.0 * (this dot normal) * normal

    fun refract(normal: Vector, factor: Double): Vector {
        val cosTheta = min(-this dot normal, 1.0)
        val rPrep = factor * (this + cosTheta * normal)
        val rParallel = -sqrt(abs(1.0 - rPrep.lengthSquared())) * normal
        return rPrep + rParallel
    }

    fun nearZero() = abs(x) < 1e-8 && abs(y) < 1e-8 && abs(z) < 1e-8

    fun toVectorString() = "$x $y $z"

    fun toColorString(): String {
        val color = clamp(this, 0.0, 0.999)
        return "${(255.999 * color.x).toInt()} ${(255.999 * color.y).toInt()} ${(255.999 * color.z).toInt()}"
    }

    companion object {
        @JvmStatic
        fun randomInUnitDisk(): Vector {
            while (true) {
                val vector = Vector(Random.nextDouble(), Random.nextDouble(), 0.0)
                if (vector.lengthSquared() < 1) return vector
            }
        }

        @JvmStatic
        fun randomInHemisphere(normal: Vector): Vector {
            val inUnitSphere = randomInUnitSphere()
            return if (inUnitSphere dot normal > 0.0) inUnitSphere else -inUnitSphere
        }

        @JvmStatic
        fun randomUnitVector() = randomInUnitSphere().unit()

        @JvmStatic
        fun randomInUnitSphere(): Vector {
            while (true) {
                val vector = random(-1.0, 1.0)
                if (vector.lengthSquared() < 1) return vector
            }
        }

        @JvmStatic
        fun random(min: Double, max: Double) =
            Vector(Random.nextDouble(min, max), Random.nextDouble(min, max), Random.nextDouble(min, max))

        @JvmStatic
        fun random() =
            Vector(Random.nextDouble(), Random.nextDouble(), Random.nextDouble())

        @JvmStatic
        private fun clamp(x: Double, min: Double, max: Double) = if (x < min) min else if (x > max) max else x

        @JvmStatic
        private fun clamp(vector: Vector, min: Double, max: Double) =
            Vector(clamp(vector.x, min, max), clamp(vector.y, min, max), clamp(vector.z, min, max))
    }
}

operator fun Double.times(vector: Vector) = vector * this
