import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckoutController implements ActionListener {
    private CheckoutScreen view;
    private DataAdapter dataAdapter; // to save and load product
    private Order order = null;

    public CheckoutController(CheckoutScreen view, DataAdapter dataAdapter) {
        this.dataAdapter = dataAdapter;
        this.view = view;

        view.getBtnAdd().addActionListener(this);
        view.getBtnPay().addActionListener(this);

        order = new Order();

    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getBtnAdd())
            addProduct();
        else
        if (e.getSource() == view.getBtnPay())
            saveOrder();
    }

    private void saveOrder() {
        JOptionPane.showMessageDialog(null, "This function is being implemented!");
    }

    private void addProduct() {
        String id = JOptionPane.showInputDialog("Enter ProductID: ");
        Product product = dataAdapter.loadProduct(Integer.parseInt(id));
        if (product == null) {
            JOptionPane.showMessageDialog(null, "This product does not exist!");
            return;
        }

        double quantity = Double.parseDouble(JOptionPane.showInputDialog(null,"Enter quantity: "));

        if (quantity < 0 || quantity > product.getQuantity()) {
            JOptionPane.showMessageDialog(null, "This quantity is not valid!");
            return;
        }

        OrderLine line = new OrderLine();
        line.setOrderID(this.order.getOrderID());
        line.setProductID(product.getProductID());
        line.setQuantity(quantity);
        line.setCost(quantity * product.getPrice());

        product.setQuantity(product.getQuantity() - quantity); // update new quantity!!
        dataAdapter.saveProduct(product); // and store this product back right away!!!

        order.getLines().add(line);
        order.setTotalCost(order.getTotalCost() + line.getCost());

        Object[] row = new Object[5];
        row[0] = line.getProductID();
        row[1] = product.getName();
        row[2] = product.getPrice();
        row[3] = line.getQuantity();
        row[4] = line.getCost();

        this.view.addRow(row);
        this.view.getLabTotal().setText("Total: $" + order.getTotalCost());
        this.view.invalidate();
    }

}