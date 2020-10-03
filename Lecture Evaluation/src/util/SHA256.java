package util;

import java.security.MessageDigest;

public class SHA256 {


	//�̸��Ͽ� �ؽ� ������ ��(�ؽ���) ���ϴ� �Լ�
	public static String getSHA256(String input){
		StringBuffer result = new StringBuffer();
		try {
		    //������ ����ڰ� �Է��� ���� SHA-256���� �˰��� ������ �� �ֵ��� ����
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			//�ܼ��� SHA-256�� �����ϸ� ��ŷ�� ���� ������ �����Ƿ� �Ϲ�������, salt�� ����(�����ϰ�)
			//salt���� �ڽ��� ���ϴ� ������ �־ ��
			byte[] salt = "Hello! This is Salt.".getBytes();
			digest.reset();
			digest.update(salt); // salt�� ����
			//input(UTF-8)�� �ؽ��� ������ ���� char �迭�� �־��� 
			byte[]chars = digest.digest(input.getBytes("UTF-8"));
			//char �迭�� ���ڿ� ���·� �����
			for(int i= 0; i<chars.length; i++) {
				//����(0xff)�� �ؽ� ������ chars�� �ش��ε����� AND(&)����
				String hex = Integer.toHexString(0xff & chars[i]); 
				//1�ڸ����� 0�� ������ �� 2�ڸ� ���� ������ 16���� ���·� ����
				if(hex.length() == 1) result.append("0");
				result.append(hex);
			}
		} catch(Exception e) {
			e.printStackTrace(); 
		}
		return result.toString(); //�ؽ��� ��ȯ 
	}
}
