/*
 * Copyright 2012 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vafer.jdeb.maven;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.vafer.jdeb.mapping.LsMapper;
import org.vafer.jdeb.mapping.PermMapper;

/**
 * Maven "mapper" element acting as factory for the entry mapper.
 * Supported types: ls, prefix, perm
 *
 * @author Bryan Sant <bryan.sant@gmail.com>
 */
public final class Mapper {

    /**
     * @parameter
     * @required
     */
    private String type;

    /**
     * @parameter
     */
    private int uid = -1;

    /**
     * @parameter
     */
    private int gid = -1;

    /**
     * @parameter
     */
    private String user;

    /**
     * @parameter
     */
    private String group;

    /**
     * @parameter
     */
    private String filemode;

    /**
     * @parameter
     */
    private String dirmode;

    /**
     * @parameter
     */
    private String prefix;

    /**
     * @parameter
     */
    private int strip;

    /**
     * @parameter
     */
    private File src;


    public org.vafer.jdeb.mapping.Mapper createMapper() throws IOException {

        if ("ls".equalsIgnoreCase(type)) {
            try {
                return new LsMapper(new FileInputStream(src));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if ("perm".equalsIgnoreCase(type)) {
            return new PermMapper(uid, gid, user, group, filemode, dirmode, strip, prefix);
        }

        // @deprecated
        if ("prefix".equalsIgnoreCase(type)) {
        	System.err.println("The 'prefix' mapper is deprecated. Please use 'perm' instead. Same syntax and more.");
            return new PermMapper(strip, prefix);
        }

        throw new IOException("Unknown mapper type '" + type + "'");
    }

}
