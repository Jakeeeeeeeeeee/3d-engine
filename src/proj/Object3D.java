package proj;

import geometry.*;

import java.awt.*;
import java.io.IOException;

public class Object3D {

    public Mesh mesh;
    public Vector3 position;
    public Vector3 acceleration;
    public Vector3 velocity;
    public boolean physics;
    public Vector3 rotation;


    public Object3D(String path){
        try {
            this.mesh = ObjParser.parse(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.position = new Vector3(0,0,0);
        this.acceleration = new Vector3(0,1,0);
        this.velocity = new Vector3(0,0,0);
        this.rotation = new Vector3(0,0,0);
        this.physics = false;
    }

    public void render(Graphics g, Camera cam, Color c){
        cam.renderMesh(this.mesh,g,c, this.position);

        if(this.physics){
            physics();
        }

    }

    public void scale(double scalar){
        this.mesh.scale(scalar);
    }

    public void physics(){
        this.velocity=this.velocity.add(this.acceleration);
        this.position=this.position.add(this.velocity);

    }

    public AABB getAABB() {
        Vector3 min = new Vector3(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        Vector3 max = new Vector3(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);

        for (Triangle triangle : this.mesh.triangles) {
            for (Vector3 vertex : new Vector3[]{triangle.p1, triangle.p2, triangle.p3}) {
                min.x = Math.min(min.x, vertex.x + position.x);
                min.y = Math.min(min.y, vertex.y + position.y);
                min.z = Math.min(min.z, vertex.z + position.z);

                max.x = Math.max(max.x, vertex.x + position.x);
                max.y = Math.max(max.y, vertex.y + position.y);
                max.z = Math.max(max.z, vertex.z + position.z);
            }
        }

        return new AABB(min, max);
    }

    public boolean checkCollision(Object3D other) {
        AABB aabb1 = this.getAABB();
        AABB aabb2 = other.getAABB();
        return aabb1.intersects(aabb2);
    }

    public void handleCollision(Object3D other) {
        // Example: Simple collision response (reverse velocity)
        if (checkCollision(other)) {
            this.velocity = this.velocity.multiply(-1);
            other.velocity = other.velocity.multiply(-1);
        }
    }

    public void rotate(Vector3 rotation){
        this.mesh.rotateX(rotation.x);
        this.mesh.rotateY(rotation.y);
        this.mesh.rotateZ(rotation.z);
    }
}
