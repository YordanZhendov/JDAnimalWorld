package jdanimal.demo.util.impl;

import jdanimal.demo.util.FileUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class FileUtilImpl implements FileUtil {

    @Override
    public String readFileContent(String filePath) throws IOException {
        return Files.readAllLines(Path.of(filePath))
                .stream()
                .filter(x-> !x.isEmpty())
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public void write(String content, String filePath) throws IOException {
        Files.write(Paths.get(filePath), Collections.singleton(content), StandardCharsets.UTF_8);
    }
}
