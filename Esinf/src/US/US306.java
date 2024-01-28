package US;

import Data_info.GraphStore;
import Data_info.IrrigationObject;
import Data_info.IrrigationSystem;

import java.sql.Time;
import java.util.Date;

/**
 * The type Us 306.
 */
public class US306 {
    /**
     * Rega.
     */
    public static void rega() {
        Date currentDate = new Date(System.currentTimeMillis());
        Time currentTime = new Time(currentDate.getHours(), currentDate.getMinutes(), currentDate.getSeconds());
        boolean diaPar = false;
        Time[] horas = GraphStore.irrigationSystem.getHoras();
        boolean existeRegaAtiva = false;
        if (currentDate.getDate() % 2 == 0) {
            diaPar = true;
        }

        for (IrrigationObject io : GraphStore.irrigationSystem.getIrrigationObjectList()) {
            for (int x = 0; x < horas.length; x++) {
                Time timeComDuracao = new Time(horas[x].getTime() + (long) io.getDuracoes() * 60000);
                if (currentTime.after(horas[x]) && currentTime.before(timeComDuracao)) {
                    Time tempoQueFalta = new Time(timeComDuracao.getTime() - currentTime.getTime());
                    if (diaPar && (io.getRegularidades().equals("p") || io.getRegularidades().equals("t"))) {
                        existeRegaAtiva = true;
                        System.out.printf("A parcela %s esta a ser regada. Faltam %d minutos para terminar.%n", io.getParcelas(), tempoQueFalta.getTime() / 60000);
                    } else if (!diaPar && (io.getRegularidades().equals("i") || io.getRegularidades().equals("t"))) {
                        existeRegaAtiva = true;
                        System.out.printf("A parcela %s esta a ser regada. Faltam %d minutos para terminar.%n", io.getParcelas(), tempoQueFalta.getTime() / 60000);
                    }
                }
            }
        }
        if (!existeRegaAtiva) {
            System.out.println("Nenhuma parcela esta a ser regada.");
        }

    }
}
