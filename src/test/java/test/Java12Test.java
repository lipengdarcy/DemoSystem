package test;

/**
 * java 12新特性
 */
public class Java12Test {

	public static void main(String[] args) {
		t1();

	}

	/**
	 * 1.Java中书写多行字符串和特殊转义字符时非常不方便和不直观，Java 12将会引入原生字符串(Raw String Literals)解决这一问题。
	 */
	public static void t1() {
		String html2 = " <html> <body> <p>Hello World.</p> </body> </html> ";
		//String html = ` <html> <body> <p>Hello World.</p> </body> </html> `;
		System.out.println(html2);
		
		//boolean b0 = `\n`.equals("\\n"); 
		//boolean b1 = `\n`.unescape().equals("\n"); 
		//boolean b2 = `\n`.length == 2; 
		//boolean b3 = `\n`.unescape().length == 1;
		
	}

}
