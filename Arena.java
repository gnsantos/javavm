import java.awt.Point;
import java.util.Random;

public class Arena {
    
    //    DEFINES:
    private static final char CRYSTAL = '*';
    private static final char ROBOT = 'r';
    private static final char EMPTY = ' ';
    
    private static final char BOUNDS = '^';
    
    private static final int NUM_ROBOTS = 16;
    private static final int NUM_CRYSTALS = 10;
    
    
    //    Attributes
    static MatrixHexa matrix;
    
    static Robot[] robots;
    
    static char[][] arena;
    
    static char[][] map;
    
    static char[][] screen;
    
    public static void main(String[] args) {
        
//        Só dimensões pares no mapa!
        char[][] map = {
            {'^', '^', ' ', '^', ' ', '^', ' ', '^', ' ', '^', ' ', '^', ' ', '^', '@', '^'},
            {'^', ' ', '/', ' ', '^', ' ', '^', ' ', '^', ' ', '^', ' ', '^', ' ', ' ', '^'},
            {'^', ' ', '^', '/', '/', '^', '^', '^', '^', '^', '^', '^', ' ', ' ', ' ', '^'},
            {'^', ' ', '^', '^', '^', '/', '/', '^', '^', '^', ' ', ' ', '^', '^', ' ', '^'},
            {'^', ' ', '^', '^', '^', '^', '^', '/', ' ', ' ', '^', '^', '^', '^', ' ', '^'},
            {'^', ' ', '^', '^', '^', '^', ' ', ' ', '^', '/', '/', '^', '^', '^', ' ', '^'},
            {'^', ' ', '^', '^', ' ', ' ', '^', '^', '^', '^', '^', '/', '/', '^', ' ', '^'},
            {'^', ' ', ' ', ' ', '^', '^', '^', '^', '^', '^', '^', '^', '^', '/', ' ', '^'},
            {'^', ' ', ' ', '^', ' ', '^', ' ', '^', ' ', '^', ' ', '^', ' ', '^', ' ', '^'},
            {'^', '@', '^', ' ', '^', ' ', '^', ' ', '^', ' ', '^', ' ', '^', ' ', '^', '^'}
        };
        
        matrix = new MatrixHexa(map);
        
        robots = new Robot[10];
        
        initArena(map.length, map[0].length);
        initScreen(map.length, map[0].length, map);
        
        HexDirection dir = HexDirection.LEFT;
        
        if (dir.x() == -1) {
        }
        
        printArena();
        
    }
    
    static void initArena(int mapHeight, int mapWidth) {
        arena = new char[mapHeight][mapWidth];
        
        Random gen = new Random();
        
        //        Initializes the matriz with spaces
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                arena[i][j] = EMPTY;
            }
        }
        
        //        Inserts robots at totally random locations
        for (int k = 0; k < NUM_ROBOTS; k++) {
            arena[gen.nextInt(mapHeight)][gen.nextInt(mapWidth)] = ROBOT;
        }
        
        //        Inserts crystals at totally random locations
        for (int k = 0; k < NUM_CRYSTALS; k++) {
            arena[gen.nextInt(mapHeight)][gen.nextInt(mapWidth)] = CRYSTAL;
        }
    }
    
    static void initScreen(int mapHeight, int mapWidth, char[][] map) {
        
        int m = mapHeight*4+2;
        int n = mapWidth*7 + 3;
        
        screen = new char[m][n];

        int i = 0;
        int j = 0;
        int x = 0;
        int y = 0;
        char a;
        
        // Inicializa a matriz
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
                for (int l = 2; l < 7; l++) screen[x+0][y+l] = map[i][j];
                for (int l = 1; l < 8; l++) screen[x+1][y+l] = map[i][j];
                for (int l = 1; l < 8; l++) screen[x+2][y+l] = map[i][j];
                if (map[i][j] == EMPTY) a = '_';
                else a = map[i][j];
                for (int l = 2; l < 7; l++) screen[x+3][y+l] = a;
            }
        }
        
        // Desenha os robôs
        for (i = 0; i < mapHeight; i++) {
            for (j = 0; j < mapWidth; j++){
                if (arena[i][j] == ROBOT) {
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
        
        // Desenha os cristais
        for (i = 0; i < mapHeight; i++) {
            for (j = 0; j < mapWidth; j++){
                if (arena[i][j] == CRYSTAL) {
                    // x e y dão o ponto de "origem" do hexágono
                    y = j*7;
                    x = i*4;
                    if (j%2 == 0) x += 2;
                    
                    screen[x + 1][y + 4] = '*';
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

//          /     \
//         /   0   \
//   /^^^^^\  /H\  /
//  /^^^^^^^\_/_\_/
//  \^^^^^^^/     \
//   \^^^^^/   0   \
//   /     \  /H\  /
//  /   *   \ / \ /
//  \       /^^^^^\
//   \_____/^^^^^^^\
//   /     \^^^^^^^/
//  /       \^^^^^/
//  \       /
//   \     /




//\^^^^^^^/^^^^^\^^^^^^^/^^^^^\^^^^^^^/^^^^^\^^^^^^^/^^^^^\^^^^^^^/^^^^^\^^^^^^^/^^^^^\^^^^^^^/^^^^^\^^^^^^^/^^^^^\
//^\^^^^^/^^^^^^^\^^^^^/^^^^^^^\^^^^^/^^^^^^^\^^^^^/^^^^^^^\^^^^^/^^^*^^^\^^^^^/^^^^^^^\^^^^^/^^^^^^^\^^^^^/^^^^^^^\
//^/^^^^^\^^^^^^^/     \^^^^^^^/     \^^^^^^^/     \^^^^^^^/     \^^^^^^^/     \^^^^^^^/  0  \^^^^^^^/@@0@@\^^^^^^^/
///^^^*^^^\^^^^^/       \^^^^^/       \^^^^^/       \^^^^^/       \^^^^^/   *   \^^^^^/  /H\  \^^^^^/@@/H\@@\^^^^^/
//\^^^^^^^/     \       /     \       /     \       /     \       /     \       /     \       /     \@@@@@@@/^^^^^\
//^\^^^^^/       \     /       \     /       \     /       \     /       \     /       \     /       \@@@@@/^^^^^^^\
//^/^^^^^\       /wwwww\       /^^^^^\       /^^^^^\       /^^^^^\       /^^^^^\       /^^^^^\       /     \^^^^^^^/
///^^^^^^^\     /wwwwwww\     /^^^^^^^\     /^^^*^^^\     /^^^^^^^\     /^^^^^^^\     /^^^^^^^\     /       \^^^^^/
//\^^^^^^^/     \wwwwwww/wwwww\^^^^^^^/^^^^^\^^^^^^^/^^^^^\^^^^^^^/^^^^^\^^^^^^^/^^^^^\^^/^\^^/     \       /^^^^^\
//^\^^^^^/       \wwwww/wwwwwww\^^^^^/^^^^^^^\^^^^^/^^^^^^^\^^^^^/^^^^^^^\^^^^^/^^^^^^^\^^^^^/       \     /^^^^^^^\
//^/^^^^^\       /^^^^^\wwwwwww/wwwww\^^^^^^^/^^^^^\^^^^^^^/^^^^^\^^^^^^^/^^^^^\^^^^^^^/  0  \       /     \^^^^^^^/
///^^^^^^^\     /^^^^^^^\wwwww/wwwwwww\^^^^^/^^^^^^^\^^^^^/^^^^^^^\^^^^^/^^^^^^^\^^^^^/  /H\  \     /   *   \^^^^^/
//\^^^^^^^/  0  \^^^^^^^/^^^^^\wwwwwww/wwwww\^^/^\^^/^^0^^\^^^^^^^/^^^^^\^^^^^^^/     \       /^^^^^\       /^^^^^\
//^\^^^^^/  /H\  \^^^^^/^^^^^^^\wwwww/wwwwwww\^^^^^/^^/H\^^\^^^^^/^^^^^^^\^^^^^/       \     /^^^*^^^\     /^^^^^^^\
//^/^^^^^\  / \  /^^^^^\^^^^^^^/^^^^^\wwwwwww/ww0ww\^^/^\^^/^^^^^\^^^^^^^/     \       /^^^^^\^^^^^^^/     \^^^^^^^/
///^^^^^^^\     /^^^^^^^\^^^^^/^^^*^^^\wwwww/ww/H\ww\^^^^^/^^^^^^^\^^^^^/       \     /^^^^^^^\^^^^^/       \^^^^^/
//\^^^^^^^/     \^^^^^^^/^^^^^\^^^^^^^/^^^^^\wwwwwww/wwwww\^^/^\^^/  0  \       /^^^^^\^^^^^^^/^^0^^\       /^^^^^\
//^\^^^^^/       \^^^^^/^^^^^^^\^^^^^/^^^^^^^\wwwww/wwwwwww\^^^^^/  /H\  \     /^^^^^^^\^^^^^/^^/H\^^\     /^^^^^^^\
//^/^^^^^\       /^^^^^\^^^^^^^/^^^^^\^^^^^^^/^^^^^\wwwwwww/  0  \  / \  /^^^^^\^^^^^^^/^^^^^\^^/^\^^/     \^^^^^^^/
///^^^^^^^\     /^^^^^^^\^^^^^/^^^^^^^\^^^^^/^^^^^^^\wwwww/  /H\  \     /^^^^^^^\^^^^^/^^^^^^^\^^^^^/       \^^^^^/
//\^^^^^^^/     \^^^^^^^/^^^^^\^^^^^^^/^^^^^\^^/^\^^/     \       /wwwww\^^^^^^^/^^^^^\^^/^\^^/^^^^^\       /^^^^^\
//^\^^^^^/       \^^^^^/^^^^^^^\^^^^^/^^^^^^^\^^^^^/       \     /wwwwwww\^^^^^/^^^^^^^\^^^^^/^^^^^^^\     /^^^^^^^\
//^/^^^^^\       /^^^^^\^^^^^^^/^^^^^\^^^^^^^/  0  \       /^^^^^\wwwwwww/wwwww\^^^^^^^/^^0^^\^^^^^^^/     \^^^^^^^/
///^^^^^^^\     /^^^^^^^\^^^^^/^^^^^^^\^^^^^/  /H\  \     /^^^^^^^\wwwww/wwwwwww\^^^^^/^^/H\^^\^^^^^/       \^^^^^/
//\^^^^^^^/     \^^^^^^^/^^^^^\^^^^^^^/     \       /^^^^^\^^^^^^^/^^^^^\wwwwwww/wwwww\^^^^^^^/^^^^^\       /^^^^^\
//^\^^^^^/       \^^^^^/^^^^^^^\^^^^^/       \     /^^^^^^^\^^^^^/^^^^^^^\wwwww/wwwwwww\^^^^^/^^^^^^^\     /^^^^^^^\
//^/^^^^^\       /^^^^^\^^^^^^^/     \       /^^^^^\^^^^^^^/^^^^^\^^^^^^^/^^^^^\wwwwwww/wwwww\^^^^^^^/     \^^^^^^^/
///^^^^^^^\     /^^^^^^^\^^^^^/       \     /^^^^^^^\^^^^^/^^^^^^^\^^^^^/^^^^^^^\wwwww/wwwwwww\^^^^^/       \^^^^^/
//\^^^^^^^/     \^^^^^^^/     \       /^^^^^\^^^^^^^/^^^^^\^^^^^^^/^^^^^\^^^^^^^/^^^^^\wwwwwww/wwwww\       /^^^^^\
//^\^^^^^/       \^^^^^/       \     /^^^^^^^\^^^^^/^^^^^^^\^^^^^/^^^^^^^\^^^^^/^^^^^^^\wwwww/wwwwwww\     /^^^^^^^\
//^/^^^^^\       /     \       /^^^^^\^^^^^^^/^^^^^\^^^^^^^/^^^^^\^^^^^^^/^^^^^\^^^^^^^/^^^^^\wwwwwww/     \^^^^^^^/
///^^^^^^^\     /       \     /^^^^^^^\^^^^^/^^^*^^^\^^^^^/^^^^^^^\^^^^^/^^^^^^^\^^^^^/^^^^^^^\wwwww/       \^^^^^/
//\^^^^^^^/     \  / \  /^^^^^\^^^^^^^/^^^^^\^^^^^^^/^^^^^\^^^^^^^/^^^^^\^^^^^^^/^^^^^\^^^^^^^/^^^^^\  / \  /^^^^^\
//^\^^^^^/       \     /^^^^^^^\^^^^^/^^^^^^^\^^^^^/^^^^^^^\^^^^^/^^^^^^^\^^^^^/^^^^^^^\^^^^^/^^^*^^^\     /^^^^^^^\
//^/^^^^^\       /  0  \^^^^^^^/     \^^^^^^^/     \^^^^^^^/     \^^^^^^^/     \^^^^^^^/     \^^^^^^^/  0  \^^^^^^^/
///^^^^^^^\     /  /H\  \^^^^^/       \^^^^^/       \^^^^^/       \^^^^^/       \^^^^^/   *   \^^^^^/  /H\  \^^^^^/
//\^^^^^^^/@@@@@\       /     \  / \  /     \       /  0  \  / \  /     \       /     \       /     \       /^^^^^\
//^\^^^^^/@@@@@@@\     /       \     /       \     /  /H\  \     /       \     /       \     /       \     /^^^^^^^\
//^/^^^^^\@@@@@@@/^^^^^\       /^^0^^\       /^^^^^\  / \  /^^0^^\       /^^^^^\       /^^^^^\       /^^^^^\^^^^^^^/
///^^^^^^^\@@@@@/^^^^^^^\     /^^/H\^^\     /^^^^^^^\     /^^/H\^^\     /^^^^^^^\     /^^^^^^^\     /^^^^^^^\^^^^^/


