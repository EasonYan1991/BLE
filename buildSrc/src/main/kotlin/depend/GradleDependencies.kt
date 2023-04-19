@file:JvmName("GradleDependencies")

package depend

import groovy.lang.Closure
import org.gradle.api.Action
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.plugins.ObjectConfigurationAction
import org.gradle.api.plugins.PluginAware
import org.gradle.api.plugins.PluginContainer
import org.gradle.api.plugins.PluginManager
import kotlin.reflect.KProperty

object GradleDependencies: PluginAware, ExtensionAware {
    operator fun getValue(nothing: Nothing?, property: KProperty<*>): String {
        return "7.4.2"
    }

    const val version = "7.4.2"
    override fun getPlugins(): PluginContainer {
        TODO("Not yet implemented")
    }

    override fun apply(closure: Closure<*>) {
        TODO("Not yet implemented")
    }

    override fun apply(action: Action<in ObjectConfigurationAction>) {
        TODO("Not yet implemented")
    }

    override fun apply(options: MutableMap<String, *>) {
        TODO("Not yet implemented")
    }

    override fun getPluginManager(): PluginManager {
        TODO("Not yet implemented")
    }

    override fun getExtensions(): ExtensionContainer {
        TODO("Not yet implemented")
    }
}
