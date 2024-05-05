package Lab6;

import java.util.Scanner;

public class GraphMenu {
    private static Graph graph = new Graph();
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        String[] inputs ={};// {"cn a a", "cn b b", "cn c c", "cn d d", "cn e e", "ce a b u", "ce a e u", "ce a d u","ce b e u", "ce b c u", "ce c e u", "ce c d u", "dl"};
        int inputId = 0;
        while (true) {
            try{
                String input;

                if (inputId >= inputs.length) {
                    input = scanner.nextLine();
                } else {
                    input = inputs[inputId];
                    inputId ++;
                }

                while (input.charAt(input.length() - 1) == ' ') {
                    input = input.substring(0, input.length() - 1);
                }


                String command = input.substring(0, 2).toLowerCase();


                if (command.equals("hp")) {
                    System.out.println("cn key data      : Create node with key and data");
                    System.out.println("ce key key d/u   : Create edge betweens keys that is directed or undirected");
                    System.out.println("rn key           : Remove node with key and all edges connected to and from it");
                    System.out.println("re key key       : Remove directed edge betweens keys");
                    System.out.println("dl               : Display graph as an adjaceny list");
                    System.out.println("dm               : Display graph as an adjaceny matrix");
                    System.out.println("dd key           : Display depth first traversal of the graph starting from key");
                    System.out.println("db key           : Display depth first traversal of the graph starting from key");
                    System.out.println("hp               : Display this help menu");
                } else if (command.equals("cn")) {
                    String[] splitInput = input.split(" ");
                    if (splitInput.length < 3) {
                        throw new GraphMenuException("Invalid input command format, needs 2 arguements, recieved " + (splitInput.length - 1));
                    }
                    String key = splitInput[1];
                    String data = "";
                    for (int i = 2; i < splitInput.length; i ++) {
                        data += splitInput[i];
                    }

                    graph.addNode(key, data);
                } else if (command.equals("rn")) {
                    String[] splitInput = input.split(" ");
                    if (splitInput.length != 2) {
                        throw new GraphMenuException("Invalid input command format, needs 1 arguements, recieved " + (splitInput.length - 1));
                    }
                    String key = splitInput[1];
                    graph.deleteNode(key);
                } else if (command.equals("ce")) {
                    String[] splitInput = input.split(" ");
                    if (splitInput.length != 4) {
                        throw new GraphMenuException("Invalid input command format, needs 3 arguements, recieved " + (splitInput.length - 1));
                    }
                    String key1 = splitInput[1];
                    String key2 = splitInput[2];

                    // System.out.println("Connect " + key1 + " to " + key2 + ", u/d?: " + splitInput[3]);

                    if (splitInput[3].equals("d")) {
                        graph.addDirectedEdge(key1, key2);
                    } else if (splitInput[3].equals("u")) {
                        graph.addEdge(key1, key2);
                    } else {
                        throw new GraphMenuException("Edge type must be one of d(directed) or u(undirected), recieved: " + splitInput[3]);
                    }
                } else if (command.equals("re")) {
                    String[] splitInput = input.split(" ");

                    if (splitInput.length != 3) {
                        throw new GraphMenuException("Invalid input command format, needs 2 arguements, recieved " + (splitInput.length - 1));
                    }

                    graph.deleteDirectedEdge(splitInput[1], splitInput[2]);
                } else if (command.equals("dl")) {
                    graph.displayAsList();
                } else if (command.equals("dm")) {
                    graph.displayAsMatrix();
                } else if (command.equals("dd")) {
                    String[] splitInput = input.split(" ");
                    if (splitInput.length != 2) {
                        throw new GraphMenuException("Invalid input command format, needs 1 arguements, recieved " + splitInput.length);
                    }
                    String key = splitInput[1];
                    System.out.println(graph.depthFirstKeys(key));
                } else if (command.equals("db")) {
                    String[] splitInput = input.split(" ");
                    if (splitInput.length != 2) {
                        throw new GraphMenuException("Invalid input command format, needs 1 arguements, recieved " + splitInput.length);
                    }
                    String key = splitInput[1];
                    System.out.println(graph.breadthFirstKeys(key));
                } else {
                    throw new GraphMenuException("Invalid input: " + input + " is not a recognised command");
                }
            } catch (Exception e) {
                System.out.println("Exception occurred: " + e);
            }
        }
        
    }
}
