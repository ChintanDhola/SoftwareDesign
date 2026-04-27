public class UlamFunctionVerifier{
    public static void main(String[] args) {
        int limit = 1000000;
        System.out.println("Starting verification for n < " + limit + "...");

        for (int i = 1; i < limit; i++) {
            if (!verifies(i)) {
                System.out.println("Failed for n = " + i);
                return;
            }
        }
        System.out.println("Verification complete. All positive integers n < 1,000,000 terminate with 1.");
    }

    private static boolean verifies(int n) {
        long num_to_test = n;
        while (num_to_test != 1) {
            if (num_to_test % 2 == 0) {
                num_to_test /= 2;
            } else {
                num_to_test = 3 * num_to_test + 1;
            }
            // safety break if it somehow goes below 1 or reaches a cycle (not expected for n < 1M)
            if (num_to_test <= 0)
                return false;
        }
        return true;
    }
}