package org.leo.goanno.test;

import java.util.Arrays;
import java.util.List;

//
// @Description: unit test for ganno, please run this before submit commit
//
public class GenerateTest {
    public static void main(String []args) {
        test("test.go", "test2.go", "test3.go", "test4.go");
    }

    private static void test(String ...files) {
        Arrays.stream(files).forEach(GenerateTest::testFile);
        System.out.println("all test complete");
    }

    private static void testFile(String file) {
        String template = TestCommon.readGoFuncTemplate(file);
        List<TestCommon.GoFuncInfo> goFuncInfoList = TestCommon.readGoFuncInfoList(file);
        try {
            TestCommon.assertTrue(null != goFuncInfoList && !goFuncInfoList.isEmpty(), "goFuncInfoList is empty for file: " + file);
            goFuncInfoList.stream().forEach(goFuncInfo -> TestCommon.assertGenerateCode(template, goFuncInfo));
            System.out.println("test complete for file: " + file);
        } catch (Exception e) {
            System.err.println("test failed for file: " + file);
            e.printStackTrace();
        }
    }
}
