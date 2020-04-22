import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.GregorianCalendar;

import javax.swing.*;

public class PosServer {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyFrame f= new MyFrame("����");//������ ����
	
	}
}

class MyFrame extends JFrame implements ActionListener {//ȭ�� ������ �̺�Ʈ���� Ŭ����
	/*���̾�α� ���� ������*/
	private Dialog dlg = new Dialog(this, "���ݿ�����", true);
	private TextField title2= new TextField();//��Ʈ��ȣ �Է��ϴºκ�
	private JButton dlgButton1 = new JButton("Ȯ ��");
	private JButton dlgButton2 = new JButton("�� ��");
	private GridLayout gl=new GridLayout(2, 2,2,2);
	private Label dlgLb2= new Label("��Ʈ��ȣ");
	
	/*�гΰ��� ������*/
	private MyPanel1 jp1= new MyPanel1();
	private MyPanel1 jp2= new MyPanel1(2);//����Ʈ�� ���޴������� ���ݵ� ǥ���ϱ�
	private MyPanel1 jp3= new MyPanel1('a');//���� �����ӿ� �Ⱥ���.�гο��� �ƹ��͵� ����.-�ѱݾ� �߰��ϱ�
	private MyPanel1 jp4= new MyPanel1('a',2);
	private String menu[]={"������", "Ƣ  ��", "��  ��", "��  ��", "��  ��","��  Ƣ","�����","��  ��","�����"};
	private JButton[] jbm=new JButton[9];
	private JButton m_jb1=new JButton("�� ��");
	private JButton m_jb2=new JButton("�� ��");
	
	/*�۾�ü�� �� ���� ������*/
	private Font font1= new Font("�޸տ�ü", Font.BOLD,20);//1��° �гο�(�޴�)
	private Font font2= new Font("", Font.BOLD,20);//2��° ��ο�(���)
	private Font font3= new Font("", Font.BOLD,50);//�ֹ���ϱ۾�ü
	private Font font4= new Font("", Font.BOLD,16);//�ֹ������ �޴��� �۾�ü
	private Font font5= new Font("", Font.BOLD,40);//�ѱݾ� �۾�ü
	private Font font6= new Font("HY�׷���", Font.BOLD,30);//�ѱݾ� �۾�ü
	private Label lb= new Label(" �� �� �� �� ", Label.LEFT);
	private Label lb2= new Label("�� �� ��");
	private Label lb3= new Label("0");
	
	/*����Ʈ ���ú�����*/
	private List menulist1 = new List();//�ֹ��� �޴��̸�
	private List menulist2 = new List();//�ֹ��� �޴����� ����
	
	/*�̺�Ʈ ó���� ���Ǵ� ������*/
	private int count[]=new int[9];	//�ֹ��޴��� ������ ���� ����
	private int price[]={2500,2500,3000,2000,3000,4000,3000,5000,1000};//�޴� ���� �ʱ�ȭ
	
	private PrintWriter writer= null;		//������ ������ ���Ϸ� ���
	private int fileNum=1;					//���ϻ����� �̸��ٲٱ�� - ���߿� ����������� �ٲ���
	private Integer menuPrice= new Integer(0); //�ѱݾ� ǥ���� ����
	/*���� ���ú�����*/
	File f = new File("C:\\Users\\NY\\Desktop\\������Ʈ �����");//���ϰ�δ� ���� �Ľ������� �����Ѱ���
	
	Toolkit kit = Toolkit.getDefaultToolkit();
	Image icon=kit.getImage("C:\\Users\\NY\\Desktop\\aa.PNG");
	
	public MyFrame(String title){	//���� ������
		
		super(title);
		super.setSize(1000,300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();//����� ȭ��ũ�ⱸ��
		Dimension frm= super.getSize();//������ ũ�� ����
		int x=(int)(screen.getWidth()/2-frm.getWidth()/2);//x��ǥ ����� ȭ�� �߾Ӱ�
		int y=(int)(screen.getHeight()/2-frm.getHeight()/2);//y��ǥ ����� ȭ�� �߾Ӱ�
		super.setLocation(x,y);	//�������� ��ġ����
		super.setResizable(false);;//ũ������ ����
		setLayout(null);
		
		
		
		
		super.setIconImage(icon);
	
		this.init1();//ȭ�鱸���ϴ� �޼ҵ�
		
		f.mkdir();//��������� ���� ����
		try {
			Thread.sleep(3000);	//���ϻ����Ҷ����� ��ٸ�
		} catch (InterruptedException e1) {}
		start();	//�̺�Ʈ�� ������ ���� �޼ҵ�
		super.setVisible(true);		//����ȭ�� ���̰� �ϱ�
		

		
	}
	
	public void init1(){		//�޴� ��� �г�// ����ư ������ ������ ���� ��������鼭 �����
		Container con1= this.getContentPane();
		jp1.setBounds(0,0,600,200);
		for(int i=0; i<menu.length;i++){
			jbm[i]=new JButton(menu[i]);	//��ư �ʱ�ȭ
			jbm[i].setFont(font1);
			jp1.add(jbm[i]);	//�гο� ��ư �ޱ�
		}
		con1.add(jp1);
	
		jp2.setBounds(610,0,380,270);
		jp2.setBackground(Color.GRAY);
		m_jb1.setFont(font2);
		m_jb2.setFont(font2);
		lb.setFont(font3);
		menulist1.setFont(font4);
		menulist2.setFont(font4);
		
		jp4.add(m_jb1);
		jp4.add(m_jb2);
		
		
		jp2.add(jp4, BorderLayout.SOUTH);	
		jp2.add(lb,BorderLayout.NORTH);
		jp2.add(menulist1,BorderLayout.CENTER);
		jp2.add(menulist2,BorderLayout.EAST);
		con1.add(jp2);
		lb2.setFont(font5);
		lb3.setFont(font6);
		jp3.setBounds(100, 220, 300, 50);
		jp3.add(lb2, BorderLayout.WEST);
		jp3.add(lb3, BorderLayout.CENTER);
		con1.add(jp3);
		
		dlg.setBounds(900, 350, 300, 100);
		dlg.setLayout(gl);
		dlg.add(dlgLb2);
		dlg.add(title2);
		dlg.add(dlgButton1);
		dlg.add(dlgButton2);
		
		
	
		
	}
	public void start(){//�̺�Ʈ ���� �Լ�
		jbm[0].addActionListener(this);
		jbm[1].addActionListener(this);
		jbm[2].addActionListener(this);
		jbm[3].addActionListener(this);
		jbm[4].addActionListener(this);
		jbm[5].addActionListener(this);
		jbm[6].addActionListener(this);
		jbm[7].addActionListener(this);
		jbm[8].addActionListener(this);
		m_jb1.addActionListener(this);
		dlgButton1.addActionListener(this);
		dlgButton2.addActionListener(this);
		m_jb2.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {//��ư ������ ������ �̺�Ʈ��
		// TODO Auto-generated method stub
	
		String strPrice= null;
		if(e.getSource()==jbm[0]){	//
			
			String str1=new String();
			
			count[0]++;
			
			str1=menu[0];
			
			if(count[0]==1)
			{
			menulist1.add(str1);
			menulist2.add(price[0]+"x"+count[0]);
			}
			
			if(count[0]!=1)
			{
			menulist1.remove(str1);
			menulist2.remove(price[0]+"x"+(count[0]-1));
			menulist1.add(str1);
			menulist2.add(price[0]+"x"+count[0]);
			}
			
			menuPrice+=(price[0]);
			strPrice= new String(""+menuPrice);	//������ ���ڿ��� ����
			lb3.setText(strPrice);//������ ���� ���ڿ��� �󺧿� ����
		}
	
		
		else if (e.getSource()==jbm[1]){// �Ի��ư ������ ����
			String str1=new String();
			count[1]++;
			str1=menu[1];
			if(count[1]==1)
			{
				menulist1.add(str1);
				menulist2.add(price[1]+"x"+count[1]);
				}
				
				if(count[1]!=1)
				{
				menulist1.remove(str1);
				menulist2.remove(price[1]+"x"+(count[1]-1));
				menulist1.add(str1);
				menulist2.add(price[1]+"x"+count[1]);
				}
				
				menuPrice+=(price[1]);
				strPrice= new String(""+menuPrice);	//������ ���ڿ��� ����
				lb3.setText(strPrice);
		}
		else if (e.getSource()==jbm[2]){// �Ի��ư ������ ����
			String str1=new String();
			count[2]++;
			str1=menu[2];
			if(count[2]==1)
			{
				menulist1.add(str1);
				menulist2.add(price[2]+"x"+count[2]);
				}
				
				if(count[2]!=1)
				{
				menulist1.remove(str1);
				menulist2.remove(price[2]+"x"+(count[2]-1));
				menulist1.add(str1);
				menulist2.add(price[2]+"x"+count[2]);
				}
				menuPrice+=(price[2]);//�ѱݾ� ���ϴºκ�
				strPrice= new String(""+menuPrice);	//������ ���ڿ��� ����
				lb3.setText(strPrice);//�ѱݾ� ���Ѱ� �󺧿� ���̱�
		}
		else if (e.getSource()==jbm[3]){// �Ի��ư ������ ����
			String str1=new String();
			count[3]++;
			str1=menu[3];
			if(count[3]==1)
			{
				menulist1.add(str1);
				menulist2.add(price[3]+"x"+count[3]);
				}
				
				if(count[3]!=1)
				{
				menulist1.remove(str1);
				menulist2.remove(price[3]+"x"+(count[3]-1));
				menulist1.add(str1);
				menulist2.add(price[3]+"x"+count[3]);
				}
				menuPrice+=(price[3]);
				strPrice= new String(""+menuPrice);	//������ ���ڿ��� ����
				lb3.setText(strPrice);
		}
		else if (e.getSource()==jbm[4]){// �Ի��ư ������ ����
			String str1=new String();
			count[4]++;
			str1=menu[4];
			if(count[4]==1)
			{
				menulist1.add(str1);
				menulist2.add(price[4]+"x"+count[4]);
				}
				
				if(count[4]!=1)
				{
				menulist1.remove(str1);
				menulist2.remove(price[4]+"x"+(count[4]-1));
				menulist1.add(str1);
				menulist2.add(price[4]+"x"+count[4]);
				}
				menuPrice+=(price[4]);
				strPrice= new String(""+menuPrice);	//������ ���ڿ��� ����
				lb3.setText(strPrice);
		}
		else if (e.getSource()==jbm[5]){// �Ի��ư ������ ����
			String str1=new String();
			count[5]++;
			str1=menu[5];
			if(count[5]==1)
			{
				menulist1.add(str1);
				menulist2.add(price[5]+"x"+count[5]);
				}
				
				if(count[5]!=1)
				{
				menulist1.remove(str1);
				menulist2.remove(price[5]+"x"+(count[5]-1));
				menulist1.add(str1);
				menulist2.add(price[5]+"x"+count[5]);
				}
				menuPrice+=(price[5]);
				strPrice= new String(""+menuPrice);	//������ ���ڿ��� ����
				lb3.setText(strPrice);
		}
		else if (e.getSource()==jbm[6]){// �Ի��ư ������ ����
			String str1=new String();
			count[6]++;
			str1=menu[6];
			if(count[6]==1)
			{
				menulist1.add(str1);
				menulist2.add(price[6]+"x"+count[6]);
				}
				
				if(count[6]!=1)
				{
				menulist1.remove(str1);
				menulist2.remove(price[6]+"x"+(count[6]-1));
				menulist1.add(str1);
				menulist2.add(price[6]+"x"+count[6]);
				}
				menuPrice+=(price[6]);
				strPrice= new String(""+menuPrice);	//������ ���ڿ��� ����
				lb3.setText(strPrice);
		}
		else if (e.getSource()==jbm[7]){// �Ի��ư ������ ����
			String str1=new String();
			count[7]++;
			str1=menu[7];
			if(count[7]==1)
			{
				menulist1.add(str1);
				menulist2.add(price[7]+"x"+count[7]);
				}
				
				if(count[7]!=1)
				{
				menulist1.remove(str1);
				menulist2.remove(price[7]+"x"+(count[7]-1));
				menulist1.add(str1);
				menulist2.add(price[7]+"x"+count[7]);
				}
				menuPrice+=(price[7]);
				strPrice= new String(""+menuPrice);	//������ ���ڿ��� ����
				lb3.setText(strPrice);
		}
		else if (e.getSource()==jbm[8]){// �Ի��ư ������ ����
			String str1=new String();
			
			count[8]++;
			str1=menu[8];
			
			if(count[8]==1)
			{
				menulist1.add(str1);
				menulist2.add(price[8]+"x"+count[8]);
				}
				
				if(count[8]!=1)
				{
				menulist1.remove(str1);
				menulist2.remove(price[8]+"x"+(count[8]-1));
				menulist1.add(str1);
				menulist2.add(price[8]+"x"+count[8]);
				}
				menuPrice+=(price[8]);
				strPrice= new String(""+menuPrice);	//������ ���ڿ��� ����
				lb3.setText(strPrice);
		}

		else if (e.getSource()==m_jb2)
		{//��� ��ư������ ��� ����.
			menulist1.removeAll();
			menulist2.removeAll();
			for(int i296=0;i296<9;i296++)
			{
				count[i296]=0;//���� �ʱ�ȭ
			}
			menuPrice=0;	//�ѱݾ� �ʱ�ȭ
			lb3.setText("0");//ȭ�鿡 �ݾ׵� �ʱ�ȭ
		}
		
		else if (e.getSource()==m_jb1){	//����ϱ� ��ư �̺�Ʈ
	
			String[] strList1= new String[menulist1.getItemCount()];//����Ʈ ��ϸ�ŭ ����
			strList1 = menulist1.getItems();//����Ʈ ��� �Է�
			String[] strList2= new String[menulist2.getItemCount()];//����Ʈ ��ϸ�ŭ ����
			strList2 = menulist2.getItems();//����Ʈ ��� �Է�
		
			String fileName= new String(fileNum+"��° ������"+ ".txt");//���� �̸� ����
			File f1= new File(f,fileName);//���ϻ���
			
			if(!f1.exists()){//������ �������� Ȯ�� ���ٸ� exists()=�����ϸ� TRUE����
				try{	
					f1.createNewFile();	//���ϻ���
				}catch(IOException io){}
			}
			else{
				f1.delete();	//���� �����̸� �������մٸ� ����
				try 
				{
					f1.createNewFile();	//������ ���� ����
				}
				catch (IOException e1) {}
			}
			
			try {
				FileWriter fw= new FileWriter(f1);
				BufferedWriter bw = new BufferedWriter(fw, 1024);
				writer = new PrintWriter(bw);
			} catch (IOException e1) {
				System.out.println("������ �����ϴ�.");
			}

			writer.println("���� ����");
			writer.println("--�̸�-----����");
			for(int i305=0; i305<strList1.length;i305++)
			{
					writer.printf("%s     %s ",strList1[i305],strList2[i305]);
					writer.println();
			}
			writer.println("�ѱ��ž� : "+menuPrice);
			writer.printf("������ : %1$tY�� %1$tm�� %1$td��", new GregorianCalendar());	//������
			writer.println();	
			
			try
			{
				writer.close();//���ϴݱ�
				
			}
			catch(Exception ec){}
			
			menulist1.removeAll();//����Ʈ ����
			menulist2.removeAll();
			
			for(int i296=0;i296<9;i296++)
			{
				count[i296]=0;//���� �ʱ�ȭ
			}
				
			menuPrice=0;	//�ѱݾ� �ʱ�ȭ
			lb3.setText("0");//ȭ�鿡 �ݾ׵� �ʱ�ȭ
			dlg.setVisible(true);//���ݿ����� ���̾�α� ���			
		}

		
		else if (e.getSource()==dlgButton1)//���̾�α� Ȯ�ι�ư������ �̺�Ʈ
		{
		//���̾�α� �̺�Ʈ ������ ����� �κ�
		String str2 = title2.getText().trim();//�ؽ�Ʈ�ʵ尪�� �޾ƿ�-��Ʈ��ȣ
		title2.setEchoChar((char)0);//��������--�Ƹ� ��������� �����Ѵٴ°Ű���.
		int fortNum = Integer.parseInt(str2);//��Ʈ��ȣ�� stringŸ���̿��� int������ �ٲٴ� �κ�

		SendThead thead = new SendThead(str2, fortNum);
		thead.start();
		
		dlg.dispose();//���̾�α� �ݴ� �κ�
		
	
		
		fileNum++;//�����̸��ڿ� �ٴ� ���� ����.
			
		
		}//Ȯ�ι�ư �̺�Ʈ ��
		
		else if(e.getSource()==dlgButton2)//���̾�α� �ƴϿ� ��ư ������
		{
			dlg.dispose();//���̾�α� �ݴ� �κ�
			
			fileNum++;//�����̸��ڿ� �ٴ� ���� ����.
			
		}
		
	}//�̺�Ʈ ��ü
	
}//����Ŭ������ü

class MyPanel1 extends JPanel{//�г� Ŭ����
	

	public MyPanel1(){	
		setLayout(new GridLayout(3, 3,2,2));
		}
	public MyPanel1(int i){
		setLayout(new BorderLayout());
	}
	public MyPanel1(char a){
		setLayout(new BorderLayout());
	}
	public MyPanel1(char a, int b){	//��� ��� ��ư �κ�
		setLayout(new GridLayout(1,2,2,2));
	}
}
class SendThead extends Thread{
	String str;//ip�ּ� ���� ����
	int fortNum;//��Ʈ��ȣ ���� ���� /* = Integer.parseInt(str2);*/
	SendThead(String iP,int num){
		str=iP;
		fortNum=num;
	}
	
	public void run(){

		//������ ������ ����
		InetAddress ia = null;
		String data = new String("���ݿ����� �߱޵Ǿ����ϴ�.");
		
			
		try {
			ia=InetAddress.getByName("127.0.0.1");// �ϴ� ������ �����°ɷ� ��.
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
	
		DatagramSocket soc=null;
	
			
				try {
					soc = new DatagramSocket();
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	
		
		DatagramPacket dp = new DatagramPacket(data.getBytes(),
							data.getBytes().length,ia, fortNum);
		
		
				try {
					soc.send(dp);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		soc.close();
		
		
	}
}
