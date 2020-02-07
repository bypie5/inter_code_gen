package inter_code_gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class ClassGraph {
    List<CGNode> nodes;
    public List< Tuple<CGNode, CGNode> > edges;

    public ClassGraph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void addEdge(String x, String y) {

        CGNode x_node = null;
        CGNode y_node = null;

        Iterator<CGNode> nodeIterator = nodes.iterator();
        while (nodeIterator.hasNext()) {
            CGNode currNode = nodeIterator.next();
            if (currNode.equals(x)) {
                x_node = currNode; // Node exists in list already
                break;
            }
            if (currNode.equals(y)) {
                y_node = currNode;
                break;
            }
        }

        // Node needs to be added to list
        if (x_node == null) {
            x_node = new CGNode(x);
            nodes.add(x_node);
        }

        if (y_node == null) {
            y_node = new CGNode(y);
            nodes.add(y_node);
        }

        edges.add(new Tuple<>(x_node, y_node));
    }

    public void print() {
        Iterator<Tuple<CGNode, CGNode> > edgeIterator = edges.iterator();
        while (edgeIterator.hasNext()) {
            Tuple<CGNode, CGNode> currEdge = edgeIterator.next();

            System.out.println(currEdge.x.name + " -> " + currEdge.y.name);
        }
    }
}
