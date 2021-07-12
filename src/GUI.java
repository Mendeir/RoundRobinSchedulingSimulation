import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class GUI extends JFrame implements ActionListener {

    //Calling logic class
    Logic algoProcess = new Logic();

    //Instantiation of arrays for displaying process
    String[] queue = {"2","3","4","5","6","7","8"};
    int[] inputQueue;
    int[] waitTimeStorage;
    int[] turnTimeStorage;

    //Declaration of variables for setter
    int quantumInput = 0;
    double avgWaitTime = 0;
    double avgTurnTime = 0;

    //used for array instantiation
    int queueSize = 0;

    //used for loops
    int i;
    int j;

    //Instantiation of gui objects
    JFrame frame = new JFrame("Round Robin Scheduling Simulation");
    JPanel panelOne = new JPanel();
    JPanel panelResult = new JPanel();
    JPanel dropDownPanel = new JPanel();
    JButton enterButton = new JButton("Enter");
    JButton rightButton = new JButton(">");
    JButton leftButton = new JButton("<");
    JButton randomButton = new JButton("Random");
    JComboBox selectionQueue = new JComboBox(queue);
    JTable table;
    JLabel[] label;
    JTextField quantumTime = new JTextField();
    JTextField[] input;
    GridBagConstraints gbc =  new GridBagConstraints();
    DefaultTableModel data = new DefaultTableModel();

    // formatting panel layout
    TitledBorder title;
    Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
    DecimalFormat df = new DecimalFormat("###.####");

    GUI(){
        //format for display output
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Frame size and design
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(400, 100, 500, 490);

        // Panel size and format
        dropDownPanel.setBounds(0, 0, 500, 50);
        panelOne.setBounds(0, 50, 485, 400);
        panelOne.setLayout(new GridBagLayout());
        title = BorderFactory.createTitledBorder(blackLine, "Please Input Numbers");
        title.setTitleJustification(TitledBorder.CENTER);
        panelOne.setBorder(title);

        panelResult.setBounds(0, 50, 485, 400);
        panelResult.setLayout(new BorderLayout());

        // Button sizes and formats
        dropDownPanel.add(leftButton);
        dropDownPanel.add(rightButton);
        rightButton.addActionListener(this);
        leftButton.addActionListener(this);
        randomButton.setBounds(0, 20, 100, 30);
        randomButton.addActionListener(this);
        dropDownPanel.add(randomButton);
        enterButton.setBounds(300, 20, 100, 25);
        enterButton.addActionListener(this);
        dropDownPanel.add(enterButton);

        // Combobox design and simulations
        selectionQueue.setBounds(200, 20, 100, 25);
        selectionQueue.addActionListener(this);
        dropDownPanel.add(selectionQueue);

        // Adds and visibility of the frame
        frame.add(panelOne);
        frame.add(panelResult);
        frame.add(dropDownPanel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        // box size action
        if (e.getSource() == selectionQueue) {

            // Get the user's wanted size form the comboBox
            String size = (String) selectionQueue.getSelectedItem();
            queueSize = Integer.parseInt(size);
            queueGenerator(queueSize);
        }

        // Random Button action
        if (e.getSource() == randomButton) {
            quantumTime.setText(String.format("%d", (int) (Math.random() * 10)));
            for (i = 0; i < queueSize; i++) {
                input[i].setText(String.format("%d", (int) (Math.random() * 10)));
            }
        }

        if (e.getSource() == enterButton){
            // try catch for error handling
            try {
                //initialization of inputMatrix
                inputQueue = new int[queueSize];
                // Store all the values input by the user on inputQueue
                quantumInput = Integer.parseInt(quantumTime.getText());
                for (i = 0; i < queueSize; i++) {
                        inputQueue[i] = Integer.parseInt(input[i].getText());
                    }
            }catch(NumberFormatException er){
                // calling error window class
                ErrorWindow error = new ErrorWindow();
            }
            //process done calling logic class
            algoProcess.roundRobinSchedulingProcess(inputQueue,quantumInput);
            waitTimeStorage = algoProcess.getWaitingTimeCollection();
            turnTimeStorage = algoProcess.getTurnAroundTimeCollection();
            avgWaitTime = algoProcess.getAverageWaitingTime();
            avgTurnTime = algoProcess.getAverageTurnAroundTime();
            displayQueue();
        }

        if (e.getSource() ==  rightButton){

        }

        if (e.getSource() == leftButton){

        }
    }

    public void queueGenerator(int queueSize){
        label = new JLabel[queueSize];
        input = new JTextField[queueSize];

        panelOne.removeAll();
        panelOne.revalidate();
        frame.repaint();

        label[0] = new JLabel("Quantum Time");
        gbc.gridy = 0;
        label[0].setFont(new Font("Arial", Font.BOLD, 20));
        quantumTime = new JTextField(4);
        gbc.gridy = 0;
        quantumTime.setFont(new Font("Arial", Font.BOLD, 20));
        panelOne.add(label[0],gbc);
        panelOne.add(quantumTime,gbc);
        quantumTime.setHorizontalAlignment(JTextField.CENTER);

        for(i=0; i < queueSize; i++){
            label[i] = new JLabel("P" + (i+1));
            gbc.gridy = i+1;
            label[i].setFont(new Font("Arial", Font.BOLD, 20));
            panelOne.add(label[i],gbc);
            label[i].setHorizontalAlignment(JLabel.CENTER);

            input[i] = new JTextField(4);
            gbc.gridy = i+1;
            input[i].setFont(new Font("Arial", Font.BOLD, 20));
            panelOne.add(input[i], gbc);
            input[i].setHorizontalAlignment(JTextField.CENTER);
        }
    }

    public void finalResultTable(){
        frame.setBounds(400, 100, 500, 490);
        panelOne.setBorder(new EmptyBorder(0,0,0,0));
        table = new JTable(data);
        table.setFont(new Font("Arial", Font.PLAIN, 20));
        table.setRowHeight(30);

        data.addColumn("Process");
        data.addColumn("Burst Time");
        data.addColumn("Waiting Time");
        data.addColumn("Turnaround Time");

        for(i=0; i < queueSize; i++){
            data.addRow(new Object[]{"P" + (i+1), inputQueue[i],waitTimeStorage[i],turnTimeStorage[i]});
        }
        data.addRow(new Object[]{"Average", null, avgWaitTime, avgTurnTime});


        panelResult.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void displayQueue(){
        frame.setBounds(200, 100, 1000, 690);
        panelOne.setBounds(0, 50, 985, 200);

        panelOne.removeAll();
        panelOne.revalidate();
        frame.repaint();

        for (int i = 0; i < queueSize; i++) {
            labelIndex = new JLabel(String.valueOf(i),JLabel.CENTER);
            labelValues = new JLabel(String.valueOf(values.get(i)),JLabel.CENTER);
            labelValues.setBounds(i*50,120,50,50);
            labelIndex.setBounds(i*50,70,50,50);
            labelValues.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panelOne.add(labelValues);
            panelOne.add(labelIndex);
        }

    }

}
