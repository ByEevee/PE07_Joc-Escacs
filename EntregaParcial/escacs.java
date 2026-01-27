package EntregaParcial;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class escacs {
    String nomJugador1;
    String nomJugador2;
    String jugadorBlanques;
    String jugadorNegres;
    int partidasjugades = 0;
    String guanyadorAnterior = "";
    
    
    char[][] tauler = new char[8][8];
    String tornActual = "BLANQUES";
    boolean partidaEnCurs = false;
    
    
    ArrayList<Character> pecesMenjadesBlanques = new ArrayList<>();
    ArrayList<Character> pecesMenjadesNegres = new ArrayList<>();
    
    
    ArrayList<String> movimentsBlanques = new ArrayList<>();
    ArrayList<String> movimentsNegres = new ArrayList<>();
    
    // Colors per consola
    public static final String RESET = "\u001B[0m";
    public static final String BOLD_WHITE = "\u001B[1;37m";
    public static final String BOLD_BLUE = "\u001B[1;34m";

    public static void main(String[] args) {
        escacs game = new escacs();
        game.main();
    }
    public void main() {
        boolean respostaCorrecta = false;
        do {
            try {
                System.out.println("Benvingut al joc d'escacs!");
            System.out.println("Selecciona una opció:");
            System.out.println("1. Jugar");
            System.out.println("2. Sortir");
            int opcio = llegirInt();
            switch (opcio) {
                case 1:
                    System.out.println("Iniciant partida...");
                    
                    if (partidasjugades==0){
                        demanarNomsJugadors();
                        System.out.println("Els noms dels jugadors han estat registrats.");
                        System.out.println("Escollint els colors dels jugadors...");
                        escollirColor();
                        
                        partidasjugades++;
                        
                    }
                    inicialitzarTauler();
                    imprimirTauler();
                    partidasjugades++;
                    respostaCorrecta = true;
                    
                    break;

                case 2:
                    System.out.println("Sortint del joc.");
                    respostaCorrecta = true;
                    break;

            
                default:
                    System.out.println("Opció no vàlida. Si us plau, selecciona una opció vàlida. Introdueix de nou un numero ENTER en l'interval (1-2):");
                    break;
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Opció no vàlida. Si us plau, selecciona una opció vàlida. Introdueix de nou un numero ENTER en l'interval (1-2):");
            }
            catch (Exception e) {
                System.out.println("Error inesperat. Si us plau, selecciona una opció vàlida. Introdueix de nou un numero ENTER en l'interval (1-2):");
            }
            
        } while (!respostaCorrecta);
        
    }
    public int llegirInt() {
        boolean entradaCorrecta = false;
        int numero = 0;
        do {
            try {
                Scanner sc = new Scanner(System.in);
                numero = sc.nextInt();
                entradaCorrecta = true;
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("Entrada no vàlida. Si us plau, introdueix un nombre enter vàlid.");
            }
            catch (Exception e) {
                System.out.println("Entrada desconeguda. Si us plau, introdueix un nombre enter.");
            }
            

        } while (!entradaCorrecta);
       
        return numero;
    }
    public void demanarNomsJugadors() {
        System.out.println("Introdueix el nom del jugador 1:");
        nomJugador1 = llegirString();
        System.out.println("Introdueix el nom del jugador 2:");
        nomJugador2 = llegirString();
        System.out.println("Els jugadors són:");
        System.out.println("Jugador 1 (blanques): " + nomJugador1);
        System.out.println("Jugador 2 (negres): " + nomJugador2);
    }
    public String llegirString() {
        boolean entradaCorrecta = false;
        String entrada = "";
        do {
            try {
                Scanner sc = new Scanner(System.in);
                entrada = sc.nextLine();
                if (!entrada.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                System.out.println("Entrada no vàlida. Només s'accepten lletres, torna a Introduir el nom siusplau:");
                }
                else{
                entradaCorrecta = true;
                }
            }
            catch (Exception e) {
                System.out.println("Entrada desconeguda. Si us plau, introdueix un nom vàlid:");
            }
            

        } while (!entradaCorrecta);
       
        return entrada;

    }
    public void escollirColor() {
        int ordre = (int)(Math.random() * 2) + 1;

        if (ordre == 1) {
            System.out.println("Jugador 1 comença amb " + BOLD_WHITE + "BLANQUES" + RESET);
            System.out.println("Jugador 2 comença amb " + BOLD_BLUE + "NEGRES" + RESET);
        } else {
            System.out.println("Jugador 1 comença amb " + BOLD_BLUE + "NEGRES" + RESET);
            System.out.println("Jugador 2 comença amb " + BOLD_WHITE + "BLANQUES" + RESET);
        }
    }
    public void inicialitzarTauler() {
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tauler[i][j] = '.';
            }
        }
        // Peces negres (files 0 i 1) - MINÚSCULES
        tauler[0][0] = 't'; tauler[0][7] = 't'; // Torres
        tauler[0][1] = 'c'; tauler[0][6] = 'c'; // Cavalls
        tauler[0][2] = 'a'; tauler[0][5] = 'a'; // Alfils
        tauler[0][3] = 'r'; // Reina (r minúscula)
        tauler[0][4] = 'k'; // Rei
        
        for (int i = 0; i < 8; i++) {
            tauler[1][i] = 'p'; // Peons negres
        }
        
        // Peces blanques (files 6 i 7) - MAJÚSCULES
        for (int i = 0; i < 8; i++) {
            tauler[6][i] = 'P'; // Peons blancs
        }
        
        tauler[7][0] = 'T'; tauler[7][7] = 'T'; // Torres
        tauler[7][1] = 'C'; tauler[7][6] = 'C'; // Cavalls
        tauler[7][2] = 'A'; tauler[7][5] = 'A'; // Alfils
        tauler[7][3] = 'R'; // Reina (R majúscula)
        tauler[7][4] = 'K'; // Rei
        
        // Reiniciar peces menjades i moviments
        pecesMenjadesBlanques.clear();
        pecesMenjadesNegres.clear();
        movimentsBlanques.clear();
        movimentsNegres.clear();
        
        tornActual = "BLANQUES";
        partidaEnCurs = true;
    }
    public void imprimirTauler() {
        System.out.println("\n  a b c d e f g h");
        for (int i = 0; i < 8; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 8; j++) {
                char peca = tauler[i][j];
                if (Character.isUpperCase(peca)) {
                    System.out.print(BOLD_WHITE + peca + RESET + " ");
                } else if (Character.isLowerCase(peca)) {
                    System.out.print(BOLD_BLUE + peca + RESET + " ");
                } else {
                    System.out.print(peca + " ");
                }
            }
            System.out.println();
        }
    }

}