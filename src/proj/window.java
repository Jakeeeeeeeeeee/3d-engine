package proj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import geometry.*;

public class window extends JFrame implements MouseMotionListener, KeyListener {

    private Graphics bufferGraphics;
    private Graphics windowGraphics;
    private Image buffer;
    private Timer timer;
    private ActionListener loop;

    private Camera camera;
    private int lastMouseX, lastMouseY;

    private boolean moveForward = false;
    private boolean moveBackward = false;
    private boolean strafeLeft = false;
    private boolean strafeRight = false;
    private boolean moveUp = false;
    private boolean moveDown = false;


    private Object3D cat;
    private Object3D road;



    public window() {
        setSize(16 * 120, 9 * 120);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        util.screenDims.x = getWidth();
        util.screenDims.y = getHeight();

        windowGraphics = getGraphics();

        buffer = createImage(getWidth(), getHeight());
        bufferGraphics = buffer.getGraphics();

        camera = new Camera(new Vector3(0, 0, 0),500);

        addMouseMotionListener(this); // Add mouse motion listener
        addKeyListener(this);         // Add key listener for movement controls

        // Hide the cursor
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.createImage("");
        Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), "invisibleCursor");
        setCursor(c);

        cat = new Object3D("src/12221_Cat_v1_l3.obj");
        cat.scale(10);
//        cat.physics = true;
        cat.position = new Vector3(0,-500,0);
        cat.rotate(new Vector3(Math.PI/2,0,0));

        road = new Object3D("src/road.obj");
        road.scale(100);




        loop = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                render();
                double speed = 10.0; // Movement speed
                if(moveForward){
                    camera.moveForward(speed);
                }
                if(moveBackward){
                    camera.moveBackward(speed);
                }
                if(strafeLeft){
                    camera.strafeLeft(speed);
                }
                if(strafeRight){
                    camera.strafeRight(speed);
                }
                if(moveDown){
                    camera.moveDown(speed);
                }
                if(moveUp){
                    camera.moveUp(speed);
                }
            }
        };

        timer = new Timer(1000/120, loop);
        timer.start();
    }


    public void render() {
        bufferGraphics.setColor(Color.GRAY);
        bufferGraphics.fillRect(0, 0, getWidth(), getHeight());

        // Render your objects relative to the camera


        Vector3 origin = new Vector3(0,0,0);
        Vector3 px = new Vector3(10000,0,0);
        Vector3 nx = new Vector3(-10000,0,0);

        Vector3 py = new Vector3(0,10000,0);
        Vector3 ny = new Vector3(0,-10000,0);

        Vector3 pz = new Vector3(0,0,10000);
        Vector3 nz = new Vector3(0,0,-10000);

        Triangle xaxis = new Triangle(nx,origin,px);
        Triangle yaxis = new Triangle(ny,origin,py);
        Triangle zaxis = new Triangle(nz,origin,pz);

        Mesh xaxism = new Mesh();
        Mesh yaxism = new Mesh();
        Mesh zaxism = new Mesh();

        xaxism.triangles.add(xaxis);
        yaxism.triangles.add(yaxis);
        zaxism.triangles.add(zaxis);

        camera.renderMesh(xaxism,bufferGraphics,Color.BLUE);
        camera.renderMesh(yaxism,bufferGraphics,Color.GREEN);
        camera.renderMesh(zaxism,bufferGraphics,Color.RED);



        Cube camCube = new Cube(camera.getDirection().multiply(camera.focalLength),10);





        cat.render(bufferGraphics,camera,new Color(255, 100,150));

        Ray camDir = new Ray(new Vector3(),camera.getDirection());

        camDir.render(bufferGraphics,camera);



       // camToOrigin.render(bufferGraphics,camera);


        camera.renderMesh(camCube,bufferGraphics,Color.yellow,camera.getDirection().multiply(camera.focalLength));



        windowGraphics.drawImage(buffer, 0, 0, null);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        int deltaX = mouseX - lastMouseX;
        int deltaY = mouseY - lastMouseY;

        // Adjust camera yaw and pitch based on mouse movement
        camera.yaw += deltaX * 0.01;   // Sensitivity can be adjusted
        camera.pitch -= deltaY * 0.01;

        // Limit pitch to avoid flipping (gimbal lock)
        camera.pitch = Math.max(-Math.PI/2, Math.min(Math.PI/2, camera.pitch));

        lastMouseX = mouseX;
        lastMouseY = mouseY;

        // Re-center the mouse cursor
        centerMouse();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e); // Treat dragging the same as moving for first-person control
    }

    @Override
    public void keyPressed(KeyEvent e) {


        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: // Move forward
                moveForward = true;
                break;
            case KeyEvent.VK_S: // Move backward
                moveBackward = true;
                break;
            case KeyEvent.VK_A: // Strafe left
                strafeLeft = true;
                break;
            case KeyEvent.VK_D: // Strafe right
                strafeRight = true;
                break;
            case KeyEvent.VK_SPACE: // Move up (fly up)
                moveUp = true;
                break;
            case KeyEvent.VK_SHIFT: // Move down (fly down)
                moveDown = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: // Move forward
                moveForward = false;
                break;
            case KeyEvent.VK_S: // Move backward
                moveBackward = false;
                break;
            case KeyEvent.VK_A: // Strafe left
                strafeLeft = false;
                break;
            case KeyEvent.VK_D: // Strafe right
                strafeRight = false;
                break;
            case KeyEvent.VK_SPACE: // Move up (fly up)
                moveUp = false;
                break;
            case KeyEvent.VK_SHIFT: // Move down (fly down)
                moveDown = false;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Handle key typed if needed
    }

    private void centerMouse() {
        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        Point center = new Point(getWidth() / 2, getHeight() / 2);

        if (pointerInfo != null) {
            Robot robot;
            try {
                robot = new Robot();
                robot.mouseMove(getLocationOnScreen().x + center.x, getLocationOnScreen().y + center.y);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }

        lastMouseX = center.x;
        lastMouseY = center.y;
    }

    public static void main(String[] args) {
        new window();
    }
}
