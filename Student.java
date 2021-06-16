package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Student extends JFrame {
    private JPanel jPanel;
    private JTextField student_number,name,subjects;
    private JButton jButton;
    private  JTable jTable;
    private  JScrollPane scrollPane;
    DefaultTableModel tableModel;

    public Student(){

        Container cnt = this.getContentPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450,190,1200,600);
        setResizable(false);
        jPanel = new JPanel();
        jPanel.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(jPanel);
        jPanel.setLayout(null);

        JLabel jLabelNewUserReg = new JLabel("Student Data");
        jLabelNewUserReg.setFont(new Font("Times New Roman",Font.PLAIN,33));
        jLabelNewUserReg.setBounds(62,52,325,50);
        jPanel.add(jLabelNewUserReg);

        JLabel jStudentNumber = new JLabel("Reg Number");
        jStudentNumber.setFont(new Font("Tahoma",Font.PLAIN,20));
        jStudentNumber.setBounds(48,152,164,43);
        jPanel.add(jStudentNumber);


        JLabel jname = new JLabel("Name");
        jname.setFont(new Font("Tahoma",Font.PLAIN,20));
        jname.setBounds(48,243,110,29);
        jPanel.add(jname);

        JLabel subjects_json = new JLabel("Marks");
        subjects_json.setFont(new Font("Tahoma",Font.PLAIN,20));
        subjects_json.setBounds(48,350,110,29);
        jPanel.add(subjects_json);

        subjects = new JTextField();
        subjects.setFont(new Font("Tahoma", Font.PLAIN, 12));
        subjects.setBounds(210, 351, 200, 110);
        jPanel.add(subjects);
        subjects.setColumns(10);


        jTable  = new JTable();
        jTable.setBackground(new Color(164,62,72));
        tableModel = new DefaultTableModel();

        scrollPane = new JScrollPane(jTable);
        scrollPane.setBounds(540,156,300,300);
        jPanel.add(scrollPane);

        Object[] column = {"ID","Name","Subject","Marks"};
        final Object[] row = new Object[5];
        tableModel.setColumnIdentifiers(column);
        jTable.setModel(tableModel);

        student_number = new JTextField();
        student_number.setFont(new Font("Tahoma", Font.PLAIN, 32));
        student_number.setBounds(210, 151, 200, 50);
        jPanel.add(student_number);
        student_number.setColumns(10);

        name = new JTextField();
        name.setFont(new Font("Tahoma", Font.PLAIN, 32));
        name.setBounds(210, 235, 200, 50);
        jPanel.add(name);
        name.setColumns(10);

        jButton = new JButton();
        jButton = new JButton("Add");
        jButton.setFont(new Font("Tahoma",Font.PLAIN,22));
        jButton.setBounds(229,467,160,65);
        jPanel.add(jButton);


        try{
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","admin");
            String query = "select * from student";
            Statement sta = connection.createStatement();
            ResultSet rs = sta.executeQuery(query);

            while(rs.next()){
                tableModel.addRow(new Object[]{rs.getString(1), rs.getString(2)});
               // jComboBox.addItem(rs.getString("id"));

            }

            connection.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String s_name = name.getText();
                String s_number = student_number.getText();
                String stringToBeInserted = subjects.toString();


                row[0] = student_number.getText();
                row[1] = name.getText();
                tableModel.addRow(row);
                try{
                    Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","admin");
                    String query = "insert into student(marks) values('"+s_number+"','"+s_name+"','"+stringToBeInserted+"')";
                    Statement sta = connection.createStatement();
                    int x = sta.executeUpdate(query);
                    if (x==0){
                        JOptionPane.showMessageDialog(jButton,"This already exist");

                    } else {
                        JOptionPane.showMessageDialog(jButton,"Welcome," + "Your account is successfully created");

                    }
                    connection.close();
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        });
    }
}
