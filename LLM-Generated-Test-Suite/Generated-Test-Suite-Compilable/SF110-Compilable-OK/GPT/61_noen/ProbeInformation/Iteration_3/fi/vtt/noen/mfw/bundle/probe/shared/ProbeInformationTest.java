package fi.vtt.noen.mfw.bundle.probe.shared;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import fi.vtt.noen.mfw.bundle.probe.shared.ProbeInformation;
import fi.vtt.noen.mfw.bundle.common.Const;

public class ProbeInformationTest {

    private ProbeInformation probeInfo;
    private final String targetName = "Target1";
    private final String targetType = "TypeA";
    private final String bmClass = "ClassX";
    private final String bmName = "NameY";
    private final String bmDescription = "DescriptionZ";
    private final String probeName = "ProbeDesc";
    private final int precision = 5;
    private final String xmlRpcUrl = "http://example.com";

    @Before
    public void setUp() {
        probeInfo = new ProbeInformation(targetName, targetType, bmClass, bmName, bmDescription, probeName, precision, xmlRpcUrl);
    }

    @Test
    public void testGetTargetName() {
        assertEquals(targetName, probeInfo.getTargetName());
    }

    @Test
    public void testGetTargetType() {
        assertEquals(targetType, probeInfo.getTargetType());
    }

    @Test
    public void testGetBmClass() {
        assertEquals(bmClass, probeInfo.getBmClass());
    }

    @Test
    public void testGetBmName() {
        assertEquals(bmName, probeInfo.getBmName());
    }

    @Test
    public void testGetBmDescription() {
        assertEquals(bmDescription, probeInfo.getBmDescription());
    }

    @Test
    public void testGetProbeName() {
        assertEquals(probeName, probeInfo.getProbeName());
    }

    @Test
    public void testGetPrecision() {
        assertEquals(precision, probeInfo.getPrecision());
    }

    @Test
    public void testGetXmlRpcUrl() {
        assertEquals(xmlRpcUrl, probeInfo.getXmlRpcUrl());
    }

    @Test
    public void testGetMeasureURI() {
        String expectedURI = Const.createMeasureURI(targetType, targetName, bmClass, bmName);
        assertEquals(expectedURI, probeInfo.getMeasureURI());
    }

    @Test
    public void testToString() {
        String expectedString = "ProbeInformation{targetName='" + targetName + '\'' +
                ", targetType='" + targetType + '\'' +
                ", bmClass='" + bmClass + '\'' +
                ", bmName='" + bmName + '\'' +
                ", bmDescription='" + bmDescription + '\'' +
                ", probeDescription='" + probeName + '\'' +
                ", precision=" + precision + '}';
        assertEquals(expectedString, probeInfo.toString());
    }

    @Test
    public void testEquals() {
        ProbeInformation sameProbeInfo = new ProbeInformation(targetName, targetType, bmClass, bmName, bmDescription, probeName, precision, xmlRpcUrl);
        ProbeInformation differentProbeInfo = new ProbeInformation("Different", targetType, bmClass, bmName, bmDescription, probeName, precision, xmlRpcUrl);

        assertTrue(probeInfo.equals(sameProbeInfo));
        assertFalse(probeInfo.equals(differentProbeInfo));
        assertFalse(probeInfo.equals(null));
        assertFalse(probeInfo.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        ProbeInformation sameProbeInfo = new ProbeInformation(targetName, targetType, bmClass, bmName, bmDescription, probeName, precision, xmlRpcUrl);
        assertEquals(probeInfo.hashCode(), sameProbeInfo.hashCode());
    }
}