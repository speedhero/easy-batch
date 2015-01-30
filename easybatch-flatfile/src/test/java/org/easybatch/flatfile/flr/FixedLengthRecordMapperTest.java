/*
 * The MIT License
 *
 *  Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package org.easybatch.flatfile.flr;

import org.easybatch.flatfile.FlatFileRecord;
import org.easybatch.core.record.StringRecord;
import org.easybatch.flatfile.Bean;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test class for {@link org.easybatch.flatfile.flr.FixedLengthRecordMapper}.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class FixedLengthRecordMapperTest {

    private FixedLengthRecordMapper fixedLengthRecordMapper;

    private StringRecord stringRecord;

    @Before
    public void setUp() throws Exception {
        fixedLengthRecordMapper = new FixedLengthRecordMapper<Bean>(Bean.class,
                new int[]{4, 2, 3},
                new String[]{"field1", "field2", "field3"});
        stringRecord = new StringRecord(1, "aaaabbccc");
    }

    @Test (expected = Exception.class)
    public void testIllFormedRecord() throws Exception {
        stringRecord = new StringRecord(1, "aaaabbcccd"); // unexpected record size
        fixedLengthRecordMapper.parseRecord(stringRecord);
    }

    @Test
    public void testRecordParsing() throws Exception {
        FlatFileRecord flatFileRecord = fixedLengthRecordMapper.parseRecord(stringRecord);
        Assertions.assertThat(flatFileRecord.getFlatFileFields().size()).isEqualTo(3);
        Assertions.assertThat(flatFileRecord.getFlatFileFields().get(0).getRawContent()).isEqualTo("aaaa");
        Assertions.assertThat(flatFileRecord.getFlatFileFields().get(1).getRawContent()).isEqualTo("bb");
        Assertions.assertThat(flatFileRecord.getFlatFileFields().get(2).getRawContent()).isEqualTo("ccc");
    }

}