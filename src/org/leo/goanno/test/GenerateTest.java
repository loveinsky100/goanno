package org.leo.goanno.test;

import org.leo.goanno.generate.Generator;
import org.leo.goanno.generate.impl.DefaultFuncCommentGeneratorImpl;
import org.leo.goanno.template.impl.DefaultTemplateImpl;

public class GenerateTest {
    private static boolean generateCompare(String func, String except) {
        Generator generator = new DefaultFuncCommentGeneratorImpl(new DefaultTemplateImpl(0, 0), null);
        String template = generator.generate(func);

        return except.equals(template);
    }

    public static void main(String[] args) {

    }
}
