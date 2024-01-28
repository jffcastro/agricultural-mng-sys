package Data_info;

import java.sql.Time;
import java.util.ArrayList;

/**
 * The type Irrigation system.
 */
public class IrrigationSystem {

    private Time[] horas;

    private ArrayList<IrrigationObject> irrigationObjectList = new ArrayList<>();

    /**
     * Instantiates a new Irrigation system.
     *
     * @param horas the horas
     */
    public IrrigationSystem(Time[] horas) {
        this.horas = horas;
    }

    /**
     * Instantiates a new Irrigation system.
     *
     * @param horas        the horas
     * @param parcela      the parcela
     * @param duracao      the duracao
     * @param regularidade the regularidade
     */
    public IrrigationSystem(Time[] horas, String parcela, int duracao, String regularidade) {
        this.horas = horas;
        irrigationObjectList.add(new IrrigationObject(parcela,duracao,regularidade));
    }

    /**
     * Get horas time [ ].
     *
     * @return the time [ ]
     */
    public Time[] getHoras() {
        return horas;
    }

    /**
     * Sets horas.
     *
     * @param horas the horas
     */
    public void setHoras(Time[] horas) {
        this.horas = horas;
    }

    /**
     * Gets irrigation object list.
     *
     * @return the irrigation object list
     */
    public ArrayList<IrrigationObject> getIrrigationObjectList() {
        return irrigationObjectList;
    }

    /**
     * Sets irrigation object list.
     *
     * @param irrigationObjectList the irrigation object list
     */
    public void setIrrigationObjectList(ArrayList<IrrigationObject> irrigationObjectList) {
        this.irrigationObjectList = irrigationObjectList;
    }


}
