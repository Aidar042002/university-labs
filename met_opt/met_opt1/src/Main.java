public class Main {
    public static void main(String[] args) {
        double a=-1;
        double b=0;
        double e=0.003;

        System.out.println(halfDivision(a,b,e));
        System.out.println(goldenRatio(a,b,e));
        System.out.println(chords(a,b,e));
        System.out.println(newton(a,b,e));

    }

    public static double func(double x){
        double f= Math.pow(x,4)+Math.pow(x,2)+x+1;
        return f;
    }

    public static double getDerivative(double x) {
        return 4*Math.pow(x,3)+2*x+1;
    }

    public static double getSecondDerivative(double x) {
            return 12* Math.pow(x, 2)+2;
    }

    public static double halfDivision(double a, double b, double e){
        while (b-a>2*e){
            double x1=(a+b-e)/2;
            double x2=(a+b+e)/2;

            double y1=func(x1);
            double y2=func(x2);

            if(y1>y2){
                a=x1;
            } else{
                b=x2;
            }

        }

        return ((a+b)/2);
    }

    public static double goldenRatio(double a, double b, double e){
        double phi=(1+Math.sqrt(5))/2;
        double x1=b-(b-a)/phi;
        double x2=a+(b-a)/phi;

        while ((b-a)/2>=e){
            if(func(x1)> func(x2)){
                a=x1;
                x1=x2;
                x2=b-(x1-a);
            }else{
                b=x2;
                x2=x1;
                x1=a+(b-x2);
            }
        }
        return (a + b)/2;
    }

    public static double chords(double a, double b, double e) {
        double aDer = getDerivative(a);
        double bDer = getDerivative(b);

        while (true) {
            double x = a - (aDer / (aDer - bDer)) * (a - b);
            double xDer = getDerivative(x);

            if (xDer <= e) {
                return x;
            }

            if (xDer > 0) {
                b = x;
                bDer = xDer;
            } else {
                a = x;
                aDer = xDer;
            }
        }
    }

    public static double newton(double a, double b, double e) {
        double x0 = b;
        double x0Der = getDerivative(x0);
        double x0SDer = getSecondDerivative(x0);
        double x = x0 - (x0Der / x0SDer);

        while (true) {
            double xDer = getDerivative(x);

            if (Math.abs(xDer) < e) {
                break;
            }

            x = x - xDer / getSecondDerivative(x);
        }

        return x;
    }

}





