package calebzhou.rdicloudrest.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class FileUtil {
    public static final File blockRecFolder = new File(new File("."),"block_rec");
    public static void writeToFile(File file,Object obj) {
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            OutputStreamWriter writer = null;
            boolean var4;
            try {
                writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
                writer.write(obj+"");
            } catch (Throwable var8) {
                var8.printStackTrace();
            } finally {
                IOUtils.closeQuietly((Writer)writer);
            }

    }
    public static void writeLineToFile(File file,Object line){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(line.toString());
    }
    public static String readAllLines(File file){
        Path path = Paths.get(file.getAbsolutePath());
        String read = "";
        try {

            read = Files.readAllLines(path).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return read;
    }
}
