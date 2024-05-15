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

        printAirportInfo("PER");

        // routes.displayAsList(); 

        // printAllRoutes("PER", "CDG", 4, false);
        // allRoutes = routes.breadthFirstKeyList("PER", "ASP", 1);
        // printAllRoutes(allRoutes);
        // allRoutes = routes.breadthFirstKeyList("ASP", "SYD", 1);
        // printAllRoutes(allRoutes);
    }

    public void printAllRoutes(String codeFrom, String codeTo, int maxDepth, boolean sortByDistance) {
        if (hasAirport(codeFrom) == false) {
            throw new RouterException("Looking for route from node that doesn't exist: " + codeFrom);
        } else if (hasAirport(codeTo) == false) {
            throw new RouterException("Looking for route from to that doesn't exist: " + codeTo);
        }

        LinkedList allRoutes = routes.breadthFirstKeyList(codeFrom, codeTo, maxDepth);

        if (sortByDistance) {
            allRoutes = sortAllRoutesByDistance(allRoutes);
        } else {
            allRoutes = sortAllRoutesByLayover(allRoutes, maxDepth);
        }

        int routeCount = allRoutes.getSize();
        String fromName = ((Airport) airports.getElement(codeFrom)).getName();
        String toName = ((Airport) airports.getElement(codeTo)).getName();

        System.out.println("Found " + routeCount + " routes between " + fromName + " (" + codeFrom + ") and " + toName + " (" + codeTo + ")");

        allRoutes.setIteratorAtHead();

        do {
            LinkedList currentPath = (LinkedList) allRoutes.getIteratorData();
            currentPath.setIteratorAtHead();

            String pathString = "Length: ";
            pathString += (int) currentPath.getIteratorData();
            pathString += " km  \tLayovers: ";
            pathString += (currentPath.getSize() - 2);
            pathString += "\tRoute: ";
            currentPath.setIteratorNext();

            do {
                pathString += currentPath.getIteratorData();
                pathString += " -> ";
            } while (currentPath.setIteratorNext());
            pathString = pathString.substring(0, pathString.length() - 4);
            System.out.println(pathString);
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

            System.out.println("Airports: " + routes.nodeCount());
            System.out.println("Flights: " + routes.edgeCount());
            
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



}
