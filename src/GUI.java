import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JTable;

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

    //Process iterator
    int navigationCounter = -1;
    int processCounter = 0;
    int listCounter = 0;

    //used for array instantiation
    int queueSize = 0;

    //used for loops
    int i;
    int j;

    // ArrayList Declaration
    ArrayList <Integer> displayChartValues;
    ArrayList <ArrayList<String>> displayProcessValues;

    //Instantiation of gui objects
    JFrame frame = new JFrame("Round Robin Scheduling Simulation");
    JPanel panelOne = new JPanel();
    JPanel panelTwo = new JPanel();
    JPanel panelResult = new JPanel();
    JPanel dropDownPanel = new JPanel();
    JButton enterButton = new JButton("Enter");
    JButton rightButton = new JButton(">");
    JButton leftButton = new JButton("<");
    JButton randomButton = new JButton("Random");
    JComboBox selectionQueue = new JComboBox(queue);
    JTable table;
    JLabel[] label;
    JLabel labelProcessValues;
    JLabel labelChartValues;
    JTextField quantumTime = new JTextField();
    JTextField[] input;
    GridBagConstraints gbc =  new GridBagConstraints();
    DefaultTableModel data = new DefaultTableModel();

    // formatting panel layout
    TitledBorder title;
    Border blackLine = BorderFactory.createLineBorder(Color.BLACK);

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
        frame.add(panelTwo);
        frame.add(panelOne);
        frame.add(panelResult);
        frame.add(dropDownPanel);
        frame.setLayout(null);
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
            displayChartValues = algoProcess.getChartValues();
            displayProcessValues = algoProcess.getProcessValues();
        }

        if (e.getSource() ==  rightButton){
            if (navigationCounter != 1 + displayProcessValues.size())
            navigationCounter++;
            if (navigationCounter == 0){
                displayQueue();
                processCounter = 0;
            }else if (navigationCounter != 1 + displayProcessValues.size()){
                nextQueue();
                processCounter++;
            }else{
                finalResultTable();
            }
        }

        if (e.getSource() == leftButton){
            if (navigationCounter != 0 )
                navigationCounter--;

            if (navigationCounter == 0){
                panelTwo.setBorder(new EmptyBorder(0,0,0,0));
                panelTwo.removeAll();
                displayQueue();
                processCounter = 0;
            }else if (navigationCounter != 1 + displayProcessValues.size()){
                panelTwo.removeAll();
                --processCounter;
                nextQueue();
            }else{
                processCounter = 1+displayProcessValues.size();
                finalResultTable();
            }
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
        panelOne.removeAll();

        labelProcessValues = new JLabel("Quantum Time: " + quantumInput);
        labelProcessValues.setBounds(50,100,400,50);
        labelProcessValues.setFont(new Font("Arial",Font.BOLD,15));
        panelOne.add(labelProcessValues);

        panelTwo.setBorder(new EmptyBorder(0,0,0,0));
        frame.setBounds(400, 0, 600, 700);
        panelResult.setBounds(0, 350, 585, 400);
        data.setRowCount(0);
        data.setColumnCount(0);
        table = new JTable(data);
        table.setFont(new Font("Arial", Font.PLAIN, 20));
        table.setRowHeight(30);
        table.setRequestFocusEnabled(true);
        table.setPreferredScrollableViewportSize(new Dimension(485,300));

        data.addColumn("Process");
        data.addColumn("Burst Time");
        data.addColumn("Waiting Time");
        data.addColumn("Turnaround Time");

        for(i=0; i < queueSize; i++){
            data.addRow(new Object[]{"P" + (i+1), inputQueue[i],waitTimeStorage[i],turnTimeStorage[i]});
        }
        data.addRow(new Object[]{"Average", null, avgWaitTime, avgTurnTime});

        JScrollPane scrollPane = new JScrollPane(table);
        panelResult.add(scrollPane);
    }

    public void displayQueue(){
        panelOne.removeAll();
        panelOne.revalidate();
        frame.repaint();

        frame.setBounds(200, 100, 1000, 390);
        panelOne.setBounds(0, 50, 985, 150);
        panelTwo.setBounds(0,200,985,150);
        panelOne.setBorder(new EmptyBorder(0, 0, 0, 0));
        panelOne.setLayout(null);
        panelTwo.setLayout(null);

        for (i = 1; i <= queueSize; i++) {

            labelProcessValues = new JLabel( "P" + String.valueOf(i),JLabel.CENTER);
            labelProcessValues.setBounds(i*100,50,100,50);
            labelProcessValues.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panelOne.add(labelProcessValues);

        }

        labelProcessValues = new JLabel("Quantum Time: " + quantumInput);
        labelProcessValues.setBounds(50,100,400,50);
        labelProcessValues.setFont(new Font("Arial",Font.PLAIN,15));
        panelOne.add(labelProcessValues);

        for (i = 0; i < displayChartValues.size()-1; i++) {

            labelChartValues = new JLabel(" ",JLabel.CENTER);
            labelChartValues.setBounds(i*50,25,50,50);
            labelChartValues.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panelTwo.add(labelChartValues);

        }
        for (i = 0; i < displayChartValues.size(); i++){
            labelChartValues = new JLabel(String.valueOf(displayChartValues.get(i)));
            labelChartValues.setBounds(i*48,70,50,50);
            panelTwo.add(labelChartValues);
        }
    }

    public void nextQueue() {
        panelOne.removeAll();
        panelOne.revalidate();
        panelTwo.revalidate();
        frame.repaint();

        frame.setBounds(200, 100, 1000, 390);
        panelOne.setBounds(0, 50, 985, 150);
        panelTwo.setBounds(0,200,985,150);
        panelOne.setBorder(new EmptyBorder(0, 0, 0, 0));
        panelOne.setLayout(null);
        panelTwo.setLayout(null);

        if(processCounter == 0) {
            listCounter = 0;
        }else if(processCounter == 1) {
            listCounter = displayProcessValues.get(0).size();
        }

        for (i =0; i < displayProcessValues.get(processCounter).size(); i++) {
            listCounter++;
            labelProcessValues = new JLabel(String.valueOf(displayProcessValues.get(processCounter).get(i)), JLabel.CENTER);
            labelProcessValues.setBounds((i+1) * 100, 50, 100, 50);
            labelProcessValues.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panelOne.add(labelProcessValues);

            labelChartValues = new JLabel(String.valueOf(displayProcessValues.get(processCounter).get(i)),JLabel.CENTER);
            labelChartValues.setBounds((listCounter-1)*50,25,50,50);
            labelChartValues.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panelTwo.add(labelChartValues);
    }

        labelProcessValues = new JLabel("Quantum Time: " + quantumInput);
        labelProcessValues.setBounds(50,100,400,50);
        labelProcessValues.setFont(new Font("Arial",Font.PLAIN,15));
        panelOne.add(labelProcessValues);

        for (i = 0; i < displayChartValues.size()-1; i++) {

            labelChartValues = new JLabel(" ",JLabel.CENTER);
            labelChartValues.setBounds(i*50,25,50,50);
            labelChartValues.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panelTwo.add(labelChartValues);


        }
        for (i = 0; i < displayChartValues.size(); i++){
            labelChartValues = new JLabel(String.valueOf(displayChartValues.get(i)));
            labelChartValues.setBounds(i*48,70,50,50);
            panelTwo.add(labelChartValues);
        }
    }

}
