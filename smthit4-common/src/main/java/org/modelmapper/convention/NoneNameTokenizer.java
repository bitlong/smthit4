package org.modelmapper.convention;

import org.modelmapper.spi.NameTokenizer;
import org.modelmapper.spi.NameableType;

/**
 * TODO
 *
 * @author bean
 * @date 2023/9/8
 */
public class NoneNameTokenizer implements NameTokenizer {
    @Override
    public String[] tokenize(String s, NameableType nameableType) {
        System.out.println(s);
        return new String[]{s};
    }
}
