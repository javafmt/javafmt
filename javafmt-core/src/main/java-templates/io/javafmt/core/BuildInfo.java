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

public final class BuildInfo {

    /**
     * The release version of this JavaFmt build.
     */
    public static final String VERSION = "@build.version@";

    /**
     * The Git hash this JavaFmt version was build from.
     */
    public static final String GIT_HASH = "@build.hash@";

    /**
     * The timestamp of the build.
     */
    public static final String TIMESTAMP = "@build.timestamp@";
}
