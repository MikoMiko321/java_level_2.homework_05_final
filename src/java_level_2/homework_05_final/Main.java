package java_level_2.homework_05_final;

public class Main {
    static final int size = 10000000;
    static final int h = size / 2;
    static float[] myArray = new float[size];
    public static void main(String[] args) {
        System.out.println("Processing array in single thread:");
        myArrayProcessInSingleThread(myArray);
        System.out.println("Processing array in two threads:");
        myArrayProcessInTwoThreads(myArray);
    }

    public static void myArrayProcessInSingleThread(float arr[]){
        for(int i=0;i<arr.length;i++){
            arr[i]=1;
        }
        long a = System.currentTimeMillis();
        for(int i=0;i<arr.length;i++){
            arr[i]=(float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Single thread process time: "+(System.currentTimeMillis() - a));
    }

    public static void myArrayProcessInTwoThreads(float arr[]){
        float[] a1 = new float[h];
        float[] a2 = new float[h];
        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);
        Thread t1 = new Thread(() -> myArrayProcessInSingleThread(a1));
        Thread t2 = new Thread(() -> myArrayProcessInSingleThread(a2));
        t1.start();
        t2.start();
        //System.out.println("t1 is alive "+t1.isAlive());
        //System.out.println("t2 is alive "+t2.isAlive());
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("t1 is alive "+t1.isAlive());
        //System.out.println("t2 is alive "+t2.isAlive());
        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
        System.out.println("Total process time: "+(System.currentTimeMillis() - a));
    }
}