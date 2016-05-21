package com.o2o.freemarker.po;  
  
/**  
 * <p>o2o</p>  
 * 类名:SalgradePO<br>  
 * 创建人:haifeng.chen<br>  
 * 创建时间:20160409<br>  
 */  
   
public class SalgradePO {  
  
      
    /**  
     * 工资等级  
     */  
    private Integer grade;  
      
    /**  
     * 此等级的最低工资  
     */  
    private Integer losal;  
      
    /**  
     * 此等级的最高工资  
     */  
    private Integer hisal;  
      
      
    public Integer getGrade() {  
        return grade;  
    }  
      
    public void setGrade(Integer grade) {  
        this.grade = grade;  
    }  
      
    public Integer getLosal() {  
        return losal;  
    }  
      
    public void setLosal(Integer losal) {  
        this.losal = losal;  
    }  
      
    public Integer getHisal() {  
        return hisal;  
    }  
      
    public void setHisal(Integer hisal) {  
        this.hisal = hisal;  
    }  
  
}  