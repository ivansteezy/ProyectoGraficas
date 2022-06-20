import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Mesh
{
    Mesh()
    {
        triangles = new ArrayList<Triangle>();
    }

    void LoadFromFile(String filepath)
    {
        var vertx = new ArrayList<Vector3>();
        var path = Paths.get(filepath);
        try
        {
            var lines = Files.readAllLines(path);
            for(var line : lines)
            {
                var stringData = line.split(" ");
                if(line.charAt(0) == 'v')
                {
                    Vector3 vec = new Vector3();

                    vec.x = Float.parseFloat(stringData[2]);
                    vec.y = Float.parseFloat(stringData[4]);
                    vec.z = Float.parseFloat(stringData[6]);

                    vertx.add(vec);
                }
                if(line.charAt(0) == 'f')
                {
                    int[] f = new int[3];
                    f[0] = Integer.parseInt(stringData[2]);
                    f[1] = Integer.parseInt(stringData[4]);
                    f[2] = Integer.parseInt(stringData[6]);

                    triangles.add(new Triangle(vertx.get(f[0] - 1), vertx.get(f[1] - 1), vertx.get(f[2] - 1)));
                }
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public List<Triangle> triangles;
}
