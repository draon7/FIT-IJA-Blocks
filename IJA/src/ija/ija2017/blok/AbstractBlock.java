package ija.ija2017.blok;

import ija.ija2017.port.AbstractPort;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractBlock implements IBlock{
    private List<InputPort> inputPorts = new ArrayList<>();
    private List<OutputPort> outputPorts = new ArrayList<>();

    public int getNumberOfInputPorts() {
        return inputPorts.size();
    }

    public int getNumberOfOutputPorts() {
        return outputPorts.size();
    }

    public List<InputPort> getInputPorts() {
        return inputPorts;
    }

    public List<OutputPort> getOutputPorts() {
        return outputPorts;
    }

    public void setInputPortData(InputPort inputPort, double value) {
        inputPort.setValue(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractBlock)) return false;
        AbstractBlock that = (AbstractBlock) o;
        return Objects.equals(inputPorts, that.inputPorts) &&
                Objects.equals(outputPorts, that.outputPorts);
    }

    @Override
    public int hashCode() {

        return Objects.hash(inputPorts, outputPorts);
    }
}
