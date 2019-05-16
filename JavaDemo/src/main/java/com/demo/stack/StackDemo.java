package com.demo.stack;

import java.util.Stack;

/**
 * @author Iving
 * @description FILO:First In Last Out(栈：先进后出)
 * @date on 2019/5/16
 **/
public class StackDemo {

    private Stack<String> mStack= new Stack<>();

    public StackDemo() {
        mStack.push("AA");
        mStack.push("BB");
        mStack.push("CC");
        mStack.push("DD");
    }


    public void println(){
        for( int i = 0; mStack.size()> 0; i++){
            System.out.println(mStack.pop());
        }
    }
}
