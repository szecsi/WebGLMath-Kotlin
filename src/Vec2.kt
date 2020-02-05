package vision.gears.webglmath

import org.khronos.webgl.Float32Array
import org.khronos.webgl.get
import org.khronos.webgl.set
import org.khronos.webgl.WebGLRenderingContext
import org.khronos.webgl.WebGLUniformLocation
import kotlin.random.Random
import kotlin.math.sqrt

@Suppress("NOTHING_TO_INLINE")
class Vec2(backingStorage: Float32Array?, offset: Int = 0) : Uniform {

  constructor(u: Float = 0.0f, v: Float = 0.0f) : this(null, 0){
    storage[0] = u
    storage[1] = v
  }
  constructor(other : Vec1, v: Float = 0.0f) : this(other.storage[0], v ){}
  constructor(other : Vec2) : this(null, 0)  {
    storage.set(other.storage);
  }

  override val storage: Float32Array = backingStorage?.subarray(offset, offset+2)?:Float32Array(2)
  inline var x : Float
    get() = storage[0]
    set(value) { storage[0] = value }
  inline var y : Float
    get() = storage[1]
    set(value) { storage[1] = value }
  inline val xy0 : Vec3
    get() = Vec3(storage[0], storage[1], 0.0f)
  inline val xy00 : Vec4
    get() = Vec4(storage[0], storage[1], 0.0f, 0.0f)    
  inline val xy01 : Vec4
    get() = Vec4(storage[0], storage[1], 0.0f, 1.0f)

  inline fun clone() : Vec2 {
    return Vec2(this);
  }

  inline fun set(u: Float = 0.0f, v: Float = 0.0f) : Vec2 {
    storage[0] = u
    storage[1] = v
    return this 
  }

  override fun set(vararg values : Float) : Vec2 {
    storage[0] = values.getOrElse(0) {0.0f}
    storage[1] = values.getOrElse(1) {0.0f}
    return this 
  }

  companion object {
    val zeros = Vec2()
    val ones = Vec2(1.0f, 1.0f) 

    inline fun makeRandom(minVal: Vec2 = Vec2.zeros, maxVal: Vec2 = Vec2.ones) : Vec2 {
      return Vec2(
          Random.nextFloat() * (maxVal.storage[0] - minVal.storage[0]) + minVal.storage[0],
          Random.nextFloat() * (maxVal.storage[1] - minVal.storage[1]) + minVal.storage[1]
        )  
    }
    inline fun makeRandom(minVal: Float = 0.0f, maxVal: Float = 1.0f) : Vec2 {
      return Vec2.makeRandom(Vec2(minVal, minVal), Vec2(maxVal, maxVal))
    }
  }

  inline fun randomize(minVal: Vec2 = Vec2.zeros, maxVal: Vec2 = Vec2.ones){
    set(Vec2.makeRandom(minVal, maxVal))
  }
  inline fun randomize(minVal: Float = 0.0f, maxVal: Float = 1.0f){
    randomize(Vec2(minVal, minVal), Vec2(maxVal, maxVal))
  }

  inline fun clamp(minVal: Vec2 = Vec2.zeros, maxVal: Vec2 = Vec2.ones) : Vec2 {
    if(storage[0] < minVal.storage[0]){
      storage[0] = minVal.storage[0]
    }
    if(storage[1] < minVal.storage[1]){
      storage[1] = minVal.storage[1]
    }
    if(storage[0] > maxVal.storage[0]){
      storage[0] = maxVal.storage[0]
    }
    if(storage[1] > maxVal.storage[1]){
      storage[1] = maxVal.storage[1]
    }
    return this
  }

  operator inline fun unaryPlus() : Vec2 {
    return this
  }

  operator inline fun unaryMinus() : Vec2 {
    return Vec2(-storage[0], -storage[1])
  }

  operator inline fun times(scalar : Float) : Vec2 {
    return Vec2(
      storage[0] * scalar,
      storage[1] * scalar
      )
  }

  operator inline fun div(scalar : Float) : Vec2 {
    return Vec2(
      storage[0] / scalar,
      storage[1] / scalar      
      )
  }

  operator inline fun timesAssign(scalar : Float) {
    storage[0] *= scalar
    storage[1] *= scalar
  }

  operator inline fun divAssign(scalar : Float) {
    storage[0] /= scalar
    storage[1] /= scalar
  }

  operator inline fun plusAssign(other : Vec2) {
    storage[0] += other.storage[0]
    storage[1] += other.storage[1]
  }

  operator inline fun plus(other : Vec2) : Vec2 {
    return Vec2(
      storage[0] + other.storage[0],
      storage[1] + other.storage[1]
      )
  }

  operator inline fun minusAssign(other : Vec2) {
    storage[0] -= other.storage[0]
    storage[1] -= other.storage[1]
  }

  operator inline fun minus(other : Vec2) : Vec2 {
    return Vec2(
      storage[0] - other.storage[0],
      storage[1] - other.storage[1]
      )
  }

  operator inline fun timesAssign(other : Vec2) {
    storage[0] *= other.storage[0]
    storage[1] *= other.storage[1]
  }

  operator inline fun times(other : Vec2) : Vec2 {
    return Vec2(
      storage[0] * other.storage[0],
      storage[1] * other.storage[1]
      )
  }

  operator inline fun divAssign(other : Vec2) {
    storage[0] /= other.storage[0]
    storage[1] /= other.storage[1]
  }

  operator inline fun div(other : Vec2) : Vec2 {
    return Vec2(
      storage[0] / other.storage[0],
      storage[1] / other.storage[1]
      )
  }       

  inline fun lengthSquared() : Float {
    return storage[0] * storage[0] + storage[1] * storage[1]
  }

  inline fun length() : Float {
    return sqrt(lengthSquared());
  }

  inline fun normalize() : Vec2 {
    val l = this.length()
    storage[0] /= l
    storage[1] /= l
    return this
  }

  infix inline fun dot(other : Vec2) : Float {
    return (
     storage[0] * other.storage[0] +
     storage[1] * other.storage[1] )
  }

  override fun commit(gl : WebGLRenderingContext, uniformLocation : WebGLUniformLocation, samplerIndex : Int){
    gl.uniform2fv(uniformLocation, storage);
  }
}

@Suppress("NOTHING_TO_INLINE")
operator inline fun Float.times(v: Vec2) = Vec2(this * v.storage[0], this * v.storage[1])
@Suppress("NOTHING_TO_INLINE")
operator inline fun Float.div(v: Vec2) = Vec2(this / v.storage[0], this / v.storage[1])
