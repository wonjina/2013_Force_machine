import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.GregorianCalendar;

import javax.swing.*;

public class PosServer {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyFrame f= new MyFrame("계산기");//프레임 생성
	
	}
}

class MyFrame extends JFrame implements ActionListener {//화면 구성과 이벤트관련 클래스
	/*다이얼로그 관련 변수들*/
	private Dialog dlg = new Dialog(this, "현금영수증", true);
	private TextField title2= new TextField();//포트번호 입력하는부분
	private JButton dlgButton1 = new JButton("확 인");
	private JButton dlgButton2 = new JButton("취 소");
	private GridLayout gl=new GridLayout(2, 2,2,2);
	private Label dlgLb2= new Label("포트번호");
	
	/*패널관련 변수들*/
	private MyPanel1 jp1= new MyPanel1();
	private MyPanel1 jp2= new MyPanel1(2);//리스트에 각메뉴종류별 가격도 표시하기
	private MyPanel1 jp3= new MyPanel1('a');//현재 프레임에 안붙임.패널에도 아무것도 없음.-총금액 추가하기
	private MyPanel1 jp4= new MyPanel1('a',2);
	private String menu[]={"떡복이", "튀  김", "순  대", "어  묵", "유  부","프  튀","수제어묵","범  벅","음료수"};
	private JButton[] jbm=new JButton[9];
	private JButton m_jb1=new JButton("계 산");
	private JButton m_jb2=new JButton("취 소");
	
	/*글씨체과 라벨 관련 변수들*/
	private Font font1= new Font("휴먼옛체", Font.BOLD,20);//1번째 패널용(메뉴)
	private Font font2= new Font("", Font.BOLD,20);//2번째 페널용(계산)
	private Font font3= new Font("", Font.BOLD,50);//주문목록글씨체
	private Font font4= new Font("", Font.BOLD,16);//주문목록의 메뉴들 글씨체
	private Font font5= new Font("", Font.BOLD,40);//총금액 글씨체
	private Font font6= new Font("HY그래픽", Font.BOLD,30);//총금액 글씨체
	private Label lb= new Label(" 주 문 목 록 ", Label.LEFT);
	private Label lb2= new Label("총 금 액");
	private Label lb3= new Label("0");
	
	/*리스트 관련변수들*/
	private List menulist1 = new List();//주문한 메뉴이름
	private List menulist2 = new List();//주문한 메뉴들의 가격
	
	/*이벤트 처리시 사용되는 변수들*/
	private int count[]=new int[9];	//주문메뉴의 종류별 갯수 저장
	private int price[]={2500,2500,3000,2000,3000,4000,3000,5000,1000};//메뉴 가격 초기화
	
	private PrintWriter writer= null;		//데이터 포맷후 파일로 출력
	private int fileNum=1;					//파일생성시 이름바꾸기용 - 나중에 좋은방법으로 바꾸자
	private Integer menuPrice= new Integer(0); //총금액 표시할 변수
	/*파일 관련변수들*/
	File f = new File("C:\\Users\\NY\\Desktop\\프로젝트 가계부");//파일경로는 산기대 컴시점으로 지정한거임
	
	Toolkit kit = Toolkit.getDefaultToolkit();
	Image icon=kit.getImage("C:\\Users\\NY\\Desktop\\aa.PNG");
	
	public MyFrame(String title){	//계산기 프레임
		
		super(title);
		super.setSize(1000,300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();//모니터 화면크기구함
		Dimension frm= super.getSize();//프레임 크기 구함
		int x=(int)(screen.getWidth()/2-frm.getWidth()/2);//x좌표 모니터 화면 중앙값
		int y=(int)(screen.getHeight()/2-frm.getHeight()/2);//y좌표 모니터 화면 중앙값
		super.setLocation(x,y);	//프레임을 위치지정
		super.setResizable(false);;//크기조절 여부
		setLayout(null);
		
		
		
		
		super.setIconImage(icon);
	
		this.init1();//화면구성하는 메소드
		
		f.mkdir();//경로지정한 파일 생성
		try {
			Thread.sleep(3000);	//파일생성할때까지 기다림
		} catch (InterruptedException e1) {}
		start();	//이벤트나 쓰레드 관련 메소드
		super.setVisible(true);		//실행화면 보이게 하기
		

		
	}
	
	public void init1(){		//메뉴 목록 패널// 계산버튼 누를시 프레임 새로 만들어지면서 실행됨
		Container con1= this.getContentPane();
		jp1.setBounds(0,0,600,200);
		for(int i=0; i<menu.length;i++){
			jbm[i]=new JButton(menu[i]);	//버튼 초기화
			jbm[i].setFont(font1);
			jp1.add(jbm[i]);	//패널에 버튼 달기
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
	public void start(){//이벤트 관련 함수
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
	public void actionPerformed(ActionEvent e) {//버튼 누를때 실행할 이벤트들
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
			strPrice= new String(""+menuPrice);	//가격을 문자열에 저장
			lb3.setText(strPrice);//가격을 넣은 문자열을 라벨에 부착
		}
	
		
		else if (e.getSource()==jbm[1]){// 게산버튼 누를시 실행
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
				strPrice= new String(""+menuPrice);	//가격을 문자열에 저장
				lb3.setText(strPrice);
		}
		else if (e.getSource()==jbm[2]){// 게산버튼 누를시 실행
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
				menuPrice+=(price[2]);//총금액 더하는부분
				strPrice= new String(""+menuPrice);	//가격을 문자열에 저장
				lb3.setText(strPrice);//총금액 구한걸 라벨에 붙이기
		}
		else if (e.getSource()==jbm[3]){// 게산버튼 누를시 실행
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
				strPrice= new String(""+menuPrice);	//가격을 문자열에 저장
				lb3.setText(strPrice);
		}
		else if (e.getSource()==jbm[4]){// 게산버튼 누를시 실행
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
				strPrice= new String(""+menuPrice);	//가격을 문자열에 저장
				lb3.setText(strPrice);
		}
		else if (e.getSource()==jbm[5]){// 게산버튼 누를시 실행
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
				strPrice= new String(""+menuPrice);	//가격을 문자열에 저장
				lb3.setText(strPrice);
		}
		else if (e.getSource()==jbm[6]){// 게산버튼 누를시 실행
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
				strPrice= new String(""+menuPrice);	//가격을 문자열에 저장
				lb3.setText(strPrice);
		}
		else if (e.getSource()==jbm[7]){// 게산버튼 누를시 실행
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
				strPrice= new String(""+menuPrice);	//가격을 문자열에 저장
				lb3.setText(strPrice);
		}
		else if (e.getSource()==jbm[8]){// 게산버튼 누를시 실행
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
				strPrice= new String(""+menuPrice);	//가격을 문자열에 저장
				lb3.setText(strPrice);
		}

		else if (e.getSource()==m_jb2)
		{//취소 버튼누르면 모드 삭제.
			menulist1.removeAll();
			menulist2.removeAll();
			for(int i296=0;i296<9;i296++)
			{
				count[i296]=0;//갯수 초기화
			}
			menuPrice=0;	//총금액 초기화
			lb3.setText("0");//화면에 금액도 초기화
		}
		
		else if (e.getSource()==m_jb1){	//계산하기 버튼 이벤트
	
			String[] strList1= new String[menulist1.getItemCount()];//리스트 목록만큼 생성
			strList1 = menulist1.getItems();//리스트 목록 입력
			String[] strList2= new String[menulist2.getItemCount()];//리스트 목록만큼 생성
			strList2 = menulist2.getItems();//리스트 목록 입력
		
			String fileName= new String(fileNum+"번째 구매자"+ ".txt");//파일 이름 저장
			File f1= new File(f,fileName);//파일생성
			
			if(!f1.exists()){//파일이 존재유무 확인 없다면 exists()=존재하면 TRUE리턴
				try{	
					f1.createNewFile();	//파일생성
				}catch(IOException io){}
			}
			else{
				f1.delete();	//만약 같은이름 파일이잇다면 삭제
				try 
				{
					f1.createNewFile();	//삭제후 새로 생성
				}
				catch (IOException e1) {}
			}
			
			try {
				FileWriter fw= new FileWriter(f1);
				BufferedWriter bw = new BufferedWriter(fw, 1024);
				writer = new PrintWriter(bw);
			} catch (IOException e1) {
				System.out.println("파일이 없습니다.");
			}

			writer.println("구매 내역");
			writer.println("--이름-----가격");
			for(int i305=0; i305<strList1.length;i305++)
			{
					writer.printf("%s     %s ",strList1[i305],strList2[i305]);
					writer.println();
			}
			writer.println("총구매액 : "+menuPrice);
			writer.printf("구매일 : %1$tY년 %1$tm월 %1$td일", new GregorianCalendar());	//구매일
			writer.println();	
			
			try
			{
				writer.close();//파일닫기
				
			}
			catch(Exception ec){}
			
			menulist1.removeAll();//리스트 삭제
			menulist2.removeAll();
			
			for(int i296=0;i296<9;i296++)
			{
				count[i296]=0;//갯수 초기화
			}
				
			menuPrice=0;	//총금액 초기화
			lb3.setText("0");//화면에 금액도 초기화
			dlg.setVisible(true);//현금영수증 다이어로그 출력			
		}

		
		else if (e.getSource()==dlgButton1)//다이어로그 확인버튼누를시 이벤트
		{
		//다이어로그 이벤트 실행후 실행될 부분
		String str2 = title2.getText().trim();//텍스트필드값을 받아옴-포트번호
		title2.setEchoChar((char)0);//에코제거--아마 비공개글을 제거한다는거같음.
		int fortNum = Integer.parseInt(str2);//포트번호가 string타입이여서 int형으로 바꾸는 부분

		SendThead thead = new SendThead(str2, fortNum);
		thead.start();
		
		dlg.dispose();//다이얼로그 닫는 부분
		
	
		
		fileNum++;//파일이름뒤에 붙는 숫자 변경.
			
		
		}//확인버튼 이벤트 끝
		
		else if(e.getSource()==dlgButton2)//다이어로그 아니오 버튼 누를시
		{
			dlg.dispose();//다이얼로그 닫는 부분
			
			fileNum++;//파일이름뒤에 붙는 숫자 변경.
			
		}
		
	}//이벤트 몸체
	
}//메인클래스몸체

class MyPanel1 extends JPanel{//패널 클래스
	

	public MyPanel1(){	
		setLayout(new GridLayout(3, 3,2,2));
		}
	public MyPanel1(int i){
		setLayout(new BorderLayout());
	}
	public MyPanel1(char a){
		setLayout(new BorderLayout());
	}
	public MyPanel1(char a, int b){	//취소 계산 버튼 부분
		setLayout(new GridLayout(1,2,2,2));
	}
}
class SendThead extends Thread{
	String str;//ip주소 받을 변수
	int fortNum;//포트번호 받을 변수 /* = Integer.parseInt(str2);*/
	SendThead(String iP,int num){
		str=iP;
		fortNum=num;
	}
	
	public void run(){

		//전송할 데이터 저장
		InetAddress ia = null;
		String data = new String("현금영수증 발급되었습니다.");
		
			
		try {
			ia=InetAddress.getByName("127.0.0.1");// 일단 나에게 보내는걸로 함.
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
