package org.modelmapper.convention;

import org.modelmapper.convention.InexactMatcher;
import org.modelmapper.convention.LooseMatchingStrategy;
import org.modelmapper.spi.MatchingStrategy;
import org.modelmapper.spi.PropertyNameInfo;
import org.modelmapper.spi.Tokens;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * TODO
 *
 * @author bean
 * @date 2023/9/8
 */
public class CustomMatchingStrategy implements MatchingStrategy {
    public boolean matches(PropertyNameInfo propertyNameInfo) {
        return new Matcher(propertyNameInfo).match();
    }

    @Override
    public boolean isExact() {
        return false;
    }

    @Override
    public String toString() {
        return "smthit-custom";
    }

    static class Matcher extends InexactMatcher {
        Matcher(PropertyNameInfo propertyNameInfo) {
            super(propertyNameInfo);
        }

        boolean match() {
            Set<Integer> matchSources = new HashSet();
            Iterator var2 = this.propertyNameInfo.getDestinationPropertyTokens().iterator();

            while(var2.hasNext()) {
                Tokens destTokens = (Tokens)var2.next();
                int destTokenIndex = 0;

                while(destTokenIndex < destTokens.size()) {
                    InexactMatcher.DestTokensMatcher matchedTokens = this.matchSourcePropertyName(destTokens, destTokenIndex);
                    if (matchedTokens.match()) {
                        destTokenIndex += matchedTokens.maxMatchTokens();
                        matchSources.addAll(matchedTokens.matchSources());
                    } else {
                        if (!this.matchSourcePropertyType(destTokens.token(destTokenIndex)) && !this.matchSourceClass(destTokens.token(destTokenIndex))) {
                            return false;
                        }

                        ++destTokenIndex;
                    }
                }
            }

            return matchSources.size() == this.sourceTokens.size();
        }
    }
}
