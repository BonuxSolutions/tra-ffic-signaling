package traffic.signaling;

import static traffic.signaling.Utils.readFromInputStream;
import static traffic.signaling.Utils.saveResult;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UncheckedIOException;
import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        var allfiles = args[0].split(",");
        if (allfiles.length == 1) {
            forFile(args[0]);
        } else if (allfiles.length > 1) {
            Arrays.asList(allfiles).forEach(App::forFile);
        }
    }

    private static void forFile(String file) {
        try {
            var is = new FileInputStream(file);
            var input = readFromInputStream(is);
            var superClass = new SuperClass(input);

            System.out.println(input);

            saveResult(superClass.performMagic(file));
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException(e);
        }
    }
}
