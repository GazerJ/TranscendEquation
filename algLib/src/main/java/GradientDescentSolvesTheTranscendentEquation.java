import java.io.*;

public class GradientDescentSolvesTheTranscendentEquation {

    //本code主要利用梯度下降算法求解了超越方程
    //背景：
    //在机器学习的核心内容就是把数据喂给一个人工设计的模型，然后让模型自动的“学习”，
    // 从而优化模型自身的各种参数，最终使得在某一组参数下该模型能够最佳的匹配该学习任务。
    // 那么这个“学习”的过程就是机器学习算法的关键。梯度下降法就是实现该“学习”过程的一种最常见的方式，
    // 尤其是在深度学习(神经网络)模型中，BP反向传播方法的核心就是对每层的权重参数不断使用梯度下降来进行优化。
    //梯度下降法(gradient descent)是一种常用的一阶(first-order)优化方法，是求解无约束优化问题最简单、最经典的方法之一。
    //事实上梯度下降算法的本质是寻找最优解
    //即损失函数的的极小值
    //如果我们吧损失函数定义为对某个超越方程的偏差的平方
    //那么当损失函数极小值的时候
    //可以理解x此时”最符合“这个超越方程
    //当然如果这个方程有解的话损失函数一定收敛
    //下面举一个栗子

    // 利用梯度下降求解超越方程 以 e^x=-x 为例子
    //定义对方程的偏差为损失函数
    // L=（e^x+x）^2
    //当损失函数取极小值的时候就是x的解
    //dL=2*（e^x+x）(e^x+1)

    public static double dL(double x) {
        //定义损失函数
        //损失函数是迭代结果对方程的偏差的平方
        //这里只是以这个为例子，这个偏差函数可以换成对任何方程的
        //也可以是方程组，如L=L1+L2+L3.是对每个方程的偏差平方
        return 2 * (Math.exp(x) + x) * (Math.exp(x) + 1);
    }

    public static double go(double x0, double alpha, int nStep) {
        double x = x0;
        //定义初始迭代值
        //for循环迭代nStep
        //alpha是学习率
        for (int i = 0; i < nStep; i++) {
            x = x - alpha * dL(x);
        }
        //返回迭代稳定后的x值
        //此次的x是L的极小值
        //也是对超越方程偏差最小的解
        //可以认为是超越方程的解
        return x;

    }

    public static void main(String[] args) {
        double x0 = 1.5;
        double alpha = 0.0001;
        double alpha1 = 0.0001;
        int nStep = 1000000;
        //初始化各个参数
        //x0是初始值
        //alpha是学习率
        //nStep是迭代部署
        double x = go(x0, alpha, nStep);
        System.out.println("the result of eq{exp(x)=-x} is " + x);

        double[] r0 = {0, 2};
        double[] r = new double[2];
        r = go1(r0, alpha1, nStep);
        System.out.print("the result of eq{y=exp(x) &x^2+y^2=4}   x:" + r[0] + "\t" + "y:" + r[1]);

    }

    // 二维两个曲线的交点
    //曲线1 x^2+y^2=4;
    //曲线2 y=exp(x)
    // 定义L为对方程1的偏差和方程2的偏差
    //L=((x^2+y^2-4)+(y-exp(x)))^2
    //dL/dx=2*((x^2+y^2-4)+(y-exp(x)))*2x-exp(x)
    //dL/dy=2*((x^2+y^2-4)+(y-exp(x)))*(2y+1)
    public static double[] go1(double[] r0, double alpha1, int nStep) {
        double[] r = new double[2];
        double[] temp = new double[2];
        for (int i = 0; i < nStep; i++) {
            temp = r.clone();
            r[0] = r[0] - alpha1 * dL1(temp)[0];
            r[1] = r[1] - alpha1 * dL1(temp)[1];
        }
        //返回迭代稳定后的x值
        //此次的x是L的极小值
        //也是对超越方程偏差最小的解
        //可以认为是超越方程的解
        return r;

    }

    //定义返回的梯度
    public static double[] dL1(double[] r) {
        double[] dL = new double[2];
        dL[0] = 2 * (r[0] * r[0] + r[1] * r[1] - 4 + r[1] - Math.exp(r[0])) * (2 * r[0] - Math.exp(r[0]));
        dL[1] = 2 * (r[0] * r[0] + r[1] * r[1] - 4 + r[1] - Math.exp(r[0])) * (2 * r[1] + 1);

        return dL;
    }

}
