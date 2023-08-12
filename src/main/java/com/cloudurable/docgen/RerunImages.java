package com.cloudurable.docgen;

import com.cloudurable.docgen.util.MermaidUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class RerunImages {

    public static void main(String... args) {
        try {
            final File outputDir = new File(args.length == 1 ? args[0] : "./test/all/output");

            if (!outputDir.exists()) {
                return;
            }
            runImageGenCheck(outputDir);
        }catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }


    }

    public static void runImageGenCheck(File outputDir) {
        final File mermaidOutputDir = new File(outputDir, "mermaid");
        final File imagesOutputDir = new File(outputDir, "images");
        imagesOutputDir.mkdirs();

        File[] imageFileArray = imagesOutputDir.listFiles();
        File[] mermaidFileArray = mermaidOutputDir.listFiles();

        if (mermaidFileArray == null) {
            System.out.println("No mermaid files yet");
            System.exit(2);
        }

        System.out.println("Total mermaid files " + mermaidFileArray.length);

        Set<String> mermaidFiles = Arrays.stream(mermaidFileArray).map(f -> f.getName()).map(name -> removeExt(name)).collect(Collectors.toSet());
        Set<String> imageFiles = imageFileArray == null ? Collections.emptySet() : Arrays.stream(imageFileArray).map(f -> f.getName()).map(name -> removeExt(name)).collect(Collectors.toSet());

        if (imageFiles.size() != mermaidFiles.size()) {
            System.out.println("Total mermaid files " + mermaidFiles.size());
            System.out.println("Sizes not equal " + (mermaidFiles.size() - imageFiles.size()));
            mermaidFiles.removeAll(imageFiles);
            System.out.println("Missing images ");
            mermaidFiles.forEach(f ->
                    System.out.println(new File(imagesOutputDir, f + ".png"))
            );

            mermaidFiles.forEach(f ->
                    reRun(new File(mermaidOutputDir, f + ".mmd"), new File(imagesOutputDir, f + ".png"))
            );
        }

    }

    private static void reRun(File input, File output) {
        MermaidUtils.runMmdc(input, output);
    }

    private static String removeExt(String name) {
        int i = name.indexOf('.');
        if (i != -1) {
            return name.substring(0, i);
        } else {
            return name;
        }
    }
}
