
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
    /* * 프로그램명 : bra03_s10
    * 작성자 : 박태현
    * 작성일 : 2009/10/22
    * 설명    : 등록된 개별지로, MICR 리스트를 조회한다.
    Input :
    BRAN_CD (지부 코드)
    CRET_GBN (OCR/MICR 생성 구분)
    DEMD_YRMN (청구 년월)
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
            dobj  = CALLindtnpaper_list_SEL1(Conn, dobj);           //  리스트
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
        dobj  = CALLindtnpaper_list_SEL1(Conn, dobj);           //  리스트
        return dobj;
    }
    // 리스트
    public DOBJ CALLindtnpaper_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
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
        String   CRET_GBN = dobj.getRetObject("S").getRecord().get("CRET_GBN");   //OCR/MICR 생성 구분
        String   DEMD_FROM = dobj.getRetObject("S").getRecord().get("DEMD_FROM");   //청구시작일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   DEMD_TO = dobj.getRetObject("S").getRecord().get("DEMD_TO");   //청구종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XB.UPSO_CD  ,  XB.UPSO_NM  ,  DECODE(XB.PAYPRES_GBN,  'B',  XB.MNGEMSTR_NM,  XB.PERMMSTR_NM)  PAYPRES_NM  ,  DECODE(XB.MAIL_RCPT,  'U',  XB.UPSO_NEW_ADDR1  ||  DECODE(XB.UPSO_NEW_ADDR2,  '',  '',  ',  '||XB.UPSO_NEW_ADDR2)  ||  XB.UPSO_REF_INFO,  'B',  XB.MNGEMSTR_NEW_ADDR1  ||  DECODE(XB.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '||XB.MNGEMSTR_NEW_ADDR2)  ||  XB.MNGEMSTR_REF_INFO,    XB.PERMMSTR_NEW_ADDR1  ||  DECODE(XB.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '||XB.PERMMSTR_NEW_ADDR2)  ||  XB.PERMMSTR_REF_INFO  )  UPSO_ADDR  ,  DECODE(XB.MAIL_RCPT,  'U',  XB.UPSO_NEW_ZIP,  'B',  XB.MNGEMSTR_NEW_ZIP,  XB.PERMMSTR_NEW_ZIP)  UPSO_ZIP  ,  XA.MONPRNCFEE  ,  XA.TOT_DEMD_AMT  -  (XA.TOT_ADDT_AMT  +  XA.TOT_EADDT_AMT)  -  XA.MONPRNCFEE  NONPY_AMT  ,  XA.TOT_ADDT_AMT  +  XA.TOT_EADDT_AMT  TOT_ADDT_AMT  ,  XA.TOT_DEMD_AMT  ,  XA.START_YRMN  ,  XA.END_YRMN  ,  XC.BIPLC_NM  BRAN_NM  ,  XC.ADDR||'  '||XC.HNM  BRAN_ADDR  ,  XC.POST_NUM  ,  XC.PHON_NUM  BRAN_PHON  ,  XC.FAX_NUM  BRAN_FAX  ,  XD.GRADNM  ,  XA.DEMD_DAY  ,  XA.DEMD_NUM  ,  DECODE(XA.PRNT_DAY,  NULL,  0,  1)  PRINTED  ,  XB.MAIL_RCPT  ,  XB.CLIENT_NUM  ,  DECODE(XA.INSPRES_ID,  'DEMD_MICR_OPEN',  '자동',  '')  AS  AUTO_YN  FROM  GIBU.TBRA_DEMD_INDTN  XA  ,  GIBU.TBRA_UPSO  XB  ,  INSA.TCPM_BIPLC  XC  ,  (   \n";
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
        sobj.setString("CRET_GBN", CRET_GBN);               //OCR/MICR 생성 구분
        sobj.setString("DEMD_FROM", DEMD_FROM);               //청구시작일
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("DEMD_TO", DEMD_TO);               //청구종료일
        return sobj;
    }
    //##**$$indtnpaper_list
    //##**$$end
}
