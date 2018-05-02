package ija.ija2017.blok;

import ija.ija2017.port.AbstractPort;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;

import java.util.List;


public interface IBlock {
    void calculate();

    boolean canStart();

    void setReady();

    int getNumberOfInputPorts();

    int getNumberOfOutputPorts();

    List<InputPort> getInputPorts();

    List<OutputPort> getOutputPorts();
    }
