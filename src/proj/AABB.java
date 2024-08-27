package proj;

import geometry.Vector3;

public class AABB {
    public Vector3 min;
    public Vector3 max;

    public AABB(Vector3 min, Vector3 max) {
        this.min = min;
        this.max = max;
    }

    public boolean intersects(AABB other) {
        // Check for overlap along each axis
        return (this.min.x <= other.max.x && this.max.x >= other.min.x) &&
                (this.min.y <= other.max.y && this.max.y >= other.min.y) &&
                (this.min.z <= other.max.z && this.max.z >= other.min.z);
    }
}