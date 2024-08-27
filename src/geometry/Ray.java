package geometry;

import java.awt.*;
import proj.Camera;
import proj.util;

public class Ray {
    Vector3 origin;
    Vector3 direction;

    public Ray(){
        origin = new Vector3();
        direction = new Vector3();
    }

    public Ray(Vector3 origin, Vector3 direction){
        this.origin = origin;
        this.direction = direction;
    }

    public void render(Graphics g, Camera c){

        Vector3 normalizedDirection = direction.normalize();
        Triangle ray = new Triangle(origin,origin,origin.add(normalizedDirection.multiply(100)));

        Mesh rayMesh = new Mesh();
        rayMesh.triangles.add(ray);

        c.renderMesh(rayMesh,g,new Color(200,150,20),this.origin);
    }
}
