package com.rock.jdk.test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author ayl
 * @Date 2025-1-21
 */
public class Test {

    private static class Node {

        //x
        private BigDecimal x;

        //y
        private BigDecimal y;

        //z
        private BigDecimal z;

        //初始化
        public Node(BigDecimal x, BigDecimal y, BigDecimal z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public int hashCode() {
            return Integer.valueOf(this.x.add(this.y).add(this.z).toBigInteger().toString());
        }

        @Override
        public boolean equals(Object obj) {
            //强转
            Node another = (Node) obj;
            return super.equals(obj);
        }

    }

    public static void main(String[] args) {

        //第一组数据
        BigDecimal A1 = new BigDecimal("11190.33167");
        BigDecimal B1 = new BigDecimal("13151.9928");
        BigDecimal C1 = new BigDecimal("11921.43");
        BigDecimal D1 = new BigDecimal("11948.1323");

        //第二组数据
        BigDecimal A2 = new BigDecimal("154598.742");
        BigDecimal B2 = new BigDecimal("34488.7897");
        BigDecimal C2 = new BigDecimal("56988.57");
        BigDecimal D2 = new BigDecimal("81772.6465");

        System.out.println("Solutions for the first set of coefficients:");
        findAndPrintSolutions(A1, B1, C1, D1);

        System.out.println("\nSolutions for the second set of coefficients:");
        findAndPrintSolutions(A2, B2, C2, D2);
    }

    private static void findAndPrintSolutions(BigDecimal A, BigDecimal B, BigDecimal C, BigDecimal D) {
        List<BigDecimal[]> solutions = findSolutions(A, B, C, D);
        for (BigDecimal[] solution : solutions) {
            System.out.printf("x = %.2f, y = %.2f, z = %.2f\n", solution[0], solution[1], solution[2]);
        }
    }

    private static List<BigDecimal[]> findSolutions(BigDecimal A, BigDecimal B, BigDecimal C, BigDecimal D) {
        //结果列表
        List<BigDecimal[]> solutions = new ArrayList<>();
        //每次叠加
        BigDecimal step = new BigDecimal("0.00001");
        //每个字段最大
        BigDecimal one = BigDecimal.ONE;
        //误差
        BigDecimal tolerance = new BigDecimal("0.00001");
        //循环1
        for (BigDecimal x = new BigDecimal("0.1"); x.compareTo(one) <= 0; x = x.add(step)) {
            //循环2
            for (BigDecimal y = new BigDecimal("0.1"); y.compareTo(one.subtract(x)) <= 0; y = y.add(step)) {
                //根据x、y取z
                BigDecimal z = one.subtract(x).subtract(y);
                //计算
                BigDecimal leftSide = A.multiply(x)
                        .add(B.multiply(y))
                        .add(C.multiply(z))
                        .setScale(2, RoundingMode.HALF_UP);
                //目标结果
                BigDecimal rightSide = D.setScale(2, RoundingMode.HALF_UP);
                //如果满足
                if (leftSide.subtract(rightSide).abs().compareTo(tolerance) < 0) {
                    solutions.add(new BigDecimal[]{
                            x.setScale(2, RoundingMode.HALF_UP),
                            y.setScale(2, RoundingMode.HALF_UP),
                            z.setScale(2, RoundingMode.HALF_UP)});
                    if (solutions.size() >= 50) {
                        return solutions;
                    }
                }
            }
        }

        return solutions;
    }

}
