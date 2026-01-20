package EntregaParcial;

import java.util.InputMismatchException;
import java.util.Scanner;


public class escacs {
    String nomJugador1;
    String nomJugador2;
    int partidasjugades = 0;
    public static final String RESET = "\u001B[0m";
    public static final String BOLD_WHITE = "\u001B[1;37m";
    public static final String BOLD_BROWN = "\u001B[1;34m"; 

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
            System.out.println("Jugador 2 comença amb " + BOLD_BROWN + "NEGRES" + RESET);
        } else {
            System.out.println("Jugador 1 comença amb " + BOLD_BROWN + "NEGRES" + RESET);
            System.out.println("Jugador 2 comença amb " + BOLD_WHITE + "BLANQUES" + RESET);
        }
    }
}