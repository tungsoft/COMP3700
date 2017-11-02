import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;

public class ProductView extends JFrame {
    private JTextField txtProductID = new JTextField(10);
    private JTextField txtProductName = new JTextField(30);
    private JTextField txtProductPrice = new JTextField(10);
    private JTextField txtProductQuantity = new JTextField(10);

    private JButton btnLoad = new JButton("Load Product");
    private JButton btnSave = new JButton("Save Product");
    private JButton btnExit = new JButton("Exit");


    PrintWriter outStream;
    BufferedReader inStream;
    Socket socket;
    Gson gson = new Gson();

    public ProductView() throws Exception {
        this.setTitle("Product View");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setSize(500, 200);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panelButton = new JPanel();
        panelButton.add(btnLoad);
        panelButton.add(btnSave);
        panelButton.add(btnExit);
        this.getContentPane().add(panelButton);

        JPanel panelProductID = new JPanel();
        panelProductID.add(new JLabel("Product ID: "));
        panelProductID.add(txtProductID);
        txtProductID.setHorizontalAlignment(JTextField.RIGHT);
        this.getContentPane().add(panelProductID);

        JPanel panelProductName = new JPanel();
        panelProductName.add(new JLabel("Product Name: "));
        panelProductName.add(txtProductName);
        this.getContentPane().add(panelProductName);

        JPanel panelProductInfo = new JPanel();
        panelProductInfo.add(new JLabel("Price: "));
        panelProductInfo.add(txtProductPrice);
        txtProductPrice.setHorizontalAlignment(JTextField.RIGHT);

        panelProductInfo.add(new JLabel("Quantity: "));
        panelProductInfo.add(txtProductQuantity);
        txtProductQuantity.setHorizontalAlignment(JTextField.RIGHT);

        this.getContentPane().add(panelProductInfo);

        String serverHostname = new String("127.0.0.1");
        int portNumber = 8888;

        System.out.println("Attemping to connect to host " + serverHostname + " on port " + portNumber);

        socket = new Socket(serverHostname, portNumber);

        outStream = new PrintWriter(socket.getOutputStream(), true);
        inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        this.btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ClientRequest request = new ClientRequest("GET Product", txtProductID.getText());
                    outStream.println(gson.toJson(request));
                    String serverAnswer = inStream.readLine();
                    Product product = gson.fromJson(serverAnswer, Product.class);
                    if (product == null) {
                        JOptionPane.showMessageDialog(null, "No product with this ID exists in database!");
                    } else {
                        txtProductName.setText(product.getName());
                        txtProductPrice.setText(String.valueOf(product.getPrice()));
                        txtProductQuantity.setText(String.valueOf(product.getQuantity()));
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Product product = new Product();
                    product.setProductID(Integer.parseInt(txtProductID.getText()));
                    product.setName(txtProductName.getText());
                    product.setPrice(Double.parseDouble(txtProductPrice.getText()));
                    product.setQuantity(Double.parseDouble(txtProductQuantity.getText()));

                    ClientRequest request = new ClientRequest("PUT Product", gson.toJson(product));
                    outStream.println(gson.toJson(request));

                    String serverAnswer = inStream.readLine();
                    if (serverAnswer.equals("ERROR"))
                        JOptionPane.showMessageDialog(null, "Cannot save this product to the server!");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        this.btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ClientRequest request = new ClientRequest("BYE", "");
                    outStream.println(gson.toJson(request));
                    outStream.close();
                    inStream.close();
                    socket.close();
                    System.exit(0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void setOutStream(PrintWriter outStream) {
        this.outStream = outStream;
    }

    public void setInStream(BufferedReader inStream) {
        this.inStream = inStream;
    }

    public static void main(String[] args) throws Exception {
        ProductView productView = new ProductView();
        productView.setVisible(true);
    }
}
