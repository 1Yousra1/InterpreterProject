import java.util.ArrayList;
import java.util.List;

class Tokenizer {
    private final String program;
    private int currentIndex;
    private final List<String> tokens;

    public Tokenizer(String program) {
        this.program = program;
        this.currentIndex = 0;
        this.tokens = new ArrayList<>();
        tokenizeProgram();
    }

    private void tokenizeProgram() {
        while (currentIndex < program.length()) {
            char currentChar = program.charAt(currentIndex);


            if (Character.isWhitespace(currentChar)) {
                currentIndex++;
                continue;
            }
            else if (Character.isLetter(currentChar) || currentChar == '_') {
                tokens.add(parseIdentifier());
                continue;
            }
            else if (Character.isDigit(currentChar)) {
                tokens.add(parseNumber());
                continue;
            }
            else if ("=+-*/();".indexOf(currentChar) != -1) {
                tokens.add(String.valueOf(currentChar));
                currentIndex++;
                continue;
            }

            throw new RuntimeException("Unexpected character: " + currentChar);
        }
    }

    private String parseIdentifier() {
        StringBuilder identifier = new StringBuilder();
        while (currentIndex < program.length() &&
                (Character.isLetterOrDigit(program.charAt(currentIndex)) || program.charAt(currentIndex) == '_')) {
            identifier.append(program.charAt(currentIndex));
            currentIndex++;
        }
        return identifier.toString();
    }

    private String parseNumber() {
        StringBuilder number = new StringBuilder();
        while (currentIndex < program.length() && Character.isDigit(program.charAt(currentIndex))) {
            number.append(program.charAt(currentIndex));
            currentIndex++;
        }

        if (number.length() > 1 && number.charAt(0) == '0') {
            throw new RuntimeException("Error: Invalid number");
        }

        return number.toString();
    }

    public List<String> getTokens() {
        return tokens;
    }
}

