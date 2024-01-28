package Data_info;

/**
 * The type Irrigation object.
 */
public class IrrigationObject {
    private String parcelas;
    private int duracoes;
    private String regularidades;

    /**
     * Instantiates a new Irrigation object.
     *
     * @param parcelas      the parcelas
     * @param duracoes      the duracoes
     * @param regularidades the regularidades
     */
    public IrrigationObject(String parcelas, int duracoes, String regularidades) {
        this.parcelas = parcelas;
        this.duracoes = duracoes;
        this.regularidades = regularidades;
    }

    /**
     * Gets parcelas.
     *
     * @return the parcelas
     */
    public String getParcelas() {
        return parcelas;
    }

    /**
     * Sets parcelas.
     *
     * @param parcelas the parcelas
     */
    public void setParcelas(String parcelas) {
        this.parcelas = parcelas;
    }

    /**
     * Gets duracoes.
     *
     * @return the duracoes
     */
    public int getDuracoes() {
        return duracoes;
    }

    /**
     * Sets duracoes.
     *
     * @param duracoes the duracoes
     */
    public void setDuracoes(int duracoes) {
        this.duracoes = duracoes;
    }

    /**
     * Gets regularidades.
     *
     * @return the regularidades
     */
    public String getRegularidades() {
        return regularidades;
    }

    /**
     * Sets regularidades.
     *
     * @param regularidades the regularidades
     */
    public void setRegularidades(String regularidades) {
        this.regularidades = regularidades;
    }

}
