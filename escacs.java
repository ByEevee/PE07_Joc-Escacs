

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.text.StyledEditorKit.BoldAction;


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
    public static final String BOLD_GREEN = "\u001B[1;32m";
    public static final String BOLD_BLUE = "\u001B[1;34m";

    public static void main(String[] args) {
        escacs game = new escacs();
        game.main();
    }
    public void main() {
        boolean respostaCorrecta = false;
        do {
            try {
                System.out.println("---------------------Benvingut al joc d'escacs!--------------------------");
                System.out.println();
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
                            
                            
                        }
                        inicialitzarTauler();
                        jugarPartida();
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
    public void jugarPartida() {
        while (partidaEnCurs) {
            imprimirTauler();
            
            String jugadorActual = tornActual.equals("BLANQUES") ? jugadorBlanques : jugadorNegres;
            System.out.println("Torn de " + jugadorActual + " (" + tornActual.toLowerCase() + ").");
            
            String moviment = demanarMoviment();
            
            if (moviment.equalsIgnoreCase("Abandonar")) {
                String guanyador = tornActual.equals("BLANQUES") ? jugadorNegres : jugadorBlanques;
                finalitzarPartida("Abandonament", guanyador);
                break;
            }
            
            if (processarMoviment(moviment)) {
                canviarTorn();
            }
        }
    }
    public void preguntarTornarJugar() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Voleu tornar a jugar? (s/n): ");
        String resposta = sc.nextLine().trim().toLowerCase();
        
        if (resposta.equals("s")) {
            System.out.print("Voleu mantenir els mateixos jugadors? (s/n): "); // Pregunta per si cambien de jugadors per no apagar el programa i tornarlo a obrir
            String mantenir = sc.nextLine().trim().toLowerCase();
            
            if (!mantenir.equals("s")) {
                demanarNomsJugadors();
                partidasjugades = 0;
                guanyadorAnterior = "";
            }
            
            System.out.println("Escollint els colors dels jugadors...");
            escollirColor();
            inicialitzarTauler();
            jugarPartida();
        }
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
        
        try {
            System.out.println("Introdueix el nom del jugador 1:");
            nomJugador1 = llegirString();
            System.out.println("Introdueix el nom del jugador 2:");
            nomJugador2 = llegirString();
            System.out.println("Els noms dels jugadors han estat registrats.");
        } catch (Exception e) {
            System.out.println("Error en llegir els noms dels jugadors. Si us plau, torna-ho a intentar.");
            demanarNomsJugadors(); 
        }
    

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
        if (guanyadorAnterior.isEmpty()) {
            
            int ordre = (int)(Math.random() * 2) + 1;
            if (ordre == 1) {
                jugadorBlanques = nomJugador1;
                jugadorNegres = nomJugador2;
                System.out.println(nomJugador1 + " comença amb " + BOLD_WHITE + "BLANQUES" + RESET);
                System.out.println(nomJugador2 + " comença amb " + BOLD_BLUE + "NEGRES" + RESET);
            } else {
                jugadorBlanques = nomJugador2;
                jugadorNegres = nomJugador1;
                System.out.println(nomJugador2 + " comença amb " + BOLD_WHITE + "BLANQUES" + RESET);
                System.out.println(nomJugador1 + " comença amb " + BOLD_BLUE + "NEGRES" + RESET);
            }
        } else {
            
            jugadorBlanques = guanyadorAnterior;
            jugadorNegres = guanyadorAnterior.equals(nomJugador1) ? nomJugador2 : nomJugador1;
            System.out.println(jugadorBlanques + " (guanyador anterior) comença amb " + BOLD_WHITE + "BLANQUES" + RESET);
            System.out.println(jugadorNegres + " comença amb " + BOLD_BLUE + "NEGRES" + RESET);
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
        tauler[0][3] = 'r'; // Reina 
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
        tauler[7][3] = 'R'; // Reina 
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
        System.out.println(BOLD_GREEN + "\n  a b c d e f g h" + RESET);
        for (int i = 0; i < 8; i++) {
            System.out.print(BOLD_GREEN + (8 - i) + " " + RESET);
            
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
    
    public String validarMoviment(int[] origen, int[] desti) {
        int origenFila = origen[0], origenCol = origen[1];
        int destiFila = desti[0], destiCol = desti[1];
        
        
        char peca = tauler[origenFila][origenCol];
        if (peca == '.') {
            return "No hi ha cap peça a la posició d'origen.";
        }
        
       
        boolean esBlanques = Character.isUpperCase(peca);
        if ((tornActual.equals("BLANQUES") && !esBlanques) || 
            (tornActual.equals("NEGRES") && esBlanques)) {
            return "No pots moure una peça de l'altre jugador.";
        }
        
        
        char pecaDesti = tauler[destiFila][destiCol];
        if (pecaDesti != '.') {
            boolean destiEsBlanques = Character.isUpperCase(pecaDesti);
            if (esBlanques == destiEsBlanques) {
                return "No pots matar una peça del teu propi color.";
            }
        }
        
        
        char tipusPeca = Character.toLowerCase(peca);
        boolean movimentValid = false;
        
        switch (tipusPeca) {
            case 'p':
                movimentValid = esMovimentValidPeo(origen, desti, esBlanques);
                break;
            case 't':
                movimentValid = esMovimentValidTorre(origen, desti);
                break;
            case 'c':
                movimentValid = esMovimentValidCavall(origen, desti);
                break;
            case 'a':
                movimentValid = esMovimentValidAlfil(origen, desti);
                break;
            case 'r':
                movimentValid = esMovimentValidReina(origen, desti);
                break;
            case 'k':
                movimentValid = esMovimentValidRei(origen, desti);
                break;
        }
        
        
        if (!movimentValid) {
            return "Moviment no vàlid per aquesta peça.";
        }
        
        return null; 
    }
    public boolean validarFormatMoviment(String entrada) {
        String[] parts = entrada.split(" ");
        if (parts.length != 2) return false;
        
        for (int i = 0 ; i < parts.length; i++) {
            String part = parts[i];
            if (part.length() != 2){
                return false;
            } 
            char col = part.charAt(0);
            char fila = part.charAt(1);
            if (col < 'a' || col > 'h') {
                return false;
            }
            if (fila < '1' || fila > '8') {
                return false;
            }
        }
        return true;
    }
    public int[] convertirCoordenades(String coord) {
        if (coord.length() != 2) return null;
        
        char col = coord.charAt(0);
        char fila = coord.charAt(1);
        
        if (col < 'a' || col > 'h' || fila < '1' || fila > '8') {
            return null;
        }
        
        int columna = col - 'a';
        int filaIndex = 8 - (fila - '0');
        
        return new int[]{filaIndex, columna};
    }
    public String demanarMoviment() {
        try{
            System.out.println("Introdueix el teu moviment (format: e2 (origen) e4 (desti)) o 'Abandonar' per rendir-te:");
            Scanner sc = new Scanner(System.in);
            String moviment = sc.nextLine();
            return moviment;
        }
        catch (Exception e) {
            System.out.println("Error en llegir el moviment. Si us plau, torna-ho a intentar.");
            return demanarMoviment();
        }
        
    }
    public void canviarTorn() {
        tornActual = tornActual.equals("BLANQUES") ? "NEGRES" : "BLANQUES";
    }
    
    public void mourePeca(int[] origen, int[] desti, String movimentText) {
        int origenFila = origen[0], origenCol = origen[1];
        int destiFila = desti[0], destiCol = desti[1];
        
        char peca = tauler[origenFila][origenCol];
        char pecaCapturada = tauler[destiFila][destiCol];
        
    
        if (pecaCapturada != '.') {
            if (tornActual.equals("BLANQUES")) {
                pecesMenjadesBlanques.add(pecaCapturada);
            } else {
                pecesMenjadesNegres.add(pecaCapturada);
            }
        }
        
    
        tauler[destiFila][destiCol] = peca;
        tauler[origenFila][origenCol] = '.';
        
        
        guardarMoviment(movimentText, tornActual);
    }
    public void guardarMoviment(String moviment, String color) {
        if (color.equals("BLANQUES")) {
            movimentsBlanques.add(moviment);
        } else {
            movimentsNegres.add(moviment);
        }
    }
    public boolean hiHaPecesEntreMig(int[] origen, int[] desti) {
        int origenFila = origen[0], origenCol = origen[1];
        int destiFila = desti[0], destiCol = desti[1];
        
        int dirFila = Integer.compare(destiFila, origenFila);
        int dirCol = Integer.compare(destiCol, origenCol);
        
        int fila = origenFila + dirFila;
        int col = origenCol + dirCol;
        
        while (fila != destiFila || col != destiCol) {
            if (tauler[fila][col] != '.') {
                return true;
            }
            fila += dirFila;
            col += dirCol;
        }
        
        return false;
    }
    public boolean processarMoviment(String moviment) {
        if (!validarFormatMoviment(moviment)) {
            System.out.println("FORMAT NO VÀLID. Utilitza el format: e2 (origen) e4 (desti).");
            return false;
        }
        
        String[] parts = moviment.split(" ");
        int[] origen = convertirCoordenades(parts[0]);
        int[] desti = convertirCoordenades(parts[1]);
        
        if (origen == null || desti == null) {
            System.out.println("COORDENADES NO VÀLIDES.");
            return false;
        }
        
        String error = validarMoviment(origen, desti);
        if (error != null) {
            System.out.println("MOVIMENT NO PERMÈS: " + error);
            return false;
        }
        
        mourePeca(origen, desti, moviment);
        return true;
    }
    
    public boolean esMovimentValidPeo(int[] origen, int[] desti, boolean esBlanques) {
        int origenFila = origen[0], origenCol = origen[1];
        int destiFila = desti[0], destiCol = desti[1];
        
        int direccio = esBlanques ? -1 : 1;
        int filaInicial = esBlanques ? 6 : 1;
       
        if (destiCol == origenCol && destiFila == origenFila + direccio) {
            return tauler[destiFila][destiCol] == '.';
        }
        
        if (destiCol == origenCol && origenFila == filaInicial && 
            destiFila == origenFila + 2 * direccio) {
            return tauler[destiFila][destiCol] == '.' && 
                tauler[origenFila + direccio][destiCol] == '.';
        }
        
        if (Math.abs(destiCol - origenCol) == 1 && destiFila == origenFila + direccio) {
            return tauler[destiFila][destiCol] != '.';
        }
        
        return false;
    }
    public boolean esMovimentValidTorre(int[] origen, int[] desti) {
        int origenFila = origen[0], origenCol = origen[1];
        int destiFila = desti[0], destiCol = desti[1];
        
        if (origenFila != destiFila && origenCol != destiCol) {
            return false;
        }
    
        return !hiHaPecesEntreMig(origen, desti); 
    }
    public boolean esMovimentValidCavall(int[] origen, int[] desti) {
        int origenFila = origen[0], origenCol = origen[1];
        int destiFila = desti[0], destiCol = desti[1];
        
        int difFila = Math.abs(destiFila - origenFila);
        int difCol = Math.abs(destiCol - origenCol);
        
        return (difFila == 2 && difCol == 1) || (difFila == 1 && difCol == 2);
    }
    public boolean esMovimentValidAlfil(int[] origen, int[] desti) {
        int origenFila = origen[0], origenCol = origen[1];
        int destiFila = desti[0], destiCol = desti[1];
        
        if (Math.abs(destiFila - origenFila) != Math.abs(destiCol - origenCol)) {
            return false;
        }
        
        return !hiHaPecesEntreMig(origen, desti);
    }
    public boolean esMovimentValidReina(int[] origen, int[] desti) {
        return esMovimentValidTorre(origen, desti) || esMovimentValidAlfil(origen, desti); //Perque fa el mateix moviments que la torre i l'alfil
        }
    public boolean esMovimentValidRei(int[] origen, int[] desti) {
        int origenFila = origen[0], origenCol = origen[1];
        int destiFila = desti[0], destiCol = desti[1];
        
        int difFila = Math.abs(destiFila - origenFila);
        int difCol = Math.abs(destiCol - origenCol);
        
        return difFila <= 1 && difCol <= 1;
    }


    public void finalitzarPartida(String motiu, String guanyador) {
        partidaEnCurs = false;
        System.out.println("\n=== FI DE LA PARTIDA ===");
        System.out.println("Motiu: " + motiu);
        System.out.println("Guanyador: " + guanyador);
        guanyadorAnterior = guanyador;
        
        mostrarResumPartida();
    }

    public void mostrarResumPartida() {
        System.out.println("\n=== RESUM DE LA PARTIDA ===");
        System.out.println("\nMoviments de " + jugadorBlanques + " (BLANQUES):");
        for (int i = 0; i < movimentsBlanques.size(); i++) {
            System.out.println((i + 1) + ". " + movimentsBlanques.get(i));
        }
        
        System.out.println("\nMoviments de " + jugadorNegres + " (NEGRES):");
        for (int i = 0; i < movimentsNegres.size(); i++) {
            System.out.println((i + 1) + ". " + movimentsNegres.get(i));
        }
        System.out.println();
        preguntarTornarJugar();
    }

}