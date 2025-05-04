
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s05
{
    public bra01_s05()
    {
    }
    //##**$$upso_adrs_select
    /* * ���α׷��� : bra01_s05
    * �ۼ��� : �̱���
    * �ۼ��� : 2009/11/26
    * ���� : ����Ŭ���ݷ� ��ϵ� ����Ʈ�� ��ȸ�Ѵ�.
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLupso_adrs_select(DOBJ dobj)
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
            dobj  = CALLupso_adrs_select_SEL1(Conn, dobj);           //  ���� ���� ����
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
    public DOBJ CTLupso_adrs_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_adrs_select_SEL1(Conn, dobj);           //  ���� ���� ����
        return dobj;
    }
    // ���� ���� ����
    // ����� ������ ������ �����´�.
    public DOBJ CALLupso_adrs_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_adrs_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_adrs_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CONSORG_ID = dobj.getRetObject("S").getRecord().get("CONSORG_ID");   //���� ID
        String   START_DATE = dobj.getRetObject("S").getRecord().get("START_DATE");   //��ȸ�����
        String   END_DATE = dobj.getRetObject("S").getRecord().get("END_DATE");   //���� �Ͻ�
        String   MATCH_YN = dobj.getRetObject("S").getRecord().get("MATCH_YN");   //��Ī ����(Y/N/D)
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.CONS_DATE  ,  XA.CONS_SEQ  ,  XA.UPSO_PHON_NUM  ,  XA.CONSORG_ID  ,  XA.BRAN_CD  ,  XB.DEPT_NM  BRAN_NM  ,  XA.UPSO_CD  ,  XC.UPSO_NM  ,  XA.FILE_ROUT  ,  XA.FILE_NM  ,  XA.MATCH_YN  ,  XA.IO_GBN  ,  XD.REMAK  ,  XD.BRE_REMAK  ,  '1'  CHK_IO  ,  XA.CONS_TM  ,  XA.FILE_SIZE  ,  XD.VISIT_SEQ  FROM  GIBU.TBRA_UPSO_ADRS_FILEINFO  XA  ,  INSA.TCPM_DEPT  XB  ,  GIBU.TBRA_UPSO  XC  ,  (   \n";
        query +=" SELECT  A.CONS_DATE  ,  A.CONS_SEQ  ,  A.REMAK  ,  B.REMAK  BRE_REMAK  ,  A.VISIT_SEQ  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO_VISIT_BRE  B  WHERE  A.UPSO_CD  =  B.UPSO_CD(+)   \n";
        query +=" AND  A.VISIT_DAY  =  B.VISIT_DAY(+)   \n";
        query +=" AND  A.JOB_GBN  =  B.JOB_GBN(+)   \n";
        query +=" AND  A.VISIT_SEQ  =  B.VISIT_SEQ(+)   \n";
        query +=" AND  B.VISIT_NUM  =  1  )  XD  WHERE  XA.CONSORG_ID  =  :CONSORG_ID   \n";
        query +=" AND  XA.CONS_DATE  BETWEEN  :START_DATE   \n";
        query +=" AND  :END_DATE   \n";
        query +=" AND  XA.CONS_TM  NOT  IN  '000000'   \n";
        query +=" AND  XA.MATCH_YN  LIKE  :MATCH_YN   \n";
        query +=" AND  XC.UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XB.GIBU(+)  =  XA.BRAN_CD   \n";
        query +=" AND  XD.CONS_DATE(+)  =  XA.CONS_DATE   \n";
        query +=" AND  XD.CONS_SEQ(+)  =  XA.CONS_SEQ  ORDER  BY  XA.CONS_DATE  DESC,  XA.CONS_SEQ ";
        sobj.setSql(query);
        sobj.setString("CONSORG_ID", CONSORG_ID);               //���� ID
        sobj.setString("START_DATE", START_DATE);               //��ȸ�����
        sobj.setString("END_DATE", END_DATE);               //���� �Ͻ�
        sobj.setString("MATCH_YN", MATCH_YN);               //��Ī ����(Y/N/D)
        return sobj;
    }
    //##**$$upso_adrs_select
    //##**$$upso_adrs_save
    /* * ���α׷��� : bra01_s05
    * �ۼ��� : �̱���
    * �ۼ��� : 2009/11/26
    * ���� : ����Ŭ���ݷ� ��ϵ� ����� �ش� ������ ��Ī ������ �����Ѵ�.
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLupso_adrs_save(DOBJ dobj)
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
            dobj  = CALLupso_adrs_save_MIUD1(Conn, dobj);           //  ��ȭ��������
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
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
    public DOBJ CTLupso_adrs_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_adrs_save_MIUD1(Conn, dobj);           //  ��ȭ��������
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ��ȭ��������
    public DOBJ CALLupso_adrs_save_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_adrs_save_SEL10(Conn, dobj);           //  �湮���üũ
                if(dobj.getRetObject("SEL10").getRecord().getDouble("CNT") == 0 && !dobj.getRetObject("R").getRecord().get("MATCH_YN").equals("D") && !dobj.getRetObject("R").getRecord().get("UPSO_CD").equals(""))
                {
                    dobj  = CALLupso_adrs_save_SEL11(Conn, dobj);           //  �����ִ밪���ϱ�
                    dobj  = CALLupso_adrs_save_INS5(Conn, dobj);           //  ���ҹ湮���
                    if(!dobj.getRetObject("R").getRecord().get("BRE_REMAK").equals(""))
                    {
                        dobj  = CALLupso_adrs_save_INS10(Conn, dobj);           //  ���볻��޸�
                        dobj  = CALLupso_adrs_save_UPD6(Conn, dobj);           //  ��ȭ��������
                    }
                    else
                    {
                        dobj  = CALLupso_adrs_save_UPD6(Conn, dobj);           //  ��ȭ��������
                    }
                }
                else if(dobj.getRetObject("SEL10").getRecord().getDouble("CNT") > 0 && !dobj.getRetObject("R").getRecord().get("MATCH_YN").equals("D") && !dobj.getRetObject("R").getRecord().get("UPSO_CD").equals("") && !dobj.getRetObject("R").getRecord().get("BRE_REMAK").equals(""))
                {
                    dobj  = CALLupso_adrs_save_UPD12(Conn, dobj);           //  �湮��� �޸� ����
                    dobj  = CALLupso_adrs_save_UPD6(Conn, dobj);           //  ��ȭ��������
                }
                else
                {
                    dobj  = CALLupso_adrs_save_UPD6(Conn, dobj);           //  ��ȭ��������
                }
            }
        }
        return dobj;
    }
    // �湮���üũ
    // �湮����� �Ǿ��ִ��� Ȯ���Ѵ�.
    public DOBJ CALLupso_adrs_save_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_adrs_save_SEL10(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        rvobj.Println("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_adrs_save_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CONS_DATE = dobj.getRetObject("R").getRecord().get("CONS_DATE");   //��� �Ͻ�
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  SUBSTR(:CONS_DATE  ,1,8)   \n";
        query +=" AND  VISIT_SEQ  =  :VISIT_SEQ   \n";
        query +=" AND  JOB_GBN  =  'R' ";
        sobj.setSql(query);
        sobj.setString("CONS_DATE", CONS_DATE);               //��� �Ͻ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �����ִ밪���ϱ�
    // ���ҹ湮����� �ϷĹ�ȣ(4�ڸ�)�� �����Ѵ�.
    public DOBJ CALLupso_adrs_save_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_adrs_save_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        rvobj.Println("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_adrs_save_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CONS_DATE = dobj.getRetObject("R").getRecord().get("CONS_DATE");   //��� �Ͻ�
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(VISIT_SEQ),0)  +  1  MAX_VISIT_SEQ  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  SUBSTR(:CONS_DATE  ,1,8)   \n";
        query +=" AND  JOB_GBN  =  'R' ";
        sobj.setSql(query);
        sobj.setString("CONS_DATE", CONS_DATE);               //��� �Ͻ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ҹ湮���
    // ���ҹ湮������ �űԷ� ����Ѵ�
    public DOBJ CALLupso_adrs_save_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS5");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_adrs_save_INS5(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_adrs_save_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //����Ͻ�
        String   CONSPRES = dobj.getRetObject("GOV").getRecord().get("HAN_NM");   //�����
        String   CONS_DATE = dobj.getRetObject("R").getRecord().get("CONS_DATE");   //��� �Ͻ�
        int   CONS_SEQ = dobj.getRetObject("R").getRecord().getInt("CONS_SEQ");   //��� ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="R";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //���
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   VISIT_DAY = wutil.substr(dobj.getRetObject("R").getRecord().get("CONS_DATE"),0,8);   //�湮 ����
        int   VISIT_SEQ = dobj.getRetObject("SEL11").getRecord().getInt("MAX_VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT (VISIT_SEQ, INS_DATE, INSPRES_ID, CONS_SEQ, VISIT_DAY, CONS_DATE, JOB_GBN, UPSO_CD, CONSPRES, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, :INSPRES_ID , :CONS_SEQ , :VISIT_DAY , :CONS_DATE , :JOB_GBN , :UPSO_CD , :CONSPRES , :REMAK )";
        sobj.setSql(query);
        sobj.setString("CONSPRES", CONSPRES);               //�����
        sobj.setString("CONS_DATE", CONS_DATE);               //��� �Ͻ�
        sobj.setInt("CONS_SEQ", CONS_SEQ);               //��� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // ���볻��޸�
    // ���볻��޸������Ѵ�.
    public DOBJ CALLupso_adrs_save_INS10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS10");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS10");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_adrs_save_INS10(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS10") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_adrs_save_INS10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="R";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK = dobj.getRetObject("R").getRecord().get("BRE_REMAK");   //���
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   VISIT_DAY = wutil.substr(dobj.getRetObject("R").getRecord().get("CONS_DATE"),0,8);   //�湮 ����
        int   VISIT_NUM = 1;   //�湮 ��ȣ
        int   VISIT_SEQ = dobj.getRetObject("SEL11").getRecord().getInt("MAX_VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(:VISIT_SEQ , sysdate, :INSPRES_ID , :VISIT_DAY , :JOB_GBN , :VISIT_NUM , :UPSO_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_NUM", VISIT_NUM);               //�湮 ��ȣ
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // ��ȭ��������
    // ����,�����ڵ带 ��Ī������ ���������Ѵ�.
    public DOBJ CALLupso_adrs_save_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD6");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_adrs_save_UPD6(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_adrs_save_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   MATCH_YN = dvobj.getRecord().get("MATCH_YN");   //��Ī ����(Y/N/D)
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   CONS_DATE = dvobj.getRecord().get("CONS_DATE");   //��� �Ͻ�
        int   CONS_SEQ = dvobj.getRecord().getInt("CONS_SEQ");   //��� ����
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_ADRS_FILEINFO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , UPSO_CD=:UPSO_CD , MOD_DATE=SYSDATE , BRAN_CD=:BRAN_CD , MATCH_YN=:MATCH_YN  \n";
        query +=" where CONS_SEQ=:CONS_SEQ  \n";
        query +=" and CONS_DATE=:CONS_DATE";
        sobj.setSql(query);
        sobj.setString("MATCH_YN", MATCH_YN);               //��Ī ����(Y/N/D)
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("CONS_DATE", CONS_DATE);               //��� �Ͻ�
        sobj.setInt("CONS_SEQ", CONS_SEQ);               //��� ����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    // �湮��� �޸� ����
    // �湮����� �޸� �����Ѵ�.
    public DOBJ CALLupso_adrs_save_UPD12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD12");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD12");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_adrs_save_UPD12(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD12") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_adrs_save_UPD12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   MOD_DATE = dvobj.getRecord().get("MOD_DATE");   //���� �Ͻ�
        String   MODPRES_ID = dvobj.getRecord().get("MODPRES_ID");   //������ ID
        String   JOB_GBN ="R";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   VISIT_DAY = wutil.substr(dobj.getRetObject("R").getRecord().get("CONS_DATE"),0,8);   //�湮 ����
        int   VISIT_SEQ = dobj.getRetObject("R").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_VISIT  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MOD_DATE=:MOD_DATE , REMAK=:REMAK  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("MOD_DATE", MOD_DATE);               //���� �Ͻ�
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    //##**$$upso_adrs_save
    //##**$$upso_adrs_delete
    /* * ���α׷��� : bra01_s05
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/26
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLupso_adrs_delete(DOBJ dobj)
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
            dobj  = CALLupso_adrs_delete_MIUD4(Conn, dobj);           //  ��ȭ��������
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
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
    public DOBJ CTLupso_adrs_delete( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_adrs_delete_MIUD4(Conn, dobj);           //  ��ȭ��������
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ��ȭ��������
    public DOBJ CALLupso_adrs_delete_MIUD4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD4");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_adrs_delete_UPD1(Conn, dobj);           //  ��ȭ�����ʱ�ȭ
                dobj  = CALLupso_adrs_delete_DEL2(Conn, dobj);           //  �湮��ϻ���
                dobj  = CALLupso_adrs_delete_DEL3(Conn, dobj);           //  �޸����
            }
        }
        return dobj;
    }
    // ��ȭ�����ʱ�ȭ
    // ��ȭ������ ���� ������ Ŭ�����Ѵ�
    public DOBJ CALLupso_adrs_delete_UPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD1");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_adrs_delete_UPD1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_adrs_delete_UPD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MOD_DATE = dvobj.getRecord().get("MOD_DATE");   //���� �Ͻ�
        String   CONS_DATE = dvobj.getRecord().get("CONS_DATE");   //��� �Ͻ�
        int   CONS_SEQ = dvobj.getRecord().getInt("CONS_SEQ");   //��� ����
        String   MODPRES_ID = dvobj.getRecord().get("MODPRES_ID");   //������ ID
        String   BRAN_CD ="";   //���� �ڵ�
        String   MATCH_YN ="N";   //����
        String   UPSO_CD ="";   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_ADRS_FILEINFO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , UPSO_CD=:UPSO_CD , MOD_DATE=:MOD_DATE , BRAN_CD=:BRAN_CD , MATCH_YN=:MATCH_YN  \n";
        query +=" where CONS_SEQ=:CONS_SEQ  \n";
        query +=" and CONS_DATE=:CONS_DATE";
        sobj.setSql(query);
        sobj.setString("MOD_DATE", MOD_DATE);               //���� �Ͻ�
        sobj.setString("CONS_DATE", CONS_DATE);               //��� �Ͻ�
        sobj.setInt("CONS_SEQ", CONS_SEQ);               //��� ����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("MATCH_YN", MATCH_YN);               //����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �湮��ϻ���
    // �湮 ��� ������ ���� �Ѵ�.
    public DOBJ CALLupso_adrs_delete_DEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL2");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_adrs_delete_DEL2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL2") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_adrs_delete_DEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //���� ����
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //�湮 ����
        int   VISIT_SEQ = dvobj.getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_VISIT  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // �޸����
    // �޸�����Ѵ�
    public DOBJ CALLupso_adrs_delete_DEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL3");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_adrs_delete_DEL3(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL3") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_adrs_delete_DEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //���� ����
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //�湮 ����
        int   VISIT_SEQ = dvobj.getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_VISIT_BRE  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    //##**$$upso_adrs_delete
    //##**$$upso_adrs_staff
    /* * ���α׷��� : bra01_s05
    * �ۼ��� : �̱���
    * �ۼ��� : 2009/09/07
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLupso_adrs_staff(DOBJ dobj)
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
            dobj  = CALLupso_adrs_staff_SEL3(Conn, dobj);           //  ����������ȸ
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
    public DOBJ CTLupso_adrs_staff( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_adrs_staff_SEL3(Conn, dobj);           //  ����������ȸ
        return dobj;
    }
    // ����������ȸ
    // �ش� ����� ���������� ��ȸ�Ѵ�.
    public DOBJ CALLupso_adrs_staff_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_adrs_staff_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_adrs_staff_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_NUM = dobj.getRetObject("S").getRecord().get("STAFF_NUM");   //�����ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.STAFF_NUM  STAFF_CD  ,  B.HAN_NM  STAFF_NM  ,  B.HAN_NM  ||  '('  ||  NVL(C.IPPBX_USER_ID,'�̵��')  ||  ')'  CALL_NM  ,  B.ETCOM_DAY  ,  A.GIBU,  NVL(C.IPPBX_USER_ID,'�̵��')  IPPBX_USER_ID  FROM  INSA.TCPM_DEPT  A  ,  INSA.TINS_MST01  B  ,  FIDU.TENV_MEMBER  C  WHERE  A.DEPT_CD  =  B.DEPT_CD   \n";
        query +=" AND  A.GIBU  =   \n";
        query +=" (SELECT  A.GIBU  FROM  INSA.TCPM_DEPT  A  ,  INSA.TINS_MST01  B  WHERE  A.DEPT_CD  =  B.DEPT_CD   \n";
        query +=" AND  B.STAFF_NUM  =  :STAFF_NUM   \n";
        query +=" AND  B.RETIRE_DAY  IS  NULL)   \n";
        query +=" AND  B.RETIRE_DAY  IS  NULL   \n";
        query +=" AND  B.STAFF_NUM  =  C.STAFF_NUM(+) ";
        sobj.setSql(query);
        sobj.setString("STAFF_NUM", STAFF_NUM);               //�����ȣ
        return sobj;
    }
    //##**$$upso_adrs_staff
    //##**$$end
}
