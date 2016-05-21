package com.o2o.freemarker.po;  
  
/**  
 * <p>o2o</p>  
 * 类名:EmpPO<br>  
 * 创建人:haifeng.chen<br>  
 * 创建时间:20160409<br>  
 */  
   
public class EmpPO {  
  
      
    /**  
     * 雇员编号  
     */  
    private Integer empno;  
      
    /**  
     * 雇员姓名  
     */  
    private String ename;  
      
    /**  
     * 雇员职位  
     */  
    private String job;  
      
    /**  
     * 雇员对应的领导的编号  
     */  
    private Integer mgr;  
      
    /**  
     * 雇员的雇佣日期  
     */  
    private java.util.Date hiredate;  
      
    /**  
     * 雇员的基本工资  
     */  
    private java.math.BigDecimal sal;  
      
    /**  
     * 奖金  
     */  
    private java.math.BigDecimal comm;  
      
    /**  
     * 所在部门  
     */  
    private Integer deptno;  
      
      
    public Integer getEmpno() {  
        return empno;  
    }  
      
    public void setEmpno(Integer empno) {  
        this.empno = empno;  
    }  
      
    public String getEname() {  
        return ename;  
    }  
      
    public void setEname(String ename) {  
        this.ename = ename;  
    }  
      
    public String getJob() {  
        return job;  
    }  
      
    public void setJob(String job) {  
        this.job = job;  
    }  
      
    public Integer getMgr() {  
        return mgr;  
    }  
      
    public void setMgr(Integer mgr) {  
        this.mgr = mgr;  
    }  
      
    public java.util.Date getHiredate() {  
        return hiredate;  
    }  
      
    public void setHiredate(java.util.Date hiredate) {  
        this.hiredate = hiredate;  
    }  
      
    public java.math.BigDecimal getSal() {  
        return sal;  
    }  
      
    public void setSal(java.math.BigDecimal sal) {  
        this.sal = sal;  
    }  
      
    public java.math.BigDecimal getComm() {  
        return comm;  
    }  
      
    public void setComm(java.math.BigDecimal comm) {  
        this.comm = comm;  
    }  
      
    public Integer getDeptno() {  
        return deptno;  
    }  
      
    public void setDeptno(Integer deptno) {  
        this.deptno = deptno;  
    }  
  
}  