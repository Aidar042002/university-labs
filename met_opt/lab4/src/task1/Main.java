package task1;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        double x1 = 1.1;
        double x2 = 1.1;
        double h = 0.25;
        double e = 0.001;

        gradientDescent(x1,x2,h,e);

    }

    public static double getFunction(double x1, double x2){
        return Math.pow(x1,4)+Math.pow(x2, 4)-2*Math.pow(x1-x2, 2);
    }

    public static double getGradOne(double x1, double x2){
        return 4*Math.pow(x1, 3)-4*x1+4*x2;
    }

    public static double getGradTwo(double x1, double x2){
        return 4*Math.pow(x2, 3)+4*x1-4*x2;
    }

    public static void gradientDescent(double x1, double x2, double h, double e){
        double prevX1=x1;
        double prevX2=x2;
        while (true){

            prevX1=x1;
            prevX2=x2;
            if(Math.abs(getFunction(x1,x2)-getFunction(prevX1,prevX2))>e) break;
            double grad1=getGradOne(x1, x2);
            double grad2=getGradTwo(x1, x2);
            System.out.println("grad1="+grad1);
            System.out.println("grad2="+grad2);
            x1=x1-h*grad1;
            x2=x2-h*grad2;
            if(getFunction(x1, x2)>getFunction(prevX1, prevX2)){
                h/=2;
            }

            double[] M={x1, x2};
            System.out.println("fn="+getFunction(x1, x2));
            System.out.println("fp="+getFunction(prevX1, prevX2));
            System.out.println(Arrays.toString(M));

            if(Math.abs(getFunction(x1,x2)-getFunction(prevX1,prevX2))<e){
                break;
            }

        }
        System.out.println("Поиск минимума");
        System.out.println("x1="+x1);
        System.out.println("x2="+x2);
        System.out.println("func="+getFunction(x1, x2));
    }
}


