import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class PB extends JFrame {

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

    private String gccHome;
    private String gcc;

    PB(String gccHome) 
    {
        this.gccHome=gccHome;
        if(gccHome.endsWith("\\") || gccHome.endsWith("/"))
        {
            this.gcc=gccHome+"bin"+File.separator+"gcc.exe";
        }
        else
        {
            this.gcc=gccHome+File.separator+"bin"+File.separator+"gcc.exe";
        }
        programLabel = new JLabel("Program");
        program = new JTextArea();
        programPanel = new JPanel();
        programPanel.setLayout(new BorderLayout());
        programPanel.add(programLabel, BorderLayout.NORTH);
        programPanel.add(new JScrollPane(program), BorderLayout.CENTER);
        programPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        outputLabel = new JLabel("Output");
        output = new JTextArea();
        outputPanel = new JPanel();
        outputPanel.setLayout(new BorderLayout());
        outputPanel.add(outputLabel, BorderLayout.NORTH);
        outputPanel.add(new JScrollPane(output), BorderLayout.CENTER);
        output.setEditable(false);
        outputPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        inputLabel = new JLabel("Input");
        input = new JTextArea();
        inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(inputLabel, BorderLayout.NORTH);
        inputPanel.add(new JScrollPane(input), BorderLayout.CENTER);
        inputPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        errorLabel = new JLabel("Errors");
        error = new JTextArea();
        errorPanel = new JPanel();
        errorPanel.setLayout(new BorderLayout());
        errorPanel.add(errorLabel, BorderLayout.NORTH);
        errorPanel.add(new JScrollPane(error), BorderLayout.CENTER);
        error.setEditable(false);
        errorPanel.setBorder(BorderFactory.createLineBorder(Color.RED));

        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 2));
        centerPanel.add(programPanel);
        centerPanel.add(errorPanel);
        centerPanel.add(inputPanel);
        centerPanel.add(outputPanel);

        compileButton = new JButton("Compile");
        executeButton = new JButton("Execute");
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(compileButton);
        bottomPanel.add(executeButton);

        container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(centerPanel, BorderLayout.CENTER);
        container.add(bottomPanel, BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(10, 10);
        setSize(1024, 768);
        setVisible(true);

        compileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev)
            {
                try 
                {
                    File tmpFolder=new File("tmp");
                    if(tmpFolder.exists() && tmpFolder.isDirectory()==false) tmpFolder.delete();
                    if(tmpFolder.exists()==false) tmpFolder.mkdir();
                } catch (Exception e) 
                {
                    System.out.println(e);
                }
            }
        });
    }

    public static void main(String[] args) {
        String gccHome=System.getenv("GCC_HOME");
        if(gccHome==null || gccHome.length()==0)
        {
            System.out.println("GCC_HOME environment variable is not set");
            return;
        }
        PB pb = new PB(gccHome);
    }
}
