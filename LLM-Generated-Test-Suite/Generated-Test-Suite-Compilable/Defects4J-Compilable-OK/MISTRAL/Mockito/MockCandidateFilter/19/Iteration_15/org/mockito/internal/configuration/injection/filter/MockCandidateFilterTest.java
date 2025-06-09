package org.mockito.internal.configuration.injection.filter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MockCandidateFilterTest {

    @Mock
    private MockCandidateFilter mockCandidateFilter;

    @Mock
    private Field mockField;

    @Mock
    private OngoingInjecter mockOngoingInjecter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFilterCandidate() throws Exception {
        // Arrange
        Collection<Object> mocks = Arrays.asList(new Object(), new Object());
        Object fieldInstance = new Object();

        when(mockCandidateFilter.filterCandidate(mocks, mockField, fieldInstance)).thenReturn(mockOngoingInjecter);

        // Act
        OngoingInjecter result = mockCandidateFilter.filterCandidate(mocks, mockField, fieldInstance);

        // Assert
        assertNotNull(result);
        assertEquals(mockOngoingInjecter, result);
        verify(mockCandidateFilter).filterCandidate(mocks, mockField, fieldInstance);
    }

    @Test(expected = NullPointerException.class)
    public void testFilterCandidateWithNullMocks() throws Exception {
        // Arrange
        Collection<Object> mocks = null;
        Object fieldInstance = new Object();

        // Act
        mockCandidateFilter.filterCandidate(mocks, mockField, fieldInstance);

        // Assert: Expecting NullPointerException
    }

    @Test(expected = NullPointerException.class)
    public void testFilterCandidateWithNullField() throws Exception {
        // Arrange
        Collection<Object> mocks = Arrays.asList(new Object(), new Object());
        Object fieldInstance = new Object();

        // Act
        mockCandidateFilter.filterCandidate(mocks, null, fieldInstance);

        // Assert: Expecting NullPointerException
    }

    @Test(expected = NullPointerException.class)
    public void testFilterCandidateWithNullFieldInstance() throws Exception {
        // Arrange
        Collection<Object> mocks = Arrays.asList(new Object(), new Object());

        // Act
        mockCandidateFilter.filterCandidate(mocks, mockField, null);

        // Assert: Expecting NullPointerException
    }
}