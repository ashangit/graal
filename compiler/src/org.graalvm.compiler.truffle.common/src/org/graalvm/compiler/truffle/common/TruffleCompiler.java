/*
 * Copyright (c) 2018, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.graalvm.compiler.truffle.common;

import java.util.Map;

/**
 * A compiler that partially evaluates and compiles a {@link CompilableTruffleAST} to machine code.
 */
public interface TruffleCompiler {
    String FIRST_TIER_COMPILATION_SUFFIX = "#1";
    String SECOND_TIER_COMPILATION_SUFFIX = "#2";

    /**
     * Creates a new, unique compilation identifier for {@code compilable}. Each call results in a
     * new identifier.
     */
    String getCompilationIdentifier(CompilableTruffleAST compilable);

    /**
     * Opens a debug context for Truffle compilation. The {@code close()} method should be called on
     * the returned object once the compilation is finished.
     *
     * @param options the options for the debug context
     * @param compilationId an unique identifier to be used for a single compilation. Ignored if
     *            {@code compilable == null}.
     * @param compilable the Truffle AST to be compiled or {@code null} if the returned context will
     *            be used for multiple Truffle compilations
     * @return the new {@link TruffleDebugContext}
     */
    TruffleDebugContext openDebugContext(Map<String, Object> options, String compilationId, CompilableTruffleAST compilable);

    /**
     * Compiles {@code compilable} to machine code.
     *
     * @param debug a debug context to use
     * @param compilationId an unique identifier to be used for the compilation
     * @param options option values relevant to compilation
     * @param compilable the Truffle AST to be compiled
     * @param inlining a guide for Truffle level inlining to be performed during compilation
     * @param task an object that must be periodically queried during compilation to see if the
     */
    void doCompile(TruffleDebugContext debug, String compilationId, Map<String, Object> options, CompilableTruffleAST compilable, TruffleInliningPlan inlining, TruffleCompilationTask task,
                    TruffleCompilerListener listener);

    /**
     * Returns a unique name for the configuration in use by this compiler.
     */
    String getCompilerConfigurationName();

    /**
     * Notifies this object that it will no longer being used and should thus perform all relevant
     * finalization tasks.
     */
    void shutdown();
}
