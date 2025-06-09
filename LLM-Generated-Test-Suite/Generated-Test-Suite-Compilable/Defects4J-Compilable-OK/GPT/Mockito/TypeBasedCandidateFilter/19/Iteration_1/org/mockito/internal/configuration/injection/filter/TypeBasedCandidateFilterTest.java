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
        mocks.add("StringMock");
        Field field = TestClass.class.getDeclaredField("stringField");
        Object fieldInstance = new Object();

        // Act
        filter.filterCandidate(mocks, field, fieldInstance);

        // Assert
        verify(nextMock).filterCandidate(mocks, field, fieldInstance);
    }

    @Test
    public void testFilterCandidateWithNonMatchingType() throws NoSuchFieldException {
        // Arrange
        List<Object> mocks = new ArrayList<>();
        mocks.add(123); // Integer mock
        Field field = TestClass.class.getDeclaredField("stringField");
        Object fieldInstance = new Object();

        // Act
        filter.filterCandidate(mocks, field, fieldInstance);

        // Assert
        verify(nextMock).filterCandidate(new ArrayList<>(), field, fieldInstance);
    }

    @Test
    public void testFilterCandidateWithMultipleTypes() throws NoSuchFieldException {
        // Arrange
        List<Object> mocks = new ArrayList<>();
        mocks.add("StringMock");
        mocks.add(123); // Integer mock
        Field field = TestClass.class.getDeclaredField("stringField");
        Object fieldInstance = new Object();

        // Act
        filter.filterCandidate(mocks, field, fieldInstance);

        // Assert
        List<Object> expectedMocks = new ArrayList<>();
        expectedMocks.add("StringMock");
        verify(nextMock).filterCandidate(expectedMocks, field, fieldInstance);
    }

    @Test
    public void testFilterCandidateWithEmptyMocks() throws NoSuchFieldException {
        // Arrange
        List<Object> mocks = new ArrayList<>();
        Field field = TestClass.class.getDeclaredField("stringField");
        Object fieldInstance = new Object();

        // Act
        filter.filterCandidate(mocks, field, fieldInstance);

        // Assert
        verify(nextMock).filterCandidate(new ArrayList<>(), field, fieldInstance);
    }

    // Helper class for testing
    private static class TestClass {
        private String stringField;
    }
}