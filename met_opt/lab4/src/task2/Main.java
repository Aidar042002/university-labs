package task2;

import java.util.Arrays;

public class Main {
    static double e=0.01;
    public static void main(String[] args) {

        double xPrev=0.1;
        double yPrev=0.1;

        while (true){
            double sx=getGradOne(xPrev, yPrev);
            double sy=getGradTwo(xPrev, yPrev);

            double step=getStep(xPrev, yPrev, sx, sy);

            double xNew=xPrev-step*sx;
            double yNew=yPrev -step*sy;

            double[] M={xNew, yNew};
            System.out.println("[x1, x2]"+ Arrays.toString(M));

            double fPrev=getFunction(xPrev, yPrev);
            double fNew=getFunction(xNew, yNew);

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

    public static double getS(double x1, double x2, double sx, double sy, double h){
        return getFunction(x1-h*sx, x2-h*sy);
    }

    public static double getStep(double x1, double x2, double sx, double sy){
        double a=-5;
        double b=Math.pow(x1, 2)+Math.pow(x2, 2);
        while (b-a>2*e){
            double h1=(a+b-e)/2;
            double h2=(a+b+e)/2;

            double y1=getS(x1, x2, sx, sy, h1);
            double y2=getS(x1, x2, sx, sy, h2);

            if(y1>y2){
                a=h1;
            } else{
                b=h2;
            }
        }
        return (a+b)/2;
    }

}
