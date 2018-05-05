package ija.ija2017.blok;

import ija.ija2017.Data.AbstractData;
import ija.ija2017.port.InputPort;
import ija.ija2017.port.OutputPort;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractBlock implements IBlock{
    protected List<InputPort> inputPorts = new ArrayList<>();
    protected List<OutputPort> outputPorts = new ArrayList<>();

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

    public abstract void createUI(Pane pane);
}
