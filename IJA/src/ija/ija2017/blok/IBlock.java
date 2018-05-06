package ija.ija2017.blok;

import ija.ija2017.port.AbstractPort;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;

import java.util.List;

/**
 * The IBlock interface implements interface for calculation with blocks
 */
public interface IBlock {

    /**
     * This method is used to calculate data from inputports
     * to outputports, block must be ready othewwise undefined.
     */
    void calculate();

    /**
     * This method decides if all input ports have data ready
     * to calculate.
     * @return true if all ports are ready otherwise false
     */
    boolean canStart();

    /**
     * This method sets all outputports ready.
     */
    void setReady();

    /**
     * This method get number of InputPorts in Block.
     * @return number of InputPorts
     */
    int getNumberOfInputPorts();

    /**
     * This method returns number of OutputPorts in Block.
     * @return number of outputPorts
     */
    int getNumberOfOutputPorts();

    /**
     * This method gets list of inputports in Block.
     * @return List of InputPorts.
     */
    List<InputPort> getInputPorts();


    /**
     * This method gets list of outputports in Block.
     * @return List of OutputPorts.
     */
    List<OutputPort> getOutputPorts();
    }
