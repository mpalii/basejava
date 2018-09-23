package ru.javaops.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapUuidStorageTest.class,
        MapResumeStorageTest.class,
        PathObjectSerializerTest.class,
        FileObjectSerializerTest.class,
        PathXmlSerializerTest.class,
        FileXmlSerializerTest.class,
        PathJsonSerializerTest.class,
        FileJsonSerializerTest.class,
        PathDataSerializerTest.class,
        FileDataSerializerTest.class
})
public class SuiteTest {

}
