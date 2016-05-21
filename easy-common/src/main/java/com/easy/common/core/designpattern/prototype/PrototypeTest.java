package com.easy.common.core.designpattern.prototype;

import java.io.IOException;
import java.io.OptionalDataException;

public class PrototypeTest {

    public static void main(String[] args) throws OptionalDataException, ClassNotFoundException, IOException {
        Prototype pro = new ConcretePrototype("prototype");
        Prototype pro2 = (Prototype)pro.clone();
        System.out.println(pro2.getName());
        System.out.println(pro.getName());
        System.out.println(pro.getName()==pro2.getName());
        
        DeepClone deep = new DeepClone();
        deep.setName("deep");
        DeepClone deep2 = (DeepClone) deep.deepClone();
        System.out.println(deep2.getName());
        System.out.println(deep.getName());
        System.out.println(deep.getName()==deep2.getName());
    }

}
