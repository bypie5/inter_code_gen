package syntax_checker;

import syntaxtree.*;
import parser.*;

import java.util.ArrayList;
import java.util.List;

public class Typecheck {
  public static SymbolTable symbolTable;

  // Take file in from stdin (ie [program] < [input.file])
  public static MiniJavaParser parser = new MiniJavaParser(System.in);

  public static void typeCheck() {
    // Set up data
    symbolTable = new SymbolTable();
    SymbolTableConstructor firstVisitor = new SymbolTableConstructor();
    CheckVisitor<String> secondVisitor = new CheckVisitor<>();

    /*
      Use this so automatic testing works
      Probably will work for the final run too
    */
    parser.ReInit(System.in);

    try {
      Goal root = parser.Goal();

      // First pass; Give the visitor data it needs
      firstVisitor.root = root;
      firstVisitor.symbolTable = symbolTable;

      // Construct symbol table
      root.accept(firstVisitor);

      // Check to make sure class refs are not circular
      Graph classGraph = new Graph();
      List<String> classes = symbolTable.hashT.getAllItems();
      for (int i = 0; i < classes.size(); i++) {
        ClassBinder cb = (ClassBinder) symbolTable.get(Symbol.symbol(classes.get(i)));
        classGraph.addEdge(cb.parent, classes.get(i));
      }

      if (!classGraph.acyclic()) {
        firstVisitor.foundError = true;
      } else {

        // Second pass
        secondVisitor.root = root;
        secondVisitor.symbolTable = symbolTable;

        // Type check based off items stored in the symbol table
       root.accept(secondVisitor);
      }
    } catch (Exception e) {
      System.out.println("ERROR: " + e);
      e.printStackTrace();
    }

    // If the program makes it this far, it is correct
    if (!firstVisitor.foundError && !secondVisitor.foundError) {
      System.out.println("Program type checked successfully");
      //<final>//System.exit(0);
    }
    else {
      System.out.println("Type error");
      //<final>//System.exit(1);
    }
  }

  public static void main(String args[]) {
    typeCheck();
  }
}

class Graph {
  List<GraphNode> nodes;

  public Graph() {
    nodes = new ArrayList<>();
  }

  // Adds edge: n1 -> n2
  public void addEdge(String n1_key, String n2_key) {
    if (n1_key != null) {
      addNode(n1_key).next = addNode(n2_key);
    } else {
      addNode(n2_key);
    }
  }

  public void print() {
    GraphNode curr = nodes.get(0);

    while (curr != null && !curr.visited) {
      System.out.print(curr.value + " -> ");
      curr.visited = true;
      curr = curr.next;
    }
  }

  public boolean acyclic() {
    GraphNode curr = nodes.get(0);

    while (curr != null && !curr.visited) {
      if (curr.next != null && curr.next.visited) return false;

      curr.visited = true;
      curr = curr.next;
    }

    return true;
  }

  GraphNode addNode(String key) {
    GraphNode n = find(key);
    if (n != null || key == null)
      return n;

    GraphNode g = new GraphNode(key);
    nodes.add(g);
    return g;
  }

  GraphNode find(String key) {
    for (int i = 0; i < nodes.size(); i++) {
      if (key != null && nodes.get(i).value.equals(key))
        return nodes.get(i);
    }

    return null;
  }

}

class GraphNode {

  public String value;
  public GraphNode next;
  public boolean visited;

  public GraphNode(String value) {
    this.value = value;
    visited = false;
  }

  public void print() {
    if (next != null)
      System.out.println(value + " -> " + next.value);
    else
      System.out.println(value);
  }

}