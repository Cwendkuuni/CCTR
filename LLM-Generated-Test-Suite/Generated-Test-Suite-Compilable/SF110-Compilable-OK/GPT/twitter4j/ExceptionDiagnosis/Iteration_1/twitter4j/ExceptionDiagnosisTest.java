package twitter4j;

import org.junit.Test;
import static org.junit.Assert.*;
import twitter4j.ExceptionDiagnosis;

public class ExceptionDiagnosisTest {

    @Test
    public void testConstructorWithThrowable() {
        Throwable throwable = new Throwable();
        ExceptionDiagnosis diagnosis = new ExceptionDiagnosis(throwable);
        assertNotNull(diagnosis);
    }

    @Test
    public void testConstructorWithThrowableAndInclusionFilter() {
        Throwable throwable = new Throwable();
        String[] inclusionFilter = {"java"};
        ExceptionDiagnosis diagnosis = new ExceptionDiagnosis(throwable, inclusionFilter);
        assertNotNull(diagnosis);
    }

    @Test
    public void testGetStackLineHash() {
        Throwable throwable = new Throwable();
        ExceptionDiagnosis diagnosis = new ExceptionDiagnosis(throwable);
        assertEquals(0, diagnosis.getStackLineHash());
    }

    @Test
    public void testGetLineNumberHash() {
        Throwable throwable = new Throwable();
        ExceptionDiagnosis diagnosis = new ExceptionDiagnosis(throwable);
        assertEquals(0, diagnosis.getLineNumberHash());
    }

    @Test
    public void testAsHexString() {
        Throwable throwable = new Throwable();
        ExceptionDiagnosis diagnosis = new ExceptionDiagnosis(throwable);
        assertNotNull(diagnosis.asHexString());
    }

    @Test
    public void testToHexString() {
        Throwable throwable = new Throwable();
        ExceptionDiagnosis diagnosis = new ExceptionDiagnosis(throwable);
        String hexString = diagnosis.asHexString();
        assertTrue(hexString.matches("[0-9a-f]{8}-[0-9a-f]{8}"));
    }

    @Test
    public void testEquals() {
        Throwable throwable1 = new Throwable();
        Throwable throwable2 = new Throwable();
        ExceptionDiagnosis diagnosis1 = new ExceptionDiagnosis(throwable1);
        ExceptionDiagnosis diagnosis2 = new ExceptionDiagnosis(throwable2);
        assertTrue(diagnosis1.equals(diagnosis2));
    }

    @Test
    public void testNotEquals() {
        Throwable throwable1 = new Throwable();
        Throwable throwable2 = new Throwable("Different");
        ExceptionDiagnosis diagnosis1 = new ExceptionDiagnosis(throwable1);
        ExceptionDiagnosis diagnosis2 = new ExceptionDiagnosis(throwable2);
        assertFalse(diagnosis1.equals(diagnosis2));
    }

    @Test
    public void testHashCode() {
        Throwable throwable = new Throwable();
        ExceptionDiagnosis diagnosis = new ExceptionDiagnosis(throwable);
        int expectedHashCode = 31 * diagnosis.getStackLineHash() + diagnosis.getLineNumberHash();
        assertEquals(expectedHashCode, diagnosis.hashCode());
    }

    @Test
    public void testToString() {
        Throwable throwable = new Throwable();
        ExceptionDiagnosis diagnosis = new ExceptionDiagnosis(throwable);
        String expectedString = "ExceptionDiagnosis{stackLineHash=" + diagnosis.getStackLineHash() + ", lineNumberHash=" + diagnosis.getLineNumberHash() + '}';
        assertEquals(expectedString, diagnosis.toString());
    }
}