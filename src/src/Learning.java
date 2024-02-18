package src.src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class Learning extends JFrame {
    private JTextPane textPane;
    private JTextField answerField;
    private final String caseStudyContent = """
            <html>
            <body>
            <h1>Investment Case Study: The Rise of Tesla</h1>
            <h2>Overview</h2>
            <p>This case study explores Tesla, Inc.'s journey from a startup to becoming one of the most valuable automotive companies in the world. It focuses on Tesla's strategic decisions, innovative product development, and market positioning that disrupted the traditional automotive industry.</p>
            <h2>Key Points:</h2>
            <ul>
            <li>Founding and Mission: Founded in 2003 by a group of engineers, Tesla aimed to prove electric vehicles could be better than gasoline-powered cars.</li>
            <li>Innovation and Challenges: The Roadster, Model S, Model X, and Model 3's development highlighted Tesla's innovation. The company faced production, financial, and logistical challenges.</li>
            <li>Market Disruption: Tesla's direct-to-consumer sales model, Supercharger network, and Autopilot technology set it apart from competitors.</li>
            <li>Financial Growth: Despite early struggles, Tesla achieved profitability and became the highest-valued automaker by market capitalization in 2020.</li>
            </ul>
            </body>
            </html>
            """;
    private final String educationalMaterialContent = """
            <html>
            <body>
            <h1>Basics of Investing in Stocks</h1>
            <h2>Introduction to Stocks</h2>
            <p>Stocks represent ownership in a company. When you buy a company's stock, you own a piece of that company. Stocks are bought and sold on stock exchanges.</p>
            
            <h2>Why Invest in Stocks?</h2>
            <p>Over the long term, stocks have historically provided higher returns than other investments, such as bonds or savings accounts, but they come with higher risk.</p>
            
            <h2>Understanding the Market</h2>
            <p>The stock market is influenced by many factors, including economic indicators, company earnings, and market sentiment. Investors should conduct thorough research or consult financial advisors.</p>
            
            <h2>Risk Management</h2>
            <p>Diversification, understanding your risk tolerance, and long-term planning are key to managing investment risk.</p>
            </body>
            </html>
            """;
    private List<QuizQuestion> quizQuestions;
    private int currentQuestionIndex = 0;
    private JButton nextButton;

    private class QuizQuestion {
        String content;
        String correctAnswer;

        QuizQuestion(String content, String correctAnswer) {
            this.content = content;
            this.correctAnswer = correctAnswer;
        }
    }
    // Example questions
    private final String question1 = """
            <html>
            <body>
            <h1>Quiz: Tesla Case Study and Investing Basics</h1>
            <h2>Who founded Tesla?</h2>
            <p>A) Elon Musk alone<br>
            B) A group of engineers<br>
            C) The U.S. Government<br>
            D) General Motors<br></p>
            </html>
            </body>
            """;

    private final String question2 = """
            <html>
            <body>
            <h1>Quiz: Tesla Case Study and Investing Basics</h1>
            <h2>What was Tesla's primary mission?</h2>
            <p>A) To build the fastest car in the world<br>
            B) To prove electric vehicles could be better than gasoline-powered cars<br>
            C) To create autonomous driving technology<br>
            D) To become the largest car manufacturer in the world</p>
            </html>
            </body>
            """;

    private final String question3 = """
            <html>
            <body>
            <h1>Quiz: Tesla Case Study and Investing Basics</h1>
            <h2>Which of the following is NOT a reason to invest in stocks?</h2>
            <p>A) Potential for high returns<br>
            B) Guaranteed income<br>
            C) Ownership in a company<br>
            D) Potential to influence corporate decisions</p>
            </html>
            </body>
            """;

    private final String question4 = """
            <html>
            <body>
            <h1>Quiz: Tesla Case Study and Investing Basics</h1>
            <h2>What is one way to manage risk when investing in stocks?</h2>
            <p>A) Putting all your money into one stock<br>
            B) Avoiding stocks altogether<br>
            C) Diversification<br>
            D) Consulting a fortune teller</p>
            </html>
            </body>
            """;

    public Learning() {
        setTitle("Investment Learning Platform");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        initializeQuizQuestions();

        // Text pane for displaying content
        textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textPane);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton caseStudyButton = new JButton("Case Study");
        JButton materialButton = new JButton("Educational Material");
        JButton quizButton = new JButton("Quiz");
        JButton submitButton = new JButton("Submit Answer");
        answerField = new JTextField(5);
        nextButton = new JButton("Next Question");
        nextButton.setEnabled(false); // Disabled until the quiz starts

        caseStudyButton.addActionListener(e -> displayContent(caseStudyContent));
        materialButton.addActionListener(e -> displayContent(educationalMaterialContent));
        quizButton.addActionListener(e -> startQuiz());
        submitButton.addActionListener(e -> checkAnswer());
        nextButton.addActionListener(e -> displayNextQuestion());

        buttonPanel.add(caseStudyButton);
        buttonPanel.add(materialButton);
        buttonPanel.add(quizButton);
        buttonPanel.add(answerField);
        buttonPanel.add(submitButton);
        buttonPanel.add(nextButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void initializeQuizQuestions() {
        quizQuestions = new ArrayList<>();
        quizQuestions.add(new QuizQuestion(question1, "B")); // Example question
        quizQuestions.add(new QuizQuestion(question2, "B")); // Example question
        quizQuestions.add(new QuizQuestion(question3, "B")); // Example question
        quizQuestions.add(new QuizQuestion(question4, "C")); // Example question
        // Add more questions as needed
    }

    private void displayContent(String content) {
        textPane.setText(content);
    }

    private void startQuiz() {
        currentQuestionIndex = 0;
        displayCurrentQuestion();
    }

    private void displayCurrentQuestion() {
        if (currentQuestionIndex < quizQuestions.size()) {
            QuizQuestion question = quizQuestions.get(currentQuestionIndex);
            displayContent(question.content);
            nextButton.setEnabled(false); // Disable next button until the correct answer is provided
        } else {
            textPane.setText("<html>All quiz questions completed. Great job!</html>");
        }
    }

    private void checkAnswer() {
        String userAnswer = answerField.getText().trim();
        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);
        if (userAnswer.equalsIgnoreCase(currentQuestion.correctAnswer)) {
            JOptionPane.showMessageDialog(this, "Correct answer!");
            nextButton.setEnabled(true); // Enable next button
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect answer. Try again!");
        }
    }

    private void displayNextQuestion() {
        if (currentQuestionIndex < quizQuestions.size() - 1) {
            currentQuestionIndex++;
            displayCurrentQuestion();
        } else {
            textPane.setText("<html>All quiz questions completed. Great job!</html>");
            nextButton.setEnabled(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Learning().setVisible(true));
    }
}