import java.util.Scanner;

public class Interpreter {
    private final VariableManager variableManager;

    public Interpreter() {
        this.variableManager = new VariableManager();
    }

    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter your program (double Enter to execute, type 'exit' to quit):");
            StringBuilder inputProgram = new StringBuilder();

            while (true) {
                String line = scanner.nextLine().trim();

                if (line.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting program.");
                    return;
                }

                if (line.isEmpty()) break;
                else inputProgram.append(line).append("\n");
            }
            interpreter.interpret(inputProgram.toString());
        }
    }

    public void interpret(String inputProgram) {
        try {
            Tokenizer tokenizer = new Tokenizer(inputProgram);
            Parser parser = new Parser(tokenizer, variableManager);
            parser.parseProgram();

            printVariables();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        variableManager.clearVariables();
    }

    private void printVariables() {
        variableManager.getAllVariables().forEach((key, value) -> System.out.println(key + " = " + value));
    }
}

