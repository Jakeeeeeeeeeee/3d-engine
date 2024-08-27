package geometry;

public class Plane extends Mesh {
    public Plane(Vector3 pos, double scale) {
        super();

        // Define the four corners of the plane
        Vector3 v0 = new Vector3(pos.x - 0.5 * scale, pos.y - 0.5 * scale, pos.z);
        Vector3 v1 = new Vector3(pos.x + 0.5 * scale, pos.y - 0.5 * scale, pos.z);
        Vector3 v2 = new Vector3(pos.x + 0.5 * scale, pos.y + 0.5 * scale, pos.z);
        Vector3 v3 = new Vector3(pos.x - 0.5 * scale, pos.y + 0.5 * scale, pos.z);

        // Create the plane's face with 2 triangles
        this.triangles.add(new Triangle(new Vector3(v0), new Vector3(v1), new Vector3(v2)));
        this.triangles.add(new Triangle(new Vector3(v0), new Vector3(v2), new Vector3(v3)));
    }
}
