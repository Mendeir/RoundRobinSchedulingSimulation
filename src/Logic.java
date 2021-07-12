import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Logic {
    private int[] waitingTimeCollection;
    private int[] turnAroundTimeCollection;
    private double averageWaitingTime;
    private double averageTurnAroundTime;
    private final DecimalFormat fourDecimalPlaces;
    private final ArrayList<Integer> chartValues;
    private final ArrayList<ArrayList<String>> processValues;

    Logic () {
        averageWaitingTime = 0;
        averageTurnAroundTime = 0;
        fourDecimalPlaces = new DecimalFormat("#0.0000");
        chartValues = new ArrayList<Integer>();
        processValues = new ArrayList<ArrayList<String>>();
    }

    void roundRobinSchedulingProcess(int[] processBurstTime, int givenQuantumTime) {
        this.calculateWaitingTime(processBurstTime, givenQuantumTime);
        this.calculateTurnAroundTime(processBurstTime);
        this.calculateAverageTime();
        this.calculateAverageTurnAroundTime();
        System.out.println("Average Waiting Time: " + averageWaitingTime);
        System.out.println("Average Turn Around Time: " + averageTurnAroundTime);
    }

    void calculateWaitingTime(int[] burstTimeCollection, int quantumTime) {
        int[] trackRemainingBurstTime = burstTimeCollection.clone();
        waitingTimeCollection = new int[burstTimeCollection.length];

        int currentTime = 0;
        chartValues.add(0);
        int processCounter = 0;



        while(true) {
            boolean done = true;
            int indexCounter = 0;
            boolean passed = true;

            for (int counter = 0; counter < burstTimeCollection.length; ++counter) {

                if (trackRemainingBurstTime[counter] > 0) {
                    done = false;

                    if (passed) {
                        processValues.add(new ArrayList<String>());
                        passed = false;
                    }

                    String processString = "P" + (counter + 1);
                    processValues.get(processCounter).add(indexCounter, processString);
                    ++indexCounter;

                    System.out.print("Burst Time: " + "P" + (counter + 1) + " ");
                    if (trackRemainingBurstTime[counter] > quantumTime) {
                        currentTime += quantumTime;
                        trackRemainingBurstTime[counter] -= quantumTime;
                    } else {
                        currentTime += trackRemainingBurstTime[counter];
                        waitingTimeCollection[counter] = currentTime - burstTimeCollection[counter];
                        trackRemainingBurstTime[counter] = 0;
                    }
                    chartValues.add(currentTime);
                }
            }

            ++processCounter;
            System.out.println();

            if (done)
                break;
        }

        for (int a : waitingTimeCollection) {
            System.out.print(a + " ");
        }
        System.out.println();
        for (int a : chartValues) {
            System.out.print(a + " ");
        }

        System.out.println(processValues);
        System.out.println("Test" + processValues.get(0).get(1));
        System.out.println();
    }

    void calculateTurnAroundTime(int[] burstTimeCollection) {
        turnAroundTimeCollection = new int[burstTimeCollection.length];
        for (int counter = 0; counter < burstTimeCollection.length; ++counter) {
            turnAroundTimeCollection[counter] = burstTimeCollection[counter] + waitingTimeCollection[counter];
        }

        for (int a : turnAroundTimeCollection) {
            System.out.print(a + " ");
        }
    }

    void calculateAverageTime() {
        int totalWaitingTime = 0;

        for (int waitingTime : waitingTimeCollection) {
            totalWaitingTime += waitingTime;
        }

        String answer = fourDecimalPlaces.format((float)totalWaitingTime / waitingTimeCollection.length);
        this.averageWaitingTime = Double.parseDouble(answer);
    }

    void calculateAverageTurnAroundTime() {
        int totalTurnAroundTime = 0;

        for (int turanAroundTime : turnAroundTimeCollection) {
            totalTurnAroundTime += turanAroundTime;
        }

        String answer = fourDecimalPlaces.format((float)totalTurnAroundTime / turnAroundTimeCollection.length);
        this.averageTurnAroundTime = Double.parseDouble(answer);
    }

    public int[] getTurnAroundTimeCollection() {
        return turnAroundTimeCollection;
    }

    public ArrayList<ArrayList<String>> getProcessValues() {
        return processValues;
    }

    public ArrayList<Integer> getChartValues() {
        return chartValues;
    }

    public double getAverageTurnAroundTime() {
        return averageTurnAroundTime;
    }

    public double getAverageWaitingTime() {
        return averageWaitingTime;
    }

    public int[] getWaitingTimeCollection() {
        return waitingTimeCollection;
    }

    public void setAverageTurnAroundTime(double averageTurnAroundTime) {
        this.averageTurnAroundTime = averageTurnAroundTime;
    }
}