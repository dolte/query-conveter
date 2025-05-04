
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s29
{
    public bra01_s29()
    {
    }
    //##**$$select_letter
    /*
    */
    public DOBJ CTLselect_letter(DOBJ dobj)
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
            dobj  = CALLselect_letter_SEL1(Conn, dobj);           //  �����ȸ
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
    public DOBJ CTLselect_letter( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLselect_letter_SEL1(Conn, dobj);           //  �����ȸ
        return dobj;
    }
    // �����ȸ
    public DOBJ CALLselect_letter_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLselect_letter_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_letter_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   OPT_LIST = dobj.getRetObject("S").getRecord().get("OPT_LIST");   //��� ����
        String   TO = dobj.getRetObject("S").getRecord().get("TO");   //��������
        String   UPSO_NM = dobj.getRetObject("S").getRecord().get("UPSO_NM");   //���� ��
        String   STAT_GBN = dobj.getRetObject("S").getRecord().get("STAT_GBN");   //ó������
        String   REPPRES = dobj.getRetObject("S").getRecord().get("REPPRES");   //��ǥ��
        String   FROM = dobj.getRetObject("S").getRecord().get("FROM");   //��������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT LETTER_NUM, POST_NUM, ADDR || DECODE(ADDR_DETED, '', '', ', ' || ADDR_DETED) AS ADDR, UPSO_NM, BSTYP, REPPRES, INS_NUM, BIOWN_NUM, AREA1, PHON_NUM, EMAIL_ADDR, TO_CHAR(INS_DATE, 'YYYY-MM-DD') AS INS_DATE, TO_CHAR(COMPL_DAY,'YYYY-MM-DD') AS COMPL_DAY, STAT_GBN, BRAN_CD, REMAK  ";
        query +=" FROM HOMP.THOM_UPSO_LETTER  ";
        query +=" WHERE 1=1  ";
        query +=" AND TO_CHAR(INS_DATE,'YYYYMMDD') BETWEEN :FROM  ";
        query +=" AND :TO  ";
        if( !UPSO_NM.equals("") )
        {
            query +=" AND UPSO_NM LIKE '%'|| :UPSO_NM ||'%'  ";
        }
        if( !REPPRES.equals("") )
        {
            query +=" AND REPPRES LIKE '%'|| :REPPRES ||'%'  ";
        }
        if( !STAT_GBN.equals("") )
        {
            query +=" AND STAT_GBN LIKE :STAT_GBN || '%'  ";
        }
        if( !OPT_LIST.equals("") )
        {
            query +=" AND BRAN_CD LIKE :OPT_LIST || '%'  ";
        }
        query +=" ORDER BY LETTER_NUM DESC  ";
        sobj.setSql(query);
        if(!OPT_LIST.equals(""))
        {
            sobj.setString("OPT_LIST", OPT_LIST);               //��� ����
        }
        sobj.setString("TO", TO);               //��������
        if(!UPSO_NM.equals(""))
        {
            sobj.setString("UPSO_NM", UPSO_NM);               //���� ��
        }
        if(!STAT_GBN.equals(""))
        {
            sobj.setString("STAT_GBN", STAT_GBN);               //ó������
        }
        if(!REPPRES.equals(""))
        {
            sobj.setString("REPPRES", REPPRES);               //��ǥ��
        }
        sobj.setString("FROM", FROM);               //��������
        return sobj;
    }
    //##**$$select_letter
    //##**$$set_bran_cd
    /*
    */
    public DOBJ CTLset_bran_cd(DOBJ dobj)
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
            dobj  = CALLset_bran_cd_UPD1(Conn, dobj);           //  ������� ����
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
    public DOBJ CTLset_bran_cd( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLset_bran_cd_UPD1(Conn, dobj);           //  ������� ����
        return dobj;
    }
    // ������� ����
    public DOBJ CALLset_bran_cd_UPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLset_bran_cd_UPD1(dobj, dvobj);
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
    private SQLObject SQLset_bran_cd_UPD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   LETTER_NUM = dvobj.getRecord().get("LETTER_NUM");   //��û�� ��ȣ
        String   STAT_GBN ="2";   //ó������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update HOMP.THOM_UPSO_LETTER  \n";
        query +=" set BRAN_CD=:BRAN_CD , STAT_GBN=:STAT_GBN  \n";
        query +=" where LETTER_NUM=:LETTER_NUM";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("LETTER_NUM", LETTER_NUM);               //��û�� ��ȣ
        sobj.setString("STAT_GBN", STAT_GBN);               //ó������
        return sobj;
    }
    //##**$$set_bran_cd
    //##**$$end
}
