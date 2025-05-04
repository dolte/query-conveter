
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s09_2
{
    public bra01_s09_2()
    {
    }
    //##**$$upso_visit_list
    /* * 프로그램명 : bra01_s09
    * 작성자 : 서정재
    * 작성일 : 2009/12/04
    * 설명 : 등록된 업소방문 내용을 조회한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
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
            dobj  = CALLupso_visit_list_SEL1(Conn, dobj);           //  업소방문리스트
            dobj  = CALLupso_visit_list_SEL2(Conn, dobj);           //  업소정보
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
        dobj  = CALLupso_visit_list_SEL1(Conn, dobj);           //  업소방문리스트
        dobj  = CALLupso_visit_list_SEL2(Conn, dobj);           //  업소정보
        return dobj;
    }
    // 업소방문리스트
    // 출장,최고서등록정보,업소클릭콜, 업소명 변경등 등록된 정보를 조회한다.
    public DOBJ CALLupso_visit_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
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
        String   START_DAY = dvobj.getRecord().get("START_DAY");   //시작일
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   END_DAY = dvobj.getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.VISIT_DAY  ,  A.VISIT_TIME  ,  A.JOB_GBN  ,  B.CODE_NM  ,  A.CONSPRES  ,  A.REMAK  ,  A.CONS_DATE  ,  A.CONS_SEQ  ,  C.FILE_ROUT,  C.FILE_NM  ,  A.VISIT_SEQ  ,  FIDU.GET_STAFF_NM(A.INSPRES_ID)  AS  INSPRES_NM  ,  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  AS  INS_DATE  ,  TO_CHAR(A.INS_DATE,  'yyyymmddhh24miss')  AS  INS_DATE2  ,  D.FILE_TYPE  ,  DECODE(D.FILE_TYPE,  'mp4',  D.FILE_ROUT,  '/home/jeus/komca_web/upload')  AS  IMG_ROUT  ,  D.FILE_TEMPNM  AS  IMG_TEMPNM  ,  A.SATN_NUM  ,  '0'  AS  CNT1  ,  '0'  AS  CNT2  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  FIDU.TENV_CODE  B  ,  GIBU.TBRA_UPSO_ADRS_FILEINFO  C  ,  GIBU.TBRA_UPSO_VISIT_ATTCH  D  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.HIGH_CD  =  '00198'   \n";
        query +=" AND  A.JOB_GBN  =  B.CODE_CD  AND	A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  A.CONS_DATE  =  C.CONS_DATE(+)   \n";
        query +=" AND  A.CONS_SEQ  =  C.CONS_SEQ(+)   \n";
        query +=" AND  A.UPSO_CD  =  D.UPSO_CD(+)   \n";
        query +=" AND  A.VISIT_DAY  =  D.VISIT_DAY(+)   \n";
        query +=" AND  A.VISIT_SEQ  =  D.VISIT_SEQ(+)   \n";
        query +=" AND  A.JOB_GBN  =  D.JOB_GBN(+)  ORDER  BY  VISIT_DAY  DESC,  A.VISIT_SEQ  DESC ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 업소정보
    // 업소의 기본정보를 조회한다.
    public DOBJ CALLupso_visit_list_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
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
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.MNGEMSTR_NM  ,  XA.PERMMSTR_NM  ,  XA.STAFF_CD  ,  XC.HAN_NM  AS  STAFF_NM  ,  XA.UPSO_PHON  ,  XA.UPSO_NEW_ZIP  AS  UPSO_ZIP  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,'',  '',',  '  ||  XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  AS  ADDR  ,  TRIM(XB.BSTYP_CD)  ||  XB.UPSO_GRAD  AS  GRAD  ,  XB.GRADNM  ,  TO_CHAR(XB.MONPRNCFEE,  '999,999,999,999')  AS  MONPRNCFEE  ,  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.OPBI_DAY  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  ZB.MONPRNCFEE  ,  ZC.GRADNM  ,  TRIM(ZB.BSTYP_CD)  AS  BSTYP_CD  ,  ZB.UPSO_GRAD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(A.JOIN_SEQ)  AS  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  :UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  XB  ,  INSA.TINS_MST01  XC  WHERE  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.STAFF_NUM(+)  =  XA.STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$upso_visit_list
    //##**$$end
}
