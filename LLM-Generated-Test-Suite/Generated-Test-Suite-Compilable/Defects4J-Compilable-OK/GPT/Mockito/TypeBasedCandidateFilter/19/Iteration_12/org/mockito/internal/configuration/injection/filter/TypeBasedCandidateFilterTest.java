package org.mockito.internal.configuration.injection.filter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.configuration.injection.filter.MockCandidateFilter;
import org.mockito.internal.configuration.injection.filter.OngoingInjecter;
import org.mockito.internal.configuration.injection.filter.TypeBasedCandidateFilter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class TypeBasedCandidateFilterTest {

    @Mock
    private MockCandidateFilter nextFilter;

    private TypeBasedCandidateFilter typeBasedCandidateFilter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        typeBasedCandidateFilter = new TypeBasedCandidateFilter(nextFilter);
    }

    @Test
    public void testFilterCandidateWithMatchingType() throws NoSuchFieldException {
        // Arrange
        List<Object> mocks = new ArrayList<>();
        mocks.add("StringMock");
        Field field = TestClass.class.getDeclaredField("stringField");
        Object fieldInstance = new Object();

        // Act
        OngoingInjecter injecter = typeBasedCandidateFilter.filterCandidate(mocks, field, fieldInstance);

        // Assert
        assertNotNull(injecter);
        verify(nextFilter).filterCandidate(mocks, field, fieldInstance);
    }

    @Test
    public void testFilterCandidateWithNoMatchingType() throws NoSuchFieldException {
        // Arrange
        List<Object> mocks = new ArrayList<>();
        mocks.add(123); // Integer mock
        Field field = TestClass.class.getDeclaredField("stringField");
        Object fieldInstance = new Object();

        // Act
        OngoingInjecter injecter = typeBasedCandidateFilter.filterCandidate(mocks, field, fieldInstance);

        // Assert
        assertNotNull(injecter);
        verify(nextFilter).filterCandidate(new ArrayList<>(), field, fieldInstance);
    }

    @Test
    public void testFilterCandidateWithMultipleMatchingTypes() throws NoSuchFieldException {
        // Arrange
        List<Object> mocks = new ArrayList<>();
        mocks.add("StringMock1");
        mocks.add("StringMock2");
        Field field = TestClass.class.getDeclaredField("stringField");
        Object fieldInstance = new Object();

        // Act
        OngoingInjecter injecter = typeBasedCandidateFilter.filterCandidate(mocks, field, fieldInstance);

        // Assert
        assertNotNull(injecter);
        verify(nextFilter).filterCandidate(mocks, field, fieldInstance);
    }

    @Test
    public void testFilterCandidateWithEmptyMocks() throws NoSuchFieldException {
        // Arrange
        List<Object> mocks = new ArrayList<>();
        Field field = TestClass.class.getDeclaredField("stringField");
        Object fieldInstance = new Object();

        // Act
        OngoingInjecter injecter = typeBasedCandidateFilter.filterCandidate(mocks, field, fieldInstance);

        // Assert
        assertNotNull(injecter);
        verify(nextFilter).filterCandidate(mocks, field, fieldInstance);
    }

    private static class TestClass {
        private String stringField;
    }
}