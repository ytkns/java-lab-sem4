import java.io.*;

public class lab7 {

    public static void main(String[] args) {
        String filePath = "input.txt"; 

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                try {
                    processLine(line, filePath);
                } catch (LexicalErrorException e) {
                    System.err.println(line + " -> błąd leksykalny: " + e.getMessage());
                } catch (SyntaxErrorException e) {
                    System.err.println(line + " -> błąd składni: " + e.getMessage());
                } catch (RuntimeEvaluationException e) {
                    System.err.println(line + " -> błąd wykonania: " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Błąd: Plik wejściowy nie istnieje " + filePath);
            System.exit(1); 
        } catch (SecurityException e) {
            System.err.println("Błąd: Brak uprawnień do odczytu pliku.");
            System.exit(1); 
        } catch (IOException e) {
            System.err.println("Błąd: Nieoczekiwany błąd podczas odczytu pliku: " + e.getMessage());
            System.exit(1); 
        }
    }

    private static void processLine(String line, String filename) throws LexicalErrorException, SyntaxErrorException, RuntimeEvaluationException, IOException {
        
        String trimmedLine = line.trim();

        if (!trimmedLine.matches("^[a-zA-Z0-9+\\-*/()=\\s.]+$")) {
            throw new LexicalErrorException("niedozwolone znaki");
        }

        if (!trimmedLine.endsWith("=")) {
            throw new SyntaxErrorException("oczekiwano '=' ");
        }

        int openParen = 0;
        int closeParen = 0;
        int divCount = 0;

        for (char c : trimmedLine.toCharArray()) {
            if (c == '(') openParen++;
            if (c == ')') closeParen++;
            if (c == '*' || c == '/') divCount++;
        }

        if (openParen > closeParen) throw new SyntaxErrorException("oczekiwano )");
        if (closeParen > openParen) throw new SyntaxErrorException("oczekiwano (");
        if (openParen > 1) throw new SyntaxErrorException("dozwolona tylko jedna para nawiasów ");
        if (divCount > 2) throw new SyntaxErrorException("za dużo operacji mnożenia/dzielenia (maksymalnie dwie) ");

        String expression = trimmedLine.substring(0, trimmedLine.length() - 1).trim();

        if (expression.matches(".*\\/\\s*0(\\D.*|$)")) {
            throw new RuntimeEvaluationException("dzielenie przez zero");
        }

        String result;
        try {
            result = pythonEval(expression, filename);
            
            if (result == null || result.isEmpty()) {
                throw new RuntimeEvaluationException("błąd obliczeń");
            }

            if (result.contains(".") || result.contains("e")) {
                double val = Double.parseDouble(result);
                if (val > 0 && val < 1)
                    throw new RuntimeEvaluationException("przekroczono limit liczb całkowitych ");
            }

            double numericResult = Double.parseDouble(result);
            if (numericResult > Integer.MAX_VALUE || numericResult < Integer.MIN_VALUE) {
                throw new RuntimeEvaluationException("przekroczono limit liczb całkowitych ");
            }

            System.out.println(line + " " + result);

        } catch (NumberFormatException e) {
            throw new RuntimeEvaluationException("Błąd konwersji wyniku matematycznego.");
        } 
    }

    public static String pythonEval(String expression, String filename) throws IOException{
        String ans;
        Process process = new ProcessBuilder(
            "python3",
            "-c",
            "print(eval('" + expression+ "'))"
        ).start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))){
            ans = reader.readLine();
            return ans;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return "";
    }
}

class SyntaxErrorException extends Exception {
    public SyntaxErrorException(String message) { super(message); }
}

class RuntimeEvaluationException extends Exception {
    public RuntimeEvaluationException(String message) { super(message); }
}

class LexicalErrorException extends Exception {
    public LexicalErrorException(String message) { super(message); }
}

