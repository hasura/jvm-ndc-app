package io.hasura

import io.hasura.ndc.app.interfaces.ConfigHandler
import io.hasura.ndc.app.services.StateContainer
import io.quarkus.runtime.Quarkus
import io.quarkus.runtime.QuarkusApplication
import io.quarkus.runtime.annotations.QuarkusMain


@QuarkusMain
object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Quarkus.run(MyApp::class.java, *args)
    }

    class MyApp (
        private val configHandler: ConfigHandler,
    ) : QuarkusApplication {

        override fun run(vararg args: String): Int {
            if(args.size != 2 || args[0] != "--configuration") {
                exitWithHelp()
            }

            try {
                val configParts = configHandler.parseConfig(args[1])
                StateContainer.setState(configParts)
            } catch (e:Exception) {
                e.printStackTrace()
                exitWithHelp()
            }

            Quarkus.waitForExit()
            return 0
        }

        private fun exitWithHelp() {
            println("""
                    Usage:
                        --configuration <path to config>
                """.trimIndent())
            System.exit(0)
        }
    }


}
