package DSA_Assignment_21494561.RouteFinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import DSA_Assignment_21494561.DataTypes.Graph;
import DSA_Assignment_21494561.DataTypes.LinkedList;
import DSA_Assignment_21494561.DataTypes.HashTable;
import DSA_Assignment_21494561.DataTypes.Heap;
import DSA_Assignment_21494561.DataTypes.HashTable.HashTableException;

//Uses data from openflights, downloaded 13-May-2024, https://openflights.org/data.php

public class Router {

    int counter = 0;

    public class RouterException extends RuntimeException {
        private RouterException(String s) {
            super(s);
        }
    }


    Graph routes;
    HashTable airports;

    int count = 0;

    public Router() {
        airports = new HashTable();
        routes = new Graph();

        readInAllAirports();

        readInFlights();

        LinkedList useAirportCodes = routes.getAllKeys();
        HashTable usedAirports = new HashTable();

        useAirportCodes.setIteratorAtHead();
        do {
            String currentCode = (String) useAirportCodes.getIteratorData();
            usedAirports.addElement(currentCode, airports.getElement(currentCode));
        } while (useAirportCodes.setIteratorNext());

        airports = usedAirports;

    }

    public void printRoutes(String codeFrom, String codeTo, int maxDepth, boolean sortByDistance) {
        if (hasAirport(codeFrom) == false) {
            throw new RouterException("Looking for route from node that doesn't exist: " + codeFrom);
        } else if (hasAirport(codeTo) == false) {
            throw new RouterException("Looking for route from to that doesn't exist: " + codeTo);
        }

        LinkedList allRoutes = routes.breadthFirstKeyList(codeFrom, codeTo, maxDepth);

        if (sortByDistance) {
            allRoutes = quickSortRoutesByDistance(allRoutes);
        } else {
            allRoutes = sortAllRoutesByLayover(allRoutes, maxDepth);
        }

        int routeCount = allRoutes.getSize();
        String fromName = ((Airport) airports.getElement(codeFrom)).getName();
        String toName = ((Airport) airports.getElement(codeTo)).getName();

        System.out.println("Found " + routeCount + " routes between " + fromName + " (" + codeFrom + ") and " + toName + " (" + codeTo + ")");

        if (routeCount > 20) {
            System.out.println("Displaying the top 20 routes");
        }

        allRoutes.setIteratorAtHead();

        int i = 1;

        do {
            LinkedList currentPath = (LinkedList) allRoutes.getIteratorData();
            currentPath.setIteratorAtHead();

            String pathString = i + ": Length: ";
            pathString += (int) currentPath.getIteratorData();
            pathString += " km  \tLayovers: ";
            pathString += (currentPath.getSize() - 3);
            pathString += "\tRoute: ";
            currentPath.setIteratorNext();

            do {
                pathString += currentPath.getIteratorData();
                pathString += " -> ";
            } while (currentPath.setIteratorNext());
            pathString = pathString.substring(0, pathString.length() - 4);
            System.out.println(pathString);

            i ++;

            if (i > 20) {
                allRoutes.setIteratorAtTail();
            }
        } while (allRoutes.setIteratorNext());
    }

    public void printAirportInfo(String code) {
        if (hasAirport(code) == false) {
            System.out.println(code + " is not a known airport");
        } else {
            Airport printing = (Airport) airports.getElement(code);
            String toPrint = code;
            toPrint += ": ";
            toPrint += printing.getName();
            System.out.println(toPrint);
        }
    }

    public boolean hasAirport(String code) {
        return airports.hasKey(code);
    }

    private LinkedList sortAllRoutesByLayover(LinkedList allRoutes, int maxDepth) {
        Heap[] routeHeapArray = new Heap[maxDepth + 2];
        for (int i = 0; i < maxDepth + 2; i ++) {
            routeHeapArray[i] = new Heap(allRoutes.getSize());
        }

        allRoutes.setIteratorAtHead();
        do {
            LinkedList currentRoute = (LinkedList) allRoutes.getIteratorData();
            int length = currentRoute.getSize() - 1;
            routeHeapArray[length].add(((int) currentRoute.peekFront()), currentRoute);
        } while (allRoutes.setIteratorNext());

        LinkedList newRoutes = new LinkedList();

        for (int i = maxDepth + 2 - 1; i >= 0; i --) {
            while (routeHeapArray[i].getSize() != 0) {
                newRoutes.pushFront(routeHeapArray[i].pull());
            }
        }
        

        return newRoutes;
    }

    private LinkedList sortAllRoutesByDistance(LinkedList allRoutes) {
        Heap routeHeap = new Heap(allRoutes.getSize());
        allRoutes.setIteratorAtHead();
        do {
            LinkedList currentRoute = (LinkedList) allRoutes.getIteratorData();
            routeHeap.add(((int) currentRoute.peekFront()) * 100 + currentRoute.getSize(), currentRoute);
        } while (allRoutes.setIteratorNext());

        LinkedList newRoutes = new LinkedList();

        while (routeHeap.getSize() != 0) {
            newRoutes.pushFront(routeHeap.pull());
        }

        return newRoutes;
    }

    // Sorts from largest to smallest
    private LinkedList mergeSortRoutesByDistance(LinkedList allRoutes) {

        Object[] routeArray = allRoutes.asArray();

        merge(0, routeArray.length, routeArray);

        LinkedList sorted = new LinkedList();

        for (int i = 0; i < routeArray.length; i ++) {
            sorted.pushFront(routeArray[i]);
        }

        return sorted;
    }

    // Start include, stop excluded
    private void merge(int start, int stop, Object[] routesToSort) {

        
        if (stop == 0) {
            throw new RouterException("Lol");
        }
        if (stop - start > 1) {
            int mid = (start + stop) / 2;
            
            merge(start, mid, routesToSort);
            merge(mid, stop, routesToSort);
            
            Object leftArray[] = new Object[mid - start];
            for (int i = start; i < mid; i ++) {
                leftArray[i - start] = routesToSort[i];
            }
            
            Object rightArray[] = new Object[stop - mid];
            for (int i = mid; i < stop; i ++) {
                rightArray[i - mid] = routesToSort[i];
            }
            
            int leftIndex = 0;
            int rightIndex = 0;
            int arrayIndex = start;

            while (arrayIndex < stop) {
                
                if (leftIndex < leftArray.length && rightIndex < rightArray.length) {
                    LinkedList leftCurrent = (LinkedList) leftArray[leftIndex];
                    LinkedList rightCurrent = (LinkedList) rightArray[rightIndex];
    
                    if (getRouteDistance(leftCurrent) > getRouteDistance(rightCurrent)) {
                        routesToSort[arrayIndex] = leftCurrent;
                        leftIndex ++;
                    } else {
                        routesToSort[arrayIndex] = rightCurrent;
                        rightIndex ++;
                    }
                } else if (leftIndex < leftArray.length && rightIndex >= rightArray.length){
                    LinkedList leftCurrent = (LinkedList) leftArray[leftIndex];
                    routesToSort[arrayIndex] = leftCurrent;
                    leftIndex ++;
                    
                } else if (leftIndex >= leftArray.length && rightIndex < rightArray.length){
                    LinkedList rightCurrent = (LinkedList) rightArray[rightIndex];
                    
                    routesToSort[arrayIndex] = rightCurrent;
                    rightIndex ++;
                } else {
                    throw new RouterException("Merge sort, I'm so sure this won't happen, no idea how it could happen");
                }


                arrayIndex ++;

            }
        }

    }


    private LinkedList quickSortRoutesByDistance(LinkedList allRoutes) {
        Object[] routeArray = allRoutes.asArray();

        quick(0, routeArray.length, routeArray);

        LinkedList sorted = new LinkedList();

        for (int i = 0; i < routeArray.length; i ++) {
            sorted.pushFront(routeArray[i]);
        }

        return sorted;
    }

    private void quick(int start, int stop, Object[] routesToSort) {
        if (stop - start > 1) {
            LinkedList pivotRoute = (LinkedList) routesToSort[stop - 1];
            int pivot = getRouteDistance(pivotRoute);
            LinkedList moreThan = new LinkedList(); 
            LinkedList lessThan = new LinkedList(); // or equal

            int i;
            for (i = start; i < stop - 1; i ++) {
                LinkedList current = (LinkedList) routesToSort[i];
                if (getRouteDistance(current) <= pivot) {
                    lessThan.pushBack(current);
                } else {
                    moreThan.pushBack(current);
                }

            }

            i = start;
            while (moreThan.getSize() > 0) {
                // System.out.println(i);
                routesToSort[i] = moreThan.popFront();
                i ++;
            }
            int pivotIndex = i;
            routesToSort[i] = pivotRoute;
            i ++;
            
            while (lessThan.getSize() > 0) {
                routesToSort[i] = lessThan.popFront();
                i ++;
            }

            
            if (i != stop) {
                throw new RouterException("Quick sort lost or gained an element unexpectedly");
            }

            // double ratio = ((double) (pivotIndex - start)) / (stop - start) ;
            // System.out.println(ratio);
            quick(start, pivotIndex, routesToSort);
            quick(pivotIndex + 1, stop, routesToSort);
            }
    
    }



    private int getRouteDistance(LinkedList route) {
        return (int) route.peekFront();
    }

    private void readInAllAirports() {
        Scanner scanner = new Scanner(System.in);
        File airportFile = new File("DSA_Assignment_21494561\\RouteFinder\\airports.csv");
        try {
            scanner = new Scanner(airportFile);

            if (!scanner.hasNextLine()) {
                scanner.close();
                throw new RouterException("Airport file is empty");
            }

            count = 0;
            while (scanner.hasNextLine()) {
                addAirport(scanner.nextLine());
            }
            
        } catch (FileNotFoundException e) {
            scanner.close();
            System.out.println("Airport file initialisation failed: " + e );
        }
    }

    @SuppressWarnings("unused")
    private void addAirport(String raw) {
        final int Id = 0; //Openflight id for airport
        final int Name = 1;
        final int City = 2;
        final int Country = 3;
        final int IATA = 4; //3 letter code identifying the airport
        final int ICAO = 5; //4 letter code identifying the airport, valid for more small airports
        final int Latitude = 6;
        final int Longitude = 7;
        final int Altitude = 8;
        final int Timezone = 9;
        final int DST = 10;
        final int TzDatabaseTimezone = 11;
        final int Type = 12;
        final int Source = 13;

        String[] split = raw.split(",");

        if (split.length != 14) {
            throw new RouterException("Airport entry invalid at line: " + (count + 1));
        }

       
        // System.out.println(count + ": IATA: " + split[IATA] + " Name: " + split[Name] + " Lattitude: " + split[Latitude] + " Longitude: " + split[Longitude]);
        
        String iata = split[IATA];
        String name = split[Name];
        Double lattitude = Double.parseDouble(split[Latitude]);
        Double longitude = Double.parseDouble(split[Longitude]);
        
        //Confirm the IATA code is valid
        if (iata.length() == 5) {
            iata = iata.substring(1, 4);
            name = name.substring(1, name.length() - 1);
            Airport newAirport = new Airport(iata, name, lattitude, longitude);
            airports.addElement(iata, newAirport);

        }
        
        count ++;

    }

    //Reads in valid flights operated by Qantas(QF), British Airways(BA) or Spirit(NK)
    private void readInFlights() {
        Scanner scanner = new Scanner(System.in);
        File routeFile = new File("DSA_Assignment_21494561\\RouteFinder\\routes.csv");
        try {
            scanner = new Scanner(routeFile);

            if (!scanner.hasNextLine()) {
                scanner.close();
                throw new RouterException("Route file is empty");
            }

            count = 0;
            while (scanner.hasNextLine()) {
                addRoute(scanner.nextLine());
            }

            System.out.println("Loaded in " + routes.nodeCount() + " airports and " + routes.edgeCount() + " flights");
            
        } catch (FileNotFoundException e) {
            scanner.close();
            System.out.println("Route file initialisation failed: " + e );
        }
    }

    @SuppressWarnings("unused")
    private void addRoute(String raw) {

        final int Airline = 0;
        final int AirlineID = 1;
        final int SourceAirport = 2; //IATA or ICAO code
        final int SourceAirportID = 3;
        final int DestinationAirport = 4; //IATA or ICAO code
        final int DestinationAirportID = 5;
        final int CodeShare = 6;
        final int Stops = 7;
        final int Equipment = 8;

        String[] split = raw.split(",");

        if (split.length != 9) {
            System.out.println("Route entry invalid at line: " + (count + 1) + " Raw: " + raw);
            // throw new RouteFinderException("Route entry invalid at line: " + (count + 1));
        }

       
        // System.out.println(count + ": IATA: " + split[IATA] + " Name: " + split[Name] + " Lattitude: " + split[Latitude] + " Longitude: " + split[Longitude]);

        //Confirm source and destination a valid IATA code is valid
        String source = split[SourceAirport];
        String destination = split[DestinationAirport];
        String airline = split[Airline];

        if (shouldAddRoute(source, destination, airline)) {
            try {
                Airport sourceAirport = (Airport) airports.getElement(source);
                Airport destinationAirport = (Airport) airports.getElement(destination);

                if (routes.hasNode(source) == false) {
                    routes.addNode(source, sourceAirport);
                }
                
                if (routes.hasNode(destination) == false) {
                    routes.addNode(destination, destinationAirport);
                }    


                int distance = sourceAirport.getDistanceKm(destinationAirport);
                routes.addDirectedEdge(source, destination, distance);
            } catch (HashTableException e) {
                String msg = e.getMessage();
                msg = msg.substring(0, 36);
                
                //If complaining that route is to nonexistent airport then just ignore that route
                if (msg.equals("Getting object that does not exist: ") == false) {
                    throw e;
                }
                
            }
            
        }
        
        count ++;
    }

    private boolean shouldAddRoute(String sourceKey, String destinationKey, String airline) {
        boolean result = true;
        
        if (sourceKey.length() != 3 && destinationKey.length() != 3) {
            result = false;
        } else if (airline.equals("QF") == false && airline.equals("BA") == false && airline.equals("NK") == false) {
            result = false;
        } else if (airports.hasKey(sourceKey) == false || airports.hasKey(destinationKey) == false) {
            result = false;
        } else if (routes.hasNode(sourceKey) && routes.hasNode(destinationKey)) {
            if (routes.hasEdge(sourceKey, destinationKey)) {
                result = false;
            } 
        }

        return result;
    }


    public void sortComparison() {
        for (int j = 0; j < 20; j ++) {
            Airport from = (Airport) airports.randomEntry();
            Airport to = (Airport) airports.randomEntry();
            while (from.getName().equals(to.getName())) {
                to = (Airport) airports.randomEntry();
            }
    
            int i = 1;
            LinkedList allRoutes = new LinkedList();
            while (allRoutes.getSize() == 0) {
                allRoutes = routes.breadthFirstKeyList(from.getCode(), to.getCode(), i);
                i ++;
    
                if (i >= 10) {
                    from = (Airport) airports.randomEntry();
                    to = (Airport) airports.randomEntry();
                    while (from.getName().equals(to.getName())) {
                        to = (Airport) airports.randomEntry();
                    }
                    i = 1;
                }
            }
    
            int max = i + 3;
    
            for (i = i - 1; i < max; i ++) {
                allRoutes = routes.breadthFirstKeyList(from.getCode(), to.getCode(), i);
                Long start = System.nanoTime();   
                sortAllRoutesByDistance(allRoutes);
                Long end = System.nanoTime();
                Long heap = end - start;
                start = System.nanoTime();
                mergeSortRoutesByDistance(allRoutes);
                end = System.nanoTime();
                Long merge = end - start;
                start = System.nanoTime();
                quickSortRoutesByDistance(allRoutes);
                end = System.nanoTime();
                Long quick = end - start;
    
                System.out.println(allRoutes.getSize() + ", " + heap + ", " + merge + ", " + quick);
            }
    
        }
    }
        

}
