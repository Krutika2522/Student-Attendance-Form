import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class STAForm extends Frame implements ActionListener
{
	Choice dt1,dt2,dt3,brn;
	Button b,r;
	TextField t1,t2;
	public STAForm()
	{
		dt1 = new Choice();
		dt1.setBounds(170,150,50,25);
		dt1.add("Day");
		for(int i=1;i<=31;i++)
		{
			dt1.add(""+i);
		}
		
		dt2 = new Choice();
		dt2.setBounds(230,150,59,25);
		dt2.add("Month");
		for(int i=1;i<=12;i++)
		{
			dt2.add(""+i);
		}

		dt3 = new Choice();
		dt3.setBounds(300,150,59,25);
		dt3.add("Year");
		for(int i=1;i<=5;i++)
		{
			dt3.add("202"+i);
		}

		brn = new Choice();
		brn.setBounds(170,200,80,25);
		brn.add("Branch");
		brn.add("CO");
		brn.add("EJ");
		brn.add("IS");
		brn.add("IF");

		setTitle("Attendance Form");
		setLayout(null);

		b=new Button("Save Data");
		b.setBounds(200,250,80,25);
		
		r=new Button("Retrive");
		r.setBounds(300,250,80,25);

		t1=new TextField(10);
		t1.setBounds(170,50,200,25);	
		
		t2=new TextField(10);
		t2.setBounds(170,100,200,25);

		add(dt1);
		add(dt2);
		add(dt3);
		add(brn);
		add(t1);
		add(t2);
		add(b);
		add(r);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		b.addActionListener(this);
		r.addActionListener(this);
        repaint();
	}
	public void actionPerformed(ActionEvent ae)
	{
	String s = ae.getActionCommand();
	if(s=="Save Data")
	{
		Frame f=new Frame();
		JOptionPane.showMessageDialog(f,"Your response has been recorded.");
	
		String nm = t1.getText();
		int rn = Integer.parseInt(t2.getText());
		String dt = dt3.getSelectedItem()+"-"+dt2.getSelectedItem()+"-"+dt1.getSelectedItem();
		String ba = brn.getSelectedItem();
		
		try
		{
        		Class.forName("com.mysql.jdbc.Driver");

        		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost/db_poly","root","");

        		String str = "insert into student(sname,rollno,date,branch)" + " values (?, ?, ?, ?)";

       	 		PreparedStatement ps = conn.prepareStatement(str);
        		ps.setString(1,nm);
        		ps.setInt(2,rn);
        		ps.setString(3,dt);
				ps.setString(4,ba);
        		ps.execute();
        		conn.close();
    		}
    		catch(Exception e){ }
	}

	if(s=="Retrive")
	{
		JFrame frame2 = new JFrame("Database Results");
        frame2.setLayout(new FlowLayout());
        frame2.setSize(500, 400);
		frame2.setVisible(true);
 
        DefaultTableModel dtm = new DefaultTableModel();
        JTable table = new JTable(dtm);
        table.setPreferredScrollableViewportSize(new Dimension(460, 300));
        frame2.add(new JScrollPane(table));
        dtm.addColumn("Student");
        dtm.addColumn("Roll NO");
        dtm.addColumn("Date");
		dtm.addColumn("Branch");

        try {
         
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/db_poly","root","");
            Statement s1 = con.createStatement();
            s1.execute("select * from student");
            ResultSet rs = s1.getResultSet();
            while(rs.next())
            {
                String sname = rs.getString(1);
                int rollno = rs.getInt(2);
                String date = rs.getString(3);
				String branch = rs.getString(4);
                dtm.addRow(new Object[]{sname, rollno, date , branch});
			}
			s1.close();
            con.close();
        } 
		catch(Exception e){ }
	}
	}
    public static void main (String[] args) throws Exception
	{
		STAForm af = new STAForm();
		af.setVisible(true);
		af.setSize(470,300);
    }
	public void paint(Graphics g)
	{
		Toolkit t=Toolkit.getDefaultToolkit();

		Image i=t.getImage("img.jpg");  
        g.drawImage(i,0,0,this); 

		Image i1=t.getImage("i1.png");
		g.drawImage(i1,100,50,this); 

		Image i2=t.getImage("i2.png");
		g.drawImage(i2,86,100,this); 

		Image i3=t.getImage("i3.png");
		g.drawImage(i3,104,150,this);
		
		Image i4=t.getImage("i4.png");
		g.drawImage(i4,92,200,this);  
	}
}