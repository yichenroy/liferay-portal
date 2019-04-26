/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jenkins.results.parser.testray.model;

import java.io.File;

/**
 * @author Kevin Yen
 */
public class Attachment {

    public Attachment(String label, String path, File file) {
        _file = file;
        _label = label;
        _path = path;
    }


    public File getFile() {
        return _file;
    }

    public String getLabel() {
        return _label;
    }

    public String getPath() {
        return _path;
    }

    public void setLabel(String label) {
        _label = label;
    }

    public void setPath(String path) {
        _path = path;
    }

    public void setFile(File file) {
        _file = file;
    }

    private String _label;
    private String _path;
    private File _file;

}
