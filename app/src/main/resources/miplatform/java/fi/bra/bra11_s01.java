
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra11_s01
{
    public bra11_s01()
    {
    }
    //##**$$gibu_end_regist
    /* * ���α׷��� : bra11_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/09/14
    * ����    : ������ �� �Ա� ���������� �����Ѵ�.
    ����: ���� �����θ� ��밡���ϴ�.
    Input
    ACT_FLAG (�׼� �÷���)
    BRAN_CD (���� �ڵ�)
    END_DAY (������)
    END_MON (���� ��)
    END_YEAR (���� �⵵)
    OLD_END_DAY (���� ������)
    YEAR (�˻��⵵)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLgibu_end_regist(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
            Conn.setAutoCommit(false);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLgibu_end_regist_MPD27(Conn, dobj);           //  �������� �б�
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLgibu_end_regist_SEL11(Conn, dobj);           //  �������� ����ȸ
            Conn.commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
                Conn.rollback();
            }
            catch(Exception re)
            {
                re.printStackTrace();
            }
        }
        finally
        {
            try
            {
                Conn.close();
            }
            catch(SQLException e)
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e,"DataBase Close Error");
                e.printStackTrace();
            }
        }
        return dobj;
    }
    public DOBJ CTLgibu_end_regist( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLgibu_end_regist_MPD27(Conn, dobj);           //  �������� �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLgibu_end_regist_SEL11(Conn, dobj);           //  �������� ����ȸ
        return dobj;
    }
    // �������� �б�
    public DOBJ CALLgibu_end_regist_MPD27(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD27");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( dvobj.getRecord().get("ACT_FLAG").equals("Insert"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLgibu_end_regist_INS7(Conn, dobj);           //  ���������Է�
            }
            else if( dvobj.getRecord().get("ACT_FLAG").equals("Update"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLgibu_end_regist_XIUD10(Conn, dobj);           //  ���� �� ���
                dobj  = CALLgibu_end_regist_XIUD12(Conn, dobj);           //  ������������
            }
            else if( dvobj.getRecord().get("ACT_FLAG").equals("Delete"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLgibu_end_regist_XIUD11(Conn, dobj);           //  ���� �� ���
                dobj  = CALLgibu_end_regist_DEL8(Conn, dobj);           //  ������������
            }
        }
        return dobj;
    }
    // ���������Է�
    // ����,�⵵�� �ش��ϴ� ���������Է�
    public DOBJ CALLgibu_end_regist_INS7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLgibu_end_regist_INS7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS7") ;
        rvobj.Println("INS7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgibu_end_regist_INS7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   END_DAY = dvobj.getRecord().get("END_DAY");   //������
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   END_YEAR = dvobj.getRecord().get("END_YEAR");   //���� �⵵
        String   END_MON = dvobj.getRecord().get("END_MON");   //���� ��
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //�����ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BRANEND (INS_DATE, INSPRES_ID, END_MON, END_YEAR, BRAN_CD, END_DAY)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :END_MON , :END_YEAR , :BRAN_CD , :END_DAY )";
        sobj.setSql(query);
        sobj.setString("END_DAY", END_DAY);               //������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("END_YEAR", END_YEAR);               //���� �⵵
        sobj.setString("END_MON", END_MON);               //���� ��
        sobj.setString("INSPRES_ID", INSPRES_ID);               //�����ID
        return sobj;
    }
    // ���� �� ���
    public DOBJ CALLgibu_end_regist_XIUD11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD11");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLgibu_end_regist_XIUD11(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgibu_end_regist_XIUD11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   END_MON = dobj.getRetObject("R").getRecord().get("END_MON");   //���� ��
        String   END_YEAR = dobj.getRetObject("R").getRecord().get("END_YEAR");   //���� �⵵
        String   PROCPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //ó���� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_BRANEND_HISTORY (BRAN_CD, END_YEAR, END_MON, END_DAY, INSPRES_ID, INS_DATE, MODPRES_ID, MOD_DATE, PROCPRES_ID, PROC_DATE, SEQ, IUDFLAG) SELECT BRAN_CD , END_YEAR , END_MON , END_DAY , INSPRES_ID , INS_DATE , MODPRES_ID , MOD_DATE , :PROCPRES_ID , SYSDATE , (SELECT NVL(MAX(SEQ) + 1, 0) FROM GIBU.TBRA_BRANEND_HISTORY WHERE BRAN_CD = :BRAN_CD AND END_YEAR = :END_YEAR AND END_MON = :END_MON) , 'D' FROM GIBU.TBRA_BRANEND WHERE BRAN_CD = :BRAN_CD AND END_YEAR = :END_YEAR AND END_MON = :END_MON";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("END_MON", END_MON);               //���� ��
        sobj.setString("END_YEAR", END_YEAR);               //���� �⵵
        sobj.setString("PROCPRES_ID", PROCPRES_ID);               //ó���� ID
        return sobj;
    }
    // ���� �� ���
    public DOBJ CALLgibu_end_regist_XIUD10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD10");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLgibu_end_regist_XIUD10(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgibu_end_regist_XIUD10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   END_MON = dobj.getRetObject("R").getRecord().get("END_MON");   //���� ��
        String   END_YEAR = dobj.getRetObject("R").getRecord().get("END_YEAR");   //���� �⵵
        String   PROCPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //ó���� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_BRANEND_HISTORY (BRAN_CD, END_YEAR, END_MON, END_DAY, INSPRES_ID, INS_DATE, MODPRES_ID, MOD_DATE, PROCPRES_ID, PROC_DATE, SEQ, IUDFLAG) SELECT BRAN_CD , END_YEAR , END_MON , END_DAY , INSPRES_ID , INS_DATE , MODPRES_ID , MOD_DATE , :PROCPRES_ID , SYSDATE , (SELECT NVL(MAX(SEQ) + 1, 0) FROM GIBU.TBRA_BRANEND_HISTORY WHERE BRAN_CD = :BRAN_CD AND END_YEAR = :END_YEAR AND END_MON = :END_MON) , 'U' FROM GIBU.TBRA_BRANEND WHERE BRAN_CD = :BRAN_CD AND END_YEAR = :END_YEAR AND END_MON = :END_MON";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("END_MON", END_MON);               //���� ��
        sobj.setString("END_YEAR", END_YEAR);               //���� �⵵
        sobj.setString("PROCPRES_ID", PROCPRES_ID);               //ó���� ID
        return sobj;
    }
    // ������������
    // ���� �������� ����� �������� �Ѿ��
    public DOBJ CALLgibu_end_regist_XIUD12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD12");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLgibu_end_regist_XIUD12(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD12");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgibu_end_regist_XIUD12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   END_DAY = dobj.getRetObject("R").getRecord().get("END_DAY");   //������
        String   END_MON = dobj.getRetObject("R").getRecord().get("END_MON");   //���� ��
        String   END_YEAR = dobj.getRetObject("R").getRecord().get("END_YEAR");   //���� �⵵
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   OLD_END_DAY = dobj.getRetObject("R").getRecord().get("OLD_END_DAY");   //���� ������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_BRANEND  \n";
        query +=" SET END_DAY = :END_DAY , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE BRAN_CD = :BRAN_CD  \n";
        query +=" AND END_YEAR = :END_YEAR  \n";
        query +=" AND END_MON = :END_MON  \n";
        query +=" AND END_DAY = SUBSTR(:OLD_END_DAY,7,2)";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("END_DAY", END_DAY);               //������
        sobj.setString("END_MON", END_MON);               //���� ��
        sobj.setString("END_YEAR", END_YEAR);               //���� �⵵
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("OLD_END_DAY", OLD_END_DAY);               //���� ������
        return sobj;
    }
    // ������������
    // �����ϴ� ���� ���� ������ ������ �ʿ信 ���� ���������� �����ϴ� ��찡 �߻��Ѵ�
    public DOBJ CALLgibu_end_regist_DEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL8");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLgibu_end_regist_DEL8(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL8") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgibu_end_regist_DEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   END_YEAR = dvobj.getRecord().get("END_YEAR");   //���� �⵵
        String   END_MON = dvobj.getRecord().get("END_MON");   //���� ��
        String   END_DAY = dobj.getRetObject("R").getRecord().get("OLD_END_DAY").substring(6,8);   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BRANEND  \n";
        query +=" where END_MON=:END_MON  \n";
        query +=" and END_YEAR=:END_YEAR  \n";
        query +=" and BRAN_CD=:BRAN_CD  \n";
        query +=" and END_DAY=:END_DAY";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("END_YEAR", END_YEAR);               //���� �⵵
        sobj.setString("END_MON", END_MON);               //���� ��
        sobj.setString("END_DAY", END_DAY);               //������
        return sobj;
    }
    // �������� ����ȸ
    // ��ȸ�⵵�� �ش��ϴ� ���κ� ���������� ��ȸ�Ѵ�.
    public DOBJ CALLgibu_end_regist_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLgibu_end_regist_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgibu_end_regist_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YEAR = dobj.getRetObject("S1").getRecord().get("YEAR");   //�˻��⵵
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  GIBU  BRAN_CD  ,  DEPT_NM  ,  MAX(YY)  END_YEAR  ,  MAX(DECODE  (MM,  '01',  END_YMD))  M01  ,  MAX(DECODE  (MM,  '02',  END_YMD))  M02  ,  MAX(DECODE  (MM,  '03',  END_YMD))  M03  ,  MAX(DECODE  (MM,  '04',  END_YMD))  M04  ,  MAX(DECODE  (MM,  '05',  END_YMD))  M05  ,  MAX(DECODE  (MM,  '06',  END_YMD))  M06  ,  MAX(DECODE  (MM,  '07',  END_YMD))  M07  ,  MAX(DECODE  (MM,  '08',  END_YMD))  M08  ,  MAX(DECODE  (MM,  '09',  END_YMD))  M09  ,  MAX(DECODE  (MM,  '10',  END_YMD))  M10  ,  MAX(DECODE  (MM,  '11',  END_YMD))  M11  ,  MAX(DECODE  (MM,  '12',  END_YMD))  M12  FROM  (   \n";
        query +=" SELECT  B.GIBU,  B.MM,  DECODE(A.END_DAY,  NULL,  NULL,  B.YY  ||  B.MM  ||  A.END_DAY)  END_YMD,  B.DEPT_NM,  B.YY  FROM  GIBU.TBRA_BRANEND  A  ,  (   \n";
        query +=" SELECT  GIBU,  :YEAR  YY,  B.MM,  A.DEPT_NM  FROM  INSA.TCPM_DEPT  A  ,  GIBU.COPY_MM  B  WHERE  A.DEPT_CD  BETWEEN  '106010001'   \n";
        query +=" AND  '106019999'  )  B  WHERE  A.BRAN_CD  (+)  =  B.GIBU   \n";
        query +=" AND  A.END_YEAR  (+)  =  B.YY   \n";
        query +=" AND  A.END_MON  (+)  =  B.MM  )  GROUP  BY  GIBU,  DEPT_NM  ORDER  BY  GIBU ";
        sobj.setSql(query);
        sobj.setString("YEAR", YEAR);               //�˻��⵵
        return sobj;
    }
    //##**$$gibu_end_regist
    //##**$$gibu_end_search
    /* * ���α׷��� : bra11_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/09/14
    * ����    : ������ �� �Ա� ���������� �����Ѵ�.
    ����: ���� �����θ� ��밡���ϴ�.
    Input
    ACT_FLAG (�׼� �÷���)
    BRAN_CD (���� �ڵ�)
    END_DAY (������)
    END_MON (���� ��)
    END_YEAR (���� �⵵)
    OLD_END_DAY (���� ������)
    YEAR (�˻��⵵)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLgibu_end_search(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLgibu_end_search_SEL1(Conn, dobj);           //  ���κ�����������ȸ
            dobj  = CALLgibu_end_search_SEL2(Conn, dobj);           //  �Աݱ����� ��ȸ
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
            }
            catch(Exception re)
            {
                re.printStackTrace();
            }
        }
        finally
        {
            try
            {
                Conn.close();
            }
            catch(SQLException e)
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e,"DataBase Close Error");
                e.printStackTrace();
            }
        }
        return dobj;
    }
    public DOBJ CTLgibu_end_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLgibu_end_search_SEL1(Conn, dobj);           //  ���κ�����������ȸ
        dobj  = CALLgibu_end_search_SEL2(Conn, dobj);           //  �Աݱ����� ��ȸ
        return dobj;
    }
    // ���κ�����������ȸ
    // ��ȸ�⵵�� �ش��ϴ� ���κ� ���������� ��ȸ�Ѵ�.
    public DOBJ CALLgibu_end_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLgibu_end_search_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgibu_end_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YEAR = dobj.getRetObject("S").getRecord().get("YEAR");   //�˻��⵵
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  GIBU  BRAN_CD  ,  DEPT_NM  ,  MAX(YY)  END_YEAR  ,  MAX(DECODE  (MM,  '01',  END_YMD))  M01  ,  MAX(DECODE  (MM,  '02',  END_YMD))  M02  ,  MAX(DECODE  (MM,  '03',  END_YMD))  M03  ,  MAX(DECODE  (MM,  '04',  END_YMD))  M04  ,  MAX(DECODE  (MM,  '05',  END_YMD))  M05  ,  MAX(DECODE  (MM,  '06',  END_YMD))  M06  ,  MAX(DECODE  (MM,  '07',  END_YMD))  M07  ,  MAX(DECODE  (MM,  '08',  END_YMD))  M08  ,  MAX(DECODE  (MM,  '09',  END_YMD))  M09  ,  MAX(DECODE  (MM,  '10',  END_YMD))  M10  ,  MAX(DECODE  (MM,  '11',  END_YMD))  M11  ,  MAX(DECODE  (MM,  '12',  END_YMD))  M12  FROM  (   \n";
        query +=" SELECT  B.GIBU,  B.MM,  DECODE(A.END_DAY,  NULL,  NULL,  B.YY  ||  B.MM  ||  A.END_DAY)  END_YMD,  B.DEPT_NM,  B.YY  FROM  GIBU.TBRA_BRANEND  A  ,  (   \n";
        query +=" SELECT  GIBU,  :YEAR  YY,  B.MM,  A.DEPT_NM  FROM  INSA.TCPM_DEPT  A  ,  GIBU.COPY_MM  B  WHERE  A.DEPT_CD  BETWEEN  '106010001'   \n";
        query +=" AND  '106019999'  )  B  WHERE  A.BRAN_CD  (+)  =  B.GIBU   \n";
        query +=" AND  A.END_YEAR  (+)  =  B.YY   \n";
        query +=" AND  A.END_MON  (+)  =  B.MM  )  GROUP  BY  GIBU,  DEPT_NM  ORDER  BY  GIBU ";
        sobj.setSql(query);
        sobj.setString("YEAR", YEAR);               //�˻��⵵
        return sobj;
    }
    // �Աݱ����� ��ȸ
    public DOBJ CALLgibu_end_search_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLgibu_end_search_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgibu_end_search_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YEAR = dobj.getRetObject("S").getRecord().get("YEAR");   //�˻��⵵
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :YEAR  AS  END_YEAR  ,  MAX(DECODE(MM,  '01',  END_DAY))  AS  M01  ,  MAX(DECODE(MM,  '02',  END_DAY))  AS  M02  ,  MAX(DECODE(MM,  '03',  END_DAY))  AS  M03  ,  MAX(DECODE(MM,  '04',  END_DAY))  AS  M04  ,  MAX(DECODE(MM,  '05',  END_DAY))  AS  M05  ,  MAX(DECODE(MM,  '06',  END_DAY))  AS  M06  ,  MAX(DECODE(MM,  '07',  END_DAY))  AS  M07  ,  MAX(DECODE(MM,  '08',  END_DAY))  AS  M08  ,  MAX(DECODE(MM,  '09',  END_DAY))  AS  M09  ,  MAX(DECODE(MM,  '10',  END_DAY))  AS  M10  ,  MAX(DECODE(MM,  '11',  END_DAY))  AS  M11  ,  MAX(DECODE(MM,  '12',  END_DAY))  AS  M12  ,  MAX(DECODE(MM,  '01',  USE_YN))  AS  U01  ,  MAX(DECODE(MM,  '02',  USE_YN))  AS  U02  ,  MAX(DECODE(MM,  '03',  USE_YN))  AS  U03  ,  MAX(DECODE(MM,  '04',  USE_YN))  AS  U04  ,  MAX(DECODE(MM,  '05',  USE_YN))  AS  U05  ,  MAX(DECODE(MM,  '06',  USE_YN))  AS  U06  ,  MAX(DECODE(MM,  '07',  USE_YN))  AS  U07  ,  MAX(DECODE(MM,  '08',  USE_YN))  AS  U08  ,  MAX(DECODE(MM,  '09',  USE_YN))  AS  U09  ,  MAX(DECODE(MM,  '10',  USE_YN))  AS  U10  ,  MAX(DECODE(MM,  '11',  USE_YN))  AS  U11  ,  MAX(DECODE(MM,  '12',  USE_YN))  AS  U12  FROM  (   \n";
        query +=" SELECT  B.MM,  DECODE(A.END_DAY,  NULL,  NULL,  END_YEAR||END_MON||END_DAY)  AS  END_DAY,  A.USE_YN  FROM  GIBU.TBRA_DEPO_MON  A,  GIBU.COPY_MM  B  WHERE  A.MON(+)  =  B.MM   \n";
        query +=" AND  A.YEAR  =  :YEAR  ) ";
        sobj.setSql(query);
        sobj.setString("YEAR", YEAR);               //�˻��⵵
        return sobj;
    }
    //##**$$gibu_end_search
    //##**$$rept_closing
    /*
    */
    public DOBJ CTLrept_closing(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLrept_closing_SEL1(Conn, dobj);           //  ���θ���üũ
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
            }
            catch(Exception re)
            {
                re.printStackTrace();
            }
        }
        finally
        {
            try
            {
                Conn.close();
            }
            catch(SQLException e)
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e,"DataBase Close Error");
                e.printStackTrace();
            }
        }
        return dobj;
    }
    public DOBJ CTLrept_closing( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_closing_SEL1(Conn, dobj);           //  ���θ���üũ
        return dobj;
    }
    // ���θ���üũ
    // �ش� ����� ���θ����� �Ϸ�Ǿ����� Ȯ���Ѵ�. ȸ����ǥ ���� �� ��ȸ RETURN ���� ���� (Y : ����, N : �����ȵ�)
    public DOBJ CALLrept_closing_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_closing_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_closing_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //�Ա� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  (CASE  WHEN  (A.CNT  >=  B.CNT)  THEN  'Y'  ELSE  'N'  END)  AS  REPT_CLOSING  FROM  (   \n";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_BRANEND  WHERE  SUBSTR(:REPT_YRMN,  1,  4)  =  END_YEAR   \n";
        query +=" AND  SUBSTR(:REPT_YRMN,  5,  2)  =  END_MON  )  A  ,  (   \n";
        query +=" SELECT  COUNT(*)  CNT  FROM  INSA.TCPM_DEPT  WHERE  GIBU  IS  NOT  NULL   \n";
        query +=" AND  GIBU  <>  'AL'  )  B ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //�Ա� ���
        return sobj;
    }
    //##**$$rept_closing
    //##**$$update_depo_day
    /*
    */
    public DOBJ CTLupdate_depo_day(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
            Conn.setAutoCommit(false);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLupdate_depo_day_UPD1(Conn, dobj);           //  �Աݱ����Ϻ���
            Conn.commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
                Conn.rollback();
            }
            catch(Exception re)
            {
                re.printStackTrace();
            }
        }
        finally
        {
            try
            {
                Conn.close();
            }
            catch(SQLException e)
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e,"DataBase Close Error");
                e.printStackTrace();
            }
        }
        return dobj;
    }
    public DOBJ CTLupdate_depo_day( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupdate_depo_day_UPD1(Conn, dobj);           //  �Աݱ����Ϻ���
        return dobj;
    }
    // �Աݱ����Ϻ���
    public DOBJ CALLupdate_depo_day_UPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("UPD1");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupdate_depo_day_UPD1(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD1") ;
        rvobj.Println("UPD1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupdate_depo_day_UPD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");        //������ ID
        String MOD_DATE ="";        //���� �Ͻ�
        String   END_DAY = dvobj.getRecord().get("END_DAY");   //������
        String   END_YEAR = dvobj.getRecord().get("END_YEAR");   //���� �⵵
        String   END_MON = dvobj.getRecord().get("END_MON");   //���� ��
        String   MON = dvobj.getRecord().get("MON");   //���Ͽ�
        String   YEAR = dvobj.getRecord().get("YEAR");   //�˻��⵵
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_DEPO_MON  \n";
        query +=" set END_MON=:END_MON , END_YEAR=:END_YEAR , MOD_DATE=SYSDATE , END_DAY=:END_DAY  \n";
        query +=" where YEAR=:YEAR  \n";
        query +=" and MON=:MON";
        sobj.setSql(query);
        sobj.setString("END_DAY", END_DAY);               //������
        sobj.setString("END_YEAR", END_YEAR);               //���� �⵵
        sobj.setString("END_MON", END_MON);               //���� ��
        sobj.setString("MON", MON);               //���Ͽ�
        sobj.setString("YEAR", YEAR);               //�˻��⵵
        return sobj;
    }
    //##**$$update_depo_day
    //##**$$interface_rept_insert
    /* * ���α׷��� : bra11_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/20
    * ����    : ���ο��� �߻��� ¡���ݾ� (�Աݱݾ�) ��Ȳ�� ȸ�� �ý��۰� �����Ѵ�.
    1) procon_gbn = 1 ��ü �Ա� ���� ���� : ��ü ¡���ݾ�(ä������ �Աݺ�����)
    2) procon_gbn = 3 ���� �Ա� ���� ���� : �ش���� ���Ұ� ���ε� ¡���ݾ�
    3) procon_gbn = 2 �й� ���� ���� : ���ΰ� �й谡 �߻��� ��� �й� ���� ����
    4) procon_gbn = 6 ��ȯ ���� ���� : ��ȯ�� �߻��� ��� ��ȯ ���� ����
    5) procon_gbn = 4 ������ü(���) ���� ����  -- �߰� 2010/04/13
    6) procon_gbn = 5 �������� ��������           -- �߰� 2010/04/13
    7) procon_gbn = 7 ������� �Աݺ�
    8) procon_gbn = 8 ���� �Աݺ�
    9) procon_gbn = 9 ��������ݺ�
    * �Ա� ���� �� �׸� ������ ���� ó�� -> ���úμ��� ���� ó�� �� ���۾� ���� �ʿ�
    * �� �ڷ�� ȸ��ο��� ȸ�� ���� �� �й�ο��� �й�� ����ϴ� �ڷ���
    * ���� �Աݾ� -> ȸ��� -> ȸ��� �Ա�Ȯ��(�ش���ǥ����) -> �й�� -> �й�� ���� : ���� ���� �Ұ�. (���� �ʿ� �� ����ǿ��� ���۾� ó��)
    Input
    END_DAY (������)
    PRCON_YRMN (��Ȳ ���)
    START_DAY (������)
    * ������ : 2010/04/08
    * ������ :
    * �������� :
    1) ������ 'o', 'k', 'l', 'p', 'y' �Ӹ� �ƴ϶� ��� ������ ���ؼ� �����Ѵ�. (5.XIUD QUERY ����)
    2)  3.XIUD/5.XIUD�� ��ȸ������ RECV_DAY�� �� �������� MAPPING_DAY�� �������� (�������� MAPPING_DAY ��� �ʵ尡 ���� ������ �������ڷ� ������ �ɾ������)
    3)  7.XIUD�� ��ȸ������ RECV_DAY�� ������ ���� ó���Ѵ�.
    �� ������ �����ϴ� ���� : ȸ���� ��û�� ���� ��������
    - ���ΰ��� �߻��ϴ� �й�ݾ׿� ���ؼ� ���κ� ������ �ʿ��ϱ� ������ ����Ÿ�� ���� ��û
    - ��ȸ������ ���ΰ� �Աݺй谡 �߻��� ����, �� �Է³�¥(INS_DATE)�� �Ѵ�.
    * ������ : 2010/04/13
    * ������ :
    * �������� : ������ü(���), �������� ���� ���� query �߰�
    */
    public DOBJ CTLusedemdrept_insert(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
            Conn.setAutoCommit(false);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLinterface_rept_insert_XIUD15(Conn, dobj);           //  ���� ����
            dobj  = CALLinterface_rept_insert_SEL1(Conn, dobj);           //  SEQ �� �˻�
            dobj  = CALLinterface_rept_insert_XIUD1(Conn, dobj);           //  �Ա����� ����
            dobj  = CALLinterface_rept_insert_SEL2(Conn, dobj);           //  SEQ �� �˻�
            dobj  = CALLinterface_rept_insert_XIUD2(Conn, dobj);           //  ���αݾ� ����
            dobj  = CALLinterface_rept_insert_SEL3(Conn, dobj);           //  SEQ �� �˻�
            dobj  = CALLinterface_rept_insert_XIUD3(Conn, dobj);           //  �й�� �Ա����� ����
            dobj  = CALLinterface_rept_insert_SEL4(Conn, dobj);           //  SEQ �� �˻�
            dobj  = CALLinterface_rept_insert_XIUD4(Conn, dobj);           //  ��ȯ���� ����
            dobj  = CALLinterface_rept_insert_SEL10(Conn, dobj);           //  SEQ �� �˻�
            dobj  = CALLinterface_rept_insert_XIUD11(Conn, dobj);           //  ������ü(���) ��������
            dobj  = CALLinterface_rept_insert_SEL12(Conn, dobj);           //  SEQ �� �˻�
            dobj  = CALLinterface_rept_insert_XIUD13(Conn, dobj);           //  ���ڼ��� ��������
            dobj  = CALLinterface_rept_insert_SEL14(Conn, dobj);           //  SEQ �� �˻�
            dobj  = CALLinterface_rept_insert_XIUD16(Conn, dobj);           //  ������� �Աݺ� ��������
            dobj  = CALLinterface_rept_insert_SEL17(Conn, dobj);           //  SEQ �� �˻�
            dobj  = CALLinterface_rept_insert_XIUD18(Conn, dobj);           //  ���� �Աݺ� ��������
            dobj  = CALLinterface_rept_insert_SEL19(Conn, dobj);           //  SEQ �� �˻�
            dobj  = CALLinterface_rept_insert_XIUD20(Conn, dobj);           //  ��������ݺ� ��������
            Conn.commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
                Conn.rollback();
            }
            catch(Exception re)
            {
                re.printStackTrace();
            }
        }
        finally
        {
            try
            {
                Conn.close();
            }
            catch(SQLException e)
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e,"DataBase Close Error");
                e.printStackTrace();
            }
        }
        return dobj;
    }
    public DOBJ CTLusedemdrept_insert( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLinterface_rept_insert_XIUD15(Conn, dobj);           //  ���� ����
        dobj  = CALLinterface_rept_insert_SEL1(Conn, dobj);           //  SEQ �� �˻�
        dobj  = CALLinterface_rept_insert_XIUD1(Conn, dobj);           //  �Ա����� ����
        dobj  = CALLinterface_rept_insert_SEL2(Conn, dobj);           //  SEQ �� �˻�
        dobj  = CALLinterface_rept_insert_XIUD2(Conn, dobj);           //  ���αݾ� ����
        dobj  = CALLinterface_rept_insert_SEL3(Conn, dobj);           //  SEQ �� �˻�
        dobj  = CALLinterface_rept_insert_XIUD3(Conn, dobj);           //  �й�� �Ա����� ����
        dobj  = CALLinterface_rept_insert_SEL4(Conn, dobj);           //  SEQ �� �˻�
        dobj  = CALLinterface_rept_insert_XIUD4(Conn, dobj);           //  ��ȯ���� ����
        dobj  = CALLinterface_rept_insert_SEL10(Conn, dobj);           //  SEQ �� �˻�
        dobj  = CALLinterface_rept_insert_XIUD11(Conn, dobj);           //  ������ü(���) ��������
        dobj  = CALLinterface_rept_insert_SEL12(Conn, dobj);           //  SEQ �� �˻�
        dobj  = CALLinterface_rept_insert_XIUD13(Conn, dobj);           //  ���ڼ��� ��������
        dobj  = CALLinterface_rept_insert_SEL14(Conn, dobj);           //  SEQ �� �˻�
        dobj  = CALLinterface_rept_insert_XIUD16(Conn, dobj);           //  ������� �Աݺ� ��������
        dobj  = CALLinterface_rept_insert_SEL17(Conn, dobj);           //  SEQ �� �˻�
        dobj  = CALLinterface_rept_insert_XIUD18(Conn, dobj);           //  ���� �Աݺ� ��������
        dobj  = CALLinterface_rept_insert_SEL19(Conn, dobj);           //  SEQ �� �˻�
        dobj  = CALLinterface_rept_insert_XIUD20(Conn, dobj);           //  ��������ݺ� ��������
        return dobj;
    }
    // ���� ����
    public DOBJ CALLinterface_rept_insert_XIUD15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD15");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLinterface_rept_insert_XIUD15(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD15");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinterface_rept_insert_XIUD15(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //������
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //��Ȳ ���
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM GIBU.TBRA_INTERFACE_REPT_AMT  \n";
        query +=" WHERE PRCON_YRMN = :PRCON_YRMN  \n";
        query +=" AND MAPPING_DAY BETWEEN :START_DAY  \n";
        query +=" AND :END_DAY  \n";
        query +=" AND ACCTN_COMPL_YN = 'N'  \n";
        query +=" AND TRUST_COMPL_YN = 'N'";
        sobj.setSql(query);
        sobj.setString("END_DAY", END_DAY);               //������
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        sobj.setString("START_DAY", START_DAY);               //������
        return sobj;
    }
    // SEQ �� �˻�
    // PRCON_GBN = '1'
    public DOBJ CALLinterface_rept_insert_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLinterface_rept_insert_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinterface_rept_insert_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //��Ȳ ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  AS  SEQ  FROM  GIBU.TBRA_INTERFACE_REPT_AMT  WHERE  PRCON_YRMN  =  :PRCON_YRMN  AND	PRCON_GBN  =  '1' ";
        sobj.setSql(query);
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        return sobj;
    }
    // �Ա����� ����
    // PRCON_GBN ='1'
    public DOBJ CALLinterface_rept_insert_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLinterface_rept_insert_XIUD1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinterface_rept_insert_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //������
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //��Ȳ ���
        double   SEQ = dobj.getRetObject("SEL1").getRecord().getDouble("SEQ");   //������ȣ
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_INTERFACE_REPT_AMT ( PRCON_YRMN , PRCON_GBN , SEQ , REPT_BRAN , MAPPING_BRAN , RECV_DAY , MAPPING_DAY , REPT_GBN , BSTYP_CD , ONOFF_GBN , LEVY_AMT , MDM_CD , ACCN_NUM , INSPRES_ID , INS_DATE ) SELECT :PRCON_YRMN , '1' PRCON_GBN , :SEQ + ROWNUM SEQ , BRAN_CD , NULL MAPPING_BRAN , RECV_DAY , MAPPING_DAY , REPT_GBN , NULL BSTYP_CD , NULL ONOFF_GBN , LEVY_AMT , NULL MDM_CD , ACCN_NUM , :INSPRES_ID , SYSDATE FROM ( SELECT BRAN_CD , RECV_DAY , REPT_GBN , SUM(REPT_AMT) - SUM(COMIS) LEVY_AMT , ACCN_NUM , MAPPING_DAY FROM GIBU.TBRA_REPT WHERE RECV_DAY BETWEEN :START_DAY AND :END_DAY AND (DISTR_GBN IS NULL OR DISTR_GBN IN ('41','42')) GROUP BY BRAN_CD, RECV_DAY, REPT_GBN, ACCN_NUM , MAPPING_DAY )";
        sobj.setSql(query);
        sobj.setString("END_DAY", END_DAY);               //������
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("START_DAY", START_DAY);               //������
        return sobj;
    }
    // SEQ �� �˻�
    // PRCON_GBN ='3'
    public DOBJ CALLinterface_rept_insert_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLinterface_rept_insert_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinterface_rept_insert_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //��Ȳ ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  AS  SEQ  FROM  GIBU.TBRA_INTERFACE_REPT_AMT  WHERE  PRCON_YRMN  =  :PRCON_YRMN  AND	PRCON_GBN  =  '3' ";
        sobj.setSql(query);
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        return sobj;
    }
    // ���αݾ� ����
    // PRCON_GBN ='3'
    public DOBJ CALLinterface_rept_insert_XIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLinterface_rept_insert_XIUD2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinterface_rept_insert_XIUD2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //������
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //��Ȳ ���
        double   SEQ = dobj.getRetObject("SEL2").getRecord().getDouble("SEQ");   //������ȣ
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_INTERFACE_REPT_AMT ( PRCON_YRMN , PRCON_GBN , SEQ , REPT_BRAN , MAPPING_BRAN , RECV_DAY , MAPPING_DAY , REPT_GBN , BSTYP_CD , ONOFF_GBN , LEVY_AMT , MDM_CD , ACCN_NUM , INSPRES_ID , INS_DATE ) SELECT :PRCON_YRMN , '3' PRCON_GBN , :SEQ + ROWNUM , REPT_BRAN , MAPPING_BRAN , RECV_DAY , MAPPING_DAY , REPT_GBN , GRAD_GBN , ONOFF_GBN , REPT_AMT , MDM_CD , ACCN_NUM , :INSPRES_ID , SYSDATE FROM ( SELECT ZA.BRAN_CD REPT_BRAN , ZB.MAPPING_BRAN , ZB.RECV_DAY , ZB.MAPPING_DAY , ZB.REPT_GBN , ZA.GRAD_GBN , ZA.GRADNM , DECODE(ZA.ONOFF_GBN, '-', NULL, ZA.ONOFF_GBN) ONOFF_GBN , NVL(ZB.REPT_AMT, 0) REPT_AMT , ZB.ACCN_NUM , ZA.MDM_CD , ZA.SRT FROM ( SELECT XB.BRAN_CD , XA.GRAD_GBN , XA.GRADNM , XA.SRT , XA.MDM_CD , DECODE(XA.ONOFF_GBN, '1', '-', XA.ONOFF_GBN) ONOFF_GBN FROM ( SELECT A.GRAD_GBN , A.GRADNM , CASE A.GRAD_GBN WHEN 'o' THEN DECODE(B.SRT, '1', 'DD0101', 'DD0102') WHEN 'k' THEN DECODE(B.SRT, '1', 'DE0101', 'DE0102') WHEN 'l' THEN DECODE(B.SRT, '1', 'DF0101', 'DF0102') WHEN 'p' THEN DECODE(B.SRT, '1', 'DE0101', 'DE0102') WHEN 'y' THEN DECODE(B.SRT, '1', 'DD0101', 'DD0102') ELSE A.MDM_CD END MDM_CD , CASE A.GRAD_GBN WHEN 'k' THEN DECODE(B.SRT, '1', 1, 2) WHEN 'l' THEN DECODE(B.SRT, '1', 3, 4) WHEN 'o' THEN DECODE(B.SRT, '1', 5, 6) WHEN 'p' THEN DECODE(B.SRT, '1', 7, 8) WHEN 'y' THEN DECODE(B.SRT, '1', 9, 10) ELSE 11 END SRT , CASE WHEN A.GRAD_GBN IN ('o', 'k', 'l', 'p', 'y') THEN DECODE (B.SRT, '1', 'O', 'F') ELSE DECODE (B.SRT, '1', '1', NULL) END ONOFF_GBN FROM GIBU.TBRA_BSTYPGRAD A , ( SELECT '1' SRT FROM DUAL UNION ALL SELECT '2' SRT FROM DUAL ) B WHERE BSTYP_CD = 'z' ) XA , ( SELECT GIBU BRAN_CD FROM INSA.TCPM_DEPT WHERE GIBU <> 'AL' ) XB WHERE XA.ONOFF_GBN IS NOT NULL ORDER BY BRAN_CD, SRT ) ZA , ( SELECT TA.BRAN_CD , TA.MAPPING_BRAN , TA.RECV_DAY , TA.MAPPING_DAY , TA.REPT_GBN , TA.BSTYP_CD , TA.ONOFF_GBN , TA.ACCN_NUM , SUM(TA.REPT_AMT) REPT_AMT FROM ( SELECT XA.UPSO_CD , XA.BRAN_CD , XA.MAPPING_BRAN , XA.RECV_DAY , XA.MAPPING_DAY , XA.REPT_GBN , XB.BSTYP_CD , CASE WHEN XB.BSTYP_CD IN ('o', 'k', 'l', 'p', 'y') THEN XA.ONOFF_GBN ELSE '-' END ONOFF_GBN , XA.REPT_AMT , XA.ACCN_NUM FROM ( SELECT A.BRAN_CD , A.BRAN_CD MAPPING_BRAN , A.RECV_DAY , A.MAPPING_DAY , A.REPT_GBN , A.UPSO_CD , A.ACCN_NUM , A.REPT_AMT - A.COMIS REPT_AMT , B.ONOFF_GBN FROM GIBU.TBRA_REPT A , GIBU.TBRA_UPSO B WHERE A.MAPPING_DAY BETWEEN :START_DAY AND :END_DAY AND A.UPSO_CD = B.UPSO_CD AND A.DISTR_GBN IS NULL UNION ALL SELECT A.BRAN_CD , A.BRAN_CD MAPPING_BRAN , C.RECV_DAY , A.MAPPING_DAY , A.REPT_GBN , A.UPSO_CD , C.ACCN_NUM , A.DISTR_AMT REPT_AMT , B.ONOFF_GBN FROM GIBU.TBRA_REPT_UPSO_DISTR A , GIBU.TBRA_UPSO B , GIBU.TBRA_REPT C WHERE B.UPSO_CD = A.UPSO_CD AND C.REPT_DAY = A.REPT_DAY AND C.REPT_NUM = A.REPT_NUM AND C.REPT_GBN = A.REPT_GBN AND A.MAPPING_DAY BETWEEN :START_DAY AND :END_DAY ) XA , ( SELECT A.UPSO_CD , A.BSTYP_CD , A.UPSO_GRAD FROM GIBU.TBRA_UPSORTAL_INFO A , ( SELECT UPSO_CD , MAX(JOIN_SEQ) JOIN_SEQ FROM GIBU.TBRA_UPSORTAL_INFO GROUP BY UPSO_CD ) B WHERE A.JOIN_SEQ = B.JOIN_SEQ ) XB , GIBU.TBRA_BSTYPGRAD XC WHERE XA.UPSO_CD = XB.UPSO_CD AND XC.BSTYP_CD = XB.BSTYP_CD AND XC.GRAD_GBN = XB.UPSO_GRAD ) TA GROUP BY TA.BRAN_CD, TA.MAPPING_BRAN, TA.RECV_DAY, TA.MAPPING_DAY, TA.REPT_GBN, TA.BSTYP_CD, TA.ONOFF_GBN, TA.ACCN_NUM ) ZB WHERE ZA.BRAN_CD = ZB.BRAN_CD (+) AND ZA.GRAD_GBN = ZB.BSTYP_CD (+) AND ZA.ONOFF_GBN = ZB.ONOFF_GBN (+) ) WHERE REPT_AMT > 0";
        sobj.setSql(query);
        sobj.setString("END_DAY", END_DAY);               //������
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("START_DAY", START_DAY);               //������
        return sobj;
    }
    // SEQ �� �˻�
    // PRCON_GBN ='2'
    public DOBJ CALLinterface_rept_insert_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLinterface_rept_insert_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinterface_rept_insert_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //��Ȳ ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  AS  SEQ  FROM  GIBU.TBRA_INTERFACE_REPT_AMT  WHERE  PRCON_YRMN  =  :PRCON_YRMN  AND	PRCON_GBN  =  '2' ";
        sobj.setSql(query);
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        return sobj;
    }
    // �й�� �Ա����� ����
    // PRCON_GBN ='2'
    public DOBJ CALLinterface_rept_insert_XIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLinterface_rept_insert_XIUD3(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinterface_rept_insert_XIUD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //������
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //��Ȳ ���
        double   SEQ = dobj.getRetObject("SEL3").getRecord().getDouble("SEQ");   //������ȣ
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_INTERFACE_REPT_AMT ( PRCON_YRMN , PRCON_GBN , SEQ , REPT_BRAN , MAPPING_BRAN , RECV_DAY , MAPPING_DAY , REPT_GBN , BSTYP_CD , ONOFF_GBN , LEVY_AMT , MDM_CD , ACCN_NUM , INSPRES_ID , INS_DATE ) SELECT :PRCON_YRMN , '2' PRCON_GBN , :SEQ + ROWNUM SEQ , REPT_BRAN , MAPPING_BRAN , RECV_DAY , MAPPING_DAY , REPT_GBN , NULL BSTYP_CD , NULL ONOFF_GBN , LEVY_AMT , NULL MDM_CD , ACCN_NUM , :INSPRES_ID , SYSDATE FROM ( SELECT A.BRAN_CD REPT_BRAN , B.BRAN_CD MAPPING_BRAN , A.RECV_DAY , A.REPT_GBN , SUM(B.DISTR_AMT) LEVY_AMT , A.ACCN_NUM , TO_CHAR(B.INS_DATE, 'YYYYMMDD') MAPPING_DAY FROM GIBU.TBRA_REPT A , GIBU.TBRA_REPT_DISTR B WHERE TO_CHAR(B.INS_DATE, 'YYYYMMDD') BETWEEN :START_DAY AND :END_DAY AND B.REPT_DAY = A.REPT_DAY AND B.REPT_NUM = A.REPT_NUM AND B.REPT_GBN = A.REPT_GBN GROUP BY A.BRAN_CD, B.BRAN_CD, A.RECV_DAY, A.REPT_GBN, A.ACCN_NUM,TO_CHAR(B.INS_DATE, 'YYYYMMDD') )";
        sobj.setSql(query);
        sobj.setString("END_DAY", END_DAY);               //������
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("START_DAY", START_DAY);               //������
        return sobj;
    }
    // SEQ �� �˻�
    // S.PRCON_YRMN
    public DOBJ CALLinterface_rept_insert_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLinterface_rept_insert_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinterface_rept_insert_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //��Ȳ ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  AS  SEQ  FROM  GIBU.TBRA_INTERFACE_REPT_AMT  WHERE  PRCON_YRMN  =  :PRCON_YRMN  AND	PRCON_GBN  =  '6' ";
        sobj.setSql(query);
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        return sobj;
    }
    // ��ȯ���� ����
    // PRCOD_GBN='6'
    public DOBJ CALLinterface_rept_insert_XIUD4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD4");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLinterface_rept_insert_XIUD4(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinterface_rept_insert_XIUD4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //������
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //��Ȳ ���
        double   SEQ = dobj.getRetObject("SEL4").getRecord().getDouble("SEQ");   //������ȣ
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_INTERFACE_REPT_AMT ( PRCON_YRMN , PRCON_GBN , SEQ , REPT_BRAN , MAPPING_BRAN , RECV_DAY , MAPPING_DAY , REPT_GBN , BSTYP_CD , ONOFF_GBN , LEVY_AMT , MDM_CD , ACCN_NUM , INSPRES_ID , INS_DATE ) SELECT :PRCON_YRMN , '6' PRCON_GBN , :SEQ + ROWNUM , REPT_BRAN , MAPPING_BRAN , RECV_DAY , MAPPING_DAY , REPT_GBN , GRAD_GBN , ONOFF_GBN , REPT_AMT , MDM_CD , ACCN_NUM , :INSPRES_ID , SYSDATE FROM ( SELECT ZA.BRAN_CD REPT_BRAN , ZB.MAPPING_BRAN , ZB.RECV_DAY , ZB.MAPPING_DAY , ZB.REPT_GBN , ZA.GRAD_GBN , ZA.GRADNM , DECODE(ZA.ONOFF_GBN, '-', NULL, ZA.ONOFF_GBN) ONOFF_GBN , NVL(ZB.REPT_AMT, 0) REPT_AMT , ZB.ACCN_NUM , ZA.MDM_CD , ZA.SRT FROM ( SELECT XB.BRAN_CD , XA.GRAD_GBN , XA.GRADNM , XA.SRT , XA.MDM_CD , DECODE(XA.ONOFF_GBN, '1', '-', XA.ONOFF_GBN) ONOFF_GBN FROM ( SELECT A.GRAD_GBN , A.GRADNM , CASE A.GRAD_GBN WHEN 'o' THEN DECODE(B.SRT, '1', 'DD0101', 'DD0102') WHEN 'k' THEN DECODE(B.SRT, '1', 'DE0101', 'DE0102') WHEN 'l' THEN DECODE(B.SRT, '1', 'DF0101', 'DF0102') WHEN 'p' THEN DECODE(B.SRT, '1', 'DE0101', 'DE0102') WHEN 'y' THEN DECODE(B.SRT, '1', 'DD0101', 'DD0102') ELSE A.MDM_CD END MDM_CD , CASE A.GRAD_GBN WHEN 'k' THEN DECODE(B.SRT, '1', 1, 2) WHEN 'l' THEN DECODE(B.SRT, '1', 3, 4) WHEN 'o' THEN DECODE(B.SRT, '1', 5, 6) WHEN 'p' THEN DECODE(B.SRT, '1', 7, 8) WHEN 'y' THEN DECODE(B.SRT, '1', 9, 10) ELSE 11 END SRT , CASE WHEN A.GRAD_GBN IN ('o', 'k', 'l', 'p', 'y') THEN DECODE (B.SRT, '1', 'O', 'F') ELSE DECODE (B.SRT, '1', '1', NULL) END ONOFF_GBN FROM GIBU.TBRA_BSTYPGRAD A , ( SELECT '1' SRT FROM DUAL UNION ALL SELECT '2' SRT FROM DUAL ) B WHERE BSTYP_CD = 'z' ) XA , ( SELECT GIBU BRAN_CD FROM INSA.TCPM_DEPT WHERE GIBU <> 'AL' ) XB WHERE XA.ONOFF_GBN IS NOT NULL ORDER BY BRAN_CD, SRT ) ZA , ( SELECT TA.BRAN_CD , TA.MAPPING_BRAN , TA.RECV_DAY , TA.MAPPING_DAY , TA.REPT_GBN , TA.BSTYP_CD , TA.ONOFF_GBN , TA.ACCN_NUM , SUM(TA.REPT_AMT) REPT_AMT FROM ( SELECT XA.UPSO_CD , XA.BRAN_CD , XA.MAPPING_BRAN , XA.RECV_DAY , XA.MAPPING_DAY , XA.REPT_GBN , XB.BSTYP_CD , CASE WHEN XB.BSTYP_CD IN ('o', 'k', 'l', 'p', 'y') THEN XA.ONOFF_GBN ELSE '-' END ONOFF_GBN , XA.REPT_AMT , XA.ACCN_NUM FROM ( SELECT A.BRAN_CD , A.BRAN_CD MAPPING_BRAN , A.RETURN_DAY RECV_DAY , A.RETURN_DAY MAPPING_DAY , '07' REPT_GBN , A.UPSO_CD , NULL ACCN_NUM , A.RETURN_AMT REPT_AMT , B.ONOFF_GBN FROM GIBU.TBRA_REPT_RETURN A , GIBU.TBRA_UPSO B WHERE A.RETURN_DAY BETWEEN :START_DAY AND :END_DAY AND A.UPSO_CD = B.UPSO_CD ) XA , ( SELECT A.UPSO_CD , A.BSTYP_CD , A.UPSO_GRAD FROM GIBU.TBRA_UPSORTAL_INFO A , ( SELECT UPSO_CD , MAX(JOIN_SEQ) JOIN_SEQ FROM GIBU.TBRA_UPSORTAL_INFO GROUP BY UPSO_CD ) B WHERE A.JOIN_SEQ = B.JOIN_SEQ ) XB , GIBU.TBRA_BSTYPGRAD XC WHERE XA.UPSO_CD = XB.UPSO_CD AND XC.BSTYP_CD = XB.BSTYP_CD AND XC.GRAD_GBN = XB.UPSO_GRAD ) TA GROUP BY TA.BRAN_CD, TA.MAPPING_BRAN, TA.RECV_DAY, TA.MAPPING_DAY, TA.REPT_GBN, TA.BSTYP_CD, TA.ONOFF_GBN, TA.ACCN_NUM ) ZB WHERE ZA.BRAN_CD = ZB.BRAN_CD AND ZA.GRAD_GBN = ZB.BSTYP_CD AND ZA.ONOFF_GBN = ZB.ONOFF_GBN )";
        sobj.setSql(query);
        sobj.setString("END_DAY", END_DAY);               //������
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("START_DAY", START_DAY);               //������
        return sobj;
    }
    // SEQ �� �˻�
    // ������ü(���) prcon_gbn:4
    public DOBJ CALLinterface_rept_insert_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLinterface_rept_insert_SEL10(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinterface_rept_insert_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //��Ȳ ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  AS  SEQ  FROM  GIBU.TBRA_INTERFACE_REPT_AMT  WHERE  PRCON_YRMN  =  :PRCON_YRMN  AND	PRCON_GBN  =  '4' ";
        sobj.setSql(query);
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        return sobj;
    }
    // ������ü(���) ��������
    // PRCON_GBN ='4'
    public DOBJ CALLinterface_rept_insert_XIUD11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD11");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLinterface_rept_insert_XIUD11(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinterface_rept_insert_XIUD11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //������
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //��Ȳ ���
        double   SEQ = dobj.getRetObject("SEL10").getRecord().getDouble("SEQ");   //������ȣ
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_INTERFACE_REPT_AMT ( PRCON_YRMN , PRCON_GBN , SEQ , REPT_BRAN , MAPPING_BRAN , RECV_DAY , MAPPING_DAY , REPT_GBN , LEVY_AMT , ACCN_NUM , INSPRES_ID , INS_DATE ) SELECT :PRCON_YRMN , '4' PRCON_GBN , :SEQ + ROWNUM SEQ , REPT_BRAN , MAPPING_BRAN , RECV_DAY , DECODE(MOD_DAY, NULL, INS_DAY, MOD_DAY) MAPPING_DAY , '03' REPT_GBN , LEVY_AMT , ACCN_NUM , :INSPRES_ID , SYSDATE FROM ( SELECT BRAN_CD REPT_BRAN , BRAN_CD MAPPING_BRAN , RECV_DAY , TO_CHAR(INS_DATE, 'YYYYMMDD') INS_DAY , TO_CHAR(MOD_DATE, 'YYYYMMDD') MOD_DAY , SUM(OUT_AMT) LEVY_AMT , ACCN_NUM FROM GIBU.TBRA_REPT_TRANS WHERE REPT_DAY BETWEEN :START_DAY AND :END_DAY AND DISTR_GBN = '44' GROUP BY BRAN_CD, RECV_DAY, TO_CHAR(MOD_DATE, 'YYYYMMDD'), TO_CHAR(INS_DATE, 'YYYYMMDD'), ACCN_NUM )";
        sobj.setSql(query);
        sobj.setString("END_DAY", END_DAY);               //������
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("START_DAY", START_DAY);               //������
        return sobj;
    }
    // SEQ �� �˻�
    // ���ڼ��� prcon_gbn:5
    public DOBJ CALLinterface_rept_insert_SEL12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL12");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL12");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLinterface_rept_insert_SEL12(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL12");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinterface_rept_insert_SEL12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //��Ȳ ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  AS  SEQ  FROM  GIBU.TBRA_INTERFACE_REPT_AMT  WHERE  PRCON_YRMN  =  :PRCON_YRMN  AND	PRCON_GBN  =  '5' ";
        sobj.setSql(query);
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        return sobj;
    }
    // ���ڼ��� ��������
    // PRCON_GBN ='5'
    public DOBJ CALLinterface_rept_insert_XIUD13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD13");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLinterface_rept_insert_XIUD13(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD13");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinterface_rept_insert_XIUD13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //������
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //��Ȳ ���
        double   SEQ = dobj.getRetObject("SEL12").getRecord().getDouble("SEQ");   //������ȣ
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_INTERFACE_REPT_AMT ( PRCON_YRMN , PRCON_GBN , SEQ , REPT_BRAN , MAPPING_BRAN , RECV_DAY , MAPPING_DAY , REPT_GBN , LEVY_AMT , ACCN_NUM , INSPRES_ID , INS_DATE ) SELECT :PRCON_YRMN , '5' PRCON_GBN , :SEQ + ROWNUM SEQ , REPT_BRAN , MAPPING_BRAN , RECV_DAY , DECODE(MOD_DAY, NULL, INS_DAY, MOD_DAY) MAPPING_DAY , '03' REPT_GBN , LEVY_AMT , ACCN_NUM , :INSPRES_ID , SYSDATE FROM ( SELECT BRAN_CD REPT_BRAN , BRAN_CD MAPPING_BRAN , RECV_DAY , TO_CHAR(INS_DATE, 'YYYYMMDD') INS_DAY , TO_CHAR(MOD_DATE, 'YYYYMMDD') MOD_DAY , SUM(REPT_AMT) LEVY_AMT , ACCN_NUM FROM GIBU.TBRA_REPT_TRANS WHERE REPT_DAY BETWEEN :START_DAY AND :END_DAY AND DISTR_GBN = '43' GROUP BY BRAN_CD, RECV_DAY, TO_CHAR(MOD_DATE, 'YYYYMMDD'), TO_CHAR(INS_DATE, 'YYYYMMDD'), ACCN_NUM )";
        sobj.setSql(query);
        sobj.setString("END_DAY", END_DAY);               //������
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("START_DAY", START_DAY);               //������
        return sobj;
    }
    // SEQ �� �˻�
    // ������� prcon_gbn:7
    public DOBJ CALLinterface_rept_insert_SEL14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL14");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL14");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLinterface_rept_insert_SEL14(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL14");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinterface_rept_insert_SEL14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //��Ȳ ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  AS  SEQ  FROM  GIBU.TBRA_INTERFACE_REPT_AMT  WHERE  PRCON_YRMN  =  :PRCON_YRMN  AND	PRCON_GBN  =  '7' ";
        sobj.setSql(query);
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        return sobj;
    }
    // ������� �Աݺ� ��������
    // PRCON_GBN ='7'
    public DOBJ CALLinterface_rept_insert_XIUD16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD16");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLinterface_rept_insert_XIUD16(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD16");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinterface_rept_insert_XIUD16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //������
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //��Ȳ ���
        double   SEQ = dobj.getRetObject("SEL14").getRecord().getDouble("SEQ");   //������ȣ
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_INTERFACE_REPT_AMT ( PRCON_YRMN , PRCON_GBN , SEQ , REPT_BRAN , MAPPING_BRAN , RECV_DAY , MAPPING_DAY , REPT_GBN , LEVY_AMT , ACCN_NUM , INSPRES_ID , INS_DATE ) SELECT :PRCON_YRMN , '7' PRCON_GBN , :SEQ + ROWNUM SEQ , REPT_BRAN , MAPPING_BRAN , RECV_DAY , DECODE(MOD_DAY, NULL, INS_DAY, MOD_DAY) MAPPING_DAY , '03' REPT_GBN , LEVY_AMT , ACCN_NUM , :INSPRES_ID , SYSDATE FROM ( SELECT BRAN_CD REPT_BRAN , BRAN_CD MAPPING_BRAN , RECV_DAY , TO_CHAR(INS_DATE, 'YYYYMMDD') INS_DAY , TO_CHAR(MOD_DATE, 'YYYYMMDD') MOD_DAY , SUM(REPT_AMT) LEVY_AMT , ACCN_NUM FROM GIBU.TBRA_REPT_TRANS WHERE REPT_DAY BETWEEN :START_DAY AND :END_DAY AND DISTR_GBN = '45' GROUP BY BRAN_CD, RECV_DAY, TO_CHAR(MOD_DATE, 'YYYYMMDD'), TO_CHAR(INS_DATE, 'YYYYMMDD'), ACCN_NUM )";
        sobj.setSql(query);
        sobj.setString("END_DAY", END_DAY);               //������
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("START_DAY", START_DAY);               //������
        return sobj;
    }
    // SEQ �� �˻�
    // �����Աݺ� prcon_gbn:8
    public DOBJ CALLinterface_rept_insert_SEL17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL17");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL17");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLinterface_rept_insert_SEL17(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL17");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinterface_rept_insert_SEL17(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //��Ȳ ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  AS  SEQ  FROM  GIBU.TBRA_INTERFACE_REPT_AMT  WHERE  PRCON_YRMN  =  :PRCON_YRMN  AND	PRCON_GBN  =  '8' ";
        sobj.setSql(query);
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        return sobj;
    }
    // ���� �Աݺ� ��������
    // PRCON_GBN ='8'
    public DOBJ CALLinterface_rept_insert_XIUD18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD18");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLinterface_rept_insert_XIUD18(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD18");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinterface_rept_insert_XIUD18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //������
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //��Ȳ ���
        double   SEQ = dobj.getRetObject("SEL17").getRecord().getDouble("SEQ");   //������ȣ
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_INTERFACE_REPT_AMT ( PRCON_YRMN , PRCON_GBN , SEQ , REPT_BRAN , MAPPING_BRAN , RECV_DAY , MAPPING_DAY , REPT_GBN , LEVY_AMT , ACCN_NUM , INSPRES_ID , INS_DATE ) SELECT :PRCON_YRMN , '8' PRCON_GBN , :SEQ + ROWNUM SEQ , REPT_BRAN , MAPPING_BRAN , RECV_DAY , DECODE(MOD_DAY, NULL, INS_DAY, MOD_DAY) MAPPING_DAY , '03' REPT_GBN , LEVY_AMT , ACCN_NUM , :INSPRES_ID , SYSDATE FROM ( SELECT BRAN_CD REPT_BRAN , BRAN_CD MAPPING_BRAN , RECV_DAY , TO_CHAR(INS_DATE, 'YYYYMMDD') INS_DAY , TO_CHAR(MOD_DATE, 'YYYYMMDD') MOD_DAY , SUM(REPT_AMT) LEVY_AMT , ACCN_NUM FROM GIBU.TBRA_REPT_TRANS WHERE REPT_DAY BETWEEN :START_DAY AND :END_DAY AND DISTR_GBN = '46' GROUP BY BRAN_CD, RECV_DAY, TO_CHAR(MOD_DATE, 'YYYYMMDD'), TO_CHAR(INS_DATE, 'YYYYMMDD'), ACCN_NUM )";
        sobj.setSql(query);
        sobj.setString("END_DAY", END_DAY);               //������
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("START_DAY", START_DAY);               //������
        return sobj;
    }
    // SEQ �� �˻�
    // ��������ݺ� prcon_gbn:9
    public DOBJ CALLinterface_rept_insert_SEL19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL19");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL19");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLinterface_rept_insert_SEL19(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL19");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinterface_rept_insert_SEL19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //��Ȳ ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  AS  SEQ  FROM  GIBU.TBRA_INTERFACE_REPT_AMT  WHERE  PRCON_YRMN  =  :PRCON_YRMN  AND	PRCON_GBN  =  '9' ";
        sobj.setSql(query);
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        return sobj;
    }
    // ��������ݺ� ��������
    // PRCON_GBN ='9'
    public DOBJ CALLinterface_rept_insert_XIUD20(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD20");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLinterface_rept_insert_XIUD20(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD20");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinterface_rept_insert_XIUD20(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //������
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //��Ȳ ���
        double   SEQ = dobj.getRetObject("SEL19").getRecord().getDouble("SEQ");   //������ȣ
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_INTERFACE_REPT_AMT ( PRCON_YRMN , PRCON_GBN , SEQ , REPT_BRAN , MAPPING_BRAN , RECV_DAY , MAPPING_DAY , REPT_GBN , LEVY_AMT , ACCN_NUM , INSPRES_ID , INS_DATE ) SELECT :PRCON_YRMN , '9' PRCON_GBN , :SEQ + ROWNUM SEQ , REPT_BRAN , MAPPING_BRAN , RECV_DAY , DECODE(MOD_DAY, NULL, INS_DAY, MOD_DAY) MAPPING_DAY , '03' REPT_GBN , LEVY_AMT , ACCN_NUM , :INSPRES_ID , SYSDATE FROM ( SELECT BRAN_CD REPT_BRAN , BRAN_CD MAPPING_BRAN , RECV_DAY , TO_CHAR(INS_DATE, 'YYYYMMDD') INS_DAY , TO_CHAR(MOD_DATE, 'YYYYMMDD') MOD_DAY , SUM(REPT_AMT) LEVY_AMT , ACCN_NUM FROM GIBU.TBRA_REPT_TRANS WHERE REPT_DAY BETWEEN :START_DAY AND :END_DAY AND DISTR_GBN = '47' GROUP BY BRAN_CD, RECV_DAY, TO_CHAR(MOD_DATE, 'YYYYMMDD'), TO_CHAR(INS_DATE, 'YYYYMMDD'), ACCN_NUM )";
        sobj.setSql(query);
        sobj.setString("END_DAY", END_DAY);               //������
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("START_DAY", START_DAY);               //������
        return sobj;
    }
    //##**$$interface_rept_insert
    //##**$$end
}
