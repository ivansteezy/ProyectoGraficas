import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Engine extends JPanel
{
    private BufferedImage mBuffer;
    private Graphics mPixelGraphics;

    private Mesh mMeshCube;
    private Matrix4x4 mProjectionMatrix;
    float fTheta;

    Engine()
    {
        mMeshCube = new Mesh();
        mProjectionMatrix = new Matrix4x4();
        fTheta = 0;

        // initialize cube
        // South
        mMeshCube.triangles.add(new Triangle(new Vector3(0.0f, 0.0f, 0.0f), new Vector3(0.0f, 1.0f,0.0f), new Vector3(1.0f, 1.0f, 0.0f)));
        mMeshCube.triangles.add(new Triangle(new Vector3(0.0f, 0.0f, 0.0f), new Vector3(1.0f, 1.0f,0.0f), new Vector3(1.0f, 0.0f, 0.0f)));

        // East
        mMeshCube.triangles.add(new Triangle(new Vector3(1.0f, 0.0f, 0.0f), new Vector3(1.0f, 1.0f,0.0f), new Vector3(1.0f, 1.0f, 1.0f)));
        mMeshCube.triangles.add(new Triangle(new Vector3(1.0f, 0.0f, 0.0f), new Vector3(1.0f, 1.0f,1.0f), new Vector3(1.0f, 0.0f, 1.0f)));

        // North
        mMeshCube.triangles.add(new Triangle(new Vector3(1.0f, 0.0f, 1.0f), new Vector3(1.0f, 1.0f,1.0f), new Vector3(0.0f, 1.0f, 1.0f)));
        mMeshCube.triangles.add(new Triangle(new Vector3(1.0f, 0.0f, 1.0f), new Vector3(0.0f, 1.0f,1.0f), new Vector3(0.0f, 0.0f, 1.0f)));

        // West
        mMeshCube.triangles.add(new Triangle(new Vector3(0.0f, 0.0f, 1.0f), new Vector3(0.0f, 1.0f,1.0f), new Vector3(0.0f, 1.0f, 0.0f)));
        mMeshCube.triangles.add(new Triangle(new Vector3(0.0f, 0.0f, 1.0f), new Vector3(0.0f, 1.0f,0.0f), new Vector3(0.0f, 0.0f, 0.0f)));

        // Top
        mMeshCube.triangles.add(new Triangle(new Vector3(0.0f, 1.0f, 0.0f), new Vector3(0.0f, 1.0f,1.0f), new Vector3(1.0f, 1.0f, 1.0f)));
        mMeshCube.triangles.add(new Triangle(new Vector3(0.0f, 1.0f, 0.0f), new Vector3(1.0f, 1.0f,1.0f), new Vector3(1.0f, 1.0f, 0.0f)));

        // Bottom
        mMeshCube.triangles.add(new Triangle(new Vector3(1.0f, 0.0f, 1.0f), new Vector3(0.0f, 0.0f,1.0f), new Vector3(0.0f, 0.0f, 0.0f)));
        mMeshCube.triangles.add(new Triangle(new Vector3(1.0f, 0.0f, 1.0f), new Vector3(0.0f, 0.0f,0.0f), new Vector3(1.0f, 0.0f, 0.0f)));

        float fNear = 0.1f;
        float fFar = 1000.0f;
        float fFov = 90.0f;
        float fAspectRatio = 800 / 800;
        float fFovRad = 1.0f / (float)Math.tan(fFov * 0.5f / 180.0f * 3.14159f);

        mProjectionMatrix.matrix[0][0] = fAspectRatio * fFovRad;
        mProjectionMatrix.matrix[1][1] = fFovRad;
        mProjectionMatrix.matrix[2][2] = fFar / (fFar - fNear);
        mProjectionMatrix.matrix[3][2] = (-fFar * fNear) / (fFar - fNear);
        mProjectionMatrix.matrix[2][3] = 1.0f;
        mProjectionMatrix.matrix[3][3] = 0.0f;
    }

    private void MultiplyMatrixVector(Vector3 i, Vector3 o, Matrix4x4 m)
    {
        o.x = i.x * m.matrix[0][0] + i.y * m.matrix[1][0] + i.z * m.matrix[2][0] + m.matrix[3][0];
        o.y = i.x * m.matrix[0][1] + i.y * m.matrix[1][1] + i.z * m.matrix[2][1] + m.matrix[3][1];
        o.z = i.x * m.matrix[0][2] + i.y * m.matrix[1][2] + i.z * m.matrix[2][2] + m.matrix[3][2];

        float w = i.x * m.matrix[0][3] + i.y * m.matrix[1][3] + i.z * m.matrix[2][3] + m.matrix[3][3];

        if(w != 0.0f)
        {
            o.x /= w;
            o.y /= w;
            o.z /= w;
        }
    }

    @Override
    public void paintComponent(Graphics graphics)
    {
        graphics.setColor(Color.BLACK);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 800, 800);

        mBuffer = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        mPixelGraphics = mBuffer.createGraphics();

        var matRotZ = new Matrix4x4();
        var matRotX = new Matrix4x4();
        fTheta += 0.01f;

        //Rotation Z
        matRotZ.matrix[0][0] = (float)Math.cos(fTheta);
        matRotZ.matrix[0][1] = (float)Math.sin(fTheta);
        matRotZ.matrix[1][0] = -(float)Math.sin(fTheta);
        matRotZ.matrix[1][1] = (float)Math.cos(fTheta);
        matRotZ.matrix[2][2] = 1;
        matRotZ.matrix[3][3] = 1;

        //Rotation X
        matRotX.matrix[0][0] = 1;
        matRotX.matrix[1][1] = (float)Math.cos(fTheta * 0.5f);
        matRotX.matrix[1][2] = (float)Math.sin(fTheta * 0.5f);
        matRotX.matrix[2][1] = -(float)Math.sin(fTheta * 0.5f);
        matRotX.matrix[2][2] = (float)Math.cos(fTheta * 0.5f);;
        matRotX.matrix[3][3] = 1;

        //Draw triangles
        for(var tri : mMeshCube.triangles)
        {
            Triangle projectedTriangle = new Triangle();
            Triangle rotatedTriangleZ = new Triangle();
            Triangle rotatedTriangleX = new Triangle();

            //RotationZ
            MultiplyMatrixVector(tri.vertices[0], rotatedTriangleZ.vertices[0], matRotZ);
            MultiplyMatrixVector(tri.vertices[1], rotatedTriangleZ.vertices[1], matRotZ);
            MultiplyMatrixVector(tri.vertices[2], rotatedTriangleZ.vertices[2], matRotZ);

            //Rotation X
            MultiplyMatrixVector(rotatedTriangleZ.vertices[0], rotatedTriangleX.vertices[0], matRotX);
            MultiplyMatrixVector(rotatedTriangleZ.vertices[1], rotatedTriangleX.vertices[1], matRotX);
            MultiplyMatrixVector(rotatedTriangleZ.vertices[2], rotatedTriangleX.vertices[2], matRotX);


            //make a deep copy (java thing)
            Triangle translatedTriangle = new Triangle();
            translatedTriangle.vertices[0].x = rotatedTriangleX.vertices[0].x;
            translatedTriangle.vertices[1].x = rotatedTriangleX.vertices[1].x;
            translatedTriangle.vertices[2].x = rotatedTriangleX.vertices[2].x;

            translatedTriangle.vertices[0].y = rotatedTriangleX.vertices[0].y;
            translatedTriangle.vertices[1].y=  rotatedTriangleX.vertices[1].y;
            translatedTriangle.vertices[2].y = rotatedTriangleX.vertices[2].y;

            translatedTriangle.vertices[0].z = rotatedTriangleX.vertices[0].z;
            translatedTriangle.vertices[1].z = rotatedTriangleX.vertices[1].z;
            translatedTriangle.vertices[2].z = rotatedTriangleX.vertices[2].z;


            //offset into the screen
            translatedTriangle.vertices[0].z = rotatedTriangleX.vertices[0].z + 3.0f;
            translatedTriangle.vertices[1].z = rotatedTriangleX.vertices[1].z + 3.0f;
            translatedTriangle.vertices[2].z = rotatedTriangleX.vertices[2].z + 3.0f;

            //project
            MultiplyMatrixVector(translatedTriangle.vertices[0], projectedTriangle.vertices[0], mProjectionMatrix);
            MultiplyMatrixVector(translatedTriangle.vertices[1], projectedTriangle.vertices[1], mProjectionMatrix);
            MultiplyMatrixVector(translatedTriangle.vertices[2], projectedTriangle.vertices[2], mProjectionMatrix);

            //scale into view
            projectedTriangle.vertices[0].x += 1.0f; projectedTriangle.vertices[0].y += 1.0f;
            projectedTriangle.vertices[1].x += 1.0f; projectedTriangle.vertices[1].y += 1.0f;
            projectedTriangle.vertices[2].x += 1.0f; projectedTriangle.vertices[2].y += 1.0f;
            projectedTriangle.vertices[0].x *= 0.5 * (float)800;
            projectedTriangle.vertices[0].y *= 0.5 * (float)800;
            projectedTriangle.vertices[1].x *= 0.5 * (float)800;
            projectedTriangle.vertices[1].y *= 0.5 * (float)800;
            projectedTriangle.vertices[2].x *= 0.5 * (float)800;
            projectedTriangle.vertices[2].y *= 0.5 * (float)800;

            DrawTriangle(projectedTriangle.vertices[0].x, projectedTriangle.vertices[0].y,
                         projectedTriangle.vertices[1].x, projectedTriangle.vertices[1].y,
                         projectedTriangle.vertices[2].x, projectedTriangle.vertices[2].y,
                         Color.BLACK, mBuffer);
        }

        graphics.drawImage(mBuffer, 0, 0, this);
    }

    public static void DrawLine(int x0, int y0, int x1, int y1, Color col, BufferedImage bufferedImage)
    {
        Color c = col;
        float adjacent = (float) Float.max(x0, x1) - Float.min(x0, x1);
        float opposite = (float) Float.max(y0, y1) - Float.min(y0, y1);
        float steepness = (float) opposite / adjacent;

        int nextX = 0;
        int nextY = 0;
        steepness = Math.abs(steepness);

        nextX = (x0 < x1) ? 1 : -1;
        nextY = (y0 < y1) ? 1 : -1;

        if (Math.toDegrees(Math.atan(steepness)) > 45)
        {
            steepness = (float) Math.abs(adjacent / opposite);

            for (int i = 0; i <= Math.abs(opposite); i++)
            {
                bufferedImage.setRGB(x0 + (int) (i * steepness * nextX), y0 + (i * nextY), c.getRGB());
            }
        }
        else
        {
            for (int h = 0; h <= Math.abs(adjacent); h++)
            {
                bufferedImage.setRGB(x0 + h * nextX, y0 + (int) (h * steepness * nextY), c.getRGB());
            }
        }
    }

    public void DrawTriangle(float x1, float y1, float x2, float y2, float x3, float y3, Color color, BufferedImage bufferedImage)
    {
        DrawLine((int)x1, (int)y1, (int)x2, (int)y2, color, bufferedImage);
        DrawLine((int)x2, (int)y2, (int)x3, (int)y3, color, bufferedImage);
        DrawLine((int)x3, (int)y3, (int)x1, (int)y1, color, bufferedImage);
    }

    public static void main(String[] args) throws InterruptedException
    {

        Engine engine = new Engine();
        JFrame app = new JFrame("3D Graphics!");

        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.add(engine);
        app.setSize(800, 800);
        app.setVisible(true);

        while (true)
        {
            Thread.sleep(10);
            engine.repaint();
        }
    }
}
