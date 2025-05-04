
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_p21
{
    public bra10_p21()
    {
    }
    //##**$$sel_stomu_upso_pop
    /*
    */
    public DOBJ CTLsel_stomu_upso_pop(DOBJ dobj)
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
            dobj  = CALLsel_stomu_upso_pop_SEL1(Conn, dobj);           //  ������ȸ
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
    public DOBJ CTLsel_stomu_upso_pop( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_stomu_upso_pop_SEL1(Conn, dobj);           //  ������ȸ
        return dobj;
    }
    // ������ȸ
    public DOBJ CALLsel_stomu_upso_pop_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_stomu_upso_pop_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_stomu_upso_pop_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //���� �����ȣ
        String   REPPRES_NM = dobj.getRetObject("S").getRecord().get("REPPRES_NM");   //��ǥ�� ��
        String   DONG = dobj.getRetObject("S").getRecord().get("DONG");   //��
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   UPSO_PHON = dobj.getRetObject("S").getRecord().get("UPSO_PHON");   //���� ��ȭ
        String   UPSO_NM = dobj.getRetObject("S").getRecord().get("UPSO_NM");   //���� ��
        String   STAT_GBN = dobj.getRetObject("S").getRecord().get("STAT_GBN");   //ó������
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   BIOWN_NUM = dobj.getRetObject("S").getRecord().get("BIOWN_NUM");   //����� ��ȣ
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT BRAN_CD , UPSO_CD , UPSO_NM , BSTYP_CD , UPSO_BRA , UPSO_ADDR , UPSO_NEW_ADDR1 , UPSO_NEW_ADDR2 , UPSO_REF_INFO , UPSO_NEW_ADDR1 || (CASE WHEN UPSO_NEW_ADDR2 IS NULL THEN '' ELSE ', ' || UPSO_NEW_ADDR2 END) AS ADDR , STAT_GBN , NEW_DAY , CLSED_DAY , REPPRES_NM , UPSO_PHON , BIOWN_NUM , SITE_AREA , GIBU.FT_GET_STOMU_MONPRNCFEE(A.UPSO_CD) AS MONPRNCFEE , GIBU.FT_GET_STOMU_LOCATION(UPSO_CD) AS LOC_GBN , BSCON_CD  ";
        query +=" FROM GIBU.TBRA_STOMU_UPSO A  ";
        query +=" WHERE BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        if( !BSCON_CD.equals("") )
        {
            query +=" AND BSCON_CD = :BSCON_CD  ";
        }
        if( !STAT_GBN.equals("") )
        {
            query +=" AND STAT_GBN = :STAT_GBN  ";
        }
        if( !UPSO_CD.equals("") )
        {
            query +=" AND UPSO_CD = :UPSO_CD  ";
        }
        if( !UPSO_NM.equals("") )
        {
            query +=" AND UPSO_NM LIKE '%' || :UPSO_NM || '%'  ";
        }
        if( !UPSO_PHON.equals("") )
        {
            query +=" AND UPSO_PHON LIKE '%' || :UPSO_PHON || '%'  ";
        }
        if( !REPPRES_NM.equals("") )
        {
            query +=" AND REPPRES_NM LIKE '%' || :REPPRES_NM || '%'  ";
        }
        if( !BIOWN_NUM.equals("") )
        {
            query +=" AND BIOWN_NUM LIKE '%' || :BIOWN_NUM || '%'  ";
        }
        if( !MNG_ZIP.equals("") )
        {
            query +=" AND UPSO_NEW_ZIP = :MNG_ZIP  ";
        }
        if( !DONG.equals("") )
        {
            query +=" AND UPSO_REF_INFO LIKE '%' || :DONG || '%'  ";
        }
        query +=" ORDER BY BSCON_CD, BRAN_CD, BSTYP_CD, UPSO_CD  ";
        sobj.setSql(query);
        if(!MNG_ZIP.equals(""))
        {
            sobj.setString("MNG_ZIP", MNG_ZIP);               //���� �����ȣ
        }
        if(!REPPRES_NM.equals(""))
        {
            sobj.setString("REPPRES_NM", REPPRES_NM);               //��ǥ�� ��
        }
        if(!DONG.equals(""))
        {
            sobj.setString("DONG", DONG);               //��
        }
        if(!UPSO_CD.equals(""))
        {
            sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        }
        if(!UPSO_PHON.equals(""))
        {
            sobj.setString("UPSO_PHON", UPSO_PHON);               //���� ��ȭ
        }
        if(!UPSO_NM.equals(""))
        {
            sobj.setString("UPSO_NM", UPSO_NM);               //���� ��
        }
        if(!STAT_GBN.equals(""))
        {
            sobj.setString("STAT_GBN", STAT_GBN);               //ó������
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        if(!BIOWN_NUM.equals(""))
        {
            sobj.setString("BIOWN_NUM", BIOWN_NUM);               //����� ��ȣ
        }
        if(!BSCON_CD.equals(""))
        {
            sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        }
        return sobj;
    }
    //##**$$sel_stomu_upso_pop
    //##**$$end
}
