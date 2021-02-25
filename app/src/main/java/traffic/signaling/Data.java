package traffic.signaling;

import java.util.List;
import java.util.Map;

record Input(int durationOfSimulation, int numberOfIntersections, int numberOfStreets, int numberOfCars, int bonus,
                List<Street> streets, List<Path> paths) {
}

record Street(int intersectStart, int intersectEnd, String name, int time) {
}

record Path(int numberOfStreets, List<String> streetNames) {
}

record Result(String name, int a, List<Schedule> s) {
        @Override
        public String toString() {
                return String.format("%d\n%s", a, Utils.toString(s));
        }
}

record Schedule(int i, int e, Map<String, Integer> durations) {
        @Override
        public String toString() {
                return String.format("%d\n%d\n%s", i, e, Utils.toString(durations));
        }
}