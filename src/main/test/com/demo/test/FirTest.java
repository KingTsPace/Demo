package com.demo.test;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class FirTest {

    @Test
    public void testCanlendar(){
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat parse = new SimpleDateFormat("yyyyMMdd");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(calendar.DATE,-1);
        String s = parse.format(calendar.getTime());
        System.out.println(s);

        new HashMap<String,Object>(16);
    }




}
