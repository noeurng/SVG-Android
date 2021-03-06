package com.github.megatronking.svg.plugin.task

import com.github.megatronking.svg.plugin.SVG2VectorExtension
import com.github.megatronking.svg.plugin.SVGExtension
import org.gradle.api.DefaultTask

public class SVGBaseTask extends DefaultTask {

    def SVGExtension configuration
    def SVG2VectorExtension[] svg2vectorConfigurations;

    public void run() {
        configuration = project.svg
        if (configuration.javaDir) {
            configuration.javaDir = resolveProjectDir(configuration.javaDir)
        }
        if (configuration.shapeDir) {
            configuration.shapeDir = resolveProjectDir(configuration.shapeDir)
        }
        if (configuration.vectorDirs) {
            def vectorDirs = []
            configuration.vectorDirs.each { vectorDir->
                vectorDir = resolveProjectDir(vectorDir);
                vectorDirs.add(vectorDir)
            }
            configuration.vectorDirs = vectorDirs
        }

        svg2vectorConfigurations = project.extensions.svg2vector
        if (svg2vectorConfigurations) {
            svg2vectorConfigurations.each { svg2vectorConfiguration->
                if(!configuration.vectorDirs.contains(svg2vectorConfiguration.vectorDir)) {
                    configuration.vectorDirs.add(svg2vectorConfiguration.vectorDir)
                }
            }
        }
    }

    public def file(def filePath) {
        return new File(filePath)
    }

    public def file(def dir, def fileName) {
        return new File(dir, fileName)
    }

    public def resolveProjectDir(def dir) {
        def dirFile = file(dir)
        if (dir != null && !dirFile.exists() && !dir.startsWith(project.projectDir.absolutePath)) {
            dir = project.projectDir.absolutePath + File.separator + dir
        }
        return dir
    }
}