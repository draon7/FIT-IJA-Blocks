package ija.ija2017.tests;

import ija.ija2017.blok.BlockSquareSize;
import ija.ija2017.blok.BlockTriangleSurface;
import ija.ija2017.port.AbstractPort;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ija.ija2017.blok.IBlock;
import ija.ija2017.scheme.Scheme;

public class projTest {
    private IBlock bl1;
    private IBlock bl2;
    private Scheme sch1;

    public projTest(){
    }

    @Before
    public void SetUp (){
        bl1 = new BlockTriangleSurface();
        bl2 = new BlockSquareSize();
        sch1 = new Scheme("test scheme");
        sch1.addBlock(bl1);
        sch1.addBlock(bl2);
    }

    @Test
    public void Test01 (){
        Assert.assertEquals("test typu vstupniho portu 0", bl1.getInputPortDataType(0), AbstractPort.DataType.size);
        Assert.assertEquals("test typu vstupniho portu 1", bl1.getInputPortDataType(1), AbstractPort.DataType.size);
        Assert.assertEquals("test typu vstupniho portu 2", bl1.getInputPortDataType(2), AbstractPort.DataType.size);
        Assert.assertEquals("test typu vystupniho portu", bl1.getOutputPortDataType(0), AbstractPort.DataType.surface);
    }

    @Test
    public void Test02 (){
        Assert.assertEquals("test typu vstupniho portu", bl2.getInputPortDataType(0), AbstractPort.DataType.surface);
        Assert.assertEquals("test typu vystupniho portu", bl2.getOutputPortDataType(0), AbstractPort.DataType.size);
    }

    @Test
    public void Test03 (){
        bl1.setInputPortData(0,3.0);
        bl1.setInputPortData(1,4.0);
        bl1.setInputPortData(2,5.0);
        bl1.calculate();
        Assert.assertEquals(6.0, bl1.getOutputPortValue(0), 0.1);
    }

    @Test
    public void Test04 () {
        sch1.connectPorts(bl1, 0,bl2, 0);
        bl2.setInputPortData(0,4);
        bl1.setInputPortData(1,3);
        bl1.setInputPortData(2,4);
        Assert.assertTrue("calculate order", sch1.calculateOrder());
        sch1.calculate();
        Assert.assertEquals(3.0, bl1.getOutputPortValue(0), 0.1);
    }
}
