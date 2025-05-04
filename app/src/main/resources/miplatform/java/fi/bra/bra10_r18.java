
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_r18
{
    public bra10_r18()
    {
    }
    //##**$$sel_demd_stomu
    /*
    */
    public DOBJ CTLsel_demd_stomu(DOBJ dobj)
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
            dobj  = CALLsel_demd_stomu_SEL1(Conn, dobj);           //  매장음악사업자 청구자료조회
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
    public DOBJ CTLsel_demd_stomu( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_demd_stomu_SEL1(Conn, dobj);           //  매장음악사업자 청구자료조회
        return dobj;
    }
    // 매장음악사업자 청구자료조회
    public DOBJ CALLsel_demd_stomu_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_demd_stomu_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_demd_stomu_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //청구 년월
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BSCON_UPSO_CD  ,  UPSO_CD  ,  AUTO_YN  ,  BRAN_NM  ,  UPSO_NM  ,  BSTYP_NM  ,  ADDR  ,  UPSO_PHON  ,  EMAIL_ADDR  ,  BIOWN_NUM  ,  REMAK  ,  MONPRNCFEE  AS  DEMD_AMT  ,  MONPRNCFEE  AS  USE_AMT  ,  0  AS  ADDT_AMT  ,  0  AS  EADDT_AMT  FROM  (   \n";
        query +=" SELECT  B.BSCON_UPSO_CD  ,  C.UPSO_CD  ,  (CASE  WHEN   \n";
        query +=" (SELECT  COUNT  (1)  AS  CNT  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  TERM_YN  =  'N')  >  0  THEN  'Y'  ELSE  'N'  END)  AS  AUTO_YN  ,  GIBU.GET_BRAN_NM  (C.BRAN_CD)  AS  BRAN_NM  ,  C.UPSO_NM  ,   \n";
        query +=" (SELECT  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  GRAD_GBN  =  D.BSTYP_CD   \n";
        query +=" AND  BSTYP_CD  =  'z')  AS  BSTYP_NM  ,  C.UPSO_NEW_ADDR1  ||  ',  '  ||  C.UPSO_NEW_ADDR2  ||  C.UPSO_REF_INFO  AS  ADDR  ,  C.UPSO_PHON  ,  C.EMAIL_ADDR  ,  C.BIOWN_NUM  ,  ''  AS  REMAK  ,  D.MONPRNCFEE  FROM  GIBU.TBRA_STOMU_CONTRINFO  B  ,  GIBU.TBRA_UPSO  C  ,  GIBU.TBRA_UPSORTAL_INFO  D  ,  (   \n";
        query +=" SELECT  XA.UPSO_CD,  MAX  (XA.JOIN_SEQ)  AS  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  XA  WHERE  APPL_DAY  <=  SUBSTR(:DEMD_YRMN,  0,  6)  ||  '31'  GROUP  BY  XA.UPSO_CD)  E  WHERE  B.UPSO_CD  =  C.UPSO_CD(+)   \n";
        query +=" AND  C.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  D.JOIN_SEQ  =  E.JOIN_SEQ   \n";
        query +=" AND  D.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  ((B.BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  :BSCON_CD  IS  NOT  NULL)   \n";
        query +=" OR  (:BSCON_CD  IS  NULL))   \n";
        query +=" AND  B.UPSO_CD  IS  NOT  NULL  ORDER  BY  BSCON_UPSO_CD) ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        return sobj;
    }
    //##**$$sel_demd_stomu
    //##**$$end
}
