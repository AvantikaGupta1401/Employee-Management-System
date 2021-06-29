/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Employee;


import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.TableCellRenderer;
/**
 *
 * @author jaind
 */
public class All_Emp extends JFrame implements ActionListener{
    
    DefaultTableModel model = new DefaultTableModel();
    Container cnt = this.getContentPane();
    JTable jtbl = new JTable(model);
    conn con = null;
    String str ="";
    ResultSet rs= null;
   JButton b1;
   JLabel id15;
    All_Emp(){
        this.setVisible(true);
        this.setSize(1700,550);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        
        this.setBackground(Color.white);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cnt.setLayout(new FlowLayout(FlowLayout.LEFT));
        model.addColumn("Name");
        model.addColumn("Father's Name");
        model.addColumn("Age");
        model.addColumn("Date of Birth");
        model.addColumn("Address");
        model.addColumn("Mobile no.");
        model.addColumn("Email Address");
        model.addColumn("Education");
        model.addColumn("Post");
        model.addColumn("Aadhar");
        model.addColumn("Employee Id");
        model.addColumn("Update");
        model.addColumn("Remove");
        try{
            con = new conn();
            str = "select * from employee";
            rs= con.s.executeQuery(str);
             while(rs.next()){
                model.addRow(new Object[]{ rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11)});
            }
        }catch(Exception e){
            e.printStackTrace();
        }
       
 jtbl.getColumnModel().getColumn(0).setPreferredWidth(100);
      jtbl.getColumnModel().getColumn(3).setPreferredWidth(100);
        jtbl.getColumnModel().getColumn(11).setCellRenderer(new ButtonRenderer("Update"));;
        jtbl.getColumnModel().getColumn(11).setCellEditor(new ButtonEditor(new JTextField("Update")));
        jtbl.getColumnModel().getColumn(12).setCellRenderer(new ButtonRenderer("Remove"));;
        jtbl.getColumnModel().getColumn(12).setCellEditor(new ButtonEditor(new JTextField("Remove")));
JScrollPane pg = new JScrollPane(jtbl);
        cnt.add(pg);
jtbl.setPreferredScrollableViewportSize(new Dimension(1350,432));
jtbl.setRowHeight(60);
b1=new JButton("Cancel");   
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        b1.setBounds(0,0,150,200);
        this.add(b1);
        b1.addActionListener(this);
        
    }
  class ButtonRenderer extends JButton implements  TableCellRenderer
{
private String lbl;
  //CONSTRUCTOR
  public ButtonRenderer(String lbl1) {
    //SET BUTTON PROPERTIES
    lbl = lbl1;
    setOpaque(true);
  }
     @Override
  public Component getTableCellRendererComponent(JTable table, Object obj,
      boolean selected, boolean focused, int row, int col) {

    //SET PASSED OBJECT AS BUTTON TEXT
      setText(lbl);

    return this;
  }
    }
    
   class ButtonEditor extends DefaultCellEditor
{
  protected JButton btn;
   private String lbl,lbl2;
   private Boolean clicked;

   public ButtonEditor(JTextField txt) {
    super(txt);
    
    btn=new JButton();
    btn.setOpaque(true);

    //WHEN BUTTON IS CLICKED
    btn.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {

        fireEditingStopped();
      }
    });
  }

   //OVERRIDE A COUPLE OF METHODS
   @Override
  public Component getTableCellEditorComponent(JTable table, Object obj,
      boolean selected, int row, int col) {

      //SET TEXT TO BUTTON,SET CLICKED TO TRUE,THEN RETURN THE BTN OBJECT
     lbl="Update";
     lbl2="Remove";
     int index1 = jtbl.getSelectedColumn();
     if(index1==11)
        {
            btn.setText(lbl);
         
        }
        else
        if(index1==12)
        {
            btn.setText(lbl2);
        }
     clicked=true;
    return btn;
  }

  //IF BUTTON CELL VALUE CHNAGES,IF CLICKED THAT IS
   @Override
  public Object getCellEditorValue() {

     if(clicked)
      {
      //SHOW US SOME MESSAGE
          int index= jtbl.getSelectedRow();
          int index1 = jtbl.getSelectedColumn();
          String sh = jtbl.getValueAt(index,10).toString();
        if(index1==11)
        {
            setVisible(false);
        new Update_View(sh);
         
        }
        else
        if(index1==12)
        {
           try{
            conn con = new conn();
                String str = "delete from employee where emp_id = '"+sh+"'";
                con.s.executeUpdate(str);
                JOptionPane.showMessageDialog(null,"deleted successfully");
            setVisible(false);
        new All_Emp();
           }
           catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Exception occured while delting record "+ex);
            }
         
        }
      } 
    //SET IT TO FALSE NOW THAT ITS CLICKED
    clicked=false;
    
         
    return new String(lbl);
    
  }

   @Override
  public boolean stopCellEditing() {

         //SET CLICKED TO FALSE FIRST
      clicked=false;
    return super.stopCellEditing();
  }

   @Override
  protected void fireEditingStopped() {
    // TODO Auto-generated method stub
    super.fireEditingStopped();
  }
}
public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == b1){
            this.setVisible(false);
            new details();
        }
}
    public static void main(String[] args){
        new All_Emp();
    }
    
}
