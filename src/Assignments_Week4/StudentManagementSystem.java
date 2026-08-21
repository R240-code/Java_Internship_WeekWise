package Assignments_Week4;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class StudentManagementSystem extends JFrame {
    private final JTextField name = input(), email = input(), course = input(), phone = input(), search = input();
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Name", "Email", "Course", "Phone"}, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(model);
    private int selectedId = -1;

    public StudentManagementSystem() {
        setTitle("EduTrack | Student Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1080, 680); setLocationRelativeTo(null);
        add(ui()); load("");
    }

    private JPanel ui() {
        JPanel root = new JPanel(new BorderLayout()); root.setBackground(new Color(244,247,252));
        JPanel head = new JPanel(new BorderLayout()); head.setBackground(new Color(15,35,65));
        head.setBorder(new EmptyBorder(18,35,18,35));
        JLabel title = text("EduTrack  |  Student Management",22,Color.WHITE);
        JLabel sub = text("Persistent records powered by MySQL",13,new Color(205,222,245));
        head.add(title,BorderLayout.WEST); head.add(sub,BorderLayout.EAST); root.add(head,BorderLayout.NORTH);
        JPanel body=new JPanel(new BorderLayout(20,0)); body.setBackground(root.getBackground());
        body.setBorder(new EmptyBorder(25,35,30,35)); body.add(form(),BorderLayout.WEST); body.add(records(),BorderLayout.CENTER);
        root.add(body); return root;
    }

    private JPanel form() {
        JPanel p=card(); p.setPreferredSize(new Dimension(310,0)); p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        p.add(text("Student details",19,new Color(15,35,65))); p.add(Box.createVerticalStrut(7));
        p.add(text("Add a record or select one to update.",12,Color.GRAY)); p.add(Box.createVerticalStrut(20));
        p.add(field("FULL NAME",name)); p.add(Box.createVerticalStrut(10));
        p.add(field("EMAIL ADDRESS",email)); p.add(Box.createVerticalStrut(10));
        p.add(field("COURSE",course)); p.add(Box.createVerticalStrut(10));
        p.add(field("PHONE NUMBER",phone)); p.add(Box.createVerticalStrut(18));
        JPanel b=new JPanel(new GridLayout(2,2,8,8)); b.setOpaque(false);
        JButton add=button("Add Student",new Color(13,148,136)), update=button("Update",new Color(37,99,235));
        JButton delete=button("Delete",new Color(220,38,38)), clear=button("Clear",new Color(100,116,139));
        add.addActionListener(e->add()); update.addActionListener(e->update()); delete.addActionListener(e->delete()); clear.addActionListener(e->clear());
        b.add(add);b.add(update);b.add(delete);b.add(clear);p.add(b);return p;
    }

    private JPanel records() {
        JPanel p=card(); p.setLayout(new BorderLayout(0,15));
        JPanel top=new JPanel(new BorderLayout());top.setOpaque(false);top.add(text("Student records",19,new Color(15,35,65)),BorderLayout.WEST);
        JPanel tools=new JPanel(new FlowLayout(FlowLayout.RIGHT,7,0));tools.setOpaque(false);search.setPreferredSize(new Dimension(220,34));
        JButton go=button("Search",new Color(37,99,235)),refresh=button("Refresh",new Color(100,116,139));
        go.addActionListener(e->load(search.getText().trim()));search.addActionListener(e->load(search.getText().trim()));
        refresh.addActionListener(e->{search.setText("");load("");});tools.add(search);tools.add(go);tools.add(refresh);top.add(tools,BorderLayout.EAST);p.add(top,BorderLayout.NORTH);
        table.setRowHeight(30);table.setSelectionBackground(new Color(219,234,254));table.getSelectionModel().addListSelectionListener(e->select());
        p.add(new JScrollPane(table));return p;
    }

    private void add() { if(!valid())return; run("INSERT INTO students(name,email,course,phone) VALUES(?,?,?,?)",false); }
    private void update() { if(selectedId<0){error("Select a student first.");return;}if(!valid())return;run("UPDATE students SET name=?,email=?,course=?,phone=? WHERE id=?",true); }
    private void delete() {
        if(selectedId<0){error("Select a student first.");return;}
        if(JOptionPane.showConfirmDialog(this,"Delete this student record?","Confirm deletion",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION)return;
        try(Connection c=DatabaseConnection.getConnection(); PreparedStatement ps=c.prepareStatement("DELETE FROM students WHERE id=?")){
            ps.setInt(1,selectedId);ps.executeUpdate();success("Student deleted.");clear();load(search.getText().trim());
        }catch(SQLException e){dbError(e);}
    }
    private void run(String sql,boolean updating) {
        try(Connection c=DatabaseConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,name.getText().trim());ps.setString(2,email.getText().trim());ps.setString(3,course.getText().trim());ps.setString(4,phone.getText().trim());
            if(updating)ps.setInt(5,selectedId);ps.executeUpdate();success(updating?"Student updated.":"Student added.");clear();load(search.getText().trim());
        }catch(SQLIntegrityConstraintViolationException e){error("This email address already exists.");}catch(SQLException e){dbError(e);}
    }
    private void load(String q) {
        model.setRowCount(0);String s="SELECT * FROM students WHERE name LIKE ? OR email LIKE ? OR course LIKE ? OR phone LIKE ? ORDER BY id DESC";
        try(Connection c=DatabaseConnection.getConnection(); PreparedStatement ps=c.prepareStatement(s)){
            for(int i=1;i<=4;i++)ps.setString(i,"%"+q+"%");ResultSet r=ps.executeQuery();
            while(r.next())model.addRow(new Object[]{r.getInt("id"),r.getString("name"),r.getString("email"),r.getString("course"),r.getString("phone")});
        }catch(SQLException e){dbError(e);}
    }
    private void select(){int r=table.getSelectedRow();if(r<0)return;selectedId=(int)model.getValueAt(r,0);name.setText(model.getValueAt(r,1).toString());email.setText(model.getValueAt(r,2).toString());course.setText(model.getValueAt(r,3).toString());phone.setText(model.getValueAt(r,4).toString());}
    private boolean valid(){if(name.getText().trim().isEmpty()||email.getText().trim().isEmpty()||course.getText().trim().isEmpty()||phone.getText().trim().isEmpty()){error("Complete all fields.");return false;}if(!email.getText().trim().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")){error("Enter a valid email.");return false;}return true;}
    private void clear(){selectedId=-1;name.setText("");email.setText("");course.setText("");phone.setText("");table.clearSelection();}
    private JPanel card(){JPanel p=new JPanel();p.setBackground(Color.WHITE);p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(226,232,240)),new EmptyBorder(22,22,22,22)));return p;}
    private JPanel field(String label,JTextField f){JPanel p=new JPanel();p.setOpaque(false);p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));p.add(text(label,11,Color.DARK_GRAY));p.add(Box.createVerticalStrut(4));p.add(f);return p;}
    private JTextField input(){JTextField f=new JTextField();f.setMaximumSize(new Dimension(Integer.MAX_VALUE,36));f.setPreferredSize(new Dimension(240,36));f.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(203,213,225)),new EmptyBorder(6,8,6,8)));return f;}
    private JLabel text(String s,int size,Color c){JLabel l=new JLabel(s);l.setFont(new Font("SansSerif",size>=18?Font.BOLD:Font.PLAIN,size));l.setForeground(c);return l;}
    private JButton button(String s,Color c){JButton b=new JButton(s);b.setBackground(c);b.setForeground(Color.WHITE);b.setFocusPainted(false);b.setBorder(new EmptyBorder(9,12,9,12));return b;}
    private void success(String s){JOptionPane.showMessageDialog(this,s,"EduTrack",JOptionPane.INFORMATION_MESSAGE);}
    private void error(String s){JOptionPane.showMessageDialog(this,s,"EduTrack",JOptionPane.ERROR_MESSAGE);}
    private void dbError(SQLException e){error("Database error: "+e.getMessage()+"\nCheck MySQL is running and DatabaseConnection.java has the correct password.");}
    public static void main(String[] a){SwingUtilities.invokeLater(()->new StudentManagementSystem().setVisible(true));}
}
