import java.util.List;

class Parser {
    private final VariableManager variableManager;
    private int currentIndex;
    private final List<String> tokens;
    private String currentToken;

    public Parser(Tokenizer tokenizer, VariableManager variableManager) {
        this.variableManager = variableManager;
        this.currentIndex = -1;
        this.tokens = tokenizer.getTokens();
        parseProgram();
    }

    public void parseProgram() {
        advance();
        while (currentToken != null) {
            parseAssignment();
        }
    }

    private void parseAssignment() {
        String identifier = parseIdentifier();
        match("=");
        int value = parseExpression();
        variableManager.assignVariable(identifier, value);
        match(";");
    }

    private String parseIdentifier() {
        if (currentToken == null || !isIdentifier(currentToken))
            throw new RuntimeException("Syntax error: Expected an identifier");

        String identifier = currentToken;
        advance();
        return identifier;
    }

    private boolean isIdentifier(String token) {
        return token.matches("[a-zA-Z_][a-zA-Z0-9_]*");
    }

    private int parseExpression() {
        int value = parseTerm();
        while (currentToken != null && (currentToken.equals("+") || currentToken.equals("-"))) {
            String operator = currentToken;
            advance();
            int term = parseTerm();
            if (operator.equals("+")) {
                value += term;
            } else {
                value -= term;
            }
        }
        return value;
    }

    private int parseTerm() {
        int value = parseFact();
        while (currentToken != null && currentToken.equals("*")) {
            advance();
            value *= parseFact();
        }
        return value;
    }

    private int parseFact() {
        int negateCount = 0;
        while (currentToken != null && (currentToken.equals("-") || currentToken.equals("+"))) {
            if (currentToken.equals("-")) {
                negateCount++;
            }
            advance();
        }

        if (currentToken == null) {
            throw new RuntimeException("Syntax error: Unexpected end of input");
        }

        int value;
        if (currentToken.matches("\\d+")) {
            value = Integer.parseInt(currentToken);
            advance();
        } else if (currentToken.equals("(")) {
            advance();
            value = parseExpression();
            match(")");
        } else if (isIdentifier(currentToken)) {
            String identifier = currentToken;
            advance();
            value = variableManager.getVariable(identifier);
        } else {
            throw new RuntimeException("Syntax error: Unexpected token '" + currentToken + "'");
        }

        if (negateCount % 2 != 0) {
            value = -value;
        }

        return value;
    }

    private void match(String expectedToken) {
        if (!expectedToken.equals(currentToken))
            throw new RuntimeException("Syntax error: Expected '" + expectedToken + "'");
        advance();
    }

    private void advance() {
        currentIndex++;
        if (currentIndex < tokens.size())
            currentToken = tokens.get(currentIndex);
        else
            currentToken = null;
    }
}