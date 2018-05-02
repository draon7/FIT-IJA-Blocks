package ija.ija2017.blok;

import ija.ija2017.port.AbstractPort;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;

public class BlockRectangleSurface extends AbstractBlock {
    private InputPort inputSize1 = new InputPort(AbstractPort.DataType.size, null);
    private InputPort inputSize2 = new InputPort(AbstractPort.DataType.size, null);
    private OutputPort outputSurface = new OutputPort(AbstractPort.DataType.surface, null);

    public BlockRectangleSurface() {
        inputPorts.add(inputSize1);
        inputPorts.add(inputSize2);
        outputPorts.add(outputSurface);
    }

    public void calculate(){
        outputSurface.setValue(inputSize1.getValue()*inputSize2.getValue());
    }

    public boolean canStart() {
        return (inputSize1.isReady() && inputSize2.isReady());
    }

    public void setReady() {
        outputSurface.setReady();
    }


}
