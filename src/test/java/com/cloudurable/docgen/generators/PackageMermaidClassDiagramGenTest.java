package com.cloudurable.docgen.generators;

import com.cloudurable.docgen.TestUtil;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class PackageMermaidClassDiagramGenTest {

    @Test
    public void test() {
        final var  gen = new PackageMermaidClassDiagramGen();
        final var packageName  ="org.example.model";
        final var javaItems = TestUtil.loadSimpleProject();

        final var classDefsPerPackage = TestUtil.mapPackageToClassDefs(javaItems);
        final var source = classDefsPerPackage.get(packageName);
        System.out.println(source);
        final var mermaidClassDiagram = gen.generateClassDiagramFromPackage(packageName, source);
        assertNotNull(mermaidClassDiagram);
        assertFalse(mermaidClassDiagram.isBlank());
        System.out.println(mermaidClassDiagram);

    }

}
