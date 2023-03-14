package org.sq.zbnss.uitl;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculationUtil {
    private static BigDecimal o1;
    private static BigDecimal o2;

    private static int defDiv;

    private static CalculationUtil a = null;

    //懒汉单例线程安全
    public static CalculationUtil getCalculationUtil(Object o1, Object o2){
        if (a == null){
            synchronized (CalculationUtil.class){
                a = new CalculationUtil();
            }
        }
        //赋值
        try {
            CalculationUtil.o1 = new BigDecimal(String.valueOf(o1));
            CalculationUtil.o2 = new BigDecimal(String.valueOf(o2));
        }catch (RuntimeException e){
            throw new RuntimeException("参数类型转换失败!");
        }
        return a;
    }
    /**
     * @param o1 参数1
     * @param o2 参数2
     */
    public static CalculationUtil getCalculationUtil(Object o1, Object o2,int defDiv){
        if (a == null){
            synchronized (CalculationUtil.class){
                a = new CalculationUtil();
            }
        }
        //赋值
        try {
            CalculationUtil.o1 = new BigDecimal(String.valueOf(o1));
            CalculationUtil.o2 = new BigDecimal(String.valueOf(o2));
            CalculationUtil.defDiv = defDiv;
        }catch (RuntimeException e){
            throw new RuntimeException("参数类型转换失败!");
        }
        return a;
    }

    private CalculationUtil(){}

    /**
     * 加
     * @return 结果
     */
    public String add(){
        return String.valueOf(o1.add(o2));
    }
    /**
     * 减
     * @return 结果
     */
    public String subtract(){
        return String.valueOf(o1.subtract(o2));
    }
    /**
     * 乘
     * @return 结果
     */
    public String multiply(){
        return String.valueOf(o1.multiply(o2));
    }
    /**
     * 除
     * @return 结果
     */
    public String divide(){
        return String.valueOf(o1.divide(o2,defDiv, RoundingMode.HALF_UP));
    }
}
