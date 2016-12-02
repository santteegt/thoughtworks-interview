package com.thoughtworks.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Application Launcher
 *
 * @author santteegt
 */
public abstract class App {

    private JFrame frame;

    protected static String OK = "OK";

    protected File output = new File("output.txt");

    protected Map<String, String> properties;

    public App(String ... args) {
        this.frame = new JFrame();
        this.parseArguments(Arrays.asList(args));
    }

    protected enum AppProperties {
        SHOW_FILE_CHOOSER("show.file.chooser"),
        INPUT_FILE("input.file");

        private String key;

        AppProperties(String key) {
            this.key = key;

        }

        public String getKey() {
            return this.key;
        }

    }

    private static class PropertiesExtractor {

        public static String extractKey(String arg) {
            return arg.substring(arg.indexOf("-D") + 2, arg.indexOf("="));
        }


        public static String extractValue(String arg) {
            return arg.substring(arg.indexOf("=") + 1);
        }
    }

    protected void parseArguments(List<String> properties) {
        Stream<String> props = properties.stream().filter(p -> p.matches("^\\-D.*"));
        this.properties = props.collect(Collectors.
                toMap(o ->  PropertiesExtractor.extractKey(o), o -> PropertiesExtractor.extractValue(o)));
    }

    protected File openFileDialog(String ... args)throws Exception {

        File filePath = null;
        if(Boolean.valueOf(this.properties.get(AppProperties.SHOW_FILE_CHOOSER.getKey()))) {

            this.frame.setVisible(true);
            this.frame.setExtendedState(JFrame.ICONIFIED);
            this.frame.setExtendedState(JFrame.NORMAL);

            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new FileNameExtensionFilter("Text Files (*.txt, *.csv)", "txt", "csv"));
            if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(null)) {
                frame.setVisible(false);
                filePath = fc.getSelectedFile();
            } else {
                throw new IllegalArgumentException("File has not been selected! Closing application");
            }
        } else {
            URI file = this.getClass().getClassLoader()
                    .getResource(this.properties.get(AppProperties.INPUT_FILE.getKey())).toURI();
            filePath = new File(file);
            System.out.println("WORKING WITH TEST CASE FILE");
        }

        return filePath;
    }

    public abstract void execute() throws Exception;


    public void writeSolution(List<String> lines)throws Exception {
        FileUtils.writeLines(this.output, lines);

    }

    public void writeSolution(String line, Boolean append) throws Exception {
        if(append && this.output.exists()) {
            FileUtils.writeStringToFile(this.output, line + "\n", Charset.defaultCharset(), true);
        } else {
            FileOutputStream out = new FileOutputStream(this.output);
            IOUtils.write(line + "\n", out, Charset.defaultCharset());
        }
    }

}
