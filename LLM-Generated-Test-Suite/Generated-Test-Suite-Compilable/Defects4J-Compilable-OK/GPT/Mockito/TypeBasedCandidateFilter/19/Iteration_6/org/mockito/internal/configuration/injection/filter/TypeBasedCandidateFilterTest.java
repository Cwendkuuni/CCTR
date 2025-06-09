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
    public void testFilterCandidateWithMatchingType() {
        List<Object> mocks = new ArrayList<>();
        mocks.add(new TestClass());
        mocks.add(new AnotherClass());

        OngoingInjecter ongoingInjecterMock = mock(OngoingInjecter.class);
        when(nextMockCandidateFilter.filterCandidate(anyCollection(), any(Field.class), any())).thenReturn(ongoingInjecterMock);

        OngoingInjecter result = typeBasedCandidateFilter.filterCandidate(mocks, testField, fieldInstance);

        assertNotNull(result);
        verify(nextMockCandidateFilter).filterCandidate(argThat(argument -> argument.size() == 1 && argument.contains(mocks.get(0))), eq(testField), eq(fieldInstance));
    }

    @Test
    public void testFilterCandidateWithNoMatchingType() {
        List<Object> mocks = new ArrayList<>();
        mocks.add(new AnotherClass());

        OngoingInjecter ongoingInjecterMock = mock(OngoingInjecter.class);
        when(nextMockCandidateFilter.filterCandidate(anyCollection(), any(Field.class), any())).thenReturn(ongoingInjecterMock);

        OngoingInjecter result = typeBasedCandidateFilter.filterCandidate(mocks, testField, fieldInstance);

        assertNotNull(result);
        verify(nextMockCandidateFilter).filterCandidate(argThat(Collection::isEmpty), eq(testField), eq(fieldInstance));
    }

    @Test
    public void testFilterCandidateWithEmptyMocks() {
        List<Object> mocks = new ArrayList<>();

        OngoingInjecter ongoingInjecterMock = mock(OngoingInjecter.class);
        when(nextMockCandidateFilter.filterCandidate(anyCollection(), any(Field.class), any())).thenReturn(ongoingInjecterMock);

        OngoingInjecter result = typeBasedCandidateFilter.filterCandidate(mocks, testField, fieldInstance);

        assertNotNull(result);
        verify(nextMockCandidateFilter).filterCandidate(argThat(Collection::isEmpty), eq(testField), eq(fieldInstance));
    }

    private static class TestClass {
        private TestClass testField;
    }

    private static class AnotherClass {
    }
}