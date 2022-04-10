import java.util.Comparator;

import javax.swing.JOptionPane;

import Clases.ColaPrioridad;
import Clases.Conjunto;
import Clases.Operador;

public class App {

    private static long milisInicio;
    private static int nodosExplorados;

    private static final Estado puzzles[] = {
            // Los comentario indican el indice y la profundidad de la soluci�n.
            new Estado(1, 2, 3, 7, 8, 4, 5, 6, 9, 10, 0, 11, 12, 13, 14, 15), // 0: 10
            new Estado(1, 2, 3, 7, 8, 4, 5, 6, 12, 9, 10, 11, 13, 14, 15, 0), // 1: 12
            new Estado(1, 2, 3, 7, 8, 4, 5, 6, 0, 10, 11, 15, 12, 13, 9, 14), // 2: 18
            new Estado(1, 2, 3, 7, 8, 4, 5, 6, 10, 11, 15, 14, 9, 12, 13, 0), // 3: 20
            new Estado(1, 2, 3, 7, 8, 4, 5, 6, 13, 12, 0, 9, 14, 15, 11, 10), // 4: 22
            new Estado(1, 2, 3, 7, 8, 4, 5, 6, 11, 15, 0, 14, 10, 12, 13, 9), // 5: 28
            new Estado(1, 2, 3, 7, 8, 4, 5, 6, 11, 15, 14, 13, 10, 0, 9, 12), // 6: 30
            new Estado(1, 2, 3, 7, 8, 4, 5, 6, 0, 14, 13, 12, 15, 11, 10, 9), // 7: 32
            new Estado(1, 2, 3, 7, 8, 4, 5, 6, 15, 14, 9, 13, 11, 10, 12, 0), // 8: 36
            new Estado(1, 2, 3, 7, 15, 8, 4, 5, 14, 9, 6, 13, 11, 0, 10, 12), // 9: 44
            new Estado(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 14, 0), // 10: Imposible (it's possible, in 44
                                                                              // mov)
            new Estado(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0), // 11: ???
            new Estado(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 14), // 11: Impossible.
    };

    private static Estado busquedaHeuristicaManhattan(Estado inicial) {
        Conjunto<Estado> db = new Conjunto<Estado>();
        ColaPrioridad<Estado> queue = new ColaPrioridad<Estado>(
                new Comparator<Estado>() {
                    @Override
                    public int compare(Estado arg0, Estado arg1) {
                        return Estado.manhattan.compare(arg0, arg1);
                    }
                });
        queue.add(inicial);
        db.add(inicial);
        try {
            while (!queue.isEmpty()) {
                Estado e = queue.poll();
                nodosExplorados++;
                if (e.esObjetivo()) {
                    System.out.print("Soluci�n\n" + e.solucion());
                    return e;
                } /* if */
                for (Operador<Estado> op : Estado.moves) {
                    Estado n = op.run(e);
                    /* if profundidad is lesser than limite */
                    if ((n != null) && (db.get(n) == null)) {
                        queue.add(n);
                        db.add(n);
                    } /* if */
                } /* for */
            } /* while */
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } /* try/catch */
        return null; /* we have explored everything and none is esObjetivo() */
    }

    private static void printSolucion(String algoritmo, Estado e) {
        long milisTotal = System.currentTimeMillis() - milisInicio;
        System.out.println("\n" + algoritmo);

        if (e == null) {
            System.out.println("No se ha encontrado la solución.");
        } else {
            System.out.println(algoritmo);
            System.out.println("     Profundidad: " + e.getProfundidad());
        }

        System.out.println("Nodos explorados: " + nodosExplorados);
        System.out.println("    Milisegundos: " + milisTotal);
    }

    public static void main(String[] args) {

        int[] iEst = new int[16];
        Estado ini = null;
        String sEst = JOptionPane.showInputDialog("Ingrese el estado separado por comas").replace(" ", "");
        int i = 0;
        System.out.println(sEst);
        for (String c : sEst.split(",")) {
            try {
                iEst[i] = Integer.parseInt(c);
                i++;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Ingrese una lista con valores enteros");
            }
        }
        ini = new Estado(iEst);
        Estado inicial = ini;
        busquedaHeuristicaManhattan(inicial);

        String algoritmo = "Busqueda con heuristica de distancias Manhattan";

        milisInicio = System.currentTimeMillis();

        printSolucion(algoritmo, inicial);
    }
}
