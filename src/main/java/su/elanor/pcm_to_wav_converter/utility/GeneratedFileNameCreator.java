package su.elanor.pcm_to_wav_converter.utility;

import java.io.File;
import java.util.Optional;

public class GeneratedFileNameCreator {
    private static String fileNameWithoutExtension (String fileName) {
        if (fileName.startsWith(".")) {
            return "." + fileNameWithoutExtension(fileName.substring(1));
        } else {
            return Optional.of(fileName.lastIndexOf(".")).filter(i-> i >= 0)
                    .filter(i-> i > fileName.lastIndexOf(File.separator))
                    .map(i-> fileName.substring(0, i)).orElse(fileName);
        }
    }

    public static String get(String originalFileName) {
        var originalFile = new File(originalFileName);

        var path = originalFile.getParent();
        var fileName = fileNameWithoutExtension(originalFile.getName());

        var ret = new StringBuilder();
        ret.append(path);
        ret.append("/");
        ret.append(fileName);
        ret.append(".wav");

        return ret.toString();
    }
}
