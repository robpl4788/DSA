package DSA_Assignment_21494561.RouteFinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import DSA_Assignment_21494561.DataTypes.Graph;
import DSA_Assignment_21494561.DataTypes.HashTable;
import DSA_Assignment_21494561.DataTypes.HashTable.HashTableException;

//Uses data from openflights, downloaded 13-May-2024, https://openflights.org/data.php

public class Router {

    int counter = 0;

    public class RouteFinderException extends RuntimeException {
        private RouteFinderException(String s) {
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

        routes.displayAsList();
    }

    private void readInAllAirports() {
        Scanner scanner = new Scanner(System.in);
        File airportFile = new File("DSA_Assignment_21494561\\RouteFinder\\airports.csv");
        try {
            scanner = new Scanner(airportFile);

            if (!scanner.hasNextLine()) {
                scanner.close();
                throw new RouteFinderException("Airport file is empty");
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
            throw new RouteFinderException("Airport entry invalid at line: " + (count + 1));
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
                throw new RouteFinderException("Route file is empty");
            }

            count = 0;
            while (scanner.hasNextLine()) {
                addRoute(scanner.nextLine());
            }

            System.out.println("Airports: " + routes.nodeCount());
            System.out.println("Routes: " + routes.edgeCount());
            
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
        }

        if (airline.equals("QF") == false && airline.equals("BA") == false && airline.equals("NK") == false) {
            result = false;
        }

        if (airports.hasKey(sourceKey) == false || airports.hasKey(destinationKey) == false) {
            result = false;
        }
        
        if (routes.hasNode(sourceKey) && routes.hasNode(destinationKey)) {
            if (routes.hasEdge(sourceKey, destinationKey)) {
                result = false;
            } 
        }

        return result;
    }



}
