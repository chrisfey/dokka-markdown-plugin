package net.chrisfey.dokka.markdown

import org.jetbrains.dokka.plugability.ConfigurableBlock

data class Configuration(
    val jekyll: Boolean
) : ConfigurableBlock