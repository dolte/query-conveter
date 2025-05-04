
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s15
{
    public bra01_s15()
    {
    }
    //##**$$upso_list_kylog
    /*
    */
    public DOBJ CTLupso_list_kylog(DOBJ dobj)
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
            dobj  = CALLupso_list_kylog_SEL1(Conn, dobj);           //  업소목록
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
    public DOBJ CTLupso_list_kylog( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_list_kylog_SEL1(Conn, dobj);           //  업소목록
        return dobj;
    }
    // 업소목록
    public DOBJ CALLupso_list_kylog_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_list_kylog_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_list_kylog_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  INS_DATE  ,  UPSO_CD  ,  BRAN_CD  ,  BRAN_NM  ,  UPSO_NM  ,  GRAD_CD  ,  GRAD_NM  ,  MNGEMSTR_NM  ,  PERMMSTR_NM  ,  UPSO_ADDR  ,  DONG  ,  UPSO_NEW_ZIP  ,  UPSO_PHON  ,  MNGEMSTR_HPNUM  ,  ROOM_NAME  ,  SERIAL_NO  ,  DECODE(SERIAL_NO,  NULL,  0,  1)  AS  SERIAL_GBN  ,  CO_STATUS_NM  ,  HYUPAEUP_GBN  ,  STAFF_CD  FROM  (   \n";
        query +=" SELECT  TO_CHAR(A.INS_DATE,  'YYYY-MM-DD')  AS  INS_DATE  ,  A.UPSO_CD  ,  A.BRAN_CD  ,  AA.DEPT_NM  AS  BRAN_NM  ,  A.UPSO_NM  ,  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(A.UPSO_CD),1,3)  AS  GRAD_CD  ,  AAA.GRADNM  AS  GRAD_NM  ,  A.MNGEMSTR_NM  ,  A.PERMMSTR_NM  ,  A.UPSO_NEW_ADDR1  ||  DECODE(A.UPSO_NEW_ADDR2,  '',  '',  ',  '||A.UPSO_NEW_ADDR2)  ||  A.UPSO_REF_INFO  AS  UPSO_ADDR  ,   \n";
        query +=" (SELECT  DECODE(COURT_NM,  NULL,  TOWNTWSHP,  COURT_NM)  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  A.UPSO_BD_MNG_NUM  )  AS  DONG  ,  A.UPSO_NEW_ZIP  ,  A.UPSO_PHON  ,  A.MNGEMSTR_HPNUM  ,   \n";
        query +=" (SELECT  /*+  INDEX_DESC  (KDS_SHOPROOM  PK_KDS_SM_SEQ)  */  ROOM_NAME  FROM  LOG.KDS_SHOPROOM  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  CO_STATUS  =  '07001'   \n";
        query +=" AND  ROWNUM  =  1)  AS  ROOM_NAME  ,   \n";
        query +=" (SELECT  /*+  INDEX_DESC  (KDS_SHOPROOM  PK_KDS_SM_SEQ)  */  SERIAL_NO  FROM  LOG.KDS_SHOPROOM  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  CO_STATUS  =  '07001'   \n";
        query +=" AND  ROWNUM  =  1)  AS  SERIAL_NO  ,  LOG.FT_GET_UPSO_STATUS_NM(A.UPSO_CD)  AS  CO_STATUS_NM  ,  GIBU.FT_GET_UPSO_HYUPAEUP(A.UPSO_CD)  AS  HYUPAEUP_GBN  ,  A.STAFF_CD  FROM  GIBU.TBRA_UPSO  A  ,  INSA.TCPM_DEPT  AA  ,  GIBU.TBRA_BSTYPGRAD  AAA  ,  LOG.KDS_SHOP  B  WHERE  A.BRAN_CD  =  AA.GIBU   \n";
        query +=" AND  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(A.UPSO_CD),1,3)  =  AAA.BSTYP_CD  ||  AAA.GRAD_GBN   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.STAFF_CD  =  DECODE(:STAFF_CD,  NULL,  A.STAFF_CD,  :STAFF_CD)   \n";
        query +=" AND  LOG.FT_GET_UPSO_STATUS_NM(A.UPSO_CD)  <>  '이관') ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$upso_list_kylog
    //##**$$end
}
