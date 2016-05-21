package com.o2o.freemarker.po;  
  
/**  
 * <p>o2o</p>  
 * 类名:DeptPO<br>  
 * 创建人:haifeng.chen<br>  
 * 创建时间:20160409<br>  
 */  
   
public class DeptPO {  
  
      
    /**  
     * 部门编号  
     */  
    private Integer deptno;  
      
    /**  
     * 部门名称  
     */  
    private String dname;  
      
    /**  
     * 部门所在位置  
     */  
    private String loc;  
      
      
    public Integer getDeptno() {  
        return deptno;  
    }  
      
    public void setDeptno(Integer deptno) {  
        this.deptno = deptno;  
    }  
      
    public String getDname() {  
        return dname;  
    }  
      
    public void setDname(String dname) {  
        this.dname = dname;  
    }  
      
    public String getLoc() {  
        return loc;  
    }  
      
    public void setLoc(String loc) {  
        this.loc = loc;  
    }  
  
}  