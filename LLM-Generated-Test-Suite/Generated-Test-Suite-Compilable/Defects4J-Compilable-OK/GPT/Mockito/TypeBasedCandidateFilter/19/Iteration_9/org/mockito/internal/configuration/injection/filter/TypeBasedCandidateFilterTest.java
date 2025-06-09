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
    public void testFilterCandidateWithMatchingType() throws NoSuchFieldException {
        // Arrange
        List<Object> mocks = new ArrayList<>();
        mocks.add("stringMock");
        mocks.add(123);

        Field field = TestClass.class.getDeclaredField("stringField");
        Object fieldInstance = new TestClass();

        List<Object> expectedMatches = new ArrayList<>();
        expectedMatches.add("stringMock");

        OngoingInjecter ongoingInjecterMock = mock(OngoingInjecter.class);
        when(nextMock.filterCandidate(expectedMatches, field, fieldInstance)).thenReturn(ongoingInjecterMock);

        // Act
        OngoingInjecter result = filter.filterCandidate(mocks, field, fieldInstance);

        // Assert
        assertEquals(ongoingInjecterMock, result);
        verify(nextMock).filterCandidate(expectedMatches, field, fieldInstance);
    }

    @Test
    public void testFilterCandidateWithNoMatchingType() throws NoSuchFieldException {
        // Arrange
        List<Object> mocks = new ArrayList<>();
        mocks.add(123);

        Field field = TestClass.class.getDeclaredField("stringField");
        Object fieldInstance = new TestClass();

        List<Object> expectedMatches = new ArrayList<>();

        OngoingInjecter ongoingInjecterMock = mock(OngoingInjecter.class);
        when(nextMock.filterCandidate(expectedMatches, field, fieldInstance)).thenReturn(ongoingInjecterMock);

        // Act
        OngoingInjecter result = filter.filterCandidate(mocks, field, fieldInstance);

        // Assert
        assertEquals(ongoingInjecterMock, result);
        verify(nextMock).filterCandidate(expectedMatches, field, fieldInstance);
    }

    @Test
    public void testFilterCandidateWithMultipleMatchingTypes() throws NoSuchFieldException {
        // Arrange
        List<Object> mocks = new ArrayList<>();
        mocks.add("stringMock1");
        mocks.add("stringMock2");

        Field field = TestClass.class.getDeclaredField("stringField");
        Object fieldInstance = new TestClass();

        List<Object> expectedMatches = new ArrayList<>();
        expectedMatches.add("stringMock1");
        expectedMatches.add("stringMock2");

        OngoingInjecter ongoingInjecterMock = mock(OngoingInjecter.class);
        when(nextMock.filterCandidate(expectedMatches, field, fieldInstance)).thenReturn(ongoingInjecterMock);

        // Act
        OngoingInjecter result = filter.filterCandidate(mocks, field, fieldInstance);

        // Assert
        assertEquals(ongoingInjecterMock, result);
        verify(nextMock).filterCandidate(expectedMatches, field, fieldInstance);
    }

    private static class TestClass {
        private String stringField;
    }
}