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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.PrintStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainTest {

    @Mock
    private JavaFmt javaFmt;

    private StringWriter writer;

    private Main main;

    private PrintStream output;

    @Before
    public void setUp() throws Exception {
        writer = new StringWriter();
        WriterOutputStream os = new WriterOutputStream(writer, StandardCharsets.UTF_8);

        output = new PrintStream(os);
        main = new Main(javaFmt, output);
    }

    @Test
    public void should_show_help_when_help_option_is_passed() {
        int existCode = runWithArgs("-h");

        assertHelpPrinted(existCode);
    }

    @Test
    public void should_show_help_when_long_help_option_is_passed() {
        int existCode = runWithArgs("--help");

        assertHelpPrinted(existCode);
    }

    @Test
    public void should_show_help_when_no_parameter_is_passed() {
        int existCode = runWithArgs();

        assertHelpPrinted(existCode);
    }

    @Test
    public void should_call_test_on_JavaFmt_when_test_option_is_passed() {
        runWithArgs("-t");

        verify(javaFmt, only()).test();
    }

    private void assertHelpPrinted(final int existCode) {
        assertEquals(0, existCode);

        String helpMsg = writer.toString();
        assertTrue(helpMsg, helpMsg.contains("Usage"));
        assertTrue(helpMsg, helpMsg.contains("display this help message"));
        assertTrue(helpMsg, helpMsg.contains("file or directory, in which case all *.java files are formatted."));
    }

    private int runWithArgs(String... args) {
        int exitCode = main.run(args);
        output.flush();
        return exitCode;
    }
}
