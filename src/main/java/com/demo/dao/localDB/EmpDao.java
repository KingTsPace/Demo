package com.demo.dao.localDB;

import com.demo.bean.Emp;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EmpDao {
    List<Emp> queryEmp(Map<String,Object> map);

}
