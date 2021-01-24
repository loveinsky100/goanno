package org.leo.goanno.test;

import java.util.List;

//
// @Description: unit test for ganno, please run this before submit commit
//
public class GenerateTest {
    public static void main(String []args) {
        List<TestCommon.GoFuncInfo> goFuncInfoList = TestCommon.readGoFuncInfoList(TestCommon.TEST_FILE);
        TestCommon.assertTrue(null != goFuncInfoList && !goFuncInfoList.isEmpty(), "goFuncInfoList is empty");
        goFuncInfoList.stream().forEach(TestCommon::assertGenerateCode);
        System.out.println("all test complete");
    }
}
