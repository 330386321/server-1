package com.lawu.eshop.ad.constants;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RedPacketArithmetic {  
    //红包最小值  
    private static final double MINVALUE = 0.2;  
    //红包最大值  
    private static final double MAXVALUE = 1.8;  
    
    private static DecimalFormat format = new DecimalFormat(".##");
  
  
    /** 
     * 判断红包是否合情理 
     * @param money 
     * @param count 
     * @return 
     */  
    public static boolean isRight(double money,double count) {  
    	double avg = Double.parseDouble(format.format(money/count));  
        if(avg < MINVALUE*money/count) {  
            return false;  
        } else if(avg > MAXVALUE*money/count) {  
            return false;  
        }  
        return true;  
    }  
  
    /** 
     * 分红包核心算法 
     * @param money 
     * @param minS 
     * @param maxS 
     * @param count 
     * @return 
     */  
    public static double randomRedPacket(double money,double minS,double maxS,int count) {  
        //当人数剩余一个时，把当前剩余全部返回  
        if(count == 1) {  
            return money;  
        }  
        //如果当前最小红包等于最大红包，之间返回当前红包  
        if(minS == maxS) {  
            return minS;  
        }  
        double max = maxS>money?money:maxS;  
        //随机产生一个红包  
        maxS=Double.parseDouble(format.format(max-minS));
        double one = (double)(Math.random()*Double.parseDouble(format.format(maxS+minS)));  
        double balance = Double.parseDouble(format.format(money - one));  
       
        //判断此次分配后，后续是否合理  
        if(isRight(balance,count-1)) {  
            return one;  
        } else {  
            //重新分配  
        	double avg = Double.parseDouble(format.format(balance/(count-1)));  
            //如果本次红包过大，导致下次不够分，走这一条  
            if(avg < money/count*MINVALUE) {  
                return randomRedPacket(money, minS, one, count);  
            } else {  
                return randomRedPacket(money, one, maxS, count);  
            }  
        }  
    }  
  
    /** 
     * 分红包 
     * @param money 
     * @param count 
     * @return 
     */  
    public static List<Double> spiltRedPackets(double money,int count) {  
        //首先判断红包是否合情理  
        if(!isRight(money,count)) {  
            return null;  
        }  
        List<Double> list = new ArrayList<Double>();  
        double max = Double.parseDouble(format.format(money/count))*MAXVALUE;  
        max = max>money?money:max;  
        for(int i = 0 ; i < count; i++) {  
        	double value = randomRedPacket(money,MINVALUE*money/count,max,count-i);  
        	value= Double.parseDouble(format.format(value));  
            list.add(value);  
            money -= value;  
        }  
        return list;  
    }  
   
} 
