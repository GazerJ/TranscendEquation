import java.io.*;

//数值积分和数值微分是科学计算中最常用的方法
//本code主要写了Euler方法分别是实现了数值积分数值微分方程的求解
//用Euler方法求数值积分以 \int(a)(b)(x*exp(x))(dx)为例子
//用Euler方法求解微分方程 dy/dx=y*e^x   初始条件：x=0,y=1求y(x)
public class numInt {
    //定义被积函数（可修改为其他任何无奇点的函数）
    public static double func(double x) {
        return x * Math.exp(x);
        //返回f(x)函数值
    }
    //用Euler算法迭代求解数值积分 以 \int(a)(b)(x*exp(x))(dx)为例子
    // a是下限
    // b是上限
    // dx 差分后的步长
    // nStep是差分的数量
    public static double goEuler(double a, double b, double dx, int nStep) {
    //x=0 从0开始积分
        double x = 0;
        double xnew;
        //xnew 是迭代的x
        //循环计算出所有的func
        //将积分变成求和进而得出积分的值
        for (int i = 0; i < nStep; i++) {
            xnew = a + dx * i;
            x = x + func(xnew) * dx;
        }
        return x;
        //返回积分值
    }

//定义main函数
    public static void main(String[] args) throws IOException {
        // a是下限
        // b是上限
        // dx 差分后的步长
        // nStep是差分的数量
        //用于积分和微分
        double a;
        double b;
        double dx;
        int nStep;
        a = 0;
        b = 1;
        dx = 0.0001;
        nStep = (int) ((b - a) / dx);
        double Int = goEuler(a, b, dx, nStep);
        //用Euler方法求解一重数值积分 \int(a)(b)(x*e^x)(dx)
        System.out.println("The result of NumInt is " + Int);
        // 以上是数值积分


        // 以下是数值微分
        //用Euler方法求解微分方程 dy/dx=y*e^x   初始条件：x=0,y=1
        double[] y = goEulerd(a, b, dx, nStep);
        //将求解的微分方程的函数y（x）存储为文件
        savetxt("./outy.txt", y);

        System.out.println("The result Write to ./outy.txt");


    }
    //用Euler算法迭代求解数值微分方程 微分方程 dy/dx=y*e^x   初始条件：x=0,y=1求y(x)
    // a是下限
    // b是上限
    // dx 差分后的步长
    // nStep是差分的数量

    //定义微分方程的函数
    public static double func2(double x, double y) {
        return y * Math.exp(x);
    }

    public static double[] goEulerd(double a, double b, double dx, int nStep) {
        //定义box存放每一步的x和y
        double[] x = new double[nStep];
        double[] y = new double[nStep];
        x[0] = 0;
        y[0] = 1;
        //初始条件设置
        //迭代记录每一时刻x和对于的y的值
        for (int i = 1; i < nStep; i++) {
            x[i] = x[i - 1] + dx;
            y[i] = y[i - 1] + func2(x[i - 1], y[i - 1]) * dx;
            //利用Euler 方法迭代计算y(x)的值
        }
        return y;
        //返回数组y，并储存为文件
    }


    public static void savetxt(String path, double[] data) throws IOException {
        //文件写入已经在np.txt 中作注释了
        File file = new File(path);
        try {
            FileWriter fis = new FileWriter(file);
            BufferedWriter dis = new BufferedWriter(fis);
            for (int i = 0; i < data.length; i++) {
                dis.write(Double.toString(data[i]));
                dis.newLine();
            }
            dis.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
