package org.mockito.internal.configuration.injection.filter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.configuration.injection.filter.MockCandidateFilter;
import org.mockito.internal.configuration.injection.filter.OngoingInjecter;
import org.mockito.internal.configuration.injection.filter.TypeBasedCandidateFilter;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TypeBasedCandidateFilterTest {

    private MockCandidateFilter mockNextFilter;
    private TypeBasedCandidateFilter typeBasedCandidateFilter;
    private Field mockField;

    @Before
    public void setUp() throws NoSuchFieldException {
        mockNextFilter = mock(MockCandidateFilter.class);
        typeBasedCandidateFilter = new TypeBasedCandidateFilter(mockNextFilter);
        mockField = getClass().getDeclaredField("mockNextFilter");
    }

    @Test
    public void testFilterCandidate_NoMatches() {
        Collection<Object> mocks = Arrays.asList("mock1", "mock2");
        Object fieldInstance = new Object();

        OngoingInjecter result = typeBasedCandidateFilter.filterCandidate(mocks, mockField, fieldInstance);

        verify(mockNextFilter).filterCandidate(Collections.emptyList(), mockField, fieldInstance);
        assertNotNull(result);
    }

    @Test
    public void testFilterCandidate_SingleMatch() {
        MockCandidateFilter mockFilter = mock(MockCandidateFilter.class);
        Collection<Object> mocks = Arrays.asList(mockFilter, "mock2");
        Object fieldInstance = new Object();

        OngoingInjecter result = typeBasedCandidateFilter.filterCandidate(mocks, mockField, fieldInstance);

        verify(mockNextFilter).filterCandidate(Collections.singletonList(mockFilter), mockField, fieldInstance);
        assertNotNull(result);
    }

    @Test
    public void testFilterCandidate_MultipleMatches() {
        MockCandidateFilter mockFilter1 = mock(MockCandidateFilter.class);
        MockCandidateFilter mockFilter2 = mock(MockCandidateFilter.class);
        Collection<Object> mocks = Arrays.asList(mockFilter1, mockFilter2, "mock3");
        Object fieldInstance = new Object();

        OngoingInjecter result = typeBasedCandidateFilter.filterCandidate(mocks, mockField, fieldInstance);

        verify(mockNextFilter).filterCandidate(Arrays.asList(mockFilter1, mockFilter2), mockField, fieldInstance);
        assertNotNull(result);
    }

    @Test
    public void testFilterCandidate_NoMocks() {
        Collection<Object> mocks = Collections.emptyList();
        Object fieldInstance = new Object();

        OngoingInjecter result = typeBasedCandidateFilter.filterCandidate(mocks, mockField, fieldInstance);

        verify(mockNextFilter).filterCandidate(Collections.emptyList(), mockField, fieldInstance);
        assertNotNull(result);
    }

    @Test
    public void testFilterCandidate_FieldInstanceNull() {
        Collection<Object> mocks = Arrays.asList("mock1", "mock2");
        Object fieldInstance = null;

        OngoingInjecter result = typeBasedCandidateFilter.filterCandidate(mocks, mockField, fieldInstance);

        verify(mockNextFilter).filterCandidate(Collections.emptyList(), mockField, fieldInstance);
        assertNotNull(result);
    }
}