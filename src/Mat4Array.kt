package vision.gears.webglmath

import org.khronos.webgl.Float32Array
import org.khronos.webgl.get
import org.khronos.webgl.set
import org.khronos.webgl.WebGLRenderingContext
import org.khronos.webgl.WebGLUniformLocation
import kotlin.random.Random
import kotlin.math.pow


class Mat4Array(backingStorage: Float32Array?, startIndex: Int = 0, endIndex: Int = 0) : UniformFloat {

  constructor(size : Int) : this(null, 0, size) {}

  override val storage = backingStorage?.subarray(startIndex*16, endIndex*16)?:Float32Array((endIndex - startIndex)*16)

  override fun set(vararg values : Float) : Mat4Array {
    for(i in 0 until storage.length) {
      storage[i] = values.getOrNull(i) ?: if(i%16%5==0) 1.0f else 0.0f
    }
    return this
  }

  operator fun get(i : Int) : Mat4{
    return Mat4(storage, i*16)
  }

  override fun commit(gl : WebGLRenderingContext, uniformLocation : WebGLUniformLocation, samplerIndex : Int){
    gl.uniformMatrix4fv(uniformLocation, false, storage);
  }

}
