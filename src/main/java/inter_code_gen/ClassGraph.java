package inter_code_gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Collections;

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
            }
            if (currNode.equals(y)) {
                y_node = currNode;
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

    public List<String> topologicalSort() {
        List<String> orderedList = new ArrayList<>();

        while (hasUnmarkedPermNodes()) {
            visit(getUnmarkedPermNode(), orderedList);
        }

        // Prune for "null" nodes (ie nodes with no parent)
        orderedList.remove(null);

        Collections.reverse(orderedList);

        return orderedList;
    }

    public void print() {
        Iterator<Tuple<CGNode, CGNode> > edgeIterator = edges.iterator();
        while (edgeIterator.hasNext()) {
            Tuple<CGNode, CGNode> currEdge = edgeIterator.next();

            System.out.println(currEdge.x.name + " -> " + currEdge.y.name);
        }
    }

    // Helper functions for topological sort

    boolean hasUnmarkedPermNodes() {
        Iterator<CGNode> n =  nodes.iterator();
        while (n.hasNext()) {
            if (!n.next().permMark)
                return true;
        }

        return false;
    }

    CGNode getUnmarkedPermNode() {
        Iterator<CGNode> n =  nodes.iterator();
        while (n.hasNext()) {
            CGNode c = n.next();
            if (!c.permMark) return c;
        }

        return null;
    }

    // if edge n -> m exists, returns m
    CGNode getEdgePartner(CGNode n) {
        Iterator<Tuple<CGNode, CGNode> > edge = edges.iterator();
        while (edge.hasNext()) {
            Tuple<CGNode, CGNode> currEdge = edge.next();
            if (currEdge.x.equals(n.name))
                return currEdge.y;
        }

        return null;
    }

    void visit(CGNode node, List<String> list) {
        if (node.permMark)
            return;
        if (node.tempMark)
            System.out.println("CLASS GRAPH NOT DAG");

        node.giveTempMark();

        CGNode edgeMate = getEdgePartner(node);
        if (edgeMate != null)
            visit(edgeMate, list);

        node.removeTempMark();
        node.givePermMark();

        list.add(0, node.name);
    }
}
