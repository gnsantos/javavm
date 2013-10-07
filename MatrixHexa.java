import java.awt.Point;
import java.util.Random;

public class MatrixHexa {
    
    private Terrain[][] terrains;
    
    private Point size;
    
    public MatrixHexa () {
        
        Random gen = new Random();
        
        size = new Point(10, 10);
        
        terrains = new Terrain[size.x][size.y];
        
        for (int i = 0; i < size.x; i++) {
            for (int j = 0; j < size.y; j++) {
                terrains[i][j] = new Terrain(gen.nextInt(26));
            }
        }
        
    }
    
    Point size() {
        return size;
    }
    
    int terrainIndex(int x, int y) {
        return terrains[x][y].index();
    }
    
}