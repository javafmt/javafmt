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
import picocli.CommandLine;

import java.io.PrintStream;

public final class Main {

    private final JavaFmt javaFmt;

    private final PrintStream output;

    Main(JavaFmt javaFmt, PrintStream output) {
        this.javaFmt = javaFmt;
        this.output = output;
    }

    public int run(String... args) {
        CliOptions options = new CliOptions();
        CommandLine cmd = new CommandLine(options);
        cmd.parse(args);

        int exitCode = 0;
        if (options.showHelp) {
            cmd.usage(output);
        } else if (options.showVersionInfo) {
            cmd.printVersionHelp(output);
        } else if (options.test) {
            if(!javaFmt.test(options.files)) {
                exitCode = 1;
            }
        } else if(options.files.length > 0) {
            javaFmt.format(options.files);
        } else {
            cmd.usage(output);
        }
        return exitCode;
    }

    public static void main(String... args) {
        int exitCode = new Main(new JavaFmt(), System.out).run(args);
        System.exit(exitCode);
    }
}
