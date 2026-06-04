package ecohero;

public class Question {
    private final String prompt;
    private final String[] options;
    private final int correctIndex;

    public Question(String prompt, String[] options, int correctIndex) {
        this.prompt = prompt;
        this.options = options;
        this.correctIndex = correctIndex;
    }

    public String getPrompt() {
        return prompt;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }
}
