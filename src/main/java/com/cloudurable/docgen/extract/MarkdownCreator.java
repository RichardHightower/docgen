package com.cloudurable.docgen.extract;

import java.io.File;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

import static com.cloudurable.docgen.util.FileUtils.readFile;
import static com.cloudurable.docgen.util.FileUtils.writeFile;


public class MarkdownCreator {


    private final File rootDir;
    private final File outputFile;

    public MarkdownCreator(MarkdownCreator.Builder builder) {
        this.rootDir = builder.rootDir;
        this.outputFile = builder.outputFile;
    }

    public interface FileVisitor {
        void visitFile(File file);
        default void visitDir(File dir) {}
    }

    private  final Set<String> allowedExtensions= new HashSet<>(List.of("java", "md", "txt", "js", "json"));//, "xml", "md", "yaml", "yml",
            //"txt", "sh", "js", "json", "Jenkinsfile", "Dockerfile"));

    private  final Map<String, String> extToLang= Map.of("java", "java",
            "xml", "xml", "md", "markdown", "yaml", "yaml", "yml", "yaml",
            "txt", "", "sh", "sh", "json", "javascript", "js", "javascript");

    private final MarkdownCreator.FileVisitor visitor = new MarkdownCreator.FileVisitor() {

        private final StringBuilder builder = new StringBuilder();
        @Override
        public void visitFile(File file) {
            builder.append("\n## FILE ").append(file).append("\n\n");
            var fileType = extToLang.getOrDefault(extractExt(file.getName()), "");
            builder.append("```")
                    .append(fileType)
                    .append("\n")
                    .append(readFile(file))
                    .append("\n```\n");
        }

        @Override
        public void visitDir(File dir) {
            builder.append("\n## DIR ").append(dir).append("\n\n");
        }

        @Override
        public String toString() {
            return builder.toString();
        }
    };
    private  final Set<String> ignoreDirectories= new HashSet<>(List.of("target", "build"));

    public static void main(String[] args) {
        try {

            final var rootDir = args.length > 0 ? new File(args[0]) : new File(".");
            final var outputFile = args.length > 1 ? new File(args[1]) : new File("target/output.md");

            final var markdown =  MarkdownCreator.builder().setRootDir(rootDir).setOutputFile(outputFile).build();

            markdown.run();


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        walkDir(rootDir, visitor);
        writeFile(outputFile, visitor.toString());
    }


    private  String extractExt(String fileName) {
        int indexOfLastDot = fileName.lastIndexOf('.');
        if (indexOfLastDot == -1) {
            return fileName;
        }
        return fileName.substring(indexOfLastDot+1);
    }

    public void walkDir(File dir, FileVisitor fileVisitor)  {

        fileVisitor.visitDir(dir);

        var files = dir.listFiles(pathname -> {
            if (pathname.isDirectory() && !pathname.getName().startsWith(".") && !ignoreDirectories.contains(pathname.getName())) {
                walkDir(pathname, fileVisitor);
                return false;
            }
            final var ext = extractExt(pathname.getName());
            return allowedExtensions.contains(ext);
        });

        if (files!=null && files.length > 0) {
            Arrays.stream(files).forEach(fileVisitor::visitFile);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {

        private  File rootDir;
        private  File outputFile;

        public Builder setRootDir(File rootDir) {
            this.rootDir = rootDir;
            return this;
        }

        public Builder setOutputFile(File outputFile) {
            this.outputFile = outputFile;
            return this;
        }

        public MarkdownCreator build() {
            return new MarkdownCreator(this);
        }

    }
}
