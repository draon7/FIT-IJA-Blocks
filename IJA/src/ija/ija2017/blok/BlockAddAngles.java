package ija.ija2017.blok;

import ija.ija2017.port.AbstractPort;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;


public class BlockAddAngles extends AbstractBlock {
    private InputPort inputAngle1 = new InputPort(AbstractPort.DataType.angle, null);
    private InputPort inputAngle2 = new InputPort(AbstractPort.DataType.angle, null);
    private OutputPort outputAngle = new OutputPort(AbstractPort.DataType.angle, null);

    public BlockAddAngles() {
        inputPorts.add(inputAngle1);
        inputPorts.add(inputAngle2);
        outputPorts.add(outputAngle);
    }

    public void calculate(){
        outputAngle.setValue(inputAngle1.getValue()+inputAngle2.getValue());
    }
    public boolean canStart(){
        return (inputAngle1.isReady() && inputAngle2.isReady());
    }

    public void setReady() {
        outputAngle.setReady();
    }
}
