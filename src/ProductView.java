import javax.swing.*;

public class ProductView extends JFrame{
    private JTextField txtProductID  = new JTextField(10);
    private JTextField txtProductName  = new JTextField(30);
    private JTextField txtProductPrice  = new JTextField(10);
    private JTextField txtProductQuantity  = new JTextField(10);

    private JButton btnLoad = new JButton("Load Product");
    private JButton btnSave = new JButton("Save Product");

    public ProductView() {
        this.setTitle("Product View");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setSize(500, 200);

        JPanel panelButton = new JPanel();
        panelButton.add(btnLoad);
        panelButton.add(btnSave);
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

    }

    public JButton getBtnLoad() {
        return btnLoad;
    }

    public JButton getBtnSave() {
        return btnSave;
    }

    public JTextField getTxtProductID() {
        return txtProductID;
    }

    public JTextField getTxtProductName() {
        return txtProductName;
    }

    public JTextField getTxtProductPrice() {
        return txtProductPrice;
    }

    public JTextField getTxtProductQuantity() {
        return txtProductQuantity;
    }
}
