package me.cormoran.asciidoc.confluence.extension.jira;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.asciidoctor.jruby.extension.spi.ExtensionRegistry;

public class AsciidocConfluenceExtensionJira implements ExtensionRegistry {

    @Override
    public void register(Asciidoctor asciidoctor) {
        final JavaExtensionRegistry javaExtensionRegistry = asciidoctor.javaExtensionRegistry();
        javaExtensionRegistry.inlineMacro(JiraInlineMacroProcessor.class);
    }
}
