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

package io.javafmt.cli;

import io.javafmt.core.JavaFmt;
import org.apache.commons.io.output.WriterOutputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MainTest {

    @Mock
    private JavaFmt javaFmt;

    private StringWriter writer;

    private Main main;

    private PrintStream output;

    @BeforeEach
    void setUp() {
        writer = new StringWriter();
        WriterOutputStream os = new WriterOutputStream(writer, StandardCharsets.UTF_8);

        output = new PrintStream(os);
        main = new Main(javaFmt, output);
    }

    @Test
    void should_show_help_when_help_option_is_passed() {
        int exitCode = runWithArgs("-h");

        assertHelpPrinted(exitCode);
    }

    @Test
    void should_show_help_when_long_help_option_is_passed() {
        int exitCode = runWithArgs("--help");

        assertHelpPrinted(exitCode);
    }

    @Test
    void should_show_help_when_no_parameter_is_passed() {
        int exitCode = runWithArgs();

        assertHelpPrinted(exitCode);
    }

    @Test
    void should_call_test_on_JavaFmt_when_test_option_is_passed() {
        runWithArgs("-t");

        verify(javaFmt, only()).test();
    }

    private void assertHelpPrinted(final int exitCode) {
        assertThat(exitCode).isEqualTo(0);

        String helpMsg = writer.toString();
        assertThat(helpMsg).contains("Usage");
        assertThat(helpMsg).contains("display this help message");
        assertThat(helpMsg).contains("file or directory, in which case all *.java files are formatted.");
    }

    private int runWithArgs(String... args) {
        int exitCode = main.run(args);
        output.flush();
        return exitCode;
    }
}
