package multithread;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class MultiThread {
    public static void main(String[] args) {
        
        System.out.println("Main Thread iniziata..."); // output  che ci avvia che il programma è avviato
        
        long start = System.currentTimeMillis(); // ottiene il tempo di avvio
        
        Monitor moniT = new Monitor(); //monitor per controllare l'accesso alle risorse condivise
        
        Thread tic = new Thread (new TicTacToe("TIC", moniT));   // creazione 1° treads
        
        Thread tac = new Thread(new TicTacToe("TAC", moniT)); // creazione 2° treads
        
        Thread toe = new Thread(new TicTacToe("TOE", moniT)); // creazione 3° treads
        
        toe.start(); // inizio 1 thread 
        
        tac.start(); // inizio 2 thread 
        
        tic.start(); // inizio 3 thread 
            
        try{
            tic.join(); // Attendo che l'esecuzione di ogni thread finisca. Per poi proseguire
            tac.join();
            toe.join();
        }catch (InterruptedException e) {}
        
        
        long end = System.currentTimeMillis(); // ottengo il tempo al momento della conclusione del programma
        System.out.println("Main Thread completata! tempo di esecuzione: " + (end - start) + "ms"); // esegue operazioni per sapere quanto ci ha messo ad eseguire tutto il codice
        System.out.println("Punteggio: " + moniT.getPunteggio()); // Output del punteggio
    }
}

class TicTacToe implements Runnable {
    private String t;
    public String msg;
    private Monitor m;
    // Costruttore, possiamo usare il costruttore per passare dei parametri al THREAD
    public TicTacToe (String s, Monitor m) {
        this.m = m;
        this.t = s;
    }    
    @Override // Annotazione per il compilatore
    // se facessimo un overloading invece di un override il copilatore ci segnalerebbe l'errore
    public void run() {
        for (int i = 10; i > 0; i--) {
            msg = "<" + t + "> " + t + ": " + i;
            m.Scrivi(t, msg);
        }
    }
}

//classe monitor per la gestione di risorse condivise tra threads
class Monitor{
    private String ultimoThread = ""; //variabile contenente il thread eseguito precedentemente
    private int punteggio; 
    private final Random rand = new Random(); 
    private int randoM;
    
    //Uso Synchronized in tal modo da avere un accesso ordinato da parte dei Thread
    public synchronized void Scrivi(String t, String msg){
            try {
                randoM = rand.nextInt(300) + 100;
                TimeUnit.MILLISECONDS.sleep(randoM);
            } catch (InterruptedException e) {
                System.out.println(e);
                return; 
            }
            if(t.equals("TOE") && ultimoThread.equals("TAC")) //condizione 
            {
                punteggio ++;
                msg += "\t" + "<--- Eccomi: " + punteggio; //aggiungo un segna punti, per verificare più velocemente il codice
            }          
            ultimoThread = t; // assegno all'ultimoThread il valore ottenuto dal Thread
            System.out.println(msg);  // visualizzo messaggio in output
    }   
    public int getPunteggio() // restituisce il punteggio 
    {
        return punteggio;
    }
}
