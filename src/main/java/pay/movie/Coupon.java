package pay.movie;

import java.io.File;
import java.util.Scanner;

public class Coupon {
    private String code;

    public Coupon(String code) {
        this.code = code;
    }

    public double couponDiscount(String code) {
        File file = new File("couponCode.txt");
        try(Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if (data[0].equalsIgnoreCase(code)) {
                    return Double.parseDouble(data[1]);
                }
            }
        }catch (Exception a) {
            throw new RuntimeException("Coupon Is Not Found");
        }
        return 0;
    }
    public String getCode() {
        return code;
    }
}