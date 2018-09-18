/*
 * Copyright 2018 Benedikt Ritter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.javafmt.core;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

/**
 * JavaFmt API main entry point.
 *
 * <p>
 * This class is the main entry point to the JavaFmt API.
 * It has to operations:
 *
 * <ul>
 * <li>format - format the given files, writing the formatted result back.</li>
 * <li>test  - test whether the given files are well formatted.</li>
 * </ul>
 * </p>
 *
 * @since 0.1.0
 */
public class JavaFmt {

    /**
     * Tests whether the given files or files inside the given directories are well formatted.
     *
     * <p>
     * <strong>Note:</strong> This method assumes UTF-8 encoding.
     * </p>
     *
     * @param files files to test.
     * @return true if all files are well formatted, false otherwise.
     * @see #test(Charset, File... files)
     */
    public boolean test(File... files) {
        return test(StandardCharsets.UTF_8, files);
    }

    /**
     * Tests whether the given files or files inside the given directories are well formatted using the given Charset.
     *
     * @param charset the Charset to use for reading the files.
     * @param files   the files to test.
     * @return true if all files are well formatted, false otherwise.
     * @see #test(File...)
     */
    public boolean test(Charset charset, File... files) {
        for (File file : files) {
            if (!testFile(charset, file)) return false;
        }
        return true;
    }

    private boolean testFile(final Charset encoding, final File file) {
        if (file.isDirectory()) {
            Collection<File> files = FileUtils.listFiles(file, new String[] { "java" }, true);
            return test(encoding, files.toArray(new File[0]));
        }
        try {
            String fileContent = FileUtils.readFileToString(file, encoding);
            return test(fileContent);
        } catch (IOException iox) {
            return false;
        }
    }

    private boolean test(String fileContent) {
        CompilationUnit parsed = JavaParser.parse(fileContent);
        return fileContent.equals(parsed.toString());
    }

    /**
     * Format the given files or files inside the given directories overriding their contents with the formatting result.
     *
     * <p>
     * <strong>Note:</strong> This method assumes UTF-8 encoding.
     * </p>
     *
     * @param files the files to format.
     * @see #format(Charset, File...)
     */
    public void format(File... files) {
        format(StandardCharsets.UTF_8, files);
    }

    /**
     * Format the given files or files inside the given directories using the given Charset.
     *
     * @param charset the Charset to use for reading the files.
     * @param files   the files to format.
     */
    public void format(Charset charset, File... files) {
        for (File file : files) {
            formatFile(charset, file);
        }
    }

    private void formatFile(Charset charset, File file) {
        if (file.isDirectory()) {
            Collection<File> files = FileUtils.listFiles(file, new String[] { "java" }, true);
            format(charset, files.toArray(new File[0]));
        }
        try {
            String fileContent = FileUtils.readFileToString(file, charset);
            String formatted = format(fileContent);
            FileUtils.write(file, formatted, charset);
        } catch (IOException iox) {
            // ignore
        }
    }

    private String format(String fileContent) {
        CompilationUnit parsed = JavaParser.parse(fileContent);
        return parsed.toString();
    }
}
