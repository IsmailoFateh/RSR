
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT_NUMBER = 8080;

    private static List<User> users = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + PORT_NUMBER);
            System.exit(1);
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private User user;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.equals("login")) {
                        out.println("Enter your username:");
                        String username = in.readLine();
                        out.println("Enter your password:");
                        String password = in.readLine();
                        boolean foundUser = false;
                        for (User user : users) {
                            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                                this.user = user;
                                foundUser = true;
                                break;
                            }
                        }
                        if (foundUser) {
                            out.println("Successfully logged in.");
                        } else {
                            out.println("Invalid username or password.");
                        }
                    } else if (inputLine.equals("signup")) {
                        out.println("Enter your username:");
                        String username = in.readLine();
                        out.println("Enter your password:");
                        String password = in.readLine();
                        users.add(new User(username, password));
                        out.println("Successfully signed up.");
                    } else if (inputLine.equals("add") || inputLine.equals("multiply")) {
                        out.println("Enter the number of rows for the first matrix:");
                        int rows1 = Integer.parseInt(in.readLine());
                        out.println("Enter the number of columns for the first matrix:");
                        int columns1 = Integer.parseInt(in.readLine());

                        double[][] matrix1 = new double[rows1][columns1];
                        for (int i = 0; i < rows1; i++) {
                            for (int j = 0; j < columns1; j++) {
                                matrix1[i][j]=Double.parseDouble(in.readLine());
                            }
                            }
                            out.println("Enter the number of rows for the second matrix:");
                            int rows2 = Integer.parseInt(in.readLine());
                            out.println("Enter the number of columns for the second matrix:");
                            int columns2 = Integer.parseInt(in.readLine());
        
                            double[][] matrix2 = new double[rows2][columns2];
                            for (int i = 0; i < rows2; i++) {
                                for (int j = 0; j < columns2; j++) {
                                    matrix2[i][j] = Double.parseDouble(in.readLine());
                                }
                            }
        
                            double[][] result = new double[rows1][columns2];
                            for (int i = 0; i < rows1; i++) {
                                for (int j = 0; j < columns2; j++) {
                                    result[i][j] = 0;
                                    for (int k = 0; k < columns1; k++) {
                                        if (inputLine.equals("add")) {
                                            result[i][j] += matrix1[i][k] + matrix2[k][j];
                                        } else if (inputLine.equals("multiply")) {
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
                        } else if (inputLine.equals("exit")) {
                            break;
                        }
                    }
        
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Error handling client connection.");
                }
            }
        }
        
        private static class User {
            private String username;
            private String password;
        
            public User(String username, String password) {
                this.username = username;
                this.password = password;
            }
        
            public String getUsername() {
                return username;
            }
        
            public String getPassword() {
                return password;
            }
        }
    }
