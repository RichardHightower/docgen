package com.cloudurable.docgen.generators;

import com.cloudurable.docgen.TestUtil;
import org.junit.jupiter.api.Test;


import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class ClassDiagramByPackageGenTest {

    @Test
    public void testModel() {
        var diagram = doTest("org.example.model");
        System.out.println(diagram);
    }

    @Test
    public void testRepo() {
        var diagram = doTest("org.example.repo");
        System.out.println(diagram);
    }

    @Test
    public void testService() {
        var diagram = doTest("org.example.service");
        System.out.println(diagram);
    }

    public String doTest(final String packageName) {
        final var  gen = new ClassDiagramByPackageGen();
        final var javaItems = TestUtil.loadSimpleProject();

        final var classDefsPerPackage = TestUtil.mapPackageToClassDefs(javaItems);
        final var source = classDefsPerPackage.get(packageName);
        final var mermaidClassDiagram = gen.generateClassDiagramFromPackage(packageName, source,
                s -> {
                }, s -> {});
        assertNotNull(mermaidClassDiagram);
        assertFalse(mermaidClassDiagram.isBlank());

        return mermaidClassDiagram;
    }

}
