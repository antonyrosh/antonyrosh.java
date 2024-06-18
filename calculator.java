import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class calculator implements ActionListener {

    private JFrame frame;
    private JTextField displayLabel;
    private double firstNumber = 0;
    private double secondNumber = 0;
    private double result = 0;
    private String operator = "";
    private boolean isOperatorClicked = false;

    public calculator() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel = new JTextField();
        displayLabel.setPreferredSize(new Dimension(300, 80));
        displayLabel.setEditable(false);
        displayLabel.setHorizontalAlignment(JTextField.RIGHT);
        frame.add(displayLabel, BorderLayout.NORTH);

        initializeButtons();

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initializeButtons() {
        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "C", "+",
                "=" // "=" button spans two columns
        };

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        for (String label : buttonLabels) {
            addButton(buttonPanel, label);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
    }

    private void addButton(Container container, String label) {
        JButton button = new JButton(label);
        button.addActionListener(this);
        container.add(button);
    }

    private void handleOperatorButton(String label) {
        firstNumber = Double.parseDouble(displayLabel.getText());
        operator = label;
        isOperatorClicked = true;
        displayLabel.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String label = e.getActionCommand();

        if (label.equals("C")) {
            clearCalculator();
        } else if (label.matches("[+\\-*/]")) {
            handleOperatorButton(label);
        } else if (label.equals(".")) {
            handleDecimalPoint();
        } else if (label.equals("=")) {
            calculateResult();
        } else {
            displayLabel.setText(displayLabel.getText() + label);
        }
    }

    private void clearCalculator() {
        displayLabel.setText("");
        firstNumber = 0;
        secondNumber = 0;
        result = 0;
        operator = "";
        isOperatorClicked = false;
    }

    private void handleDecimalPoint() {
        if (!displayLabel.getText().contains(".")) {
            displayLabel.setText(displayLabel.getText() + ".");
        }
    }

    private void calculateResult() {
        if (isOperatorClicked) {
            secondNumber = Double.parseDouble(displayLabel.getText());
            switch (operator) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "*":
                    result = firstNumber * secondNumber;
                    break;
                case "/":
                    if (secondNumber != 0) {
                        result = firstNumber / secondNumber;
                    } else {
                        displayLabel.setText("Error: Division by zero");
                        return;
                    }
                    break;
            }
            displayLabel.setText(String.valueOf(result));
            firstNumber = result;
            secondNumber = 0;
            operator = "";
            isOperatorClicked = false;
        }
    }

    public static void main(String[] args) {
        new calculator();
    }
}
