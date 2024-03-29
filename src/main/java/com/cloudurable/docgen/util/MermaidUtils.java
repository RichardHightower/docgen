package com.cloudurable.docgen.util;

import com.cloudurable.docgen.Result;
import com.cloudurable.docgen.mermaid.validation.ContentRule;
import com.cloudurable.docgen.mermaid.validation.RuleResult;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class MermaidUtils {


    public static Result runMmdc(File input, File output) {
        String command = "/opt/homebrew/bin/mmdc -i " + input.toString() + " -o " + output + " -s 2 -b white";
        //String command = "/Users/richardhightower/.nvm/versions/node/v6.17.1/bin/mmdc -i " + input.toString() + " -o " + output + " -s 2 -b white";
        //docker run --rm -u `id -u`:`id -g` -v /path/to/diagrams:/data minlag/mermaid-cli
        //String command = "docker run --rm -u 501:20 -v " + input.getParentFile().getParentFile() + ":/data minlag/mermaid-cli -i "
        //        + "./mermaid/" + input.getName() + " -o ./images/" + output.getName() + " -s 2 -b white";
        System.out.println(command);
        ExecutorService executorService = Executors.newCachedThreadPool();

        final AtomicReference<StringBuilder> outputRef = new AtomicReference<>(new StringBuilder());
        final AtomicReference<StringBuilder> errorRef = new AtomicReference<>(new StringBuilder());
        final CountDownLatch latch = new CountDownLatch(2);

        try {
            System.out.println(command);
            Process process = Runtime.getRuntime().exec(command);


            executorService.submit(() -> {
                StringBuilder outputBuilder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while (true) {
                    try {
                        if (!((line = reader.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new IllegalStateException(e);
                    }
                    outputBuilder.append(line).append("\n");
                    System.out.println(line);
                }
                outputRef.set(outputBuilder);
                latch.countDown();
            });

            executorService.submit(() -> {
                StringBuilder outputBuilder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String line;
                while (true) {
                    try {
                        if (!((line = reader.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new IllegalStateException(e);
                    }
                    outputBuilder.append(line).append("\n");
                    System.err.println(line);
                }
                errorRef.set(outputBuilder);
                latch.countDown();
            });

            int exitCode = process.waitFor();

            if (latch.await(30, TimeUnit.SECONDS)) {

            } else {
                System.err.println("TIMEOUT");
            }

            executorService.shutdown();
            if (exitCode != 0) {
                System.err.printf("CAN'T PROCESS DIAGRAM %s", FileUtils.readFile(input));
            }
            return new Result(exitCode, outputRef.get().toString(), errorRef.get().toString(), null, true);
        } catch (IOException | InterruptedException e) {
            return new Result(-1, outputRef.get().toString(), errorRef.get().toString(), e, false);
        }
    }


    public static ContentRule createRule() {
        return content -> {
            if (content.isBlank()) {
                return RuleResult.EMPTY_CONTENT;
            }
            final Result validate = validate(content);

            if (validate.getResult() == 0 && validate.getException() == null) {
                return RuleResult.SUCCESS;
            } else {

                final var errorParts = validate.getErrors().split(" at ");
                final var errors = errorParts.length > 1 ? errorParts[0] : validate.getErrors();
                System.err.println(content + "\n\n\n");
                return RuleResult.builder().description(
                                "mermaid image generator failed \n" + errors
                        ).lineNumber(0).violatedLine("")
                        .ruleName("MermaidImageGen")
                        .build();
            }
        };
    }

    public static Result validate(String mermaidContent) {

        try {

            final var outputDir = new File("./tmpgen");
            outputDir.mkdirs();
            final var input = new File("mermaid.mmd");
            final var output = new File("mermaid.png");
            FileUtils.writeFile(input, mermaidContent);

            String command = "/opt/homebrew/bin/mmdc -i " + input.toString() + " -o " + output + " -s 2 -b white";

            ExecutorService executorService = Executors.newCachedThreadPool();

            final AtomicReference<StringBuilder> outputRef = new AtomicReference<>(new StringBuilder());
            final AtomicReference<StringBuilder> errorRef = new AtomicReference<>(new StringBuilder());
            final CountDownLatch latch = new CountDownLatch(2);

            try {
                System.out.println(command);
                Process process = Runtime.getRuntime().exec(command);


                executorService.submit(() -> {
                    StringBuilder outputBuilder = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while (true) {
                        try {
                            if (!((line = reader.readLine()) != null)) break;
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new IllegalStateException(e);
                        }
                        outputBuilder.append(line).append("\n");
                        System.out.println(line);
                    }
                    outputRef.set(outputBuilder);
                    latch.countDown();
                });

                executorService.submit(() -> {
                    StringBuilder outputBuilder = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                    String line;
                    while (true) {
                        try {
                            if (!((line = reader.readLine()) != null)) break;
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new IllegalStateException(e);
                        }
                        outputBuilder.append(line).append("\n");
                        System.err.println(line);
                    }
                    errorRef.set(outputBuilder);
                    latch.countDown();
                });

                int exitCode = process.waitFor();

                if (latch.await(30, TimeUnit.SECONDS)) {
                    executorService.shutdown();
                    return new Result(exitCode, outputRef.get().toString(), errorRef.get().toString(), null, true);
                } else {
                    executorService.shutdown();
                    return new Result(exitCode, outputRef.get().toString(), errorRef.get().toString(), null, false);
                }

            } catch (IOException | InterruptedException e) {
                return new Result(-1, outputRef.get().toString(), errorRef.get().toString(), e, false);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
