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

    private MockCandidateFilter nextMock;
    private TypeBasedCandidateFilter filter;

    @Before
    public void setUp() {
        nextMock = mock(MockCandidateFilter.class);
        filter = new TypeBasedCandidateFilter(nextMock);
    }

    @Test
    public void testFilterCandidate_withMatchingType() throws NoSuchFieldException {
        // Arrange
        List<Object> mocks = new ArrayList<>();
        mocks.add("StringMock");
        Field field = TestClass.class.getDeclaredField("stringField");
        Object fieldInstance = new Object();

        OngoingInjecter expectedInjecter = mock(OngoingInjecter.class);
        when(nextMock.filterCandidate(anyCollection(), eq(field), eq(fieldInstance))).thenReturn(expectedInjecter);

        // Act
        OngoingInjecter result = filter.filterCandidate(mocks, field, fieldInstance);

        // Assert
        assertEquals(expectedInjecter, result);
        verify(nextMock).filterCandidate(mocks, field, fieldInstance);
    }

    @Test
    public void testFilterCandidate_withNonMatchingType() throws NoSuchFieldException {
        // Arrange
        List<Object> mocks = new ArrayList<>();
        mocks.add(123); // Integer mock
        Field field = TestClass.class.getDeclaredField("stringField");
        Object fieldInstance = new Object();

        OngoingInjecter expectedInjecter = mock(OngoingInjecter.class);
        when(nextMock.filterCandidate(anyCollection(), eq(field), eq(fieldInstance))).thenReturn(expectedInjecter);

        // Act
        OngoingInjecter result = filter.filterCandidate(mocks, field, fieldInstance);

        // Assert
        assertEquals(expectedInjecter, result);
        verify(nextMock).filterCandidate(eq(new ArrayList<>()), eq(field), eq(fieldInstance));
    }

    @Test
    public void testFilterCandidate_withMultipleMatchingTypes() throws NoSuchFieldException {
        // Arrange
        List<Object> mocks = new ArrayList<>();
        mocks.add("StringMock1");
        mocks.add("StringMock2");
        Field field = TestClass.class.getDeclaredField("stringField");
        Object fieldInstance = new Object();

        OngoingInjecter expectedInjecter = mock(OngoingInjecter.class);
        when(nextMock.filterCandidate(anyCollection(), eq(field), eq(fieldInstance))).thenReturn(expectedInjecter);

        // Act
        OngoingInjecter result = filter.filterCandidate(mocks, field, fieldInstance);

        // Assert
        assertEquals(expectedInjecter, result);
        verify(nextMock).filterCandidate(mocks, field, fieldInstance);
    }

    private static class TestClass {
        private String stringField;
    }
}