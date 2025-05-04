
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra08_r01
{
    public bra08_r01()
    {
    }
    //##**$$new_upso_list
    /* * 프로그램명 : bra08_r01
    * 작성자 : 서정재
    * 작성일 : 2009/10/09
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLnew_upso_list(DOBJ dobj)
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
            dobj  = CALLnew_upso_list_SEL1(Conn, dobj);           //  신규업소안내문리스트
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
    public DOBJ CTLnew_upso_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLnew_upso_list_SEL1(Conn, dobj);           //  신규업소안내문리스트
        return dobj;
    }
    // 신규업소안내문리스트
    public DOBJ CALLnew_upso_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLnew_upso_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnew_upso_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  MAX(A.UPSO_NM)  AS  UPSO_NM  ,  MAX(DECODE(A.PAYPRES_GBN,  'B',  A.MNGEMSTR_NM,  A.PERMMSTR_NM))  AS  RECV_NM  ,  MAX(DECODE(A.MAIL_RCPT,  'U',  A.UPSO_NEW_ZIP,  'B',  A.MNGEMSTR_NEW_ZIP,  A.PERMMSTR_NEW_ZIP))  AS  RECV_ZIP  ,  MAX(DECODE(A.MAIL_RCPT,  'U',  A.UPSO_NEW_ADDR1  ||  DECODE(A.UPSO_NEW_ADDR2,  '',  '',  ',  '||A.UPSO_NEW_ADDR2)  ||  A.UPSO_REF_INFO  ,  'B',  A.MNGEMSTR_NEW_ADDR1  ||  DECODE(A.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '||A.MNGEMSTR_NEW_ADDR2)  ||  A.MNGEMSTR_REF_INFO    ,  A.PERMMSTR_NEW_ADDR1  ||  DECODE(A.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '||A.PERMMSTR_NEW_ADDR2)  ||  A.PERMMSTR_REF_INFO  ))  AS  RECV_ADDR  ,  SUM(NVL(B.REPT_AMT,0))  AS  AMT  ,  MAX(D.BIPLC_NM)  AS  BRAN_NM  ,   \n";
        query +=" (SELECT  IPPBX_INPHONE_NUM  FROM  FIDU.TENV_MEMBER  WHERE  USER_ID  =  A.STAFF_CD)  AS  BRAN_TEL  ,  MAX(D.POST_NUM)  AS  BRAN_ZIP  ,  MAX(D.ADDR||'  '||D.HNM)  AS  BRAN_ADDR  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  MAPPING_DAY  ,  REPT_AMT  ,  UPSO_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD  UNION  ALL   \n";
        query +=" SELECT  MAPPING_DAY  ,  DISTR_AMT  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD  )B  ,  INSA.TCPM_BIPLC  D  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.NEW_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  B.MAPPING_DAY  =  A.NEW_DAY   \n";
        query +=" AND  D.GIBU  =  A.BRAN_CD  GROUP  BY  A.UPSO_CD  ORDER  BY  UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    //##**$$new_upso_list
    //##**$$end
}
