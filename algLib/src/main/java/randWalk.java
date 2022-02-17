import java.io.*;
import java.util.Random;

public class randWalk {
    //随机行走问题
    //最经典的一维随机游走问题有
    // 赌徒输光问题 和 酒鬼失足问题
    // 赌徒输光问题
    // 赌徒在赌场赌博，赢的概率是p1 ，输的概率1-p
    // 每次的赌注为1元，假设赌徒最开始时有赌金n元，
    // 赢了赌金加1元，输了赌金减1元. 问赌徒输光的概率是多少？
    // 酒鬼失足问题
    // 一个醉鬼行走在一头是悬崖的道路上
    // 酒鬼从距离悬崖仅一步之遥的位置出发
    // 向前一步或向后退一步的概率皆为 问酒鬼失足掉入悬崖的概率是多少？
//
    //本code 主要是模拟一个随机行走的过程
    //初始时刻，酒鬼坐标是（0,0）
    //他朝东南西北方向行走的概率完全一样都是1/4
    // 他每次行走的多少步也是随机的
    //我们认为摇色子觉得他走多少步
    //
    public static void main(String[] args) throws IOException {
        int T=1000;

        //模拟1000秒
        int x[]=new int [T];
        int y[]=new int [T];
        //定义两个容易存放酒鬼的坐标
        go(x,y,T);
        //调用运动函数
        //保存醉汉的运动轨迹
        savetxt("./traceOfheX.txt",x);
        savetxt("./traceOfheY.txt",y);
        System.out.print("the trace of he write to ./traceX/Y.txt");
    }

    private static void go(int[] x, int[] y,int T) {
        int a=0;

        for (int i =0;i<T-1;i++){
            a=getN();
            if (a==0){
                x[i+1]=x[i]+getN();
                y[i+1]=y[i];
                //如果抽到0，则向x轴正方向运动1~4步
            }
            if (a==1){
                x[i+1]=x[i]-getN();
                y[i+1]=y[i];
                //如果抽到0，则向x轴负方向运动1~4步
            }
            if (a==2){
                x[i+1]=x[i];
                y[i+1]=y[i]+getN();
                //如果抽到0，则向y轴正方向运动1~4步
            }
            if (a==3){
                x[i+1]=x[i];
                y[i+1]=y[i]-getN();
                //如果抽到0，则向x轴负方向运动1~4步
            }

        }

    }

    private static int getN() {
        Random rnd = new Random();
        //摇色子决定醉汉运动的方向和运动的长远
        return rnd.nextInt(4);
    }


    public static void savetxt(String path, int[] data) throws IOException {
        //定义保存一维数组方法
        //定义文件类
        File file = new File(path);
        try {
            //定义写文件类
            FileWriter fis = new FileWriter(file);
            //定义缓存去
            BufferedWriter dis = new BufferedWriter(fis);
            for (int i = 0; i < data.length; i++) {

                dis.write(Integer.toString(data[i]));
                //一维数组的元素的写入
                dis.newLine();
                //写入”\n“换行符
            }
            dis.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //抛出异常
        }
    }
}
