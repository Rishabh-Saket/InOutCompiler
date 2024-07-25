import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

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
        output.setForeground(Color.green);
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
        error.setForeground(Color.red);
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

                    String src="ci102d.c";
                    File srcFile=new File("tmp"+File.separator+src);
                    if(srcFile.exists()) srcFile.delete();
                    RandomAccessFile randomAccessFile=new RandomAccessFile(srcFile, "rw");
                    randomAccessFile.writeBytes(program.getText());
                    randomAccessFile.close();

                    String out="out.exe";

                    ProcessBuilder processBuilder=new ProcessBuilder();
                    processBuilder.directory(tmpFolder);

                    java.util.List<String> list=new ArrayList<>();
                    list.add(gcc);
                    list.add(src);
                    list.add("-o");
                    list.add(out);
                    processBuilder.command(list);

                    File stderr=new File("err.data");
                    if(stderr.exists()) stderr.delete();
                    error.setText("");
                    processBuilder.redirectError(stderr);

                    Process process=processBuilder.start();
                    process.waitFor();

                    if(stderr.exists())
                    {
                        RandomAccessFile errRandomAccessFile=new RandomAccessFile(stderr, "r");
                        while(errRandomAccessFile.getFilePointer()<errRandomAccessFile.length()) 
                        {
                            error.append(errRandomAccessFile.readLine()+"\r\n");
                        }
                        errRandomAccessFile.close();
                        stderr.delete();
                    }

                    JOptionPane.showMessageDialog(PB.this, "Compilation Done");

                } catch (Exception e) 
                {
                    System.out.println(e);
                }
            }
        });

        executeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev)
            {
                try 
                {
                    File tmpFolder=new File("tmp");
                    String out="out.exe";
                    File outFile = new File(tmpFolder, out);
                    if (!outFile.exists()) {
                        JOptionPane.showMessageDialog(PB.this, "Executable not found. Please compile first.");
                        return;
                    }

                    ProcessBuilder processBuilder = new ProcessBuilder();
                    processBuilder.directory(tmpFolder);
                    java.util.List<String> list = new ArrayList<>();
                    list.add(outFile.getAbsolutePath());
                    processBuilder.command(list);

                    File stdout = new File("output.data");
                    if (stdout.exists()) stdout.delete();
                    output.setText("");
                    processBuilder.redirectOutput(stdout);

                    Process process = processBuilder.start();
                    process.waitFor();

                    if (stdout.exists()) {
                        RandomAccessFile outRandomAccessFile = new RandomAccessFile(stdout, "r");
                        while (outRandomAccessFile.getFilePointer() < outRandomAccessFile.length()) {
                            output.append(outRandomAccessFile.readLine() + "\r\n");
                        }
                        outRandomAccessFile.close();
                        stdout.delete();
                    }

                    JOptionPane.showMessageDialog(PB.this, "Execution Done");

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
