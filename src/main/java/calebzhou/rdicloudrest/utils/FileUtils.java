package calebzhou.rdicloudrest.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Slf4j
public class FileUtils {
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
    public static void writeLineToFile(File folder,String file,String line){
        log.info(line);
        try (FileWriter myWriter = new FileWriter(new File(folder, file + ".txt"))) {
            myWriter.append(line + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
