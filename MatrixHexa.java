public class MatrixHexa {
    
    private String hello;
    
    private Terrain[] array;
    
    public MatrixHexa () {
        array = new Terrain[10];
        
        for (int i = 0; i < 10; i++) {
            array[i] = new Terrain();
        }
        
    }
    
    public String hello () {
        return this.hello;
    }
    
}