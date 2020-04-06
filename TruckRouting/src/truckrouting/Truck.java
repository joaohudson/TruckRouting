/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truckrouting;

/**
 *
 * @author João Hudson
 */
public class Truck {
    
    private final int id;
    private final int maxLoad;
    private int load;
    
    /**
     * Cria um caminhão.
     * @param id o identificador úncio deste
     * caminhão.
     * @param maxLoad O número máximo de pontos
     * adjacentes a este.
     */
    public Truck(int id, int maxLoad)
    {
        this.id = id;
        this.maxLoad = maxLoad;
        this.load = maxLoad;
    }
    
    /**
     * Obtém o identificador único deste
     * caminhão.
     * @return O identificador único deste
     * caminhão. 
     */
    public int getId()
    {
        return id;
    }
    
    /**
     * Obtém à capacidade máxima de pontos
     * adjacentes deste ponto.
     * @return À capacidade máxima de pontos
     * adjacentes deste ponto.
     */
    public int getMaxLoad()
    {
        return maxLoad;
    }
    
    public int getLoad()
    {
        return load;
    }
    
    public void sub(int value)
    {
        load -= value;
    }
    
    public void clear()
    {
        load = 0;
    }
}
