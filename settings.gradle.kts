pluginManagement {
    val quarkusPluginVersion: String by settings
    val quarkusPluginId: String by settings
    plugins {
        id(quarkusPluginId) version quarkusPluginVersion
    }
}

rootProject.name = "ndc-app"
