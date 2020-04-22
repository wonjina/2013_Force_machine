import java.net.*;
import java.awt.*;
import java.io.*;

import javax.swing.JFrame;


public class PosClientRecive{

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub]
		String str;
		ReciveFrame f2 = new ReciveFrame("현금영수증");
		
		byte[] by = new byte[65508];// 데이터가 저장될 공간 생성 //최대값으로 설정해놈
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
	Label lb= new Label("받는중");
	private GridLayout gl = new GridLayout(1,1,1,1);
	private Font font= new Font("휴먼옛체", Font.BOLD,40);

	private Toolkit kit = Toolkit.getDefaultToolkit();
	private Image icon=kit.getImage("C:\\Users\\NY\\Desktop\\aa.PNG");
	
	public ReciveFrame(String title){
		super.setSize(1000,300);
		super.setTitle(title);
		Dimension screen2 = Toolkit.getDefaultToolkit().getScreenSize();//모니터 화면크기구함
		Dimension frm2= super.getSize();//프레임 크기 구함
		int x=(int)(screen2.getWidth()/2-frm2.getWidth()/2);//x좌표 모니터 화면 중앙값
		int y=(int)(screen2.getHeight()/2-frm2.getHeight()/2);//y좌표 모니터 화면 중앙값
		super.setLocation(x,y);	//프레임을 위치지정
		super.setResizable(false);;//크기조절 여부
	
		 setLayout(null);
		 init1();
		 super.setIconImage(icon);
		super.setVisible(true);	
		
	}
	public void init1(){		//메뉴 목록 패널// 계산버튼 누를시 프레임 새로 만들어지면서 실행됨
		Container con1= this.getContentPane();
		con1.setLayout(gl);
		lb.setFont(font);
	
		con1.add(lb);
	}
}
