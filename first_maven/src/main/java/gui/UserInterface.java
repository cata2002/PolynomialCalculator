package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static business_logi.PolynomialMethods.*;

public class UserInterface extends JFrame {
    private JLabel pol1;
    private JLabel pol2;
    private JLabel title;
    private JTextField fieldP1;
    private JTextField fieldP2;
    private JButton compute;
    private JComboBox operation;


    public UserInterface()  {
        this.setBounds(100,100,400,300);
        this.setTitle("Calculator");
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        title=new JLabel("Polynomial calculator");
        title.setBounds(90,20,220,30);
        title.setFont(new Font("Tahoma",Font.PLAIN,20));
        this.getContentPane().add(title);

        pol1=new JLabel("Polynom 1");
        pol1.setBounds(20,80,120,20);
        pol1.setFont(new Font("Tahoma",Font.PLAIN,16));
        this.getContentPane().add(pol1);

        pol2=new JLabel("Polynom 2");
        pol2.setBounds(20,110,120,20);
        pol2.setFont(new Font("Tahoma",Font.PLAIN,16));
        this.getContentPane().add(pol2);

        fieldP1=new JTextField();
        fieldP1.setBounds(100,81,160,20);
        this.getContentPane().add(fieldP1);

        fieldP2=new JTextField();
        fieldP2.setBounds(100,111,160,20);
        this.getContentPane().add(fieldP2);

        operation=new JComboBox(new String[]{"Add","Subtract","Multiply","Divide","Differentiate","Integrate"});
        operation.setBounds(130,150,80,30);
        this.getContentPane().add(operation);

        compute=new JButton("Compute");
        compute.setBounds(110,200,125,30);
        this.getContentPane().add(compute);

        compute.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String poly1=fieldP1.getText();
                String poly2=fieldP2.getText();
                String operation1=operation.getItemAt(operation.getSelectedIndex()).toString();
                int op=getOpCode(operation1);
                if(!checkValidPoly(poly1)||!checkValidPoly(poly2)) showMessage("Invalid polynomials!");
                else{
                    showMessage(returnResult(poly1,poly2,op));
                }
                refresh();
            }
        });

        this.setVisible(true);
    }
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    public void refresh(){
        fieldP1.setText(null);
        fieldP2.setText(null);
        operation.setSelectedIndex(0);
    }
}
