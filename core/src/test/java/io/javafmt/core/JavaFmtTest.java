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

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JavaFmtTest {

    private File wellformatted;

    private File missformatted;

    @Before
    public void setUp() throws Exception {
        wellformatted = getFile("Wellformatted.java");
        missformatted = getFile("Missformatted.java");
    }

    private File getFile(final String fileName) throws URISyntaxException {
        return new File(getClass().getClassLoader().getResource("io/javafmt/core/" + fileName).toURI());
    }

    @Test
    public void should_detect_single_missformatted_file() {
        assertFalse(new JavaFmt().test(missformatted));
    }

    @Test
    public void should_detect_single_missformatted_file_among_well_formatted_files() {
        assertFalse(new JavaFmt().test(wellformatted, missformatted, wellformatted));
    }

    @Test
    public void should_detect_single_wellformatted_file() {
        assertTrue(new JavaFmt().test(wellformatted));
    }
}
