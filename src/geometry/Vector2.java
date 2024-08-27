package geometry;

public class Vector2 {
    public double x,y;

    public Vector2(double x, double y){
        this.x = x;
        this.y = y;

    }

    public Vector2(){
        this.x = 0;
        this.y = 0;
    }
    @Override
    public String toString() {
        return "Vector2(" + x + ", " + y + ")";
    }
}
