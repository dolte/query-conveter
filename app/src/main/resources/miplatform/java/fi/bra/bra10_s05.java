
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_s05
{
    public bra10_s05()
    {
    }
    //##**$$new_bonus_cal
    /* * ���α׷��� : bra10_s05
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/23
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLnew_bonus_cal(DOBJ dobj)
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
            dobj  = CALLnew_bonus_cal_SEL1(Conn, dobj);           //  ��ȸ�� ����
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
    public DOBJ CTLnew_bonus_cal( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLnew_bonus_cal_SEL1(Conn, dobj);           //  ��ȸ�� ����
        return dobj;
    }
    // ��ȸ�� ����
    public DOBJ CALLnew_bonus_cal_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLnew_bonus_cal_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnew_bonus_cal_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NEW_YRMN = dobj.getRetObject("S").getRecord().get("NEW_YRMN");   //���� ���
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        double   RATE = dobj.getRetObject("S").getRecord().getDouble("RATE");   //����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.BRAN_CD  ,  XB.DEPT_NM  BRAN_NM  ,  XA.STAFF_CD  ,  XA.STAFF_NM  ,  XA.NEW_UPSO_CNT  ,  XA.NEW_UPSO_TOTAMT  ,  XA.NEW_UPSO_AMT  FROM  (   \n";
        query +=" SELECT  DECODE(TA.BRAN_CD,  'C'  ,'B',  'H',  'I',  'J',  'K',  'O','K',  TA.BRAN_CD)  BRAN_CD  ,  TA.STAFF_CD  STAFF_CD  ,  MAX(TB.HAN_NM)  STAFF_NM  ,  COUNT(*)  NEW_UPSO_CNT  ,  SUM(NVL(TD.MONPRNCFEE,0))  NEW_UPSO_TOTAMT  ,  TRUNC(SUM(NVL(TD.MONPRNCFEE,0))  *  :RATE  /  100,  0)  NEW_UPSO_AMT  FROM  GIBU.TBRA_UPSO  TA  ,  INSA.TINS_MST01  TB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  TD  WHERE  TA.NEW_DAY  LIKE  :NEW_YRMN||'%'   \n";
        query +=" AND  TA.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  TA.BRAN_CD,  'AL',  TA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  TA.BEFORE_UPSO_CD  IS  NULL   \n";
        query +=" AND  TD.BSTYP_CD  NOT  IN  ('v')   \n";
        query +=" AND  TB.STAFF_NUM(+)  =  TA.STAFF_CD   \n";
        query +=" AND  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(TA.CLSBS_INS_DAY,1,6),'  ')  >=:NEW_YRMN)   \n";
        query +=" AND  TD.UPSO_CD  =  TA.UPSO_CD  GROUP  BY  TA.BRAN_CD,  TA.STAFF_CD  )  XA  ,  INSA.TCPM_DEPT  XB  WHERE  XB.GIBU(+)  =  XA.BRAN_CD  ORDER  BY  BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("NEW_YRMN", NEW_YRMN);               //���� ���
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setDouble("RATE", RATE);               //����
        return sobj;
    }
    //##**$$new_bonus_cal
    //##**$$new_bonus_sel
    /* * ���α׷��� : bra10_s05
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/20
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLnew_bonus_sel(DOBJ dobj)
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
            dobj  = CALLnew_bonus_sel_SEL1(Conn, dobj);           //  ��ȸ
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
    public DOBJ CTLnew_bonus_sel( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLnew_bonus_sel_SEL1(Conn, dobj);           //  ��ȸ
        return dobj;
    }
    // ��ȸ
    public DOBJ CALLnew_bonus_sel_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLnew_bonus_sel_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnew_bonus_sel_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NEW_YRMN = dobj.getRetObject("S").getRecord().get("NEW_YRMN");   //���� ���
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BRAN_CD  ,  C.DEPT_NM  BRAN_NM  ,  A.STAFF_CD  ,  B.HAN_NM  STAFF_NM  ,  A.NEW_UPSO_CNT  ,  A.NEW_UPSO_TOTAMT  ,  A.NEW_UPSO_AMT  ,  A.RATE  FROM  GIBU.TBRA_NEW_UPSO_BONUS_MNG  A  ,  INSA.TINS_MST01  B  ,  INSA.TCPM_DEPT  C  WHERE  A.NEW_YRMN  =  :NEW_YRMN   \n";
        query +=" AND  A.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  A.BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  B.STAFF_NUM  =  A.STAFF_CD   \n";
        query +=" AND  C.GIBU  =  A.BRAN_CD  ORDER  BY  A.BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("NEW_YRMN", NEW_YRMN);               //���� ���
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$new_bonus_sel
    //##**$$new_bonus_insert
    /* * ���α׷��� : bra10_s05
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/20
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLnew_bonus_insert(DOBJ dobj)
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
            if(!dobj.getRetObject("S1").getRecord().get("BRAN_CD").equals("") && !dobj.getRetObject("S1").getRecord().get("BRAN_CD").equals("AL"))
            {
                dobj  = CALLnew_bonus_insert_MPD6(Conn, dobj);
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
                dobj  = CALLnew_bonus_insert_INS2(Conn, dobj);           //  ��������
            }
            else
            {
                dobj  = CALLnew_bonus_insert_DEL6(Conn, dobj);           //  ��ü����
                dobj  = CALLnew_bonus_insert_INS10(Conn, dobj);           //  ��������
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
    public DOBJ CTLnew_bonus_insert( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if(!dobj.getRetObject("S1").getRecord().get("BRAN_CD").equals("") && !dobj.getRetObject("S1").getRecord().get("BRAN_CD").equals("AL"))
        {
            dobj  = CALLnew_bonus_insert_MPD6(Conn, dobj);
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLnew_bonus_insert_INS2(Conn, dobj);           //  ��������
        }
        else
        {
            dobj  = CALLnew_bonus_insert_DEL6(Conn, dobj);           //  ��ü����
            dobj  = CALLnew_bonus_insert_INS10(Conn, dobj);           //  ��������
        }
        return dobj;
    }
    public DOBJ CALLnew_bonus_insert_MPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD6");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLnew_bonus_insert_DEL1(Conn, dobj);           //  ���κ�����
            }
        }
        return dobj;
    }
    // ���κ�����
    public DOBJ CALLnew_bonus_insert_DEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL1");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLnew_bonus_insert_DEL1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnew_bonus_insert_DEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   NEW_YRMN = dvobj.getRecord().get("NEW_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_NEW_UPSO_BONUS_MNG  \n";
        query +=" where NEW_YRMN=:NEW_YRMN  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("NEW_YRMN", NEW_YRMN);               //���� ���
        return sobj;
    }
    // ��������
    public DOBJ CALLnew_bonus_insert_INS2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLnew_bonus_insert_INS2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS2") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnew_bonus_insert_INS2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        double   RATE = dvobj.getRecord().getDouble("RATE");   //����
        double   NEW_UPSO_AMT = dvobj.getRecord().getDouble("NEW_UPSO_AMT");   //�ű� ���� �ݾ�
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   NEW_YRMN = dvobj.getRecord().get("NEW_YRMN");   //���� ���
        double   NEW_UPSO_TOTAMT = dvobj.getRecord().getDouble("NEW_UPSO_TOTAMT");   //�ű� ���� �ѱݾ�
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        int   NEW_UPSO_CNT = dvobj.getRecord().getInt("NEW_UPSO_CNT");   //�ű� ���� ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_NEW_UPSO_BONUS_MNG (NEW_UPSO_CNT, INS_DATE, INSPRES_ID, STAFF_CD, NEW_UPSO_TOTAMT, NEW_YRMN, BRAN_CD, NEW_UPSO_AMT, RATE)  \n";
        query +=" values(:NEW_UPSO_CNT , SYSDATE, :INSPRES_ID , :STAFF_CD , :NEW_UPSO_TOTAMT , :NEW_YRMN , :BRAN_CD , :NEW_UPSO_AMT , :RATE )";
        sobj.setSql(query);
        sobj.setDouble("RATE", RATE);               //����
        sobj.setDouble("NEW_UPSO_AMT", NEW_UPSO_AMT);               //�ű� ���� �ݾ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("NEW_YRMN", NEW_YRMN);               //���� ���
        sobj.setDouble("NEW_UPSO_TOTAMT", NEW_UPSO_TOTAMT);               //�ű� ���� �ѱݾ�
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setInt("NEW_UPSO_CNT", NEW_UPSO_CNT);               //�ű� ���� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    // ��ü����
    public DOBJ CALLnew_bonus_insert_DEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL6");
        VOBJ dvobj = dobj.getRetObject("S1");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLnew_bonus_insert_DEL6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnew_bonus_insert_DEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NEW_YRMN = dvobj.getRecord().get("NEW_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_NEW_UPSO_BONUS_MNG  \n";
        query +=" where NEW_YRMN=:NEW_YRMN";
        sobj.setSql(query);
        sobj.setString("NEW_YRMN", NEW_YRMN);               //���� ���
        return sobj;
    }
    // ��������
    public DOBJ CALLnew_bonus_insert_INS10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS10");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLnew_bonus_insert_INS10(dobj, dvobj);
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
    private SQLObject SQLnew_bonus_insert_INS10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        double   RATE = dvobj.getRecord().getDouble("RATE");   //����
        double   NEW_UPSO_AMT = dvobj.getRecord().getDouble("NEW_UPSO_AMT");   //�ű� ���� �ݾ�
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   NEW_YRMN = dvobj.getRecord().get("NEW_YRMN");   //���� ���
        double   NEW_UPSO_TOTAMT = dvobj.getRecord().getDouble("NEW_UPSO_TOTAMT");   //�ű� ���� �ѱݾ�
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        int   NEW_UPSO_CNT = dvobj.getRecord().getInt("NEW_UPSO_CNT");   //�ű� ���� ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_NEW_UPSO_BONUS_MNG (NEW_UPSO_CNT, INS_DATE, INSPRES_ID, STAFF_CD, NEW_UPSO_TOTAMT, NEW_YRMN, BRAN_CD, NEW_UPSO_AMT, RATE)  \n";
        query +=" values(:NEW_UPSO_CNT , SYSDATE, :INSPRES_ID , :STAFF_CD , :NEW_UPSO_TOTAMT , :NEW_YRMN , :BRAN_CD , :NEW_UPSO_AMT , :RATE )";
        sobj.setSql(query);
        sobj.setDouble("RATE", RATE);               //����
        sobj.setDouble("NEW_UPSO_AMT", NEW_UPSO_AMT);               //�ű� ���� �ݾ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("NEW_YRMN", NEW_YRMN);               //���� ���
        sobj.setDouble("NEW_UPSO_TOTAMT", NEW_UPSO_TOTAMT);               //�ű� ���� �ѱݾ�
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setInt("NEW_UPSO_CNT", NEW_UPSO_CNT);               //�ű� ���� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    //##**$$new_bonus_insert
    //##**$$end
}
