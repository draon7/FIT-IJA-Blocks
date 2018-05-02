package ija.ija2017.blok;

import ija.ija2017.port.AbstractPort;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;

public class BlockAddSizes extends AbstractBlock {
    public InputPort inputSize1 = new InputPort(AbstractPort.DataType.size, null);
    public InputPort inputSize2 = new InputPort( AbstractPort.DataType.size, null);
    public OutputPort outputSize = new OutputPort(AbstractPort.DataType.size, null);

    public BlockAddSizes() {
        inputPorts.add(inputSize1);
        inputPorts.add(inputSize2);
        outputPorts.add(outputSize);
    }

    public void calculate(){
        outputSize.setValue(inputSize1.getValue()+inputSize2.getValue());
    }

    public boolean canStart() {
        return (inputSize1.isReady() && inputSize2.isReady());
    }

    public void setReady() {
        outputSize.setReady();
    }
}
