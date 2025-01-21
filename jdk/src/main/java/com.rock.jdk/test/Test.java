package com.rock.jdk.test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author ayl
 * @Date 2025-1-21
 */
public class Test {

    //返回结果节点
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
            //计算hash
            return Integer.valueOf(this.x.add(this.y).add(this.z).toBigInteger().toString());
        }

        @Override
        public boolean equals(Object obj) {
            //强转
            Node another = (Node) obj;
            //返回
            return this.x.compareTo(another.x) == 0 &&
                    this.y.compareTo(another.y) == 0 &&
                    this.z.compareTo(another.z) == 0;
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
        //计算
        Set<Node> solutions = findSolutions(A, B, C, D);
        //循环
        for (Node solution : solutions) {
            //输出
            System.out.printf("x = %.2f, y = %.2f, z = %.2f\n", solution.x, solution.y, solution.z);
        }
    }

    //具体单个实现
    private static Set<Node> findSolutions(BigDecimal A, BigDecimal B, BigDecimal C, BigDecimal D) {
        //结果列表
        Set<Node> solutions = new HashSet<>();
        //每次叠加
        BigDecimal step = new BigDecimal("0.00001");
        //x、y、z的最大值
        BigDecimal one = BigDecimal.ONE;
        //结果误差
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
                //如果满足误差
                if (leftSide.subtract(rightSide).abs().compareTo(tolerance) < 0) {
                    //初始化结果
                    Node node = new Node(
                            x.setScale(2, RoundingMode.HALF_UP),
                            y.setScale(2, RoundingMode.HALF_UP),
                            z.setScale(2, RoundingMode.HALF_UP)
                    );
                    //记录结果
                    solutions.add(node);
                    //如果结果够了
                    if (solutions.size() >= 50) {
                        //结束
                        return solutions;
                    }
                }
            }
        }
        //放回结果列表
        return solutions;
    }

}
