package ija.ija2017.blok;

import ija.ija2017.port.AbstractPort;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;


public interface IBlock {
    void calculate();

    boolean canStart();

    void setReady();

    int getNumberOfInputPorts();

    int getNumberOfOutputPorts();

    AbstractPort.DataType getInputPortDataType(int index);

    AbstractPort.DataType getOutputPortDataType(int index);

    Double getInputPortValue(int index);

    Double getOutputPortValue(int index);

    void setInputPortData (int index, double value);

    InputPort getInputPort(int index);

    OutputPort getOutputPort(int index);

    void connectToInputPort (IBlock block, int inputPortIndex, int outputPortIndex);

    void connectToOutputPort (IBlock block, int outputPortIndex, int inputPortIndex);
}
