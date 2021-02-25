package traffic.signaling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {
    static Input readFromInputStream(InputStream inputStream) {
        var inputBuilder = new InputBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            var line = br.readLine();
            assert line != null;
            inputBuilder.head(line);
            while ((line = br.readLine()) != null) {
                inputBuilder.tail(line);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return inputBuilder.build();
    }

    static void saveResult(Result result) {
        var path = Paths.get(result.name() + ".result");
        byte[] strToBytes = result.toString().getBytes();

        try {
            Files.write(path, strToBytes);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    static String toString(Map<String, Integer> map){
        return map.entrySet().stream().map(e -> e.getKey()+ " " + e.getValue()).collect(Collectors.joining("\n"));
    }

    static <T> String toString(List<T> list){
        return list.stream().map(Object::toString).collect(Collectors.joining("\n"));
    }

    private static class InputBuilder {
        int durationOfSimulation;
        int numberOfIntersections;
        int numberOfStreets;
        int numberOfCars;
        int bonus;
        List<Street> streets = new LinkedList<>();
        List<Path> paths = new LinkedList<>();

        InputBuilder head(String line) {
            var elems = line.split(" ");
            durationOfSimulation = Integer.parseInt(elems[0]);
            numberOfIntersections = Integer.parseInt(elems[1]);
            numberOfStreets = Integer.parseInt(elems[2]);
            numberOfCars = Integer.parseInt(elems[3]);
            bonus = Integer.parseInt(elems[4]);

            return this;
        }

        InputBuilder tail(String line) {
            var elems = line.split(" ");
            if (streets.size() < numberOfStreets) {
                int intersectStart = Integer.parseInt(elems[0]);
                int intersectEnd = Integer.parseInt(elems[1]);
                String name = elems[2];
                int time = Integer.parseInt(elems[3]);
                streets.add(new Street(intersectStart, intersectEnd, name, time));
            } else {
                int numberOfStreets = Integer.parseInt(elems[0]);
                List<String> streetNames = List.of(elems);

                paths.add(new Path(numberOfStreets, streetNames.subList(1, streetNames.size())));
            }
            return this;
        }

        Input build() {
            return new Input(durationOfSimulation, numberOfIntersections, numberOfStreets, numberOfCars, bonus, streets,
                    paths);
        }
    }
}
