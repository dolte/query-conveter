
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s10_2
{
    public bra03_s10_2()
    {
    }
    //##**$$indtnpaper_list
    /* * ���α׷��� : bra03_s10
    * �ۼ��� : ������
    * �ۼ��� : 2009/10/22
    * ����    : ��ϵ� ��������, MICR ����Ʈ�� ��ȸ�Ѵ�.
    Input :
    BRAN_CD (���� �ڵ�)
    CRET_GBN (OCR/MICR ���� ����)
    DEMD_YRMN (û�� ���)
    */
    public DOBJ CTLindtnpaper_list(DOBJ dobj)
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
            dobj  = CALLindtnpaper_list_SEL1(Conn, dobj);           //  ����Ʈ
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
    public DOBJ CTLindtnpaper_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLindtnpaper_list_SEL1(Conn, dobj);           //  ����Ʈ
        return dobj;
    }
    // ����Ʈ
    public DOBJ CALLindtnpaper_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLindtnpaper_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtnpaper_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CRET_GBN = dobj.getRetObject("S").getRecord().get("CRET_GBN");   //OCR/MICR ���� ����
        String   DEMD_FROM = dobj.getRetObject("S").getRecord().get("DEMD_FROM");   //û��������
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   DEMD_TO = dobj.getRetObject("S").getRecord().get("DEMD_TO");   //û��������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XB.UPSO_CD  ,  XB.UPSO_NM  ,  DECODE(XB.PAYPRES_GBN,  'B',  XB.MNGEMSTR_NM,  XB.PERMMSTR_NM)  PAYPRES_NM  ,  DECODE(XB.MAIL_RCPT,  'U',  XB.UPSO_NEW_ADDR1  ||  DECODE(XB.UPSO_NEW_ADDR2,  '',  '',  ',  '||XB.UPSO_NEW_ADDR2)  ||  XB.UPSO_REF_INFO,  'B',  XB.MNGEMSTR_NEW_ADDR1  ||  DECODE(XB.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '||XB.MNGEMSTR_NEW_ADDR2)  ||  XB.MNGEMSTR_REF_INFO,    XB.PERMMSTR_NEW_ADDR1  ||  DECODE(XB.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '||XB.PERMMSTR_NEW_ADDR2)  ||  XB.PERMMSTR_REF_INFO  )  UPSO_ADDR  ,  DECODE(XB.MAIL_RCPT,  'U',  XB.UPSO_NEW_ZIP,  'B',  XB.MNGEMSTR_NEW_ZIP,  XB.PERMMSTR_NEW_ZIP)  UPSO_ZIP  ,  XA.MONPRNCFEE  ,  XA.TOT_DEMD_AMT  -  (XA.TOT_ADDT_AMT  +  XA.TOT_EADDT_AMT)  -  XA.MONPRNCFEE  NONPY_AMT  ,  XA.TOT_ADDT_AMT  +  XA.TOT_EADDT_AMT  TOT_ADDT_AMT  ,  XA.TOT_DEMD_AMT  ,  XA.START_YRMN  ,  XA.END_YRMN  ,  XC.BIPLC_NM  BRAN_NM  ,  XC.ADDR||'  '||XC.HNM  BRAN_ADDR  ,  XC.POST_NUM  ,  XC.PHON_NUM  BRAN_PHON  ,  XC.FAX_NUM  BRAN_FAX  ,  XD.GRADNM  ,  XA.DEMD_DAY  ,  XA.DEMD_NUM  ,  DECODE(XA.PRNT_DAY,  NULL,  0,  1)  PRINTED  ,  XB.MAIL_RCPT  ,  XB.CLIENT_NUM  ,  DECODE(XA.INSPRES_ID,  'DEMD_MICR_OPEN',  '�ڵ�',  '')  AS  AUTO_YN  FROM  GIBU.TBRA_DEMD_INDTN  XA  ,  GIBU.TBRA_UPSO  XB  ,  INSA.TCPM_BIPLC  XC  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZC.GRADNM  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XD  WHERE  XB.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.DEMD_DAY  BETWEEN  :DEMD_FROM   \n";
        query +=" AND  :DEMD_TO   \n";
        query +=" AND  XA.CRET_GBN  =  :CRET_GBN   \n";
        query +=" AND  XA.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XD.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XC.GIBU  =  XB.BRAN_CD   \n";
        query +=" AND  XA.INSPRES_ID  NOT  IN  ('DEMD_MICR_REPT',  'DEMD_MICR')  ORDER  BY  DEMD_DAY,  DEMD_NUM ";
        sobj.setSql(query);
        sobj.setString("CRET_GBN", CRET_GBN);               //OCR/MICR ���� ����
        sobj.setString("DEMD_FROM", DEMD_FROM);               //û��������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("DEMD_TO", DEMD_TO);               //û��������
        return sobj;
    }
    //##**$$indtnpaper_list
    //##**$$end
}
