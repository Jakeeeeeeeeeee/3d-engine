package geometry;

public class Vector3 {
    public double x, y, z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vector3(Vector3 other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    @Override
    public String toString() {
        return "Vector3(" + x + ", " + y + ", " + z + ")";
    }

    // Method for vector addition (overloading the + operator)
    public Vector3 add(Vector3 other) {
        return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    // Method for vector subtraction (overloading the - operator)
    public Vector3 subtract(Vector3 other) {
        return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    // Rotate this vector around the X-axis
    public void rotateX(double angle) {
        double cosAngle = Math.cos(angle);
        double sinAngle = Math.sin(angle);
        double yNew = y * cosAngle - z * sinAngle;
        double zNew = y * sinAngle + z * cosAngle;
        this.y = yNew;
        this.z = zNew;
    }

    // Rotate this vector around the Y-axis
    public void rotateY(double angle) {
        double cosAngle = Math.cos(angle);
        double sinAngle = Math.sin(angle);
        double xNew = x * cosAngle + z * sinAngle;
        double zNew = -x * sinAngle + z * cosAngle;
        this.x = xNew;
        this.z = zNew;
    }

    // Rotate this vector around the Z-axis
    public void rotateZ(double angle) {
        double cosAngle = Math.cos(angle);
        double sinAngle = Math.sin(angle);
        double xNew = x * cosAngle - y * sinAngle;
        double yNew = x * sinAngle + y * cosAngle;
        this.x = xNew;
        this.y = yNew;
    }

    public double magnitude(){
        return Math.sqrt(x*x+y*y+z*z);
    }

    public Vector3 multiply(double other) {
        return new Vector3(this.x * other, this.y * other, this.z * other);
    }

    public double dot(Vector3 other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }
    public Vector3 cross(Vector3 other) {
        double crossX = this.y * other.z - this.z * other.y;
        double crossY = this.z * other.x - this.x * other.z;
        double crossZ = this.x * other.y - this.y * other.x;
        return new Vector3(crossX, crossY, crossZ);
    }

    public Vector3 normalize() {
        double magnitude = magnitude();
        if (magnitude == 0) {
            return new Vector3(0, 0, 0); // Return a zero vector if the magnitude is 0 to avoid division by zero
        }
        return new Vector3(this.x / magnitude, this.y / magnitude, this.z / magnitude);
    }

}
