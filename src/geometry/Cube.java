package geometry;

public class Cube extends Mesh {

    public Cube(Vector3 pos, double scale) {
        super();

        Vector3 v0 = new Vector3(pos.x - 0.5 * scale, pos.y - 0.5 * scale, pos.z - 0.5 * scale);
        Vector3 v1 = new Vector3(pos.x + 0.5 * scale, pos.y - 0.5 * scale, pos.z - 0.5 * scale);
        Vector3 v2 = new Vector3(pos.x + 0.5 * scale, pos.y + 0.5 * scale, pos.z - 0.5 * scale);
        Vector3 v3 = new Vector3(pos.x - 0.5 * scale, pos.y + 0.5 * scale, pos.z - 0.5 * scale);
        Vector3 v4 = new Vector3(pos.x - 0.5 * scale, pos.y - 0.5 * scale, pos.z + 0.5 * scale);
        Vector3 v5 = new Vector3(pos.x + 0.5 * scale, pos.y - 0.5 * scale, pos.z + 0.5 * scale);
        Vector3 v6 = new Vector3(pos.x + 0.5 * scale, pos.y + 0.5 * scale, pos.z + 0.5 * scale);
        Vector3 v7 = new Vector3(pos.x - 0.5 * scale, pos.y + 0.5 * scale, pos.z + 0.5 * scale);

        // Corrected triangles with counter-clockwise winding

        // Front face
        this.triangles.add(new Triangle(new Vector3(v0), new Vector3(v2), new Vector3(v1)));
        this.triangles.add(new Triangle(new Vector3(v0), new Vector3(v3), new Vector3(v2)));

        // Back face
        this.triangles.add(new Triangle(new Vector3(v4), new Vector3(v6), new Vector3(v5)));
        this.triangles.add(new Triangle(new Vector3(v4), new Vector3(v7), new Vector3(v6)));

        // Left face
        this.triangles.add(new Triangle(new Vector3(v0), new Vector3(v7), new Vector3(v3)));
        this.triangles.add(new Triangle(new Vector3(v0), new Vector3(v4), new Vector3(v7)));

        // Right face
        this.triangles.add(new Triangle(new Vector3(v1), new Vector3(v6), new Vector3(v5)));
        this.triangles.add(new Triangle(new Vector3(v1), new Vector3(v2), new Vector3(v6)));

        // Top face
        this.triangles.add(new Triangle(new Vector3(v3), new Vector3(v6), new Vector3(v2)));
        this.triangles.add(new Triangle(new Vector3(v3), new Vector3(v7), new Vector3(v6)));

        // Bottom face
        this.triangles.add(new Triangle(new Vector3(v0), new Vector3(v5), new Vector3(v4)));
        this.triangles.add(new Triangle(new Vector3(v0), new Vector3(v1), new Vector3(v5)));
    }
}
