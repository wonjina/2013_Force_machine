import java.net.*;
import java.awt.*;
import java.io.*;

import javax.swing.JFrame;


public class PosClientRecive{

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub]
		String str;
		ReciveFrame f2 = new ReciveFrame("���ݿ�����");
		
		byte[] by = new byte[65508];// �����Ͱ� ����� ���� ���� //�ִ밪���� �����س�
		DatagramSocket soc = new DatagramSocket(1006);
	
	
		DatagramPacket dp = new DatagramPacket(by,by.length);

		soc.receive(dp);
		soc.close();
		str=new String(dp.getData()).trim();

		System.out.println(str);
		f2.lb.setText(str);

	}

}
class ReciveFrame extends JFrame/* implements ActionListener*/ {

	
		// TODO Auto-generated method stub
	Label lb= new Label("�޴���");
	private GridLayout gl = new GridLayout(1,1,1,1);
	private Font font= new Font("�޸տ�ü", Font.BOLD,40);

	private Toolkit kit = Toolkit.getDefaultToolkit();
	private Image icon=kit.getImage("C:\\Users\\NY\\Desktop\\aa.PNG");
	
	public ReciveFrame(String title){
		super.setSize(1000,300);
		super.setTitle(title);
		Dimension screen2 = Toolkit.getDefaultToolkit().getScreenSize();//����� ȭ��ũ�ⱸ��
		Dimension frm2= super.getSize();//������ ũ�� ����
		int x=(int)(screen2.getWidth()/2-frm2.getWidth()/2);//x��ǥ ����� ȭ�� �߾Ӱ�
		int y=(int)(screen2.getHeight()/2-frm2.getHeight()/2);//y��ǥ ����� ȭ�� �߾Ӱ�
		super.setLocation(x,y);	//�������� ��ġ����
		super.setResizable(false);;//ũ������ ����
	
		 setLayout(null);
		 init1();
		 super.setIconImage(icon);
		super.setVisible(true);	
		
	}
	public void init1(){		//�޴� ��� �г�// ����ư ������ ������ ���� ��������鼭 �����
		Container con1= this.getContentPane();
		con1.setLayout(gl);
		lb.setFont(font);
	
		con1.add(lb);
	}
}
