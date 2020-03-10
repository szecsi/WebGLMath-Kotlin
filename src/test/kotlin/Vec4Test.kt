import vision.gears.webglmath.Vec4
import kotlin.math.abs
import kotlin.test.Test
import kotlin.test.assertTrue

class Vec4Test {
  val eps : Float = 0.001f
  fun equalsVec4(a : Vec4, b : Vec4) : Boolean {
    return when {
      abs(a.x - b.x) > eps -> false
      abs(a.y - b.y) > eps -> false
      abs(a.z - b.z) > eps -> false
      abs(a.w - b.w) > eps -> false
      else -> true
    }
  }

  @Test
  fun testAddition() {
    val zeros = Vec4.zeros
    val ones = Vec4(1.0f, 1.0f, 1.0f, 1.0f)
    val ones2 = Vec4.ones
    val twos = Vec4(2.0f, 2.0f, 2.0f, 2.0f)
    val seriesFromZero = Vec4(0.0f, 1.0f, 2.0f, 3.0f)
    val seriesFromOne = Vec4(1.0f, 2.0f, 3.0f, 4.0f)

    assertTrue ( equalsVec4(seriesFromZero, seriesFromZero) )
    assertTrue ( equalsVec4(seriesFromZero, seriesFromZero + zeros) )
    assertTrue ( equalsVec4(seriesFromOne, seriesFromZero + ones) )

    assertTrue ( equalsVec4(twos, ones + ones2) )
    assertTrue ( equalsVec4(twos, ones + ones) )
    assertTrue ( equalsVec4(twos, ones2 + ones2) )
  }

  @Test
  fun testMultiplication() {
    val zeros = Vec4.zeros
    val ones = Vec4(1.0f, 1.0f, 1.0f, 1.0f)
    val ones2 = Vec4.ones
    val twos = Vec4(2.0f, 2.0f, 2.0f, 2.0f)
    val seriesFromZero = Vec4(0.0f, 1.0f, 2.0f, 3.0f)
    val seriesFromOne = Vec4(1.0f, 2.0f, 3.0f, 4.0f)

    assertTrue { equalsVec4(seriesFromZero, seriesFromZero) }
    assertTrue { equalsVec4(zeros, seriesFromZero * zeros) }
    assertTrue { equalsVec4(seriesFromOne, seriesFromOne * ones) }

    assertTrue { equalsVec4(ones, ones * ones2) }
    assertTrue { equalsVec4(ones, ones * ones) }
    assertTrue { equalsVec4(ones, ones2 * ones2) }
    assertTrue { equalsVec4(twos, twos *ones2) }
  }


}