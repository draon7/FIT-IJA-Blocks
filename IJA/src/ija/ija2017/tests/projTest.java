package ija.ija2017.tests;

import ija.ija2017.Data.AbstractData;
import ija.ija2017.Data.DataAttack;
import ija.ija2017.Data.DataFighter;
import ija.ija2017.blok.BlockHealing;
import ija.ija2017.blok.BlockDefense;
import ija.ija2017.port.AbstractPort;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ija.ija2017.blok.IBlock;
import ija.ija2017.scheme.Scheme;

/**
 * Class for testing
 */
public class projTest {
    private IBlock blockDefense;
    private IBlock BlockHealing;
    private Scheme sch1;

    /**
     * Constructor for tests
     */
    public projTest(){
    }

    /**
     * Class for setting up tests
     */
    @Before
    public void SetUp (){
        blockDefense = new BlockDefense();
        BlockHealing = new BlockHealing();
        sch1 = new Scheme("test scheme");
        sch1.addBlock(blockDefense);
        sch1.addBlock(BlockHealing);
    }

    /**
     * Test1, tests types of InputPorts
     */
    @Test
    public void Test01 (){
        Assert.assertEquals("test typu vstupniho portu 0", blockDefense.getInputPorts().get(0).getDataType(), AbstractData.DataType.fighter);
        Assert.assertEquals("test typu vstupniho portu 1", blockDefense.getInputPorts().get(1).getDataType(), AbstractData.DataType.attack);
        Assert.assertEquals("test typu vystupniho portu", blockDefense.getOutputPorts().get(0).getDataType(), AbstractData.DataType.fighter);
    }

    /**
     * Test2, tests OutputPorts
     */
    @Test
    public void Test02 (){
        Assert.assertEquals("test typu vstupniho portu", BlockHealing.getInputPorts().get(0).getDataType(), AbstractData.DataType.fighter);
        Assert.assertEquals("test typu vystupniho portu", BlockHealing.getOutputPorts().get(0).getDataType(), AbstractData.DataType.fighter);
    }

    /**
     * Test3, tests port calculation
     */
    @Test
    public void Test03 (){
        blockDefense.getInputPorts().get(0).setData(new DataFighter(200.0, 10.0, 10.0,10.0));
        blockDefense.getInputPorts().get(1).setData(new DataAttack(100.0));
        blockDefense.calculate();
        Assert.assertEquals(100.0, ((DataFighter)blockDefense.getOutputPorts().get(0).getValue()).Health, 0.1);
    }

    /**
     * Test4, tests connection of blocks and calculation
     */
    @Test
    public void Test04 () {
        sch1.connectPorts(blockDefense.getInputPorts().get(0), BlockHealing.getOutputPorts().get(0));
        BlockHealing.getInputPorts().get(0).setData(new DataFighter(200.0,10.0,10.0,10.0));
        blockDefense.getInputPorts().get(1).setData(new DataAttack(100.0));
        Assert.assertTrue("calculate order", sch1.calculateOrder());
        sch1.calculate();
        Assert.assertEquals(140.0, ((DataFighter)blockDefense.getOutputPorts().get(0).getValue()).Health, 0.1);
    }
}
