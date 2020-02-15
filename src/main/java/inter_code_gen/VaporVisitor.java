package inter_code_gen;

import visitor.*;
import syntaxtree.*;
import syntax_checker.*;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class VaporVisitor<R,A> implements GJVisitor<R,A>  {

    public GenerateVapor gv;

    //ClassBinder currClass = null;
    //MethodsBinder currMethod = null;

    ClassBinder currClassBinder = null;
    String currClass = "";
    String currMethod = "";

    // Temporary variable count
    int tempCount = 0;
    int labelCount = 0;

    List<String> funcCallTemps = new ArrayList<>();
    List<MethodsBinder> funcCallTypes = new ArrayList<>();

    static String trueString = "1";
    static String falseString = "0";

    String classname(MainClass mc) {
        return mc.f1.f0.toString();
    }

    String classname(ClassDeclaration c) {
        return c.f1.f0.toString();
    }

    String classname(ClassExtendsDeclaration c) {
        return c.f1.f0.toString();
    }

    String methodname(MethodDeclaration m) {
        return m.f2.f0.toString();
    }

    String createTemp() {
        String var = "t." + tempCount;
        tempCount++;
        return var;
    }

    String createLabel() {
        String var = "l." + labelCount;
        labelCount++;
        return var;
    }

    ClassRecord findRecord(String className) {
        Iterator<ClassRecord> iter = J2V.classRecords.iterator();
        while (iter.hasNext()) {
            ClassRecord currRecord = iter.next();
            if (currRecord.classname.equals(className)) {
                return currRecord;
            }
        }
        return null;
    }

    int getMethodOffset(String className, String methodName) {
        return findRecord(className).getMethodOffset(methodName);
    }

    String getMethodLabel(String className, String methodName) {
        return findRecord(className).getMethodLabel(getMethodOffset(className, methodName));
    }

    List<String> getMethodArgs(String className, String methodName) {
        List<String> args = new ArrayList<>();

        ClassBinder currClass = (ClassBinder) Typecheck.symbolTable.get(Symbol.symbol(className));
        MethodsBinder currMethod = (MethodsBinder) currClass.methods.get(Symbol.symbol(methodName));

        for (int i = 0; i < currMethod.params.size(); i++) {
            args.add(currMethod.params.get(i));
        }

        return args;
    }

    //
    // Auto class visitors--probably don't need to be overridden.
    //
    public R visit(NodeList n, A argu) {
        R _ret=null;
        int _count=0;
        for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this,argu);
            _count++;
        }
        return _ret;
    }

    public R visit(NodeListOptional n, A argu) {
        if ( n.present() ) {
            R _ret=null;
            int _count=0;
            for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
                e.nextElement().accept(this,argu);
                _count++;
            }
            return _ret;
        }
        else
            return null;
    }

    public R visit(NodeOptional n, A argu) {
        if ( n.present() )
            return n.node.accept(this,argu);
        else
            return null;
    }

    public R visit(NodeSequence n, A argu) {
        R _ret=null;
        int _count=0;
        for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this,argu);
            _count++;
        }
        return _ret;
    }

    public R visit(NodeToken n, A argu) { return null; }

    //
    // User-generated visitor methods below
    //

    /**
     * f0 -> MainClass()
     * f1 -> ( TypeDeclaration() )*
     * f2 -> <EOF>
     */
    public R visit(Goal n, A argu) {
        R _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "{"
     * f3 -> "public"
     * f4 -> "static"
     * f5 -> "void"
     * f6 -> "main"
     * f7 -> "("
     * f8 -> "String"
     * f9 -> "["
     * f10 -> "]"
     * f11 -> Identifier()
     * f12 -> ")"
     * f13 -> "{"
     * f14 -> ( VarDeclaration() )*
     * f15 -> ( Statement() )*
     * f16 -> "}"
     * f17 -> "}"
     */
    public R visit(MainClass n, A argu) {
        R _ret=null;

        gv.addLine("func Main()");
        gv.increaseIndent();

        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        n.f6.accept(this, argu);
        n.f7.accept(this, argu);
        n.f8.accept(this, argu);
        n.f9.accept(this, argu);
        n.f10.accept(this, argu);
        n.f11.accept(this, argu);
        n.f12.accept(this, argu);
        n.f13.accept(this, argu);
        n.f14.accept(this, argu);
        n.f15.accept(this, argu);
        n.f16.accept(this, argu);
        n.f17.accept(this, argu);

        gv.addLine("ret");
        gv.descreaseIndent();

        gv.addLine("");

        return _ret;
    }

    /**
     * f0 -> ClassDeclaration()
     *       | ClassExtendsDeclaration()
     */
    public R visit(TypeDeclaration n, A argu) {
        R _ret=null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "{"
     * f3 -> ( VarDeclaration() )*
     * f4 -> ( MethodDeclaration() )*
     * f5 -> "}"
     */
    public R visit(ClassDeclaration n, A argu) {
        R _ret=null;

        currClassBinder = (ClassBinder) Typecheck.symbolTable.get(Symbol.symbol(classname(n)));
        currClass = classname(n);
        currMethod = null;

        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "extends"
     * f3 -> Identifier()
     * f4 -> "{"
     * f5 -> ( VarDeclaration() )*
     * f6 -> ( MethodDeclaration() )*
     * f7 -> "}"
     */
    public R visit(ClassExtendsDeclaration n, A argu) {
        R _ret=null;

        currClassBinder = (ClassBinder) Typecheck.symbolTable.get(Symbol.symbol(classname(n)));
        currClass = classname(n);
        currMethod = null;

        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        n.f6.accept(this, argu);
        n.f7.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     * f2 -> ";"
     */
    public R visit(VarDeclaration n, A argu) {
        R _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);

        //gv.buffer.add(n.f1.f0.toString());

        return _ret;
    }

    /**
     * f0 -> "public"
     * f1 -> Type()
     * f2 -> Identifier()
     * f3 -> "("
     * f4 -> ( FormalParameterList() )?
     * f5 -> ")"
     * f6 -> "{"
     * f7 -> ( VarDeclaration() )*
     * f8 -> ( Statement() )*
     * f9 -> "return"
     * f10 -> Expression()
     * f11 -> ";"
     * f12 -> "}"
     */
    public R visit(MethodDeclaration n, A argu) {
        R _ret=null;

        //currMethod = (MethodsBinder) currClass.methods.get(Symbol.symbol(methodname(n)));
        currMethod = methodname(n);


        List<String> args = getMethodArgs(currClass, currMethod);

        String argString = "";
        Iterator<String> iter = args.iterator();
        while(iter.hasNext()) {
            argString += " " + iter.next();
        }
        gv.addLine("func " + getMethodLabel(currClass, currMethod) + "(this" + argString + ")");

        gv.increaseIndent();

        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        n.f6.accept(this, argu);
        n.f7.accept(this, argu);
        n.f8.accept(this, argu);
        n.f9.accept(this, argu);
        String retExp = (String) n.f10.accept(this, argu);
        n.f11.accept(this, argu);
        n.f12.accept(this, argu);

        gv.addLine("ret " + retExp);

        gv.descreaseIndent();

        gv.addLine("");

        return _ret;
    }

    /**
     * f0 -> FormalParameter()
     * f1 -> ( FormalParameterRest() )*
     */
    public R visit(FormalParameterList n, A argu) {
        R _ret=null;
        _ret = n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     */
    public R visit(FormalParameter n, A argu) {
        R _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);

        _ret = (R) n.f1.f0.toString();

        return _ret;
    }

    /**
     * f0 -> ","
     * f1 -> FormalParameter()
     */
    public R visit(FormalParameterRest n, A argu) {
        R _ret=null;
        n.f0.accept(this, argu);
        _ret = n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> ArrayType()
     *       | BooleanType()
     *       | IntegerType()
     *       | Identifier()
     */
    public R visit(Type n, A argu) {
        R _ret=null;
        _ret = n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "int"
     * f1 -> "["
     * f2 -> "]"
     */
    public R visit(ArrayType n, A argu) {
        R _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "boolean"
     */
    public R visit(BooleanType n, A argu) {
        R _ret=null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "int"
     */
    public R visit(IntegerType n, A argu) {
        R _ret=null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Block()
     *       | AssignmentStatement()
     *       | ArrayAssignmentStatement()
     *       | IfStatement()
     *       | WhileStatement()
     *       | PrintStatement()
     */
    public R visit(Statement n, A argu) {
        R _ret=null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "{"
     * f1 -> ( Statement() )*
     * f2 -> "}"
     */
    public R visit(Block n, A argu) {
        R _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "="
     * f2 -> Expression()
     * f3 -> ";"
     */
    public R visit(AssignmentStatement n, A argu) {
        R _ret=null;
        String idName = (String) n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        String expVal = (String) n.f2.accept(this, argu);
        n.f3.accept(this, argu);

        // <Type>::<name>
        if (expVal.contains("::")) {
            int div = expVal.indexOf("::");
            String allocType = expVal.substring(0, div);
            String varName = expVal.substring(div + 2);
            expVal = varName;
        }

        if (findRecord(currClass) != null) {
            int fieldOffset = findRecord(currClass).getFieldOffset(idName);
            if (fieldOffset != -1) {
                idName = "[this + " + (fieldOffset * 4) + "]";
            }
        }

        gv.addLine(idName + " = " + expVal);

        return _ret;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "["
     * f2 -> Expression()
     * f3 -> "]"
     * f4 -> "="
     * f5 -> Expression()
     * f6 -> ";"
     */
    public R visit(ArrayAssignmentStatement n, A argu) {
        R _ret=null;
        String baseAddr = (String) n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        String offset = (String)n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        String assign = (String) n.f5.accept(this, argu);
        n.f6.accept(this, argu);

        if (findRecord(currClass) != null) {
            int fieldOffset = findRecord(currClass).getFieldOffset(baseAddr);
            if (fieldOffset != -1) {
                String temp = createTemp();
                gv.addLine(temp + " = Add(this " + (fieldOffset * 4) + ")");
                gv.addLine(temp + " = [" + temp + "]");
                baseAddr = temp;
            }
        }

        String alignedOffset = createTemp();
        gv.addLine(alignedOffset + " = MulS(" + offset + " 4)");
        String index = createTemp();
        gv.addLine(index + " = Add(" + baseAddr + " " + alignedOffset + ")");
        gv.addLine(index + " = Add(" + index + " 4)");
        gv.addLine("[" + index + "] = " + assign);

        return _ret;
    }

    /**
     * f0 -> "if"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> Statement()
     * f5 -> "else"
     * f6 -> Statement()
     */
    public R visit(IfStatement n, A argu) {
        R _ret=null;

        String elseLabel = createLabel();
        String endIf = createLabel();

        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        String boolResult = (String) n.f2.accept(this, argu);
        n.f3.accept(this, argu);

        gv.addLine("if0 " + boolResult + " goto " + ":" + elseLabel);

        gv.increaseIndent();
        n.f4.accept(this, argu);
        gv.addLine("goto :" + endIf);
        gv.descreaseIndent();
        gv.addLine(elseLabel + ":");
        gv.increaseIndent();

        n.f5.accept(this, argu);
        n.f6.accept(this, argu);

        gv.descreaseIndent();
        gv.addLine(endIf + ":");

        return _ret;
    }

    /**
     * f0 -> "while"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> Statement()
     */
    public R visit(WhileStatement n, A argu) {
        R _ret=null;

        String testL = createLabel();
        String doneL = createLabel();

        gv.addLine(testL + ":");

        gv.increaseIndent();

        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        String exp = (String) n.f2.accept(this, argu);

        gv.addLine("if0 " + exp + " goto " + ":" + doneL);

        n.f3.accept(this, argu);
        n.f4.accept(this, argu);

        gv.descreaseIndent();

        gv.addLine("goto :" + testL);
        gv.addLine(doneL + ":");

        return _ret;
    }

    /**
     * f0 -> "System.out.println"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> ";"
     */
    public R visit(PrintStatement n, A argu) {
        R _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        R printVal = n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);

        if (findRecord(currClass) != null) {
            int fieldOffset = findRecord(currClass).getFieldOffset((String)printVal);
            if (fieldOffset != -1) {
                String temp_rhs = createTemp();
                gv.addLine(temp_rhs + " = [this + " + (fieldOffset * 4) + "]");
                printVal = (R) temp_rhs;
            }
        }

        gv.addLine("PrintIntS(" + printVal + ")");

        return _ret;
    }

    /**
     * f0 -> AndExpression()
     *       | CompareExpression()
     *       | PlusExpression()
     *       | MinusExpression()
     *       | TimesExpression()
     *       | ArrayLookup()
     *       | ArrayLength()
     *       | MessageSend()
     *       | PrimaryExpression()
     */
    public R visit(Expression n, A argu) {
        R _ret=null;
        _ret = n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "&&"
     * f2 -> PrimaryExpression()
     */
    public R visit(AndExpression n, A argu) {
        R _ret=null;
        R rhs = n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        R lhs = n.f2.accept(this, argu);

        //if

        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "<"
     * f2 -> PrimaryExpression()
     */
    public R visit(CompareExpression n, A argu) {
        R _ret=null;
        R rhs = n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        R lhs = n.f2.accept(this, argu);

        if (findRecord(currClass) != null) {
            int fieldOffset = findRecord(currClass).getFieldOffset((String)rhs);
            if (fieldOffset != -1) {
                String temp_rhs = createTemp();
                gv.addLine(temp_rhs + " = [this + " + (fieldOffset * 4) + "]");
                rhs = (R) temp_rhs;
            }
        }

        if (findRecord(currClass) != null) {
            int fieldOffset = findRecord(currClass).getFieldOffset((String)lhs);
            if (fieldOffset != -1) {
                String temp_lhs = createTemp();
                gv.addLine(temp_lhs + " = [this + " + (fieldOffset * 4) + "]");
                lhs = (R) temp_lhs;
            }
        }

        String result = createTemp();
        gv.addLine(result + " = LtS(" + rhs + " " + lhs + ")");

        _ret = (R) result;

        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "+"
     * f2 -> PrimaryExpression()
     */
    public R visit(PlusExpression n, A argu) {
        R _ret=null;
        R rhs = n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        R lhs = n.f2.accept(this, argu);


        if (findRecord(currClass) != null) {
            int fieldOffset = findRecord(currClass).getFieldOffset((String)rhs);
            if (fieldOffset != -1) {
                String temp_rhs = createTemp();
                gv.addLine(temp_rhs + " = [this + " + (fieldOffset * 4) + "]");
                rhs = (R) temp_rhs;
            }
        }

        if (findRecord(currClass) != null) {
            int fieldOffset = findRecord(currClass).getFieldOffset((String)lhs);
            if (fieldOffset != -1) {
                String temp_lhs = createTemp();
                gv.addLine(temp_lhs + " = [this + " + (fieldOffset * 4) + "]");
                lhs = (R) temp_lhs;
            }
        }

        String result = createTemp();
        gv.addLine(result + " = " + "Add(" + rhs + " " + lhs + ")");

        _ret = (R) result;

        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "-"
     * f2 -> PrimaryExpression()
     */
    public R visit(MinusExpression n, A argu) {
        R _ret=null;
        R rhs = n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        R lhs = n.f2.accept(this, argu);

        if (findRecord(currClass) != null) {
            int fieldOffset = findRecord(currClass).getFieldOffset((String)rhs);
            if (fieldOffset != -1) {
                String temp_rhs = createTemp();
                gv.addLine(temp_rhs + " = [this + " + (fieldOffset * 4) + "]");
                rhs = (R) temp_rhs;
            }
        }

        if (findRecord(currClass) != null) {
            int fieldOffset = findRecord(currClass).getFieldOffset((String)lhs);
            if (fieldOffset != -1) {
                String temp_lhs = createTemp();
                gv.addLine(temp_lhs + " = [this + " + (fieldOffset * 4) + "]");
                lhs = (R) temp_lhs;
            }
        }

        String result = createTemp();
        gv.addLine(result + " = " + "Sub(" + rhs + " " + lhs + ")");

        _ret = (R) result;

        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "*"
     * f2 -> PrimaryExpression()
     */
    public R visit(TimesExpression n, A argu) {
        R _ret=null;
        R rhs = n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        R lhs = n.f2.accept(this, argu);

        if (findRecord(currClass) != null) {
            int fieldOffset = findRecord(currClass).getFieldOffset((String)rhs);
            if (fieldOffset != -1) {
                String temp_rhs = createTemp();
                gv.addLine(temp_rhs + " = [this + " + (fieldOffset * 4) + "]");
                rhs = (R) temp_rhs;
            }
        }

        if (findRecord(currClass) != null) {
            int fieldOffset = findRecord(currClass).getFieldOffset((String)lhs);
            if (fieldOffset != -1) {
                String temp_lhs = createTemp();
                gv.addLine(temp_lhs + " = [this + " + (fieldOffset * 4) + "]");
                lhs = (R) temp_lhs;
            }
        }

        String result = createTemp();
        gv.addLine(result + " = " + "MulS(" + rhs + " " + lhs + ")");

        _ret = (R) result;

        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "["
     * f2 -> PrimaryExpression()
     * f3 -> "]"
     */
    public R visit(ArrayLookup n, A argu) {
        R _ret=null;
        String ptr = (String) n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        String offset = (String) n.f2.accept(this, argu);
        n.f3.accept(this, argu);

        // TODO: Check if the value of [<val>] is gte 0
        String result = createTemp();
        String alignedOffset = createTemp();
        String lp = createLabel();
        String l = createLabel();
        gv.addLine(alignedOffset + " = " + offset);
        // alignedOffset is now the index
        String baseAddress = createTemp();
        if (findRecord(currClass) != null) {
            int fieldOffset = findRecord(currClass).getFieldOffset(ptr);
            if (fieldOffset != -1) {
                String temp = createTemp();
                gv.addLine(temp + " = [this + " + (fieldOffset * 4) + "]");
                ptr = temp;
            }
        }

        gv.addLine(baseAddress + " = [" + ptr + "]");

        gv.addLine("ok = LtS(" + alignedOffset + " " + baseAddress + ")");
        gv.addLine("if ok goto :" + lp);
        gv.addLine("Error(\"Array index out of bounds\")");
        gv.addLine(lp+":");
        gv.addLine("ok = LtS(-1 " + offset + ")");
        gv.addLine("if ok goto :" + l);
        gv.addLine("Error(\"Array index out of bounds\")");
        gv.addLine(l + ":");
        gv.addLine(alignedOffset + " = MulS(" + offset + " 4)");
        gv.addLine(alignedOffset + " = Add(" + ptr + " " + alignedOffset + ")");
        gv.addLine(alignedOffset + " = Add(" + alignedOffset + " 4)");
        gv.addLine(result + " = [" + alignedOffset + "]");

        _ret = (R) result;

        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> "length"
     */
    public R visit(ArrayLength n, A argu) {
        R _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> Identifier()
     * f3 -> "("
     * f4 -> ( ExpressionList() )?
     * f5 -> ")"
     */
    public R visit(MessageSend n, A argu) {
        R _ret=null;

        // TODO: Handle when funcOwner is not a "new <Class>" statement
        R funcOwner = n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        String args = (String) n.f4.accept(this, argu);
        n.f5.accept(this, argu);

        String vtableBase = createTemp();
        String result = createTemp();
        String methodName = n.f2.f0.toString();

        // Primary expression was an allocation
        // <Type>::<name>
        if (((String) funcOwner).contains("::")) {
            int div = ((String) funcOwner).indexOf("::");

            String allocType = ((String) funcOwner).substring(0, div);
            String varName = ((String) funcOwner).substring(div + 2);

            int offset = getMethodOffset(allocType, methodName);

            gv.addLine(vtableBase + " = [" + varName + "]"); // Get
            gv.addLine(vtableBase + " = [" + vtableBase + "]");
            gv.addLine(vtableBase + " = [" + vtableBase + " + " + offset*4 + "]");
            if (args != null)
                gv.addLine(result + " = call " + vtableBase + "(" + varName + " " + args + ")");
            else
                gv.addLine(result + " = call " + vtableBase + "(" + varName + ")");
        } else {
            if (funcOwner.equals("this")) {
                int offset = getMethodOffset(currClass, methodName);

                String funcPtr = createTemp();
                gv.addLine(funcPtr + " = [this]");
                gv.addLine(funcPtr + " = [" + funcPtr + "]");
                gv.addLine(funcPtr + " = [" + funcPtr + " + " + offset * 4 +  "]");
                if (args != null)
                    gv.addLine(result + " = call " + funcPtr + "(this " + args + ")");
                else
                    gv.addLine(result + " = call " + funcPtr + "(this)");
            } else if (funcCallTemps.contains(funcOwner)) {
                // Nested function call
                int index = funcCallTemps.indexOf(funcOwner);
                String classReturned = funcCallTypes.get(index).getClassReturned();

                int offset = getMethodOffset(classReturned, methodName);

                gv.addLine(funcOwner + " = [" + funcOwner + "]");
                gv.addLine(funcOwner + " = [" + funcOwner + "]");
                gv.addLine(funcOwner + " = [" + funcOwner + " + " + (offset * 4) + "]");
                if (args != null)
                    gv.addLine(result + " = call " + funcOwner + "(this " + args + ")");
                else
                    gv.addLine(result + " = call " + funcOwner + "(this)");

            } else {
                // Fetch type of funcOwner
                ClassBinder cb = (ClassBinder) Typecheck.symbolTable.get(Symbol.symbol(currClass));
                MethodsBinder mb = (MethodsBinder) cb.methods.get(Symbol.symbol(methodName));
                String type = cb.getIdType((String) funcOwner, currMethod);

                if (type != null) {
                    int offset = getMethodOffset(type, methodName);

                    gv.addLine(funcOwner + " = [" + funcOwner + "]");
                    gv.addLine(funcOwner + " = [" + funcOwner + "]");
                    gv.addLine(funcOwner + " = [" + funcOwner + " + " + (offset * 4) + "]");
                    if (args != null)
                        gv.addLine(result + " = call " + funcOwner + "(this " + args + ")");
                    else
                        gv.addLine(result + " = call " + funcOwner + "(this)");

                    funcCallTemps.add(result);
                    funcCallTypes.add(mb);
                }
            }
        }

        _ret = (R) result;

        return _ret;
    }

    /**
     * f0 -> Expression()
     * f1 -> ( ExpressionRest() )*
     */
    public R visit(ExpressionList n, A argu) {
        R _ret=null;
        String exp = (String) n.f0.accept(this, argu);
        n.f1.accept(this, argu);

        String rest = "";

        if (n.f1.present()) {
            for (int i = 0; i < n.f1.size(); i++) {
                String r = (String) n.f1.elementAt(i).accept(this, argu);
                rest = rest + " " + r;
            }
        }

        _ret = (R) (exp + rest);

        return _ret;
    }

    /**
     * f0 -> ","
     * f1 -> Expression()
     */
    public R visit(ExpressionRest n, A argu) {
        R _ret=null;
        n.f0.accept(this, argu);
        String exp = (String) n.f1.accept(this, argu);
        _ret = (R) exp;
        return _ret;
    }

    /**
     * f0 -> IntegerLiteral()
     *       | TrueLiteral()
     *       | FalseLiteral()
     *       | Identifier()
     *       | ThisExpression()
     *       | ArrayAllocationExpression()
     *       | AllocationExpression()
     *       | NotExpression()
     *       | BracketExpression()
     */
    public R visit(PrimaryExpression n, A argu) {
        R _ret=null;
        _ret = n.f0.accept(this, argu);

        return _ret;
    }

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    public R visit(IntegerLiteral n, A argu) {
        R _ret=null;
        n.f0.accept(this, argu);

        _ret = (R)n.f0.toString();

        return _ret;
    }

    /**
     * f0 -> "true"
     */
    public R visit(TrueLiteral n, A argu) {
        R _ret=null;
        n.f0.accept(this, argu);

        // GT 0 is true
        _ret = (R) trueString;

        return _ret;
    }

    /**
     * f0 -> "false"
     */
    public R visit(FalseLiteral n, A argu) {
        R _ret=null;
        n.f0.accept(this, argu);

        // 0 is false
        _ret = (R) falseString;

        return _ret;
    }

    /**
     * f0 -> <IDENTIFIER>
     */
    public R visit(Identifier n, A argu) {
        R _ret=null;
        n.f0.accept(this, argu);

        String idName = n.f0.toString();

        _ret = (R) idName;

        return _ret;
    }

    /**
     * f0 -> "this"
     */
    public R visit(ThisExpression n, A argu) {
        R _ret=null;
        n.f0.accept(this, argu);

        _ret = (R) "this";

        return _ret;
    }

    /**
     * f0 -> "new"
     * f1 -> "int"
     * f2 -> "["
     * f3 -> Expression()
     * f4 -> "]"
     */
    public R visit(ArrayAllocationExpression n, A argu) {
        R _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        R sizeStr = n.f3.accept(this, argu);
        n.f4.accept(this, argu);

        String arrayName = createTemp();
        String sizeOffset = createTemp();

        gv.addLine(sizeOffset + " = MulS(" + sizeStr + " 4)");
        gv.addLine(sizeOffset + " = Add(" + sizeOffset + " 4)");
        gv.addLine(arrayName + " = HeapAllocZ(" + sizeOffset + ")");

        // Store the size (in index) of the array in the base of the array
        gv.addLine("[" + arrayName + "] = " + sizeStr);

        _ret = (R) arrayName;

        return _ret;
    }

    /**
     * f0 -> "new"
     * f1 -> Identifier()
     * f2 -> "("
     * f3 -> ")"
     */
    public R visit(AllocationExpression n, A argu) {
        R _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);

        String classString = n.f1.f0.toString();
        String objectStr = createTemp();
        ClassRecord record = findRecord(classString);

        gv.addLine(objectStr + " = HeapAllocZ(" + (record.getSize()) + ")");
        gv.addLine("[" + objectStr + "] = " + ":" + classString);

        //gv.addLine(objectStr + " = HeapAllocZ(" + record.getSize() + ")");
        //gv.addLine("[" + objectStr + "] = " + ":" + classString);


        _ret = (R) (classString + "::" + objectStr);

        return _ret;
    }

    /**
     * f0 -> "!"
     * f1 -> Expression()
     */
    public R visit(NotExpression n, A argu) {
        R _ret=null;
        n.f0.accept(this, argu);
        R value = n.f1.accept(this, argu);

        if (value.equals("1")) _ret = (R) "0";
        if (value.equals("0")) _ret = (R) "1";

        return _ret;
    }

    /**
     * f0 -> "("
     * f1 -> Expression()
     * f2 -> ")"
     */
    public R visit(BracketExpression n, A argu) {
        R _ret=null;
        n.f0.accept(this, argu);
        _ret = n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }
}
