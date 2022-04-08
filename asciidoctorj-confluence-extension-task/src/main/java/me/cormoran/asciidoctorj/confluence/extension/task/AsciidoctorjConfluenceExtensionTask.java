package me.cormoran.asciidoctorj.confluence.extension.task;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.asciidoctor.jruby.extension.spi.ExtensionRegistry;

public class AsciidoctorjConfluenceExtensionTask implements ExtensionRegistry {

    @Override
    public void register(Asciidoctor asciidoctor) {
        final JavaExtensionRegistry javaExtensionRegistry = asciidoctor.javaExtensionRegistry();
        javaExtensionRegistry.treeprocessor(TaskProcessor.class);
    }
}
