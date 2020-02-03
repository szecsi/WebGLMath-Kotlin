import org.khronos.webgl.Float32Array
import org.khronos.webgl.get
import org.khronos.webgl.set
import org.khronos.webgl.WebGLRenderingContext
import org.khronos.webgl.WebGLUniformLocation

interface Uniform {
	abstract val storage : Float32Array

	open fun set(other: Uniform) : Uniform {
		for(i : Int in 0 until storage.length) {
    		storage[i] = if(i < other.storage.length) other.storage[i] else 0.0f
    }
    return this
	}

	abstract fun set(vararg values : Float) : Uniform

	abstract fun commit(gl : WebGLRenderingContext, uniformLocation : WebGLUniformLocation, samplerIndex : Int = 0)
}
