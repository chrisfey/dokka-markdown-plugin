package net.chrisfey.dokka.markdown

import org.jetbrains.dokka.CoreExtensions
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.gfm.GfmPlugin
import org.jetbrains.dokka.plugability.DokkaPlugin
import org.jetbrains.dokka.plugability.DokkaPluginApiPreview
import org.jetbrains.dokka.plugability.PluginApiPreviewAcknowledgement
import org.jetbrains.dokka.plugability.configuration

class DokkaMarkdownPlugin : DokkaPlugin() {

    private val gfmBase by lazy { plugin<GfmPlugin>() }
    private val dokkaBase by lazy { plugin<DokkaBase>() }


    val renderer by extending {
        CoreExtensions.renderer providing { ctx ->
            Renderer(ctx, configuration<DokkaMarkdownPlugin, Configuration>(ctx))
        } override dokkaBase.htmlRenderer override gfmBase.renderer
    }

    @DokkaPluginApiPreview
    override fun pluginApiPreviewAcknowledgement() = PluginApiPreviewAcknowledgement
}

