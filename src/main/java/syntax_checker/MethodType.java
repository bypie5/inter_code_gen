package syntax_checker;

import syntaxtree.*;

public class MethodType {
  public Type returnType;
  public NodeOptional argumentList;

  public MethodType(Type returnType, NodeOptional argumentList) {
    this.returnType = returnType;
    this.argumentList = argumentList;
  }

  public boolean equals(MethodType rhs) {
    if (this.returnType.f0.which == rhs.returnType.f0.which) {
      if (!this.argumentList.present() && !rhs.argumentList.present()) {
        return true;
      } else {
        if (
          this.argumentList.present() && rhs.argumentList.present() &&
          ((FormalParameterList)this.argumentList.node).f1.size() == ((FormalParameterList)rhs.argumentList.node).f1.size()
        ) 
          return true;   
      }
    }
    return false;
  }
}
