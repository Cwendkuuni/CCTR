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

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class TypeBasedCandidateFilterTest {

    private TypeBasedCandidateFilter typeBasedCandidateFilter;
    private MockCandidateFilter nextMockCandidateFilter;

    @Before
    public void setUp() {
        nextMockCandidateFilter = mock(MockCandidateFilter.class);
        typeBasedCandidateFilter = new TypeBasedCandidateFilter(nextMockCandidateFilter);
    }

    @Test
    public void testFilterCandidateWithMatchingType() throws NoSuchFieldException {
        // Arrange
        List<Object> mocks = new ArrayList<>();
        mocks.add("StringMock");
        mocks.add(123); // Integer mock

        Field field = TestClass.class.getDeclaredField("stringField");
        Object fieldInstance = new TestClass();

        // Act
        typeBasedCandidateFilter.filterCandidate(mocks, field, fieldInstance);

        // Assert
        verify(nextMockCandidateFilter).filterCandidate(argThat(argument -> {
            return argument.size() == 1 && argument.contains("StringMock");
        }), eq(field), eq(fieldInstance));
    }

    @Test
    public void testFilterCandidateWithNoMatchingType() throws NoSuchFieldException {
        // Arrange
        List<Object> mocks = new ArrayList<>();
        mocks.add(123); // Integer mock

        Field field = TestClass.class.getDeclaredField("stringField");
        Object fieldInstance = new TestClass();

        // Act
        typeBasedCandidateFilter.filterCandidate(mocks, field, fieldInstance);

        // Assert
        verify(nextMockCandidateFilter).filterCandidate(argThat(Collection::isEmpty), eq(field), eq(fieldInstance));
    }

    @Test
    public void testFilterCandidateWithMultipleMatchingTypes() throws NoSuchFieldException {
        // Arrange
        List<Object> mocks = new ArrayList<>();
        mocks.add("StringMock1");
        mocks.add("StringMock2");

        Field field = TestClass.class.getDeclaredField("stringField");
        Object fieldInstance = new TestClass();

        // Act
        typeBasedCandidateFilter.filterCandidate(mocks, field, fieldInstance);

        // Assert
        verify(nextMockCandidateFilter).filterCandidate(argThat(argument -> {
            return argument.size() == 2 && argument.contains("StringMock1") && argument.contains("StringMock2");
        }), eq(field), eq(fieldInstance));
    }

    @Test
    public void testFilterCandidateWithNullMocks() throws NoSuchFieldException {
        // Arrange
        Field field = TestClass.class.getDeclaredField("stringField");
        Object fieldInstance = new TestClass();

        // Act
        typeBasedCandidateFilter.filterCandidate(null, field, fieldInstance);

        // Assert
        verify(nextMockCandidateFilter).filterCandidate(argThat(Collection::isEmpty), eq(field), eq(fieldInstance));
    }

    @Test
    public void testConstructor() {
        // Assert
        assertNotNull(typeBasedCandidateFilter);
    }

    // Helper class for testing
    private static class TestClass {
        private String stringField;
    }
}