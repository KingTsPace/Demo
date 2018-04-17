package com.demo.bean;


import lombok.Data;

import java.sql.Date;
@Data
public class Emp {

  private String empno;
  private String ename;
  private String job;
  private String mgr;
  private Date hiredate;
  private String sal;
  private String comm;
  private String deptno;



}
