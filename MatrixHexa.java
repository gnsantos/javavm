public class MatrixHexa {
        
    private Terrain[] terrains;
    
    public MatrixHexa () {
        terrains = new Terrain[10];
        
        for (int i = 0; i < 10; i++) {
            terrains[i] = new Terrain();
        }
        
    }
    
}