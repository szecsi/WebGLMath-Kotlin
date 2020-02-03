
abstract class Drawable {
  open fun gatherUniforms(target : UniformProvider){}
  abstract fun draw(vararg uniformProviders : UniformProvider)
  open fun drawWithOverrides(overrides : Map<String, UniformProvider>, vararg uniformProviders : UniformProvider){
    draw(*uniformProviders)
  }
}

open class UniformProvider(vararg val glslStructNames : String) : Drawable() {
  internal val uniforms = HashMap<String, Uniform>()
  operator fun get(name : String): Uniform? { return uniforms[name] }
  operator fun set(name : String, uniform : Uniform ) { (uniforms[name] ?: return).set(uniform) }
  operator fun set(name : String, value : Float ) { (uniforms[name] ?: return).set(value) }

  val components = HashSet<Drawable>()

  fun addComponentsAndGatherUniforms(vararg children : Drawable){
    components.addAll(children)
    gatherUniforms(this)
  }

  override fun gatherUniforms(target : UniformProvider){
    components.forEach{
      it.gatherUniforms(target)
    }
  }

  override fun draw(vararg uniformProviders : UniformProvider){
    components.forEach(){
      it.draw(this, *uniformProviders)
    }
  }

  /**
   * Recursively calls drawWithOverrides on subcomponents, but if there is
   * an object of matching type in overrides, drawWithOverrides is called
   * on it instead of the subcomponent.
   */
  override fun drawWithOverrides(overrides : Map<String, UniformProvider>, vararg uniformProviders : UniformProvider){
    components.forEach{
      (overrides[it::class.simpleName]?:it).drawWithOverrides(
        overrides,
        this,
        *uniformProviders
        )
    }
  }    

  fun using(vararg overriders : UniformProvider) : Drawable {
    val overrideMap = overriders.map{(it::class.simpleName?:"<no class name>") to it}.toMap()
    return object : Drawable() {
      override fun draw(vararg uniformProviders : UniformProvider){
        this@UniformProvider.drawWithOverrides(overrideMap, *uniformProviders)
      }
      override fun drawWithOverrides(overrides : Map<String, UniformProvider>, vararg uniformProviders : UniformProvider){
        val allOverrides : MutableMap<String, UniformProvider> = overrideMap.toMutableMap()
        overrides.forEach{ (key : String, value : Drawable) -> allOverrides[key] = value } 
        this@UniformProvider.drawWithOverrides(allOverrides, *uniformProviders)
      }
    }
  }
}

