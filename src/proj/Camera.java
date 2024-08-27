package proj;

import geometry.Mesh;
import geometry.Triangle;
import geometry.Vector2;
import geometry.Vector3;

import java.awt.*;
import java.util.Comparator;

public class Camera {
    public Vector3 position;
    public double yaw;   // Horizontal rotation (looking left and right)
    public double pitch; // Vertical rotation (looking up and down)
    public double focalLength;

    public Camera(Vector3 position) {
        this.position = position;
        this.yaw = 0;
        this.pitch = 0;
    }
    public Camera(Vector3 position, double focalLength) {
        this.position = position;
        this.yaw = 0;
        this.pitch = 0;
        this.focalLength = focalLength;
    }

    public Vector3 getDirection() {

        double adjustedYaw = yaw - Math.PI / 2;

        double cosPitch = Math.cos(pitch);
        double sinPitch = Math.sin(pitch);
        double cosYaw = Math.cos(adjustedYaw);
        double sinYaw = Math.sin(adjustedYaw);

        return new Vector3(cosYaw * cosPitch, -sinPitch, -sinYaw * cosPitch);
    }

    public void moveForward(double speed) {
        Vector3 direction = getDirection();
        position.x += direction.x * speed*-1;
        position.y += direction.y * speed*-1;
        position.z += direction.z * speed*-1;
    }

    public void moveBackward(double speed) {
        Vector3 direction = getDirection();
        position.x -= direction.x * speed*-1;
        position.y -= direction.y * speed*-1;
        position.z -= direction.z * speed*-1;
    }

    public void strafeLeft(double speed) {
        double cosYaw = Math.cos(yaw);
        double sinYaw = Math.sin(yaw);
        position.x -= cosYaw * speed;
        position.z += sinYaw * speed;
    }

    public void strafeRight(double speed) {
        double cosYaw = Math.cos(yaw - Math.PI);
        double sinYaw = Math.sin(yaw - Math.PI);
        position.x -= cosYaw * speed;
        position.z += sinYaw * speed;
    }

    public Vector3 getScreenPos(){
        Vector3 forwardVectorOffset = getDirection().multiply(this.focalLength);
        return position.subtract(forwardVectorOffset);
    }

    public void moveUp(double speed) {
        position.y += speed;
    }

    public void moveDown(double speed) {
        position.y -= speed;
    }

    public  Vector2 projectTo2D(Vector3 point) {
        double scale = this.focalLength / (this.focalLength + point.z);
        double x2D = point.x * scale + util.screenDims.x / 2.0;
        double y2D = point.y * scale + util.screenDims.y / 2.0;
        return new Vector2(x2D, y2D);
    }

    public  void renderMesh(Mesh mesh, Graphics g, Color c) {
        // Call the overloaded method with the default value for pos
        renderMesh(mesh, g, c, new Vector3(0, 0, 0));
    }

    public  void renderMesh(Mesh mesh, Graphics g, Color c, Vector3 pos) {


        mesh.triangles.sort(new Comparator<Triangle>() {
            public int compare(Triangle t1, Triangle t2) {
                return Double.compare(t2.distanceTo(getScreenPos()), t1.distanceTo(getScreenPos()));
            }
        });

        // Precompute the normalized camera direction
        Vector3 normalizedCameraDir = getDirection();
        Vector3 p1 = new Vector3();
        Vector3 p2 = new Vector3();
        Vector3 p3 = new Vector3();

        Vector2 projectedP1 = new Vector2();
        Vector2 projectedP2 = new Vector2();
        Vector2 projectedP3 = new Vector2();

        for (Triangle triangle : mesh.triangles) {

            if(true || !isPointBehindCamera(triangle.getCentroid())) {
                // Use in-place modifications to reduce object creation
                p1 = triangle.p1.subtract(getScreenPos()).add(pos);
                p2 = triangle.p2.subtract(getScreenPos()).add(pos);
                p3 = triangle.p3.subtract(getScreenPos()).add(pos);




                // Rotate the triangle vertices around the camera's origin
                rotatePointAroundCamera(p1);
                rotatePointAroundCamera(p2);
                rotatePointAroundCamera(p3);


                // Project the transformed points to 2D
                projectedP1 = this.projectTo2D(p1);
                projectedP2 = projectTo2D(p2);
                projectedP3 = projectTo2D(p3);

                // Compute lighting
                double lightIntensity = Math.max(0, normalizedCameraDir.dot(triangle.getNormal()));

                // Apply lighting and clamp colors
                int red = util.clamp((int) (c.getRed() * lightIntensity), 0, 255);
                int green = util.clamp((int) (c.getGreen() * lightIntensity), 0, 255);
                int blue = util.clamp((int) (c.getBlue() * lightIntensity), 0, 255);

                // Render the triangle
                g.setColor(new Color(red, green, blue));
                g.fillPolygon(new int[]{(int) projectedP1.x, (int) projectedP2.x, (int) projectedP3.x},
                        new int[]{(int) projectedP1.y, (int) projectedP2.y, (int) projectedP3.y}, 3);
                g.drawPolygon(new int[]{(int) projectedP1.x, (int) projectedP2.x, (int) projectedP3.x},
                        new int[]{(int) projectedP1.y, (int) projectedP2.y, (int) projectedP3.y}, 3);
            }
        }
    }

    public boolean isPointBehindCamera(Vector3 point) {
        // Calculate the vector from the camera to the point
        Vector3 pointDir = point.subtract(getScreenPos());

        // Calculate the vector from the camera to the projection plane
        Vector3 camDir = getDirection();


        // Check if the point is behind the camera by comparing directions
        // If the dot product is negative, the point is behind the plane (screen)
        return pointDir.normalize().dot(camDir.normalize())<0;
    }

    public void rotatePointAroundCamera(Vector3 point) {
        // Apply yaw rotation (horizontal)
        point.rotateY(-yaw);

        // Apply pitch rotation (vertical)
        point.rotateX(-pitch);

        // Return the rotated point
    }

}