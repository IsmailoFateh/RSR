import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        String hostName = "localhost";
        int portNumber = 8080;

        try (
            Socket socket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);
        ) {
            String userInput;
            String serverResponse;

            while (true) {
                System.out.println("Enter 'login' to log in, or 'signup' to sign up:");
                userInput = scanner.nextLine();
                out.println(userInput);
                serverResponse = in.readLine();
                System.out.println(serverResponse);

                if (serverResponse.equals("Successfully logged in.") || serverResponse.equals("Successfully signed up.")) {
                    break;
                }
            }

            while (true) {
                System.out.println("Enter 'add' to add matrices, or 'multiply' to multiply matrices:");
                userInput = scanner.nextLine();
                out.println(userInput);
                serverResponse = in.readLine();
                System.out.println(serverResponse);

                if (serverResponse.equals("Invalid input.")) {
                    continue;
                }

                System.out.println("Enter the number of rows for the first matrix:");
                int rows1 = scanner.nextInt();
                scanner.nextLine();
                out.println(rows1);
                System.out.println("Enter the number of columns for the first matrix:");
                int columns1 = scanner.nextInt();
                scanner.nextLine();
                out.println(columns1);

                double[][] matrix1 = new double[rows1][columns1];
                for (int i = 0; i < rows1; i++) {
                    for (int j = 0; j < columns1; j++) {
                        System.out.println("Enter the value for row " + (i + 1) + ", column " + (j + 1) + " of matrix 1:");
                        matrix1[i][j] = scanner.nextDouble();
                        scanner.nextLine();
                        out.println(matrix1[i][j]);
                    }
                }

                System.out.println("Enter the number of rows for the second matrix:");
                int rows2 = scanner.nextInt();
                scanner.nextLine();
                out.println(rows2);
                System.out.println("Enter the number of columns for the second matrix:");
                int columns2 = scanner.nextInt();
                scanner.nextLine();
                out.println(columns2);

                double[][] matrix2 = new double[rows2][columns2];
                for (int i = 0; i < rows2; i++) {
                    for (int j = 0; j < columns2; j++) {
                        System.out.println("Enter the value for row " + (i + 1) + ", column " + (j + 1) + " of matrix 2:");
                        matrix2[i][j] = scanner.nextDouble();
                        scanner.nextLine();
                        out.println(matrix2[i][j]);
                    }
                }

                double[][] result = new double[rows1][columns2];
                for (int i = 0; i < rows1; i++) {
                    for (int j = 0; j < columns2; j++) {
                        result[i][j] = 0;
                        for (int k = 0; k < columns1; k++) {
                            if (userInput.equals("add")) {
                                result[i][j] += matrix1[i][k] + matrix2[k][j];
                                } else if (userInput.equals("multiply")) {
                                result[i][j] += matrix1[i][k] * matrix2[k][j];
                                }
                                }
                                out.println(result[i][j]);
                                }
                                }
                                System.out.println("Result:");
                                for (int i = 0; i < rows1; i++) {
                                    for (int j = 0; j < columns2; j++) {
                                        System.out.print(result[i][j] + " ");
                                    }
                                    System.out.println();
                                }
                    
                                System.out.println("Enter 'exit' to exit, or any other key to continue:");
                                userInput = scanner.nextLine();
                                if (userInput.equals("exit")) {
                                    out.println(userInput);
                                    break;
                                }
                            }
                        } catch (IOException e) {
                            System.err.println("Could not connect to the server.");
                            System.exit(1);
                        }
                    }
                }
