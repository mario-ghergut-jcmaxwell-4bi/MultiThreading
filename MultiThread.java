/*
 * Con questo programma voglio illustrare i seguenti concetti:
 * 1. MAIN e' un thread come gli altri e quindi puo' terminare prima che gli altri
 * 2. THREADs vengono eseguiti allo stesso tempo
 * 3. THREADs possono essere interrotti e hanno la possibilita' di interrompersi in modo pulito
 * 4. THREADs possono essere definiti mediante una CLASSE che implementa un INTERFACCIA Runnable
 * 5. THREADs possono essere avviati in modo indipendente da quando sono stati definiti
 * 6. posso passare parametri al THREADs tramite il costruttore della classe Runnable
 */
package multithread;

import java.util.Random;
/**
 *
 * @author Mario Ghergut
 */
import java.util.concurrent.TimeUnit;
import static multithread.TicTacToe.punteggio;

public class MultiThread {
    // "main" e' il THREAD principale da cui vengono creati e avviati tutti gli altri THREADs
    // i vari THREADs poi evolvono indipendentemente dal "main" che puo' eventualmente terminare prima degli altri
    public static void main(String[] args) {
        System.out.println("Main Thread iniziata...");
        long start = System.currentTimeMillis();
        
        
        // Posso creare un THREAD e avviarlo immediatamente
        Thread tic = new Thread (new TicTacToe("TIC"));
        tic.start();
        
       
       // Posso creare un 2ndo THREAD e farlo iniziare qualche tempo dopo...
        Thread tac = new Thread(new TicTacToe("TAC"));
        tac.start();
        
        Thread toe = new Thread (new TicTacToe("TOE"));
        toe.start();
                
        
        try {
            
            tic.join();
            tac.join();
            toe.join();
        } catch (InterruptedException e) {}
        
        long end = System.currentTimeMillis();
        System.out.println("il punteggio e"+ punteggio);
        System.out.println("Main Thread completata! tempo di esecuzione: " + (end - start) + "ms");
    }
    
}

// Ci sono vari (troppi) metodi per creare un THREAD in Java questo e' il mio preferito per i vantaggi che offre
// +1 si puo estendere da un altra classe
// +1 si possono passare parametri (usando il Costruttore)
// +1 si puo' controllare quando un THREAD inizia indipendentemente da quando e' stato creato
class TicTacToe implements Runnable {
        public  static int punteggio = 0;
        public  static boolean trovato=false;
    
    // non essesndo "static" c'e' una copia delle seguenti variabili per ogni THREAD 
    private final String t;
    private String msg;

    // Costruttore, possiamo usare il costruttore per passare dei parametri al THREAD
    public TicTacToe (String s) {
        this.t = s;
    }
    
    @Override // Annotazione per il compilatore
    // se facessimo un overloading invece di un override il copilatore ci segnalerebbe l'errore
    // per approfondimenti http://lancill.blogspot.it/2012/11/annotations-override.html
    public void run() {

        for (int i = 10; i > 0; i--) {
            if(t.equals("TAC"))
            {
                trovato = false;
            }
            msg = "<" + t + "> ";
            //System.out.print(msg);
            
            try {
                 Random random = new Random();// generatore di numeri casuali che sara utile per sleep
                 int j = 100;
                 int n = 300-j;
                 int k = random.nextInt(n)+j;
                TimeUnit.MILLISECONDS.sleep(k);
            } catch (InterruptedException e) {
                System.out.println("THREAD " + t + " e' stata interrotta! bye bye...");
                return; //me ne vado = termino il THREAD
            }
            if ("TOE".equals(t) && trovato == true)//confronto per vedere se TOE esce subito dopo TAC
            {

                punteggio++; 
            }   
            else
            {
                trovato= false;
            }
            msg += t + ": " + i;
            System.out.println(msg);
            
         
        }
    }
}
