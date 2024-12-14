import java.util.HashMap;

class VariableManager {
    private final HashMap<String, Integer> variables = new HashMap<>();

    public void assignVariable(String identifier, int value) {
        variables.put(identifier, value);
    }

    public int getVariable(String identifier) {
        if (!variables.containsKey(identifier)) {
            throw new RuntimeException("Uninitialized variable: " + identifier);
        }
        return variables.get(identifier);
    }

    public HashMap<String, Integer> getAllVariables() {
        return variables;
    }

    public void clearVariables() { variables.clear(); }
}
