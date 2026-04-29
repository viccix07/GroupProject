package pay.movie;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main { //extends Application {
    static double totalAmount = 12.99; // Assume price is this

//    public void start(Stage stage) throws Exception {
//        Label priceLabel = new Label("Current Price: " + totalAmount);
//
//
//
//        Button startPaymentButton =new Button("Start Payment");
//
//
//        startPaymentButton.setOnMouseClicked(e -> {
//
//        });
//
//        Button btn = new Button("Go to new view");
//
//        VBox layout1 = new VBox(btn);
//        Scene scene1 = new Scene(layout1, 400, 300);
//
//        btn.setOnAction(e -> {
//            VBox layout2 = new VBox(new Label("New Content"));
//            scene1.setRoot(layout2);
//        });
//
//        Pane pane = new Pane();
//        pane.getChildren().addAll(
//                priceLabel,
//                layout1,
//                startPaymentButton
//        );
//
//
//        Scene scene = new Scene(pane);
//        stage.setScene(scene);
//        stage.setTitle("Cinema Payment System");
//    }

     public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Booking booking = new Booking("B123");
        System.out.println("Hi there! You need to pay " + totalAmount + "$ for this movie");

        System.out.print("Do you have a coupon code? (yes/no): ");
        String hasCoupon = scanner.nextLine();
        if (hasCoupon.equalsIgnoreCase("yes")) {
            System.out.print("Enter coupon code: ");
            String code = scanner.nextLine();
            Coupon coupon = new Coupon(code);
            totalAmount = (1 - coupon.couponDiscount(code) / 100) * totalAmount;
            System.out.printf("Coupon is applied! Current Amount to Pay is %.2f ", totalAmount);
        }
        System.out.println("\nChoose payment type: 1. Cash 2. Credit Card");
        int choice = scanner.nextInt();
        PaymentProcessor processor;

        try {
            if (choice == 1) {
                processor = new CashPayment();
                // Ask for Cash Requirements;
                System.out.print("Enter cash amount to give: ");
                double cashGiven = scanner.nextDouble();
                booking.makePayment(processor, totalAmount, cashGiven);
            } else if (choice == 2) {
                 // Ask for Credit Card Requirements;
                System.out.println("Credit Card Number: ");
                String cardNumber = scanner.next();
                if (isCardValid(cardNumber) && cardNumber.length() >= 15) {
                    System.out.println("CVV: ");
                    int cvv = scanner.nextInt();
                    System.out.println("Expiry Date: ");
                    String expiryDate = scanner.next();
                    processor = new CreditCardPayment(cardNumber, cvv, expiryDate);
                    booking.makePayment(processor, totalAmount);
                } else {
                    System.out.println("Invalid Credit Card Number");
                }

            } else {
                System.out.println("Invalid choice.");
            }
        } catch (Exception e) {
            System.out.println("Alert: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    public static boolean isCardValid(String creditCard) {
        int sum = 0;
        boolean alternate = false;

        for (int i = creditCard.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(String.valueOf(creditCard.charAt(i)));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            sum += n;
            alternate = !alternate;
        }

        return (sum % 10 == 0);
    }
}
//package pay.movie;
//
//import javafx.application.Application;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.*;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import javafx.scene.text.FontWeight;
//import javafx.stage.Stage;
//
//public class Main extends Application {
//
//    private double totalAmount    = 12.99;
//    private double originalAmount = 12.99;
//    private Booking   booking        = new Booking("B123");
//
//    private Label totalLabel;
//    private Label changeLabel;
//    private Label statusLabel;
//    private VBox  cashSection;
//    private VBox  cardSection;
//
//    // ── Palette ───────────────────────────────────────────────────────
//    private static final String BG_DEEP    = "#0d0d1a";
//    private static final String BG_PANEL   = "#111128";
//    private static final String BG_FIELD   = "#1a1a35";
//    private static final String GOLD       = "#f5c842";
//    private static final String ACCENT_GRN = "#00e676";
//    private static final String ACCENT_RED = "#ff3d57";
//    private static final String ACCENT_BLU = "#29b6f6";
//    private static final String TEXT_PRI   = "#f0f0f5";
//    private static final String TEXT_SEC   = "#9090b0";
//    private static final String BORDER     = "#2e2e55";
//
//    public void start(Stage stage) {
//        stage.setTitle("Cinema Payment Terminal");
//
//
//        // ── Root ──────────────────────────────────────────────────────
//        HBox root = new HBox(0);
//        root.setStyle("-fx-background-color: " + BG_DEEP + ";");
//
//        // Left accent stripe
//        VBox stripe = new VBox();
//        stripe.setPrefWidth(6);
//        stripe.setStyle("-fx-background-color: linear-gradient(to bottom, " + GOLD + ", #9b59b6, " + ACCENT_BLU + ");");
//
//        // Main content
//        VBox content = new VBox(14);
//        content.setPadding(new Insets(26, 30, 26, 28));
//        content.setAlignment(Pos.TOP_LEFT);
//        HBox.setHgrow(content, Priority.ALWAYS);
//
//        // ── Header ────────────────────────────────────────────────────
//        Label header = new Label("CINEMA PAYMENT");
//        header.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
//        header.setTextFill(Color.web(GOLD));
//
//        Label subHeader = new Label("Secure checkout terminal");
//        subHeader.setFont(Font.font("Verdana", 11));
//        subHeader.setTextFill(Color.web(TEXT_SEC));
//
//        // ── Amount Card ───────────────────────────────────────────────
//        HBox amountCard = new HBox(20);
//        amountCard.setAlignment(Pos.CENTER_LEFT);
//        amountCard.setPadding(new Insets(14, 20, 14, 20));
//        amountCard.setStyle(
//                "-fx-background-color: " + BG_PANEL + ";" +
//                        "-fx-border-color: " + BORDER + ";" +
//                        "-fx-border-radius: 8;" +
//                        "-fx-background-radius: 8;"
//        );
//
//        VBox amountBlock = new VBox(4);
//        Label amountCaption = new Label("AMOUNT DUE");
//        amountCaption.setFont(Font.font("Verdana", 10));
//        amountCaption.setTextFill(Color.web(TEXT_SEC));
//
//        totalLabel = new Label(String.format("$%.2f", totalAmount));
//        totalLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 28));
//        totalLabel.setTextFill(Color.web(TEXT_PRI));
//        amountBlock.getChildren().addAll(amountCaption, totalLabel);
//
//        changeLabel = new Label("");
//        changeLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
//        changeLabel.setTextFill(Color.web(ACCENT_GRN));
//        changeLabel.setVisible(false);
//
//        amountCard.getChildren().addAll(amountBlock, changeLabel);
//
//        // ── Coupon Section ────────────────────────────────────────────
//        VBox couponBox = buildCouponSection();
//
//        // ── Divider ───────────────────────────────────────────────────
//        Separator sep = new Separator();
//        sep.setStyle("-fx-background-color: " + BORDER + ";");
//
//        // ── Payment Type ──────────────────────────────────────────────
//        VBox paymentTypeBox = buildPaymentSection();
//
//        // ── Cash / Card Sections ──────────────────────────────────────
//        cashSection = buildCashSection();
//        cashSection.setVisible(false);
//        cashSection.setManaged(false);
//
//        cardSection = buildCardSection();
//        cardSection.setVisible(false);
//        cardSection.setManaged(false);
//
//        // ── Global Status ─────────────────────────────────────────────
//        statusLabel = new Label("");
//        statusLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
//        statusLabel.setTextFill(Color.web(ACCENT_GRN));
//        statusLabel.setWrapText(true);
//        statusLabel.setMaxWidth(Double.MAX_VALUE);
//        statusLabel.setPadding(new Insets(6, 12, 6, 12));
//
//        content.getChildren().addAll(
//                header, subHeader,
//                amountCard,
//                couponBox,
//                sep,
//                paymentTypeBox,
//                cashSection,
//                cardSection,
//                statusLabel
//        );
//
//        root.getChildren().addAll(stripe, content);
//
//        // Width > Height
//        Scene scene = new Scene(root, 800, 760);
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.show();
//    }
//
//    // ── Coupon Section ────────────────────────────────────────────────
//    private VBox buildCouponSection() {
//        VBox section = new VBox(10);
//        section.setAlignment(Pos.CENTER_LEFT);
//
//        Label title = sectionLabel("COUPON CODE");
//
//        CheckBox hasCoupon = new CheckBox("I have a coupon code");
//        hasCoupon.setTextFill(Color.web(TEXT_SEC));
//        hasCoupon.setFont(Font.font("Verdana", 12));
//
//        HBox inputRow = new HBox(10);
//        inputRow.setAlignment(Pos.CENTER_LEFT);
//        inputRow.setVisible(false);
//        inputRow.setManaged(false);
//
//        TextField couponField = styledTextField("Enter coupon code", 220);
//        Button applyBtn       = accentButton("APPLY", GOLD, BG_DEEP);
//
//        Label couponStatus = new Label("");
//        couponStatus.setFont(Font.font("Verdana", 12));
//        couponStatus.setWrapText(true);
//
//        inputRow.getChildren().addAll(couponField, applyBtn);
//
//        hasCoupon.selectedProperty().addListener((obs, o, selected) -> {
//            inputRow.setVisible(selected);
//            inputRow.setManaged(selected);
//            if (!selected) couponStatus.setText("");
//        });
//
//        applyBtn.setOnAction(e -> {
//            String code = couponField.getText().trim();
//            if (code.isEmpty()) {
//                couponStatus.setText("Please enter a coupon code.");
//                couponStatus.setTextFill(Color.web(ACCENT_RED));
//                return;
//            }
//
//            try {
//                Coupon coupon   = new Coupon(code);
//                double discount = coupon.couponDiscount(code);
//
//                // couponDiscount returns 0 for invalid/unknown codes → reject
//                if (discount <= 0) {
//                    couponStatus.setText("✗  Invalid coupon — nothing was applied.");
//                    couponStatus.setTextFill(Color.web(ACCENT_RED));
//                    return; // totalAmount untouched
//                }
//
//                double saved = totalAmount * (discount / 100.0);
//                totalAmount  = totalAmount - saved;
//
//                totalLabel.setText(String.format("$%.2f", totalAmount));
//                changeLabel.setText(String.format("You saved $%.2f  (%.0f%% off)", saved, discount));
//                changeLabel.setVisible(true);
//
//                couponStatus.setText("✔  Coupon applied!");
//                couponStatus.setTextFill(Color.web(ACCENT_GRN));
//
//                // Lock everything permanently
//                applyBtn.setDisable(true);
//                couponField.setDisable(true);
//                hasCoupon.setDisable(true);
//
//            } catch (Exception ex) {
//                couponStatus.setText("✗  " + ex.getMessage());
//                couponStatus.setTextFill(Color.web(ACCENT_RED));
//                // totalAmount is NOT modified on error
//            }
//        });
//
//        section.getChildren().addAll(title, hasCoupon, inputRow, couponStatus);
//        return section;
//    }
//
//    // ── Payment Type Section ──────────────────────────────────────────
//    private VBox buildPaymentSection() {
//        VBox section = new VBox(10);
//        section.setAlignment(Pos.CENTER_LEFT);
//
//        Label title = sectionLabel("PAYMENT METHOD");
//
//        ToggleGroup group  = new ToggleGroup();
//        RadioButton cashRb = styledRadio("  Cash",        group);
//        RadioButton cardRb = styledRadio("  Credit Card", group);
//
//        HBox row = new HBox(16, cashRb, cardRb);
//        row.setAlignment(Pos.CENTER_LEFT);
//
//        group.selectedToggleProperty().addListener((obs, o, nv) -> {
//            boolean isCash = nv == cashRb;
//            cashSection.setVisible(isCash);
//            cashSection.setManaged(isCash);
//            cardSection.setVisible(!isCash);
//            cardSection.setManaged(!isCash);
//            statusLabel.setText("");
//        });
//
//        section.getChildren().addAll(title, row);
//        return section;
//    }
//
//    // ── Cash Section ──────────────────────────────────────────────────
//    private VBox buildCashSection() {
//        VBox section = styledPanel();
//
//        Label title     = sectionLabel("CASH PAYMENT");
//        TextField field = styledTextField("Amount you are giving ($)", 260);
//
//        Label changeDue = new Label("");
//        changeDue.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
//        changeDue.setTextFill(Color.web(ACCENT_GRN));
//
//        Button payBtn = accentButton("PAY WITH CASH", ACCENT_GRN, BG_DEEP);
//
//        payBtn.setOnAction(e -> {
//            try {
//                double given  = Double.parseDouble(field.getText().trim());
//                double change = given - totalAmount;
//
//                if (change < 0) {
//                    setStatus("Not enough cash. You are short $" + String.format("%.2f", -change), false);
//                    changeDue.setText("");
//                    return;
//                }
//
//                CashPayment processor = new CashPayment();
//                booking.makePayment(processor, totalAmount, given);
//
//                // Show change on the amount card too
//                changeDue.setText(String.format("Change to return: $%.2f", change));
//                setStatus("Cash payment successful!", true);
//
//                payBtn.setDisable(true);
//                field.setDisable(true);
//
//            } catch (NumberFormatException ex) {
//                setStatus("Enter a valid cash amount.", false);
//            } catch (Exception ex) {
//                setStatus(ex.getMessage(), false);
//            }
//        });
//
//        section.getChildren().addAll(title, field, changeDue, payBtn);
//        return section;
//    }
//
//    // ── Card Section ──────────────────────────────────────────────────
//    private VBox buildCardSection() {
//        VBox section = styledPanel();
//
//        Label title = sectionLabel("CREDIT CARD PAYMENT");
//
//        TextField cardNum     = styledTextField("Card number (15–16 digits)", 300);
//        HBox row2 = new HBox(10);
//        TextField cvvField    = styledTextField("CVV", 100);
//        TextField expiryField = styledTextField("Expiry MM/YY", 130);
//        row2.getChildren().addAll(cvvField, expiryField);
//
//        Button payBtn = accentButton("PAY WITH CARD", ACCENT_BLU, BG_DEEP);
//
//        payBtn.setOnAction(e -> {
//            String cardNumber = cardNum.getText().trim();
//            String cvvText    = cvvField.getText().trim();
//            String expiry     = expiryField.getText().trim();
//            try {
//                if (!isCardValid(cardNumber) || cardNumber.length() < 15) {
//                    setStatus("Invalid card number.", false);
//                    return;
//                }
//                int cvv = Integer.parseInt(cvvText);
//                CreditCardPayment processor = new CreditCardPayment(cardNumber, cvv, expiry);
//                booking.makePayment(processor, totalAmount);
//                setStatus("Card payment successful!", true);
//                payBtn.setDisable(true);
//                cardNum.setDisable(true);
//                cvvField.setDisable(true);
//                expiryField.setDisable(true);
//            } catch (NumberFormatException ex) {
//                setStatus("Enter a valid CVV.", false);
//            } catch (Exception ex) {
//                setStatus(ex.getMessage(), false);
//            }
//        });
//
//        section.getChildren().addAll(title, cardNum, row2, payBtn);
//        return section;
//    }
//
//    public static boolean isCardValid(String creditCard) {
//        int sum = 0;
//        boolean alt = false;
//        for (int i = creditCard.length() - 1; i >= 0; i--) {
//            int n = Integer.parseInt(String.valueOf(creditCard.charAt(i)));
//            if (alt) { n *= 2; if (n > 9) n -= 9; }
//            sum += n;
//            alt = !alt;
//        }
//        return (sum % 10 == 0);
//    }
//
//    // ── Helpers ───────────────────────────────────────────────────────
//    private void setStatus(String msg, boolean ok) {
//        statusLabel.setText((ok ? "✔  " : "✗  ") + msg);
//        statusLabel.setTextFill(Color.web(ok ? ACCENT_GRN : ACCENT_RED));
//        statusLabel.setStyle(
//                "-fx-background-color: " + (ok ? "#002a1a" : "#2a0010") + ";" +
//                        "-fx-background-radius: 6;"
//        );
//    }
//
//    private Label sectionLabel(String text) {
//        Label l = new Label(text);
//        l.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
//        l.setTextFill(Color.web(GOLD));
//        return l;
//    }
//
//    private TextField styledTextField(String prompt, double width) {
//        TextField tf = new TextField();
//        tf.setPromptText(prompt);
//        tf.setPrefWidth(width);
//        tf.setFont(Font.font("Verdana", 13));
//        String base =
//                "-fx-background-color: " + BG_FIELD + ";" +
//                        "-fx-text-fill: " + TEXT_PRI + ";" +
//                        "-fx-prompt-text-fill: " + TEXT_SEC + ";" +
//                        "-fx-border-radius: 5;" +
//                        "-fx-background-radius: 5;" +
//                        "-fx-padding: 8 10;";
//        tf.setStyle(base + "-fx-border-color: " + BORDER + ";");
//        tf.focusedProperty().addListener((obs, o, focused) ->
//                tf.setStyle(base + "-fx-border-color: " + (focused ? GOLD : BORDER) + ";")
//        );
//        return tf;
//    }
//
//    private Button accentButton(String text, String bg, String fg) {
//        Button btn = new Button(text);
//        btn.setFont(Font.font("Verdalna", FontWeight.BOLD, 12));
//        String base =
//                "-fx-text-fill: " + fg + ";" +
//                        "-fx-background-radius: 6;" +
//                        "-fx-padding: 9 22;" +
//                        "-fx-cursor: hand;";
//        btn.setStyle("-fx-background-color: " + bg + ";" + base);
//        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: derive(" + bg + ", -18%);" + base));
//        btn.setOnMouseExited(e  -> btn.setStyle("-fx-background-color: " + bg + ";" + base));
//        return btn;
//    }
//
//    private RadioButton styledRadio(String text, ToggleGroup group) {
//        RadioButton rb = new RadioButton(text);
//        rb.setToggleGroup(group);
//        rb.setTextFill(Color.web(TEXT_PRI));
//        rb.setFont(Font.font("Verdanka", 13));
//        return rb;
//    }
//
//    private VBox styledPanel() {
//        VBox box = new VBox(10);
//        box.setAlignment(Pos.CENTER_LEFT);
//        box.setPadding(new Insets(14, 18, 14, 18));
//        box.setStyle(
//                "-fx-background-color: " + BG_PANEL + ";" +
//                        "-fx-border-color: " + BORDER + ";" +
//                        "-fx-border-radius: 8;" +
//                        "-fx-background-radius: 8;"
//        );
//        return box;
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}