import java.util.concurrent.ExecutorService;

import java.util.concurrent.Executors;

public class Executo   {
    public static void main(String args[]){

        ExecutorService service=Executors.newFixedThreadPool(2);
        for(int i=0;i<10;i++){
            service.execute(new worm(0,0));

            //service.submit(new MyExecutor(i));

        }

        System.out.println("submit finish");

        service.shutdown();

    }

}
