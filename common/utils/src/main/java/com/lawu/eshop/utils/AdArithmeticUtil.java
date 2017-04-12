package com.lawu.eshop.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * 红包，积分随机生成算法工具类
 * @author zhangrc
 * @date 2017/4/11
 *
 */
public class AdArithmeticUtil {
	
	public static double [] getMoney(double money, int num){
	    Random r = new Random();
	    DecimalFormat format = new DecimalFormat(".##");
	    double middle = Double.parseDouble(format.format(money/num));
	    double [] dou = new double[num];
	    double redMoney = 0;
	    double nextMoney = money;
	    double sum = 0;
	    int index = 0;
	    for(int i=num;i>0;i--){
	      if(i == 1){
	        dou[index] = nextMoney;
	      }else{
	        while(true){
	          String str = format.format(r.nextDouble()*nextMoney);
	          redMoney = Double.parseDouble(str);
	          if(redMoney>0 && redMoney < middle){
	            break;
	          }
	        }
	        nextMoney = Double.parseDouble(format.format(nextMoney - redMoney));
	        sum = sum + redMoney;
	        dou[index] = redMoney;
	        middle = Double.parseDouble(format.format(nextMoney/(i-1)));
	        index++;
	      }
	    }
	    return dou;
	  }
	
	
	public static double [] getRedPacketMoney(double money, int num){
		money=money/num;
	    Random r = new Random();
	    double d=r.nextDouble();
	    BigDecimal m1 = new BigDecimal(money*1.8);  
	    BigDecimal n1 = new BigDecimal(money*0.2);  
	    BigDecimal d1 = new BigDecimal(d);  
	    BigDecimal b=(m1.subtract(n1));
	    money=d1.multiply(b).add(m1).doubleValue();
	    DecimalFormat format = new DecimalFormat(".##");
	    double middle = Double.parseDouble(format.format(money/num));
	    double [] dou = new double[num];
	    double redMoney = 0;
	    double nextMoney = money;
	    double sum = 0;
	    int index = 0;
	    for(int i=num;i>0;i--){
	      if(i == 1){
	        dou[index] = nextMoney;
	      }else{
	        while(true){
	          String str = format.format(r.nextDouble()*nextMoney);
	          redMoney = Double.parseDouble(str);
	          if(redMoney>0 && redMoney < middle){
	            break;
	          }
	        }
	        nextMoney = Double.parseDouble(format.format(nextMoney - redMoney));
	        sum = sum + redMoney;
	        dou[index] = redMoney;
	        middle = Double.parseDouble(format.format(nextMoney/(i-1)));
	        index++;
	      }
	    }
	    return dou;
	  }


}
