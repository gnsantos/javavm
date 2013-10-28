import java.awt.Point;
import java.util.Random;
import java.lang.*;
import java.util.*;

public class Battlefield{

    //    DEFINES:
    private static final char CRYSTAL = '*';
    private static final char ROBOT = 'r';
    private static final char EMPTY = ' ';
    
    private static final char W = '/';
    private static final char T = '^';
    private static final char E = ' ';
    private static final char B = '@';
    
    private static final char BOUNDS = T;
    
    private static final int NUM_ROBOTS = 8;
    private static final int NUM_CRYSTALS = 10;
    
    
    //    Attributes
    static MatrixHexa matrix;
        
    static Entity[][] arena;
        
    static char[][] screen;

	static char[][] map = {
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
	//Virtual Machines Atributtes
    private static Vector<BattleRobot> army = new Vector<BattleRobot>(NUM_ROBOTS);

 	public static void main (String argv[]){
        matrix = new MatrixHexa(map);
        initArena(map.length, map[0].length);
        initScreen(map.length, map[0].length, map);
        tellMeAboutTheWar();
	}
	static void tellMeAboutTheWar(){
		for (int x = 0; x < NUM_ROBOTS ; x++) {
        	System.out.println("Name : " + army.get(x).sayName() 
				+"\nSerial Number : " +army.get(x).saySerialNumber());
        	System.out.println("Team : " + army.get(x).getTeam());
        	System.out.print("Position : ");
        	army.get(x).showCoordinates();
        	System.out.println();
        	// if(army.get(x).positionScanner(4,8)){
        	// 	System.out.println("Im HERE!");
        	// }
        	// else{System.out.println("NOT HERE!");}
		}
	}
	static void insertArmy(int index, String team, String robotModel, int x, int y, int serialNumber){
        army.add(index, new BattleRobot(robotModel+"-" + serialNumber, serialNumber));
        army.get(index).setTeam("Team "+team);
        army.get(index).moveRobot(x,y);
	}

    static void initArena(int mapHeight, int mapWidth) {
        arena = new Entity[mapHeight][mapWidth];
        
        Random gen = new Random();
        gen.setSeed(3);
        
        int i;
        int j;
        
        //        Initializes the matriz with spaces
        for (i = 0; i < mapHeight; i++) {
            for (j = 0; j < mapWidth; j++) {
                arena[i][j] = null;
            }
        }
        
        //        Inserts robots at totally random locations
        for (int k = 0; k < NUM_ROBOTS; k++) {
            i = gen.nextInt(mapHeight);
            j = gen.nextInt(mapWidth);
            arena[i][j] = new Entity(ROBOT, i, j);
            if (k < NUM_ROBOTS/2){
            	insertArmy(k,"A","TX",i,j,gen.nextInt(1000));
            }
            else{
				insertArmy(k,"B","ZT",i,j,gen.nextInt(1000));
            }
            // System.out.println("Robo #"+k + "\n("+i+","+j+")");
            // System.out.println();
        }
        
        //        Inserts crystals at totally random locations
        for (int k = 0; k < NUM_CRYSTALS; k++) {
            i = gen.nextInt(mapHeight);
            j = gen.nextInt(mapWidth);
            arena[i][j] = new Entity(CRYSTAL, i, j);
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
                if (arena[i][j] != null) {
                    if (arena[i][j].type() == ROBOT) {
                        // x e y dão o ponto de "origem" do hexágono
                        y = j*7;
                        x = i*4;
                        if (j%2 == 0) x += 2;
                        
                        screen[x + 1][y + 4] = '◯';
                        screen[x + 2][y + 3] = '▔';
                        screen[x + 2][y + 4] = '█';
                        screen[x + 2][y + 5] = '▔';
                        screen[x + 3][y + 4] = '∆';
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