public class Main {
    public static void main (String[] args) {
        GUI window = new GUI();
        Logic test = new Logic();

        int[] processes = {1, 2, 3};
        int[] burstTime = {4, 3, 5};

        int quantumTime = 2;

        test.roundRobinSchedulingProcess(burstTime, quantumTime);

    }
}
