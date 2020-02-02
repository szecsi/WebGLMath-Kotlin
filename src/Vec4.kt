import org.khronos.webgl.Float32Array
import org.khronos.webgl.get
import org.khronos.webgl.set
import org.khronos.webgl.WebGLRenderingContext
import org.khronos.webgl.WebGLUniformLocation
import kotlin.random.Random
import kotlin.math.sqrt

@Suppress("NOTHING_TO_INLINE")
class Vec4(backingStorage: Float32Array?, offset: Int = 0) : Uniform {

  constructor(u: Float = 0.0f, v: Float = 0.0f, s: Float = 0.0f, t: Float = 1.0f) : this(null, 0){
    storage[0] = u
    storage[1] = v
    storage[2] = s
    storage[3] = t
  }
  constructor(other : Vec1, v: Float = 0.0f, s: Float = 0.0f, w: Float = 1.0f) : this(other.storage[0], v, s, w){}
  constructor(other : Vec2, s: Float = 0.0f, w: Float = 1.0f) : this(other.storage[0], other.storage[1], s, w){}
  constructor(other : Vec3, w: Float = 1.0f) : this(other.storage[0], other.storage[1], other.storage[2], w){}
  constructor(other : Vec4) : this(null, 0)  {
    storage.set(other.storage);
  }

  val storage: Float32Array = backingStorage?.subarray(offset, offset+4)?:Float32Array(4)
  inline var x : Float
    get() = storage[0]
    set(value) { storage[0] = value }
  inline var y : Float
    get() = storage[1]
    set(value) { storage[1] = value }
  inline var z : Float
    get() = storage[2]
    set(value) { storage[2] = value }
  inline var w : Float
    get() = storage[3]
    set(value) { storage[3] = value }    
  inline var xy : Vec2
    get() = Vec2(storage)
    set(value) { Vec2(storage).set(value) }
  inline var xyz : Vec3
    get() = Vec3(storage)
    set(value) { Vec3(storage).set(value) }

  inline fun clone() : Vec4 {
    return Vec4(this);
  }

  inline fun set(u: Float = 0.0f, v: Float = 0.0f, s: Float = 0.0f, w: Float = 1.0f) : Vec4 {
    storage[0] = u
    storage[1] = v
    storage[2] = s
    storage[3] = w
    return this 
  }

  inline fun set(other: Vec4) : Vec4 {
    other.storage[0] = storage[0]
    other.storage[1] = storage[1]
    other.storage[2] = storage[2]
    other.storage[3] = storage[3]    
    return this 
  }


  companion object {
    val zeros = Vec4(0.0f, 0.0f, 0.0f, 0.0f)
    val ones = Vec4(1.0f, 1.0f, 1.0f, 1.0f) 

    inline fun makeRandom(minVal: Vec4 = Vec4.zeros, maxVal: Vec4 = Vec4.ones) : Vec4 {
      return Vec4(
          Random.nextFloat() * (maxVal.storage[0] - minVal.storage[0]) + minVal.storage[0],
          Random.nextFloat() * (maxVal.storage[1] - minVal.storage[1]) + minVal.storage[1],
          Random.nextFloat() * (maxVal.storage[2] - minVal.storage[2]) + minVal.storage[2],                    
          Random.nextFloat() * (maxVal.storage[3] - minVal.storage[3]) + minVal.storage[3]
        )  
    }
    inline fun makeRandom(minVal: Float = 0.0f, maxVal: Float = 1.0f) : Vec4 {
      return Vec4.makeRandom(Vec4(minVal, minVal, minVal, minVal), Vec4(maxVal, maxVal, maxVal, maxVal))
    }
  }

  inline fun setRandom(minVal: Vec4 = Vec4.zeros, maxVal: Vec4 = Vec4.ones){
    set(Vec4.makeRandom(minVal, maxVal))
  }
  inline fun setRandom(minVal: Float = 0.0f, maxVal: Float = 1.0f){
    setRandom(Vec4(minVal, minVal, minVal, minVal), Vec4(maxVal, maxVal, maxVal, minVal))
  }

  inline fun clamp(minVal: Vec4 = Vec4.zeros, maxVal: Vec4 = Vec4.ones) : Vec4 {
    if(storage[0] < minVal.storage[0]){
      storage[0] = minVal.storage[0]
    }
    if(storage[1] < minVal.storage[1]){
      storage[1] = minVal.storage[1]
    }
    if(storage[2] < minVal.storage[2]){
      storage[2] = minVal.storage[2]
    }
    if(storage[3] < minVal.storage[3]){
      storage[3] = minVal.storage[3]
    }    
    if(storage[0] > maxVal.storage[0]){
      storage[0] = maxVal.storage[0]
    }
    if(storage[1] > maxVal.storage[1]){
      storage[1] = maxVal.storage[1]
    }
    if(storage[2] > maxVal.storage[2]){
      storage[2] = maxVal.storage[2]
    }        
    if(storage[3] > maxVal.storage[3]){
      storage[3] = maxVal.storage[3]
    }            
    return this
  }

  operator inline fun unaryPlus() : Vec4 {
    return this
  }

  operator inline fun unaryMinus() : Vec4 {
    return Vec4(-storage[0], -storage[1], -storage[2], -storage[3])
  }

  operator inline fun times(scalar : Float) : Vec4 {
    return Vec4(
      storage[0] * scalar,
      storage[1] * scalar,
      storage[2] * scalar,
      storage[3] * scalar
      )
  }

  operator inline fun div(scalar : Float) : Vec4 {
    return Vec4(
      storage[0] / scalar,
      storage[1] / scalar,
      storage[2] / scalar,
      storage[3] / scalar      
      )
  }

  operator inline fun timesAssign(scalar : Float) {
    storage[0] *= scalar
    storage[1] *= scalar
    storage[2] *= scalar    
    storage[3] *= scalar
  }

  operator inline fun divAssign(scalar : Float) {
    storage[0] /= scalar
    storage[1] /= scalar
    storage[2] /= scalar
    storage[3] /= scalar
  }  

  operator inline fun plusAssign(other : Vec4) {
    storage[0] += other.storage[0]
    storage[1] += other.storage[1]
    storage[2] += other.storage[2]
    storage[3] += other.storage[3]
  }

  operator inline fun plus(other : Vec4) : Vec4 {
    return Vec4(
      storage[0] + other.storage[0],
      storage[1] + other.storage[1],
      storage[2] + other.storage[2],
      storage[3] + other.storage[3]
      )
  }

  operator inline fun minusAssign(other : Vec4) {
    storage[0] -= other.storage[0]
    storage[1] -= other.storage[1]
    storage[2] -= other.storage[2]
    storage[3] -= other.storage[3]
  }

  operator inline fun minus(other : Vec4) : Vec4 {
    return Vec4(
      storage[0] - other.storage[0],
      storage[1] - other.storage[1],
      storage[2] - other.storage[2],
      storage[3] - other.storage[3]
      )
  }

  operator inline fun timesAssign(other : Vec4) {
    storage[0] *= other.storage[0]
    storage[1] *= other.storage[1]
    storage[2] *= other.storage[2]
    storage[3] *= other.storage[3]
  }

  operator inline fun times(other : Vec4) : Vec4 {
    return Vec4(
      storage[0] * other.storage[0],
      storage[1] * other.storage[1],
      storage[2] * other.storage[2],
      storage[3] * other.storage[3]
      )
  }

  operator inline fun divAssign(other : Vec4) {
    storage[0] /= other.storage[0]
    storage[1] /= other.storage[1]
    storage[2] /= other.storage[2]
    storage[3] /= other.storage[3]
  }

  operator inline fun div(other : Vec4) : Vec4 {
    return Vec4(
      storage[0] / other.storage[0],
      storage[1] / other.storage[1],
      storage[2] / other.storage[2],
      storage[3] / other.storage[3]
      )
  }       

  inline fun lengthSquared() : Float {
    return storage[0] * storage[0] + storage[1] * storage[1] + storage[2] * storage[2] + storage[3] * storage[3]
  }

  inline fun length() : Float {
    return sqrt(lengthSquared());
  }

  inline fun normalize() : Vec4 {
    val l = this.length()
    storage[0] /= l
    storage[1] /= l
    storage[2] /= l
    storage[3] /= l    
    return this
  }

  infix inline fun dot(other : Vec4) : Float {
    return (
      storage[0] * other.storage[0] +
      storage[1] * other.storage[1] +
      storage[2] * other.storage[2] +
      storage[3] * other.storage[3] )
  }

  inline fun commit(gl : WebGLRenderingContext, uniformLocation : WebGLUniformLocation){
    gl.uniform4fv(uniformLocation, storage);
  }
}

@Suppress("NOTHING_TO_INLINE")
operator inline fun Float.times(v: Vec4) = Vec4(this * v.storage[0], this * v.storage[1], this * v.storage[2], this * v.storage[3])
@Suppress("NOTHING_TO_INLINE")
operator inline fun Float.div(v: Vec4) = Vec4(this / v.storage[0], this / v.storage[1], this / v.storage[2], this / v.storage[3])