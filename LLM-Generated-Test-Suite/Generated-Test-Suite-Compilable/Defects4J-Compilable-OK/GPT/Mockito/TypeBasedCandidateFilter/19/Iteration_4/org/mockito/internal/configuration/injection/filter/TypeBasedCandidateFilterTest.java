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
    private MockCandidateFilter nextMockCandidateFilter;

    private TypeBasedCandidateFilter typeBasedCandidateFilter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        typeBasedCandidateFilter = new TypeBasedCandidateFilter(nextMockCandidateFilter);
    }

    @Test
    public void testFilterCandidateWithMatchingMocks() throws NoSuchFieldException {
        // Arrange
        List<Object> mocks = new ArrayList<>();
        mocks.add("StringMock");
        mocks.add(123);

        Field field = TestClass.class.getDeclaredField("stringField");
        Object fieldInstance = new Object();

        // Act
        OngoingInjecter result = typeBasedCandidateFilter.filterCandidate(mocks, field, fieldInstance);

        // Assert
        assertNotNull(result);
        verify(nextMockCandidateFilter).filterCandidate(anyList(), eq(field), eq(fieldInstance));
    }

    @Test
    public void testFilterCandidateWithNoMatchingMocks() throws NoSuchFieldException {
        // Arrange
        List<Object> mocks = new ArrayList<>();
        mocks.add(123);
        mocks.add(456);

        Field field = TestClass.class.getDeclaredField("stringField");
        Object fieldInstance = new Object();

        // Act
        OngoingInjecter result = typeBasedCandidateFilter.filterCandidate(mocks, field, fieldInstance);

        // Assert
        assertNotNull(result);
        verify(nextMockCandidateFilter).filterCandidate(eq(new ArrayList<>()), eq(field), eq(fieldInstance));
    }

    @Test
    public void testFilterCandidateWithEmptyMocks() throws NoSuchFieldException {
        // Arrange
        List<Object> mocks = new ArrayList<>();

        Field field = TestClass.class.getDeclaredField("stringField");
        Object fieldInstance = new Object();

        // Act
        OngoingInjecter result = typeBasedCandidateFilter.filterCandidate(mocks, field, fieldInstance);

        // Assert
        assertNotNull(result);
        verify(nextMockCandidateFilter).filterCandidate(eq(new ArrayList<>()), eq(field), eq(fieldInstance));
    }

    private static class TestClass {
        private String stringField;
    }
}