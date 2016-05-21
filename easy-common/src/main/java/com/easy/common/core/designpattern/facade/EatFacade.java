package com.easy.common.core.designpattern.facade;
/**
 * 　●　　松散耦合

　　门面模式松散了客户端与子系统的耦合关系，让子系统内部的模块能更容易扩展和维护。

　　●　　简单易用

　　门面模式让子系统更加易用，客户端不再需要了解子系统内部的实现，也不需要跟众多子系统内部的模块进行交互，只需要跟门面类交互就可以了。

　　●　　更好的划分访问层次

　　通过合理使用Facade，可以帮助我们更好地划分访问的层次。有些方法是对系统外的，有些方法是系统内部使用的。把需要暴露给外部的功能集中到门面中，这样既方便客户端使用，也很好地隐藏了内部的细节。
 * @author haifeng.chf
 *
 */
public class EatFacade {
    //示意方法，满足客户端需要的功能
    public void test(){
        OrderModule a = new OrderModule();
        a.order();
        PutModule b = new PutModule();
        b.put();
        EatModule c = new EatModule();
        c.eat();
    }
}
