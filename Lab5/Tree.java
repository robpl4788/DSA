package Lab5;

public class Tree {
    private class TreeNode { 
        // Put inside Tree class 
        private String m_key;
        private Object m_value;
        private TreeNode m_leftChild;
        private TreeNode m_rightChild;
        
        public TreeNode(String inKey, Object inVal) {
            if (inKey == null) throw new IllegalArgumentException("Key cannot be null"); 
            //consider “owning” the key 
            m_key = inKey;
            m_value = inVal;
            m_rightChild = null;
            m_leftChild = null;
        }
        
        public String getKey() { 
            return m_key;
        }
        
        public Object getValue() {
            return m_value;
        }
        
        public TreeNode getLeft() {
            return m_leftChild;
        }
        
        public void setLeft(TreeNode newLeft) {
            m_leftChild = newLeft;
        }
        
        public TreeNode getRight() {
            return m_rightChild;
        }
        
        public void setRight(TreeNode newRight) {
            m_rightChild = newRight;
        }
    }

    private TreeNode root = null;

    private TreeNode findParentInTree(String key, TreeNode current) {
        TreeNode result = null;
        if (current.getLeft() != null) {
            if (key.compareToIgnoreCase(current.getLeft().getKey()) == 0) {
                result = current;
            }
        } if (current.getRight() != null) {
            // System.out.println(key + " " + current.getRight().getKey());
            if (key.compareToIgnoreCase(current.getRight().getKey()) == 0) {
                result = current;
            }
        }

        if (result == null) {
            int compared = key.compareToIgnoreCase(current.getKey()); 
            if (compared < 0) {
                if (current.getLeft() == null) {
                    throw new TreeException("Key not in tree. Key is: " + key);
                } else {
                    result = findParentInTree(key, current.getLeft());
                }
            } else if (compared > 0) {
                if (current.getRight() == null) {
                    throw new TreeException("Key not in tree. Key is: " + key);
                } else {
                    result = findParentInTree(key, current.getRight());
                }
            }  else {
                throw new TreeException("Something bad happened, something that past me was fairly"
                        + " confident wouldn't happen so good luck lol"
                        + " key current: " + current.getKey()
                        + " key searchin for " + key);
            }
        }

        return result;
    }
    
    private TreeNode findInTree(String key, TreeNode current) {
        TreeNode result;
        int compared = key.compareToIgnoreCase(current.getKey()); 
        if (compared < 0) {
            if (current.getLeft() == null) {
                throw new TreeException("Key not in tree. Key is: " + key);
            } else {
                result = findInTree(key, current.getLeft());
            }
        } else if (compared > 0) {
            if (current.getRight() == null) {
                throw new TreeException("Key not in tree. Key is: " + key);
            } else {
                result = findInTree(key, current.getRight());
            }
        }  else {
            result = current;
        }

        return result;
    }

    public Object find(String key) {
        return findInTree(key, root).getValue();
    }
    
    private void insertIntoTree(TreeNode newNode, TreeNode current){
        int compared = newNode.getKey().compareToIgnoreCase(current.getKey()); 
        if (compared < 0) {
            if (current.getLeft() == null) {
                current.setLeft(newNode);
            } else {
                insertIntoTree(newNode, current.getLeft());
            }
        } else if (compared > 0) {
            if (current.getRight() == null) {
                current.setRight(newNode);
            } else {
                insertIntoTree(newNode, current.getRight());
            }
        } else {
            current = newNode;
            throw new TreeException("Key same as already existing, will overwrite, key is: " + newNode.getKey());
        }

    }

    public void insert(String key, Object value){
        if (root == null) {
            root = new TreeNode(key, value);
        } else {
            insertIntoTree(new TreeNode(key, value), root);
        }
    }

    public void delete(String key) {
        TreeNode toDelete = findInTree(key, root);
        TreeNode toDeletesParent = null;
        boolean deletingRoot = true;

        if (key.compareToIgnoreCase(root.getKey()) != 0) {
            toDeletesParent = findParentInTree(key, root);
            deletingRoot = false;
        }
        
        //No Children
        if (toDelete.getLeft() == null & toDelete.getRight() == null) {
            //Fix Parent
            if (toDelete == root) {
                root = null;
            } else {
                if (toDeletesParent.getLeft() != null && toDeletesParent.getLeft().getKey().equalsIgnoreCase(key)) {
                    toDeletesParent.setLeft(null);
                } else {
                    toDeletesParent.setRight(null);
                }
            }
        }

        //Left Child Only
        else if (toDelete.getLeft() != null & toDelete.getRight() == null) {
            //Fix Parent
            if (toDelete == root) {
                root = null;
            } else {
                if (toDeletesParent.getLeft() != null && toDeletesParent.getLeft().getKey().equalsIgnoreCase(key)) {
                    toDeletesParent.setLeft(toDelete.getLeft());
                } else {
                    toDeletesParent.setRight(toDelete.getLeft());
                }
            }
        }

        //Right Child Only
        else if (toDelete.getLeft() == null & toDelete.getRight() != null) {
            //Fix Parent
            if (toDelete == root) {
                root = null;
            } else {
                if (toDeletesParent.getLeft() != null && toDeletesParent.getLeft().getKey().equalsIgnoreCase(key)) {
                    toDeletesParent.setLeft(toDelete.getRight());
                } else {
                    toDeletesParent.setRight(toDelete.getRight());
                }
            }
        }

        //Both Children
        else if (toDelete.getLeft() != null & toDelete.getRight() != null) {
            //Successor is right child of toDelete
            if (toDelete.getRight().getLeft() == null) {
                TreeNode successor = toDelete.getRight();

                successor.setLeft(toDelete.getLeft());

                if (deletingRoot) {
                    root = successor;
                } else {
                    if (toDeletesParent.getLeft().getKey().equalsIgnoreCase(key)) {
                        toDeletesParent.setLeft(successor);
                    } else {
                        toDeletesParent.setRight(successor);
                    }
                }
                
            }
            //Successor is not right child of toDelete
            else {
                //Find Successor
                TreeNode successor = findMin(toDelete.getRight());
                TreeNode successorsParent = findParentInTree(successor.getKey(), toDelete);

                //Fix Successors parent
                if (successorsParent.getLeft() == successor) {
                    successorsParent.setLeft(successor.getRight());
                } else {
                    successorsParent.setRight(successor.getRight());
                }

                //Fix successppr
                successor.setLeft(toDelete.getLeft());
                successor.setRight(toDelete.getRight());

                //Fix toDeletes parent
                if (deletingRoot) {
                    root = successor;
                } else {
                    if (toDeletesParent.getLeft().getKey().equalsIgnoreCase(key)) {
                        toDeletesParent.setLeft(successor);
                    } else {
                        toDeletesParent.setRight(successor);
                    }
                }
                
            }
        }
    }
    
    private void printNodeKey(TreeNode toPrint, String prefix) {
        // String printString = "";
        // for (int i = 0; i < nodeDepth - 1; i ++) {
        //     if (rightEdge || leftEdge) {
        //         printString += " \t";
        //     } else {
        //         printString += "|\t";
        //     }
        // }
        // if (nodeDepth != 0) {
        //     printString += "|------ ";
        // }
        // printString += toPrint.getKey();

        System.out.println(prefix + " " + toPrint.getKey());
    }

    private void displayTree(TreeNode current, String prefix, boolean leftChild) {
        if (current.getRight() != null){
            if (prefix.length() != 0 && !leftChild) {
                displayTree(current.getRight(), prefix.substring(0, prefix.length() - 1) + "\t|", false);
            } else {
                displayTree(current.getRight(), prefix + "\t|", false);
            }
        }
        
        printNodeKey(current, prefix + "------");

        if (current.getLeft() != null) {
            displayTree(current.getLeft(), prefix + "\t|", true);
        }
    }

    public void display() {
        displayTree(root, "", false);
    }
    
    private int findHeight(TreeNode current) {
        int height;
        if (current.getLeft() == null) {
            height = 1;
        } else {
            height = findHeight(current.getLeft()) + 1;
        }

        if (current.getRight() == null) {
            //Height is left height
        } else {
            height = Math.max(height, findHeight(current.getRight()) + 1);
        }
        return height;
    }

    public int height() {
        return findHeight(root);
    }

    private TreeNode findMin(TreeNode current) {
        TreeNode result;

        if (current.getLeft() == null) {
            result = current;
        } else {
            result = findMin(current.getLeft());
        }

        return result;
    }

    public Object min() {
        return findMin(root).getKey();
    }

    private TreeNode findMax(TreeNode current) {
        TreeNode result;

        if (current.getRight() == null) {
            result = current;
        } else {
            result = findMax(current.getRight());
        }

        return result;
    }

    public Object max() {
        return findMax(root).getKey();
    }

    private String appendNodeKeyInOrder(String currentOutput, TreeNode current) {
        if (current.getLeft() != null){
            currentOutput = appendNodeKeyInOrder(currentOutput, current.getLeft());
        }
        
        currentOutput += current.getKey() + "\t";

        if (current.getRight() != null) {
            currentOutput = appendNodeKeyInOrder(currentOutput, current.getRight());
        }

        return currentOutput;
    }

    public String keysInOrder() {
        String keys = "";
        return appendNodeKeyInOrder(keys, root);
    }

    private String appendNodeKeyPreOrder(String currentOutput, TreeNode current) {
        currentOutput += current.getKey() + "\t";

        if (current.getLeft() != null){
            currentOutput = appendNodeKeyPreOrder(currentOutput, current.getLeft());
        }

        if (current.getRight() != null) {
            currentOutput = appendNodeKeyPreOrder(currentOutput, current.getRight());
        }

        return currentOutput;
    }

    public String keysPreOrder() {
        String keys = "";
        return appendNodeKeyPreOrder(keys, root);
    }

    private String appendNodeKeyPostOrder(String currentOutput, TreeNode current) {
        if (current.getLeft() != null){
            currentOutput = appendNodeKeyPostOrder(currentOutput, current.getLeft());
        }

        if (current.getRight() != null) {
            currentOutput = appendNodeKeyPostOrder(currentOutput, current.getRight());
        }

        currentOutput += current.getKey() + "\t";

        return currentOutput;
    }

    public String keysPostOrder() {
        String keys = "";
        return appendNodeKeyPostOrder(keys, root);    
    }

    private int countLeaves(TreeNode current) {
        int total = 0;
        if (current.getLeft() != null) {
            total += countLeaves(current.getLeft());
        }
        
        if (current.getRight() != null) {
            total += countLeaves(current.getRight());
        }
        
        if (total == 0) {
            total = 1;
        }

        return total;
    }

    public double balance() {
        //Should return 100 for a perfect tree and 0 for a degenerate tree
        //For a tree with n nodes:
        //n != 1: balance = (number of leaf nodes - 1) / (max number of leaf nodes - 1)
        //n == 1: balance = 100;
        
        double output;

        int treeHeight = height();

        if (treeHeight == 1) {
            output = 100.1;
        } else {
            int leafCount = countLeaves(root);
            //System.out.println(leafCount);
	    double maxLeaves = Math.pow(2, treeHeight - 1);
            output = (leafCount - 1.0) / (maxLeaves - 1) * 100;
        }

        return output;
    }
}
