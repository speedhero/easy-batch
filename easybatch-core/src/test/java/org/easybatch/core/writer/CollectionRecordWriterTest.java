/*
 *  The MIT License
 *
 *   Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */

package org.easybatch.core.writer;

import org.easybatch.core.mapper.GenericRecordMapper;
import org.easybatch.core.reader.IterableRecordReader;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.easybatch.core.impl.EngineBuilder.aNewEngine;

/**
 * Test class for {@link CollectionRecordWriter}.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class CollectionRecordWriterTest {

    private static final String FOO = "foo";
    private static final String BAR = "bar";

    private List<String> items;

    private CollectionRecordWriter<String> writer;

    @Before
    public void setUp() throws Exception {
        items = new ArrayList<String>();
        writer = new CollectionRecordWriter<String>(items);

    }

    @Test
    public void testWriteRecord() throws Exception {
        writer.writeRecord(FOO);
        assertThat(items).isNotEmpty().hasSize(1).containsExactly(FOO);
    }

    @Test
    public void integrationTest() throws Exception {
        List<String> input = Arrays.asList(FOO, BAR);
        List<String> output = new ArrayList<String>();

        aNewEngine()
                .reader(new IterableRecordReader<String>(input))
                .mapper(new GenericRecordMapper())
                .writer(new CollectionRecordWriter<String>(output))
                .build().call();

        assertThat(output).isNotEmpty().hasSize(2).containsExactly(FOO, BAR);
    }
}