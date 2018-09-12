package barometer.tags;

import java.text.ParseException;
import java.util.*;

public class Tokenizer {

    public List<Token> tokenize(String string) throws ParseException {
        List<Token> tokens = new ArrayList<>();
        if (string.isEmpty()) {
            return tokens;
        }
        int index = 0;
        while (index < string.length()) {
            Map<TokenType, String> temp = new HashMap<>();
            for (TokenType type : TokenType.values()) {
                String value = type.getMatcher().matchFrom(string, index);
                if (value != null) {
                    temp.put(type, value);
                }
            }
            if (temp.size() != 1) {
                System.out.println(temp.entrySet());
                throw new ParseException("invalid character: " + string.charAt(index), index);
            }
            Map.Entry<TokenType, String> entry = temp.entrySet().iterator().next();
            TokenType type = entry.getKey();
            String value = entry.getValue();
            if (!type.ignore()) {
                Token token = new Token(type, value);
                System.out.println(token.getType());
                System.out.println(token.getValue());
                tokens.add(token);
            }
            index += value.length();
        }
        return tokens;
    }
}
