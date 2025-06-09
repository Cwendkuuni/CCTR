package org.mockito.internal.configuration.injection.filter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TypeBasedCandidateFilterTest {

    private MockCandidateFilter mockNextFilter;
    private TypeBasedCandidateFilter typeBasedCandidateFilter;

    @Before
    public void setUp() {
        mockNextFilter = Mockito.mock(MockCandidateFilter.class);
        typeBasedCandidateFilter = new TypeBasedCandidateFilter(mockNextFilter);
    }

    @Test
    public void testFilterCandidate_NoMatches() throws NoSuchFieldException {
        Collection<Object> mocks = Arrays.asList("mock1", "mock2");
        Field field = String.class.getDeclaredField("CASE_INSENSITIVE_ORDER");
        Object fieldInstance = new Object();

        when(mockNextFilter.filterCandidate(anyCollection(), eq(field), eq(fieldInstance)))
                .thenReturn(null);

        OngoingInjecter result = typeBasedCandidateFilter.filterCandidate(mocks, field, fieldInstance);

        assertNull(result);
        verify(mockNextFilter, times(1)).filterCandidate(Collections.emptyList(), field, fieldInstance);
    }

    @Test
    public void testFilterCandidate_WithMatches() throws NoSuchFieldException {
        Collection<Object> mocks = Arrays.asList("mock1", new Integer(1), "mock2");
        Field field = Integer.class.getDeclaredField("MAX_VALUE");
        Object fieldInstance = new Object();

        when(mockNextFilter.filterCandidate(anyCollection(), eq(field), eq(fieldInstance)))
                .thenReturn(null);

        OngoingInjecter result = typeBasedCandidateFilter.filterCandidate(mocks, field, fieldInstance);

        assertNull(result);
        verify(mockNextFilter, times(1)).filterCandidate(Arrays.asList(new Integer(1)), field, fieldInstance);
    }

    @Test
    public void testFilterCandidate_MultipleMatches() throws NoSuchFieldException {
        Collection<Object> mocks = Arrays.asList("mock1", new Integer(1), new Integer(2));
        Field field = Integer.class.getDeclaredField("MAX_VALUE");
        Object fieldInstance = new Object();

        when(mockNextFilter.filterCandidate(anyCollection(), eq(field), eq(fieldInstance)))
                .thenReturn(null);

        OngoingInjecter result = typeBasedCandidateFilter.filterCandidate(mocks, field, fieldInstance);

        assertNull(result);
        verify(mockNextFilter, times(1)).filterCandidate(Arrays.asList(new Integer(1), new Integer(2)), field, fieldInstance);
    }

    @Test
    public void testFilterCandidate_NoMocks() throws NoSuchFieldException {
        Collection<Object> mocks = Collections.emptyList();
        Field field = Integer.class.getDeclaredField("MAX_VALUE");
        Object fieldInstance = new Object();

        when(mockNextFilter.filterCandidate(anyCollection(), eq(field), eq(fieldInstance)))
                .thenReturn(null);

        OngoingInjecter result = typeBasedCandidateFilter.filterCandidate(mocks, field, fieldInstance);

        assertNull(result);
        verify(mockNextFilter, times(1)).filterCandidate(Collections.emptyList(), field, fieldInstance);
    }
}