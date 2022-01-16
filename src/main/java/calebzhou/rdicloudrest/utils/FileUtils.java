package calebzhou.rdicloudrest.utils;


import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

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
}
