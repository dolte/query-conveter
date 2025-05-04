
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s18
{
    public bra03_s18()
    {
    }
    //##**$$search_test2
    /*
    */
    public DOBJ CTLsearch_test2(DOBJ dobj)
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
            dobj  = CALLsearch_test2_SEL1(Conn, dobj);           //  �����ȸ
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
    public DOBJ CTLsearch_test2( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_test2_SEL1(Conn, dobj);           //  �����ȸ
        return dobj;
    }
    // �����ȸ
    public DOBJ CALLsearch_test2_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsearch_test2_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_test2_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAT_GBN = dobj.getRetObject("S").getRecord().get("STAT_GBN");   //ó������
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  AS  INS_DATE  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.SEQ  ,  A.APPLICATION_GBN  ,  DECODE(A.RECEPTION_GBN,  '5',   \n";
        query +=" (SELECT  BANK_NM  FROM  ACCT.TCAC_BANK  WHERE  USE_YN  =  'Y'   \n";
        query +=" AND  BANK_CD  =  A.BANK_CD),  '7',  DECODE(A.CARD_GBN,  'WIN','�Ｚī��',  'LGC',  '����ī��'))  AS  BANK_NM  ,  DECODE(A.RECEPTION_GBN,  '5',  A.AUTO_ACCNNUM,  '7',  A.CARD_NUM)  AS  AUTO_ACCNNUM  ,  A.PAYPRES_NM  ,  DECODE(A.CONFIRM_ID,  NULL,  A.RESINUM,  SUBSTR(A.RESINUM,  1,7)||'______')  AS  RESINUM  ,  A.RELATION  ,  A.APPTN_DAY  ,  A.INSPRES_ID  ,  A.STAFF_CD  ,  A.CONFIRM_ID  ,  A.RECEPTION_GBN  ,  A.PHON_NUM  ,  ''  AS  REMAK  ,  DECODE(A.REMAK,  '01',  'combo',  'text')  AS  DISPLAY  ,  DECODE(A.CONFIRM_ID,  NULL,  '',  DECODE(A.PROC_DAY,  NULL,  '1',  '�ڷ�۽�'))  AS  CONFIRM_YN  ,  A.PROC_DAY  FROM  GIBU.TBRA_UPSO_AUTO_APPLICATION  A,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.RECEPTION_GBN  IN  ('7')   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.APPLICATION_GBN  <>  '19'   \n";
        query +=" AND  A.REMAK  =  :STAT_GBN  ORDER  BY  A.INS_DATE ";
        sobj.setSql(query);
        sobj.setString("STAT_GBN", STAT_GBN);               //ó������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$search_test2
    //##**$$save
    /*
    */
    public DOBJ CTLsave(DOBJ dobj)
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
            dobj  = CALLsave_MIUD1(Conn, dobj);           //  �б�
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
    public DOBJ CTLsave( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsave_MIUD1(Conn, dobj);           //  �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �б�
    public DOBJ CALLsave_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLsave_UPD5(Conn, dobj);           //  ���� REMAK
            }
        }
        return dobj;
    }
    // ���� REMAK
    public DOBJ CALLsave_UPD5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_UPD5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_UPD5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUTO_APPLICATION  \n";
        query +=" set REMAK=:REMAK  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$save
    //##**$$end
}
