import java.util.Comparator;

import javax.swing.JOptionPane;

import Clases.ColaPrioridad;
import Clases.Conjunto;
import Clases.Operador;

public class App {

    private static long milisInicio;
    private static int nodosExplorados;

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
                }
                for (Operador<Estado> op : Estado.moves) {
                    Estado n = op.run(e);

                    if ((n != null) && (db.get(n) == null)) {
                        queue.add(n);
                        db.add(n);
                    }
                }
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void printSolucion(String algoritmo, Estado e) {
        long milisTotal = System.currentTimeMillis() - milisInicio;
        System.out.println("\n" + algoritmo);

        if (e == null) {
            System.out.println("No se ha encontrado la solución.");
        } else {
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
        Estado inicial = ini,
                result = null;
        milisInicio = System.currentTimeMillis();
        result = busquedaHeuristicaManhattan(inicial);

        String algoritmo = "Busqueda con heuristica de distancias Manhattan";

        printSolucion(algoritmo, result);
    }
}
