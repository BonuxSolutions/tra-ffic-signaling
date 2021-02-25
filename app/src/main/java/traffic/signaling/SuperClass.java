package traffic.signaling;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SuperClass {
    private Input input;

    public SuperClass(Input input) {
        this.input = input;
    }

    Result performMagic(String file) {
        var streetPerPaths = new LinkedList<LinkedList<Street>>();
        for (Path p: input.paths()){
            var streetPerPath = new LinkedList<Street>();
            for(String streetName: p.streetNames()){
                for(Street s: input.streets()){
                    if(s.name().equals(streetName)){
                        streetPerPath.add(s);
                    }
                }
            }
            streetPerPaths.add(streetPerPath);
        }
        
        
        for(List<Street> streets: streetPerPaths){
            int totalTime = 0;
            for(Street street: streets){
                while (!isGreen(totalTime, street.intersectEnd(), street.name())) {
                    totalTime++;
                }
                totalTime += street.time();
            }
            System.out.println("totalTime: " + totalTime);
        }

        System.out.println(streetPerPaths.stream().map(s -> s.stream().map(Object::toString).collect(Collectors.joining(" "))).map(Object::toString).collect(Collectors.joining("\n")));

        return new Result(file, 3, List.of(new Schedule(1, 2, Map.of("rue-d-athenes", 2, "rue-d-amsterdam", 1)),
                new Schedule(0, 1, Map.of("rue-de-londres", 2)), new Schedule(2, 1, Map.of("rue-de-moscou", 1))));
    }

    private Boolean isGreen(int time, int intersect, String street) {
        if (street.equals("amsterdam")) {
            return (time % 2) < 1;
        }
        if (street.equals("amsterdam")) {
            return (time % 3) >1;
        }
        
        return (time % 2) == 0;
    }
}


/*
Street[intersectStart=2, intersectEnd=0, name=rue-de-londres, time=1]  T=0 t=0 i=0 => true => 0
Street[intersectStart=0, intersectEnd=1, name=rue-d-amsterdam, time=1] T=0 t=1 i=1 => true => 1
Street[intersectStart=1, intersectEnd=2, name=rue-de-moscou, time=3]   T=1 t=3 i=2 => true => 3
Street[intersectStart=2, intersectEnd=3, name=rue-de-rome, time=2]     T=4 t=2 i=3 => true => 2   Sum = 6

Street[intersectStart=3, intersectEnd=1, name=rue-d-athenes, time=1]
Street[intersectStart=1, intersectEnd=2, name=rue-de-moscou, time=3]
Street[intersectStart=2, intersectEnd=0, name=rue-de-londres, time=1]

Input[y
    durationOfSimulation=6,
    numberOfIntersections=4,
    numberOfStreets=5,
    numberOfCars=2,bonus=1000,
    streets=[
        Street[intersectStart=2, intersectEnd=0, name=rue-de-londres, time=1],
        Street[intersectStart=0, intersectEnd=1, name=rue-d-amsterdam, time=1],
        Street[intersectStart=3, intersectEnd=1, name=rue-d-athenes, time=1],
        Street[intersectStart=2, intersectEnd=3, name=rue-de-rome, time=2],
        Street[intersectStart=1, intersectEnd=2, name=rue-de-moscou, time=3]],    
    paths=[                                         2-0(0)    ?      0-1(1)    ?     1-2(3)   ?     2-3(2)   = ceļš < T(6)
        Path[numberOfStreets=4, streetNames=[rue-de-londres, rue-d-amsterdam, rue-de-moscou, rue-de-rome]],
                                                 2-0(1)    ?      0-1(1)    ?     1-2(3)   ?     2-3(2)   = ceļš < T(6)
        Path[numberOfStreets=4, streetNames=[rue-de-londres, rue-d-amsterdam, rue-de-moscou, rue-de-rome]],
        Path[numberOfStreets=3, streetNames=[rue-d-athenes, rue-de-moscou, rue-de-londres]]]]
*/