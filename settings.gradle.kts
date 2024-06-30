rootProject.name = "kfinance"

include(
    "stock:dart",
    "stock:quant",
    "stock:starter",
    "stock:yahoo",
    "util:calculator",
    "util:calendar",
    "util:converter",
    "util:model",
)

pluginManagement {
    val kotlinVersion: String by settings
    val ktlintVersion: String by settings

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.jetbrains.kotlin.jvm" -> useVersion(kotlinVersion)
                "org.jlleitschuh.gradle.ktlint" -> useVersion(ktlintVersion)
            }
        }
    }
}
