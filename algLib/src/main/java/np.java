import java.io.*;

//Background
//在python中numpy库对于科学极其重要
//本程序主要是复现了numpy中的loadtxt方法和savatxt

public class np {

    public static double[] loadtxt(String path, int n) throws IOException {
        //定义一维数组的loadtxt方法
        File file = new File(path);
        //定义文件类
        //定义box储存文件中读取的data
        double[] data = new double[n];
        try {
            //定义文件读类
            FileReader fis = new FileReader(file);
            //定义读文件流
            BufferedReader dis = new BufferedReader(fis);
            //定义储存每行的容器
            String tmp = null;
            //定义行数
            int i = 0;
            while ((tmp = dis.readLine()) != null) {
                data[i] = Double.parseDouble(tmp);
                //一维Double数组的元素的读
                i++;
                if (i >= n) break;
                //读完退出
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //抛出异常
        }
        return data;
    }


    public static void savetxt(String path, double[] data) throws IOException {
        //定义保存一维数组方法
        //定义文件类
        File file = new File(path);
        try {
            //定义写文件类
            FileWriter fis = new FileWriter(file);
            //定义缓存去
            BufferedWriter dis = new BufferedWriter(fis);
            for (int i = 0; i < data.length; i++) {

                dis.write(Double.toString(data[i]));
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

    public static void savetxt(String path, double[][] data) throws IOException {
        //对savetxt方法的重写，用于二维数组的储存
        //定义文件类
        File file = new File(path);
        try {
            //定义读文件类

            FileWriter fis = new FileWriter(file);
            //定义缓存区
            BufferedWriter dis = new BufferedWriter(fis);
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    dis.write(Double.toString(data[i][j]));
                    //二维数组的元素的写入
                    dis.write("\t");
                    //写入制表符
                }
                dis.newLine();
                //写入”\n“换行符
            }
            dis.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void savetxtN(String path, double[][] data, int n) throws IOException {
        //跳步写文件
        File file = new File(path);
        try {
            FileWriter fis = new FileWriter(file);
            BufferedWriter dis = new BufferedWriter(fis);
            for (int i = 0; i < data.length / n; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    dis.write(Double.toString(data[i * n][j]));
                    //二维数组的元素的间隔写入
                    dis.write("\t");
                }
                dis.newLine();
            }
            dis.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void savetxtCutAdd(String filepath, double[][] data, int n) {
        //跳步文件增加内容 savetxtCutAdd
        FileWriter fw = null;
        try {
            //如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f = new File(filepath);
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);

        for (int i = 0; i < data.length / n; i++) {
            for (int j = 0; j < data[i].length; j++) {
                pw.print(Double.toString(data[i * n][j]));
                //二维数组的元素的增加（不删除原文件）
                pw.print("\t");
            }
            pw.print("\n");
        }

        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            //抛出异常
        }
    }


    public static void fill(double[][] x1,double n) {
        //填充数组fill方法 用n叭二维数组填充
        for (int i = 0; i < x1.length; i++) {
            for (int j = 0; j < x1[0].length; j++) {

                x1[i][j] = n;
                //遍历数组填充
            }

        }

    }

    public  static  void main(String[] args)throws IOException {
        //初始化两个数组 调用loadtxt方法和savetxt
        double[] x={1.0,2.0,3.0,4.0};
        double[][] y={{1.0,2.0},{3.0,4.0}};
        np.savetxt("./xWrite.txt",x);
        //一维Double数组的存储
        np.savetxt("./yWrite1.txt",y);
        //二维Double数组的存储
        double[]  z=np.loadtxt("./xWrite.txt",2);
        np.fill(y,100);
        //二维数组填充
        np.savetxt("./yWrite2.txt",y);
        //二维数组存储

        System.out.print("file write and read is over");
    }
}
