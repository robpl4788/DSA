package DSA_Assignment_21494561.DataTypes;


public class Graph {

    public class GraphException extends RuntimeException {
        public GraphException(String s) {
            super(s);
        }
    }

    private class GraphNode {
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

        void addNeighbour (GraphNode newNeighbour, int weight) {
            int index = 0;

            if (neighbours.getSize() != 0) {
                neighbours.setIteratorAtHead();
                do {
                    GraphNode current = ((GraphEdge) neighbours.getIteratorData()).getDestination();
                    int keyComparison = current.getKey().compareToIgnoreCase(newNeighbour.getKey());
                    // System.out.println(keyComparison);
                    if (keyComparison < 0) {
                        index ++;
                    } else if (keyComparison == 0) {
                        throw new GraphException("Trying to add edge that already exists, from: " + key + ", to: " + newNeighbour.getKey());

                    }
                } while (neighbours.setIteratorNext());
            }
            // System.out.println("Connect " + key + " to " + newNeighbour.getKey() + " at index: " + index);

            // for (int i = 0; i < neighbours.getSize(); i ++) {
            //     System.out.println(newNeighbour.getKey().compareToIgnoreCase(((GraphNode) neighbours.peekIndex(i)).getKey()) > 0);
            //     if (newNeighbour.getKey().compareToIgnoreCase(((GraphNode) neighbours.peekIndex(i)).getKey()) < 0) {
            //         index = neighbours.getSize();
            //     } else if (newNeighbour.getKey().compareToIgnoreCase(((GraphNode) neighbours.peekIndex(i)).getKey()) == 0) {
            //     }
            // }

            // System.out.println(neighbours.getSize());
            neighbours.pushIndex(index, new GraphEdge(newNeighbour, weight));
            // System.out.println(neighbours.getSize());
        }

        boolean hasNeighbour(String key) {
            boolean hasNeighbour = false;
            if (neighbours.getSize() != 0) {
                // System.out.println("");

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
    


        int getEdgeWeight(String keyTo) {

            boolean hasNeighbour = false;
            int edgeWeight = 0;
            // System.out.println("Looking for: " + keyTo);

            if (neighbours.getSize() != 0) {
                // System.out.println("");

                neighbours.setIteratorAtHead();
                do {
                    GraphEdge currentEdge = (GraphEdge) neighbours.getIteratorData();
                    // System.out.println(currentEdge.getDestination().getKey());

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

    LinkedList nodes = new LinkedList();

    public void addNode(String key, Object data) {
        nodes.pushBack(new GraphNode(key, data));
    }

    public void addDirectedEdge(String keyFrom, String keyTo, int weight) {
        GraphNode node1 = findNode(keyFrom);
        GraphNode node2 = findNode(keyTo);

        // System.out.println("Connect " + node1.getKey() + " to " + node2.getKey() + ", directed");
 

        node1.addNeighbour(node2, weight);
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

    public void deleteNode(String key) {
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

    public void deleteDirectedEdge(String keyFrom, String keyTo) {
        GraphNode from = findNode(keyFrom);
        from.popNeighbour(keyTo);
    }

    public boolean hasNode(String key) {
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

    public boolean hasEdge(String keyFrom, String keyTo) {
        GraphNode from = findNode(keyFrom);
        return from.hasNeighbour(keyTo);
    }

    public int edgeWeight(String keyFrom, String keyTo) {
        GraphNode from = findNode(keyFrom);
        return from.getEdgeWeight(keyTo);
    }

    public int nodeCount () {
        return nodes.getSize();
    }

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

    public Object getData(String key) {
        return findNode(key).getData();
    }

    public String getAdjacentKeys(String key) {
        GraphNode node = findNode(key);
        return node.getAdjacentKeys();
    }

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
            if (currentNode.getKey().equals(keyTo)) {
                allPaths.pushBack(currentPath);
            } else if (currentPath.getSize() <= maxDepth + 1) {
                for (int i = 1; i < currentNode.neighbours.getSize(); i ++) {
                    GraphNode mightSearch = ((GraphNode.GraphEdge) currentNode.neighbours.peekIndex(i)).getDestination();
                    String mightSearchKey = mightSearch.getKey();

                    currentPath.setIteratorAtHead();
                    currentPath.setIteratorNext();
                    boolean thisPathAlreadyVisited = false;
                    do {
                        
                        if (mightSearchKey.equals((String) currentPath.getIteratorData())) {
                            thisPathAlreadyVisited = true;
                            // System.out.println(mightSearchKey + " = " +  currentPath.getIteratorData());
                        } //else {
                            // System.out.println(mightSearchKey + " != " +  currentPath.getIteratorData());
                        // }
                    } while (currentPath.setIteratorNext());
                    
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
