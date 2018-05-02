package ija.ija2017.blok;

import ija.ija2017.port.AbstractPort;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;

import static java.lang.Math.sqrt;

public class BlockTriangleSurface extends AbstractBlock {

    private InputPort inputSize1 = new InputPort(AbstractPort.DataType.size, null);
    private InputPort inputSize2 = new InputPort(AbstractPort.DataType.size, null);
    private InputPort inputSize3 = new InputPort(AbstractPort.DataType.size, null);
    private OutputPort outputSurface = new OutputPort(AbstractPort.DataType.surface, null);

    public BlockTriangleSurface() {
        this.inputPorts.add(inputSize1);
        this.inputPorts.add(inputSize2);
        this.inputPorts.add(inputSize3);
        this.outputPorts.add(outputSurface);
    }

    public void calculate(){
        double s = 0.5 * (inputSize1.getValue() + inputSize2.getValue() + inputSize3.getValue());
        outputSurface.setValue(sqrt(s*(s-inputSize1.getValue())*(s-inputSize2.getValue())*(s-inputSize3.getValue())));
    }


    public boolean canStart() {
        return (inputSize1.isReady() && inputSize2.isReady() && inputSize3.isReady());
    }

    public void setReady() {
        outputSurface.setReady();
    }

}
