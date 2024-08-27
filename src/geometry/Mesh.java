package geometry;

import java.util.Vector;

public class Mesh {

    public Vector<Triangle> triangles;

    public Mesh(){
        triangles = new Vector<Triangle>();
    }

    public void rotateX(double angle) {
        for (Triangle triangle : triangles) {
            triangle.p1.rotateX(angle);
            triangle.p2.rotateX(angle);
            triangle.p3.rotateX(angle);
        }
    }

    // Rotate around the Y-axis
    public void rotateY(double angle) {
        for (Triangle triangle : triangles) {
            triangle.p1.rotateY(angle);
            triangle.p2.rotateY(angle);
            triangle.p3.rotateY(angle);
        }
    }

    // Rotate around the Z-axis
    public void rotateZ(double angle) {
        for (Triangle triangle : triangles) {
            triangle.p1.rotateZ(angle);
            triangle.p2.rotateZ(angle);
            triangle.p3.rotateZ(angle);
        }
    }

    public void scale(double scalar){
        for (Triangle triangle : triangles) {
            triangle.p1= triangle.p1.multiply(scalar);
            triangle.p2=triangle.p2.multiply(scalar);
            triangle.p3=triangle.p3.multiply(scalar);
        }
    }

}
