package org.mockito.internal.configuration.injection.filter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class MockCandidateFilterTest {

    @Mock
    private MockCandidateFilter mockCandidateFilter;

    @Mock
    private Field mockField;

    @Mock
    private OngoingInjecter mockOngoingInjecter;

    private Collection<Object> mocks;
    private Object fieldInstance;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mocks = Arrays.asList(new Object(), new Object());
        fieldInstance = new Object();
    }

    @Test
    public void testFilterCandidate() {
        // Arrange
        when(mockCandidateFilter.filterCandidate(mocks, mockField, fieldInstance)).thenReturn(mockOngoingInjecter);

        // Act
        OngoingInjecter result = mockCandidateFilter.filterCandidate(mocks, mockField, fieldInstance);

        // Assert
        assertNotNull(result);
        verify(mockCandidateFilter).filterCandidate(mocks, mockField, fieldInstance);
    }

    @Test(expected = NullPointerException.class)
    public void testFilterCandidateWithNullMocks() {
        // Arrange
        when(mockCandidateFilter.filterCandidate(null, mockField, fieldInstance)).thenThrow(new NullPointerException());

        // Act
        mockCandidateFilter.filterCandidate(null, mockField, fieldInstance);

        // Assert (expecting NullPointerException)
    }

    @Test(expected = NullPointerException.class)
    public void testFilterCandidateWithNullField() {
        // Arrange
        when(mockCandidateFilter.filterCandidate(mocks, null, fieldInstance)).thenThrow(new NullPointerException());

        // Act
        mockCandidateFilter.filterCandidate(mocks, null, fieldInstance);

        // Assert (expecting NullPointerException)
    }

    @Test(expected = NullPointerException.class)
    public void testFilterCandidateWithNullFieldInstance() {
        // Arrange
        when(mockCandidateFilter.filterCandidate(mocks, mockField, null)).thenThrow(new NullPointerException());

        // Act
        mockCandidateFilter.filterCandidate(mocks, mockField, null);

        // Assert (expecting NullPointerException)
    }
}