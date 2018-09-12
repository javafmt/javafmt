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

import io.javafmt.core.BuildInfo;
import picocli.CommandLine.IVersionProvider;

import java.io.IOException;
import java.time.Year;

class JavaFmtVersionProvider implements IVersionProvider {

    public String[] getVersion() throws Exception {
        return new String[]{version(), build(), copyright()};
    }

    private String version() throws IOException {
        return "javafmt " + BuildInfo.VERSION;
    }

    private String build() {
        return String.format("Build %s (%s)", BuildInfo.GIT_HASH, BuildInfo.TIMESTAMP);
    }

    private String copyright() {
        return String.format("Copyright %s Benedikt Ritter", Year.now());
    }
}
