package net.chrisfey.dokka.markdown

import org.jetbrains.dokka.plugability.ConfigurableBlock

data class Configuration(
    val mode: Mode
) : ConfigurableBlock

sealed class Mode{
    object Gfm: Mode()
    object Jekyll: Mode()
}
