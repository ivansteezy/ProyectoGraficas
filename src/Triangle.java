public class Triangle
{
    public Triangle()
    {
        vertices = new Vector3[3];
        vertices[0] = new Vector3(0, 0, 0);
        vertices[1] = new Vector3(0, 0, 0);
        vertices[2] = new Vector3(0, 0, 0);
    }

    public Triangle(Vector3 point1, Vector3 point2, Vector3 point3)
    {
        vertices = new Vector3[3];
        vertices[0] = new Vector3(point1.x, point1.y, point1.z);
        vertices[1] = new Vector3(point2.x, point2.y, point2.z);
        vertices[2] = new Vector3(point3.x, point3.y, point3.z);

        /*vertices[0].x = point1.x;
        vertices[0].y = point1.y;
        vertices[0].z = point1.z;

        vertices[1].x = point2.x;
        vertices[1].y = point2.y;
        vertices[1].z = point2.z;

        vertices[2].x = point3.x;
        vertices[2].y = point3.y;
        vertices[2].z = point3.z;*/
    }
    public Vector3[] vertices;
}
