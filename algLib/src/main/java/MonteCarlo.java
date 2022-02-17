import java.io.*;
import java.util.Random;
//蒙特卡洛方法
//当所求解问题是某种随机事件出现的概率或者是某个随机变量的期望值时
//通过某种“实验”的方法
//以这种事件出现的频率估计这一随机事件的概率
// 或者得到这个随机变量的某些数字特征，并将其作为问题的解。
//本code主要是通过这种思想求出pi的值
//思想：
//在二维a*a的正方形中随机打点
//落入半径为a/2的个数比上总个数
// 圆的面积比上正方形的面积=pi*/4
public class MonteCarlo {
    //利用蒙特卡洛计算pi的值
    //在二维a*a的正方形中随机打点
    //落入半径为a/2的个数比上总个数
    //圆的面积比上正方形的面积=pi/4
    //故pi=S圆/S正方形*4
    public static void main(String[] args) {
        // N是实验次数
        // a是正方形边长
        //go方法即实验
        //最后将实验结果输出
        int N = 10000;
        double a = 1;
        double pi = go(N, a);
        System.out.print("pi equal to by MonteCarlo"+pi);
    }

    public static double go(int N, double a) {
        //定义两个box存放x和y
        double[] x = new double[N];
        double[] y = new double[N];
        //定义随机数的类产生0-1的随机数
        //并进行放缩，放缩到-a/2,a/2
        Random rnd = new Random();
        //随机打点
        for (int i = 0; i < N; i++) {
            x[i] = rnd.nextDouble() * a - a / 2;
            y[i] = rnd.nextDouble() * a - a / 2;
        }
        //getR方法就是计算sqrt(x^2+y^2)
        double[] r = getR(x, y);
        //计算到中心的距离
        //统计在园内的距离
        int count = getCount(r, a);
        //返回pi的蒙特卡洛值
        //注意
        //count是int类型
        //N是int 类型
        //如果在除法前不强制转换结果恒为0
        //因为int/int结果还是int
        //所以必须在除法前将其中一个进行强制类型转换
        return (double) (count) / N * 4;
    }

        //定义计算距离的方法getR
        //主要实现的功能是返回sqrt(x^2+y^2)
    public static double[] getR(double[] x, double[] y) {
        double[] r = new double[x.length];
        //定义一个储存每个点的距离的容器r
        //遍历数组计算出对应的r
        for (int i = 0; i < x.length; i++) {
            r[i] = Math.sqrt(x[i] * x[i] + y[i] * y[i]);
        }
        return r;
    }
        //定义计数方法
    //count 是计数容器
    //遍历整个距离数组r
    //如果r落在圆内
    //即r<a/2
    //则count++
    //反正则不加
    //最后统计出落入圆的点数
    //所有的点都落入正方形
    //利用公式
    //pi=S圆/S正方形*4
    //输出pi的蒙特卡洛打点数值
    public static int getCount(double[] r, double a) {
        int count = 0;
        //计数器
        for (int i = 0; i < r.length; i++) {
            if (r[i] < a / 2) count++;
        }
        //返回统计的个数
        return count;
    }

}
