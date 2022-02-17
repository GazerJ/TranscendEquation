public class Thead implements    Runnable{

      int i=0;

    public void run() {
        System.out.println(i++);
    }

    public static void main(String[] args) {
        Runnable a= new Thead();
        int n = 100;
        Thread[] tA=new Thread[n];
        for (int i = 0; i<n;i++){
            tA[i]=new Thread(a);
            tA[i].start();
        }


    }
}
