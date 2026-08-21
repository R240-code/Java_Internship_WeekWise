package Assignments_Week4;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// A polished file-based ATM desktop application. Start with: java ATMGUI
public class ATMGUI extends JFrame {
    static final Color NAVY = new Color(12,31,58), BLUE = new Color(37,99,235),
            TEAL = new Color(13,148,136), BG = new Color(244,247,252);
    static final String DIR = "data", ACCOUNTS = DIR + File.separator + "accounts.txt";
    static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
    final CardLayout cards = new CardLayout();
    final JPanel pages = new JPanel(cards);
    JTextField accountInput; JPasswordField pinInput; JLabel loginNote, welcome, balance, account;
    JTextArea history; Account user;

    public ATMGUI() {
        setTitle("SecureBank ATM"); setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1040,700); setMinimumSize(new Dimension(880,600)); setLocationRelativeTo(null);
        try { setupFiles(); } catch (IOException e) { error(e.getMessage()); }
        pages.add(loginPage(), "login"); pages.add(dashboard(), "dashboard"); add(pages);
    }

    JPanel loginPage() {
        JPanel root = new JPanel(new GridBagLayout()); root.setBackground(BG);
        JPanel box = new JPanel(new GridBagLayout()); box.setBackground(Color.WHITE);
        box.setPreferredSize(new Dimension(440,500)); box.setBorder(new EmptyBorder(38,46,38,46));
        GridBagConstraints c = new GridBagConstraints(); c.gridx=0; c.fill=GridBagConstraints.HORIZONTAL;
        c.insets=new Insets(7,0,7,0);
        JLabel logo = new JLabel("SB",SwingConstants.CENTER); logo.setOpaque(true); logo.setBackground(BLUE);
        logo.setForeground(Color.WHITE); logo.setFont(new Font("SansSerif",Font.BOLD,25));
        logo.setPreferredSize(new Dimension(58,58)); c.gridy=0; box.add(logo,c);
        JLabel title = label("Welcome to SecureBank",25,NAVY); title.setHorizontalAlignment(SwingConstants.CENTER);
        c.gridy=1; c.insets=new Insets(23,0,1,0); box.add(title,c);
        JLabel sub = label("Sign in to access your account",14,new Color(107,114,128));
        sub.setHorizontalAlignment(SwingConstants.CENTER); c.gridy=2; c.insets=new Insets(2,0,22,0); box.add(sub,c);
        c.gridy=3; c.insets=new Insets(4,0,2,0); box.add(label("ACCOUNT NUMBER",12,new Color(55,65,81)),c);
        accountInput = input(); accountInput.setToolTipText("Example: 1001"); c.gridy=4; c.insets=new Insets(2,0,12,0); box.add(accountInput,c);
        c.gridy=5; c.insets=new Insets(2,0,2,0); box.add(label("4-DIGIT PIN",12,new Color(55,65,81)),c);
        pinInput = new JPasswordField(); styleInput(pinInput); pinInput.setToolTipText("Example: 1234");
        pinInput.addActionListener(e -> login()); c.gridy=6; c.insets=new Insets(2,0,5,0); box.add(pinInput,c);
        loginNote = label(" ",12,new Color(185,28,28)); c.gridy=7; c.insets=new Insets(0,0,5,0); box.add(loginNote,c);
        JButton signIn = button("Sign In",BLUE); signIn.addActionListener(e -> login()); c.gridy=8; c.insets=new Insets(3,0,17,0); box.add(signIn,c);
        JLabel demo=label("<html><center>Demo account: <b>1001</b> &nbsp; PIN: <b>1234</b></center></html>",12,new Color(107,114,128));
        demo.setHorizontalAlignment(SwingConstants.CENTER); c.gridy=9; c.insets=new Insets(0,0,0,0); box.add(demo,c);
        root.add(box); return root;
    }

    JPanel dashboard() {
        JPanel root=new JPanel(new BorderLayout()); root.setBackground(BG); root.add(header(),BorderLayout.NORTH);
        JPanel body=new JPanel(new BorderLayout(22,22)); body.setOpaque(false); body.setBorder(new EmptyBorder(28,42,35,42));
        JPanel top=new JPanel(new GridLayout(1,2,22,0)); top.setOpaque(false); top.add(balanceCard()); top.add(actionCard());
        body.add(top,BorderLayout.NORTH); body.add(historyCard(),BorderLayout.CENTER); root.add(body); return root;
    }

    JPanel header() {
        JPanel p=new JPanel(new BorderLayout()); p.setBackground(NAVY); p.setBorder(new EmptyBorder(18,42,18,42));
        p.add(label("SB  |  SecureBank ATM",21,Color.WHITE),BorderLayout.WEST);
        JPanel right=new JPanel(new FlowLayout(FlowLayout.RIGHT,18,0)); right.setOpaque(false);
        welcome=label("",14,new Color(220,231,249)); JButton out=button("Log out",new Color(42,61,89));
        out.addActionListener(e->logout()); right.add(welcome); right.add(out); p.add(right,BorderLayout.EAST); return p;
    }

    JPanel balanceCard() {
        JPanel p=card(); p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        p.add(label("AVAILABLE BALANCE",12,new Color(107,114,128))); p.add(Box.createVerticalStrut(10));
        balance=label("Rs. 0.00",36,NAVY); p.add(balance); p.add(Box.createVerticalStrut(6));
        account=label("",13,new Color(107,114,128)); p.add(account); p.add(Box.createVerticalGlue());
        p.add(label("Your account is secure and encrypted",13,TEAL)); return p;
    }

    JPanel actionCard() {
        JPanel p=card(); p.setLayout(new BorderLayout(0,18)); p.add(label("Quick actions",19,NAVY),BorderLayout.NORTH);
        JPanel acts=new JPanel(new GridLayout(1,2,13,0)); acts.setOpaque(false);
        JButton dep=action("＋","Deposit",TEAL), wit=action("−","Withdraw",BLUE);
        dep.addActionListener(e->deposit()); wit.addActionListener(e->withdraw()); acts.add(dep); acts.add(wit); p.add(acts); return p;
    }

    JPanel historyCard() {
        JPanel p=card(); p.setLayout(new BorderLayout(0,14)); p.add(label("Transaction activity",19,NAVY),BorderLayout.NORTH);
        history=new JTextArea(); history.setEditable(false); history.setFont(new Font("Monospaced",Font.PLAIN,13));
        history.setForeground(new Color(31,41,55)); history.setBackground(new Color(250,251,253)); history.setBorder(new EmptyBorder(12,12,12,12));
        JScrollPane sc=new JScrollPane(history); sc.setBorder(BorderFactory.createLineBorder(new Color(226,232,240))); p.add(sc); return p;
    }

    void login() {
        try {
            user=find(accountInput.getText().trim(),new String(pinInput.getPassword()).trim());
            if(user==null){ loginNote.setText("Incorrect account number or PIN. Please try again."); pinInput.setText(""); return; }
            log("Signed in securely"); refresh(); loginNote.setText(" "); cards.show(pages,"dashboard");
        } catch(IOException e){ error("Could not sign in: "+e.getMessage()); }
    }
    void deposit(){ change(true); } void withdraw(){ change(false); }
    void change(boolean isDeposit) {
        String answer=JOptionPane.showInputDialog(this,isDeposit?"How much would you like to deposit?":"How much would you like to withdraw?",
                isDeposit?"Deposit money":"Withdraw money",JOptionPane.PLAIN_MESSAGE);
        if(answer==null)return;
        try {
            double sum=Double.parseDouble(answer.trim()); if(sum<=0)throw new NumberFormatException();
            if(!isDeposit && sum>user.balance){ error("Insufficient balance. Available: Rs. "+money(user.balance)); return; }
            user.balance += isDeposit ? sum : -sum; save(); log((isDeposit?"Deposit      +Rs. ":"Withdrawal   -Rs. ")+money(sum)+"    Balance: Rs. "+money(user.balance));
            refresh(); JOptionPane.showMessageDialog(this,isDeposit?"Deposit complete.":"Please collect Rs. "+money(sum)+".",
                    isDeposit?"Deposit complete":"Cash withdrawal approved",JOptionPane.INFORMATION_MESSAGE);
        } catch(NumberFormatException e){error("Enter a valid amount greater than zero.");}
        catch(IOException e){error("Could not save transaction: "+e.getMessage());}
    }
    void refresh() throws IOException {
        welcome.setText("Hello, Account "+user.number); account.setText("Account •••• "+user.number);
        balance.setText("Rs. "+money(user.balance)); history.setText(readHistory()); history.setCaretPosition(0);
    }
    void logout(){ try{if(user!=null)log("Signed out");}catch(IOException ignored){} user=null; accountInput.setText("");pinInput.setText("");cards.show(pages,"login"); }

    void setupFiles() throws IOException {
        File folder=new File(DIR); if(!folder.exists()&&!folder.mkdirs())throw new IOException("Cannot create data folder.");
        File f=new File(ACCOUNTS); if(!f.exists())try(BufferedWriter w=new BufferedWriter(new FileWriter(f))){
            w.write("1001|1234|5000.00");w.newLine();w.write("1002|4321|2500.00");w.newLine();
        }
    }
    Account find(String n,String pin)throws IOException{
        try(BufferedReader r=new BufferedReader(new FileReader(ACCOUNTS))){String line;while((line=r.readLine())!=null){String[] x=line.split("\\|");if(x.length==3&&x[0].equals(n)&&x[1].equals(pin))return new Account(x[0],x[1],Double.parseDouble(x[2]));}}return null;
    }
    void save()throws IOException{
        java.util.List<String> all=new ArrayList<>();try(BufferedReader r=new BufferedReader(new FileReader(ACCOUNTS))){String line;while((line=r.readLine())!=null){String[] x=line.split("\\|");all.add(x[0].equals(user.number)?user.number+"|"+user.pin+"|"+money(user.balance):line);}}
        try(BufferedWriter w=new BufferedWriter(new FileWriter(ACCOUNTS))){for(String s:all){w.write(s);w.newLine();}}
    }
    void log(String event)throws IOException{try(BufferedWriter w=new BufferedWriter(new FileWriter(historyFile(),true))){w.write(LocalDateTime.now().format(DATE)+"  |  "+event);w.newLine();}}
    String readHistory()throws IOException{File f=new File(historyFile());if(!f.exists())return "No transactions yet. Your activity will appear here.";StringBuilder b=new StringBuilder();try(BufferedReader r=new BufferedReader(new FileReader(f))){String s;while((s=r.readLine())!=null)b.append(s).append("\n");}return b.toString();}
    String historyFile(){return DIR+File.separator+"history_"+user.number+".txt";} String money(double n){return String.format("%.2f",n);}

    JPanel card(){JPanel p=new JPanel();p.setBackground(Color.WHITE);p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(226,232,240)),new EmptyBorder(24,26,24,26)));return p;}
    JLabel label(String t,int size,Color color){JLabel l=new JLabel(t);l.setFont(new Font("SansSerif",size>=18?Font.BOLD:Font.PLAIN,size));l.setForeground(color);return l;}
    JTextField input(){JTextField t=new JTextField();styleInput(t);return t;}
    void styleInput(JTextField t){t.setFont(new Font("SansSerif",Font.PLAIN,16));t.setPreferredSize(new Dimension(330,42));t.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(203,213,225)),new EmptyBorder(7,10,7,10)));}
    JButton button(String t,Color c){JButton b=new JButton(t);b.setForeground(Color.WHITE);b.setBackground(c);b.setFont(new Font("SansSerif",Font.BOLD,14));b.setFocusPainted(false);b.setBorder(new EmptyBorder(10,16,10,16));b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));return b;}
    JButton action(String icon,String text,Color c){JButton b=button("<html><center><span style='font-size:24px'>"+icon+"</span><br>"+text+"</center></html>",c);b.setBorder(new EmptyBorder(16,12,16,12));return b;}
    void error(String message){JOptionPane.showMessageDialog(this,message,"SecureBank ATM",JOptionPane.ERROR_MESSAGE);}
    static class Account{String number,pin;double balance;Account(String n,String p,double b){number=n;pin=p;balance=b;}}
    public static void main(String[] args){SwingUtilities.invokeLater(()->new ATMGUI().setVisible(true));}
}

