import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;


public class worm implements Runnable {


     final int n = 75;
     final int nStep = 4000;

     final double T ;//极限0.01，超过这个时候会发散
     final double Ts ;
     final String Tstr;
     final String Tsstr;
     final int needTime = 1;



    public worm(double T, double Ts) {
        this.T = T;
        this.Ts = Ts;
        this.Tstr = String.format("%.5f", T);
        this.Tsstr = String.format("%.5f", Ts);
    }


    public void run() {

        long start = System.currentTimeMillis();
        final double[][] x1 = new double[nStep][n];
        final double[][] y1 = new double[nStep][n];
        final double[][] x2 = new double[nStep][n];
        final double[][] y2 = new double[nStep][n];
        final double[][] x3 = new double[nStep][n];
        final double[][] y3 = new double[nStep][n];
        final double[][] x4 = new double[nStep][n];
        final double[][] y4 = new double[nStep][n];

        try {
            x1[0] = np.loadtxt("./input/toheart30x1.txt", n);
            x2[0] = np.loadtxt("./input/toheart30x2.txt", n);
            x3[0] = np.loadtxt("./input/toheart30x3.txt", n);
            x4[0] = np.loadtxt("./input/toheart30x4.txt", n);
            y1[0] = np.loadtxt("./input/toheart30y1.txt", n);
            y2[0] = np.loadtxt("./input/toheart30y2.txt", n);
            y3[0] = np.loadtxt("./input/toheart30y3.txt", n);
            y4[0] = np.loadtxt("./input/toheart30y4.txt", n);
            gotest(x1, y1, x2, y2, x3, y3, x4, y4);
            System.out.println(System.currentTimeMillis() - start);
            System.out.println("end");

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }


    public void gotest(double[][] x1, double[][] y1, double[][] x2, double[][] y2, double[][] x3, double[][] y3, double[][] x4, double[][] y4) throws IOException {
        final double dt = 0.00005;
        final Random rnd = new Random();

        double xi;
        //Random rnd = new Random();
        double[][] X;
        double[][] Y;
        double[][] dx;
        double[][] dy;
        double[][] dr;
        double[][] fljcore;
        double[][][] Tcore;
        final double[][] Fx = new double[4][n];
        final double[][] Fy = new double[4][n];
        double[][] Factive;
        for (int times = 0; times < needTime; times++) {
            for (int i = 0; i < nStep - 1; i++) {
                X = new double[][]{x1[i], x2[i], x3[i], x4[i]};
                Y = new double[][]{y1[i], y2[i], y3[i], y4[i]};
                dx = getDx(X);
                dy = getDy(Y);
                dr = getDr(dx, dy);
                fljcore = getFljcore(dr);
                Tcore = getTcore(dr, dx, dy);
                Fx[0] = gexFx(fljcore, dx, 0);
                Fy[0] = gexFy(fljcore, dy, 0);
                Fx[1] = gexFx(fljcore, dx, 1);
                Fy[1] = gexFy(fljcore, dy, 1);
                Fx[2] = gexFx(fljcore, dx, 2);
                Fy[2] = gexFy(fljcore, dy, 2);
                Fx[3] = gexFx(fljcore, dx, 3);
                Fy[3] = gexFy(fljcore, dy, 3);
                Factive = getFactive(dx, dy, dr);
                for (int j = 0; j < n; j++) {
                    xi = T * rnd.nextGaussian();
                    x1[i + 1][j] = x1[i][j] + (Factive[j][0] + Tcore[0][j][0] + Fx[0][j]) * dt + xi;
                    y1[i + 1][j] = y1[i][j] + (Factive[j][1] + Tcore[0][j][1] + Fy[0][j]) * dt + xi;
                    x2[i + 1][j] = x2[i][j] + (Tcore[1][j][0] + Fx[1][j]) * dt + xi;
                    y2[i + 1][j] = y2[i][j] + (Tcore[1][j][1] + Fy[1][j]) * dt + xi;
                    x3[i + 1][j] = x3[i][j] + (Tcore[2][j][0] + Fx[2][j]) * dt + xi;
                    y3[i + 1][j] = y3[i][j] + (Tcore[2][j][1] + Fy[2][j]) * dt + xi;
                    x4[i + 1][j] = x4[i][j] + (Tcore[3][j][0] + Fx[3][j]) * dt + xi;
                    y4[i + 1][j] = y4[i][j] + (Tcore[3][j][1] + Fy[3][j]) * dt + xi;
                }

            }
            np.savetxtCutAdd("./output/T" + Tstr + "Ts" + Tsstr + "x1.txt", x1, 2000);
            np.savetxtCutAdd("./output/T" + Tstr + "Ts" + Tsstr + "x2.txt", x2, 2000);
            np.savetxtCutAdd("./output/T" + Tstr + "Ts" + Tsstr + "x3.txt", x3, 2000);
            np.savetxtCutAdd("./output/T" + Tstr + "Ts" + Tsstr + "x4.txt", x4, 2000);
            np.savetxtCutAdd("./output/T" + Tstr + "Ts" + Tsstr + "y1.txt", y1, 2000);
            np.savetxtCutAdd("./output/T" + Tstr + "Ts" + Tsstr + "y2.txt", y2, 2000);
            np.savetxtCutAdd("./output/T" + Tstr + "Ts" + Tsstr + "y3.txt", y3, 2000);
            np.savetxtCutAdd("./output/T" + Tstr + "Ts" + Tsstr + "y4.txt", y4, 2000);
            double[] tmpx1 = x1[nStep - 1].clone();
            double[] tmpx2 = x2[nStep - 1].clone();
            double[] tmpx3 = x3[nStep - 1].clone();
            double[] tmpx4 = x4[nStep - 1].clone();
            double[] tmpy1 = y1[nStep - 1].clone();
            double[] tmpy2 = y2[nStep - 1].clone();
            double[] tmpy3 = y3[nStep - 1].clone();
            double[] tmpy4 = y4[nStep - 1].clone();

            //np.fill(x1,x2,x3,x4,y1,y2,y3,y4);

            x1[0] = tmpx1.clone();
            x2[0] = tmpx2.clone();
            x3[0] = tmpx3.clone();
            x4[0] = tmpx4.clone();
            y1[0] = tmpy1.clone();
            y2[0] = tmpy2.clone();
            y3[0] = tmpy3.clone();
            y4[0] = tmpy4.clone();


        }
    }

    public double[][] getFactive(double[][] dx, double[][] dy, double[][] dr) {
        final double[][] fActive= new double[n][2];
        final double[] p = new double[2];
        final double v0 = 10;
        double fCore;
        for (int i = 0; i < n; i++) {
            fCore = v0 / getNLco(dr, i);
            p[0] = dx[i][3 * n + i] / dr[i][3 * n + i];
            p[1] = dy[i][3 * n + i] / dr[i][3 * n + i];
            insertRand(p);
            fActive[i][0] = fCore * p[0];
            fActive[i][1] = fCore * p[1];
        }
        return fActive;
    }

    public void insertRand(double[] p) {//加入自传
        final Random rnd = new Random();

        double[] tmp = new double[2];
        double theata = Ts * rnd.nextGaussian();
        tmp[0] = Math.sin(theata) * p[1] + Math.cos(theata) * p[0];
        tmp[1] = -Math.sin(theata) * p[0] + Math.cos(theata) * p[1];
        p[0] = tmp[0];
        p[1] = tmp[1];
    }

    public double getNLco(double[][] dr, int i) {
        double N;

        final double sigma = 1;
        N = 0;
        for (int j = 0; j < 4 * n; j++) {
            if (dr[i][j] < 5 * sigma) N++;
        }

        //if (N == 0) System.out.print(dr[i][i]);
        return N;
    }

    public double[] gexFy(double[][] fljcore, double[][] dy, int k) {
        double[] Fy;
        Fy = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 4 * n; j++) {
                Fy[i] += fljcore[i + k * n][j] * dy[i + k * n][j];
            }
        }
        return Fy;

    }

    public double[] gexFx(double[][] fljcore, double[][] dx, int k) {
        double[] Fx;
        Fx = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 4 * n; j++) {
                Fx[i] += fljcore[i + k * n][j] * dx[i + k * n][j];
            }
        }
        return Fx;
    }


    public double[][] getDr(double[][] dx, double[][] dy) {
        double[][] dr;
        dr = new double[4 * n][4 * n];
        for (int i = 0; i < 4 * n; i++) {
            for (int j = 0; j < i; j++) {
                dr[i][j] = Math.sqrt(dx[i][j] * dx[i][j] + dy[i][j] * dy[i][j]);
            }
        }
        for (int i = 0; i < 4 * n; i++) {
            for (int j = i; j < 4 * n; j++) {
                dr[i][j] = dr[j][i];
            }
        }


        return dr;
    }

    public double[][] getDy(double[][] Y) {
        double[][] dy;
        dy = new double[4 * n][4 * n];
        for (int i = 0; i < 4 * n; i++) {
            for (int j = 0; j < i; j++) {
                dy[i][j] = Y[ (i / n)][(i % n)] - Y[ (j / n)][(j % n)];

            }

        }
        for (int i = 0; i < 4 * n; i++) {
            for (int j = i; j < 4 * n; j++) {
                dy[i][j] = -dy[j][i];

            }

        }
        return dy;
    }

    public double[][] getDx(double[][] X) {
        double[][] dx;
        dx = new double[4 * n][4 * n];
        for (int i = 0; i < 4 * n; i++) {
            for (int j = 0; j < i; j++) {
                dx[i][j] = X[i / n][i % n] - X[j / n][j % n];
            }
        }
        for (int i = 0; i < 4 * n; i++) {
            for (int j = i; j < 4 * n; j++) {
                dx[i][j] = -dx[j][i];

            }

        }
        return dx;
    }

    public double[][] getFljcore(double[][] dr) {
        double[][] fcore;
        double ddr;
        final double e = 1;
        final double sigma = 1;
        final double cut = (24 * e * (2 * Math.pow(sigma / 0.95, 6) - 1) * Math.pow(sigma / 0.95, 8)) / sigma / sigma;
        fcore = new double[4 * n][4 * n];
        for (int i = 0; i < 4 * n; i++) {
            for (int j = 0; j < i; j++) {

                if (dr[i][j] > 1.112) {
                    fcore[i][j] = 0;
                    continue;
                }
                if ((i % n) == (j % n)) {
                    fcore[i][j] = 0;
                    continue;
                }//自己身上不受力
                if (dr[i][j] < 0.95) {
                    fcore[i][j] = cut;
                } else if ((dr[i][j] > 0.95) && (dr[i][j] < 1.112)) {
                    ddr = sigma / dr[i][j];
                    fcore[i][j] = (24 * e * (2 * Math.pow(ddr, 6) - 1) * Math.pow(ddr, 8)) / sigma / sigma;

                } else {
                    fcore[i][j] = 0;

                }


            }
        }


        for (int i = 0; i < 4 * n; i++) {
            for (int j = i; j < 4 * n; j++) {
                fcore[i][j] = fcore[j][i];
            }
        }
        return fcore;
    }

    public double[][][] getTcore(double[][] dr, double[][] dx, double[][] dy) {
        double[][][] fcore;
        fcore = new double[4][n][2];
        double temp1, temp2;
        for (int i = 0; i < n; i++) {
            temp1 = getT(dr[n + i][i]);

            fcore[0][i][0] = temp1 * (dx[n + i][i]);
            fcore[0][i][1] = temp1 * (dy[n + i][i]);

            //temp1 =-temp1;
            temp2 = getT(dr[2 * n + i][n + i]);
            fcore[1][i][0] = temp1 * (dx[i][n + i]) + temp2 * (dx[2 * n + i][n + i]);//dx[i][n+i] 即D12
            fcore[1][i][1] = temp1 * (dy[i][n + i]) + temp2 * (dy[2 * n + i][n + i]);//dy[i][n+i] 即D12
            //temp1 = getT(dr[n + i][2 * n + i]);
            temp1 = temp2;
            temp2 = getT(dr[3 * n + i][2 * n + i]);
            fcore[2][i][0] = temp1 * (dx[n + i][2 * n + i]) + temp2 * (dx[3 * n + i][2 * n + i]);//dr[n+i][i] 即D23.d43
            fcore[2][i][1] = temp1 * (dy[n + i][2 * n + i]) + temp2 * (dy[3 * n + i][2 * n + i]);//dr[n+i][i] 即D21
            //temp2 = getT(dr[2 * n + i][3 * n + i]);
            fcore[3][i][0] = temp2 * (dx[2 * n + i][3 * n + i]);//dr[n+i][i] 即D21
            fcore[3][i][1] = temp2 * (dy[2 * n + i][3 * n + i]);//dr[n+i][i] 即D21

        }
        return fcore;
    }

    public double getT(double r) {
        final double k = 5;
        final double r0 = 1;
        return k * (r - r0) / r;
    }


}
