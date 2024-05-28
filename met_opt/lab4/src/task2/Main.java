package task2;

import java.util.Arrays;

public class Main {
    static double e=0.001;
    public static void main(String[] args) {

        double xPrev=1.1;
        double yPrev=1.1;

        while (true){
            double sx=getGradOne(xPrev, yPrev);
            double sy=getGradTwo(xPrev, yPrev);
            System.out.println("sx="+sx);
            System.out.println("yx="+sy);

            double step=getStep(xPrev, yPrev, sx, sy);
            System.out.println("step="+step);

            double xNew=xPrev-step*sx;
            System.out.println("xn="+xNew);
            double yNew=yPrev -step*sy;
            System.out.println("yn="+yNew);

            double[] M={xNew, yNew};
            System.out.println("[x1, x2]"+ Arrays.toString(M));

            double fPrev=getFunction(xPrev, yPrev);
            double fNew=getFunction(xNew, yNew);
            System.out.println("fn="+fNew);
            System.out.println("fo="+fPrev);

            if(Math.abs(fNew - fPrev)<e){
                System.out.println("Function="+fNew);
                break;
            }

            xPrev=xNew;
            yPrev=yNew;

        }
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

    public static double getStep(double x1, double x2, double sx, double sy){
        double a=-5;
        double b=5;
        int count=0;
        while (b-a>2*e){
            count++;
            double m1=(a+b-e)/2;
            double m2=(a+b+e)/2;

//            System.out.println("H::::::::::::::count="+count);
            double y1=getFunction(x1-m1*sx, x2-m1*sy);
//            System.out.println("H:::::::::::::y1="+y1);
            double y2=getFunction(x1-m2*sx, x2-m2*sy);
//            System.out.println("H:::::::::::y2="+y2);
//            System.out.println("_________________-");

            if(y1>y2){
                a=m1;
            } else{
                b=m2;
            }
        }
        return (a+b)/2;
    }

}



