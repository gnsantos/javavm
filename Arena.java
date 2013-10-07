import java.awt.*;

public class Arena {
    
    static MatrixHexa matrix;
    
    static Robot[] robots;
    
    static Crystal[] crystals;
    
    
    
    public static void main(String[] args) {
        matrix = new MatrixHexa();
        
        robots = new Robot[10];
        
        crystals = new Crystal[10];
        
        HexDirection dir = HexDirection.LEFT;
        
        if (dir.x() == -1) {
        }
        
        printArena();
        
    }
    
    
    static void printArena () {
        
        Point size = matrix.size();
        
        for (int i = 0; i <= size.x; i++) {          // Percorre as linhas da matriz
            for (int k = 0; k < 4; k++) {           // Cada hexágono ocupa 4 linhas
                
                if (k == 0) System.out.print("\\");
                if (k == 1) System.out.print(" \\");
                if (k == 2) System.out.print(" /");
                if (k == 3) System.out.print("/");
                
                if (i == size.x && k > 1) break;
                
                for (int j = 0; j < size.y; j ++) { // Percorre as colunas da matriz
                    
                    int x = 0;
                    int chars = 0;
                    String terminator = "";
                    String a = "";
                    
                    if (k == 0) {               // A primeira linha:
                        
                        if (j%2 == 0) {
                            x = i-1;
                            chars = 7;
                            terminator = "/";
                        }
                        else {
                            x = i;
                            chars = 5;
                            terminator = "\\";
                        }
                    }
                    if (k == 1) {               // A segunda linha:
                        
                        if (j%2 == 0) {
                            x = i-1;
                            chars = 5;
                            terminator = "/";
                        }
                        else {
                            x = i;
                            chars = 7;
                            terminator = "\\";
                        }
                    }
                    if (k == 2) {               // A terceira linha:
                        
                        if (j%2 == 0) {
                            x = i;
                            chars = 5;
                            terminator = "\\";
                        }
                        else {
                            x = i;
                            chars = 7;
                            terminator = "/";
                        }
                    }
                    if (k == 3) {               // A quarta linha:
                        
                        if (j%2 == 0) {
                            x = i;
                            chars = 7;
                            terminator = "\\";
                        }
                        else {
                            x = i;
                            chars = 5;
                            terminator = "/";
                        }
                    }
                    
                    if (x < 0) a = " ";
                    else if (x == size.x) a = " ";
                    else a = String.format("%c", 'a' + matrix.terrainIndex(x, j));
                    
                    for (int l = 0; l < chars; l ++) System.out.print(a);
                    System.out.print(terminator);
                    
                }
                System.out.println("");
            }
        }
        
        
    }
    
    
}

//          /:::::\
//    _____/:::::::\
//   /@@@@@\:::::::/
//  /@@@@@@@\:::::/
//  \@@@@@@@/,,,,,\
//   \@@@@@/,,,,,,,\
//   /+++++\,,,,,,,/
//  /+++++++\,,,,,/
//  \+++++++/jjjjj\
//   \+++++/jjjjjjj\
//   /'''''\jjjjjjj/
//  /'''''''\jjjjj/
//  \'''''''/UUUUU\
//   \'''''/UUUUUUU\







