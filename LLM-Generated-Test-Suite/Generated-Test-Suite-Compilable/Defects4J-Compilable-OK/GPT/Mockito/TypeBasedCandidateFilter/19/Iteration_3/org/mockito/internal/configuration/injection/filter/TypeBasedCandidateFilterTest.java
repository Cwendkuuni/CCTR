package org.mockito.internal.configuration.injection.filter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.configuration.injection.filter.MockCandidateFilter;
import org.mockito.internal.configuration.injection.filter.OngoingInjecter;
import org.mockito.internal.configuration.injection.filter.TypeBasedCandidateFilter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TypeBasedCandidateFilterTest {

    private TypeBasedCandidateFilter typeBasedCandidateFilter;
    private MockCandidateFilter nextMockCandidateFilter;
    private Field testField;
    private Object fieldInstance;

    @Before
    public void setUp() throws NoSuchFieldException {
        nextMockCandidateFilter = mock(MockCandidateFilter.class);
        typeBasedCandidateFilter = new TypeBasedCandidateFilter(nextMockCandidateFilter);
        testField = TestClass.class.getDeclaredField("testField");
        fieldInstance = new Object();
    }

    @Test
    public void testFilterCandidateWithMatchingMocks() {
        List<Object> mocks = new ArrayList<>();
        mocks.add(new TestClass());
        mocks.add(new AnotherTestClass());

        OngoingInjecter expectedInjecter = mock(OngoingInjecter.class);
        when(nextMockCandidateFilter.filterCandidate(anyList(), eq(testField), eq(fieldInstance)))
                .thenReturn(expectedInjecter);

        OngoingInjecter result = typeBasedCandidateFilter.filterCandidate(mocks, testField, fieldInstance);

        assertNotNull(result);
        assertEquals(expectedInjecter, result);
        verify(nextMockCandidateFilter).filterCandidate(anyList(), eq(testField), eq(fieldInstance));
    }

    @Test
    public void testFilterCandidateWithNoMatchingMocks() {
        List<Object> mocks = new ArrayList<>();
        mocks.add(new UnrelatedClass());

        OngoingInjecter expectedInjecter = mock(OngoingInjecter.class);
        when(nextMockCandidateFilter.filterCandidate(anyList(), eq(testField), eq(fieldInstance)))
                .thenReturn(expectedInjecter);

        OngoingInjecter result = typeBasedCandidateFilter.filterCandidate(mocks, testField, fieldInstance);

        assertNotNull(result);
        assertEquals(expectedInjecter, result);
        verify(nextMockCandidateFilter).filterCandidate(anyList(), eq(testField), eq(fieldInstance));
    }

    @Test
    public void testFilterCandidateWithEmptyMocks() {
        List<Object> mocks = new ArrayList<>();

        OngoingInjecter expectedInjecter = mock(OngoingInjecter.class);
        when(nextMockCandidateFilter.filterCandidate(anyList(), eq(testField), eq(fieldInstance)))
                .thenReturn(expectedInjecter);

        OngoingInjecter result = typeBasedCandidateFilter.filterCandidate(mocks, testField, fieldInstance);

        assertNotNull(result);
        assertEquals(expectedInjecter, result);
        verify(nextMockCandidateFilter).filterCandidate(anyList(), eq(testField), eq(fieldInstance));
    }

    private static class TestClass {
        private TestClass testField;
    }

    private static class AnotherTestClass extends TestClass {
    }

    private static class UnrelatedClass {
    }
}