package proj;

import geometry.Mesh;
import geometry.Triangle;
import geometry.Vector3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObjParser {

    public static Mesh parse(String filePath) throws IOException {
        Mesh mesh = new Mesh();
        List<Vector3> vertices = new ArrayList<>(); // To store vertices from the OBJ file

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length > 0) {
                    switch (parts[0]) {
                        case "v":
                            // Vertex data
                            double x = Double.parseDouble(parts[1]);
                            double y = Double.parseDouble(parts[2]);
                            double z = Double.parseDouble(parts[3]);
                            vertices.add(new Vector3(x, y, z));
                            break;
                        case "f":
                            // Face data
                            if (parts.length == 5) {
                                // Quad face, split into two triangles
                                int v0 = Integer.parseInt(parts[1].split("/")[0]) - 1;
                                int v1 = Integer.parseInt(parts[2].split("/")[0]) - 1;
                                int v2 = Integer.parseInt(parts[3].split("/")[0]) - 1;
                                int v3 = Integer.parseInt(parts[4].split("/")[0]) - 1;
                                mesh.triangles.add(new Triangle(vertices.get(v0), vertices.get(v1), vertices.get(v2)));
                                mesh.triangles.add(new Triangle(vertices.get(v0), vertices.get(v2), vertices.get(v3)));
                            } else if (parts.length == 4) {
                                // Triangle face
                                int v0 = Integer.parseInt(parts[1].split("/")[0]) - 1;
                                int v1 = Integer.parseInt(parts[2].split("/")[0]) - 1;
                                int v2 = Integer.parseInt(parts[3].split("/")[0]) - 1;
                                mesh.triangles.add(new Triangle(vertices.get(v0), vertices.get(v1), vertices.get(v2)));
                            }
                            break;
                    }
                }
            }
        }

        return mesh;
    }
}