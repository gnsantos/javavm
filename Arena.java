import java.awt.Point;
import java.util.Random;

public class Arena {
    
    //    DEFINES:
    private static final char CRYSTAL = '*';
    private static final char ROBOT = 'r';
    private static final char EMPTY = ' ';
    
    private static final char W = '/';
    private static final char T = '^';
    private static final char E = ' ';
    private static final char B = '@';
    
    private static final char BOUNDS = T;
    
    private static final int NUM_ROBOTS = 16;
    private static final int NUM_CRYSTALS = 10;
    
    
    //    Attributes
    static char[][] map;
    
    static MatrixHexa matrix;
        
    static Entity[][] arena;
    
    static char[][] screen;

    // Controller Atributes
    
    
    public static void main(String[] args) {
        
//        Só dimensões pares no mapa!
//        Mapa é uma matriz de chars que contém as informações de como o mapa estará
        char[][] map = {
            {T, T, E, T, E, T, E, T, E, T, E, T, E, T, B, T},
            {T, E, W, E, T, E, T, E, T, E, T, E, T, E, E, T},
            {T, E, T, W, W, T, T, T, T, T, T, T, E, E, E, T},
            {T, E, T, T, T, W, W, T, T, T, E, E, T, T, E, T},
            {T, E, T, T, T, T, T, W, E, E, T, T, T, T, E, T},
            {T, E, T, T, T, T, E, E, T, W, W, T, T, T, E, T},
            {T, E, T, T, E, E, T, T, T, T, T, W, W, T, E, T},
            {T, E, E, E, T, T, T, T, T, T, T, T, T, W, E, T},
            {T, E, E, T, E, T, E, T, E, T, E, T, E, T, E, T},
            {T, B, T, E, T, E, T, E, T, E, T, E, T, E, T, T}
        };
        
        matrix = new MatrixHexa(map);
        
        Point size = matrix.size();
        
        // Arena é uma matriz contendo as posições dos robôs e dos cristais
        initArena(size.x, size.y);
        // Screen é uma matriz de chars que será diretamente impressa na tela
        initScreen(size.x, size.y, matrix);
        
        printArena();
        
    }
    
    static void initArena(int mapHeight, int mapWidth) {
        //  Cria a matriz
        arena = new Entity[mapHeight][mapWidth];
        
        //  Gerador de números aleatórios
        Random gen = new Random();
        
        int i;
        int j;
        
        // Coloca espaços em branco, para começar
        for (i = 0; i < mapHeight; i++) {
            for (j = 0; j < mapWidth; j++) {
                arena[i][j] = null;
            }
        }
        
        //  Insere robôs em lugares aleatórios do campo
        for (int k = 0; k < NUM_ROBOTS; k++) {
            i = gen.nextInt(mapHeight);
            j = gen.nextInt(mapWidth);
            arena[i][j] = new Entity(ROBOT, i, j);
        }
        
        //  Insere cristais em lugares aleatórios do campo
        for (int k = 0; k < NUM_CRYSTALS; k++) {
            i = gen.nextInt(mapHeight);
            j = gen.nextInt(mapWidth);
            arena[i][j] = new Entity(CRYSTAL, i, j);
        }
    }
    
    static void initScreen(int mapHeight, int mapWidth, MatrixHexa matrix) {
        
        //  Tamanho da tela, em "pixels"
        int m = mapHeight*4+2;
        int n = mapWidth*7 + 3;
        
        //  Cria a tela
        screen = new char[m][n];
        
        // Contadores
        int i = 0;
        int j = 0;
        int x = 0;
        int y = 0;
        char a;
        
        // Inicializa a matriz com espaços em branco
        for (i = 0; i < m; i++) {
            for (j = 0; j < n; j++) {
                screen[i][j] = EMPTY;
            }
        }
        
        // Coloca os limites do mapa
        for (i = 8; i < n-2; i++) {
            screen[0][i] = BOUNDS;
            screen[1][i] = BOUNDS;
        }
        for (i = 1; i < n-10; i++) {
            screen[m-1][i] = BOUNDS;
            screen[m-2][i] = BOUNDS;
        }
        
        // Coloca os limites dos hexágonos
        for (i = 0; i < m; i++) {
            if (i%4 == 0) {
                j = 0;
                while (j < n-3) {
                    screen[i][j] = '\\';
                    j += 8;
                    screen[i][j] = '/';
                    j += 6;
                }
                screen[i][j] = '\\';
            }
            if (i%4 == 1) {
                j = 1;
                while (j < n-2) {
                    screen[i][j] = '\\';
                    j += 6;
                    screen[i][j] = '/';
                    j += 8;
                }
                screen[i][j] = '\\';
            }
            if (i%4 == 2) {
                j = 1;
                while (j < n-2) {
                    screen[i][j] = '/';
                    j += 6;
                    screen[i][j] = '\\';
                    j += 8;
                }
                screen[i][j] = '/';
            }
            if (i%4 == 3) {
                j = 0;
                while (j < n-3) {
                    screen[i][j] = '/';
                    j += 8;
                    screen[i][j] = '\\';
                    j += 6;
                }
                screen[i][j] = '/';
            }
        }
        
        // Tira os traços soltos
        screen[0][0] = EMPTY;
        screen[1][1] = EMPTY;
        screen[m-1][n-2] = EMPTY;
        screen[m-2][n-3] = EMPTY;
        
        // Preenche os terrenos
        for (i = 0; i < mapHeight; i++) {
            for (j = 0; j < mapWidth; j++) {
                // x e y dão o ponto de "origem" do hexágono
                y = j*7;
                x = i*4;
                if (j%2 == 0) x += 2;
                
                // Pinta o hexágono com o terreno
                for (int l = 2; l < 7; l++) screen[x+0][y+l] = matrix.terrainChar(i, j);
                for (int l = 1; l < 8; l++) screen[x+1][y+l] = matrix.terrainChar(i, j);
                for (int l = 1; l < 8; l++) screen[x+2][y+l] = matrix.terrainChar(i, j);
                if (matrix.terrainChar(i, j) == EMPTY) a = '_';
                else a = matrix.terrainChar(i, j);
                for (int l = 2; l < 7; l++) screen[x+3][y+l] = a;
            }
        }
        
        // Desenha os robôs
        for (i = 0; i < mapHeight; i++) {
            for (j = 0; j < mapWidth; j++){
                if (arena[i][j] != null) {
                    if (arena[i][j].type() == ROBOT) {
                        // x e y dão o ponto de "origem" do hexágono
                        y = j*7;
                        x = i*4;
                        if (j%2 == 0) x += 2;
                        
                        screen[x + 1][y + 4] = '0';
                        screen[x + 2][y + 3] = '/';
                        screen[x + 2][y + 4] = 'H';
                        screen[x + 2][y + 5] = '\\';
                        screen[x + 3][y + 3] = '/';
                        screen[x + 3][y + 5] = '\\';
                    }
                }
            }
        }
        
        // Desenha os cristais
        for (i = 0; i < mapHeight; i++) {
            for (j = 0; j < mapWidth; j++){
                if (arena[i][j] != null) {
                    if (arena[i][j].type() == CRYSTAL) {
                        // x e y dão o ponto de "origem" do hexágono
                        y = j*7;
                        x = i*4;
                        if (j%2 == 0) x += 2;
                        
                        screen[x + 1][y + 4] = '*';
                    }
                }
            }
        }
    }
    
    static void printArena () {
        
        for (int i = 0; i < screen.length; i++) {
            for (int j = 0; j < screen[0].length; j++) {
                System.out.print(String.format("%c", screen[i][j]));
            }
            System.out.println(String.format(""));
        }
        
    }
    
    
    
}
