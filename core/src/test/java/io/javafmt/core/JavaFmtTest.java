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

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class JavaFmtTest {

    private File wellformatted;

    private File wellformattedCopy;

    private File missformatted;

    private File missformattedCopy;

    private File missformattedExpected;

    @BeforeEach
    void setUp() throws Exception {
        File tempDirectory = FileUtils.getTempDirectory();

        wellformatted = getFile("Wellformatted.java");
        missformatted = getFile("Missformatted.java");
        missformattedExpected = getFile("Missformatted_expected.java");

        missformattedCopy = new File(tempDirectory, "Missformatted_copy.java");
        FileUtils.copyFile(missformatted, missformattedCopy);

        wellformattedCopy = new File(tempDirectory, "Wellformatted_copy.java");
        FileUtils.copyFile(wellformatted, wellformattedCopy);
    }

    private File getFile(final String fileName) throws URISyntaxException {
        return new File(getClass().getClassLoader().getResource("io/javafmt/core/" + fileName).toURI());
    }

    @Test
    void should_detect_single_missformatted_file() {
        assertThat(new JavaFmt().test(missformatted)).isFalse();
    }

    @Test
    void should_detect_single_missformatted_file_among_well_formatted_files() {
        assertThat(new JavaFmt().test(wellformatted, missformatted, wellformatted)).isFalse();
    }

    @Test
    void should_detect_single_wellformatted_file() {
        assertThat(new JavaFmt().test(wellformatted)).isTrue();
    }

    @Test
    void should_format_missformatted_file() {
        new JavaFmt().format(missformattedCopy);

        assertThat(missformattedCopy).hasSameContentAs(missformattedExpected);
    }

    @Test
    void should_not_change_wellformatted_file() {
        new JavaFmt().format(wellformattedCopy);

        assertThat(wellformattedCopy).hasSameContentAs(wellformatted);
    }
}
