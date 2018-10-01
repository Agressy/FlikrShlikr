package com.bortnikov.artem.flikrshlikr;

import com.bortnikov.artem.flikrshlikr.model.RealmModel;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockTest {

    @Test
    public void with_arguments() {
        Comparable c = mock(Comparable.class);
        when(c.compareTo(new RealmModel())).thenReturn(1);
        assertEquals(1, c.compareTo("Test"));
    }
}
