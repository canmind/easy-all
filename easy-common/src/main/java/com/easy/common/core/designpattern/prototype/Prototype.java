package com.easy.common.core.designpattern.prototype;

public class Prototype implements Cloneable {

    private String name;
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }

    public Object clone(){
        try {
            return super.clone();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}