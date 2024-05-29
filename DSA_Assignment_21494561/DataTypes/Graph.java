package DSA_Assignment_21494561.DataTypes;

// Advanced Data Type that stores nodes and how they connect to each other
public class Graph {

    public class GraphException extends RuntimeException {
        public GraphException(String s) {
            super(s);
        }
    }

    // Class to hold the data, key and what other nodes each node is connected to
    private class GraphNode {

        // Class to conveniently store each edge with a weight
        private class GraphEdge {
            GraphNode destination;
            int weight;

            private GraphNode getDestination() {
                return destination;
            }

            private int getWeight() {
                return weight;
            }

            private GraphEdge(GraphNode destination, int weight) {
                this.destination = destination;
                this.weight = weight;
            }
            
        }
        String key;
        Object data;
        LinkedList neighbours;  //List of neighbours with keys in order

        
        GraphNode (String key, Object data) {
            this.key = key;
            this.data = data;

            neighbours = new LinkedList();
        }

        String getKey() {
            return key;
        }

        Object getData() {
            return data;
        }

        // Add a neighbour to this node, ensuring neighbours remains ordered
        void addNeighbour (GraphNode newNeighbour, int weight) {
            int index = 0;

            if (neighbours.getSize() != 0) {
                neighbours.setIteratorAtHead();
                do {
                    GraphNode current = ((GraphEdge) neighbours.getIteratorData()).getDestination();
                    int keyComparison = current.getKey().compareToIgnoreCase(newNeighbour.getKey());
                    if (keyComparison < 0) {
                        index ++;
                    } else if (keyComparison == 0) {
                        throw new GraphException("Trying to add edge that already exists, from: " + key + ", to: " + newNeighbour.getKey());

                    }
                } while (neighbours.setIteratorNext());
            }

            neighbours.pushIndex(index, new GraphEdge(newNeighbour, weight));
        }

        // Checks if this node has a neighbour with a given key
        boolean hasNeighbour(String key) {
            boolean hasNeighbour = false;
            if (neighbours.getSize() != 0) {

                neighbours.setIteratorAtHead();
                do {
                    GraphNode current = ((GraphEdge) neighbours.getIteratorData()).getDestination();
                    if (current.getKey().equals(key)) {
                        hasNeighbour = true;
                    }
                } while (neighbours.setIteratorNext());
            }
            
            return hasNeighbour;
        }

        // Remove a neighbour with a given key, doesn't delete that node, just that this node is connected to it
        void popNeighbour (String key) {
            neighbours.setIteratorAtHead();
            boolean done = false;
            GraphNode current;
            do {
                current = ((GraphEdge) neighbours.getIteratorData()).getDestination();
                if (current.getKey().compareToIgnoreCase(key) == 0) {
                    done = true;
                }
            } while (done == false && neighbours.setIteratorNext());

            if (done != true) {
                throw new GraphException("Trying to remove neighbour, but node is not a neighbour, trying to remove: " + key);
            }

            neighbours.popIteratorNode();
        }

        // Get a string containing all the keys of nodes this node is connected to, seperated by \t
        String getAdjacentKeys() {
            String output = "";
            if (neighbours.getSize() != 0) {
                neighbours.setIteratorAtHead();
                do {
                    
                    output += ((GraphEdge) neighbours.getIteratorData()).getDestination().getKey();
                    output += "\t";
                } while (neighbours.setIteratorNext());
            }
            return output;

        }
    
        int edgeCount() {
            return neighbours.getSize();
        }
    

        // Get the weight of an edge to a node with a given key
        int getEdgeWeight(String keyTo) {

            boolean hasNeighbour = false;
            int edgeWeight = 0;

            if (neighbours.getSize() != 0) {

                neighbours.setIteratorAtHead();
                do {
                    GraphEdge currentEdge = (GraphEdge) neighbours.getIteratorData();

                    if (currentEdge.getDestination().getKey().equals(keyTo)) {
                        if (hasNeighbour) {
                            throw new GraphException("Two copies of edge exist");
                        }
                        hasNeighbour = true;
                        edgeWeight = currentEdge.getWeight();
                    }
                } while (neighbours.setIteratorNext());
            }

            if (hasNeighbour == false) {
                throw new GraphException("Getting weight of edge that does not exist");
            }
            return edgeWeight;
        }
    }

    //List of all nodes in graph
    LinkedList nodes = new LinkedList();

    public void addNode(String key, Object data) {
        nodes.pushBack(new GraphNode(key, data));
    }

    // Add a one way edge between two nodes
    public void addDirectedEdge(String keyFrom, String keyTo, int weight) {
        GraphNode node1 = findNode(keyFrom);
        GraphNode node2 = findNode(keyTo); 

        node1.addNeighbour(node2, weight);
    }

    // Find the reference to a node with a given key
    private GraphNode findNode (String key) {
        GraphNode result = null;
        if (nodes.getSize() != 0) {
            nodes.setIteratorAtHead();
            do {
                GraphNode current = ((GraphNode) nodes.getIteratorData());
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

    // Remove a node from the graph, including removing all edges pointed at it
    public void deleteNode(String key) {
        //Delete the node
        nodes.setIteratorAtHead();
        do {
            GraphNode current = (GraphNode) nodes.getIteratorData();
            if (current.getKey().equals(key)) {
                nodes.popIteratorNode();
            }
        } while (nodes.setIteratorNext()); 

        // Delete the neighbours
        nodes.setIteratorAtHead();
        do {
            GraphNode current = (GraphNode) nodes.getIteratorData();
            if (current.hasNeighbour(key)) {
                current.popNeighbour(key);
            }
            
        } while (nodes.setIteratorNext());
    }

    // Delete a one way edge
    public void deleteDirectedEdge(String keyFrom, String keyTo) {
        GraphNode from = findNode(keyFrom);
        from.popNeighbour(keyTo);
    }

    // Check if a node with a given key is in the graph
    public boolean hasNode(String key) {
        boolean result = false;
        if (nodes.getSize() != 0) {
            nodes.setIteratorAtHead();
            do {
                GraphNode current = ((GraphNode) nodes.getIteratorData());
                if (current.getKey().compareToIgnoreCase(key) == 0) {
                    result = true;
                }
            } while (nodes.setIteratorNext());
        }
        return result;
        }

    // Check if two nodes with given keys are connected (directionally)
    public boolean hasEdge(String keyFrom, String keyTo) {
        GraphNode from = findNode(keyFrom);
        return from.hasNeighbour(keyTo);
    }

    // Get the weight of an edge between two nodes
    public int edgeWeight(String keyFrom, String keyTo) {
        GraphNode from = findNode(keyFrom);
        return from.getEdgeWeight(keyTo);
    }

    // Get the number of nodes in the graph
    public int nodeCount () {
        return nodes.getSize();
    }

    // Get the number of edges in the graph
    public int edgeCount () {
        int edges = 0;
        if (nodeCount() != 0) {
            nodes.setIteratorAtHead();
            do {
                edges += ((GraphNode) nodes.getIteratorData()).edgeCount();
            } while (nodes.setIteratorNext());
        }
        return edges;
    }

    // Get the data frin a node with a given key
    public Object getData(String key) {
        return findNode(key).getData();
    }

    // Get a string that contains the keys of all nodes connected to a node with the given key, seperated by \t
    public String getAdjacentKeys(String key) {
        GraphNode node = findNode(key);
        return node.getAdjacentKeys();
    }

    // Display the graph as a list
    public void displayAsList () {
        if (nodes.getSize() == 0) {
		System.out.println("Nothing To Display");
        } else {
        
            nodes.setIteratorAtHead();
                System.out.println("Node:\tConnected To:");
                do {
                    GraphNode current = (GraphNode) nodes.getIteratorData();
                    System.out.println(current.getKey() + " :\t Connected to " + current.edgeCount() + " nodes:\t" + current.getAdjacentKeys());
                } while (nodes.setIteratorNext());
        }
            
    }

    // Returns a linked list of valid routes represented as linked lists, the first element of each is the total distance of the route, followed by the key path to follow
    public LinkedList breadthFirstKeyList(String keyFrom, String keyTo, int maxDepth) {

        if (hasNode(keyFrom) == false || hasNode(keyTo) == false) {
            throw new GraphException("Searching for path between one or more non-existent node, From: " + keyFrom + " To: " + keyTo);
        }

        if (keyFrom.equals(keyTo)) {
            throw new GraphException("Searching for path between the same node: " + keyFrom);
        }

        LinkedList allPaths = new LinkedList();

        LinkedList toSearchFrom = new LinkedList();

        LinkedList initialPath = new LinkedList();
        initialPath.pushFront(0);
        initialPath.pushBack(keyFrom);

        toSearchFrom.pushFront(initialPath);

        while (toSearchFrom.getSize() != 0) {
            LinkedList currentPath = (LinkedList) toSearchFrom.popFront();
            String currentKey = (String) currentPath.peekBack();
            GraphNode currentNode = findNode(currentKey);

            // If at desrtination
            if (currentNode.getKey().equals(keyTo)) {
                allPaths.pushBack(currentPath);
        
            // Else if not at max depth
            } else if (currentPath.getSize() <= maxDepth + 1) {
                for (int i = 1; i < currentNode.neighbours.getSize(); i ++) {
                    // Extend the current route by all nodes connected to the node the current route ends at
                    GraphNode mightSearch = ((GraphNode.GraphEdge) currentNode.neighbours.peekIndex(i)).getDestination();
                    String mightSearchKey = mightSearch.getKey();

                    // Check the node we might add hasn't already been visited by this path
                    currentPath.setIteratorAtHead();
                    currentPath.setIteratorNext(); // Skip the distance
                    boolean thisPathAlreadyVisited = false;

                    do {
                        if (mightSearchKey.equals((String) currentPath.getIteratorData())) {
                            thisPathAlreadyVisited = true;
                        }
                    } while (currentPath.setIteratorNext());
                    
                    // If this route hasn't visited this node add it to the list to search
                    if (thisPathAlreadyVisited == false) {
                        LinkedList newPath = new LinkedList(currentPath);
                        newPath.pushBack(mightSearchKey);
                        newPath.pushFront((int) newPath.popFront() + currentNode.getEdgeWeight(mightSearchKey));
                        toSearchFrom.pushBack(newPath);
                    }
                }
            }

            
        }   

        return allPaths;
    }
    
    // Get a Linked list containing the key for every node in the graph
    public LinkedList getAllKeys() {
        LinkedList allKeys = new LinkedList();

        if (nodeCount() != 0) {
            nodes.setIteratorAtHead();
            do {
                GraphNode current = (GraphNode) nodes.getIteratorData();
                allKeys.pushBack(current.getKey());
            } while (nodes.setIteratorNext());
        }

        

        return allKeys;
    }
}
