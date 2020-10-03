package evaluation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import util.DatabaseUtil;

public class EvaluationDAO {

	//�۾��� �Լ�(������ ���� ���)
	public int write(EvaluationDTO evaluationDTO) {
		String SQL = "INSERT INTO EVALUATION VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,evaluationDTO.getUserID().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n","<br>"));
			pstmt.setString(2,evaluationDTO.getLectureName().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n","<br>"));
			pstmt.setString(3,evaluationDTO.getProfessorName().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n","<br>"));
			pstmt.setInt(4,evaluationDTO.getLectureYear()); //���� �̹Ƿ� replaceAll �� �ʿ� ���� (���ڿ� �κи�!)
			pstmt.setString(5,evaluationDTO.getSemesterDivide().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n","<br>"));
			pstmt.setString(6,evaluationDTO.getLectureDivide().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n","<br>"));
			pstmt.setString(7,evaluationDTO.getEvaluationTitle().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n","<br>"));
			pstmt.setString(8,evaluationDTO.getEvaluationContent().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n","<br>"));
			pstmt.setString(9,evaluationDTO.getTotalScore().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n","<br>"));
			pstmt.setString(10,evaluationDTO.getCreditScore().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n","<br>"));
			pstmt.setString(11,evaluationDTO.getComfortableScore().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n","<br>"));
			pstmt.setString(12,evaluationDTO.getLectureScore().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n","<br>"));
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			try {if(conn!=null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try {if(pstmt!=null) pstmt.close();} catch(Exception e) {e.printStackTrace();}
			try {if(rs!=null) rs.close();} catch(Exception e) {e.printStackTrace();}
		}
		return -1; //�����ͺ��̽� ����
	}
	
	//������ �˻����(����ڰ� �˻��� ���뿡 ���� ���(������ ��)�� ����Ʈ�� ��ȯ�ϴ� �Լ�) 
	public ArrayList<EvaluationDTO> getList(String lectureDivide, String searchType, String search, int pageNumber) {
		if (lectureDivide.equals("��ü")) {
			lectureDivide = "";
		}
		ArrayList<EvaluationDTO> evaluationList = null; //������ �� ���� ����Ʈ
		String SQL = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//�ֽż��� ��õ�� ����: SQL���� ORDER BY(���ı���:evaluationID�� ��������, likeCount�� ��������) 
		try {
			if (searchType.equals("�ֽż�")) {
				// LIKE Ư�� ���ڿ��� �����ϴ��� ����� ����ϴ� mysql ����
				// �� �������� 5���� ������ �� ��� (6���� ���������� �ڵ� �ۼ��Ѱ�  ���� �������� �Ѿ����  ����� �ֱ� ����!:index.jsp ����)[5������ ����ϴµ� 6���� �����Ѵٴ°� ������������ �����Ѵٴ� ��]
				SQL = "SELECT * FROM EVALUATION WHERE lectureDivide LIKE ? AND CONCAT(lectureName, professorName, evaluationTitle, evaluationContent) LIKE"
						+ "? ORDER BY evaluationID DESC LIMIT " + pageNumber * 5 + ", " + pageNumber * 5 + 6;
			} else if (searchType.equals("��õ��")) {
				SQL = "SELECT * FROM EVALUATION WHERE lectureDivide LIKE ? AND CONCAT(lectureName, professorName, evaluationTitle, evaluationContent) LIKE"
						+ "? ORDER BY likeCount DESC LIMIT " + pageNumber * 5 + ", " + pageNumber * 5 + 6;
			}
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			
			//LIKE ������ % �ڿ� ���ڿ��� ���� �ش� ���ڿ��� �����ϴ��� ����� ��
			
			//lectureDivide�� ��ü,����,����,��Ÿ�� �־����Ƿ� ��ü�� ���� ""�� �׻� �����ϰ� ����� ���̰�, �������� �ش� ���ڿ� ������ ����� ����ϵ��� ����
			pstmt.setString(1, "%" + lectureDivide + "%");
			//���Ǹ�,������,������,�򰡳����� ������ ���ڿ��� ����ڰ� �˻��ѳ���(search)�� ���ԵǾ� �ִ��� Ȯ�� 
			pstmt.setString(2, "%" + search + "%");
			rs = pstmt.executeQuery();
			evaluationList = new ArrayList<EvaluationDTO>();
			//�Խù��� ������ �� ���� list�� ���
			while (rs.next()) { //����� �������� �� while�� ���� �ۼ�
				EvaluationDTO evaluation  = new EvaluationDTO(
						rs.getInt(1),
						rs.getString(2), 
						rs.getString(3),
						rs.getString(4), 
						rs.getInt(5), 
						rs.getString(6), 
						rs.getString(7), 
						rs.getString(8),
						rs.getString(9), 
						rs.getString(10), 
						rs.getString(11), 
						rs.getString(12), 
						rs.getString(13),
						rs.getInt(14)
				);
				evaluationList.add(evaluation);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {if(conn!=null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try {if(pstmt!=null) pstmt.close();} catch(Exception e) {e.printStackTrace();}
			try {if(rs!=null) rs.close();} catch(Exception e) {e.printStackTrace();}
		}
		return evaluationList;
	}
	
	//Ư�� �����򰡱ۿ� likeCount 1�� ������Ű�� �Լ�
	public int like(String evaluationID) {
		String SQL = "UPDATE EVALUATION SET likeCount = likeCount + 1 WHERE evaluationID =? ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			//evaluationID�� ����(AUTO_INCREMENT) �̹Ƿ� setInt�� ������
			pstmt.setInt(1,Integer.parseInt(evaluationID));
			return pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {if(conn!=null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try {if(pstmt!=null) pstmt.close();} catch(Exception e) {e.printStackTrace();}
			try {if(rs!=null) rs.close();} catch(Exception e) {e.printStackTrace();}
		}
		return -1; //������ ���̽� ���� 	
	}
	
	//Ư�� ������ �� �����ϴ� �Լ�
	public int delete(String evaluationID) {
		String SQL = "DELETE FROM EVALUATION WHERE evaluationID =? ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,Integer.parseInt(evaluationID));
			return pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {if(conn!=null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try {if(pstmt!=null) pstmt.close();} catch(Exception e) {e.printStackTrace();}
			try {if(rs!=null) rs.close();} catch(Exception e) {e.printStackTrace();}
		}
		return -1; //������ ���̽� ���� 	
	}
	
	//Ư���� �����򰡱��� �ۼ��� ����� userID�� �������� �Լ�
	public String getUserID(String evaluationID) {
		String SQL = "SELECT userID FROM EVALUATION WHERE evaluationID =?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,Integer.parseInt(evaluationID));
			rs = pstmt.executeQuery();
			if(rs.next()) { //��� ����(����� 1���� �� if������)
				return rs.getString(1); //userID �� ��ȯ
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//������ �ڿ� ����
			try {if(conn!=null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try {if(pstmt!=null) pstmt.close();} catch(Exception e) {e.printStackTrace();}
			try {if(rs!=null) rs.close();} catch(Exception e) {e.printStackTrace();}
		}
		return null; //userID �� �������� ����
	}	
}
	
	
	
	
	
	
	
	

