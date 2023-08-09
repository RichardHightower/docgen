package com.cloudurable.docgen.extract;
import java.io.File;
import java.util.concurrent.LinkedTransferQueue;


import java.util.stream.Collectors;

import static com.cloudurable.docgen.util.FileUtils.readFile;
import static com.cloudurable.docgen.util.FileUtils.writeFile;

public class ProjectCreator {

    private final File inputFile;
    private final File outputDir;

    private static final String DIR_MARKER = "## DIR ";
    private static final String FILE_MARKER = "## FILE ";

    public ProjectCreator(Builder builder) {
        this.inputFile = builder.inputFile;
        this.outputDir = builder.outputDir;
    }

    public static void main(String[] args) {
        try {

            final var inputFile = args.length > 0 ? new File(args[0]) : new File("./target/output.md");
            final var outputDir = args.length > 1 ? new File(args[1]) : new File("target/project_out");

            ProjectCreator.builder().setInputFile(inputFile).setOutputDir(outputDir).build().createProject();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public  void createProject() {
        outputDir.mkdirs();
        final var contents = readFile(inputFile);
        final var lines = contents.lines().collect(Collectors.toCollection(LinkedTransferQueue::new));

        var line = lines.poll();
        while (line != null) {

            if (line.startsWith(DIR_MARKER)) {
                handleDir(outputDir, line.substring(DIR_MARKER.length() + 1));
            } else if (line.startsWith(FILE_MARKER)) {
                handleFile(outputDir, line.substring(FILE_MARKER.length() + 1), lines);
            }

            line = lines.poll();
        }
    }

    private static void handleFile(File outputDir, String fileName, LinkedTransferQueue<String> lines) {
        var line = lines.poll();
        while (line != null) {
            if (line.startsWith("```")) {
                line = lines.poll();
                break;
            }
            line = lines.poll();
        }

        var builder = new StringBuilder();
        while (line != null) {
            if (line.startsWith("```")) {
                break;
            }
            builder.append(line).append("\n");
            line = lines.poll();
        }

        File outputFile = new File(outputDir, fileName);
        writeFile(outputFile, builder.toString());
    }

    private static void handleDir(File outputDir, String dirStr) {
        final var dir = new File(outputDir, dirStr);
        dir.mkdirs();
    }


    public static Builder builder() {
        return new ProjectCreator.Builder();
    }
    public static class Builder {

        private  File inputFile;
        private  File outputDir;


        public Builder setInputFile(File inputFile) {
            this.inputFile = inputFile;
            return this;
        }

        public Builder setOutputDir(File outputDir) {
            this.outputDir = outputDir;
            return this;
        }

        public ProjectCreator build() {
            return new ProjectCreator(this);
        }

    }
}
