import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class PB extends JFrame
{

    private JLabel programLabel;
    private JTextArea program;
    private JPanel programPanel;

    private JLabel outputLabel;
    private JTextArea output;
    private JPanel outputPanel;

    private JLabel errorLabel;
    private JTextArea error;
    private JPanel errorPanel;

    private JLabel inputLabel;
    private JTextArea input;
    private JPanel inputPanel;

    private JButton compileButton;
    private JButton executeButton;

    private Container container;
    private JPanel centerPanel;
    private JPanel bottomPanel;

    PB()
    {
        programLabel=new JLabel("Program");
        program=new JTextArea();
        programPanel=new JPanel();
        programPanel.setLayout(new BorderLayout());
        programPanel.add(programLabel, BorderLayout.NORTH);
        programPanel.add(program, BorderLayout.CENTER);

        outputLabel=new JLabel("Output");
        output=new JTextArea();
        outputPanel=new JPanel();
        outputPanel.setLayout(new BorderLayout());
        outputPanel.add(outputLabel, BorderLayout.NORTH);
        outputPanel.add(output, BorderLayout.CENTER);

        inputLabel=new JLabel("Input");
        input=new JTextArea();
        inputPanel=new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(inputLabel, BorderLayout.NORTH);
        inputPanel.add(input, BorderLayout.CENTER);

        errorLabel=new JLabel("Errors");
        error=new JTextArea();
        errorPanel=new JPanel();
        errorPanel.setLayout(new BorderLayout());
        errorPanel.add(errorLabel, BorderLayout.NORTH);
        errorPanel.add(error, BorderLayout.CENTER);

        centerPanel=new JPanel();
        centerPanel.setLayout(new GridLayout(2, 2));
        centerPanel.add(programPanel);
        centerPanel.add(errorPanel);
        centerPanel.add(inputPanel);
        centerPanel.add(outputPanel);

        compileButton=new JButton("Compile");
        executeButton=new JButton("Execute");
        bottomPanel=new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(compileButton);
        bottomPanel.add(executeButton);

    }

    public static void main(String[] args) 
    {
        
    }
}