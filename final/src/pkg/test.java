package pkg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Base64;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class test {
	
	public static void main(String args[]) throws IOException {

		Scanner scanner = new Scanner(System.in);
		System.out.println("�п�J�H�c�b�� :");
		String user = scanner.nextLine();
		System.out.println("�п�J�H�c�K�X :");
		String password = scanner.nextLine();
		System.out.println("�п�JReal����a�} :");
		String realto = scanner.nextLine();
		System.out.println("�п�J����a�} :");
		String to = scanner.nextLine();
		System.out.println("�п�JReal�H��a�} :");
		String realfrom = scanner.nextLine();
		System.out.println("�п�J�H��a�}:");
//		System.out.println("�п�J�H��a�}(�@��email or random):");
		String from = scanner.nextLine();
		System.out.println("�п�J�^��a�} :");
		String replyto = scanner.nextLine();
		System.out.println("�п�J����� :");
		String receiver = scanner.nextLine();
		System.out.println("�п�J�H���:");
//		System.out.println("�п�J�H���(�@�ӦW�r  or random) :");
		String sender = scanner.nextLine();
		System.out.println("�п�J�^��� :");
		String replyer = scanner.nextLine();
		System.out.println("�п�J�H����D :");
		String subject = scanner.nextLine();
		System.out.println("�п�J�H�󤺮e :(�H**����)");
		String content = scanner.nextLine();
		String str = "";
		while ( !str.equals( "**" ) ) {
		      content += str +"\n";
		      str = scanner.nextLine();
		} // while
		System.out.println("�п�J���ɧ�����|:");
		String filepath = scanner.nextLine();
		System.out.println("�п�J�n�H�X�ʫH:");
		int num = Integer.valueOf(scanner.nextLine());
		File file = new File(filepath);
		String filename = file.getName();
		
		FileReader fr = new FileReader(filepath);
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(fr);
		String strNum ;
		while ((strNum=br.readLine())!=null){
		  sb.append(strNum);
		}
//		System.out.println(sb.toString());
		
		Base64.Decoder decoder = Base64.getDecoder();
		Base64.Encoder encoder = Base64.getEncoder();

		byte[] byteuser = user.getBytes("UTF-8");
		byte[] bytepassword = password.getBytes("UTF-8");
		byte[] bytecontent = content.getBytes("UTF-8");
		String realuser = encoder.encodeToString(byteuser);
		String realpassword = encoder.encodeToString(bytepassword);
		String realcontent = encoder.encodeToString(bytecontent);
		String text = sb.toString();
		byte[] textByte = text.getBytes("UTF-8");
		//�s�X
		String encodedText = encoder.encodeToString(textByte);
//		System.out.println(encodedText);
		//�ѽX
//		System.out.println(new String(decoder.decode(encodedText), "UTF-8"));
		SSLSocketFactory f = (SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket skt = (SSLSocket) f.createSocket("mail.cycu.edu.tw",465);		
		BufferedReader reader = new BufferedReader(new InputStreamReader(skt.getInputStream()));
		String line = reader.readLine();
		System.out.println(line);
		PrintStream printer = new PrintStream(skt.getOutputStream());;
//		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(skt.getOutputStream()));
		
		printer.print("HELO\n");
		System.out.println(reader.readLine());
		printer.print("AUTH LOGIN\n");
		System.out.println(reader.readLine());
		printer.print(realuser+"\n");
		System.out.println(reader.readLine());
		printer.print(realpassword+"\n");
		System.out.println(reader.readLine());
		while(num!=0){
//		  if (sender == "random" || from == "random"){
//			  from = "abc" + num + "@gmail.com" ;
//			  sender = "abc" + num ;
//		  }
		  printer.print("MAIL FROM:" +realfrom+"\n");
		  System.out.println(reader.readLine());
		  printer.print("RCPT TO:" +to+"\n");
		  System.out.println(reader.readLine());
		  printer.print("DATA\n");
		  System.out.println(reader.readLine());
		  printer.print("Content-Type: multipart/mixed;boundary=\"splitter0\""+"\n");
		  printer.print("Reply-To:"+"\""+replyer+"\"<"+replyto+">\n");
		  printer.print("From:"+"\""+sender+"\"<"+from+">\n");
		  printer.print("To:"+"\""+receiver+"\"<"+to+">\n");
		  printer.print("Subject:"+subject+"\n");
		  printer.print("--splitter0\n");
		  printer.print("Content-Type: multipart/alternative;boundary=\"splitter1\""+"\n");
		  printer.print("--splitter1\n");
		  printer.print("Content-Type: text/plain; charset=UTF-8\n");
		  printer.print("Content-Transfer-Encoding: base64\n");
		  printer.print(realcontent+"\n");
		  printer.print("--splitter1--\n");
		  printer.print("--splitter0\n");
		  printer.print("Content-Type: text/plain; charset=UTF-8\n");
		  printer.print("Content-Transfer-Encoding: base64\n");
		  printer.print("Content-Disposition: attachment; filename=\""+filename+"\"\n");
		  printer.print(encodedText+"\n");
		  printer.print("--splitter0--\n");
		  printer.print(".\r\n");
		  System.out.println(reader.readLine());
		  num--;
		}
		printer.print("quit\n");
		System.out.println(reader.readLine());
		
    }
	
}
