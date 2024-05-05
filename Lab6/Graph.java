package Lab6;
import Lab4.LinkedList;

public class Graph {
    private class GraphNode {
        String key;
        Object data;
        int visited = 0;
        LinkedList neighbours;  //List of neighbours with keys in order

        GraphNode (String Key, Object Data) {
            key = Key;
            data = Data;

            neighbours = new LinkedList();
        }

        String getKey() {
            return key;
        }

        Object getData() {
            return data;
        }

        void addNeighbour (GraphNode newNeighbour) {
            int index = 0;

            if (neighbours.getSize() != 0) {
                neighbours.setIteratorAtHead();
                do {
                    GraphNode current = (GraphNode) neighbours.getIteratorData();
                    int keyComparison = current.getKey().compareToIgnoreCase(newNeighbour.getKey());
                    // System.out.println(keyComparison);
                    if (keyComparison < 0) {
                        index ++;
                    } else if (keyComparison == 0) {
                        throw new GraphException("Trying to add edge that already exists, from: " + key + ", to: " + newNeighbour.getKey());

                    }
                } while (neighbours.setIteratorNext());
            }
            System.out.println("Connect " + key + " to " + newNeighbour.getKey() + " at index: " + index);

            // for (int i = 0; i < neighbours.getSize(); i ++) {
            //     System.out.println(newNeighbour.getKey().compareToIgnoreCase(((GraphNode) neighbours.peekIndex(i)).getKey()) > 0);
            //     if (newNeighbour.getKey().compareToIgnoreCase(((GraphNode) neighbours.peekIndex(i)).getKey()) < 0) {
            //         index = neighbours.getSize();
            //     } else if (newNeighbour.getKey().compareToIgnoreCase(((GraphNode) neighbours.peekIndex(i)).getKey()) == 0) {
            //     }
            // }

            // System.out.println(neighbours.getSize());
            neighbours.pushIndex(index, newNeighbour);
            // System.out.println(neighbours.getSize());
        }

        boolean hasNeighbour(String key) {
            boolean hasNeighbour = false;
            if (neighbours.getSize() != 0) {
                // System.out.println("");

                neighbours.setIteratorAtHead();
                do {
                    GraphNode current = (GraphNode) neighbours.getIteratorData();
                    if (current.getKey().equals(key)) {
                        hasNeighbour = true;
                    }
                } while (neighbours.setIteratorNext());
            }
            
            return hasNeighbour;
        }

        Object popNeighbour (String key) {
            neighbours.setIteratorAtHead();
            boolean done = false;
            GraphNode current;
            do {
                current = (GraphNode) neighbours.getIteratorData();
                if (current.getKey().compareToIgnoreCase(key) == 0) {
                    done = true;
                }
            } while (done == false && neighbours.setIteratorNext());

            if (done != true) {
                throw new GraphException("Trying to remove neighbour, but node is not a neighbour, trying to remove: " + key);
            }

            return neighbours.popIteratorNode();
        }

        String getAdjacentKeys() {
            String output = "";
            if (neighbours.getSize() != 0) {
                neighbours.setIteratorAtHead();
                do {
                    
                    output += ((GraphNode) neighbours.getIteratorData()).getKey();
                    output += "\t";
                } while (neighbours.setIteratorNext());
            }
            return output;

        }
    
        int edgeCount() {
            return neighbours.getSize();
        }
    
        int getVisited() {
            return visited;
        }

        void setVisited(int setTo) {
            visited = setTo;
        }
    }

    LinkedList nodes = new LinkedList();
    int visitedMarker = 0;

    void addNode(String key, Object data) {
        nodes.pushBack(new GraphNode(key, data));
    }

    void addEdge(String key1, String key2) {
        GraphNode node1 = findNode(key1);
        GraphNode node2 = findNode(key2);

        node1.addNeighbour(node2);
        node2.addNeighbour(node1);
    }

    void addDirectedEdge(String keyFrom, String keyTo) {
        GraphNode node1 = findNode(keyFrom);
        GraphNode node2 = findNode(keyTo);

        // System.out.println("Connect " + node1.getKey() + " to " + node2.getKey() + ", directed");
 

        node1.addNeighbour(node2);
    }

    private GraphNode findNode (String key) {
        GraphNode result = null;
        if (nodes.getSize() != 0) {
            nodes.setIteratorAtHead();
            do {
                GraphNode current = ((GraphNode) nodes.getIteratorData());
                // System.out.println(key + " " + current.getKey() + " " + current.getKey().compareToIgnoreCase(key));
                if (current.getKey().compareToIgnoreCase(key) == 0) {
                    result = current;
                }
            } while (nodes.setIteratorNext());
        }

        if (result == null) {
            throw new GraphException("Search for node found nothing: Searching for node with key: " + key);
        }
        return result;
    }

    void deleteNode(String key) {
        nodes.setIteratorAtHead();
        do {
            GraphNode current = (GraphNode) nodes.getIteratorData();
            if (current.getKey().equals(key)) {
                nodes.popIteratorNode();
            }
        } while (nodes.setIteratorNext()); 

        nodes.setIteratorAtHead();
        do {
            GraphNode current = (GraphNode) nodes.getIteratorData();
            if (current.hasNeighbour(key)) {
                current.popNeighbour(key);
            }
            
        } while (nodes.setIteratorNext());
    }

    void deleteDirectedEdge(String keyFrom, String keyTo) {
        GraphNode from = findNode(keyFrom);
        from.popNeighbour(keyTo);
    }

    boolean hasNode(String key) {
        boolean result = false;
        if (nodes.getSize() != 0) {
            nodes.setIteratorAtHead();
            do {
                GraphNode current = ((GraphNode) nodes.getIteratorData());
                // System.out.println(key + " " + current.getKey() + " " + current.getKey().compareToIgnoreCase(key));
                if (current.getKey().compareToIgnoreCase(key) == 0) {
                    result = true;
                }
            } while (nodes.setIteratorNext());
        }


        return result;
        }

    boolean hasEdge(String keyFrom, String keyTo) {
        GraphNode from = findNode(keyFrom);
        return from.hasNeighbour(keyTo);
    }

    int nodeCount () {
        return nodes.getSize();
    }

    int edgeCount () {
        int edges = 0;
        if (nodeCount() != 0) {
            nodes.setIteratorAtHead();
            do {
                edges += ((GraphNode) nodes.getIteratorData()).edgeCount();
            } while (nodes.setIteratorNext());
        }
        return edges;
    }

    Object getData(String key) {
        return findNode(key).getData();
    }

    String getAdjacentKeys(String key) {
        GraphNode node = findNode(key);
        return node.getAdjacentKeys();
    }

    boolean isAdjacent(String keyFrom, String keyTo) {
        GraphNode from = findNode(keyFrom);
        return from.hasNeighbour(keyTo);
    }

    void displayAsList () {
        nodes.setIteratorAtHead();
        System.out.println("Node:\tConnected To:");
        do {
            GraphNode current = (GraphNode) nodes.getIteratorData();
            System.out.println(current.getKey() + " :\t" + current.getAdjacentKeys());
        } while (nodes.setIteratorNext());
        
        
    }

    void displayAsMatrix() {
        String nodeKeys = "\t";

        if (nodeCount() == 0) {
            System.out.println("Nothing to display");
        } else {

            System.out.println("Adjacency Matrix:");
            nodes.setIteratorAtHead();
            do {
                nodeKeys += ((GraphNode) nodes.getIteratorData()).getKey();
                nodeKeys += "\t";
            } while (nodes.setIteratorNext());
            
            System.out.println(nodeKeys);
            nodeKeys = nodeKeys.substring(1);
            String[] nodeKeyArray = (nodeKeys.split("\t"));

            for (int i = 0; i < nodeCount();  i ++) {
                System.out.print(nodeKeyArray[i]);
                for (int j = 0; j < nodeCount();  j ++) {
                    boolean hasNeighbour = ((GraphNode) nodes.peekIndex(i)).hasNeighbour(nodeKeyArray[j]);
                    if (hasNeighbour) {
                        System.out.print("\t1");
                    } else {
                        System.out.print("\t0");
                    }
                }
                System.out.print("\n");
            }

        }
    }

    private String depthFirst(GraphNode current) {
        String result = "";
        result += current.getKey();
        result += "\t";

        current.setVisited(visitedMarker);


        if (current.neighbours.getSize() != 0) {
            current.neighbours.setIteratorAtHead();
            do {
                GraphNode iteratorNode = (GraphNode) current.neighbours.getIteratorData();
                if (iteratorNode.getVisited() != visitedMarker) {
                    result += depthFirst(iteratorNode);
                }
            } while (current.neighbours.setIteratorNext());
        }
        

        return result;
    }

    String depthFirstKeys(String keyFrom) {
        visitedMarker += 1;
        GraphNode start = findNode(keyFrom);

        String result = depthFirst(start);

        return result;
    }

    String breadthFirstKeys(String keyFrom) {
        visitedMarker += 1;

        GraphNode start = findNode(keyFrom);

        String result = "";

        LinkedList toSearchFrom = new LinkedList();
        start.setVisited(visitedMarker);
        toSearchFrom.pushFront(start);

        while (toSearchFrom.getSize() != 0) {
            GraphNode current = (GraphNode) toSearchFrom.popFront();
            
            result += current.getKey();
            result += "\t";

            for (int i = 0; i < current.neighbours.getSize(); i ++) {
                GraphNode mightSearch = (GraphNode) current.neighbours.peekIndex(i);
                if (mightSearch.getVisited() != visitedMarker) {
                    toSearchFrom.pushBack(mightSearch);
                    mightSearch.setVisited(visitedMarker);
                }
            }
        }   

        return result;
    }
}