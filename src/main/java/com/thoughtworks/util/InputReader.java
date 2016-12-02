package com.thoughtworks.util;

import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by santteegt on 16/11/2016.
 *
 * Input parser for reading input schemes in the Thoughworks exercises
 *
 */
public abstract class InputReader {

    protected List<String> lines;

    public InputReader(File filePath) throws Exception {
        if(!FilenameUtils.getExtension(filePath.getName()).matches("(txt|csv)")) {
            throw new IllegalArgumentException("Input file must have in .txt or .csv format");
        }
        this.lines = IOUtils.readLines(new FileInputStream(filePath), Charset.defaultCharset());

    }

    public abstract void parse(Document entity) throws Exception;




}
