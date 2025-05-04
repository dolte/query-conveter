
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s09_1
{
    public bra01_s09_1()
    {
    }
    //##**$$upso_visit_d_list
    /* * ���α׷��� : bra01_s09
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/25
    * ���� : ���ҹ湮 �� ������ ��ȸ�Ѵ�.
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLupso_visit_d_list(DOBJ dobj)
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
            dobj  = CALLupso_visit_d_list_SEL1(Conn, dobj);           //  ���ҹ湮�󼼸���Ʈ
            dobj  = CALLupso_visit_d_list_SEL2(Conn, dobj);           //  ���� ÷������ ���
            dobj  = CALLupso_visit_d_list_MPD3(Conn, dobj);           //  ����
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
    public DOBJ CTLupso_visit_d_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_visit_d_list_SEL1(Conn, dobj);           //  ���ҹ湮�󼼸���Ʈ
        dobj  = CALLupso_visit_d_list_SEL2(Conn, dobj);           //  ���� ÷������ ���
        dobj  = CALLupso_visit_d_list_MPD3(Conn, dobj);           //  ����
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ���ҹ湮�󼼸���Ʈ
    // ���ҹ湮 �� ������ ��ȸ�Ѵ�
    public DOBJ CALLupso_visit_d_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_visit_d_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_d_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int   VISIT_SEQ = dobj.getRetObject("S").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        String   VISIT_DAY = dobj.getRetObject("S").getRecord().get("VISIT_DAY");   //�湮 ����
        String   JOB_GBN = dobj.getRetObject("S").getRecord().get("JOB_GBN");   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  VISIT_DAY  ,  JOB_GBN  ,  VISIT_NUM  ,  REMAK  ,  VISIT_SEQ  FROM  GIBU.TBRA_UPSO_VISIT_BRE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  JOB_GBN  =  :JOB_GBN   \n";
        query +=" AND  VISIT_SEQ  =  :VISIT_SEQ ";
        sobj.setSql(query);
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� ÷������ ���
    public DOBJ CALLupso_visit_d_list_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_visit_d_list_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_d_list_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int   VISIT_SEQ = dobj.getRetObject("S").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        String   VISIT_DAY = dobj.getRetObject("S").getRecord().get("VISIT_DAY");   //�湮 ����
        String   JOB_GBN = dobj.getRetObject("S").getRecord().get("JOB_GBN");   //���� ����
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  SEQ  ,  FILE_ROUT  PATH  ,  FILE_TEMPNM  UNIFILENAME  ,  FILE_NM  AS  UPFILENAME  ,  FILES  AS  ATTCH_FILE_CTENT  ,  LENGTH(FILE_NM)  AS  PROF_CHG_FILENM_LEN  FROM  GIBU.TBRA_UPSO_VISIT_ATTCH  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  VISIT_SEQ  =  :VISIT_SEQ   \n";
        query +=" AND  JOB_GBN  =  :JOB_GBN ";
        sobj.setSql(query);
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ����
    public DOBJ CALLupso_visit_d_list_MPD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD3");
        VOBJ dvobj = dobj.getRetObject("SEL2");         //���� ÷������ ��Ͽ��� ������Ų OBJECT�Դϴ�.(CALLupso_visit_d_list_SEL2)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(!dvobj.getRecord().get("PROF_CHG_FILENM_LEN").equals("0"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_visit_d_list_SEL6(Conn, dobj);           //  �������ϻ���
            }
        }
        return dobj;
    }
    // �������ϻ���
    // THOM_PRODSTTNTLETTER_ATTCHFILE�� �̿��Ͽ� �������� ����
    public DOBJ CALLupso_visit_d_list_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_visit_d_list_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        String fullname="";
        rvobj.first();
        while(rvobj.next())
        {
            wutil.makeFileOverwirte( dobj.getRetObject("R").getRecord().get("PATH"), dobj.getRetObject("R").getRecord().get("UNIFILENAME"),rvobj.getRecord().getBytes("FILES"));
        }
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_d_list_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   MNG_NUM = dobj.getRetObject("R").getRecord().getDouble("MNG_NUM");   //������ȣ
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  FILE_ROUT  ,  FILE_TEMPNM  ,  FILE_NM  ,  FILES  FROM  GIBU.TBRA_CONFIRM_DOC_ATTCH  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  SEQ  =  :SEQ   \n";
        query +=" AND  MNG_NUM  =  :MNG_NUM ";
        sobj.setSql(query);
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    //##**$$upso_visit_d_list
    //##**$$upso_visit_list
    /* * ���α׷��� : bra01_s09
    * �ۼ��� : ������
    * �ۼ��� : 2009/12/04
    * ���� : ��ϵ� ���ҹ湮 ������ ��ȸ�Ѵ�.
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLupso_visit_list(DOBJ dobj)
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
            dobj  = CALLupso_visit_list_SEL1(Conn, dobj);           //  ���ҹ湮����Ʈ
            dobj  = CALLupso_visit_list_SEL2(Conn, dobj);           //  ��������
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
    public DOBJ CTLupso_visit_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_visit_list_SEL1(Conn, dobj);           //  ���ҹ湮����Ʈ
        dobj  = CALLupso_visit_list_SEL2(Conn, dobj);           //  ��������
        return dobj;
    }
    // ���ҹ湮����Ʈ
    // ����,�ְ��������,����Ŭ����, ���Ҹ� ����� ��ϵ� ������ ��ȸ�Ѵ�.
    public DOBJ CALLupso_visit_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_visit_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dvobj.getRecord().get("START_DAY");   //������
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   END_DAY = dvobj.getRecord().get("END_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.VISIT_DAY  ,  A.VISIT_TIME  ,  A.JOB_GBN  ,  B.CODE_NM  ,  A.CONSPRES  ,  A.REMAK  ,  A.CONS_DATE  ,  A.CONS_SEQ  ,  C.FILE_ROUT,  C.FILE_NM  ,  A.VISIT_SEQ  ,  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  AS  INS_DATE  ,  TO_CHAR(A.INS_DATE,  'yyyymmddhh24miss')  AS  INS_DATE2  ,  D.FILE_ROUT  AS  IMG_ROUT  ,  D.FILE_TEMPNM  AS  IMG_TEMPNM  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  FIDU.TENV_CODE  B  ,  GIBU.TBRA_UPSO_ADRS_FILEINFO  C  ,  GIBU.TBRA_UPSO_VISIT_ATTCH  D  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.HIGH_CD  =  '00198'   \n";
        query +=" AND  A.JOB_GBN  =  B.CODE_CD  AND	A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  A.CONS_DATE  =  C.CONS_DATE(+)   \n";
        query +=" AND  A.CONS_SEQ  =  C.CONS_SEQ(+)   \n";
        query +=" AND  A.UPSO_CD  =  D.UPSO_CD(+)   \n";
        query +=" AND  A.VISIT_DAY  =  D.VISIT_DAY(+)   \n";
        query +=" AND  A.VISIT_SEQ  =  D.VISIT_SEQ(+)   \n";
        query +=" AND  A.JOB_GBN  =  D.JOB_GBN(+)  ORDER  BY  VISIT_DAY  DESC ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("END_DAY", END_DAY);               //������
        return sobj;
    }
    // ��������
    // ������ �⺻������ ��ȸ�Ѵ�.
    public DOBJ CALLupso_visit_list_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_visit_list_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_list_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.MNGEMSTR_NM  ,  XA.PERMMSTR_NM  ,  XA.STAFF_CD  ,  XC.HAN_NM  STAFF_NM  ,  XA.UPSO_PHON  ,  XA.UPSO_NEW_ZIP  UPSO_ZIP  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,  '',  '',  ',  '||XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  ADDR  ,  TRIM(XB.BSTYP_CD)  ||  XB.UPSO_GRAD  GRAD  ,  XB.GRADNM  ,  TO_CHAR(XB.MONPRNCFEE,'999,999,999,999')  MONPRNCFEE  ,  XA.UPSO_CD  ,  XA.UPSO_NM  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  :UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  XB  ,  INSA.TINS_MST01  XC  WHERE  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.STAFF_NUM(+)  =  XA.STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$upso_visit_list
    //##**$$end
}
