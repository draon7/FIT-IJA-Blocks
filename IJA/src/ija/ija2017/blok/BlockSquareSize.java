package ija.ija2017.blok;

import ija.ija2017.port.AbstractPort;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;

import static java.lang.Math.sqrt;

public class BlockSquareSize extends AbstractBlock {
    public InputPort inputSurface = new InputPort(AbstractPort.DataType.surface, null);
    public OutputPort outputSize = new OutputPort(AbstractPort.DataType.size, null);

    public BlockSquareSize() {
        inputPorts.add(inputSurface);
        outputPorts.add(outputSize);
    }

    public void calculate(){
        outputSize.setValue(sqrt(inputSurface.getValue()));
    }

    public boolean canStart() {
        return inputSurface.isReady();
    }

    public void setReady() {
        outputSize.setReady();
    }
}
