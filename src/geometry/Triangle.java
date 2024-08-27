package geometry;

public class Triangle {
    public Vector3 p1,p2,p3;

    public Triangle(Vector3 p1, Vector3 p2, Vector3 p3){
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public Triangle(){
        this.p1 = new Vector3(0,0,0);
        this.p2 = new Vector3(0,0,0);
        this.p3 = new Vector3(0,0,0);
    }

    public Vector3 getCentroid() {
        double x = (p1.x + p2.x + p3.x) / 3.0;
        double y = (p1.y + p2.y + p3.y) / 3.0;
        double z = (p1.z + p2.z + p3.z) / 3.0;

        return new Vector3(x, y, z);
    }

    // Method to calculate the distance from the triangle's centroid to a given point (like the camera)
    public double distanceTo(Vector3 point) {
        Vector3 centroid = getCentroid();
        return centroid.subtract(point).magnitude();
    }

    public Vector3 getNormal() {
        // Compute the edge vectors
        Vector3 edge1 = p2.subtract(p1);
        Vector3 edge2 = p3.subtract(p1);

        // Compute the cross product to get the normal vector
        Vector3 normal = edge1.cross(edge2);

        // Optionally normalize the result to get a unit vector
        return normal.normalize();
    }


}
