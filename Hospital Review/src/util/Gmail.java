//Gmail SMTP �̿��ϱ� ���� ���� ���� �ִ� �κ�
package util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

//�������� �����ִ� Authenticator Ŭ���� ���
public class Gmail extends Authenticator {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			//��������� ������ ������(��, ������(��))�� gmail ���̵�� ��й�ȣ �Է�
			return new PasswordAuthentication("johaein1@gmail.com","jhi852147");
		}
}
/* ���� �Է� ��, ����(Gmail) �������� ���� �� ����(�α��� �� ����) ���� ��
   ���� ������ ������ ���� ��> ���� ������ ���� �� ����� ������� �ٲ� ��� ��
   (��Ŭ������ ���� ����ȯ�濡�� ���������� ������ ����ȯ�� ������ ����ϱ� ����) */